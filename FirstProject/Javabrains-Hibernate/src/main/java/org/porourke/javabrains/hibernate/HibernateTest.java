package org.porourke.javabrains.hibernate;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateTest {

	public static void main(String[] args) {
		UserDetails user = new UserDetails();
		user.setUserId(1);
		user.setUserName("First name");
		
		Address addr = new Address();
		addr.setStreet("Made up Street");
		addr.setCity("SpringField");
		addr.setState("TEXAS");
		addr.setState("TEX123");
		user.getListOfAddress().add(addr);
		
		Address addr2 = new Address();
		addr2.setStreet("Made down Street");
		addr2.setCity("SpringField");
		addr2.setState("TEXAS");
		addr2.setState("TEX123");
		user.getListOfAddress().add(addr2);
		
		Vehicle vehicle = new Vehicle();
		vehicle.setName("Ford");
		vehicle.setUser(user);
		user.getVehicle().add(vehicle);
		
		Vehicle vehicle2 = new Vehicle();
		vehicle2.setName("Mustang");
		vehicle.setUser(user);
		user.getVehicle().add(vehicle2);
		
		user.setJoinedDate(new Date());
		user.setDescrtiption("Description goes here");

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		session.save(user);
		session.save(vehicle);
		session.save(vehicle2);
		session.getTransaction().commit();
		session.close();
		
		//NewSession
		user = null;
		session = sessionFactory.openSession();
		session.beginTransaction();
		user = (UserDetails) session.get(UserDetails.class, 1);
		//vehicle = (Vehicle) session.get(UserDetails.class, user.getVehicle().getId());
		System.out.println();
		System.out.println("User Name retrieved is " + user.getUserName());
		//System.out.println("User Vehicle retrieved is " + vehicle.getName());
	}

}
