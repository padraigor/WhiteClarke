package org.porourke.carshop.model.vechicles;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestHTMLFormatter {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
}

	@Test
	public void testconvertSetOfTypeMakeToOptionsListForDropDownList_NoData() {
		Set<Make> s = new HashSet<Make>();
		String testedString = new HTMLFormatter().convertSetOfTypeMakeToOptionsListForDropDownList(s);
		String expectedResult = "";
		assertEquals(expectedResult,testedString);
	}
	
	@Test
	public void testconvertSetOfTypeMakeToOptionsListForDropDownList_WithData() {
		Set<Make> s = new HashSet<Make>(Arrays.asList(new Make(1, null, "Voltswagen"),
													  new Make(2, null, "Ford")));
		String testedString = new HTMLFormatter().convertSetOfTypeMakeToOptionsListForDropDownList(s);
		String expectedResult = "<Option value = \"1\">Voltswagen</Option><Option value = \"2\">Ford</Option>";
		assertEquals(expectedResult,testedString);
	}
	
	

}
