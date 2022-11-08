package es.ua.dlsi.prog3.p3.highlevel;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.BufferUnderflowException;

import org.junit.Before;
import org.junit.Test;

import es.ua.dlsi.prog3.p3.highlevel.exceptions.NoLineForPrintingException;
import es.ua.dlsi.prog3.p3.lowlevel.Channel;
import es.ua.dlsi.prog3.p3.lowlevel.IODevice;
import es.ua.dlsi.prog3.p3.lowlevel.IODeviceTest;
import es.ua.dlsi.prog3.p3.lowlevel.OutputDevice;
import es.ua.dlsi.prog3.p3.lowlevel.OutputDeviceTest;

public class LinePrinterTest {

	LinePrinter lp, lp_nochan;
	IODevice in;
	Channel channel;
	static final int MAX_LINE_LENGTH=80;
	
	@Before
	public void setUp() throws Exception {
		lp = new LinePrinter();
		lp_nochan = new LinePrinter();
		in = IODeviceTest.createIODevice(0); // input device
		channel = new Channel(in, lp);
	}

	/**
	 * printLine() throws IllegalStateException if no channel is attached to this device
	 * @throws NoLineForPrintingException
	 */
	@Test(expected=IllegalStateException.class)
	public final void testPrintLineException1() throws NoLineForPrintingException {
		lp_nochan.printLine();
	}
	
	/**
	 * printLine() throws NoLineForPrintingException when there is no data to read from the channel
	 * @throws NoLineForPrintingException
	 */
	@Test(expected=NoLineForPrintingException.class)
	public final void testPrintLineException2() throws NoLineForPrintingException {
		lp.printLine();
	}
	
	/**
	 * Tests normal operation of printLine()
	 * @throws NoLineForPrintingException
	 */
	@Test
	public final void testPrintLine1() throws NoLineForPrintingException {
		String s = "Programming is fun!";
		OutputDeviceTest.sendString(channel, s);
		assertEquals("String correctly read",s,lp.printLine());
	}


}
