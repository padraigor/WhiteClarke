package org.porourke.carshop.model.hibernate;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashSet;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class HibernateVehicleTest {

	@Test
	public void testNoParameterConstructor() {
		Vehicle vehicle = new Vehicle();

		assertEquals(-1, vehicle.getId());
		assertEquals("", vehicle.getReg());
		assertEquals(null, vehicle.getModel());
		assertEquals("", vehicle.getColour());
		assertEquals(-1, vehicle.getYear());
		assertEquals(-1, vehicle.getEngSizeCC());
	}

	@Test
	public void testAllParamertersButIdConstructor() {
		Vehicle vehicle = new Vehicle("XYZ123", null, "Red", 1984, 1600);

		assertEquals(-1, vehicle.getId());
		assertEquals("XYZ123", vehicle.getReg());
		assertEquals(null, vehicle.getModel());
		assertEquals("Red", vehicle.getColour());
		assertEquals(1984, vehicle.getYear());
		assertEquals(1600, vehicle.getEngSizeCC());
	}
	
	@Test
	public void testToJSON(){
		Make make = new Make(); 	make.setName("BAE");	
		Model model = new Model();	model.setName("Tank2"); 	make.addModel(model);
		Vehicle vehicle = new Vehicle("XYZ123", model, "Red", 1984, 1600);
	
		//System.out.println("{\"id\":\"-1\",\"reg\":\"XYZ123\",\"model\":\"Tank2\",\"make\":\"BAE\",\"colour\":\"Red\",\"year\":\"1984\",\"engSizeCC\":\"1600\"}");
		//System.out.println(vehicle.toJSON());
		assertEquals(vehicle.toJSON(),"{\"id\":\"-1\",\"reg\":\"XYZ123\",\"model\":\"Tank2\",\"make\":\"BAE\",\"colour\":\"Red\",\"year\":\"1984\",\"engSizeCC\":\"1600\"}");
		
	}
	
	@Test
	public void testToJSONWhenMakeNull(){
		Make make = null;
		Model model = new Model();	model.setName("Tank2"); 
		Vehicle vehicle = new Vehicle("XYZ123", model, "Red", 1984, 1600);	

		//System.out.println("{\"id\":\"-1\",\"reg\":\"XYZ123\",\"model\":\"Tank2\",\"colour\":\"Red\",\"year\":\"1984\",\"engSizeCC\":\"1600\"}");
		//System.out.println(vehicle.toJSON());
		assertEquals(vehicle.toJSON(),"{\"id\":\"-1\",\"reg\":\"XYZ123\",\"model\":\"Tank2\",\"colour\":\"Red\",\"year\":\"1984\",\"engSizeCC\":\"1600\"}");

	}
	
	@Test
	public void testToJSONWhenModelNull(){
		Vehicle vehicle = new Vehicle("XYZ123", null, "Red", 1984, 1600);	

		System.out.println("{\"id\":\"-1\",\"reg\":\"XYZ123\",\"colour\":\"Red\",\"year\":\"1984\",\"engSizeCC\":\"1600\"}");
		System.out.println(vehicle.toJSON());
		assertEquals(vehicle.toJSON(),"{\"id\":\"-1\",\"reg\":\"XYZ123\",\"colour\":\"Red\",\"year\":\"1984\",\"engSizeCC\":\"1600\"}");
	}
	
	@Test
	public void testToJSON_From_Collection(){
		Make make = new Make(); 	make.setName("BAE");	
		Model model = new Model();	model.setName("Tank2"); 	make.addModel(model);
		Vehicle vehicle = new Vehicle("XYZ123", model, "Red", 1984, 1600);
		
		Make make2 = new Make(); 	make2.setName("LockheedMartin");	
		Model model2 = new Model();	model2.setName("F22"); 	make2.addModel(model);
		Vehicle vehicle2 = new Vehicle("No1", model2, "BlackGreen", 2015, 5300);

		Collection<Vehicle> collection = new HashSet();
		collection.add(vehicle);
		collection.add(vehicle2);
		
		//System.out.println("{\"vehicles\":[{\"id\":\"-1\",\"reg\":\"XYZ123\",\"model\":\"Tank2\",\"make\":\"LockheedMartin\",\"colour\":\"Red\",\"year\":\"1984\",\"engSizeCC\":\"1600\"},{\"id\":\"-1\",\"reg\":\"No1\",\"model\":\"F22\",\"colour\":\"BlackGreen\",\"year\":\"2015\",\"engSizeCC\":\"5300\"}]}");
		//System.out.println(Vehicle.toJSON(collection));
		
		assertEquals(Vehicle.toJSON(collection),"{\"vehicles\":[{\"id\":\"-1\",\"reg\":\"XYZ123\",\"model\":\"Tank2\",\"make\":\"LockheedMartin\",\"colour\":\"Red\",\"year\":\"1984\",\"engSizeCC\":\"1600\"},{\"id\":\"-1\",\"reg\":\"No1\",\"model\":\"F22\",\"colour\":\"BlackGreen\",\"year\":\"2015\",\"engSizeCC\":\"5300\"}]}");
	}

	@Test
	public void testAllParameterConstructor() {
		Vehicle vehicle = new Vehicle(100, "XYZ123", null, "Red", 1984, 1600);

		assertEquals(100, vehicle.getId());
		assertEquals("XYZ123", vehicle.getReg());
		assertEquals(null, vehicle.getModel());
		assertEquals("Red", vehicle.getColour());
		assertEquals(1984, vehicle.getYear());
		assertEquals(1600, vehicle.getEngSizeCC());
	}

	@Test
	public void testGetAndSetId() {
		Vehicle vehicle = new Vehicle();

		vehicle.setId(100);
		assertEquals(100, vehicle.getId());
	}

	@Test
	public void testGetAndSetReg() {
		Vehicle vehicle = new Vehicle();

		vehicle.setReg("ACB789");
		assertEquals("ACB789", vehicle.getReg());
	}

	@Test
	public void testGetAndSetModel() {
		Vehicle vehicle = new Vehicle();
		Model model = new Model();
		model.setName("TestModel");

		vehicle.setModel(model);

		assertEquals("TestModel", vehicle.getModel().getName());
		// Check Back Connection. Check
		// vehicle.model.vehicles.contains(vehicle);
		assertTrue(vehicle.getModel().getVehicles().contains(vehicle));

	}

	@Test
	public void testGetAndSetColour() {
		Vehicle vehicle = new Vehicle();

		vehicle.setColour("Blue");
		assertEquals("Blue", vehicle.getColour());
	}

	@Test
	public void testGetAndSetYear() {
		Vehicle vehicle = new Vehicle();

		vehicle.setYear(1984);
		assertEquals(1984, vehicle.getYear());
	}

	@Test
	public void testGetAndSetEngSizeCC() {
		Vehicle vehicle = new Vehicle();

		vehicle.setEngSizeCC(1600);
		assertEquals(1600, vehicle.getEngSizeCC());
	}

}
