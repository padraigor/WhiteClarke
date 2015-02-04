package com.wcna.calms.jpos.services.customer;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import com.wcna.calms.jpos.services.application.IJPOSScreenConstants;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.validation.CustomValidationItem;
import com.wcna.calms.service.validation.CustomValidationResult;
import com.wcna.calms.service.validation.ICustomValidator;
import com.wcna.calms.service.validation.IRegexValidationService;
import com.wcna.lang.StringUtil;

public class CustomerBankAccountNumberValidationService implements ICustomValidator {

	private final IRegexValidationService regexValidationService;

	public CustomerBankAccountNumberValidationService(IRegexValidationService regexValidationService) {
		this.regexValidationService = regexValidationService;
	}

	/**
	 * 1) if ID doc type is private ID, ID doc number must be
	 * 18 characters including numeric and the special character X
	 */
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
		ArrayList bankList = (ArrayList) map.get(IGenericScreenConstants.APPLICATION_BANK_LIST);
		for(int i=0; i<bankList.size();i++){
			Map bankDetails = (Map)bankList.get(i);
			String accountNumber = (String)bankDetails.get(IGenericScreenConstants.BANK_ACCOUNT_NUBMER_FIELD_NAME);
			String bankName = (String)bankDetails.get(IGenericScreenConstants.BANK_NAME_FIELD_NAME);
			if (accountNumber != null && bankName != null) {
				String fieldNameAccountNumber = IGenericScreenConstants.BANK_ACCOUNT_NUBMER_FIELD_NAME;
				if(!StringUtil.isEmpty(bankName) && !StringUtil.isEmpty(accountNumber)){
					if (IConstants.BANK_ACCOUNT_TYPE_ICBC.equals(bankName)) {
						if(!regexValidationService.validate(accountNumber,IJPOSScreenConstants.RULE_LIBRARY_CODE_BANK_ACCOUNT_NUMBER_ICBC)){
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.INVALID_FORMAT_ERROR_CODE,
									fieldNameAccountNumber,
									IGenericScreenConstants.APPLICATION_BANK_LIST);
							validationItem.setIndex(i);
							validationResult.addValidationItem(validationItem);
						}
					}else if(IConstants.BANK_ACCOUNT_TYPE_BOC.equals(bankName)){
						if(!regexValidationService.validate(accountNumber,IJPOSScreenConstants.RULE_LIBRARY_CODE_BANK_ACCOUNT_NUMBER_BOC)){
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.INVALID_FORMAT_ERROR_CODE,
									fieldNameAccountNumber,
									IGenericScreenConstants.APPLICATION_BANK_LIST);
							validationItem.setIndex(i);
							validationResult.addValidationItem(validationItem);
						}
					}else if(IConstants.BANK_ACCOUNT_TYPE_ABC.equals(bankName)){
						if(!regexValidationService.validate(accountNumber,IJPOSScreenConstants.RULE_LIBRARY_CODE_BANK_ACCOUNT_NUMBER_ABC)){
							CustomValidationItem validationItem = new CustomValidationItem(
									IGenericScreenConstants.INVALID_FORMAT_ERROR_CODE,
									fieldNameAccountNumber,
									IGenericScreenConstants.APPLICATION_BANK_LIST);
							validationItem.setIndex(i);
							validationResult.addValidationItem(validationItem);
						}
					}
				}
			}
		}
		return validationResult;
	}
}
