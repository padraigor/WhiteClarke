package org.porourke.carshop.model.vechicles;
//This Code is redundant -> Delete
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.porourke.carshop.model.VechicleInterface;

public class TestVechicle {
	private static VechicleInterface vehicle1;
	private static VechicleInterface vehicle2;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		vehicle1 = new Vehicle();		vehicle2 = new Vehicle();			
		vehicle1.setReg("XYZ123");		vehicle2.setReg(		vehicle1.getReg());
		//vehicle1.setMake("Voltswagan");	vehicle2.setMake(		vehicle1.getMake());
		//vehicle1.setModel("Golf");		vehicle2.setModel(		vehicle1.getModel());
		vehicle1.setColour("Black");	vehicle2.setColour(		vehicle1.getColour());
		vehicle1.setYear(2013);			vehicle2.setYear(		vehicle1.getYear());
		vehicle1.setEngSizeCC(1605);	vehicle2.setEngSizeCC(	vehicle1.getEngSizeCC());
	}
	
	@Test
	public void testEqualsAndHash() {// also tests all getters and setters 
		assertEquals(vehicle1,vehicle2);
		assertEquals(vehicle1.hashCode(),vehicle2.hashCode());
		
		vehicle2.setReg("ABC123");
		assertNotEquals(vehicle1,vehicle2);
		vehicle2.setReg(vehicle1.getReg());
	}
	
	@Test
	public void testToString() {// also tests all getters and setters 
		System.out.println(vehicle1.toString());
		assertEquals(vehicle1.toString(),"Vehicle [reg=XYZ123, make=Voltswagan, model=Golf, colour=Black, year=2013, engSizeCC=1605]");
	}
	

}
