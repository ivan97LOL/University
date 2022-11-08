/**
 * @author Iván Álvarez García DNI: 49623492A
 */
package es.ua.dlsi.prog3.p2.model;
import java.util.ArrayList;

import es.ua.dlsi.prog3.p2.exceptions.PressureWheelException;
import es.ua.dlsi.prog3.p2.exceptions.TooManyWheelsException;
import es.ua.dlsi.prog3.p2.exceptions.WrongTyreTypeException;
/**
 * Class that creates a car with its attributes
 */
public class Car {
	/**
	 * instance attribute that stores the different wheels
	 */
	private ArrayList<Wheel> wheels;
	 /**
	  * Default constructor that creates wheels
	  */
	public Car() {
		wheels = new ArrayList<Wheel>();
	}
	/**
	 * Copy constructor that copies another Car object
	 * @param c : passed by reference
	 */
	public Car(Car c) {
		
		for(int i = 0; i< c.getWheels().size(); i++) {
			wheels.add(new Wheel(c.getWheels().get(i)));
		}
	}
	
	/**
	 * Method that add wheel objects to the ArrayList wheels
	 * makes a deep copy
	 * and manages the possibles exceptions
	 * @param w : passed by reference
	 * @throws TooManyWheelsException : exception for too many wheels
	 * @throws WrongTyreTypeException : exception for wrong tyre
	 */
	public void addWheel(Wheel w) throws TooManyWheelsException, WrongTyreTypeException{
		if(this.wheels.size() == 4) {
			throw new TooManyWheelsException();
		}else if(wheels.size()>0) {
			if(w.getTyreType() != this.wheels.get(0).getTyreType()) {
				throw new WrongTyreTypeException();
			}else {
				this.wheels.add(new Wheel(w));
			}
		}else {
			this.wheels.add(new Wheel(w));
		}
	}
	/**
	 * Getter that returns the wheels
	 * @return wheels : ArrayList of wheel object
	 */
	public ArrayList<Wheel> getWheels(){
		return wheels;
	}
	/**
	 * Method that changes the Tyres of the car
	 * and manages the possibles exceptions
	 * @param tyre : passed by references
	 * @param d : passed by reference
	 * @throws PressureWheelException : exception for incorrect wheel pressure
	 */
	public void changeTyres(TyreType tyre, double d) throws PressureWheelException {
		if(tyre == null) {
			throw new IllegalArgumentException("Tyre is null");
		}else {
			try {
				for(int i = 0; i< wheels.size(); i++) {
					wheels.get(i).setTyreType(tyre);
					wheels.get(i).inflate(d);
				}
			}catch(PressureWheelException e) {
				e.getMessage();
				throw e;
			}catch(Exception e) {
				throw new RuntimeException();
			}
		}
	}
}
