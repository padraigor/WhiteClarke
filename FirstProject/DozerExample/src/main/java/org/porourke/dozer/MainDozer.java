package org.porourke.dozer;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;


public class MainDozer {
	public static void main(String args[]){
		List<String> list = new ArrayList<String>();
		//Add the mapping configuration
		list.add("file://localhost/C:\\Projects\\workspace\\FirstProject\\DozerExample\\src\\main\\java\\org\\porourke\\dozer\\HibernateDozerMapping.xml");
		
		Employee emp= new Employee();
		emp.setEmpID("54601");		
		emp.setStreetNumber("7G");
		emp.setState("Maharasthra");
		emp.setCity("Pune");
		emp.setCountry("India");
		
		Address address = new Address();
		Mapper mapper = new org.dozer.DozerBeanMapper(list);
		mapper.map(emp,address);
		
		System.out.println("StreetNumber" + address.getStreetNumber());
		System.out.println("State" + address.getState());
		System.out.println("City" + address.getCity());
		System.out.println("Country" + address.getCountry());
	}

}
