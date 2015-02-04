package com.wcna.calms.jpos.services.customer;

import java.util.Iterator;
import java.util.List;
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

public class CustomerAddressValidationService implements ICustomValidator {

	private final Properties projectProperties;

	private int validationType;
	public CustomerAddressValidationService(Properties projectProperties) {
		this.projectProperties = projectProperties;
	}
	public void setValidationType(int validationType) {
		this.validationType = validationType;
	}

	public CustomValidationResult validate(Map<String, Object> map,	Locale locale) {
		if (validationType == 0 || validationType ==1) {
			return validate1(map, locale);
		}
		if (validationType == 2) {
			return validate2(map, locale);
		}
		if (validationType == 3) {
			return validate3(map, locale);
		}
		if(validationType == 5) {
			return validate5(map, locale);
		}
		throw new SystemException("Invalid validation type " + validationType);
	}

	public CustomValidationResult validate2(Map<String, Object> map,
			Locale locale) {
		CustomValidationResult validationResult = new CustomValidationResult();

		String triggerAction = "";
		Object actionObj = map.get(IConstants.TR_ACTION_KEY);
		if (actionObj != null && !StringUtil.isEmpty(String.valueOf(actionObj))) {
			triggerAction = String.valueOf(actionObj);
		}
		Map<String, String> currentAddress = (Map<String, String>) map.get(IGenericScreenConstants.APPLICATION_ADDRESS);
		Map<String, String> previousAddress = (Map<String, String>) map.get(IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS);
		if (currentAddress == null || previousAddress == null || IJPOSScreenConstants.ACTION_SAVE_NO_VALIDATION.equals(triggerAction)) {
			return validationResult;
		}
		int maxYears = NumberUtils.toInt((String) projectProperties.get("customerScreenAddressConfiguration." + IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS + ".showWhenLessThanYears"), 100);
		int maxMonths = NumberUtils.toInt((String) projectProperties.get("customerScreenAddressConfiguration." + IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS + ".showWhenLessThanMonths"), 12);
		int yearsAtAddress = NumberUtils.toInt(currentAddress.get("yearsAtAddress"), 0);
		int monthsAtAddress = NumberUtils.toInt(currentAddress.get("monthsAtAddress"), 0);
		if (((yearsAtAddress < maxYears) || (yearsAtAddress==0)) && (monthsAtAddress < maxMonths)) {
			if (StringUtils.isBlank((String)previousAddress.get(IGenericScreenConstants.APP_ADDRESS_LINE2))) {
            	CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE, IGenericScreenConstants.APP_ADDRESS_LINE2, IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS);
            	validationResult.addValidationItem(validationItem);
            }
            if (StringUtils.isBlank((String)previousAddress.get(IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME))) {
            	CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE, IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME, IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS);
            	validationResult.addValidationItem(validationItem);
            }
            if (StringUtils.isBlank((String)previousAddress.get(IGenericScreenConstants.RESIDENCE_STREET_NUMBER_FIELD_NAME))) {
            	CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE, IGenericScreenConstants.RESIDENCE_STREET_NUMBER_FIELD_NAME, IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS);
            	validationResult.addValidationItem(validationItem);
            }
            if (StringUtils.isBlank((String)previousAddress.get(IGenericScreenConstants.RESIDENCE_CITY_FIELD_NAME))) {
            	CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE, IGenericScreenConstants.RESIDENCE_CITY_FIELD_NAME, IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS);
            	validationResult.addValidationItem(validationItem);
            }
            if (StringUtils.isBlank((String)previousAddress.get(IGenericScreenConstants.RESIDENCE_YEARATADDRESS_FIELD_NAME))) {
            	CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE, IGenericScreenConstants.RESIDENCE_YEARATADDRESS_FIELD_NAME, IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS);
            	validationResult.addValidationItem(validationItem);
            }if (StringUtils.isBlank((String)previousAddress.get(IGenericScreenConstants.RESIDENCE_MONTHATADDRESS_FIELD_NAME))) {
            	CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE, IGenericScreenConstants.RESIDENCE_MONTHATADDRESS_FIELD_NAME, IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS);
            	validationResult.addValidationItem(validationItem);
            }
		}
		return validationResult;
	}
	public CustomValidationResult validate1(Map<String, Object> map,
			Locale locale) {
		CustomValidationResult validationResult = new CustomValidationResult();
		String triggerAction = "";
		Object actionObj = map.get(IConstants.TR_ACTION_KEY);
		if (actionObj != null && !StringUtil.isEmpty(String.valueOf(actionObj))) {
			triggerAction = String.valueOf(actionObj);
		}
		Map<String, String> currentAddress = (Map<String, String>) map.get(IGenericScreenConstants.APPLICATION_ADDRESS);
		Map<String, String> previousAddress = (Map<String, String>) map.get(IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS);
		if (currentAddress == null || previousAddress == null || IJPOSScreenConstants.ACTION_SAVE_NO_VALIDATION.equals(triggerAction)) {
			return validationResult;
		}
		int maxYears = NumberUtils.toInt((String) projectProperties.get("customerScreenAddressConfiguration." + IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS + ".showWhenLessThanYears"), 100);
		int maxMonths = NumberUtils.toInt((String) projectProperties.get("customerScreenAddressConfiguration." + IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS + ".showWhenLessThanMonths"), 12);
		int yearsAtAddress = NumberUtils.toInt(currentAddress.get("yearsAtAddress"), 0);
		int monthsAtAddress = NumberUtils.toInt(currentAddress.get("monthsAtAddress"), 0);
		if (yearsAtAddress < maxYears && monthsAtAddress < maxMonths) {
			if (StringUtils.isBlank((String)previousAddress.get(IGenericScreenConstants.APP_ADDRESS_LINE2))) {
            	CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE, IGenericScreenConstants.APP_ADDRESS_LINE2, IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS);
            	validationResult.addValidationItem(validationItem);
            }
            if (StringUtils.isBlank((String)previousAddress.get(IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME))) {
            	CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE, IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME, IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS);
            	validationResult.addValidationItem(validationItem);
            }
            if (StringUtils.isBlank((String)previousAddress.get(IGenericScreenConstants.RESIDENCE_CITY_FIELD_NAME))) {
            	CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE, IGenericScreenConstants.RESIDENCE_CITY_FIELD_NAME, IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS);
            	validationResult.addValidationItem(validationItem);
            }
            if (StringUtils.isBlank((String)previousAddress.get(IGenericScreenConstants.RESIDENCE_STATE_PROVINCE_FIELD_NAME))) {
            	CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE, IGenericScreenConstants.RESIDENCE_STATE_PROVINCE_FIELD_NAME, IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS);
            	validationResult.addValidationItem(validationItem);
            }
		}
		return validationResult;
	}
	private CustomValidationResult validate3(Map<String, Object> map, Locale locale) {
		CustomValidationResult validationResult = new CustomValidationResult();
		String triggerAction = "";
		Object actionObj = map.get(IConstants.TR_ACTION_KEY);
		if (actionObj != null && !StringUtil.isEmpty(String.valueOf(actionObj))) {
			triggerAction = String.valueOf(actionObj);
		}
		if (!"SAVE_NOV".equals(triggerAction)) {
			Map<String, String> currentAddress = (Map<String, String>) map
					.get(IGenericScreenConstants.APPLICATION_ADDRESS);

			if ((currentAddress != null)
					&& (StringUtils
							.isEmpty(currentAddress
									.get(IGenericScreenConstants.RESIDENCE_STREET_NUMBER_FIELD_NAME)))
					&& (StringUtils
							.isEmpty(currentAddress
									.get(IGenericScreenConstants.RESIDENCE_SUITE_NUMBER_FIELD_NAME)))
					&& (StringUtils.isEmpty(currentAddress
							.get(IGenericScreenConstants.APP_ADDRESS_LINE3)))) {
				CustomValidationItem validationItem = new CustomValidationItem(
						IGenericScreenConstants.REQUIRED_CURR_ADDRESS_ERROR_CODE,
						"", IGenericScreenConstants.APPLICATION_ADDRESS);
				validationResult.addValidationItem(validationItem);
			}
			int totalMonthsOfResidence = 0;
			totalMonthsOfResidence = getTotalMonthsOfResidence(
					(Map<String, String>) map
							.get(IGenericScreenConstants.APPLICATION_ADDRESS),
					totalMonthsOfResidence);
			totalMonthsOfResidence = getTotalMonthsOfResidence(
					(Map<String, String>) map
							.get(IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS),
					totalMonthsOfResidence);
			totalMonthsOfResidence = getTotalMonthsOfResidence(
					(Map<String, String>) map
							.get(IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS2),
					totalMonthsOfResidence);
			if (totalMonthsOfResidence < 36) {
				Map<String, String> previousAddress = (Map<String, String>) map
						.get(IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS);
				validatePreviousAddress(
						validationResult,
						previousAddress,
						IGenericScreenConstants.REQUIRED_PREV_ADDRESS_ERROR_CODE,
						IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS);
				if ((!isPrivateIndividualCoApplicant(map)) &&((isAddressEmpty((Map<String, String>) map
						.get(IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS)))
						|| (isAddressEmpty((Map<String, String>) map
								.get(IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS2))))) {
					CustomValidationItem validationItem = new CustomValidationItem(
							IGenericScreenConstants.TOTAL_MONTHS_3_RESIDENCE_ERROR_CODE,
							"",
							IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS2,
							new String[] { String
									.valueOf(totalMonthsOfResidence) });
					validationResult.addValidationItem(validationItem);
				}else if (isAddressEmpty((Map<String, String>) map
							.get(IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS))){
					CustomValidationItem validationItem = new CustomValidationItem(
							IGenericScreenConstants.TOTAL_MONTHS_2_RESIDENCE_ERROR_CODE,
							"",
							IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS,
							new String[] { String
									.valueOf(totalMonthsOfResidence) });
					validationResult.addValidationItem(validationItem);
				}

				if (!isPrivateIndividualCoApplicant(map)){
					Map<String, String> previousAddress2 = (Map<String, String>) map
							.get(IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS2);
					validatePreviousAddress(
							validationResult,
							previousAddress2,
							IGenericScreenConstants.REQUIRED_SECOND_PREV_ADDRESS_ERROR_CODE,
							IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS2);
				}

			}
		}
		return validationResult;
	}

	public CustomValidationResult validate5(Map<String, Object> map, Locale locale) {
		CustomValidationResult validationResult = new CustomValidationResult();
		String triggerAction = "";
		Object actionObj = map.get(IConstants.TR_ACTION_KEY);
		if (actionObj != null && !StringUtil.isEmpty(String.valueOf(actionObj))) {
			triggerAction = String.valueOf(actionObj);
		}
		if ("SAVE_NOV".equals(triggerAction)) {
			// check mandatory fields for Billing addres
			validateAddressListElements(validationResult, map, IGenericScreenConstants.APPLICATION_BILLING_ADDRESS_LIST);

			// check mandatory fields for Other address
			validateAddressListElements(validationResult, map, IGenericScreenConstants.APPLICATION_OTHER_ADDRESS_LIST);
		}

		return validationResult;
	}

	private void validateAddressListElements(CustomValidationResult result, Map<String, Object> map, String controlPrefix) {
		List<Map<String,String>> addressList = (List<Map<String,String>>) map.get(controlPrefix);
		if(addressList != null && !addressList.isEmpty()) {

			Iterator<Map<String,String>> it = addressList.iterator();
			while(it.hasNext()) {
				Map<String,String> address = it.next();
				if(!isAddressEmpty(address)) {
					if(StringUtil.isEmpty(address.get(IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME))) {
						CustomValidationItem validationItem = new CustomValidationItem(
								IGenericScreenConstants.REQUIRED_ERROR_CODE,
								IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME,
								controlPrefix);
						result.addValidationItem(validationItem);
					}
					if(StringUtil.isEmpty(address.get(IGenericScreenConstants.RESIDENCE_STREET_NAME_FIELD_NAME))) {
						CustomValidationItem validationItem = new CustomValidationItem(
								IGenericScreenConstants.REQUIRED_ERROR_CODE,
								IGenericScreenConstants.RESIDENCE_STREET_NAME_FIELD_NAME,
								controlPrefix);
						result.addValidationItem(validationItem);
					}
					if(StringUtil.isEmpty(address.get(IGenericScreenConstants.RESIDENCE_STREET_NUMBER_FIELD_NAME))) {
						CustomValidationItem validationItem = new CustomValidationItem(
								IGenericScreenConstants.REQUIRED_ERROR_CODE,
								IGenericScreenConstants.RESIDENCE_STREET_NUMBER_FIELD_NAME,
								controlPrefix);
						result.addValidationItem(validationItem);
					}
					if(StringUtil.isEmpty(address.get(IGenericScreenConstants.APP_ADDRESS_LINE3))) {
						CustomValidationItem validationItem = new CustomValidationItem(
								IGenericScreenConstants.REQUIRED_ERROR_CODE,
								IGenericScreenConstants.APP_ADDRESS_LINE3,
								controlPrefix);
						result.addValidationItem(validationItem);
					}

					if(StringUtil.isEmpty(address.get(IGenericScreenConstants.APP_ADDRESS_NEIGHBOURHOOD))) {
						CustomValidationItem validationItem = new CustomValidationItem(
								IGenericScreenConstants.REQUIRED_ERROR_CODE,
								IGenericScreenConstants.APP_ADDRESS_NEIGHBOURHOOD,
								controlPrefix);
						result.addValidationItem(validationItem);
					}
					if(StringUtil.isEmpty(address.get(IGenericScreenConstants.RESIDENCE_CITY_FIELD_NAME))) {
						CustomValidationItem validationItem = new CustomValidationItem(
								IGenericScreenConstants.REQUIRED_ERROR_CODE,
								IGenericScreenConstants.RESIDENCE_CITY_FIELD_NAME,
								controlPrefix);
						result.addValidationItem(validationItem);
					}
					if(StringUtil.isEmpty(address.get(IGenericScreenConstants.RESIDENCE_COUNTY_FIELD_NAME))) {
						CustomValidationItem validationItem = new CustomValidationItem(
								IGenericScreenConstants.REQUIRED_ERROR_CODE,
								IGenericScreenConstants.RESIDENCE_COUNTY_FIELD_NAME,
								controlPrefix);
						result.addValidationItem(validationItem);
					}
				}
			}
		}
	}

	private int getTotalMonthsOfResidence(Map<String, String> address, int total) {
		if (address != null) {
			int yearWithCurrentEmployment = NumberUtils
					.toInt(address
							.get(IGenericScreenConstants.RESIDENCE_YEARS_AT_ADDRESS_FIELD_NAME));
			int monthWithCurrentEmployemnt = NumberUtils
					.toInt(address
							.get(IGenericScreenConstants.RESIDENCE_MONTHS_AT_ADDRESS_FIELD_NAME));
			total += yearWithCurrentEmployment * 12;
			total += monthWithCurrentEmployemnt;
		}
		return total;
	}

	public boolean isPrivateIndividualCoApplicant(Map map){
		if ((map.get("__SCREEN_CODE__").equals("PrivateIndividualJointApplicant")) || (map.get("__SCREEN_CODE__").equals("ConsumerGuarantor"))){
			return true;
		}
		return false;
	}
	private void validatePreviousAddress(
			CustomValidationResult validationResult,
			Map<String, String> previousAddress, String errorCode, String sectionCode) {
		if(previousAddress!=null) {
			if(StringUtils.isEmpty(previousAddress
					.get(IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME))){
				CustomValidationItem validationItem = new CustomValidationItem(
						IGenericScreenConstants.REQUIRED_ERROR_CODE,
						IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME,
						sectionCode);
				validationResult.addValidationItem(validationItem);
			}
			if ((StringUtils
					.isEmpty(previousAddress
							.get(IGenericScreenConstants.RESIDENCE_SUITE_NUMBER_FIELD_NAME)))
					&& (StringUtils
							.isEmpty(previousAddress
									.get(IGenericScreenConstants.RESIDENCE_STREET_NUMBER_FIELD_NAME)))
					&& (StringUtils
							.isEmpty(previousAddress
									.get(IGenericScreenConstants.APP_ADDRESS_LINE3)))) {
				CustomValidationItem validationItem = new CustomValidationItem(
						errorCode,
						"",
						sectionCode);
				validationResult.addValidationItem(validationItem);
			}
			if(StringUtils.isEmpty(previousAddress
					.get(IGenericScreenConstants.RESIDENCE_COUNTRY_FIELD_NAME))){
				CustomValidationItem validationItem = new CustomValidationItem(
						IGenericScreenConstants.REQUIRED_ERROR_CODE,
						IGenericScreenConstants.RESIDENCE_COUNTRY_FIELD_NAME,
						sectionCode);
				validationResult.addValidationItem(validationItem);
			}
			if(StringUtils.isEmpty(previousAddress
					.get(IGenericScreenConstants.RESIDENCE_STREET_NAME_FIELD_NAME))){
				CustomValidationItem validationItem = new CustomValidationItem(
						IGenericScreenConstants.REQUIRED_ERROR_CODE,
						IGenericScreenConstants.RESIDENCE_STREET_NAME_FIELD_NAME,
						sectionCode);
				validationResult.addValidationItem(validationItem);
			}
			if(StringUtils.isEmpty(previousAddress
					.get(IGenericScreenConstants.APP_ADDRESS_LINE4))){
				CustomValidationItem validationItem = new CustomValidationItem(
						IGenericScreenConstants.REQUIRED_ERROR_CODE,
						IGenericScreenConstants.APP_ADDRESS_LINE4,
						sectionCode);
				validationResult.addValidationItem(validationItem);
			}
			if(StringUtils.isEmpty(previousAddress
					.get(IGenericScreenConstants.RESIDENCE_YEARS_AT_ADDRESS_FIELD_NAME))){
				CustomValidationItem validationItem = new CustomValidationItem(
						IGenericScreenConstants.REQUIRED_ERROR_CODE,
						IGenericScreenConstants.RESIDENCE_YEARS_AT_ADDRESS_FIELD_NAME,
						sectionCode);
				validationResult.addValidationItem(validationItem);
			}
			if(StringUtils.isEmpty(previousAddress
					.get(IGenericScreenConstants.RESIDENCE_MONTHS_AT_ADDRESS_FIELD_NAME))){
				CustomValidationItem validationItem = new CustomValidationItem(
						IGenericScreenConstants.REQUIRED_ERROR_CODE,
						IGenericScreenConstants.RESIDENCE_MONTHS_AT_ADDRESS_FIELD_NAME,
						sectionCode);
				validationResult.addValidationItem(validationItem);
			}
		}
	}

	private boolean isAddressEmpty(Map<String, String> previousAddress){
		if((previousAddress!=null) &&
				(StringUtils.isEmpty(previousAddress.get(IGenericScreenConstants.RESIDENCE_STREET_NAME_FIELD_NAME))) &&
				(StringUtils.isEmpty(previousAddress.get(IGenericScreenConstants.RESIDENCE_STREET_NUMBER_FIELD_NAME))) &&
				(StringUtils.isEmpty(previousAddress.get(IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME))) &&
				(StringUtils.isEmpty(previousAddress.get(IGenericScreenConstants.RESIDENCE_SUITE_NUMBER_FIELD_NAME))) &&
				(StringUtils.isEmpty(previousAddress.get(IGenericScreenConstants.APP_ADDRESS_LINE3))) &&
				(StringUtils.isEmpty(previousAddress.get(IGenericScreenConstants.RESIDENCE_CITY_FIELD_NAME))) &&
				(StringUtils.isEmpty(previousAddress.get(IGenericScreenConstants.APP_ADDRESS_LINE4))) &&
				(StringUtils.isEmpty(previousAddress.get(IGenericScreenConstants.RESIDENCE_YEARS_AT_ADDRESS_FIELD_NAME))) &&
				(StringUtils.isEmpty(previousAddress.get(IGenericScreenConstants.RESIDENCE_MONTHS_AT_ADDRESS_FIELD_NAME))) &&
				(StringUtils.isEmpty(previousAddress.get(IGenericScreenConstants.RESIDENCE_RESIDENTIAL_STATUS_CODE_FIELD_NAME))) &&
				(StringUtils.isEmpty(previousAddress.get(IGenericScreenConstants.APP_ADDRESS_NEIGHBOURHOOD)))){
				return true;
		}
		return false;
	}

}
