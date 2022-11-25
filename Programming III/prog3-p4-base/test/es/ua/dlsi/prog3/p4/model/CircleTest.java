package es.ua.dlsi.prog3.p4.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Before;
import org.junit.Test;


public class CircleTest {	
	Coordinate rc;
	Form2D c; 


	@Before
	public void setUp() throws Exception {
		rc = new Coordinate(100, 500);
		c = new Circle(rc, 231, Color.fuchsia);
	}
	
	@Test
	public final void testCtor1() {
		Circle c = new Circle();
		assertEquals("Default ctor, radius==0",0.0,c.getRadius(),0.001);
	}

	@Test
	public final void testCtor2() {
		Circle c2 = new Circle((Circle)c);
		assertEquals("Copy ctor",c.getFillColor(),c2.getFillColor());
		assertEquals("Copy ctor",c.getPosition(),c2.getPosition());
	}


	@Test
	public final void testColores() throws Exception {
		assertEquals("CL", Color.fuchsia, c.getFillColor());
		c.setFillColor(Color.none);
		assertEquals("CL", Color.none, c.getFillColor());
	}

	@Test
	public final void testMover() throws Exception {
		Form2D r = new Circle(rc, 23, Color.aqua); 
		Coordinate old = r.move(new Coordinate());
		assertEquals(rc, old);
	}
	
	
	@Test
	public final void testEscalar200() throws Exception   {
		Coordinate coord = new Coordinate();
		Circle c1 = new Circle(coord, 10, Color.navy);
		c1.scale(200);
		assertEquals("Escalar 200", 20, c1.getRadius(), 0.001); 
	}	
	
	@Test
	public final void testCloneIsDefined() {
		Coordinate coord = new Coordinate();
		Circle c1 = new Circle(coord, 10, Color.navy);
		c1.clone();
		c1.clone(new Coordinate(1.1,1.1));
	}
	
	@Test
	public final void testToString()  {
		String s = "(100.0,500.0),fill=fuchsia,radius=231.0";
		assertEquals("Circle toString()",s,c.toString());		
	}

	@Test
	public final void testEquals()  {
		Form2D c2 = new Circle(rc, 231, Color.fuchsia);
		assertEquals("Circle.equals()",c,c2);
	}

}
