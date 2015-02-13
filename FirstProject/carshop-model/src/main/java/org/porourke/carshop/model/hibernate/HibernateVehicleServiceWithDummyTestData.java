package org.porourke.carshop.model.hibernate;

import org.hibernate.SessionFactory;

public class HibernateVehicleServiceWithDummyTestData extends HibernateVehicleService{

	public HibernateVehicleServiceWithDummyTestData(SessionFactory sessionFactory){
		super(sessionFactory);
		

	}
	
}
