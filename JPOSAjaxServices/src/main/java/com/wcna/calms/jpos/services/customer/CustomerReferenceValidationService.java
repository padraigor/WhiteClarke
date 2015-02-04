package com.wcna.calms.jpos.services.customer;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import com.wcg.exception.SystemException;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.validation.CustomValidationItem;
import com.wcna.calms.service.validation.CustomValidationResult;
import com.wcna.calms.service.validation.ICustomValidator;
import com.wcna.lang.StringUtil;

public class CustomerReferenceValidationService implements ICustomValidator {

	private final Properties projectProperties;

	private int validationType;
	private String refControlPrefix;

	public CustomerReferenceValidationService(Properties projectProperties) {
		this.projectProperties = projectProperties;
	}
	public void setValidationType(int validationType) {
		this.validationType = validationType;
	}
	public void setRefControlPrefix(String assetControlPrefix) {
		this.refControlPrefix = assetControlPrefix;
	}

	public CustomValidationResult validate(Map<String, Object> map,
			Locale locale) {
		if(validationType == 5) {
			return validate5(map, locale);
		}
		throw new SystemException("Invalid validation type " + validationType);
	}

	public CustomValidationResult validate5(Map<String, Object> map, Locale locale) {

		CustomValidationResult validationResult = new CustomValidationResult();
		String triggerAction = "";
		Object actionObj = map.get(IConstants.TR_ACTION_KEY);
		if (actionObj != null && !StringUtil.isEmpty(String.valueOf(actionObj))) {
			triggerAction = String.valueOf(actionObj);
		}
		if (!"SAVE_NOV".equals(triggerAction)) {
			List<Map<String,String>> referenceList = (List<Map<String,String>>) map.get(IGenericScreenConstants.APP_CUSTOMER_REFERENCE_LIST);
			if(referenceList != null && !referenceList.isEmpty()) {

				Iterator<Map<String,String>> it = referenceList.iterator();
				int counter = 0;
				while(it.hasNext()) {
					Map<String,String> ref = it.next();
					if(!isReferenceEmpty(ref)) {
						if(StringUtil.isEmpty(ref.get(IGenericScreenConstants.QUOTE_CUSTOMER_NAME))) {
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.REQUIRED_ERROR_CODE,
									IGenericScreenConstants.QUOTE_CUSTOMER_NAME,
									refControlPrefix);
							validationResult.addValidationItem(validationItem);
						}
						if(StringUtil.isEmpty(ref.get(IGenericScreenConstants.APP_CUSTOMER_REFERENCES_PHONE_FIELD_NAME))) {
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.REQUIRED_ERROR_CODE,
									IGenericScreenConstants.APP_CUSTOMER_REFERENCES_PHONE_FIELD_NAME,
									refControlPrefix);
							validationResult.addValidationItem(validationItem);
						}
						if(StringUtil.isEmpty(ref.get(IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME))) {
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.REQUIRED_ERROR_CODE,
									IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME,
									refControlPrefix);
							validationResult.addValidationItem(validationItem);
						}
						if(StringUtil.isEmpty(ref.get(IGenericScreenConstants.RESIDENCE_STREET_NAME_FIELD_NAME))) {
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.REQUIRED_ERROR_CODE,
									IGenericScreenConstants.RESIDENCE_STREET_NAME_FIELD_NAME,
									refControlPrefix);
							validationResult.addValidationItem(validationItem);
						}
						if(StringUtil.isEmpty(ref.get(IGenericScreenConstants.RESIDENCE_STREET_NUMBER_FIELD_NAME))) {
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.REQUIRED_ERROR_CODE,
									IGenericScreenConstants.RESIDENCE_STREET_NUMBER_FIELD_NAME,
									refControlPrefix);
							validationResult.addValidationItem(validationItem);
						}
						if(StringUtil.isEmpty(ref.get(IGenericScreenConstants.RESIDENCE_CITY_FIELD_NAME))) {
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.REQUIRED_ERROR_CODE,
									IGenericScreenConstants.RESIDENCE_CITY_FIELD_NAME,
									refControlPrefix);
							validationResult.addValidationItem(validationItem);
						}
						if(StringUtil.isEmpty(ref.get(IGenericScreenConstants.RESIDENCE_COUNTY_FIELD_NAME))) {
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.REQUIRED_ERROR_CODE,
									IGenericScreenConstants.RESIDENCE_COUNTY_FIELD_NAME,
									refControlPrefix);
							validationResult.addValidationItem(validationItem);
						}
					}
					counter++;
				}
			}
		}

		return validationResult;
	}

	private boolean isReferenceEmpty(Map<String,String> ref) {
		if(!StringUtil.isEmpty(ref.get(IGenericScreenConstants.QUOTE_CUSTOMER_NAME))) {
			return false;
		}
		if(!StringUtil.isEmpty(ref.get(IGenericScreenConstants.APP_CUSTOMER_REFERENCES_PHONE_FIELD_NAME))) {
			return false;
		}
		if(!StringUtil.isEmpty(ref.get(IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME))) {
			return false;
		}
		if(!StringUtil.isEmpty(ref.get(IGenericScreenConstants.RESIDENCE_STREET_NAME_FIELD_NAME))) {
			return false;
		}
		if(!StringUtil.isEmpty(ref.get(IGenericScreenConstants.RESIDENCE_STREET_NUMBER_FIELD_NAME))) {
			return false;
		}
		if(!StringUtil.isEmpty(ref.get(IGenericScreenConstants.RESIDENCE_CITY_FIELD_NAME))) {
			return false;
		}
		if(!StringUtil.isEmpty(ref.get(IGenericScreenConstants.RESIDENCE_COUNTY_FIELD_NAME))) {
			return false;
		}

		return true;
	}

}
