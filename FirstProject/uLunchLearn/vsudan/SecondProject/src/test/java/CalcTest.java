import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import com.svnevercise.Calc;


public class CalcTest {

	@Test
	public void addTest() throws Exception {
		
		Calc additionTest = new Calc();
		
		assertThat(additionTest.add(3, 6), is(9));
		assertEquals(additionTest.add(3, 6), 9);
	
	}
	@Test
	public void subTest() throws Exception {
		
		Calc additionTest = new Calc();
		
		assertThat(additionTest.subtract(6, 3), is(3));
		assertEquals(additionTest.subtract(7, 6), 1);
		
	}
}
