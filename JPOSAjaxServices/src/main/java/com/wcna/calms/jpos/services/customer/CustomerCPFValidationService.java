package com.wcna.calms.jpos.services.customer;

import java.util.Locale;
import java.util.Map;

import com.wcg.exception.SystemException;
import com.wcna.calms.jpos.services.application.IJPOSScreenConstants;
import com.wcna.calms.jpos.services.general.CheckBitValidation;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.validation.CustomValidationItem;
import com.wcna.calms.service.validation.CustomValidationResult;
import com.wcna.calms.service.validation.ICustomValidator;
import com.wcna.lang.StringUtil;

public class CustomerCPFValidationService implements ICustomValidator {

	private int validationType;
	private String cpfFieldName;

	public void setValidationType(int validationType) {
		this.validationType = validationType;
	}

	public CustomValidationResult validate(Map<String, Object> map,
			Locale locale) {

		if (validationType == 5) {
			return validate5(map, locale);
		}
		throw new SystemException("Invalid validation type " + validationType);

	}

	private CustomValidationResult validate5(Map<String, Object> map,
			Locale locale) {

		CustomValidationResult validationResult = new CustomValidationResult();
		String triggerAction = "";
		Object actionObj = map.get(IConstants.TR_ACTION_KEY);
		if (actionObj != null && !StringUtil.isEmpty(String.valueOf(actionObj))) {
			triggerAction = String.valueOf(actionObj);
		}

		if (!IJPOSScreenConstants.ACTION_SAVE_NO_VALIDATION
				.equals(triggerAction)) {

			String[] taxNumberPathParts = cpfFieldName.split("\\.");
			String taxNumberPathParent = taxNumberPathParts[0];
			String taxNumberPathObject = taxNumberPathParts[1];

			@SuppressWarnings("unchecked")
			Map<String, Object> taxNumberParent = (Map<String, Object>) map
					.get(taxNumberPathParent);

			String taxNumberToTest = (String) taxNumberParent
					.get(taxNumberPathObject);

			int[] firstCheckBitMultipliers = { 10, 9, 8, 7, 6, 5, 4, 3, 2 };
			int[] secondCheckBitMultipliers = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };

			char[] allChars = taxNumberToTest.toCharArray();

			int[] cpj = new int[11];
			int counter = 0;
			for (char c : allChars) {
				if (c != '.' && c != '-') {
					cpj[counter] = Integer.parseInt("" + c);
					counter++;
				}
			}

			int firstBit = CheckBitValidation.calculateCheckBitWithMultipliers(
					firstCheckBitMultipliers, cpj);
			int secondBit = CheckBitValidation
					.calculateCheckBitWithMultipliers(
							secondCheckBitMultipliers, cpj);

			int originalFirstBit = cpj[cpj.length - 2];
			int originalSecondBit = cpj[cpj.length - 1];

			if (firstBit != originalFirstBit || secondBit != originalSecondBit) {
				CustomValidationItem validationItem = new CustomValidationItem(
						IGenericScreenConstants.INVALID_FORMAT_ERROR_CODE,
						taxNumberPathObject, taxNumberPathParent);
				validationResult.addValidationItem(validationItem);
			}
		}

		return validationResult;
	}

	public void setCpfFieldName(String cpfFieldName) {
		this.cpfFieldName = cpfFieldName;
	}

	public String getCpfFieldName() {
		return cpfFieldName;
	}

}
