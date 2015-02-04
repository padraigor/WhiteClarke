package com.wcna.calms.jpos.services.customer;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import com.ibm.icu.util.Calendar;
import com.wcna.calms.jpos.services.application.IJPOSScreenConstants;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.calms.service.validation.CustomValidationItem;
import com.wcna.calms.service.validation.CustomValidationResult;
import com.wcna.calms.service.validation.ICustomValidator;
import com.wcna.calms.service.validation.IRegexValidationService;
import com.wcna.lang.StringUtil;


public class CustomerDateOfBirthValidationService implements ICustomValidator {

	private final IRegexValidationService regexValidationService;
	private IFormatService formatService;

	public CustomerDateOfBirthValidationService(
			IRegexValidationService regexValidationService,
			IFormatService formatService) {
		this.regexValidationService = regexValidationService;
		this.formatService = formatService;
	}
	public CustomValidationResult validate(Map<String, Object> map,Locale locale) {
		CustomValidationResult validationResult = new CustomValidationResult();
		String triggerAction = "";
		Object actionObj = map.get(IConstants.TR_ACTION_KEY);
		if (actionObj != null && !StringUtil.isEmpty(String.valueOf(actionObj))) {
			triggerAction = String.valueOf(actionObj);
		}
		if (IJPOSScreenConstants.ACTION_SAVE_NO_VALIDATION.equals(triggerAction)) {
			return validationResult;
		}
		Map<String, String> applicationCustomer = (Map<String, String>) map.get(IGenericScreenConstants.APP_CONSUMER_CUSTOMER);
		if(applicationCustomer !=null){
			String dateOfBirth =  (String) applicationCustomer.get(IGenericScreenConstants.APP_CUSTOMER_DOB_FIELD_NAME);

			if (StringUtil.isEmpty(dateOfBirth)) {
				CustomValidationItem validationItem = new CustomValidationItem(
						IGenericScreenConstants.REQUIRED_ERROR_CODE, IGenericScreenConstants.APP_CUSTOMER_DOB_FIELD_NAME,
						IGenericScreenConstants.APP_CONSUMER_CUSTOMER);
				validationResult.addValidationItem(validationItem);
				return validationResult;
			}
			dateBeforeCurrent(dateOfBirth, validationResult, locale);

		}
		return validationResult;
	}
	private void dateBeforeCurrent(String dateOfBirth,CustomValidationResult validationResult,Locale locale){

		Date birthDate = null;
		try{
		birthDate = formatService.parseDate(dateOfBirth, locale, true, null);
		}catch (ParseException e) {
			CustomValidationItem validationItem = new CustomValidationItem(
					IGenericScreenConstants.INCORRECT_ERROR_CODE, IGenericScreenConstants.APP_CUSTOMER_DOB_FIELD_NAME,
					IGenericScreenConstants.APP_CONSUMER_CUSTOMER);
			validationResult.addValidationItem(validationItem);
		}
		Date now = DateUtils.truncate(new Date(), Calendar.DATE);
		if(birthDate.after(now)){
			CustomValidationItem validationItem = new CustomValidationItem(
					IGenericScreenConstants.INCORRECT_ERROR_CODE, IGenericScreenConstants.APP_CUSTOMER_DOB_FIELD_NAME,
					IGenericScreenConstants.APP_CONSUMER_CUSTOMER);
			validationResult.addValidationItem(validationItem);
		}
	}
}