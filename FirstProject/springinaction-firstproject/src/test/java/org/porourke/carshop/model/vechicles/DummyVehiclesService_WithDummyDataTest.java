package org.porourke.carshop.model.vechicles;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class DummyVehiclesService_WithDummyDataTest {
	public static DummyVehiclesService_WithDummyData d;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		d = new DummyVehiclesService_WithDummyData();
	}

	@Test
	public void checkMakes() throws Exception {
		assertEquals(d.getMakeById(1).getName(),"Voltswagen");
		assertEquals(d.getMakes().size(),5);
		
	}
	
	@Test
	public void checkModles() throws Exception {
		assertEquals(d.getMakeById(1).getModels().size(),3);
		assertEquals(d.getMakeById(2).getModels().size(),3);
		assertEquals(d.getMakeById(1).getName(),"Voltswagen");
	}
	
	@Test
	public void checkVehicles() throws Exception {
		//System.out.println(d.getVehicleById(1).getReg());
		assertEquals(d.getVehicleById(1).getReg(),"ABC123");
		//System.out.println(d.getModelById(1).getVehicles().size());
		assertEquals(d.getModelById(1).getVehicles().size(),5);
	}
	
	
}
