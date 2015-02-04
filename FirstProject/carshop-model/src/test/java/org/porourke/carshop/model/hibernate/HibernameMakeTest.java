package org.porourke.carshop.model.hibernate;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Set;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class HibernameMakeTest {

	@Test
	public void testEmptyConstructor() {
		Make make = new Make();

		assertEquals(-1, make.getId());

		// System.out.println((new
		// ArrayList<ModelInterface>()).getClass().toString());
		// System.out.println(make.getModels().getClass().toString());

		assertEquals((new ArrayList<Model>()).getClass(), make
				.getModels().getClass());
		assertEquals("", make.getName());
	}

	@Test
	public void testConstructorWithoutID() {
		Make make = new Make(new HashSet<Model>(), "TestMake");

		assertEquals(-1, make.getId());
		assertEquals((new HashSet<Model>()).getClass(), make.getModels().getClass());
		assertEquals("TestMake", make.getName());
	}

	@Test
	public void testFullConstructorID() {
		Make make = new Make(100, new HashSet<Model>(), "TestMake");

		assertEquals(100, make.getId());
		assertEquals((new HashSet<Model>()).getClass(), make.getModels().getClass());
		assertEquals("TestMake", make.getName());
	}

	@Test
	public void testSetAndGetID() {
		Make make = new Make(100, new HashSet<Model>(), "TestMake");
		make.setId(200);
		assertEquals(200, make.getId());
	}

	@Test
	public void testSetAndGetNames() {
		Make make = new Make(100, new HashSet<Model>(), "TestMake");
		make.setName("TestName");
		assertEquals("TestName", make.getName());
	}

	@Test
	public void testAddModel() {
		Make make = new Make();
		Model model = new Model(null, make, "TestModel");

		make.addModel(model);
		Model model2 = make.getModels().toArray(new Model[make.getModels().size()])[0];

		// Check Model added to this make
		assertEquals(model, model2);
		// Check model.make = make
		assertEquals(make, model2.getMake());
	}

	@Test
	public void testSetAndGetModels() {
		Make make = new Make(100, new HashSet<Model>(), "TestMake");
		Model model = new Model(null, make, "TestModel");

		ArrayList<Model> modelList = new ArrayList<Model>();
		modelList.add(model);

		make.setModels(modelList);

		// Check Model Added
		assertEquals(model, make.getModels().toArray(new Model[make.getModels().size()])[0]);
		// Check model.make = make
		assertEquals(make,make.getModels().toArray(new Model[make.getModels().size()])[0].getMake());
	}

	@Test
	public void testFullContructorWithModelList() {
		Model model = new Model(null, null, "TestModel");
		ArrayList<Model> modelList = new ArrayList<Model>();
		modelList.add(model);

		Make make = new Make(100, modelList, "TestMake");

		// Check to make sure Model from modelList is added
		assertEquals("TestModel",make.getModels().toArray(new Model[1])[0].getName());
		// Check model from list has mode.make = make
		assertEquals("TestMake", make.getModels().toArray(new Model[1])[0].getMake().getName());
	}

	@Test
	public void testHibernate() {

		Make make = new Make(100, null, "TestMake");

		// Save all to Database
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// session.save(model);
		session.save(make);
		session.getTransaction().commit();
		session.close();

		// Check Results from Database
		session = sessionFactory.openSession();
		Make makeResult = (Make) session.get(Make.class, 1l);
		// System.out.println("Here " + makeResult.toString());
		assertEquals("TestMake", makeResult.getName());
	}

	// Test that saves and loads all makes
	// Test that saves and loads makes with models and vehicles
}
