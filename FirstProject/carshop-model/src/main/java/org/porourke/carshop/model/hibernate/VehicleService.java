package org.porourke.carshop.model.hibernate;

import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.porourke.carshop.model.VehiclesInterface;

//Expand this class to handle exceptions 
public class VehicleService implements VehiclesInterface{

	private SessionFactory sessionFactory;
	private Session session;
	
	public VehicleService(SessionFactory sessionFactory){
		sessionFactory = new Configuration().configure().buildSessionFactory();
		session = sessionFactory.openSession();
	}
	
	public void 
	destroy(){
		session.close();
	}
	
	public Collection<Make> getMakes() {
		String hql = "FROM Make";
		org.hibernate.Query query = session.createQuery(hql);
		List makes = query.list();
		return query.list();
	}

	public Make getMakeById(long id) {
		return (Make) session.get(Make.class, id);
	}

	public Model getModelById(long id) {
		return (Model) session.get(Model.class, id);
	}

	public Vehicle getVehicleById(long id) {
		return (Vehicle) session.get(Vehicle.class, id);
	}
	
	public void saveMake(Make make){
		session.save(make);
	}
	
	public void saveModel(Model model){
		session.save(model);
	}

	public void saveVehicle(Vehicle vehicle) {
		session.save(vehicle);
	}


}
