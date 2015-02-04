package com.wcg.assessment.graduate;

import junit.framework.TestCase;

public class PrimeCalculatorTest
    extends TestCase
{

    public void testIsItPrime()
    {
        assertEquals("I don't know!", new PrimeCalculator().isItPrime("from the branch"));
    }
}
