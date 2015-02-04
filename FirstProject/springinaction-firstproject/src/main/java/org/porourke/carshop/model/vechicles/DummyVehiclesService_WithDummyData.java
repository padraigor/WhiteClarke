package org.porourke.carshop.model.vechicles;

import java.util.HashSet;

public class DummyVehiclesService_WithDummyData extends DummyVehiclesService{
	
	public DummyVehiclesService_WithDummyData(){
		super();
		setupDummyData();
	}
	
	private void setupDummyData()
	{	Make voltsWagen = new Make(1,new HashSet<Model>(),"Voltswagen");				makes.put(1, voltsWagen); 
		Make ford  		= new Make(2,new HashSet<Model>(),"Ford");						makes.put(2, ford); 
		Make toyota  	= new Make(3,new HashSet<Model>(),"Toyota");					makes.put(3, toyota); 
		Make lada  		= new Make(4,new HashSet<Model>(),"Lada");						makes.put(4, lada); 
		Make BAE  		= new Make(5,new HashSet<Model>(),"BAE");						makes.put(5, BAE); 
		
		Model passat_B6 = new Model(1,new HashSet<Vehicle>(),voltsWagen,"Passat B6"); 	models.put(1, passat_B6);
		Model golf 		= new Model(2,new HashSet<Vehicle>(),voltsWagen,"Golf"); 		models.put(2, golf);
		Model polo 		= new Model(3,new HashSet<Vehicle>(),voltsWagen,"Polo"); 		models.put(3, polo);
		Model fiesta_ST = new Model(4,new HashSet<Vehicle>(),ford,		"Fiesta_ST"); 	models.put(4, fiesta_ST);
		Model focus 	= new Model(5,new HashSet<Vehicle>(),ford,		"Focus"); 		models.put(5, focus);
		Model mustang 	= new Model(6,new HashSet<Vehicle>(),ford,		"Mustang"); 	models.put(6, mustang);
		Model hilux 	= new Model(7,new HashSet<Vehicle>(),toyota,	"Hilux"); 		models.put(7, hilux);
		Model prius 	= new Model(8,new HashSet<Vehicle>(),toyota,	"Prius"); 		models.put(8, prius);
		Model landCrusier= new Model(9,new HashSet<Vehicle>(),toyota,	"LandCruiser");	models.put(9, landCrusier);
		Model yarris	= new Model(10,new HashSet<Vehicle>(),toyota,	"Yarris"); 		models.put(10, yarris);
		Model pria 		= new Model(11,new HashSet<Vehicle>(),lada,		"Pria"); 		models.put(11, pria);
		Model tank 		= new Model(12,new HashSet<Vehicle>(),BAE,		"Tank"); 		models.put(12, tank);
				
		saveVehicle(new Vehicle("ABC123", passat_B6	, "Black", 	2014, 2104)); 			
		saveVehicle(new Vehicle("ABD123", passat_B6	, "Red", 	2013, 2104));	
		saveVehicle(new Vehicle("ABE123", passat_B6	, "White", 	2012, 2104));	
		saveVehicle(new Vehicle("ABF123", passat_B6	, "Black", 	2011, 2104));	
		saveVehicle(new Vehicle("ABG123", passat_B6	, "Blue", 	2014, 2104));	
		saveVehicle(new Vehicle("ABH123", golf	   	, "Black", 	2013, 1605));	
		saveVehicle(new Vehicle("ABI123", golf		, "Red", 	2012, 1605));		
		saveVehicle(new Vehicle("ABJ123", golf		, "Black", 	2011, 1605));	
		saveVehicle(new Vehicle("ABK123", polo		, "Black", 	2010, 995));	
		saveVehicle(new Vehicle("ABL123", polo		, "Red", 	2014, 995));	
		saveVehicle(new Vehicle("ABM123", fiesta_ST	, "Black", 	2013, 1005));		
		saveVehicle(new Vehicle("ABN123", fiesta_ST	, "Black", 	2012, 998));			
		saveVehicle(new Vehicle("ABO123", fiesta_ST	, "Red", 	2011, 998));			
		saveVehicle(new Vehicle("ABP123", fiesta_ST	, "White", 	2014, 998));			
		saveVehicle(new Vehicle("ABQ123", focus		, "White", 	2013, 1605));			
		saveVehicle(new Vehicle("ABR123", focus		, "White", 	2012, 1605));			
		saveVehicle(new Vehicle("ABS123", focus		, "Black", 	2011, 1605));			
		saveVehicle(new Vehicle("ABT123", mustang	, "Red", 	2014, 2300));			
		saveVehicle(new Vehicle("ABU123", mustang	, "Red", 	2013, 2300));			
		saveVehicle(new Vehicle("ABV123", mustang	, "Black", 	2013, 2300));			
		saveVehicle(new Vehicle("ABW123", hilux		, "Black", 	2012, 2200));			
		saveVehicle(new Vehicle("ABX123", hilux		, "White", 	2012, 2200));			
		saveVehicle(new Vehicle("ABY123", hilux		, "White", 	2011, 2400));		
		saveVehicle(new Vehicle("ABZ123", prius		, "Red", 	2014, 1800));			
		saveVehicle(new Vehicle("ABC124", prius		, "Red", 	2014, 1800));		
		saveVehicle(new Vehicle("ABC125", prius		, "Black", 	2013, 1600));			
		saveVehicle(new Vehicle("ABC126", landCrusier,"Black", 	2013, 2300));	
		saveVehicle(new Vehicle("ABC127", yarris 	, "White", 	2012, 1005));	
		saveVehicle(new Vehicle("ABC128", yarris 	, "Red", 	2012, 1005));		
		saveVehicle(new Vehicle("ABC129", yarris 	, "Black", 	2011, 1005));	
		saveVehicle(new Vehicle("ABC130", pria		, "Black", 	1975, 1500));				
		saveVehicle(new Vehicle("ABC133", tank		, "Green", 	2013, 60000));				
	}

}
