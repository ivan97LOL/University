/**
 * @author Iván Álvarez García 49623492A
 */

package es.ua.dlsi.prog3.p3.highlevel;

import es.ua.dlsi.prog3.p3.highlevel.exceptions.NoLineForPrintingException;
import es.ua.dlsi.prog3.p3.lowlevel.OutputDevice;

public class LinePrinter extends OutputDevice{

	private static int MAX_LINE_LENGTH = 80;
	
	public LinePrinter() {
		super(MAX_LINE_LENGTH+1);
	}
	
	public String printLine() throws NoLineForPrintingException {
		if(!this.getChannel().hasData()) {
			throw new NoLineForPrintingException();
		}
		String s = new String(this.readStoredString());
		return s;
	}

}
