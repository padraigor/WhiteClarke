package com.wcna.calms.jpos.services.customer;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.wcg.exception.SystemException;
import com.wcna.calms.jpos.services.application.IJPOSScreenConstants;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.validation.CustomValidationItem;
import com.wcna.calms.service.validation.CustomValidationResult;
import com.wcna.calms.service.validation.ICustomValidator;
import com.wcna.lang.StringUtil;

public class CustomerCompanyAddressValidationService implements ICustomValidator {

	private final Properties projectProperties;

	private int validationType;
	public CustomerCompanyAddressValidationService(Properties projectProperties) {
		this.projectProperties = projectProperties;
	}
	public void setValidationType(int validationType) {
		this.validationType = validationType;
	}

	public CustomValidationResult validate(Map<String, Object> map,	Locale locale) {
		if (validationType == 3) {
			return validateAddress(map, locale);
		}
		throw new SystemException("Invalid validation type " + validationType);
	}


	private CustomValidationResult validateAddress(
			Map<String, Object> map,Locale locale) {
		CustomValidationResult validationResult = new CustomValidationResult();
		Map<String, String> address = (Map<String, String>) map.get(IGenericScreenConstants.APPLICATION_BILLING_ADDRESS);
		if((address!=null) && (!isAddressEmpty(address))){
			if(StringUtils.isEmpty(address
					.get(IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME))){
				CustomValidationItem validationItem = new CustomValidationItem(
						IGenericScreenConstants.REQUIRED_ERROR_CODE,
						IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME,
						IGenericScreenConstants.APPLICATION_BILLING_ADDRESS);
				validationResult.addValidationItem(validationItem);
			}
			if ((StringUtils
					.isEmpty(address
							.get(IGenericScreenConstants.RESIDENCE_SUITE_NUMBER_FIELD_NAME)))
					&& (StringUtils
							.isEmpty(address
									.get(IGenericScreenConstants.RESIDENCE_STREET_NUMBER_FIELD_NAME)))
					&& (StringUtils
							.isEmpty(address
									.get(IGenericScreenConstants.APP_ADDRESS_LINE3)))) {
				CustomValidationItem validationItem = new CustomValidationItem(
						IGenericScreenConstants.REQUIRED_COMPANY_ADDRESS_ERROR_CODE,
						"",
						IGenericScreenConstants.APPLICATION_BILLING_ADDRESS);
				validationResult.addValidationItem(validationItem);
			}
		}
		return validationResult;
	}

	private boolean isAddressEmpty(Map<String, String> previousAddress){
		if((StringUtils.isEmpty(previousAddress.get(IGenericScreenConstants.RESIDENCE_STREET_NAME_FIELD_NAME))) &&
				(StringUtils.isEmpty(previousAddress.get(IGenericScreenConstants.RESIDENCE_STREET_NUMBER_FIELD_NAME))) &&
				(StringUtils.isEmpty(previousAddress.get(IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME))) &&
				(StringUtils.isEmpty(previousAddress.get(IGenericScreenConstants.RESIDENCE_COUNTRY_FIELD_NAME))) &&
				(StringUtils.isEmpty(previousAddress.get(IGenericScreenConstants.RESIDENCE_SUITE_NUMBER_FIELD_NAME))) &&
				(StringUtils.isEmpty(previousAddress.get(IGenericScreenConstants.APP_ADDRESS_LINE3))) &&
				(StringUtils.isEmpty(previousAddress.get(IGenericScreenConstants.RESIDENCE_CITY_FIELD_NAME))) &&
				(StringUtils.isEmpty(previousAddress.get(IGenericScreenConstants.APP_ADDRESS_LINE4))) &&
				(StringUtils.isEmpty(previousAddress.get(IGenericScreenConstants.RESIDENCE_YEARS_AT_ADDRESS_FIELD_NAME))) &&
				(StringUtils.isEmpty(previousAddress.get(IGenericScreenConstants.RESIDENCE_MONTHS_AT_ADDRESS_FIELD_NAME))) &&
				(StringUtils.isEmpty(previousAddress.get(IGenericScreenConstants.RESIDENCE_RESIDENTIAL_STATUS_CODE_FIELD_NAME)))){
				return true;
		}
		return false;
	}

}
