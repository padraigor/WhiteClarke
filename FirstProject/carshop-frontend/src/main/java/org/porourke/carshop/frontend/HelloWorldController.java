package org.porourke.carshop.frontend;
 
import java.util.ArrayList;
import java.util.List;

import org.porourke.carshop.model.VehiclesInterface;
import org.porourke.carshop.model.hibernate.Make;
import org.porourke.carshop.model.hibernate.Model;
import org.porourke.carshop.model.hibernate.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
@Transactional
public class HelloWorldController {
	String message = "Welcome to Spring MVC!";
	VehiclesInterface vehicleInterface;
	@Autowired
	public HelloWorldController(VehiclesInterface vehicleInterface) {
		super();
		this.vehicleInterface = vehicleInterface;
	}

	@RequestMapping("/hello")
	public ModelAndView showMessage(
		
		@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		System.out.println("in controller");
 
		ModelAndView mv = new ModelAndView("helloworld");
		mv.addObject("message", message);
		mv.addObject("name", name);
		return mv;
	}
	
	@RequestMapping("/JSONTestJSP")
	public ModelAndView showMessage2(@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		System.out.println("in controller for JSONTestJSP");
 
		ModelAndView mv = new ModelAndView("JSONTestJSP");
		return mv;
	}
	
	@RequestMapping("/availableCars")
	public ModelAndView availbleCars(
			@RequestParam(value = "selectedMakeId",  required = false, defaultValue = "1") String makeSelectedId,
			@RequestParam(value = "selectedModelId", required = false, defaultValue = "1") String modelSelectedId){
 
		
		ModelAndView mv = new ModelAndView("AvailableCars");
		
		//List of Makes
		mv.addObject("makes", new ArrayList<Make>(vehicleInterface.getMakes()));
		mv.addObject("lastSelectedMakeId",makeSelectedId);
		
		//List of Models
		int makeSelectedIdAsInt = Integer.parseInt(makeSelectedId); 
		
		System.out.println(makeSelectedIdAsInt);
		
		List<Model> modelsList = new ArrayList<Model>(vehicleInterface.getMakeById(makeSelectedIdAsInt).getModels());  
		mv.addObject("models", modelsList);
		mv.addObject("lastSelectedModelId",modelSelectedId);
	
		//List of Vehicles
		int modelSelectedIdAsInt = Integer.parseInt(modelSelectedId);
		List<Vehicle> vehicleList;
		if(modelSelectedIdAsInt>0){
			vehicleList = new ArrayList<Vehicle>(vehicleInterface.getModelById(modelSelectedIdAsInt).getVehicles());
		}
		else
		{	vehicleList = new ArrayList<Vehicle>(modelsList.get(0).getVehicles());
		}
		mv.addObject("vehicles",  vehicleList);
		
		return mv;
	}
	
	@RequestMapping("/testDOJO")
	public ModelAndView TestDOJO(){
		ModelAndView mv = new ModelAndView("TestDOJO");
		
		return mv;
	}
}
