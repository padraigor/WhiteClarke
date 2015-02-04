package com.porourke;

import static org.junit.Assert.*;

import org.junit.Test;

public class CaseInsensitiveCountCharactersTest {

	@Test
	public void countSimpleString() 
	{
		CaseInsensitiveCountCharacters countCharactersCaseInsensitive = new CaseInsensitiveCountCharacters();
		assertEquals(countCharactersCaseInsensitive.count("abc"), 3);
	}
	
	@Test
	public void countWithDuplicateLetters() throws Exception {
		CaseInsensitiveCountCharacters countCharactersCaseInsensitive = new CaseInsensitiveCountCharacters();
		assertEquals(3, countCharactersCaseInsensitive.count("abca"));
	}
	
	@Test
	public void countWithDuplicateCaseInsensitiveLetters() throws Exception {
		CaseInsensitiveCountCharacters countCharactersCaseInsensitive = new CaseInsensitiveCountCharacters();
		assertEquals(3, countCharactersCaseInsensitive.count("Abca"));
	}
	
	@Test
	public void testCaseInsensitivityInJava() throws Exception {
		assertEquals("A", "a".toUpperCase());
		assertTrue("A".compareToIgnoreCase("a") == 0);
		
	}
	
	@Test
	public void quickTest() throws Exception {
		assertEquals("ABCD".substring(1,2) , "B" );
		
	}
	
	

}
