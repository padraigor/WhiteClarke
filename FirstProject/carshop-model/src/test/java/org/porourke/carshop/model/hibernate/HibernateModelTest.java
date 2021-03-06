package org.porourke.carshop.model.hibernate;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class HibernateModelTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testNoParameterConstructor() {
		Model model = new Model();

		assertEquals(-1, model.getId());
		assertEquals("", model.getName());
		assertEquals(null, model.getMake());
		assertEquals(new ArrayList<Vehicle>().getClass(), model
				.getVehicles().getClass());
	}

	@Test
	public void testNoIdConstructor() {
		Model model = new Model(new HashSet<Vehicle>(), new Make(),"TestName");

		assertEquals(-1, model.getId());
		assertEquals("TestName", model.getName());
		assertEquals("", model.getMake().getName());
		assertEquals(new HashSet<Vehicle>().getClass(), model.getVehicles().getClass());
	}

	@Test
	public void testAllParameterConstructor() {
		Model model = new Model(101, new HashSet<Vehicle>(), new Make(), "TestName");

		assertEquals(101, model.getId());
		assertEquals("TestName", model.getName());
		assertEquals("", model.getMake().getName());
		assertEquals(new HashSet<Vehicle>().getClass(), model.getVehicles().getClass());
	}

	@Test
	public void testGetAndSetId() {
		Model model = new Model();

		model.setId(747);

		assertEquals(747, model.getId());
	}

	@Test
	public void testGetAndSetName() {
		Model model = new Model();

		model.setName("TestModel");

		assertEquals("TestModel", model.getName());
	}

	@Test
	public void testGetAndSetMake() {
		Make make = new Make();
		make.setName("TestMakeName");
		Model model = new Model();

		model.setMake(make);

		assertEquals("TestMakeName", model.getMake().getName());
		// Check for back Connection
		// System.out.println("Here " + model.getMake().getModels().toArray(new
		// ModelInterface[1])[0]);

		assertEquals("TestMakeName", model.getMake().getModels().toArray(new Model[1])[0].getMake().getName());
	}

	@Test
	public void testGetAndSetVehicles() {
		Model model = new Model(100, new HashSet<Vehicle>(), null, "TestMake");
		Vehicle vehicle = new Vehicle("ABC123", model, "Red", 1984, 1600);

		ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();
		vehicleList.add(vehicle);

		model.setVehicles(vehicleList);

		// Check Model Added
		assertEquals(vehicle, model.getVehicles().toArray(new Vehicle[model.getVehicles().size()])[0]);
		// Check model.make = make
		assertEquals(model,model.getVehicles().toArray(new Vehicle[model.getVehicles().size()])[0].getModel());

	}

}
