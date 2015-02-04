package com.wcna.calms.jpos.services.customer;

import java.util.ArrayList;
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

public class CustomerPhoneValidationService implements ICustomValidator {

	private final IRegexValidationService regexValidationService;
	private int validationType;

	public CustomerPhoneValidationService(
			IRegexValidationService regexValidationService) {
		this.regexValidationService = regexValidationService;
	}

	public void setValidationType(int validationType) {
		this.validationType = validationType;
	}

	public CustomValidationResult validate(Map<String, Object> map,
			Locale locale) {
		if (validationType == 0) {
			return validate1(map, locale);
		}
		if (validationType == 1 || validationType == 2) {
			return validate2(map, locale);
		}
		if (validationType == 3) {
			return validate3(map, locale);
		}
		if (validationType == 5){
			return validate1(map, locale);
		}
		throw new SystemException("Invalid validation type " + validationType);
	}
	public CustomValidationResult validate2(Map<String, Object> map,
			Locale locale) {
		String triggerAction = "";
		Object actionObj = map.get(IConstants.TR_ACTION_KEY);
		if (actionObj != null && !StringUtil.isEmpty(String.valueOf(actionObj))) {
			triggerAction = String.valueOf(actionObj);
		}
		CustomValidationResult validationResult = new CustomValidationResult();
		List<Map> phoneList = (List<Map>) map.get(IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST);
		String onePhoneReqErrCode = IJPOSScreenConstants.CONSUMER_TELEPHONE_REQUIRED_CODE;
		boolean isConsumer = map.get(IGenericScreenConstants.APP_CONSUMER_CUSTOMER)!=null? true : false;
		if (!isConsumer)
			onePhoneReqErrCode = IJPOSScreenConstants.WORK_TELEPHONE_REQUIRED_CODE;

		ArrayList<String> personalPhoneList = new ArrayList<String>();
		ArrayList<String> workPhoneList = new ArrayList<String>();
		ArrayList<String> companyPhoneList = new ArrayList<String>();
		boolean flag=false;
		int idx = 0;
		for (Iterator<Map> iter = phoneList.iterator(); iter.hasNext();){
			Map phone = iter.next();
			String phoneNumber = (String)phone.get(IGenericScreenConstants.APP_CUSTOMER_PHONE_FIELD_NAME);
			String phoneTypeCode = (String)phone.get(IGenericScreenConstants.APP_CUSTOMER_PHONE_TYPE_FIELD_NAME);

			//if you enter a phone number then phone type is required
			if (!StringUtil.isBlank(phoneNumber)) {
				if (!regexValidationService.validate(phoneNumber, IJPOSScreenConstants.RULE_LIBRARY_CODE_PHONE_PT)) {
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.INVALID_FORMAT_ERROR_CODE, IGenericScreenConstants.APP_CUSTOMER_PHONE_FIELD_NAME, IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST);
					validationItem.setIndex(idx);
					validationResult.addValidationItem(validationItem);
				}
				if (StringUtil.isBlank(phoneTypeCode)) {
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE, IGenericScreenConstants.APP_CUSTOMER_PHONE_TYPE_FIELD_NAME, IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST);
					validationItem.setIndex(idx);
					validationResult.addValidationItem(validationItem);
				}
			}
			if(IConstants.PHONE_TYPE_MOBILE.equals(phoneTypeCode)){
				if(!StringUtil.isEmpty(phoneNumber)&& phoneNumber.substring(0,1).equals("0")){
					flag=true;
				}
			}
			if (!StringUtil.isEmpty(phoneNumber)) {
				if (IConstants.PHONE_TYPE_HOME.equals(phoneTypeCode) || IConstants.PHONE_TYPE_MOBILE.equals(phoneTypeCode)
						|| IConstants.PHONE_TYPE_FAX.equals(phoneTypeCode))
					personalPhoneList.add(phoneNumber);
				else if (IConstants.PHONE_TYPE_WORK.equals(phoneTypeCode))
					workPhoneList.add(phoneNumber);
				else if (IConstants.PHONE_TYPE_BUSINESS.equals(phoneTypeCode) || IConstants.PHONE_TYPE_BUSINESS_FAX.equals(phoneTypeCode))
					companyPhoneList.add(phoneNumber);
				idx++;
			}
		}

		/** only check if action is 'SAVE' (i.e. with validation)*/
		if (!IJPOSScreenConstants.ACTION_SAVE_NO_VALIDATION.equals(triggerAction)) {
			// at least 1 phone is required, and that phone cannot be business
			if (isConsumer && personalPhoneList.size() == 0){
				CustomValidationItem validationItem = new CustomValidationItem(onePhoneReqErrCode, IGenericScreenConstants.APP_CUSTOMER_PHONE_FIELD_NAME, IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST);
				validationResult.addValidationItem(validationItem);
			}
			if (!isConsumer && workPhoneList.size() == 0){
				CustomValidationItem validationItem = new CustomValidationItem(onePhoneReqErrCode, IGenericScreenConstants.APP_CUSTOMER_PHONE_FIELD_NAME, IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST);
				validationResult.addValidationItem(validationItem);
			}
			if (flag){
				CustomValidationItem validationItem = new CustomValidationItem(IJPOSScreenConstants.PHONE_MOBILE_NUMBER_ERROR_CODE, IGenericScreenConstants.APP_CUSTOMER_PHONE_FIELD_NAME, IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST);
				validationResult.addValidationItem(validationItem);
			}
		}
		return validationResult;
	}
	public CustomValidationResult validate1(Map<String, Object> map,
			Locale locale) {
	//public void validate(Map<String, Object> map, IUserContainer userContainer, List<GenericMessage> errorMessageList){
		//GenericMessage genericMessage = null;
		//String languageCode = userContainer.getLanguageCode();
		//triggerAction
		String triggerAction = "";
		Object actionObj = map.get(IConstants.TR_ACTION_KEY);
		if (actionObj != null && !StringUtil.isEmpty(String.valueOf(actionObj))) {
			triggerAction = String.valueOf(actionObj);
		}
		CustomValidationResult validationResult = new CustomValidationResult();
		List<Map> phoneList = (List<Map>) map.get(IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST);
		Map emplObj = (Map) map.get(IGenericScreenConstants.APPLICATION_EMPLOYMENT);
		String employmentType = null;
		if (emplObj != null)
			employmentType = (String)emplObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_TYPE_CODE);					//assuming if false, the customer is commercial
		String onePhoneReqErrCode = IJPOSScreenConstants.CONSUMER_TELEPHONE_REQUIRED_CODE;
		boolean isConsumer = map.get(IGenericScreenConstants.APP_CONSUMER_CUSTOMER)!=null? true : false;
		if (!isConsumer)
			onePhoneReqErrCode = IJPOSScreenConstants.COMMERCIAL_TELEPHONE_REQUIRED_CODE;

		ArrayList<String> personalPhoneList = new ArrayList<String>();
		ArrayList<String> workPhoneList = new ArrayList<String>();
		ArrayList<String> companyPhoneList = new ArrayList<String>();

		int idx = 0;
		for (Iterator<Map> iter = phoneList.iterator(); iter.hasNext();){
			Map phone = iter.next();
			String phoneNumber = (String)phone.get(IGenericScreenConstants.APP_CUSTOMER_PHONE_FIELD_NAME);
			String phoneTypeCode = (String)phone.get(IGenericScreenConstants.APP_CUSTOMER_PHONE_TYPE_FIELD_NAME);

			//if you enter a phone number then phone type is required
			if (!StringUtil.isBlank(phoneNumber)) {
				if (!regexValidationService.validate(phoneNumber, IJPOSScreenConstants.RULE_LIBRARY_CODE_PHONE_PT)) {
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.INVALID_FORMAT_ERROR_CODE, IGenericScreenConstants.APP_CUSTOMER_PHONE_FIELD_NAME, IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST);
					validationItem.setIndex(idx);
					validationResult.addValidationItem(validationItem);
					/*genericMessage = translationService.getGenericMessage(IGenericScreenConstants.INVALID_FORMAT_ERROR_CODE,
												languageCode,
												IGenericScreenConstants.APP_CUSTOMER_PHONE_FIELD_NAME,
												IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST,
												GenericMessage.ERROR);
					errorMessageList.add(genericMessage);
					((IErrorMessage)SessionManager.getInstance().getSessionData().getMessageStore()).addMessageMetaData(
							IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST,
							IGenericScreenConstants.APP_CUSTOMER_PHONE_FIELD_NAME, idx+"");*/
				}
				if (StringUtil.isBlank(phoneTypeCode)) {
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE, IGenericScreenConstants.APP_CUSTOMER_PHONE_TYPE_FIELD_NAME, IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST);
					validationItem.setIndex(idx);
					validationResult.addValidationItem(validationItem);
					/*genericMessage = translationService.getGenericMessage(IGenericScreenConstants.REQUIRED_ERROR_CODE,
																			languageCode,
																			IGenericScreenConstants.APP_CUSTOMER_PHONE_TYPE_FIELD_NAME,
																			IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST,
																			GenericMessage.ERROR);
					errorMessageList.add(genericMessage);
					((IErrorMessage)SessionManager.getInstance().getSessionData().getMessageStore()).addMessageMetaData(
							IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST,
							IGenericScreenConstants.APP_CUSTOMER_PHONE_TYPE_FIELD_NAME, idx+"");*/
				}
			}

			if (!StringUtil.isEmpty(phoneNumber)) {
				if (IConstants.PHONE_TYPE_HOME.equals(phoneTypeCode) || IConstants.PHONE_TYPE_MOBILE.equals(phoneTypeCode)
						|| IConstants.PHONE_TYPE_FAX.equals(phoneTypeCode))
					personalPhoneList.add(phoneNumber);
				else if (IConstants.PHONE_TYPE_WORK.equals(phoneTypeCode))
					workPhoneList.add(phoneNumber);
				else if (IConstants.PHONE_TYPE_BUSINESS.equals(phoneTypeCode) || IConstants.PHONE_TYPE_BUSINESS_FAX.equals(phoneTypeCode))
					companyPhoneList.add(phoneNumber);
				idx++;
			}
		}

		/** only check if action is 'SAVE' (i.e. with validation)*/
		if (!IJPOSScreenConstants.ACTION_SAVE_NO_VALIDATION.equals(triggerAction)) {
			// at least 1 phone is required, and that phone cannot be business
			if (isConsumer && personalPhoneList.size() == 0 || !isConsumer && companyPhoneList.size() == 0) {
				CustomValidationItem validationItem = new CustomValidationItem(onePhoneReqErrCode, IGenericScreenConstants.APP_CUSTOMER_PHONE_FIELD_NAME, IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST);
				validationResult.addValidationItem(validationItem);
				/*genericMessage = translationService.getGenericMessage(onePhoneReqErrCode,
						languageCode,
						IGenericScreenConstants.APP_CUSTOMER_PHONE_FIELD_NAME,
						IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST,
						GenericMessage.ERROR);
				errorMessageList.add(genericMessage);
				((IErrorMessage)SessionManager.getInstance().getSessionData().getMessageStore()).addMessageMetaData(
						IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST,
						IGenericScreenConstants.APP_CUSTOMER_PHONE_FIELD_NAME, "0");*/
			}

			//if employment type is 'CONTRATO' or 'EFECTIVO', check if there's at least one phone of WO
			if (workPhoneList.size() == 0 && (IJPOSScreenConstants.APP_EMPLOYMENT_TYPE_CONTRATO.equals(employmentType) ||
					IJPOSScreenConstants.APP_EMPLOYMENT_TYPE_EFECTIVO.equals(employmentType))) {
				CustomValidationItem validationItem = new CustomValidationItem(IJPOSScreenConstants.WORK_TELEPHONE_REQUIRED_CODE, IGenericScreenConstants.APP_CUSTOMER_PHONE_FIELD_NAME, IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST);
				validationResult.addValidationItem(validationItem);
				/*genericMessage = translationService.getGenericMessage(IJPOSScreenConstants.WORK_TELEPHONE_REQUIRED_CODE,
						languageCode,
						IGenericScreenConstants.APP_CUSTOMER_PHONE_FIELD_NAME,
						IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST,
						GenericMessage.ERROR);
				errorMessageList.add(genericMessage);
				((IErrorMessage)SessionManager.getInstance().getSessionData().getMessageStore()).addMessageMetaData(
						IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST,
						IGenericScreenConstants.APP_CUSTOMER_PHONE_FIELD_NAME, "0");*/
			}
		}
		return validationResult;
	}

	public CustomValidationResult validate3(Map<String, Object> map,
			Locale locale) {
		String triggerAction = "";
		Object actionObj = map.get(IConstants.TR_ACTION_KEY);
		if (actionObj != null && !StringUtil.isEmpty(String.valueOf(actionObj))) {
			triggerAction = String.valueOf(actionObj);
		}
		CustomValidationResult validationResult = new CustomValidationResult();
		List<Map> phoneList = (List<Map>) map.get(IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST);
		Map emplObj = (Map) map.get(IGenericScreenConstants.APPLICATION_EMPLOYMENT);
		boolean isConsumer = map.get(IGenericScreenConstants.APP_CONSUMER_CUSTOMER)!=null? true : false;
		String onePhoneReqErrCode = IJPOSScreenConstants.TELEPHONE_REQUIRED_CODE;
		ArrayList<String>PhoneList = new ArrayList<String>();
		int idx = 0;
		for (Iterator<Map> iter = phoneList.iterator(); iter.hasNext();){
			Map phone = iter.next();
			String phoneNumber = (String)phone.get(IGenericScreenConstants.APP_CUSTOMER_PHONE_FIELD_NAME);
			String phoneTypeCode = (String)phone.get(IGenericScreenConstants.APP_CUSTOMER_PHONE_TYPE_FIELD_NAME);

			//if you enter a phone number then phone type is required
			if (!StringUtil.isBlank(phoneNumber)) {
				if (StringUtil.isBlank(phoneTypeCode)) {
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE, IGenericScreenConstants.APP_CUSTOMER_PHONE_TYPE_FIELD_NAME, IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST);
					validationItem.setIndex(idx);
					validationResult.addValidationItem(validationItem);
				}
			}

			if (!StringUtil.isEmpty(phoneNumber)) {
					PhoneList.add(phoneNumber);
				idx++;
			}
		}

		/** only check if action is 'SAVE' (i.e. with validation)*/
		if (!IJPOSScreenConstants.ACTION_SAVE_NO_VALIDATION.equals(triggerAction)) {
			// at least 1 phone is required, and that phone cannot be business
			if (PhoneList.size() == 0) {
				CustomValidationItem validationItem = new CustomValidationItem(onePhoneReqErrCode, IGenericScreenConstants.APP_CUSTOMER_PHONE_FIELD_NAME, IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST);
				validationResult.addValidationItem(validationItem);
			}
		}
		return validationResult;
	}

	public CustomValidationResult validate5(Map<String, Object> map,
			Locale locale) {
		CustomValidationResult validationResult = new CustomValidationResult();

		return validationResult;
	}
}
