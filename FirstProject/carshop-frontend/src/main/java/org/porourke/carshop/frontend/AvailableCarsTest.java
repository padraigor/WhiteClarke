package org.porourke.carshop.frontend;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AvailableCarsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void ping() {
		System.out.println("Selenium2 Test up and running");
			
		WebDriver driver = new FirefoxDriver();
	
		driver.get("http://localhost:8080/carshop-frontend/availableCars");
		
		String pageSource = driver.getPageSource();
		
		assertFalse(pageSource.contains("404"));
		
		driver.quit();
	}
	
	@Test
	public void testDropDownboxes() throws Exception {
		WebDriver driver = new FirefoxDriver();
		driver.get("http://localhost:8080/carshop-frontend/availableCars");
		
		
		//Select Ford
		WebElement select = driver.findElement(By.id("makes"));
	    List<WebElement> options = select.findElements(By.tagName("option"));
	    for (WebElement option : options) {
	        if(option.getText()!=null && "Ford".equals(option.getText()))
	        {	option.click();
	        	break;
	        }
	    }
	    
	    //Select Mustang 
	    select = driver.findElement(By.id("models"));
	    options = select.findElements(By.tagName("option"));
	    for (WebElement option : options) {
	        if(option.getText()!=null && "Focus".equals(option.getText()))
	        {   option.click();
	        	break;
	        }
	    }
	    //Check for Registration number from Dummy Data
	    String pageSource = driver.getPageSource();
	    System.out.println(pageSource);
	    
	    assertTrue(pageSource.contains("ABS123"));
	    driver.quit();
	}

}
