package com.wcna.calms.jpos.services.customer;

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

public class CustomerSpouseValidationService implements ICustomValidator {

	private final IRegexValidationService regexValidationService;

	public CustomerSpouseValidationService(IRegexValidationService regexValidationService) {
		this.regexValidationService = regexValidationService;
	}

	/*
	 * if marital status = married/cohabit, spouse name and fiscal number are required
	 */
	/* (non-Javadoc)
	 * @see com.wcna.calms.jpos.services.customer.ICustomerSpouseValidationService#validate(java.util.Map, java.lang.String, java.util.List)
	 */
	public CustomValidationResult validate(Map<String, Object> map,
			Locale locale) {
	//public void validate(Map<String, Object> map, String languageCode, List<GenericMessage> errorMessageList){
		//GenericMessage genericMessage = null;
		CustomValidationResult validationResult = new CustomValidationResult();
		Map custObj = (Map) map.get(IGenericScreenConstants.APP_CONSUMER_CUSTOMER);
		Map spouseObj = (Map) map.get(IGenericScreenConstants.APPLICATION_CUSTOMER_SPOUSE);
		if (custObj != null && spouseObj != null) {
			String maritalStatus = (String)custObj.get(IGenericScreenConstants.APP_CUSTOMER_MARITAL_STATUS);
			if (IConstants.MARITAL_STATUS_MARRIED.equals(maritalStatus) ||
					IConstants.MARITAL_STATUS_COHABIT.equals(maritalStatus)) {
				if (StringUtil.isEmpty((String)spouseObj.get(IGenericScreenConstants.APP_CUSTOMER_COMMON_NAME))) {
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE, IGenericScreenConstants.APP_CUSTOMER_COMMON_NAME, IGenericScreenConstants.APPLICATION_CUSTOMER_SPOUSE);
					validationResult.addValidationItem(validationItem);
					/*genericMessage = translationService.getGenericMessage(IGenericScreenConstants.REQUIRED_ERROR_CODE,
							languageCode,
							IGenericScreenConstants.APP_CUSTOMER_COMMON_NAME,
							IGenericScreenConstants.APPLICATION_CUSTOMER_SPOUSE,
							GenericMessage.ERROR);
					errorMessageList.add(genericMessage);
					((IErrorMessage)SessionManager.getInstance().getSessionData().getMessageStore()).addMessageMetaData(
							IGenericScreenConstants.APPLICATION_CUSTOMER_SPOUSE,
							IGenericScreenConstants.APP_CUSTOMER_COMMON_NAME, null);*/
				}
				String spouseTaxNum = (String)spouseObj.get(IGenericScreenConstants.APP_CUSTOMER_TAX_NUM);
				if (StringUtil.isEmpty(spouseTaxNum) ||
						!regexValidationService.validate(spouseTaxNum, IJPOSScreenConstants.RULE_LIBRARY_CODE_9_DIGITS)) {
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_OR_INVALID_FORMAT_ERROR_CODE, IGenericScreenConstants.APP_CUSTOMER_TAX_NUM, IGenericScreenConstants.APPLICATION_CUSTOMER_SPOUSE);
					validationResult.addValidationItem(validationItem);
					/*genericMessage = translationService.getGenericMessage(IGenericScreenConstants.REQUIRED_OR_INVALID_FORMAT_ERROR_CODE,
							languageCode,
							IGenericScreenConstants.APP_CUSTOMER_TAX_NUM,
							IGenericScreenConstants.APPLICATION_CUSTOMER_SPOUSE,
							GenericMessage.ERROR);
					errorMessageList.add(genericMessage);
					((IErrorMessage)SessionManager.getInstance().getSessionData().getMessageStore()).addMessageMetaData(
							IGenericScreenConstants.APPLICATION_CUSTOMER_SPOUSE,
							IGenericScreenConstants.APP_CUSTOMER_TAX_NUM, null);*/
				}
			}
		}
		return validationResult;
	}
}
