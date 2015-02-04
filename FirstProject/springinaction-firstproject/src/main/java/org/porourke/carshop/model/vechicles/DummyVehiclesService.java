package org.porourke.carshop.model.vechicles;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.porourke.carshop.model.MakeInterface;
import org.porourke.carshop.model.Modelnterface;
import org.porourke.carshop.model.VechicleInterface;
import org.porourke.carshop.model.VechiclesInterface;

public class DummyVehiclesService implements VechiclesInterface {
	protected Map<Integer,Vehicle> vehicles;  //Actual List
	protected Map<Integer,Model> models;		//For ids only
	protected Map<Integer,Make> makes;		//For ids only
	
	
	public DummyVehiclesService(){	
		vehicles = new HashMap<Integer,Vehicle>();
		models =   new HashMap<Integer,Model>();
		makes =   new HashMap<Integer,Make>();
	}
	
	public DummyVehiclesService(HashMap<Integer,Vehicle> vehicles){	
		this.vehicles = vehicles;
	}

	public Set<Make> getMakes() {
		return new HashSet<Make>(makes.values());
	}

	public MakeInterface getMakeById(int id) {
		return makes.get(id);
	}

	public Modelnterface getModelById(int id) {
		return models.get(id);
	}

	public VechicleInterface getVehicleById(int id) {
		
		return vehicles.get(id);
	}

	public void saveVehicle(Vehicle vehicle) {
		if(!vehicles.containsValue(vehicle)){
			int id = findNextID(vehicles.keySet());
			vehicle.setId(id);
			vehicles.put(id, vehicle);
		}
		Model model = vehicle.getModel();
		if(!models.containsValue(model)){	
			int id = findNextID(models.keySet());
			model.setId(id);
			models.put(id, model);
		}
		Make make = model.getMake();
		if(!makes.containsValue(make)){	
			int id = findNextID(makes.keySet());
			make.setId(id);
			makes.put(id, make);
		}
	}
	
	private int findNextID(Set<Integer> ids){	
		int max = 0;
		for(Integer id:ids){	
			if(id>max){
				max = id;
			}
		}
		return max + 1;
	}
	
	
}

