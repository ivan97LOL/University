/**
 * @author Iván Álvarez García 49623492A
 */

package es.ua.dlsi.prog3.p4.model;

public class Form2DFactory {

	public static Form2D createForm2D(String s) {
		
		Form2D form;
		if(s != "Circle" && s != "Rectangle") {
			throw new IllegalArgumentException();
		}else if(s == "Circle") {
			form = new Circle();
		}else {
			form = new Rectangle();
		}
		
		return form;
	}

}
