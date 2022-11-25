/**
 * @author Iván Álvarez García 49623492A
 */

package es.ua.dlsi.prog3.p4.model;

import java.util.Objects;

public abstract class Form2D {
	
	private Coordinate position;
	private Color fillColor;

	protected Form2D() {
		position = new Coordinate();
		fillColor = Color.none;
	}
	
	protected Form2D(Coordinate coord, Color color) {
		position = new Coordinate(coord);
		fillColor = color;
	}
	
	protected Form2D(Form2D form) {
		
		position = form.getPosition();
		fillColor = form.getFillColor();
	}
	
	public Coordinate getPosition() {
		
		Coordinate p = new Coordinate(position);
		return p;
	}
	
	public Color getFillColor() {
	
		Color c = fillColor;
		return c;
	}
	
	public void setFillColor(Color color) {
		
		fillColor = color;
	}
	
	public Coordinate move(Coordinate coord) {
		if(coord != null) {
			Coordinate last_coord = new Coordinate(position);
			position = new Coordinate(coord);
			
			return last_coord;
			
		}
		return position;
	}
	
	public Form2D clone(Coordinate coord) {
		Form2D copy = this.clone();
		copy.move(new Coordinate(coord));
		
		return copy;
	}

	public String toString() {
		return "(" + getPosition() + "),fill=" + getFillColor();
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Form2D other = (Form2D) obj;
		return Objects.equals(position, other.position);
	}
	
	public abstract void scale(double d);
	public abstract Form2D clone();
}
