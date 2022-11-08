package es.ua.dlsi.prog3.p3.lowlevel;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ChannelTest {

	private Channel c1;
	
	private final int DEFAULT_BUFFER_SIZE=10;
	
	@Before
	public void setUp() throws Exception {
		c1 = new Channel(new IODevice(), new IODevice(DEFAULT_BUFFER_SIZE));
	}

	@Test
	public void testBufferSize() {
		assertEquals("Buffer size=="+DEFAULT_BUFFER_SIZE,DEFAULT_BUFFER_SIZE,c1.getBufferSize());
	}
	
	@Test
	public void testResetBuffer() {
		c1.input((byte) 23);
		c1.resetBuffer(DEFAULT_BUFFER_SIZE*2);
		assertEquals("Reset buffer size to "+DEFAULT_BUFFER_SIZE*2,DEFAULT_BUFFER_SIZE*2,c1.getBufferSize());
		assertFalse(c1.isFull());
		assertFalse(c1.hasData());		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testResetBufferException() {
		c1.resetBuffer(0);
	}
	
	@Test
	public final void testChannel() {
		assertFalse(c1.isFull());
		assertFalse(c1.hasData());
	}

	@Test
	public final void testInput() {
		c1.input((byte) 23);
		assertTrue(c1.hasData());
		assertFalse(c1.isFull());
	}

	@Test
	public final void testOutput() {
		c1.input((byte) 23);
		assertEquals("Retrieve input data 23",23,c1.output());
		assertFalse(c1.hasData());
		assertFalse(c1.isFull());
	}
	
	@Test
	public final void testIsEmpty() {
		assertFalse(c1.hasData());
	}

	@Test
	public final void testIsEmpty2() {
		for (int i=0; i < c1.getBufferSize(); i++) {
			c1.input((byte)23);
		}
		for (int i=0; i < c1.getBufferSize(); i++) {
			c1.output();
		}
		assertFalse(c1.hasData());
		assertFalse(c1.isFull());
	}

	@Test
	public final void testIsFull() {
		for (int i=0; i < c1.getBufferSize(); i++) {
			c1.input((byte)23);
		}
		assertTrue(c1.isFull());
		assertTrue(c1.hasData());		
	}

	@Test
	public final void testHasDataNotFull() {
		for (int i=0; i < c1.getBufferSize()-1; i++) {
			c1.input((byte)23);
		}
		assertTrue(c1.hasData());		
		assertFalse(c1.isFull());
	}
	
	@Test(expected = java.nio.BufferOverflowException.class)
	public final void testBufferOverflow() {
		for (int i=0; i < c1.getBufferSize()+1; i++) {
			c1.input((byte)23);
		}
	}

	@Test(expected = java.nio.BufferUnderflowException.class)
	public final void testBufferUnderflow() {
		c1.output();
	}

	@Test(expected = java.nio.BufferUnderflowException.class)
	public final void testBufferUnderflow2() {
		for (int i=0; i < c1.getBufferSize()-1; i++) {
			c1.input((byte)23);
		}
		for (int i=0; i < c1.getBufferSize(); i++) {
			c1.output();
		}
	}

	@Test
	public final void testCircularWriting() {
		for (int i=0; i < c1.getBufferSize(); i++) {
			c1.input((byte)23);
		}
		c1.output();
		c1.input((byte)23);
		assertTrue(c1.hasData());		
		assertTrue(c1.isFull());
	}

	@Test
	public final void testCircularWriting2() {
		for (int i=0; i < c1.getBufferSize(); i++) {
			c1.input((byte)23);
		}
		for (int i=0; i < c1.getBufferSize()/2; i++) {
			c1.output();
		}
		for (int i=0; i < c1.getBufferSize()/2; i++) {
			c1.input((byte)23);
		}
		assertTrue(c1.hasData());		
		assertTrue(c1.isFull());
	}

	@Test
	public final void testCircularWritingReading() {
		for (int i=0; i < c1.getBufferSize()*2; i++) {
			c1.input((byte)23);
			c1.output();
		}
		assertFalse(c1.hasData());		
	}

}
