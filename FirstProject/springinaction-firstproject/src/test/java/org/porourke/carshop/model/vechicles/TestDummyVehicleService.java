package org.porourke.carshop.model.vechicles;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.porourke.carshop.model.MakeInterface;
import org.porourke.carshop.model.Modelnterface;
import org.porourke.carshop.model.VechicleInterface;

public class TestDummyVehicleService {
	private static DummyVehiclesService dummyVehiclesService;
	private static List<Vehicle> vehicleList;
	
	@Before
	public void setup(){
		dummyVehiclesService = new DummyVehiclesService();
		
	}
	@Test
	public void testSaveVehicleIdUpdated() throws Exception {
		Vehicle vehicle = makeVehicle("ABC123", makeModel("Focus", makeMake("Ford")));
		dummyVehiclesService.saveVehicle(vehicle);
		assertEquals(1,vehicle.getId());
	}
	
	@Test
	public void testSaveModelIdUpdated() throws Exception {
		Vehicle vehicle = makeVehicle("ABC123", makeModel("Focus", makeMake("Ford")));
		dummyVehiclesService.saveVehicle(vehicle);
		assertEquals(1,vehicle.getModel().getId());
	}
	
	@Test
	public void testSaveMakeIdUpdated() throws Exception {
		Vehicle vehicle = makeVehicle("ABC123", makeModel("Focus", makeMake("Ford")));
		dummyVehiclesService.saveVehicle(vehicle);
		assertEquals(1,vehicle.getModel().getMake().getId());
	}
	
	@Test
	public void testSaveMakeIdUpdatedAgain() throws Exception {
		Vehicle vehicle = makeVehicle("ABC123", makeModel("Focus", makeMake("Ford")));
		dummyVehiclesService.saveVehicle(vehicle);
		Vehicle vehicle2 = makeVehicle("XYZ123", makeModel("Focus", makeMake("Ford")));
		dummyVehiclesService.saveVehicle(vehicle2);
		assertEquals(2,vehicle2.getId());
	}
	
	@Test
	public void testSaveMakeIdUpdatedAgain2() throws Exception {
		Model model = makeModel("Focus", makeMake("Ford"));
		Vehicle vehicle = makeVehicle("ABC123", model);
		dummyVehiclesService.saveVehicle(vehicle);
		Vehicle vehicle2 = makeVehicle("XYZ123", model);
		dummyVehiclesService.saveVehicle(vehicle2);
		assertEquals(1,vehicle2.getModel().getId());
	}
	@Test
	public void testGetVehicleById() throws Exception {
		
		Model model = makeModel("Focus", makeMake("Ford"));
		Vehicle vehicle = makeVehicle("ABC123", model);
		dummyVehiclesService.saveVehicle(vehicle);
		Vehicle vehicle2 = makeVehicle("XYZ123", model);
		dummyVehiclesService.saveVehicle(vehicle2);
		
		VechicleInterface result = dummyVehiclesService.getVehicleById(1);
		assertEquals(result,vehicle);
	}
	
	@Test
	public void testGetModelById() throws Exception {
		
		Model model = makeModel("Focus", makeMake("Ford"));
		Vehicle vehicle = makeVehicle("ABC123", model);
		dummyVehiclesService.saveVehicle(vehicle);
		Vehicle vehicle2 = makeVehicle("XYZ123", model);
		dummyVehiclesService.saveVehicle(vehicle2);
		
		Modelnterface result = dummyVehiclesService.getModelById(1);
		assertEquals(result,model);
	}
	
	@Test
	public void testGetMakeById() throws Exception {
		
		Make make = makeMake("Ford");
		Model model = makeModel("Focus", make);
		Vehicle vehicle = makeVehicle("ABC123", model);
		dummyVehiclesService.saveVehicle(vehicle);
		Vehicle vehicle2 = makeVehicle("XYZ123", model);
		dummyVehiclesService.saveVehicle(vehicle2);
		
		MakeInterface result = dummyVehiclesService.getMakeById(1);
		assertEquals(result,make);
	}
	
	@Test
	public void testGetMakes() throws Exception {
		
		Make make1 = makeMake("Ford");
		Vehicle vehicle = makeVehicle("ABC123", makeModel("Focus", make1));
		dummyVehiclesService.saveVehicle(vehicle);
		Vehicle vehicle2 = makeVehicle("XYZ123", makeModel("Golf", makeMake("Voltswagan")));
		dummyVehiclesService.saveVehicle(vehicle2);
		Set<Make> makes = dummyVehiclesService.getMakes();
		
		assertEquals(2,makes.size());
		assertTrue(makes.contains(make1));
	}
	
	private Vehicle makeVehicle(String reg){
		Vehicle vehicle = new Vehicle();
		vehicle.setReg(reg);
		return vehicle;
	}
	
	private Vehicle makeVehicle(String reg, Model model){
		Vehicle vehicle = makeVehicle(reg);
		vehicle.setModel(model);
		model.getVehicles().add(vehicle);
		return vehicle;
	}
	
	private Model makeModel(String name){
		Model model = new Model();
		model.setName(name);
		return model;
	}
	
	private Model makeModel(String name, Make make){
		Model model = makeModel(name);
		model.setMake(make);
		make.getModels().add(model);
		return model;
	}
	
	private Make makeMake(String name){
		Make make = new Make();
		make.setName(name);
		return make;
	}
	
	
	/*@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		vehicleList = Arrays.asList(
				new Vehicle("ABC123", "Voltswagen", "Passat B6", "Black", 2014, 2104), 	//0
				new Vehicle("ABD123", "Voltswagen", "Passat B6", "Red", 2013, 2104),	//1
				new Vehicle("ABE123", "Voltswagen", "Passat B6", "White", 2012, 2104),	//2
				new Vehicle("ABF123", "Voltswagen", "Passat B7", "Black", 2011, 2104),	//3
				new Vehicle("ABG123", "Voltswagen", "Passat B7", "Blue", 2014, 2104),	//4
				new Vehicle("ABH123", "Voltswagen", "Golf Mk6 ", "Black", 2013, 1605),	//5
				new Vehicle("ABI123", "Voltswagen", "Golf Mk6", "Red", 2012, 1605),		//6
				new Vehicle("ABJ123", "Voltswagen", "Golf Mk6", "Black", 2011, 1605),	//7
				new Vehicle("ABK123", "Voltswagen", "Polo Mk4", "Black", 2010, 995),	//8
				new Vehicle("ABL123", "Voltswagen", "Polo Mk4", "Red", 2014, 995),		//9
				new Vehicle("ABM123", "Ford", "Fiesta ST", "Black", 2013, 1005),		//10
				new Vehicle("ABN123", "Ford", "Fiesta ST", "Black", 2012, 998),			//11
				new Vehicle("ABO123", "Ford", "Fiesta ST", "Red", 2011, 998),			//12
				new Vehicle("ABP123", "Ford", "Fiesta ST", "White", 2014, 998),			//13
				new Vehicle("ABQ123", "Ford", "Focus", "White", 2013, 1605),			//14
				new Vehicle("ABR123", "Ford", "Focus", "White", 2012, 1605),			//15
				new Vehicle("ABS123", "Ford", "Focus", "Black", 2011, 1605),			//16
				new Vehicle("ABT123", "Ford", "Mustang", "Red", 2014, 2300),			//17
				new Vehicle("ABU123", "Ford", "Mustang", "Red", 2013, 2300),			//18
				new Vehicle("ABV123", "Ford", "Mustang", "Black", 2013, 2300),			//19
				new Vehicle("ABW123", "Toyota", "Hilux", "Black", 2012, 2200),			//20
				new Vehicle("ABX123", "Toyota", "Hilux", "White", 2012, 2200),			//21
				new Vehicle("ABY123", "Toyota", "Hilux", "White", 2011, 2400),			//22
				new Vehicle("ABZ123", "Toyota", "Prius", "Red", 2014, 1800),			//23
				new Vehicle("ABC124", "Toyota", "Prius", "Red", 2014, 1800),			//24
				new Vehicle("ABC125", "Toyota", "Prius", "Black", 2013, 1600),			//25
				new Vehicle("ABC126", "Toyota", "Land Crusier", "Black", 2013, 2300),	//26
				new Vehicle("ABC127", "Toyota", "Yaris Hybrid", "White", 2012, 1005),	//27
				new Vehicle("ABC128", "Toyota", "Yaris Hybrid", "Red", 2012, 1005),		//28
				new Vehicle("ABC129", "Toyota", "Yaris Hybrid", "Black", 2011, 1005),	//29
				new Vehicle("ABC130", "Lada", "Pria", "Black", 1975, 1500),				//30
				new Vehicle("ABC133", "BAE", "Tank", "Green", 2013, 60000)				//31
			);	
		dummyVehiclesService = new DummyVehiclesService(vehicleList);
	}

	@Test
	public void testGetFilteredVehicles() {
		//Test cases where there are no filters
		assertEquals(vehicleList, dummyVehiclesService.getFilteredVehicles(null, null));
		assertEquals(vehicleList, dummyVehiclesService.getFilteredVehicles("", null));
		assertEquals(vehicleList, dummyVehiclesService.getFilteredVehicles(null, ""));
		assertEquals(vehicleList, dummyVehiclesService.getFilteredVehicles("", ""));
		//testFilterResulting in One Result
		assertEquals(vehicleList.get(30),dummyVehiclesService.getFilteredVehicles("Lada", "").get(0));
		assertEquals(vehicleList.get(31),dummyVehiclesService.getFilteredVehicles("", "Tank").get(0));
		//testFilterResulting in Multiple Results
		List<Vehicle> expectedResult = Arrays.asList(
				new Vehicle("ABM123", "Ford", "Fiesta ST", "Black", 2013, 1005),		
				new Vehicle("ABN123", "Ford", "Fiesta ST", "Black", 2012, 998),			
				new Vehicle("ABO123", "Ford", "Fiesta ST", "Red", 2011, 998),			
				new Vehicle("ABP123", "Ford", "Fiesta ST", "White", 2014, 998),			
				new Vehicle("ABQ123", "Ford", "Focus", "White", 2013, 1605),		
				new Vehicle("ABR123", "Ford", "Focus", "White", 2012, 1605),			
				new Vehicle("ABS123", "Ford", "Focus", "Black", 2011, 1605),		
				new Vehicle("ABT123", "Ford", "Mustang", "Red", 2014, 2300),		
				new Vehicle("ABU123", "Ford", "Mustang", "Red", 2013, 2300),		
				new Vehicle("ABV123", "Ford", "Mustang", "Black", 2013, 2300)			
			);	
		assertEquals(expectedResult,dummyVehiclesService.getFilteredVehicles("Ford", ""));
		expectedResult = Arrays.asList(
				new Vehicle("ABM123", "Ford", "Fiesta ST", "Black", 2013, 1005),		
				new Vehicle("ABN123", "Ford", "Fiesta ST", "Black", 2012, 998),			
				new Vehicle("ABO123", "Ford", "Fiesta ST", "Red", 2011, 998),			
				new Vehicle("ABP123", "Ford", "Fiesta ST", "White", 2014, 998)					
			);	
		assertEquals(expectedResult,dummyVehiclesService.getFilteredVehicles("", "Fiesta ST"));
		expectedResult = Arrays.asList(		
				new Vehicle("ABQ123", "Ford", "Focus", "White", 2013, 1605),		
				new Vehicle("ABR123", "Ford", "Focus", "White", 2012, 1605),			
				new Vehicle("ABS123", "Ford", "Focus", "Black", 2011, 1605)		
						
			);	
		assertEquals(expectedResult,dummyVehiclesService.getFilteredVehicles("Ford", "Focus"));
		//Tests with 0 Result
		assertEquals(Arrays.asList(),dummyVehiclesService.getFilteredVehicles("For", "Focus"));
		assertEquals(Arrays.asList(),dummyVehiclesService.getFilteredVehicles("Ford","Fcus"));
		assertEquals(Arrays.asList(),dummyVehiclesService.getFilteredVehicles("For", ""));
		assertEquals(Arrays.asList(),dummyVehiclesService.getFilteredVehicles("For", null));
		assertEquals(Arrays.asList(),dummyVehiclesService.getFilteredVehicles("", "Fcus"));
		assertEquals(Arrays.asList(),dummyVehiclesService.getFilteredVehicles(null, "Fcus"));
	}

	public void testGetAllVehicleMakes() {
		Set<String> expectedResult = new HashSet<String>(Arrays.asList("Voltswagen","Ford","Toyota","Lada", "BAE"));
		assertEquals(expectedResult,dummyVehiclesService.getAllVehicleMakes());
	}
*/

}
