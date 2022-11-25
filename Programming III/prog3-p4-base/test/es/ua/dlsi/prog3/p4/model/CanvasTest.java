package es.ua.dlsi.prog3.p4.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CanvasTest {

	class NewForm2D extends Form2D {
		NewForm2D() {
		}

		NewForm2D(Coordinate pos, Color color) {
			super(pos, color);
		}
		
		NewForm2D(NewForm2D other) { 
			super(other);
		}
		
		@Override
		public void scale(double porcentaje)  {
		}
		
		@Override
		public NewForm2D clone() {
			return new NewForm2D();
		}
	}

	Canvas lienzo;
	
	@Before
	public void setUp() throws Exception {
		lienzo = new Canvas();
		lienzo.addForm(new NewForm2D());
		lienzo.addForm(new NewForm2D());
	}

	@Test
	public final void testLienzoStringFloatFloat() throws Exception {
		Canvas l = new Canvas();
		
		assertEquals("Alto por defecto", Canvas.DEFAULT_SIZE, l.getHeight(), 0.001);
		assertEquals("Ancho por defecto", Canvas.DEFAULT_SIZE, l.getWidth(), 0.001);
		
		l = new Canvas("Prueba", 10f, 20f);
		assertEquals("Alto 20", 20, l.getHeight(), 0.001);
		assertEquals("Ancho 10", 10, l.getWidth(), 0.001);
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public final void testLienzoGetMalCero() {
		lienzo.getForm(0);
	}

	@Test
	public final void testLienzoGetFormOK() throws Exception {
		Form2D c = new NewForm2D();
		assertEquals(c, lienzo.getForm(2));
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public final void testLienzoRemoveMalCero() {
		lienzo.removeForm(0);
	}

	@Test
	public  final void testCloneIsDefined() {
		lienzo.clone();
	}

	@Test
	public final void testGetNumFormsIsDefined() {
		lienzo.getNumForms();
	}

	@Test
	public  final void testToString() {
		String s = "default canvas (1000.0,1000.0) with 2 forms";
		assertEquals("Canvas.toString()",s,lienzo.toString());
	}

}
