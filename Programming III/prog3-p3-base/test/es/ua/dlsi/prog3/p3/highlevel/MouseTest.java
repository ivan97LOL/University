package es.ua.dlsi.prog3.p3.highlevel;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.nio.BufferOverflowException;

import org.junit.Before;
import org.junit.Test;

import es.ua.dlsi.prog3.p3.lowlevel.Channel;
import es.ua.dlsi.prog3.p3.lowlevel.IODevice;
import es.ua.dlsi.prog3.p3.lowlevel.IODeviceTest;
import es.ua.dlsi.prog3.p3.lowlevel.InputDevice;

public class MouseTest {

	Mouse mouse3, mouse4, mouse_nochan;
	IODevice out3, out4;
	Channel channel3, channel4;
	static byte[] coords = {1,2};
	
	@Before
	public void setUp() throws Exception {
		mouse3 = new Mouse();
		mouse4 = new Mouse();
		mouse_nochan = new Mouse();
		out3 = IODeviceTest.createIODevice(3);
		out4 = IODeviceTest.createIODevice(4);
		channel3 = new Channel(mouse3, out3);
		channel4 = new Channel(mouse4, out4);
	}

	/**
	 * put() throws IllegalStateException if no channel is attached to this device
	 */
	@Test(expected=IllegalStateException.class)
	public final void testPutException1() {
		mouse_nochan.put(coords[0],coords[1]);
	}

	/**
	 * Tests BufferOverflowException when attemping to send two bytes when the channel is full
	 */
	@Test(expected=BufferOverflowException.class)
	public final void testPutException3() {
			mouse4.put(coords[0],coords[1]);
			mouse4.put(coords[0],coords[1]);
			mouse4.put(coords[0],coords[1]);
	}
	
	
	

}
