package es.ua.dlsi.prog3.p3.highlevel;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.nio.BufferUnderflowException;
import java.util.Arrays;


import org.junit.Before;
import org.junit.Test;

import es.ua.dlsi.prog3.p3.highlevel.exceptions.NoLineForPrintingException;
import es.ua.dlsi.prog3.p3.lowlevel.Channel;
import es.ua.dlsi.prog3.p3.lowlevel.IODevice;
import es.ua.dlsi.prog3.p3.lowlevel.IODeviceTest;
import es.ua.dlsi.prog3.p3.lowlevel.OutputDeviceTest;

public class DisplayTest {
	/**
	 * 5x5 blank screen as returned by Display.refresh() 
	 */
	private static byte[][] blankScreen = {
			{ 0,0,0,0,0 },
			{ 0,0,0,0,0 },
			{ 0,0,0,0,0 },
			{ 0,0,0,0,0 },
			{ 0,0,0,0,0 }
	};


	Display display, display_nochan;
	IODevice in;
	Channel channel;
	static final int DISPLAY_SIZE=5;

	/** 
	 * Setup displays, input device and channel
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		display = new Display(DISPLAY_SIZE);
		display_nochan = new Display(DISPLAY_SIZE);
		in = IODeviceTest.createIODevice(0); // input device
		channel = new Channel(in, display);
	}

	/** 
	 * Test the constructor did its work correctly
	 */
	@Test
	public final void testDisplayInit() {
		assertEquals("Display size",DISPLAY_SIZE,display.getDisplaySize());
		assertEquals("Display buffer size",DISPLAY_SIZE*DISPLAY_SIZE*2,display.getBufferSize());
		assertTrue("Display is initially blank",Arrays.deepEquals(blankScreen, display.refresh()));
	}

	/**
	 * A Display with no channel throws IllegalStateException at calling refresh()
	 */
	@Test(expected=IllegalStateException.class)
	public final void testRefreshException()  {
		display_nochan.refresh();
	}
	
	/**
	 * Checks clear() does its job. Depends on refresh() working!
	 */
	@Test
	public final void testClear() {
		for (byte i=0; i<DISPLAY_SIZE/2; i++)
			for (byte j=0; j<DISPLAY_SIZE/2; j++)
				OutputDeviceTest.sendBytes(channel, new byte[] {i, j} );
		byte[][] screen = display.refresh();
		assertNotEquals("Pixel set",0,screen[0][0]); // check at least one pixel set
		display.clear();
		assertTrue("Display cleared",Arrays.deepEquals(blankScreen, display.refresh()));
	}


}
