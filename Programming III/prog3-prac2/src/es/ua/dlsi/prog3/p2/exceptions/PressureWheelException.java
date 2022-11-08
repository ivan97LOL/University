/**
 * @author Iván Álvarez García DNI: 49623492A
 * Class that extends from the Exception class
 */

package es.ua.dlsi.prog3.p2.exceptions;

public class PressureWheelException extends Exception{
		/**
		 * Instance attribute that stores the pressure
		 */
		private double pressure;

	/**
	 * Constructor with the pressure given
	 * 
	 * @param pressure : passed by reference;
	 */
	public PressureWheelException(double pressure) {
		this.pressure = pressure;
	}
	/**
	 * Method to print a message
	 * @return message : contains the message to print
	 */
	public String getMessage() {
		String message = "Pressure of "+ pressure +" BAR";
		return message;
	}
}
