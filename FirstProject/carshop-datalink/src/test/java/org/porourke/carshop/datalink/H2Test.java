package org.porourke.carshop.datalink;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;

import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.orourke.carshop.datalink.H2;
import org.porourke.carshop.model.MakeInterface;
import org.porourke.carshop.model.Modelnterface;
import org.porourke.carshop.model.VechicleInterface;
import org.porourke.carshop.model.vechicles.Make;
import org.porourke.carshop.model.vechicles.Model;
import org.porourke.carshop.model.vechicles.Vehicle;

public class H2Test extends H2{
	private H2 h2;
	@Before
	public void setup(){
		JdbcConnectionPool cp = JdbcConnectionPool.create("jdbc:h2:./test", "porourke", "Password123");
		h2 = new H2(cp);
	}
	
	@Test
	public void testSaveVehicleIdUpdated() throws Exception {
		Vehicle vehicle = makeVehicle("ABC123", makeModel("Focus", makeMake("Ford")));
		h2.saveVehicle(vehicle);
		assertTrue(vehicle.getId()>0);
	}
	
	@Test
	public void testSaveModelIdUpdated() throws Exception {
		Vehicle vehicle = makeVehicle("ABC123", makeModel("Focus", makeMake("Ford")));
		h2.saveVehicle(vehicle);
		assertTrue(vehicle.getModel().getId()>0);
	}	
	
	@Test
	public void testSaveMakeIdUpdated() throws Exception {
		Vehicle vehicle = makeVehicle("ABC123", makeModel("Focus", makeMake("Ford")));
		h2.saveVehicle(vehicle);
		assertTrue(vehicle.getModel().getMake().getId()>0);
	}
	
	@Test
	public void testGetVehicleById() throws Exception {
		Make make = new Make("Testmake");
		Model model = new Model("TestModel", make);
		Vehicle vehicle = new Vehicle("ABC123", model,"Red",1984,1605);
		
		h2.saveMakeWithoutConnections(make);
		h2.saveModelWithoutConnections(model);
		h2.saveVehicle(vehicle);
		
		VechicleInterface result = h2.getVehicleById(vehicle.getId());
		System.out.println(vehicle.getId());
		assertEquals(vehicle,result);
	}
	
	
	@Test
	public void testGetModelById() throws Exception {
		 
		Model model = makeModel("Focus", makeMake("Ford"));
		Vehicle vehicle = makeVehicle("ABC123", model);
		h2.saveVehicle(vehicle);
		
		Modelnterface result = h2.getModelById(model.getId());
		assertEquals("Vehicles not equal", result.getId(), model.getId());
		//assertEquals("Makes not equal", result.getMake(), model.getMake());
		assertEquals("Names not equal", result.getName(), model.getName());
		assertEquals("Vehicles not equal", result.getVehicles(), model.getVehicles());

		//assertEquals("Models not Equal", result,model);
	}
	
	@Test
	public void testGetModelByIdVehiclesLength() throws Exception {
		
		Model model = makeModel("Focus", makeMake("Ford"));
		Vehicle vehicle = makeVehicle("ABC123", model);
		h2.saveVehicle(vehicle);
		
		Modelnterface result = h2.getModelById(model.getId());
		//assertEquals("Vehicles not equal", result.getId(), model.getId());
		//assertEquals("Makes not equal", result.getMake(), model.getMake());
		//assertEquals("Names not equal", result.getName(), model.getName());
		//assertEquals("Vehicles not equal", result.getVehicles(), model.getVehicles());
		//System.out.println(result.getVehicles().size());
		//System.out.println(model.getVehicles().size());
		assertEquals(result.getVehicles().size(),model.getVehicles().size());
		
	}
	
	@Test
	public void testGetMakeById() throws Exception {
		
		Make make = makeMake("Ford");
		Model model = makeModel("Focus", make);
		Vehicle vehicle = makeVehicle("ABC123", model);
		h2.saveVehicle(vehicle);
		
		MakeInterface result = h2.getMakeById(make.getId());
		assertEquals(result,make);
	}
	
	@Test
	public void testMakeEquals() throws Exception {
		MakeInterface make = new Make();
		MakeInterface make2 = new Make();
		
		assertEquals(make,make2);
		
	}
	@Test
	public void testMakeEqualsWithIdAndName() throws Exception {
		MakeInterface make = new Make();
		make.setId(1);
		make.setName("Fred");
		
		MakeInterface make2 = new Make();
		make2.setId(1);
		make2.setName("Fred");
		
		assertEquals(make,make2);
	}
	@Test
	public void testMakeEqualsWithIdNameAndModel() throws Exception {
		
		
		MakeInterface make = new Make();
		make.setId(1);
		make.setName("Fred");
		make.addModel(new Model());
		
		
		MakeInterface make2 = new Make();
		make2.setId(1);
		make2.setName("Fred");
		make2.addModel(new Model());
		
		assertEquals(make,make2);
	}
	
	@Test
	public void saveMakeWithoutConnectionsTest() throws SQLException{
		MakeInterface make0 = null;
		MakeInterface make1 = new Make("TestMake");
		MakeInterface make2 = new Make("TestMake1");
		MakeInterface make3 = new Make(-1,new HashSet<Model>(),"TestMake");
		
		clearDatabase(h2);
		h2.saveMakeWithoutConnections(make0);	//Not Saved 
		h2.saveMakeWithoutConnections(make1);
		h2.saveMakeWithoutConnections(make2);
		h2.saveMakeWithoutConnections(make3); //Not Saved, Same Name
		
		//Check Id updated from -1 to UI by saveMake(make)
		assertNotEquals(make1.getId(), -1);
		assertNotEquals(make2.getId(), -1);
		assertNotEquals(make3.getId(), -1); 
	
		ResultSet rs = null;
		rs = h2.getConnection().createStatement().executeQuery(""
				+ "SELECT id, name FROM Make;");
		
		int countRows = 0;
		while(rs.next()) 
		{	//System.out.println("Id " + rs.getString("id") + " Name " + rs.getString("name") );
			//Check ids != -1 after pulled from database
			assertNotEquals(rs.getInt("id"),-1);
			countRows++;
		}
		assertEquals(2,countRows);
	}
	
	@Test
	public void saveModelWithoutConnectionsTest() throws SQLException{
		Make make1 = new Make("TestMake1");
		Make make2 = new Make("TestMake2");
		
		Modelnterface model0 = null;
		Modelnterface model1 = new Model("TestModel1", make1);	
		Modelnterface model2 = new Model("TestModel2", make1);	
		Modelnterface model3 = new Model("TestModel3", make2);	
		Modelnterface model4 = new Model("TestModel1", make1);	
		
		clearDatabase(h2);
		h2.saveMakeWithoutConnections(make1);
		h2.saveMakeWithoutConnections(make2);
		
		h2.saveModelWithoutConnections(model0); // Not Saved == null
		h2.saveModelWithoutConnections(model1); 
		h2.saveModelWithoutConnections(model2);
		h2.saveModelWithoutConnections(model3);
		h2.saveModelWithoutConnections(model4); // Not Saved, Same Make and Name
	
		ResultSet rs = null;
		rs = h2.getConnection().createStatement().executeQuery(""
				+ "SELECT * FROM Model");
	
		//System.out.println(this.resultSetToString(rs));
		
		int countRows = 0;
		while(rs.next()) countRows++;
		
		assertEquals(2,countRows);	
	}
	
	@Test
	public void testSaveVechicle() throws SQLException{
		Make make1 = new Make("TestMake1");
		Make make2 = new Make("TestMake2");
		
		Model model0 = null;
		Model model1 = new Model("TestModel1", make1); 	
		Model model2 = new Model("TestModel2", make2);	
		Model model3 = new Model("TestModel3", make2);
			
		Vehicle vehicle1 = new Vehicle(-1,"ABC123",model1, "Red",1981,1605);
		Vehicle vehicle2 = new Vehicle(-1,"ABC124",model1, "Red",1982,1605);
		Vehicle vehicle3 = new Vehicle(-1,"XYZ123",model2, "Red",1983,1605);
		Vehicle vehicle4 = new Vehicle(-1,"XYZ124",model3, "Red",1984,1605);
		Vehicle vehicle5 = new Vehicle(-1,"XYZ125",model0, "Red",1985,1605);// Not saved model Null
		VechicleInterface vehicle6 = new Vehicle(-1,"ABC123",model1, "Red",1981,1605);// Not saved identical to model1
		
		clearDatabase(h2);
		h2.saveMakeWithoutConnections(make1);
		h2.saveMakeWithoutConnections(make2);
		
		h2.saveModelWithoutConnections(model1);
		h2.saveModelWithoutConnections(model2);
		
		h2.saveVehicle(vehicle1);
		h2.saveVehicle(vehicle2);
		h2.saveVehicle(vehicle3);
		h2.saveVehicle(vehicle4);
		h2.saveVehicle(vehicle5);
		
		//Test Vehicles are Saved
		ResultSet rs = null;
		rs = h2.getConnection().createStatement().executeQuery(""
				+ "SELECT * FROM Vehicle");
		//System.out.println(resultSetToString(rs));
		int countRows = 0;
		while(rs.next()) countRows++;
		assertEquals(4,countRows);
		
		
		// Test Models are Saved
		rs = h2.getConnection().createStatement().executeQuery(""
				+ "SELECT * FROM Model");
		countRows = 0;
		//System.out.println(resultSetToString(rs));
		while(rs.next()) countRows++;
		assertEquals(3,countRows);
		
		// Test Makes are Saved
		rs = h2.getConnection().createStatement().executeQuery(""
				+ "SELECT * FROM Make");
		countRows = 0;
		//System.out.println(resultSetToString(rs));
		while(rs.next()) countRows++;
		assertEquals(2,countRows);
	}
	
	@Test
	public void testGetVehicleConnections() throws Exception {
		
		//Make a Vehicle, model, and make
		MakeInterface make = makeMake("TestMake");
		Model model = makeModel("TestModel"); 	
		Vehicle vehicle =  new Vehicle("TestReg",model, "Red",1984,1605);
		
		//Save to a blank database
		clearDatabase(h2);

		h2.saveVehicle(vehicle);
		
		//Get Vehicle from database
		VechicleInterface vechicleFromDatabase = h2.getVehicleById(vehicle.getId());
		
		//Check all connections are make between Vehicle, model, and make
		assertEquals("TestModel",vechicleFromDatabase.getModel().getName());
		assertEquals(1,vechicleFromDatabase.getModel().getVehicles().size());
		
		//assertTrue(vechicleFromDatabase.getModel().getVehicles().contains(model));

		//assertEquals("TestMake",vechicleFromDatabase.getModel().getMake().getName());
		
		ResultSet rs = null;
		rs = h2.getConnection().createStatement().executeQuery(""
				+ "SELECT * FROM Vehicle");
	
		System.out.println(resultSetToString(rs));
		
		
		//assertEquals(4,countRows);	

	}
	
	
	@Test
	public void testMultipleConnectionsFromConnectionPoolOnH2() throws SQLException{
		Connection connection = h2.getConnection();
		Connection connection2 = h2.getConnection();
		ResultSet rs = connection2.createStatement().executeQuery("Show Tables");
		connection2.close();
		
		connection.createStatement().executeQuery("Show Tables");
		connection.close();
	}
	//******************************************************
	//End of Tests
	//******************************************************
	
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
	
	private void clearDatabase(H2 testH2) 
	{	Connection connection = null;
		try {
			connection = testH2.getConnection();
			Statement statement = connection.createStatement();
			statement.execute(""
					+ "DROP TABLE MAKE;"
					+ "CREATE TABLE IF NOT EXISTS Make"
					+ "(	id 			int 		PRIMARY KEY AUTO_INCREMENT,"
					+ "		name 		varchar(20) NOT NULL 	UNIQUE"
					+ ");"
					+ "DROP TABLE Model;"
					+ "CREATE TABLE IF NOT EXISTS Model"
					+ "(	id			int 		PRIMARY KEY AUTO_INCREMENT,"
					+ "		make 		int			NOT NULL,"
					+ "		name 		varchar(20) NOT NULL,"
					+ "		FOREIGN KEY (make) REFERENCES public.Make (id) "
					+ ");"
					+ "DROP TABLE Vehicle;"
					+ "CREATE TABLE IF NOT EXISTS Vehicle"
					+ "(	id 			int 		PRIMARY KEY AUTO_INCREMENT,"
					+ "		reg 		varchar(20) NOT NULL	UNIQUE,"
					+ "		model 		int			NOT NULL,"
					+ "		colour 		varchar(20),"
					+ "		year 		int,"
					+ "		engSizeCC 	int,"
					+ "		FOREIGN KEY (model) REFERENCES public.Model(id)"
					+ ");"
					);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally
		{	try {
			connection.close();
			} catch (SQLException e) {
			
				e.printStackTrace();
			}	
		}
	}

	private String resultSetToString(ResultSet rs)
	{	String ans = "";
		try{
			ResultSetMetaData rsmd = rs.getMetaData();
		    int columnsNumber = rsmd.getColumnCount();
		    while (rs.next()) {
		        for (int i = 1; i <= columnsNumber; i++) {
		            if (i > 1) ans = ans + ",";
		            String columnValue = rs.getString(i);
		            ans = ans + rsmd.getColumnName(i)  + "\t" + columnValue + "\t";
		        }
		        ans = ans + "\n";
		    }
		} catch(Exception e)
		{	e.printStackTrace();
		}
		return ans;
	}
}
