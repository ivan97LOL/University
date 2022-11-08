package es.ua.dlsi.prog3.p3.highlevel;

import static org.junit.Assert.*;

import java.nio.BufferOverflowException;

import org.junit.Before;
import org.junit.Test;

import es.ua.dlsi.prog3.p3.lowlevel.Channel;
import es.ua.dlsi.prog3.p3.lowlevel.IODevice;
import es.ua.dlsi.prog3.p3.lowlevel.IODeviceTest;
import es.ua.dlsi.prog3.p3.lowlevel.InputDevice;

public class KeyboardTest {

	Keyboard kb1, kb_nochan;
	IODevice out;
	Channel channel;
	
	@Before
	public void setUp() throws Exception {
		kb1 = new Keyboard();
		kb_nochan = new Keyboard();
		out = IODeviceTest.createIODevice(3);
		channel = new Channel(kb1, out);
	}

	/**
	 * A Keyboard with no channel attached throws IllegalStateException at calling put()
	 */
	@Test(expected=IllegalStateException.class)
	public final void testPutException1() {
		kb_nochan.put('X');
	}

	/**
	 * put() throws BufferOverflowException when called when the channel is full
	 */
	@Test(expected=BufferOverflowException.class)
	public final void testPutException2() {
		for (int i=0; i<4;i++)
			kb1.put('X');
	}

}
