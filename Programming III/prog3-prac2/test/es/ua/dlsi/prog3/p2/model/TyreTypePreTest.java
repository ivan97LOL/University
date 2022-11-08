package es.ua.dlsi.prog3.p2.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class TyreTypePreTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor1() {
		new TyreType(null,-1,0);
	}		
		
	@Test
	public void testConstructor4() {
		new TyreType("185/65 R15",1,4);
	}
	
	@Test
	public void testToString() {
		TyreType t = new TyreType("185/65 R15",1,4);
		String s = "TyreType 185/65 R15 [1.0,4.0]";
		
		assertEquals(s,t.toString());	
	}
	
	@Test
	public void testEquals() {
		TyreType t1 = new TyreType("185/65 R15",1,4);
		TyreType t2 = new TyreType("185/65 R15",1,4);
		
		assertEquals(t1,t2);
	}
	
	@Test
	public void testGetMinMaxPressure() {
		TyreType t = new TyreType("185/65 R15",1,4);
		
		assertEquals(t.getMinPressure(),1,0);
		assertEquals(t.getMaxPressure(),4,0);
	}
}
