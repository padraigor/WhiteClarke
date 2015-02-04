/*
 * Copyright (c) 2012 White Clarke Group. All rights reserved.
 */

package com.wcna.calms.jpos.services.customer;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.wcna.calms.jpos.services.application.IJPOSScreenConstants;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.validation.CustomValidationItem;
import com.wcna.calms.service.validation.CustomValidationResult;
import com.wcna.calms.service.validation.ICustomValidator;
import com.wcna.calms.web.IConstantsWeb;

/**
 * Telephone validation. Currently validates that at most one home number is provided.
 *
 * TODO: make this more generic by passing in restrictions as configuration.
 *
 * @author Rhys Parsons
 */
public class CustomerTelephoneValidator implements ICustomValidator {

	/**
	 * Validate that there is at most one 'home' number.
	 */
	public CustomValidationResult validate(Map<String, Object> args, Locale locale) {

		CustomValidationResult result = new CustomValidationResult();

		String triggerAction = (String) args.get(IConstants.TR_ACTION_KEY);

		if (!IJPOSScreenConstants.ACTION_SAVE_NO_VALIDATION.equals(triggerAction)) {

			@SuppressWarnings("unchecked")
			List<Map<String,String>> phoneList = (List<Map<String,String>>) args.get(IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST);

			processHomeNumbers(phoneList, result);
		}

		return result;
	}

	private int processHomeNumbers(List<Map<String,String>> phoneList, CustomValidationResult result) {

		int homeCount = 0;
		int index     = 0;

		for (Map<String,String> phone : phoneList) {
			String phoneType = phone.get(IGenericScreenConstants.APP_CUSTOMER_PHONE_TYPE_FIELD_NAME);

			if (IConstantsWeb.HOME_PHONE.equals(phoneType)) {
				homeCount++;
			}

			if (homeCount > 1) {

				CustomValidationItem validation;

				validation = new CustomValidationItem(IGenericScreenConstants.TOO_MANY_HOME_PHONE_NUMBERS_ERROR_CODE,
						                              IGenericScreenConstants.APP_CUSTOMER_PHONE_TYPE_FIELD_NAME,
						                              IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST);
				validation.setIndex(index);

				result.addValidationItem(validation);
			}

			index++;
		}

		return homeCount;
	}
}
