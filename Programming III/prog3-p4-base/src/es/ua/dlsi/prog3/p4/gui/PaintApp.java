package es.ua.dlsi.prog3.p4.gui;

import java.util.ArrayList;
import java.util.List;


import es.ua.dlsi.prog3.p4.model.Canvas;
import es.ua.dlsi.prog3.p4.model.Circle;
import es.ua.dlsi.prog3.p4.model.Color;
import es.ua.dlsi.prog3.p4.model.Coordinate;
import es.ua.dlsi.prog3.p4.model.Form2D;
import es.ua.dlsi.prog3.p4.model.Form2DFactory;
import es.ua.dlsi.prog3.p4.model.Rectangle;

/** 
 * This class is a small program that actgs as client code for 
 * es.ua.dlsi.prog3.p4.mode.*
 * @author pierre
 * @date 24/10/2022
 **/
public class PaintApp {
	
	private Canvas lienzo;
	
	/**
	 * Executes some code using the Form2D hierarchy
	 */
	public void ejecutar() {
		lienzo = new Canvas("Prog 3", 1024.0,768.0);
		List<Circle> circles = new ArrayList<>();
		circles.add(new Circle());
		circles.add(new Circle(new Coordinate(10.0, 20.0), 5.0, Color.blue));
		circles.add(new Circle(new Coordinate(15.0, 25.0), 7.0, Color.lime));
		circles.add(new Circle((Circle)circles.get(2)));
		
		List<Rectangle> rectangles = new ArrayList<>();
		rectangles.add(new Rectangle());
		rectangles.add(new Rectangle(new Coordinate(10.0, 20.0), 5.0, 2.5, Color.blue));
		rectangles.add(new Rectangle(new Coordinate(15.0, 25.0), 7.0, 3.5, Color.lime));
		rectangles.add(new Rectangle((Rectangle)rectangles.get(2)));

		List<Form2D> forms = new ArrayList<>();
		forms.add(Form2DFactory.createForm2D("Circle"));
		forms.add(Form2DFactory.createForm2D("Rectangle"));

		for (Form2D c : circles) 
			lienzo.addForm(c);
		for (Form2D c : rectangles) 
			lienzo.addForm(c);
		for (Form2D c : forms) 
			lienzo.addForm(c);
		
		System.out.println(lienzo.toString());
		System.out.println(circles);
		System.out.println(rectangles);
	}	
	
	/**
	 * Main program
	 * @param args no args are needed
	 */
	public static void main(String args[])  {
		new PaintApp().ejecutar();
	}
}
