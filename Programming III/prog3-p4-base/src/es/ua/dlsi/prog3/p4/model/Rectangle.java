/**
 * @author Iván Álvarez García 49623492A
 */

package es.ua.dlsi.prog3.p4.model;

public class Rectangle extends Form2D{
	
	private double length;
	private double width;

	public Rectangle() {
		super();
		length = 0;
		width = 0;
	}
	
	public Rectangle(Rectangle r) {
		super(r.getPosition(),r.getFillColor());
		this.length = r.getLength();
		this.width = r.getWidth();
	}
	
	public Rectangle(Coordinate coord, double l, double w, Color color) {
		super(coord, color);
		
		if(l < 0 || w < 0) {
			throw new IllegalArgumentException();
		}
		
		this.length = l;
		this.width = w;
	}
	
	public double getLength() {
		double l = length;
		return l;
	}
	
	public double getWidth() {
		double w = width;
		return w;
	}
	

	@Override
	public void scale(double d) {
		if(d < 1) {
			throw new IllegalArgumentException();
		}
		
		this.length = length*d/100;
		this.width = width*d/100;
	}

	@Override
	public Rectangle clone() {
		Rectangle copy = new Rectangle(this);
		return copy;
	}

	@Override
	public String toString() {
		return "(" + getPosition() + ")," + "fill=" + getFillColor() + ",length=" + getLength() +
		",width=" + getWidth();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rectangle other = (Rectangle) obj;
		return Double.doubleToLongBits(length) == Double.doubleToLongBits(other.length)
				&& Double.doubleToLongBits(width) == Double.doubleToLongBits(other.width);
	}
	
	

}
