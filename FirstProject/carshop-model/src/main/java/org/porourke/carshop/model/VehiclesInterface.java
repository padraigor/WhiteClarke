package org.porourke.carshop.model;

import java.util.Collection;

import org.porourke.carshop.model.hibernate.Make;
import org.porourke.carshop.model.hibernate.Model;
import org.porourke.carshop.model.hibernate.Vehicle;



public interface VehiclesInterface {	
	public Collection<Make> getMakes();
	public Make getMakeById(long id);
	public Model getModelById(long id);
	public Vehicle getVehicleById(long id);
	public void saveVehicle(Vehicle Vehicle);
}
