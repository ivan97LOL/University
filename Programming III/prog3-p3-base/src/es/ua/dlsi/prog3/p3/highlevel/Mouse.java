/**
 * @author Iván Álvarez García 49623492A
 */

package es.ua.dlsi.prog3.p3.highlevel;

import es.ua.dlsi.prog3.p3.lowlevel.InputDevice;

public class Mouse extends InputDevice{

	public Mouse() {
		super();
	}
	
	public void put(byte x, byte y) {
		this.sendToChannel(x);
		this.sendToChannel(y);
	}

}
