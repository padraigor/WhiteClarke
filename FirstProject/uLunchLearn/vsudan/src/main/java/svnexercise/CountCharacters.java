package svnexercise;

public class CountCharacters {

	public int totalDifferentCharacters(String word)
	{
		int total = 0;
		String lowerCaseWord = word.toLowerCase();
		
		for(int i=0;i<lowerCaseWord.length();i++)
			
		{
			if(i==0)
			{
				total = 1;
			}
			else
			{
				
				if(lowerCaseWord.substring(0, i).contains(lowerCaseWord.charAt(i)+ ""))
					continue;
			
				else
				++total;
			}
			
		}
		
		return total;
		
		
	}
	
}
