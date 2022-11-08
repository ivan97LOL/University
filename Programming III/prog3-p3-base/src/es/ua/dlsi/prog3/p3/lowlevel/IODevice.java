/**
 * @author Iván Álvarez García 49623492A
 */
package es.ua.dlsi.prog3.p3.lowlevel;

import java.util.Objects;

/**
 * It represents a communication device at low level able to work with bytes 
 *
 */
public class IODevice {

	/**
     * An IO device must be linked to a channel to work
	 */
	private Channel channel;
	
	private int bufferSize;
	
	/**
     * Constructor for devices 
	 */
	 IODevice() {
		bufferSize=0;
		channel=null;
	}
	
	/**
     * Constructor for devices 
	 */
	 IODevice(int bufferSize) {
		this.bufferSize=bufferSize;
		channel=null;
	}

	/**
	 * Size of the buffer of the channel attached to this device
	 * @return the channel's buffer size
	 */
	public int getBufferSize() {
		return bufferSize;
	}

	/**
	 * Sets the channel this device will use to send/receive data
	 * 
	 * @param channel channel this device will use for communications. Must not be null.
	 * @throws NullPointerException if the argument is null
	 */
	void setChannel(Channel channel) {
		this.channel = Objects.requireNonNull(channel, "Attempt to set a IODevice channel to null");
	}
	
	/**
	 * Gets the channel this device is connected to
	 * @return the channel object
	 * @throws IllegalStateException if the channel has not been set yet (the device can't communicate) 
	 */
	protected Channel getChannel() {
		if (channel==null)
			throw new IllegalStateException("The channel is not set");
		return channel;
	}

}
