package es.ua.dlsi.prog3.p3.lowlevel;

import static org.junit.Assert.*;

import java.nio.BufferUnderflowException;

import org.junit.Before;
import org.junit.Test;

public class OutputDeviceTest {

	private Channel channel3;
	private Channel channel4;
	private OutputDevice out_nochan;
	private OutputDevice out3;
	private OutputDevice out4;
	private IODevice in;
	
	/**
	 * Sends numBytes bytes to a channel, starting at 0,1,2,...
	 * @param channel the channel to send the bytes to
	 * @param numBytes how many bytes to send
	 */
	public static void sendBytes(Channel channel, int numBytes) {
		for (byte b=0; b<numBytes; b++)
			channel.input(b);
	}

	/**
	 * Sends some bytes to a channel
	 * @param channel the channel to send the bytes to
	 * @param bytes an array containing the bytes to send
	 */
	public static void sendBytes(Channel channel, byte[] bytes) {
		for (byte b=0; b<bytes.length; b++)
			channel.input(bytes[b]);
	}

	/**
	 * Sends a string as a stream of bytes to a channel
	 * @param channel the channel to send the bytes to
	 * @param s the string to send
	 */
	public static void sendString(Channel channel, String s) {
		char[] str = s.toCharArray();
		channel.input((byte)str.length);
		for (int i=0; i< str.length; i++) 
			channel.input((byte)str[i]);
	}
	
	/**
	 * Setting up things...
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		out_nochan = new OutputDevice(3);
		out3 = new OutputDevice(3);
		out4 = new OutputDevice(4);
		in = new IODevice();
		channel3 = new Channel(in,out3);
		channel4 = new Channel(in,out4);
	}

	/**
	 * receiveFromChannel() throws IllegalStateException when there is no channel attached to this device
	 */
	@Test(expected=IllegalStateException.class)
	public final void testReceiveFromChannelException() {
		out_nochan.receiveFromChannel();
	}

	/**
	 * receiveFromChannel() throws BufferUnderflowException when there is no data to read from the channel
	 */
	@Test(expected=BufferUnderflowException.class)
	public final void testReceiveFromChannelException2() {
		out3.receiveFromChannel();
	}

	/**
	 * Checks that receiveFromChannel() actually reads something from the channel
	 */
	@Test
	public final void testReceiveFromChannel() {
		sendBytes(channel3, 1);
		assertEquals("Output device receives byte",0, out3.receiveFromChannel());
	}
	
	
	/**
	 * get() throws IllegalStateException when there is no channel attached to this device
	 */
	@Test(expected=IllegalStateException.class)
	public final void testGetExceptionNoChan() {
		out_nochan.get(2);		
	}

	/**
	 * get() returns an empty array when there is no data to read from the channel
	 */
	@Test
	public final void testGetNoData() {
		byte[] data = out3.get(2);
		assertEquals("no bytes read",0,data.length);
	}

	/* ********** Tests for readStoredString() ************** */
	/* DON'T TOUCH! */
	@Test
	public final void testReadEmptyString() {
		String s = out3.readStoredString();
		assertEquals("Empty string read","",s);
	}

	@Test(expected=IllegalStateException.class)
	public final void testReadStoredStringException() {
		out_nochan.readStoredString();
	}

	@Test(expected=BufferUnderflowException.class)
	public final void testReadStoredStringException2() {
		channel3.input((byte)2);
		channel3.input((byte)'A');
		out3.readStoredString();
	}

	@Test
	public final void testReadStoredString1() {
		sendString(channel3, "X");
		String s = out3.readStoredString();
		assertEquals("String of length 1 read","X",s);
	}

	@Test
	public final void testReadStoredString3() {
		sendString(channel4, "XYZ");
		String s = out4.readStoredString();
		assertEquals("String of length 3 read","XYZ",s);
	}

}
