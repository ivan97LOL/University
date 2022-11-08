/**
 * @author Iván Álvarez García 49623492A
 */

package es.ua.dlsi.prog3.p3.highlevel;

import es.ua.dlsi.prog3.p3.lowlevel.InputDevice;

public class Keyboard extends InputDevice{

	public Keyboard() {
		super();
	}
	
	public void put(char c) {
		this.sendToChannel((byte)c);
	}

}
