/**
 * 
 */
package es.ua.dlsi.prog3.p3.lowlevel;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.util.Objects;
import java.util.Scanner;

/** This class represents a communication channel implemented
 * as a circular buffer of bytes
 * @author Iván Álvarez García 49623492A
 */
public class Channel {
	/**
	 * byte array containing the data available in the chanel
	 * It is a circular buffer
	 * Whenever byteCount == buffer.length, trying to write into the buffer produces an exception
	 * Whenever byteCount == 0, trying to read from the buffer produces an exception
	 */
	private byte[] buffer;
	/**
	 * index of the next buffer position available for reading a byte
	 * for this index to be valid, byteCount must be > 0
	 * MUST be a number between 0 and buffer.length
	 */
	private int readPointer;
	/**
	 * index of the next buffer position available for storing a byte
	 * for this index to be valid, byteCount must be < buffer.length
	 * MUST be a number between 0 and buffer.length
	 */
	private int writePointer;
	/**
	 * Number of bytes available for reading in the buffer
	 * MUST be a number between 0 and buffer.length
	 */
	private int byteCount;

	
	
	/**
	 * Constructs a channel that connects an input device with an output device 
	 * Doesn't check if the devices are actually input/output devices.
	 * 
	 * Sets the buffer size of the channel to the buffer size of the output device
	 * 
	 * If a channel is connected to two input devices or two output devices,
	 * both will try to send/receive bytes to/from the channel, which eventually
	 * will result in a BufferOverflow/BufferUnderflow exception.
	 * 
	 * @param input Input device
	 * @param output Output device
	 * @throws NullPointerException if either argument is null
	 * @throws IllegalArgumentException if the output device's buffer size is zero
	 */
	public Channel(IODevice input, IODevice output) {
		Objects.requireNonNull(input, "An input device must be provided");
		Objects.requireNonNull(output, "An output device must be provided");
		resetBuffer(output.getBufferSize());
		input.setChannel(this);
		output.setChannel(this);
	}


	/**
	 * initializes the channel's buffer, creating a brand new empty buffer of the desired size
	 * @param bufferSize size of the buffer
	 * @throws IllegalArgumentException if bufferSize is negative or cero
	 */
	void resetBuffer(int bufferSize) {
		if (bufferSize<=0)
			throw new IllegalArgumentException("A channel buffer size can't be negative");
		buffer = new byte[bufferSize];
		readPointer=0;
		writePointer=0;
		byteCount=0;
	}
	
	/**
	 * stores a byte at the next free slot in the buffer
	 * @param datum the byte to store
	 * @throws BufferOverflowException if the buffer is already full
	 */
	 void input(byte datum) {
		if (byteCount < buffer.length) {
			buffer[writePointer] = datum;
			writePointer = (writePointer+1) % buffer.length;
			byteCount++;
		} else
			throw new BufferOverflowException();
	}

	/**
	 * Obtains the next byte available from the channel
	 * @return a byte from the channel
	 * @throws BufferUnderflowException if there is no data to read from the buffer
	 */
	 byte output() {
		if (byteCount > 0) {
			byte datum = buffer[readPointer];
			readPointer = (readPointer+1) % buffer.length;
			byteCount--;
			return datum;
		} else
			throw new BufferUnderflowException();
	}
	
	/**
	 * Queries whether the buffer is full
	 * @return true if the buffer is full
	 */
	public boolean isFull() {
		return byteCount >= buffer.length;
	}
	
	/**
	 * Queries if theres is data to read in the buffer
	 * @return true if there is at least one byte to read from the buffer
	 */
	public boolean hasData() {
		return byteCount > 0;
	}
	
	/**
	 * Gets the size of the channel buffer
	 * @return the size of the channel buffer
	 */
	public int getBufferSize() {
		return buffer.length;
	}
	
	/**
	 * Main program for testing purposes
	 * @param args this program does not need arguments from the console
	 */
	public static void main(String[] args) {
		Channel c = new Channel(new IODevice(), new IODevice(10));
		
		String s;
		Scanner in = new Scanner(System.in);
		System.out.println("Enter something: ");
		s = in.nextLine();
		
		for (int i=0; i<s.length(); i++) {
			c.input((byte)s.charAt(i));
			System.out.print(Character.toUpperCase((char)c.output()));
		}
		System.out.println();
		in.close();
	}


}
