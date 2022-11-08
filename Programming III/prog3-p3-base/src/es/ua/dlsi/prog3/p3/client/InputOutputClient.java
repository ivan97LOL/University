/**
 * @author Iván Álvarez García 49623492A
 */

package es.ua.dlsi.prog3.p3.client;

import java.awt.DisplayMode;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import es.ua.dlsi.prog3.p3.highlevel.Display;
import es.ua.dlsi.prog3.p3.highlevel.Keyboard;
import es.ua.dlsi.prog3.p3.highlevel.LinePrinter;
import es.ua.dlsi.prog3.p3.highlevel.Mouse;
import es.ua.dlsi.prog3.p3.highlevel.exceptions.NoLineForPrintingException;
import es.ua.dlsi.prog3.p3.lowlevel.Channel;


/**
 * This class runs a process that tracks the mouse cursor,
 * maps its coordinates to a Display object size, and sends them
 * via a Mouse Object to that Display. 
 * 
 * It iteratively refresh the display to show its content as an ASCII frame on the standard output
 * 
 * -- Testes on Linux and Macos, i. e., on POSIX compliant console terminals.
 *
 */
class MouseTracker implements Runnable {

	Mouse mouse;
	Display display;
	
	int linesPrinted;
	
	private static final String CSI="\033["; // CSI: Control Sequence Introducer
	private static final String CURSOR_HOME="\033[H"; // Cursor home

	/**
	 * Creates a MouseTracker process
	 * @param mouse The mouse device to use for tracking
	 * @param display The display device to use for displaying
	 */
	public MouseTracker(Mouse mouse, Display display ) {
		this.mouse=mouse;
		this.display=display;
		this.linesPrinted=0;
	}

	
	/**
	 * Simulates the display of a black and white screen on an ASCII terminal
	 * 
	 * This is an example of a 5x5 screen with a pixel set at its center
	 *	╔═════╗ 
	 * 	║     ║
	 * 	║     ║
	 * 	║  *  ║
	 * 	║     ║
	 * 	║     ║
	 * 	╚═════╝
	 * The coordinates of the four corners of the screen are 
	 * upper left 	: (0,0)
	 * upper rigth 	: (4,0)
	 * lower left 	: (0,4)
	 * lower rigth 	: (4,4)
	 * 
	 * @param screen a matrix of bytes representing the content of the screen. Every non-zero value is considered as an ON pixel.
	 */
	 void displayScreen(byte[][] screen) {
		int heigth = screen.length;
		int width = screen[0].length;

		if (linesPrinted>0)
			System.out.println(CSI+linesPrinted+"A");
		/* screen top */
		System.out.print('╔');
		for (int i=0; i<width; i++) System.out.print('═');
		System.out.println('╗');

		/* actual screen content */
		for (int i=0; i<heigth; i++) {
			System.out.print('║');
			for (int j=0; j< width; j++)
				System.out.print(screen[i][j] != 0 ? '*' : ' ');
			System.out.println('║');
		}
		/* screen bottom */
		System.out.print('╚');
		for (int i=0; i<width; i++) System.out.print('═');
		System.out.println('╝');
		linesPrinted=heigth+3;
	}


	/**
	 * The process that actualy runs the mouse tracking loop
	 */
	@Override
	public void run() {
		Channel channel = new Channel(mouse, display);
		
		PointerInfo pinfo =  MouseInfo.getPointerInfo();		
		DisplayMode dmode =  pinfo.getDevice().getDisplayMode();
		int height = dmode.getHeight();
		int width = dmode.getWidth();
		
		Point point=null;
		double x,y;
		byte bx, by;
		long size = display.getDisplaySize(); 
		byte[][] screen;
		
		System.out.println("Actual screen size: ["+width+","+height+"] ---> Virtual screen size: ["+size+","+size+"]");

		//System.out.print(CSI+"2J"); clear screen
		while (true) {
			if(Thread.interrupted()) 
                return;
			pinfo =  MouseInfo.getPointerInfo();
			point =  pinfo.getLocation();
			
			// scale to [0..1]
			x = point.x/(double)width;
			y = point.y/(double)height;
			
			// scale to display size as bytes
			bx = (byte)Math.round(x*(size-1));
			by = (byte)Math.round(y*(size-1));
			mouse.put(bx,by);
			screen = display.refresh();
			displayScreen(screen);
			System.out.println("("+bx+","+by+")");
			linesPrinted++;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				break;
			}
		}
	}
	
}


/**
 * This class contains client code for the es.ua.dlsi.prog3.p3.highlevel library
 *
 */
public class InputOutputClient {

	/**
	 * Prints input from a Keyboard to a LinePrinter
	 * @param keyboard the keyboard device
	 * @param lp the printer device
	 */
	public static void printLine(Keyboard keyboard, LinePrinter lp) {
		Channel keyboard_printer = new Channel(keyboard,lp);

			System.out.println("Write something: ");
			try {
				byte[] buffer = new byte[keyboard_printer.getBufferSize()];
				int bytesRead = System.in.read(buffer);
				if (bytesRead>0)
					keyboard.put((char)bytesRead);
				for (int i=0; i< bytesRead; i++)
					keyboard.put((char)buffer[i]);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				System.out.println("LinePrinter prints:");
				System.out.print(lp.printLine());
			} catch (NoLineForPrintingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	

	/**
	 * 'Types' a meesage using a Keyboard device
	 * @param keyboard the device
	 * @param message the message
	 */
	public static void typeAMessage(Keyboard keyboard, String message) {
		char[] data = message.toCharArray();
		keyboard.put((char)data.length);
		for (int i=0; i<data.length; i++) {
			keyboard.put(data[i]);
		}
	}

	
	/**
	 * Runs a mouse tracker for some time.
	 * @param mouse the mouse device instance
	 * @param display the display device instance
	 */
	public static void runMouseTracker(Mouse mouse, Display display) {
		System.out.println("Running for 20 seconds... Move your mouse!");
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<?> future = executor.submit(new MouseTracker(mouse, display));
		try {
		    future.get(20, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			System.out.println("DONE!");
		    future.cancel(true);
		} catch (Exception e) {
		    // handle other exceptions
			System.out.println("WTF?");
			e.printStackTrace();

		} finally {
		    executor.shutdownNow();
		}		
	}
	/** Entry point.
	 * @param args no paramateres are needed
	 */
	public static void main(String[] args)  {
		Keyboard keyboard = new Keyboard();
		Mouse mouse = new Mouse();
		
		LinePrinter printer = new LinePrinter();
		Display display = new Display(20);

		System.out.println("---- Check Mouse --> Display ----");
		runMouseTracker(mouse, display);

		System.out.println("---- Check Keyboard --> LinePrinter ----");
		printLine(keyboard, printer);		
	}
}
