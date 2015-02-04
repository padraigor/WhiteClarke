package org.porourke.carshop.model.hibernate;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ HibernameMakeTest.class,
				HibernateModelTest.class,
				HibernateVehicleTest.class,
				HibernateVehicleServiceTest.class
			  })
public class HibernateTestsSuite {

}
