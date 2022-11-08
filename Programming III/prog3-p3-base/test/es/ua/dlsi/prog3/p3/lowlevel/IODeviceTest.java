/**
 * 
 */
package es.ua.dlsi.prog3.p3.lowlevel;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for es.ua.dlsi.prog3.p3.lowlevel.IODevice
 * 
 * @author pierre
 *
 */
public class IODeviceTest {

	IODevice unbuffered;
	IODevice buffered;
	
	public static IODevice createIODevice(int bufferSize) {
		if (bufferSize>0)
			return new IODevice(bufferSize);
		else
			return new IODevice();
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		unbuffered = new IODevice();
		buffered = new IODevice(5);
	}

	/**
	 * Test method for {@link es.ua.dlsi.prog3.p3.lowlevel.IODevice#IODevice()}.
	 */
	@Test
	public final void testIODevice1() {
		assertEquals("Buffer size = 0 for unbuffered device.", 0, unbuffered.getBufferSize());
	}

	/**
	 * Test method for {@link es.ua.dlsi.prog3.p3.lowlevel.IODevice#IODevice()}.
	 */
	@Test
	public final void testIODevice2() {
		assertEquals("Buffer size = 5 for buffered device.", 5, buffered.getBufferSize());
	}

	/**
	 * Test method for {@link es.ua.dlsi.prog3.p3.lowlevel.IODevice#IODevice(int)}.
	 */
	@Test(expected=IllegalStateException.class)
	public final void testGetChannelException() {
		unbuffered.getChannel();
	}

	/**
	 * Test method for {@link es.ua.dlsi.prog3.p3.lowlevel.IODevice#IODevice(int)}.
	 */
	@Test(expected=IllegalStateException.class)
	public final void testGetChannelException2() {
		buffered.getChannel();
	}


	/**
	 * Test method for {@link es.ua.dlsi.prog3.p3.lowlevel.IODevice#setChannel(es.ua.dlsi.prog3.p3.lowlevel.Channel)}.
	 */
	@Test
	public final void testSetChannel() {
		Channel channel = new Channel(unbuffered,buffered);
		// we don't want to test Channel's ctor, so we set the channel explicity
		unbuffered.setChannel(channel);
		assertSame("Channel set for input device",channel,unbuffered.getChannel());
	}

	/**
	 * Test method for {@link es.ua.dlsi.prog3.p3.lowlevel.IODevice#setChannel(es.ua.dlsi.prog3.p3.lowlevel.Channel)}.
	 */
	@Test
	public final void testSetChannel2() {
		Channel channel = new Channel(unbuffered,buffered);
		// we don't want to test Channel's ctor, so we set the channel explicity
		buffered.setChannel(channel);
		assertSame("Channel set for output device",channel,buffered.getChannel());
	}

	/**
	 * Test method for {@link es.ua.dlsi.prog3.p3.lowlevel.IODevice#setChannel(es.ua.dlsi.prog3.p3.lowlevel.Channel)}.
	 */
	@Test(expected=NullPointerException.class)
	public final void testSetChannelException() {
		buffered.setChannel(null);
	}


}
