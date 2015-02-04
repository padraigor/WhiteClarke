package com.wcna.calms.jpos.services.customer;

import java.util.Locale;
import java.util.Map;

import com.wcg.exception.SystemException;
import com.wcna.calms.jpos.services.general.CheckBitValidation;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.validation.CustomValidationItem;
import com.wcna.calms.service.validation.CustomValidationResult;
import com.wcna.calms.service.validation.ICustomValidator;

public class CustomerRegistrationCodeValidationService implements ICustomValidator {

	private int validationType;
	private String taxFieldPath;



	public void setValidationType(int validationType) {
		this.validationType = validationType;
	}

	public CustomValidationResult validate(Map<String, Object> map,
			Locale locale) {

		if (validationType == 5){
			return validate5(map, locale);
		}
		throw new SystemException("Invalid validation type " + validationType);

	}

	private CustomValidationResult validate5(Map<String, Object> map,
			Locale locale) {

		String[] taxNumberPathParts = taxFieldPath.split("\\.");
		String taxNumberPathParent = taxNumberPathParts[0];
		String taxNumberPathObject = taxNumberPathParts[1];

		@SuppressWarnings("unchecked")
		Map<String,Object> taxNumberParent = (Map<String, Object>)map.get(taxNumberPathParent);

		String taxNumberToTest = (String) taxNumberParent.get(taxNumberPathObject);

		int[] firstCheckBitMultipliers = {5,4,3,2,9,8,7,6,5,4,3,2};
		int[] secondCheckBitMultipliers = {6,5,4,3,2,9,8,7,6,5,4,3,2};

		char[] allChars = taxNumberToTest.toCharArray();

		int[] cnpjInts = new int[14];
		int counter = 0;
		for (char c : allChars) {
			if (c!='.'&&c!='-'&&c!='/'){
				cnpjInts[counter] = Integer.parseInt(""+c);
				counter++;
			}
		}

		int firstBit = CheckBitValidation.calculateCheckBitWithMultipliers(firstCheckBitMultipliers, cnpjInts);
		int secondBit = CheckBitValidation.calculateCheckBitWithMultipliers(secondCheckBitMultipliers, cnpjInts);

		int originalFirstBit = cnpjInts[cnpjInts.length-2];
		int originalSecondBit = cnpjInts[cnpjInts.length-1];
		CustomValidationResult validationResult = new CustomValidationResult();

		if (firstBit != originalFirstBit || secondBit != originalSecondBit){
			CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.INVALID_FORMAT_ERROR_CODE, taxNumberPathObject, taxNumberPathParent);
			validationResult.addValidationItem(validationItem);
		}

		return validationResult;
	}

	public void setTaxFieldPath(String taxFieldPath) {
		this.taxFieldPath = taxFieldPath;
	}

	public String getTaxFieldPath() {
		return taxFieldPath;
	}



}
