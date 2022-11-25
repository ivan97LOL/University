package es.ua.dlsi.prog3.p4.model;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;


public class CoordinateTest {
	Coordinate c;
	
	@Before
	public void setUp() throws Exception {
		c = new Coordinate(10, 5);		
	}

	@Test
	public final void testGetX() {
		assertEquals("x", 10, c.getX(), 0.001);
	}

	@Test
	public final void testGetY() {
		assertEquals("y", 5, c.getY(), 0.001);
	}
	
	@Test
	public final void testEquals() throws Exception {
		Coordinate c1 = new Coordinate(50, 60);
		Coordinate c2 = new Coordinate(50, 60);
		assertEquals(c1, c2);
		Coordinate c3 = new Coordinate(60, 50);
		
		assertFalse(c1.equals(c3));
		assertFalse(c2.equals(c3));
	}
	
	@Test
	public final void testCCopia() throws  Exception {
		Coordinate c1 = new Coordinate(-50, 60);
		Coordinate c2 = new Coordinate(c1);
		assertEquals(c1, c2);
	}	
	
	@Test(expected=IllegalArgumentException.class)
	public final void testCoordinatesErronea1() throws Exception {
		new Coordinate(-50000, 10);
	}

	@Test(expected=IllegalArgumentException.class)
	public final void testCoordinatesErronea2() throws Exception {
		new Coordinate(50000, 10);
	}
	@Test(expected=IllegalArgumentException.class)
	public final void testCoordinatesErronea3() throws Exception {
		new Coordinate(10, -50000);
	}	
	@Test(expected=IllegalArgumentException.class)
	public final void testCoordinatesErronea4() throws Exception {
		new Coordinate(10, 50000);
	}
	
	@Test
	public final void testSumaOK() throws Exception {
		// dentro de rango
		Coordinate c1 = new Coordinate(10, 20);
		Coordinate c2 = new Coordinate(100, 200);
		Coordinate esperado = new Coordinate(110, 220);
		assertEquals(esperado, c1.sum(c2));
	}
	
	@Test(expected=ArithmeticException.class)
	public final void testSumaKO() throws Exception {
		// fuera de rango
		Coordinate c3 = new Coordinate(30000, 30000);
		c3.sum(c3);
	}	
	@Test
	public final void testRestaOK() throws Exception {
		// dentro de rango
		Coordinate c1 = new Coordinate(10, 20);
		Coordinate c2 = new Coordinate(100, 200);
		Coordinate esperado = new Coordinate(-90, -180);
		assertEquals(esperado, c1.subtract(c2));
	}
	@Test(expected=ArithmeticException.class)
	public final void testRestaKO() throws Exception {
		// fuera de rango
		Coordinate c3 = new Coordinate(-30000, -30000);
		Coordinate c4 = new Coordinate(30000, 30000);
		c3.subtract(c4);
	}	

	@Test(expected=ArithmeticException.class)
	public final void testSumaKO2() throws Exception {
		// fuera de rango
		Coordinate c2 = new Coordinate();
		Coordinate c3 = new Coordinate(300, 30);
		c2.sum(c3);
	}	

	@Test(expected=ArithmeticException.class)
	public final void testRestaKO2() throws Exception {
		// fuera de rango
		Coordinate c2 = new Coordinate();
		Coordinate c3 = new Coordinate(300, 30);
		c2.subtract(c3);
	}	

}
