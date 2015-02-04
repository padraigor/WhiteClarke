package com.wcna.calms.jpos.services.customer;

import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.wcna.calms.jpos.services.application.IJPOSScreenConstants;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.validation.CustomValidationItem;
import com.wcna.calms.service.validation.CustomValidationResult;
import com.wcna.calms.service.validation.ICustomValidator;
import com.wcna.lang.StringUtil;

public class CustomerTradingAddressValidationService implements
		ICustomValidator {

	private int validationType;

	public void setValidationType(int validationType) {
		this.validationType = validationType;
	}

	public CustomValidationResult validate(Map<String, Object> map,
			Locale locale) {
		if (validationType == 3) {
			return validate1(map, locale);
		}
		return null;
	}

	private CustomValidationResult validate1(Map<String, Object> map,
			Locale locale) {
		CustomValidationResult validationResult = new CustomValidationResult();
		String triggerAction = "";
		Object actionObj = map.get(IConstants.TR_ACTION_KEY);
		if (actionObj != null && !StringUtil.isEmpty(String.valueOf(actionObj))) {
			triggerAction = String.valueOf(actionObj);
		}
		if (!"SAVE_NOV".equals(triggerAction)) {
			Map<String, String> currentTradingAddress = (Map<String, String>) map
					.get(IGenericScreenConstants.APPLICATION_TRADING_ADDRESS);

			if ((currentTradingAddress != null)
					&& (StringUtils
							.isEmpty(currentTradingAddress
									.get(IGenericScreenConstants.RESIDENCE_STREET_NUMBER_FIELD_NAME)))
					&& (StringUtils
							.isEmpty(currentTradingAddress
									.get(IGenericScreenConstants.RESIDENCE_SUITE_NUMBER_FIELD_NAME)))
					&& (StringUtils.isEmpty(currentTradingAddress
							.get(IGenericScreenConstants.APP_ADDRESS_LINE3)))) {
				CustomValidationItem validationItem = new CustomValidationItem(
						IGenericScreenConstants.REQUIRED_CURR_ADDRESS_ERROR_CODE,
						"", IGenericScreenConstants.APPLICATION_TRADING_ADDRESS);
				validationResult.addValidationItem(validationItem);
			}
			int totalMonthsOfResidence = 0;
			totalMonthsOfResidence = getTotalMonthsOfResidence(
					(Map<String, String>) map
							.get(IGenericScreenConstants.APPLICATION_TRADING_ADDRESS),
					totalMonthsOfResidence);
			totalMonthsOfResidence = getTotalMonthsOfResidence(
					(Map<String, String>) map
							.get(IGenericScreenConstants.APPLICATION_PREVIOUS_TRADING_ADDRESS),
					totalMonthsOfResidence);
			if (totalMonthsOfResidence < 36) {
				if (isAddressEmpty((Map<String, String>) map
						.get(IGenericScreenConstants.APPLICATION_PREVIOUS_TRADING_ADDRESS))) {
					CustomValidationItem validationItem = new CustomValidationItem(
							IJPOSScreenConstants.TOTAL_MONTHS_TRADING_ERROR_CODE,
							"",
							IGenericScreenConstants.APPLICATION_TRADING_ADDRESS,
							new String[] { String
									.valueOf(totalMonthsOfResidence) });
					validationResult.addValidationItem(validationItem);
				}
				Map<String, String> previousAddress = (Map<String, String>) map
						.get(IGenericScreenConstants.APPLICATION_PREVIOUS_TRADING_ADDRESS);
				validatePreviousAddress(
						validationResult,
						previousAddress,
						IGenericScreenConstants.REQUIRED_PREV_ADDRESS_ERROR_CODE,
						IGenericScreenConstants.APPLICATION_PREVIOUS_TRADING_ADDRESS);
			}
		}
		return validationResult;
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

	private void validatePreviousAddress(
			CustomValidationResult validationResult,
			Map<String, String> previousAddress, String errorCode,
			String sectionCode) {
		if (previousAddress != null) {
			if (StringUtils.isEmpty(previousAddress
					.get(IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME))) {
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
					&& (StringUtils.isEmpty(previousAddress
							.get(IGenericScreenConstants.APP_ADDRESS_LINE3)))) {
				CustomValidationItem validationItem = new CustomValidationItem(
						errorCode, "", sectionCode);
				validationResult.addValidationItem(validationItem);
			}
			if (StringUtils
					.isEmpty(previousAddress
							.get(IGenericScreenConstants.RESIDENCE_STREET_NAME_FIELD_NAME))) {
				CustomValidationItem validationItem = new CustomValidationItem(
						IGenericScreenConstants.REQUIRED_ERROR_CODE,
						IGenericScreenConstants.RESIDENCE_STREET_NAME_FIELD_NAME,
						sectionCode);
				validationResult.addValidationItem(validationItem);
			}
			if (StringUtils.isEmpty(previousAddress
					.get(IGenericScreenConstants.APP_ADDRESS_LINE4))) {
				CustomValidationItem validationItem = new CustomValidationItem(
						IGenericScreenConstants.REQUIRED_ERROR_CODE,
						IGenericScreenConstants.APP_ADDRESS_LINE4, sectionCode);
				validationResult.addValidationItem(validationItem);
			}
			if (StringUtils
					.isEmpty(previousAddress
							.get(IGenericScreenConstants.RESIDENCE_YEARS_AT_ADDRESS_FIELD_NAME))) {
				CustomValidationItem validationItem = new CustomValidationItem(
						IGenericScreenConstants.REQUIRED_ERROR_CODE,
						IGenericScreenConstants.RESIDENCE_YEARS_AT_ADDRESS_FIELD_NAME,
						sectionCode);
				validationResult.addValidationItem(validationItem);
			}
			if (StringUtils
					.isEmpty(previousAddress
							.get(IGenericScreenConstants.RESIDENCE_MONTHS_AT_ADDRESS_FIELD_NAME))) {
				CustomValidationItem validationItem = new CustomValidationItem(
						IGenericScreenConstants.REQUIRED_ERROR_CODE,
						IGenericScreenConstants.RESIDENCE_MONTHS_AT_ADDRESS_FIELD_NAME,
						sectionCode);
				validationResult.addValidationItem(validationItem);
			}
		}
	}

	private boolean isAddressEmpty(Map<String, String> previousAddress) {
		if ((previousAddress != null)
				&& (StringUtils
						.isEmpty(previousAddress
								.get(IGenericScreenConstants.RESIDENCE_STREET_NAME_FIELD_NAME)))
				&& (StringUtils
						.isEmpty(previousAddress
								.get(IGenericScreenConstants.RESIDENCE_STREET_NUMBER_FIELD_NAME)))
				&& (StringUtils.isEmpty(previousAddress
						.get(IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME)))
				&& (StringUtils
						.isEmpty(previousAddress
								.get(IGenericScreenConstants.RESIDENCE_SUITE_NUMBER_FIELD_NAME)))
				&& (StringUtils.isEmpty(previousAddress
						.get(IGenericScreenConstants.APP_ADDRESS_LINE3)))
				&& (StringUtils
						.isEmpty(previousAddress
								.get(IGenericScreenConstants.RESIDENCE_CITY_FIELD_NAME)))
				&& (StringUtils.isEmpty(previousAddress
						.get(IGenericScreenConstants.APP_ADDRESS_LINE4)))
				&& (StringUtils
						.isEmpty(previousAddress
								.get(IGenericScreenConstants.RESIDENCE_YEARS_AT_ADDRESS_FIELD_NAME)))
				&& (StringUtils
						.isEmpty(previousAddress
								.get(IGenericScreenConstants.RESIDENCE_MONTHS_AT_ADDRESS_FIELD_NAME)))
				&& (StringUtils
						.isEmpty(previousAddress
								.get(IGenericScreenConstants.RESIDENCE_RESIDENTIAL_STATUS_CODE_FIELD_NAME)))) {
			return true;
		}
		return false;
	}

}
