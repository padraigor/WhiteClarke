package org.porourke.springinaction.firstproject.ch11hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil{
	private static final SessionFactory sessionFactory = buildSessionFactory();
	Department.java
	Employee.java
	private static SessionFactory buildSessionFactory(){
		try{
			return new Configuration().configure().buildSessionFactory();
		} catch(Throwable ex){
			System.out.println("Initial SessionFactory creation failed" + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	
	public static void shutdown(){
		getSessionFactory().close();
	}
}