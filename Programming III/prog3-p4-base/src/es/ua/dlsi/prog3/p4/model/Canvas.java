/**
 * @author Iván Álvarez García 49623492A
 */

package es.ua.dlsi.prog3.p4.model;

import java.util.ArrayList;

public class Canvas {

	private ArrayList<Form2D> forms;
	private String title;
	private double width;
	private double height;
	
	public static final float DEFAULT_SIZE = 1000;
	
	public Canvas() {
		title = new String();
		title = "default canvas";
		
		width = DEFAULT_SIZE;
		height = DEFAULT_SIZE;
		forms = new ArrayList<Form2D>();
	}
	
	public Canvas(Canvas c) {
		this.title = new String(c.title);
		this.width = c.width;
		this.height = c.height;
		this.forms = new ArrayList<Form2D>();

		for(int i = 0; i < forms.size(); i++) {
			this.forms.add(c.forms.get(i));
		}
	}
	
	public Canvas(String s, double w, double h) {
		if(w < 0 || h < 0) {
			throw new IllegalArgumentException();
		}
		this.title = new String(s);
		this.height = h;
		this.width = w;
		forms = new ArrayList<Form2D>();

	}
	
	public void addForm(Form2D form) {
		Form2D formCopy = form.clone();
		forms.add(formCopy);
	}
	
	public Canvas clone() {
		
		Canvas copy = new Canvas(this);
		return copy;
	}
	
	public Form2D getForm(int index) {
		if(index < 1 || index > this.getNumForms()) {
			throw new IndexOutOfBoundsException();
		}
		
		Form2D formCopy = this.forms.get(index-1).clone();
		return formCopy;
	}
	
	public double getWidth() {
		double w = this.width;
		return w;
	}
	
	public double getHeight() {
		double h = this.height;
		return h;
	}
	
	public int getNumForms() {
		int length = this.forms.size();
		return length;
	}
	
	public void removeForm(int index) {
		if(index < 1 || index > this.getNumForms()) {
			throw new IndexOutOfBoundsException();
		}
		
		this.forms.remove(index-1);
	}

	@Override
	public String toString() {
		return title + " (" + getWidth() + "," + getHeight() + ") with " + getNumForms() + " forms";
	}
	
}
