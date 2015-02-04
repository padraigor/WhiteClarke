package svnexercise;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class CountCharactersTest {

	@Test
	public void countCharacters() throws Exception {
		CountCharacters c = new CountCharacters();
		
		assertThat(c.totalDifferentCharacters("Sanfrancisco"), is(8));
		
	}
	
	@Test
	public void countCharactersAgain() throws Exception {
		CountCharacters c = new CountCharacters();
		
		assertThat(c.totalDifferentCharacters("Influence"), is(7));
		
	}
	
	@Test
	public void countCharactersOnceAgain() throws Exception {
		CountCharacters c = new CountCharacters();
		
		assertThat(c.totalDifferentCharacters("Camilla"), is(5));
		
	}
	
	@Test
	public void countCharactersAgainAndAgain() throws Exception {
		CountCharacters c = new CountCharacters();
		
		assertThat(c.totalDifferentCharacters("K@@TE"), is(4));
	}
}
