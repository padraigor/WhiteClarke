package com.wcg.assessment.graduate;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
    	if(args.length == 0) {
    		System.out.println("Exercise: When the app is run the output " +
    				"should tell us if the entered number argument is a prime number.");
    	} else {
    		System.out.println( new PrimeCalculator().isItPrime(args[0]) );
    	}
    }
}
