package es.ua.dlsi.prog3.p4.model;



import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;


/**
 * @author pierre
 *
 */
public class Form2DTest {
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
			return null;
		}
	}

	Form2D default_form,red_form, copy_form;
	Coordinate position;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		position = new Coordinate(1,1);
		default_form = new NewForm2D();
		red_form = new NewForm2D(position,Color.red);
		copy_form = new NewForm2D((NewForm2D)red_form);
	}

	/**
	 * Test method for {@link es.ua.dlsi.prog3.p4.model.Form2D#Form2D()}.
	 */
	@Test
	public final void testForm2D() {
		Coordinate pos = default_form.getPosition();
		Color fill = default_form.getFillColor();
		assertEquals("Form2D position",new Coordinate(),pos);
		assertEquals("Form2D color",Color.none,fill);
	}

	/**
	 * Test method for {@link es.ua.dlsi.prog3.p4.model.Form2D#Form2D(es.ua.dlsi.prog3.p4.model.Coordinate, es.ua.dlsi.prog3.p4.model.Color)}.
	 */
	@Test
	public final void testForm2DCoordinateColor() {
		Coordinate pos = red_form.getPosition();
		Color fill = red_form.getFillColor();
		assertEquals("Form2D position",position,pos);
		assertEquals("Form2D color",Color.red,fill);
	}




	
	/**
	 * Test method for {@link es.ua.dlsi.prog3.p4.model.Form2D#move(es.ua.dlsi.prog3.p4.model.Coordinate)}.
	 */
	@Test
	public final void testMove() {
		Coordinate newpos=new Coordinate(3.2,-2.3);
		red_form.move(newpos);
		Coordinate pos = red_form.getPosition();
		assertEquals("Form2D move",newpos, pos);
	}


	/**
	 * Test method for {@link es.ua.dlsi.prog3.p4.model.Form2D#setFillColor(es.ua.dlsi.prog3.p4.model.Color)}.
	 */
	@Test
	public final void testSetFillColor() {
		red_form.setFillColor(Color.black);
		assertEquals("Form2D setFillColor",Color.black,red_form.getFillColor());
	}

	/**
	 * Test method for {@link es.ua.dlsi.prog3.p4.model.Form2D#toString()}.
	 */
	@Test
	public final void testToString() {
		String result = "(1.0,1.0),fill=red";
		assertEquals("Form2D toString",result,red_form.toString());
	}

	/**
	 * Test method for {@link es.ua.dlsi.prog3.p4.model.Form2D#clone()}.
	 * Just checks it is defined
	 */
	@Test
	public final void testClone() {
		Form2D form = red_form.clone();
		assertNull("NewForm2D clone is null",form);
	}
	
	/**
	 * Test method for {@link es.ua.dlsi.prog3.p4.model.Form2D#clone(Coordinate)}.
	 * Just checks it is defined
	 */
	@Test(expected=NullPointerException.class)
	public final void testClone2() {
		Form2D form = red_form.clone(new Coordinate(2.0,3.4));
	}

	/**
	 * Test method for {@link es.ua.dlsi.prog3.p4.model.Form2D#clone(Coordinate)}.
	 * Just checks it is defined
	 */
	@Test
	public final void testScale() {
		red_form.scale(50.0);
		// NewForm2D.scale() does nothing, so no assertions here
	}

}
