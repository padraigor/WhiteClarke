package org.orourke.carshop.datalink;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

import org.h2.jdbcx.JdbcConnectionPool;
import org.porourke.carshop.model.MakeInterface;
import org.porourke.carshop.model.Modelnterface;
import org.porourke.carshop.model.VechicleInterface;
import org.porourke.carshop.model.VechiclesInterface;
import org.porourke.carshop.model.vechicles.Make;
import org.porourke.carshop.model.vechicles.Model;
import org.porourke.carshop.model.vechicles.Vehicle;

public class H2 implements VechiclesInterface{
	private JdbcConnectionPool connectionPool;
	
	public H2(){super();}
	
	public H2(JdbcConnectionPool cp) {
		super();
		this.connectionPool = cp;
		if(cp == null){
			throw new IllegalArgumentException("Connection pool cannot be null"); 
		}
	}
	/*static void initiliseConnectionPool() {
		 cp = JdbcConnectionPool.create("jdbc:h2:./test", "porourke", "Password123");
	}*/
	public JdbcConnectionPool getConnectionPool(){	
		try {
			return connectionPool;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	public Connection getConnection(){	
		try {
			return connectionPool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}	
	}  
	
	public void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (Throwable e) {
			System.out.println("Failed to close H2 database. Details follow: ");
			e.printStackTrace();
		}
	}

	public Set<Make> getMakes() {
		// TODO Auto-generated method stub
		return null;
	}

	public Make getMakeById(int id) {
		Make ans = null;
		Connection connection = null;
		try{
			connection = getConnection();
			String SQL_INSERT = "SELECT name FROM Make WHERE id = ?";
			
			PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
			statement.setInt	(1, id);
				 
			ResultSet rs = statement.executeQuery();
	
			if(rs.next()){
				String 	name = rs.getString	("name");
				
				ans = new Make();
				ans.setId(id);
				ans.setName(name);
				
			}
			else
			{	return null;//If there is no matching record return null
			}
	        if (rs.next()) { // If more than one record returned  
	        	throw new Exception("Database integrity broken on uniquesness of vehicle primary key value");
	        }   
		}catch(Exception e){
			e.printStackTrace();
		}finally{	
			closeConnection(connection);
		}
		return ans;
	}

	public Model getModelById(int id) {
		Model ans = null;
		Connection connection = null;
		try{
			connection = getConnection();
			String SQL_INSERT = "SELECT make, name FROM Model WHERE id = ?";
			
			PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
			statement.setInt	(1, id);
				 
			ResultSet rs = statement.executeQuery();
	
			if(rs.next()){
				int    	make_id 	= rs.getInt		("make");
				String 	name 		= rs.getString 	("name");
				
				Make make = this.getMakeById(make_id);
				ans = new Model();
				ans.setId(id);
				ans.setMake(make);
				ans.setName(name);
				
				this.addVehiclesToModel(ans);
			}
			else
			{	return null;//If there is no matching record return null
			}
	        if (rs.next()) { // If more than one record returned  
	        	throw new Exception("Database integrity broken on uniquesness of vehicle primary key value");
	        }   
		}catch(Exception e){
			e.printStackTrace();
		}finally{	
			closeConnection(connection);
		}
		return ans;	
	}

	//Warning does not generate complete models or makes
	public VechicleInterface getVehicleById(int id) {
		int modelId; int makeId;
		VechicleInterface ans = null; 
		Connection connection = null;
		
		try{//Get Vehicle Data *****************************
			connection = getConnection();
			String SQL_INSERT = "SELECT id, reg, model, colour, year, engSizeCC FROM Vehicle WHERE id = ?";
			
			PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
			statement.setInt	(1, id);
				 
			ResultSet rs = statement.executeQuery();
	
			if(rs.next()){
				String 	reg 		= rs.getString	("reg");
						modelId 	= rs.getInt		("model");
				String 	colour 		= rs.getString 	("colour");
				int		year		= rs.getInt 	("year");
				int		engSizeCC	= rs.getInt 	("engSizeCC");
				
				ans = new Vehicle(id, reg, null,colour,year, engSizeCC);
				
			}
			else
			{	return null;//There is no vehicle with id. Return null.
			}
	        if (rs.next()) { // If more than one record returned  
	        	throw new Exception("Database integrity broken on uniquesness of vehicle primary key value");
	        } 
	        
	       //Get Model Data *****************************
	        SQL_INSERT = "SELECT name, makeId FROM Model WHERE id = ?";
			
			statement = connection.prepareStatement(SQL_INSERT);
			statement.setInt	(1, modelId);
				 
			rs = statement.executeQuery();
	
			if(rs.next()){
				String 	name 		= rs.getString	("name");
						makeId 		= rs.getInt		("id");
				
				ans.setModel(new Model(name, null));
				
			}
			else
			{	throw new Exception("Database integrity breached. FK Model in Vehicles referencing nothing.");
			}
	        if (rs.next()) { // If more than one record returned  
	        	throw new Exception("Database integrity breached on uniquesness of Models PK value.");
	        }
	        
	        //Get Make Data *****************************
	        SQL_INSERT = "SELECT name FROM Make WHERE id = ?";
			
			statement = connection.prepareStatement(SQL_INSERT);
			statement.setInt	(1, makeId);
				 
			rs = statement.executeQuery();
	
			if(rs.next()){
				String 	name 		= rs.getString	("name");
	
				ans.getModel().setMake(new Make(name));
			}
			else
			{	throw new Exception("Database integrity breached. FK Make in Models referencing nothing.");
			}
	        if (rs.next()) { // If more than one record returned  
	        	throw new Exception("Database integrity breached. Uniquesness of Models PK affected.");
	        }
		}catch(Exception e){
			e.printStackTrace();
		}finally{	
			closeConnection(connection);
		}
		return ans;
	}

	public void saveVehicle(Vehicle vehicle) {
		//Check vehicle not null
		if(vehicle == null){
			return;
		}
		//Check if vehicle already exists in database: PK constraint
		if(getVehicleById(vehicle.getId()) != null){
			return;
		}
		//Check reg and model both not equals null: NOT NULL constraint
		if(vehicle.getReg() == null || vehicle.getModel() == null){	
			return;
		}
		//Check if vehicle with this reg already exists in database: UNIQUE constraint on reg
		//If all other details match except id, and id == -1, the set id to existing vehicle id
		VechicleInterface existingVehicle = getVehicleWithReg(vehicle.getReg());
		if(existingVehicle != null){
			if(vehicle.getId() == -1){
				if(		vehicle.getModel()    == existingVehicle.getModel()
					&&	vehicle.getColour()   == existingVehicle.getColour()
					&&	vehicle.getYear()     == existingVehicle.getYear()
					&&	vehicle.getEngSizeCC()== existingVehicle.getEngSizeCC()
				   ){
					vehicle.setId(existingVehicle.getId());
					//saveModelAndMake() add before deployment
			}	} 	
			return;
		}
		//Check to see if Model already in database
		//If not save it
		Modelnterface existingModel = getModelById(vehicle.getModel().getId());
		if(existingModel == null){
			saveModelWithoutConnections(vehicle.getModel());
		}
		
		//If a make is associated with the vehicle being saved 
		//Check to see if Make already in database 
		//If not save it 
		if(vehicle.getModel().getMake() != null)
		{	MakeInterface existingMake = getMakeById(vehicle.getModel().getMake().getId());
			if(existingMake == null){
				saveMakeWithoutConnections(vehicle.getModel().getMake());
			}
		}
		
		Connection connection = null;
		try{
			connection = getConnection();
			String SQL_INSERT = "INSERT INTO Vehicle (reg, model, colour, year, engSizeCC) VALUES(?,?,?,?,?)";
			
			PreparedStatement statement = connection.prepareStatement(SQL_INSERT,Statement.RETURN_GENERATED_KEYS);
			statement.setString	(1, vehicle.getReg());
	        statement.setInt	(2, vehicle.getModel().getId());	
	        statement.setString	(3, vehicle.getColour());
	        statement.setInt	(4, vehicle.getYear());
	        statement.setInt	(5, vehicle.getEngSizeCC());
				 
			int affectedRows = statement.executeUpdate();
	
	        if (affectedRows == 0) {
	            throw new SQLException("Creating user failed, no rows affected.");
	        }
	        
	        //Sets the id to that made by the database
	        ResultSet generatedKeys = statement.getGeneratedKeys();
	        if (generatedKeys.next()) {
	        	System.out.println("Here" + generatedKeys.getInt(1));
	        	vehicle.setId(generatedKeys.getInt(1));
	        }
	        else {
	            throw new SQLException("Creating user failed, no ID obtained.");
	        }
		}catch(Exception e){
			e.printStackTrace();
		}finally{	
			closeConnection(connection);
		
		}
	}
	
	//*********************************************************************************
	//End of VechiclesInterface
	//*********************************************************************************
	
	//Assumes some integrity of Data Entered - Model must have make with ID !=-1
	//Any Vehicles associated with model are not saved
	public void saveModelWithoutConnections(Modelnterface model){
		//Break if model == null 
		if(model == null) {	
			return;
		}
		//Check if model with same id already exists in Database: PK constraint 
		if(getModelById(model.getId()) != null){
			return;
		}
		//Check that model.name and model.make are not null: NOT NULL constraints on name and model
		if(model.getName() == null || model.getMake() == null){	
			return;
		}
		//Check that there is not a not a Persistent record with both the same name and make
		//If there is and this model has no id(id=-1) set this id to existing persistent id
		if(getIdOfModelWithSameNameAndMakeId(model.getName(), model.getMake().getId()) != -1){
			model.setId(getIdOfModelWithSameNameAndMakeId(model.getName(), model.getMake().getId()));
			return;
		}
		Connection connection = null;
		try{
			connection = getConnection();
			String SQL_INSERT = "INSERT INTO Model (make, name) VALUES(?,?)";
			
			PreparedStatement statement = connection.prepareStatement(SQL_INSERT,Statement.RETURN_GENERATED_KEYS);
			statement.setInt	(1, model.getMake().getId());	
			statement.setString	(2, model.getName());
			
			int affectedRows = statement.executeUpdate();
			
	        if (affectedRows == 0) {
	            throw new SQLException("Creating user failed, no rows affected.");
	        }
	        
	        ResultSet generatedKeys = statement.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            model.setId(generatedKeys.getInt(1));
	        }
	        else {
	            throw new SQLException("Creating user failed, no ID obtained.");
	        }
		}catch(Exception e){
			e.printStackTrace();
		}finally{	
			closeConnection(connection);
		}	
	}

	public void addVehiclesToModel(Model model){
		Connection connection = null;
		try{
			connection = getConnection();
			String SQL_INSERT = "SELECT id, reg, model, colour, year, engSizeCC FROM Vehicle WHERE model = ?";
			
			PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
			statement.setInt	(1, model.getId());
				 
			ResultSet rs = statement.executeQuery();
	
			while(rs.next()){
				int		id			= rs.getInt 	("id");
				String 	reg 		= rs.getString	("reg");
				String 	colour 		= rs.getString 	("colour");
				int		year		= rs.getInt 	("year");
				int		engSizeCC	= rs.getInt 	("engSizeCC");
				
				VechicleInterface vehicle = new Vehicle(reg, model,colour,year, engSizeCC);
				vehicle.setId(id);
			}	  
		}catch(Exception e){
			e.printStackTrace();
		}finally{	
			closeConnection(connection);
		}
	}
	
	public void addModelsAndVehiclesToMake(Make make){
		Connection connection = null;
		try{
			connection = getConnection();
			String SQL_INSERT = "SELECT id, name FROM Model WHERE make = ?";
			
			PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
			statement.setInt	(1, make.getId());
				 
			ResultSet rs = statement.executeQuery();
	
			while(rs.next()){
				int		id			= rs.getInt 	("id");
				String 	name 		= rs.getString	("name");
				
				Model model = new Model();
				model.setId(id);
				// Need to set Vehicles
				model.setMake(make);
				model.setName(name);
				model.getMake().getModels().add(model);
				addVehiclesToModel(model);
			}	  
		}catch(Exception e){
			e.printStackTrace();
		}finally{	
			closeConnection(connection);
		}
	}
	
	public MakeInterface getMakeWithName(String name){
		ResultSet rs = null;
		Connection connection = null;
		try{
			connection = connectionPool.getConnection();
			String SQL_INSERT = "SELECT id FROM Make WHERE name = ?";
			
			PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
			statement.setString	(1, name);
			rs = statement.executeQuery();
			
			if(rs.next()){
				int id = rs.getInt("id");
				return new Make(id,name);
			}
			else{	
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{	
			closeConnection(connection);
		}
		return null;
	}
	
	public int getIdOfModelWithSameNameAndMakeId(String name, int makeId){
		ResultSet rs = null;
		Connection connection = null;
		try{
			connection = connectionPool.getConnection();
			String SQL_INSERT = "SELECT id FROM Model WHERE name = ? And make = ?";
			
			PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
			statement.setString	(1, name);
			statement.setInt	(2, makeId);
			rs = statement.executeQuery();
			
			if(rs.next()){
				return rs.getInt("id");
			}
			else{	
				return -1;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{	
			closeConnection(connection);
		}
		return -1;
	}
	
	public boolean ModelWithNameExists(String name){
		ResultSet rs = null;
		Connection connection = null;
		try{
			connection = connectionPool.getConnection();
			String SQL_INSERT = "SELECT * FROM Model WHERE name = ?";
			
			PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
			statement.setString	(1, name);
			rs = statement.executeQuery();
			
			if(rs.next()){
				return false;
			}
			else{	
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{	
			closeConnection(connection);
		}
		return false;
	}
	
	public VechicleInterface getVehicleWithReg(String reg){
		ResultSet rs = null;
		Connection connection = null;
		try{
			connection = connectionPool.getConnection();
			String SQL_INSERT = "SELECT id, model, colour, year, engSizeCC FROM Vehicle WHERE reg = ?";
			
			PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
			statement.setString	(1, reg);
			rs = statement.executeQuery();
			
			if(rs.next()){
				int 	id 		= rs.getInt("id");
				int		modelId = rs.getInt("model");
				String	colour	= rs.getString("colour");
				int		year	= rs.getInt("year");
				int		engSize = rs.getInt("engSizeCC");
				return new Vehicle(id,reg,getModelById(modelId),colour,year,engSize);
			}
			else{	
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{	
			closeConnection(connection);
		}
		return null;
	}

	public void saveMakeWithoutConnections(MakeInterface make){
		//If make == null cannot be saved -> Return
		if(make == null){
			return;
		}
		//Check if there is a make with the same id: PK constraint on id
		if(getMakeById(make.getId()) != null){
			return;
		}
		//Exit if make.name == null: NOT NULL constraint on name
				if(make.getName() == null){
					return;
				}
		
		//Is there is a persistent make with the same name: UNIQUE constraint on name
		//If there is and this make has not been save(id==-1) set make to same values as persistent make
		if(getMakeWithName(make.getName()) != null){
			if(make.getId() == -1){
				make.setId(getMakeWithName(make.getName()).getId());
			}
			return;
		}
		else
		{	Connection connection = null;
			try{
				connection = getConnection();
				String SQL_INSERT = "INSERT INTO Make (name) VALUES(?)";
				
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT,Statement.RETURN_GENERATED_KEYS);	
				statement.setString	(1, make.getName());
		        
				int affectedRows = statement.executeUpdate();
		
		        if (affectedRows == 0) {
		            throw new SQLException("Creating user failed, no rows affected.");
		        }
		
		        ResultSet generatedKeys = statement.getGeneratedKeys();
		        if (generatedKeys.next()) {
		            make.setId(generatedKeys.getInt(1));
		        }
		        else {
		            throw new SQLException("Creating user failed, no ID obtained.");
		        }
			}catch(Exception e){
				e.printStackTrace();
			}finally{	
				closeConnection(connection);
			}
		}
		
	}


}
