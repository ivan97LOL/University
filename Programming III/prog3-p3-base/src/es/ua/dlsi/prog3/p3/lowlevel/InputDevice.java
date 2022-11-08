/**
 * @author Iván Álvarez García 49623492A
 */
package es.ua.dlsi.prog3.p3.lowlevel;

public class InputDevice extends IODevice{

	protected InputDevice() {
		super();
	}
	
	protected void put(byte[] b) {
		for(int i = 0; i<b.length; i++) {
			this.getChannel().input(b[i]);
		}
	}
	
	protected void sendToChannel(byte b) {
		this.getChannel().input(b);
	}

}
