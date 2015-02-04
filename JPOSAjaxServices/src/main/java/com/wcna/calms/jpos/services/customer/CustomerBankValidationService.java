package com.wcna.calms.jpos.services.customer;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.wcg.exception.SystemException;
import com.wcna.calms.jpos.services.application.IJPOSScreenConstants;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.validation.CustomValidationItem;
import com.wcna.calms.service.validation.CustomValidationResult;
import com.wcna.calms.service.validation.ICustomValidator;
import com.wcna.calms.service.validation.IRegexValidationService;
import com.wcna.lang.StringUtil;

public class CustomerBankValidationService implements ICustomValidator {

	private final IRegexValidationService regexValidationService;
	private int validationType;

	public CustomerBankValidationService(IRegexValidationService regexValidationService) {
		this.regexValidationService = regexValidationService;
	}

	public void setValidationType(int validationType) {
		this.validationType = validationType;
	}

		/**
	 * fields are mandatory only for primary bank
	 */
	public CustomValidationResult validate(Map<String, Object> map,
			Locale locale) {
		if (validationType == 0) {
			return validate1(map, locale);
		}
		if (validationType == 1) {
			return validate2(map, locale);
		}
		if(validationType == 2){
			return validate3(map, locale);
		}
		if (validationType == 5) {
			return validate5(map, locale);
		}
		throw new SystemException("Invalid validation type " + validationType);
	}

	private CustomValidationResult validate3(Map<String, Object> map,
			Locale locale) {
		CustomValidationResult validationResult = new CustomValidationResult();
		String triggerAction = getTriggerAction(map);
		List<Map> bankList = (List<Map>) map.get(IGenericScreenConstants.APPLICATION_BANK_LIST);
		if (IJPOSScreenConstants.ACTION_SAVE_NO_VALIDATION.equals(triggerAction) || bankList == null) {
			return validationResult;
		}
		for (Map bankObj : bankList) {
			String paymentMode = (String)bankObj.get(IGenericScreenConstants.BANK_PAYMENT_METHOD_CODE);
			if (StringUtil.isEmpty(paymentMode)) {
				CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE,
						IGenericScreenConstants.BANK_PAYMENT_METHOD_CODE,
						IGenericScreenConstants.APPLICATION_BANK_LIST);
				validationResult.addValidationItem(validationItem);
			}
			if ("DRDEB".equalsIgnoreCase(paymentMode)) {
				String bankAccountNumber = (String)bankObj.get(IGenericScreenConstants.BANK_ACCOUNT_NUBMER_FIELD_NAME);
				if(!validationBankAccount(bankAccountNumber)){
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.BANK_ACCOUNT_NUBMER_FIELD_NAME,
							IGenericScreenConstants.APPLICATION_BANK_LIST);
					validationResult.addValidationItem(validationItem);
				}
			}
		}
		return validationResult;

	}

	private boolean validationBankAccount(String structuredCode){
		if (structuredCode == null || structuredCode.length() != 12) {
			return false;
		}
		String controlDigit = structuredCode.substring(10,12);
		String accountNo = structuredCode.substring(0,10);
		int p1 = Integer.parseInt(controlDigit);
		if(p1==0){
			return false;
		}
		long p2 = Long.parseLong(accountNo);
		long p3 = p2/97;
		if(p2%97==0){
			p1=97;
		}
		long p5 = p3*97;
		long p6 = p2-p5;
		long p7 = 97 - p6;
		return p1 == p7;


	}
	private CustomValidationResult validate2(Map<String, Object> map,
			Locale locale) {
		CustomValidationResult validationResult = new CustomValidationResult();
		String triggerAction = getTriggerAction(map);
		List<Map> bankList = (List<Map>) map.get(IGenericScreenConstants.APPLICATION_BANK_LIST);
		if (IJPOSScreenConstants.ACTION_SAVE_NO_VALIDATION.equals(triggerAction) || bankList == null) {
			return validationResult;
		}
		int cnt = 0;
		for (Map bankObj : bankList) {
			String paymentMode = (String)bankObj.get(IGenericScreenConstants.BANK_PAYMENT_METHOD_CODE);
			if (StringUtil.isEmpty(paymentMode)) {
				CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE,
						IGenericScreenConstants.BANK_PAYMENT_METHOD_CODE,
						IGenericScreenConstants.APPLICATION_BANK_LIST);
				validationItem.setIndex(cnt);
				validationResult.addValidationItem(validationItem);
			}
			if ("DRDEB".equalsIgnoreCase(paymentMode)) {
				String ibanNumber = (String)bankObj.get(IGenericScreenConstants.BANK_IBAN_NUMBER);
				if (StringUtil.isEmpty(ibanNumber) &&
						((StringUtil.isEmpty((String)bankObj.get(IGenericScreenConstants.BANK_ABI_NUMBER)))
						|| StringUtil.isEmpty((String)bankObj.get(IGenericScreenConstants.BANK_CAB_NUMBER))
						|| StringUtil.isEmpty((String)bankObj.get(IGenericScreenConstants.BANK_CIN_NUMBER))
						|| StringUtil.isEmpty((String)bankObj.get(IGenericScreenConstants.BANK_ACCOUNT_NUBMER_FIELD_NAME)))
				) {
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.BANK_IBAN_NUMBER,
							IGenericScreenConstants.APPLICATION_BANK_LIST);
					validationItem.setIndex(cnt);
					validationResult.addValidationItem(validationItem);
				}
				if (StringUtil.isEmpty((String)bankObj.get(IGenericScreenConstants.BANK_ACCOUNT_NAME))) {
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.BANK_ACCOUNT_NAME,
							IGenericScreenConstants.APPLICATION_BANK_LIST);
					validationItem.setIndex(cnt);
					validationResult.addValidationItem(validationItem);
				}
				if (StringUtil.isEmpty((String)bankObj.get(IGenericScreenConstants.BANK_ACCOUNT_HOLDER))) {
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.BANK_ACCOUNT_HOLDER,
							IGenericScreenConstants.APPLICATION_BANK_LIST);
					validationItem.setIndex(cnt);
					validationResult.addValidationItem(validationItem);
				}
				if (StringUtil.isEmpty((String)bankObj.get(IGenericScreenConstants.BANK_TAX_NUMBER))) {
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.BANK_TAX_NUMBER,
							IGenericScreenConstants.APPLICATION_BANK_LIST);
					validationItem.setIndex(cnt);
					validationResult.addValidationItem(validationItem);
				}
			}
			cnt++;
		}
		return validationResult;

	}

	private CustomValidationResult validate1(Map<String, Object> map,
			Locale locale) {
		CustomValidationResult validationResult = new CustomValidationResult();
		String triggerAction = getTriggerAction(map);
		List<Map> bankList = (List<Map>) map.get(IGenericScreenConstants.APPLICATION_BANK_LIST);
		if (IJPOSScreenConstants.ACTION_SAVE_NO_VALIDATION.equals(triggerAction) || bankList == null) {
			return validationResult;
		}
		int cnt = 0;
		for (Map bankObj : bankList) {
			if (cnt == 0) {
				if (StringUtil.isEmpty((String)bankObj.get(IGenericScreenConstants.BANK_PAYMENT_METHOD_CODE))) {
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.BANK_PAYMENT_METHOD_CODE,
							IGenericScreenConstants.APPLICATION_BANK_LIST);
					validationItem.setIndex(cnt);
					validationResult.addValidationItem(validationItem);
				}
				if (StringUtil.isEmpty((String)bankObj.get(IGenericScreenConstants.BANK_ACCOUNT_NAME))) {
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.BANK_ACCOUNT_NAME,
							IGenericScreenConstants.APPLICATION_BANK_LIST);
					validationItem.setIndex(cnt);
					validationResult.addValidationItem(validationItem);
				}
				if (StringUtil.isEmpty((String)bankObj.get(IGenericScreenConstants.BANK_ACCOUNT_NUBMER_FIELD_NAME))
						|| !regexValidationService.validate((String)bankObj.get(IGenericScreenConstants.BANK_ACCOUNT_NUBMER_FIELD_NAME), IJPOSScreenConstants.RULE_LIBRARY_CODE_BANK_NIB)) {
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_OR_INVALID_FORMAT_ERROR_CODE,
							IGenericScreenConstants.BANK_ACCOUNT_NUBMER_FIELD_NAME,
							IGenericScreenConstants.APPLICATION_BANK_LIST);
					validationItem.setIndex(cnt);
					validationResult.addValidationItem(validationItem);
				}
				if (StringUtil.isEmpty((String)bankObj.get(IGenericScreenConstants.BANK_NAME_FIELD_NAME))) {
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.BANK_NAME_FIELD_NAME,
							IGenericScreenConstants.APPLICATION_BANK_LIST);
					validationItem.setIndex(cnt);
					validationResult.addValidationItem(validationItem);
				}
				if (StringUtil.isEmpty((String)bankObj.get(IGenericScreenConstants.BANK_BRANCH_NAME))) {
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.BANK_BRANCH_NAME,
							IGenericScreenConstants.APPLICATION_BANK_LIST);
					validationItem.setIndex(cnt);
					validationResult.addValidationItem(validationItem);
				}
			} else {
				if (!StringUtil.isEmpty((String)bankObj.get(IGenericScreenConstants.BANK_ACCOUNT_NUBMER_FIELD_NAME))) {
					if (!regexValidationService.validate((String)bankObj.get(IGenericScreenConstants.BANK_ACCOUNT_NUBMER_FIELD_NAME), IJPOSScreenConstants.RULE_LIBRARY_CODE_BANK_NIB)) {
						CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.INVALID_FORMAT_ERROR_CODE,
								IGenericScreenConstants.BANK_ACCOUNT_NUBMER_FIELD_NAME,
								IGenericScreenConstants.APPLICATION_BANK_LIST);
						validationItem.setIndex(cnt);
						validationResult.addValidationItem(validationItem);
					}
				}
			}
			cnt++;
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
		if (!"SAVE_NOV".equals(triggerAction)) {
			List<Map<String,String>> bankList = (List<Map<String,String>>) map.get(IGenericScreenConstants.APPLICATION_BANK_LIST);
			if(bankList != null && !bankList.isEmpty()) {

				Iterator<Map<String,String>> it = bankList.iterator();
				int counter = 0;
				while(it.hasNext()) {
					Map<String,String> bank = it.next();
					if(!isBankEmpty(bank)) {
						if(StringUtil.isEmpty(bank.get(IGenericScreenConstants.CUSTOMER_BANK_DEFAULT_FLAG_FIELD_NAME))) {
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.REQUIRED_ERROR_CODE,
									IGenericScreenConstants.CUSTOMER_BANK_DEFAULT_FLAG_FIELD_NAME,
									IGenericScreenConstants.APPLICATION_BANK_LIST);
							validationResult.addValidationItem(validationItem);
						}
						if(StringUtil.isEmpty(bank.get(IGenericScreenConstants.QUOTE_CUSTOMER_NAME))) {
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.REQUIRED_ERROR_CODE,
									IGenericScreenConstants.QUOTE_CUSTOMER_NAME,
									IGenericScreenConstants.APPLICATION_BANK_LIST);
							validationResult.addValidationItem(validationItem);
						}
						if(StringUtil.isEmpty(bank.get(IGenericScreenConstants.BANK_ACCOUNT_NAME))) {
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.REQUIRED_ERROR_CODE,
									IGenericScreenConstants.BANK_ACCOUNT_NAME,
									IGenericScreenConstants.APPLICATION_BANK_LIST);
							validationResult.addValidationItem(validationItem);
						}
						if(StringUtil.isEmpty(bank.get(IGenericScreenConstants.BANK_BRANCH_NAME))) {
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.REQUIRED_ERROR_CODE,
									IGenericScreenConstants.BANK_BRANCH_NAME,
									IGenericScreenConstants.APPLICATION_BANK_LIST);
							validationResult.addValidationItem(validationItem);
						}
						if(StringUtil.isEmpty(bank.get(IGenericScreenConstants.APP_CUSTOMER_REFERENCES_PHONE_FIELD_NAME))) {
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.REQUIRED_ERROR_CODE,
									IGenericScreenConstants.APP_CUSTOMER_REFERENCES_PHONE_FIELD_NAME,
									IGenericScreenConstants.APPLICATION_BANK_LIST);
							validationResult.addValidationItem(validationItem);
						}
						if(StringUtil.isEmpty(bank.get(IGenericScreenConstants.BANK_ACCOUNT_NUBMER_FIELD_NAME))) {
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.REQUIRED_ERROR_CODE,
									IGenericScreenConstants.BANK_ACCOUNT_NUBMER_FIELD_NAME,
									IGenericScreenConstants.APPLICATION_BANK_LIST);
							validationResult.addValidationItem(validationItem);
						}
						if(StringUtil.isEmpty(bank.get(IGenericScreenConstants.CUSTOMER_BANK_YEARS_WITH_FIELD_NAME))) {
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.REQUIRED_ERROR_CODE,
									IGenericScreenConstants.CUSTOMER_BANK_YEARS_WITH_FIELD_NAME,
									IGenericScreenConstants.APPLICATION_BANK_LIST);
							validationResult.addValidationItem(validationItem);
						}
						if(StringUtil.isEmpty(bank.get(IGenericScreenConstants.CUSTOMER_BANK_MONTHS_WITH_FIELD_NAME))) {
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.REQUIRED_ERROR_CODE,
									IGenericScreenConstants.CUSTOMER_BANK_MONTHS_WITH_FIELD_NAME,
									IGenericScreenConstants.APPLICATION_BANK_LIST);
							validationResult.addValidationItem(validationItem);
						}
					}
					counter++;
				}
			}
		}

		return validationResult;
	}

	private boolean isBankEmpty(Map<String,String> bank) {
		if(!StringUtil.isEmpty(bank.get(IGenericScreenConstants.CUSTOMER_BANK_DEFAULT_FLAG_FIELD_NAME))) {
			return false;
		}
		if(!StringUtil.isEmpty(bank.get(IGenericScreenConstants.QUOTE_CUSTOMER_NAME))) {
			return false;
		}
		if(!StringUtil.isEmpty(bank.get(IGenericScreenConstants.BANK_ACCOUNT_NAME))) {
			return false;
		}
		if(!StringUtil.isEmpty(bank.get(IGenericScreenConstants.BANK_BRANCH_NAME))) {
			return false;
		}
		if(!StringUtil.isEmpty(bank.get(IGenericScreenConstants.APP_CUSTOMER_REFERENCES_PHONE_FIELD_NAME))) {
			return false;
		}
		if(!StringUtil.isEmpty(bank.get(IGenericScreenConstants.BANK_ACCOUNT_NUBMER_FIELD_NAME))) {
			return false;
		}
		if(!StringUtil.isEmpty(bank.get(IGenericScreenConstants.CUSTOMER_BANK_YEARS_WITH_FIELD_NAME))) {
			return false;
		}
		if(!StringUtil.isEmpty(bank.get(IGenericScreenConstants.CUSTOMER_BANK_MONTHS_WITH_FIELD_NAME))) {
			return false;
		}

		return true;
	}

	private String getTriggerAction(Map<String, Object> map) {
		String triggerAction = "";
		Object actionObj = map.get(IConstants.TR_ACTION_KEY);
		if (actionObj != null && !StringUtil.isEmpty(String.valueOf(actionObj))) {
			triggerAction = String.valueOf(actionObj);
		}
		return triggerAction;
	}
}
