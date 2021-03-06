package org.porourke.carshop.frontend;

import java.util.ArrayList;
import java.util.List;

import org.porourke.carshop.model.hibernate.Make;
import org.porourke.carshop.model.hibernate.Model;
import org.porourke.carshop.model.hibernate.Vehicle;

public class AvailableCarsResponse {

	private ArrayList<Make> makes;
	private String makeSelectedId;
	private List<Model> models;
	private String modelSelectedId;
	private List<Vehicle> vehicles;

	public void setMakes(ArrayList<Make> makes) {
		this.makes = makes;
	}

	public void setModels(List<Model> models) {
		this.models = models;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public String getMakeSelectedId() {
		return makeSelectedId;
	}

	public void setMakeSelectedId(String makeSelectedId) {
		this.makeSelectedId = makeSelectedId;
	}

	public String getModelSelectedId() {
		return modelSelectedId;
	}

	public void setModelSelectedId(String modelSelectedId) {
		this.modelSelectedId = modelSelectedId;
	}



	public ArrayList<Make> getMakes() {
		return makes;
	}

	public List<Model> getModels() {
		return models;
	}

}
