/**
 * @author Iván Álvarez García 49623492A
 */

package es.ua.dlsi.prog3.p3.highlevel;

import java.nio.BufferUnderflowException;
import java.util.Arrays;

import es.ua.dlsi.prog3.p3.lowlevel.OutputDevice;

public class Display extends OutputDevice{
	
	private int pixel_rows;
	private byte[][] display;

	public Display(int N) {
		super(N*N*2);
		pixel_rows = N;
		display = new byte[pixel_rows][pixel_rows];
	}
	
	public int getDisplaySize() {
		return pixel_rows;
	}
	
	public byte[][] refresh() {
		
		while(this.getChannel().hasData()) {
			byte x = this.receiveFromChannel();
			
			if(this.getChannel().hasData()) {
				byte y = this.receiveFromChannel();
				
				if(x >= 0 && x < pixel_rows && y < pixel_rows) {
					display[x][y] = 1;
				}else {
					throw new IndexOutOfBoundsException();
				}
			}else {
				throw new BufferUnderflowException();
			}
			
		}
		
		return Arrays.copyOf(display, pixel_rows);
	}
	
	public void clear() {
		for(int i = 0; i < pixel_rows; i++) {
			for(int j = 0; j < pixel_rows; j++) {
				display[i][j] = 0;
			}
		}
	}

}
