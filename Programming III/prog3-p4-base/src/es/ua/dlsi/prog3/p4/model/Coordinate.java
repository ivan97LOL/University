package es.ua.dlsi.prog3.p4.model;


/**
 * This class represents a 2D coordinate in a canvas
 */
public class Coordinate {
	/**
	 * Default value for coordinate components
	 */
	public static final double NOT_VALID = Double.NaN;
	
	/**
	 * Minimum (negative) value for a coordinate component
	 */
	public static final double MIN_VALUE = -32767.9999;

	/**
	 * Maximum (positive) value for a coordinate component
	 */
	public static final double MAX_VALUE = 32767.9999;

	/**
	 * Checks that a component value is in range
	 * @param v the component to check
	 * @return true if 'v' is in range
	 */
	private boolean checkInRange(double v) {
		return (v >= Coordinate.MIN_VALUE && v <= Coordinate.MAX_VALUE);
	}
	
	/**
	 * Component x
	 */
	private double x;
	
	/**
	 * Component y
	 */
	private double y;
	
	/**
	 * Default constructor
	 */
	public Coordinate() {
		this.x = NOT_VALID;
		this.y = NOT_VALID;
	}
	
	/**
	 * Overloaded constructor from provided component values
	 * @param x first component
	 * @param y second component
	 * @throws IllegalArgumentException if any component is out of range
	 */
	public Coordinate(double x, double y)  {
		if (!checkInRange(x) || !checkInRange(y)) {
			throw new IllegalArgumentException("Arguments out of bounds: "+x+", "+y);		
		} else {
			this.x = x;
			this.y = y;
		}	
	}
	
	/**
	 * Copy constructor
	 * @param other the coordinate to copy from
	 */
	public Coordinate(Coordinate other) {
		this.x = other.x;
		this.y = other.y;
	}
	
	/**
	 * Gets the 'x' component of this coordinate
	 * @return the 'x' component
	 */
	public final double getX() {
		return x;
	}

	/**
	 * Gets the 'y' component of this coordinate
	 * @return the 'y' component
	 */
	public final double getY() {
		return y;
	}

	/**
	 * Obtains a coordinate whose components are the sum of the respective
	 * components from this coordinate and coordinate 'c'.
	 * @param c the second operand for the sum
	 * @return a coordinate which is the sum of this coordinate and coordinate 'c'
	 * @throws ArithmeticException if as a result of the sum, any component is out of range  
	 */
	public Coordinate sum(Coordinate c)   {
			if (!checkInRange(this.x + c.x) || !checkInRange(this.y + c.y))
				throw new ArithmeticException("sum of coordinates results in out of range values: ("+(this.x + c.x)+","+ (this.y + c.y)+")");		
			return new Coordinate(this.x + c.x, this.y + c.y);
	}

	/**
	 * Obtains a coordinate whose components are the subtraction of the 'c'
	 * components from this coordinate's components.
	 * @param c the second operand for the subtraction
	 * @return a coordinate which is the subtraction of this coordinate and coordinate 'c'
	 * @throws ArithmeticException if as a result of the subtraction, any component is out of range  
	 */
	public Coordinate subtract(Coordinate c)  {
		if (!checkInRange(this.x - c.x) || !checkInRange(this.y - c.y))
			throw new ArithmeticException("subtracting coordinates results in out of range values: ("+(this.x - c.x)+","+ (this.y - c.y)+")");		
		return new Coordinate(this.x - c.x, this.y - c.y);
	}
	
	/**
	 * Outputs a string representation of this coordinate
	 * @return a string representation of this coordinate
	 */
	public String toString() {
		return x + "," + y;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Coordinate))
			return false;
		Coordinate other = (Coordinate) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
		
}
