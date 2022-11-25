/**
 * @author Iván Álvarez García 49623492A
 */

package es.ua.dlsi.prog3.p4.model;

public class Circle extends Form2D{
	
	private double radius;

	public Circle() {
		super();
		radius = 0;
	}
	
	public Circle(Coordinate coord, double r, Color color) {
		super(coord, color);
		
		if(r < 0) {
			throw new IllegalArgumentException();
		}
		this.radius = r;
		
	}
	
	public Circle(Circle c) {
		super(c.getPosition(), c.getFillColor());
		this.radius = c.radius;
	}
	
	public double getRadius() {
		
		double r = radius;
		return r;
	}

	@Override
	public void scale(double d) {
		if(d <1) {
			throw new IllegalArgumentException();
		}
		this.radius = this.radius*d/100;
	}

	@Override
	public Circle clone() {
		
		Circle copy = new Circle(this);
		return copy;
	}

	@Override
	public String toString() {
		return super.toString() + ",radius=" + getRadius();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Circle other = (Circle) obj;
		return Double.doubleToLongBits(radius) == Double.doubleToLongBits(other.radius);
	}

}
