package com.wcna.calms.jpos.services.general;

public class CheckBitValidation {
	public static int calculateCheckBitWithMultipliers(
			int[] multipliers, int[] intArray) {
		int checkBit = 0;
		int total = 0;
		for (int j = 0; j < multipliers.length; j++) {
			total += intArray[j] * multipliers[j];
		}
		checkBit = total % 11;
		if (checkBit<2){
			checkBit=0;
		}else{
			checkBit=11-checkBit;
		}
		return checkBit;
	}
}
