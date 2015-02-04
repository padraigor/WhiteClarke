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

public class CustomerContactValidationService implements ICustomValidator {

	private final Properties projectProperties;

	private int validationType;
	private String contactControlPrefix;

	public CustomerContactValidationService(Properties projectProperties) {
		this.projectProperties = projectProperties;
	}
	public void setValidationType(int validationType) {
		this.validationType = validationType;
	}
	public void setContactControlPrefix(String assetControlPrefix) {
		this.contactControlPrefix = assetControlPrefix;
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
			List<Map<String,String>> contactList = (List<Map<String,String>>) map.get(IGenericScreenConstants.APP_CUSTOMER_CONTACT_LIST);
			if(contactList != null && !contactList.isEmpty()) {

				Iterator<Map<String,String>> it = contactList.iterator();
				int counter = 0;
				while(it.hasNext()) {
					Map<String,String> contact = it.next();
					if(!isContactEmpty(contact)) {
						if(StringUtil.isEmpty(contact.get(IGenericScreenConstants.CUSTOMER_CONTACT_DEFAULT_FLAG_FIELD_NAME))) {
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.REQUIRED_ERROR_CODE,
									IGenericScreenConstants.CUSTOMER_CONTACT_DEFAULT_FLAG_FIELD_NAME,
									contactControlPrefix);
							validationResult.addValidationItem(validationItem);
						}
						if(StringUtil.isEmpty(contact.get(IGenericScreenConstants.QUOTE_CUSTOMER_NAME))) {
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.REQUIRED_ERROR_CODE,
									IGenericScreenConstants.QUOTE_CUSTOMER_NAME,
									contactControlPrefix);
							validationResult.addValidationItem(validationItem);
						}
						if(StringUtil.isEmpty(contact.get(IGenericScreenConstants.CUSTOMER_CONTACT_DEPARTMENT_FIELD_NAME))) {
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.REQUIRED_ERROR_CODE,
									IGenericScreenConstants.CUSTOMER_CONTACT_DEPARTMENT_FIELD_NAME,
									contactControlPrefix);
							validationResult.addValidationItem(validationItem);
						}
						if(StringUtil.isEmpty(contact.get(IGenericScreenConstants.CUSTOMER_CONTACT_JOB_POSITION_FIELD_NAME))) {
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.REQUIRED_ERROR_CODE,
									IGenericScreenConstants.CUSTOMER_CONTACT_JOB_POSITION_FIELD_NAME,
									contactControlPrefix);
							validationResult.addValidationItem(validationItem);
						}
						if(StringUtil.isEmpty(contact.get(IGenericScreenConstants.CUSTOMER_CONTACT_PREF_CONTACT_METHOD_FIELD_NAME))) {
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.REQUIRED_ERROR_CODE,
									IGenericScreenConstants.CUSTOMER_CONTACT_PREF_CONTACT_METHOD_FIELD_NAME,
									contactControlPrefix);
							validationResult.addValidationItem(validationItem);
						}
						if(StringUtil.isEmpty(contact.get(IGenericScreenConstants.CUSTOMER_CONTACT_EMAIL_FIELD_NAME))) {
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.REQUIRED_ERROR_CODE,
									IGenericScreenConstants.CUSTOMER_CONTACT_EMAIL_FIELD_NAME,
									contactControlPrefix);
							validationResult.addValidationItem(validationItem);
						}
					}
					counter++;
				}
			}
		}

		return validationResult;
	}

	private boolean isContactEmpty(Map<String,String> bank) {
		if(!StringUtil.isEmpty(bank.get(IGenericScreenConstants.CUSTOMER_CONTACT_DEFAULT_FLAG_FIELD_NAME))) {
			return false;
		}
		if(!StringUtil.isEmpty(bank.get(IGenericScreenConstants.QUOTE_CUSTOMER_NAME))) {
			return false;
		}
		if(!StringUtil.isEmpty(bank.get(IGenericScreenConstants.CUSTOMER_CONTACT_DEPARTMENT_FIELD_NAME))) {
			return false;
		}
		if(!StringUtil.isEmpty(bank.get(IGenericScreenConstants.CUSTOMER_CONTACT_JOB_POSITION_FIELD_NAME))) {
			return false;
		}
		if(!StringUtil.isEmpty(bank.get(IGenericScreenConstants.CUSTOMER_CONTACT_PREF_CONTACT_METHOD_FIELD_NAME))) {
			return false;
		}
		if(!StringUtil.isEmpty(bank.get(IGenericScreenConstants.CUSTOMER_CONTACT_EMAIL_FIELD_NAME))) {
			return false;
		}

		return true;
	}

}
