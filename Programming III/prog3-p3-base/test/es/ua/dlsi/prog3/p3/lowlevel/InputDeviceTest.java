package es.ua.dlsi.prog3.p3.lowlevel;

import static org.junit.Assert.*;

import java.nio.BufferOverflowException;

import org.junit.Before;
import org.junit.Test;

public class InputDeviceTest {

	InputDevice input;
	IODevice output;
	
	@Before
	public void setUp() throws Exception {
		input = new InputDevice();
		output = new IODevice(5);
	}

	/**
	 * Checks there is no buffer for an InputDevice
	 */
	@Test
	public final void testInputDevice() {
		assertEquals("Buffer de dispositivo de entrada == 0",0,input.getBufferSize());
	}
	
	/**
	 * put() throws IllegalStateException when there is no channel attached to this device
	 */
	@Test(expected=IllegalStateException.class)
	public final void testPutNoChannel() {
		input.put(new byte[] {1,2,3});
	}
	
	/**
	 * Tests that put() actually sends data to the channel
	 */
	@Test
	public final void testPut() {
		Channel channel  = new Channel(input,output);
		input.put(new byte[] {1,2,3,4});
		assertTrue(channel.hasData());
	}

	/**
	 * sendToChannel() throws IllegalStateException when there is no channel attached to this device
	 */
	@Test(expected=IllegalStateException.class)
	public final void testSendToChannelException1() {
		input.sendToChannel((byte)0);
	}

	/**
	 * Tests that sendToChannel() actually sends data to the channel
	 */
	@Test
	public final void testSendToChannel() {
		Channel channel  = new Channel(input,output);
		for (byte i=0; i< 4; i++)
			input.sendToChannel(i);
		assertTrue(channel.hasData());
	}


}
