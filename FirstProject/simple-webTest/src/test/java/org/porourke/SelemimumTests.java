package org.porourke;

import static org.junit.Assert.*;

import java.io.PrintWriter;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sonatype.mavenbook.weather.WeatherService;

public class SelemimumTests 
{	@Test
	public void PingTest() throws Exception {
		System.out.println("Selenium2 Test up and running");
		
		WebDriver driver = new FirefoxDriver();
	
		driver.get("http://localhost:8080/simple-webapp/weather?zip=01201");
		
		String pageSource = driver.getPageSource();
		System.out.println(pageSource);
		
		assertTrue(pageSource.contains("Current Weather Conditions for:"));
		
		//WebElement element = driver.findElement(By.name("q"));
		//element.sendKeys("Cheese!");
		//element.submit();
		//System.out.println("Page title is: " + driver.getTitle());
		
		//(new WebDriverWait(driver,10)).until(new ExpectedCondition<Boolean>() {
		//	public Boolean apply(WebDriver d){
		//		return d.getTitle().toLowerCase().startsWith("cheese!");
		//	}
		//});
		//System.out.println("page title is: " + driver.getTitle());
		driver.quit();
	}

	public void testAgainstYahooSource() throws Exception {
		WebDriver driver = new FirefoxDriver();
		driver.get("http://localhost:8080/simple-webapp/weather?zip=01201");
		String pageSource = driver.getPageSource();
		System.out.println(pageSource);
		driver.quit();
		
		//WeatherService weatherService = new WeatherService();
		//PrintWriter out = response.getWriter();
	    //try {
	    //	out.println( weatherService.retrieveForecast( zip ) );
	    //} catch( Exception e ) {
	    //	out.println( "Error Retrieving Forecast: " + e.getMessage() );
	    //}
	}

}
