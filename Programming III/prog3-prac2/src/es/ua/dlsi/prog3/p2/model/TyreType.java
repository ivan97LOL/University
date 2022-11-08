/**
 * @author Iván Álvarez García DNI: 49623492A
 */

package es.ua.dlsi.prog3.p2.model;

import java.util.Objects;
/**
 * 
 * immutable class that defines the tyre attributes
 *
 */
public final class TyreType {
	
	/**
	 * instance attribute that stores the description
	 */

	private final String description;
	/**
	 * instance attribute that stores the minimum pressure
	 */
	private final double min_pressure;
	/**
	 * instance attribute that stores the maximum 
	 */
	private final double max_pressure;
	
	/**
	 * default constructor that creates a tyre form the params given
	 * and manages the possibles exceptions
	 * @param desc : passed by reference
	 * @param min_p : passed by reference
	 * @param max_p : passed by reference
	 */
	public TyreType(String desc, double min_p, double max_p) {
		
		if(desc == null || min_p< 0 || max_p < 0 || 
				min_p > max_p) {
				throw new IllegalArgumentException();
		}
		description = new String(desc);
		min_pressure = min_p;
		max_pressure = max_p;
		
	}
	
	/**
	 * Copy constructor that copies the tyre given
	 * and manages the possibles exceptions
	 * @param type : passed by reference
	 */
	public TyreType(TyreType type) {
		if(type == null) {
			throw new IllegalArgumentException("type is null");
		}else {
			this.description = new String(type.description);
			this.min_pressure = type.getMinPressure();
			this.max_pressure = type.getMaxPressure();
		}
	}
	/**
	 * Getter that returns the min pressure
	 * makes a superficial copy
	 * @return min : minimum pressure
	 */
	public double getMinPressure() {
		
		double min = min_pressure;
		return min;
	}
	/**
	 * Getter that returns the max pressure
	 * makes a superficial copy
	 * @return max : maximum pressure
	 */
	public double getMaxPressure() {
		double max = max_pressure;
		return max;
	}
	/**
	 * Method that returns the String to print
	 * @return string created
	 */
	public String toString() {
		return "TyreType " + description + " [" + min_pressure + ","
				+ max_pressure + "]";
	}
	/**
	 * Method that verifies if two objects are equals
	 * @return bool value
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TyreType other = (TyreType) obj;
		return Objects.equals(description, other.description)
				&& Double.doubleToLongBits(max_pressure) == Double.doubleToLongBits(other.max_pressure)
				&& Double.doubleToLongBits(min_pressure) == Double.doubleToLongBits(other.min_pressure);
	}
	
	
}