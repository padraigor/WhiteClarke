package org.porourke.carshop.model.hibernate;

import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.porourke.carshop.model.VehiclesInterface;

//Expand this class to handle exceptions 

public class HibernateVehicleService implements VehiclesInterface{

	private SessionFactory sessionFactory;
	
	public HibernateVehicleService(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory; 
		
	}
	
	public void destroy(){
	}
	
	public Collection<Make> getMakes() {
		String hql = "FROM Make";
		Session session = getSession();
		org.hibernate.Query query = session.createQuery(hql);
		List makes = query.list();
		return makes;
	}

	public Make getMakeById(long id) {
		Session session = getSession();
		Make make = (Make) session.get(Make.class, id);
		return make;
	}

	public Model getModelById(long id) {
		Session session = getSession();
		Model model = (Model) session.get(Model.class, id);
		return model;
	}

	public Vehicle getVehicleById(long id) {
		Vehicle vehicle = (Vehicle) getSession().get(Vehicle.class, id);
		return vehicle;
	}
	
	public void saveMake(Make make){
		getSession().save(make);
	}
	
	public void saveModel(Model model){
		getSession().save(model);
	}

	public void saveVehicle(Vehicle vehicle) {
		getSession().save(vehicle.getModel().getMake());
		getSession().save(vehicle.getModel());
		getSession().save(vehicle);
	}

	


	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}
