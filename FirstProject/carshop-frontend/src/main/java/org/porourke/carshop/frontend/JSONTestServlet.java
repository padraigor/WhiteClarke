package org.porourke.carshop.frontend;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.porourke.carshop.model.VehiclesInterface;
import org.porourke.carshop.model.hibernate.HibernateVehicleServiceWithDummyTestData;
import org.springframework.transaction.annotation.Transactional;

public class JSONTestServlet extends HttpServlet {
	private static final long serialVersionUID = 643444838677548L;
	private VehiclesInterface vehicles = new HibernateVehicleServiceWithDummyTestData(new Configuration().configure().buildSessionFactory());
	
	
    @Transactional
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
    	
    	//VehiclesInterface vehs = new HibernateVehicleServiceWithDummyTestData(new SessionFactory())
    	
	    System.out.println("In JSONTest.java");
		PrintWriter out = response.getWriter();
		
		out.println( "{ " + "" + " }");
		
		out.flush();
		out.close();
    }
    
   
}