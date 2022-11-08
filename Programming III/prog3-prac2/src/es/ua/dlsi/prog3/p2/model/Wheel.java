/**
 * @author Iván Álvarez García DNI: 49623492A
 */

package es.ua.dlsi.prog3.p2.model;

import es.ua.dlsi.prog3.p2.exceptions.PressureWheelException;
import es.ua.dlsi.prog3.p2.exceptions.NoTyreTypeException;
/**
 * Class that creates a wheel with its attributes
 *
 */
public class Wheel {
	/**
	 * instance attribute that stores the tyre type
	 */
	private TyreType TyreType;
	/**
	 * instance attribute that stores the pressure
	 */
	private double pressure;
/**
 * default constructor that sets the pressure to 0
 */
	public Wheel() {
		pressure = 0;
	}
	/**
	 * Constructor that receives the tyre type and adds it
	 * @param TyreType : passed by reference
	 */
	public Wheel(TyreType TyreType) {
		pressure = 0;
		this.TyreType = TyreType;
	}
	/**
	 * Copy constructor that copies the object given
	 * @param wheel : passed by reference
	 */
	public Wheel(Wheel wheel) {
		this.pressure = wheel.pressure;
		this.TyreType = wheel.getTyreType();
	}
	/**
	 * Setter that receive the type to set
	 * @param TyreTypeToCopy : passed by reference
	 */
	public void setTyreType(TyreType TyreTypeToCopy){
		this.TyreType = new TyreType(TyreTypeToCopy);
	}
	/**
	 * Getter that returns the tyre type
	 * @return TyreType : stores the tyre type
	 */
	public TyreType getTyreType() {
		return TyreType;
	}
	/**
	 * Method that gives the pressure to the wheels
	 * and manages the possibles exceptions
	 * @param num : passed by reference
	 * @throws NoTyreTypeException : exception for no tyre
	 * @throws PressureWheelException : exception for incorrect wheel pressure
	 */
	public void inflate(double num) throws NoTyreTypeException, PressureWheelException{
		if(num < 0) {
			throw new IllegalArgumentException("Negative pressure");
		}else if(TyreType == null){
			throw new NoTyreTypeException();
		}else if(num > TyreType.getMaxPressure() || num < TyreType.getMinPressure()) {
			throw new PressureWheelException(num);
		}else {
			pressure = num;
		}
	}
}
