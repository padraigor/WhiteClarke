package com.porourke;

import java.util.HashSet;
import java.util.Set;

public class CaseInsensitiveCountCharacters implements CountCharacters {

	public int count(String string) {
		
		Set<Character> uniqueCharacters = new HashSet<Character>();
		String upperCaseString = string.toUpperCase();
		for (Character character : upperCaseString.toCharArray()) {
			uniqueCharacters.add(character);
		}
		
		return uniqueCharacters.size();
	}

}
