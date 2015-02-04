package com.wcna.calms.jpos.services.customer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.product.calms.security.CalmsSessionData;
import com.wcg.product.services.session.GenericMessage;
import com.wcg.product.services.session.SessionManager;
import com.wcna.calms.jpos.services.application.IJPOSScreenConstants;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.common.IUserContainer;
import com.wcna.calms.service.customer.ICustomerShowHideService;
import com.wcna.calms.service.error.IErrorMessage;
import com.wcna.calms.service.translation.ITranslationService;
import com.wcna.calms.service.validation.CustomValidationItem;
import com.wcna.calms.service.validation.CustomValidationResult;
import com.wcna.calms.service.validation.ICustomValidator;
import com.wcna.calms.service.validation.IRegexValidationService;
import com.wcna.calms.web.IConstantsWeb;
import com.wcna.lang.StringUtil;

public class CustomerPreSaveValidationService extends CalmsAjaxService {

	private final IRegexValidationService regexValidationService;
	private final ITranslationService translationService;
	private Map<String, List<ICustomValidator>> validators;
	private final ICustomerShowHideService showHideService;

	public CustomerPreSaveValidationService(
			IRegexValidationService regexValidationService,
			ITranslationService translationService,
			ICustomerShowHideService showHideService) {
		this.regexValidationService = regexValidationService;
		this.translationService = translationService;
		this.showHideService = showHideService;
	}

	public void setValidators(Map<String, List<ICustomValidator>> validators) {
		this.validators = validators;
	}

	public Object invoke(Object object) {
		if (object instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) object;
			if (map == null) return object;

			CalmsSessionData sessionData = (CalmsSessionData) SessionManager.getInstance().getSessionData();
			IUserContainer userContainer = ((CalmsSessionData)sessionData).getUserContainer();
			String languageCode = userContainer.getLanguageCode();

			String custDataKey = getCustomerDataKey(map);
			Map custMap = (Map)map.get(custDataKey);
			showHideService.showHide(custMap, getUserContainer());
			//AppID
//			long appId = 0;
//			if (sessionData != null && sessionData.getAppContainer() != null)
//				appId = sessionData.getAppContainer().getAppID();
//
//			//RefID
//			long refId = 0;
//			Object keyIdObj = map.get(IServiceConstants.KEY_ID);
//			if (keyIdObj != null && !StringUtil.isEmpty(String.valueOf(keyIdObj))){
//				refId = new Long(String.valueOf(keyIdObj)).longValue();
//			}

			//triggerAction
			String triggerAction = "";
			Object actionObj = map.get(IConstants.TR_ACTION_KEY);
			if (actionObj != null && !StringUtil.isEmpty(String.valueOf(actionObj))) {
				triggerAction = String.valueOf(actionObj);
			}

//			IGenericScreenForm form = createBean(IGenericScreenForm.class);
//			form.convertMapsToBeans(map);

			List<GenericMessage> errorMessageList = new ArrayList<GenericMessage>();
			runCustomValidators(map, userContainer, errorMessageList);

			if (!IJPOSScreenConstants.ACTION_SAVE_NO_VALIDATION.equals(triggerAction)) {
				validateGuarantorFlag(map, languageCode, errorMessageList);
			}

//			if (map.get(IGenericScreenConstants.APPLICATION_COMMERCIAL_FINANCIAL_LIST) != null)
//				map = validateComFinancials(map);

			//save any error message into MessageStore in current session
			if (errorMessageList != null && !errorMessageList.isEmpty()){
				for (Iterator<GenericMessage> iter = errorMessageList.iterator(); iter.hasNext();){
					GenericMessage genericMessage = iter.next();
					SessionManager.getInstance().getSessionData().getMessageStore().addMessage(genericMessage);
				}
			}
		}
		return object;
	}

	private String getCustomerDataKey(Map customer) {
		String custDataKey = "";
		for (Iterator itr=customer.keySet().iterator(); itr.hasNext();) {
			String key = (String)itr.next();
			if (key.startsWith(IGenericScreenConstants.APPLICATION_CUSTOMER + IGenericScreenConstants.DELIMITER + IGenericScreenConstants.DELIMITER_TYPE)) {
				custDataKey = key;
				break;
			}
		}
		return custDataKey;
	}

	private void runCustomValidators(Map<String, Object> map, IUserContainer userContainer,
			List<GenericMessage> errorMessageList) {
		if (validators != null && !validators.isEmpty()) {
			String screenCode = (String) map.get(IGenericScreenConstants.SCREEN_CODE);
			if (screenCode != null) {
				List<ICustomValidator> list = validators.get(screenCode);
				if (list != null) {
					for (ICustomValidator validator : list) {
						CustomValidationResult result = validator.validate(map, userContainer.getLocale());
						addErrorMessage(result, userContainer.getLanguageCode(), errorMessageList);
					}
				}
			}
		}

	}

	private void addErrorMessage(CustomValidationResult result,
			String languageCode, List<GenericMessage> errorMessageList) {
		if (!result.isValid()) {
			List<CustomValidationItem> items = result.getValidationItems();
			for (CustomValidationItem item : items) {
				GenericMessage genericMessage = translationService.getGenericMessage(item.getCode(),
						languageCode,
						item.getFieldName(),
						item.getControlPrefix(),
						item.getType());

				int index = item.getIndex();

				if(item.getArgs() != null){
					genericMessage.setDesc(null);
					((IErrorMessage)SessionManager.getInstance().getSessionData().getMessageStore()).addMessage(genericMessage,item.getFieldName(),
							item.getControlPrefix(), null, item.getArgs());
				}else{
					errorMessageList.add(genericMessage);
					((IErrorMessage)SessionManager.getInstance().getSessionData().getMessageStore()).addMessageMetaData(
							item.getControlPrefix(), item.getFieldName(), (index == -1 ? null : String.valueOf(index)));
				}
			}
		}
	}

	/** currentGuarantorFlag is mandatory for company director only */
	private void validateGuarantorFlag(Map<String, Object> map, String languageCode, List<GenericMessage> errorMessageList) {
		String custRoleCode = null;
		String ref2TypeCode = null;
		try{
			custRoleCode = (String)map.get(IGenericScreenConstants.APP_CUSTOMER_ROLE_CODE);
			ref2TypeCode = (String)map.get(IGenericScreenConstants.APP_CUSTOMER_REF2_TYPE_CODE);
		}catch (Exception e){
			return;
		}
		if (IConstantsWeb.APPLICANT_REFERENCE_ROLE_CODE.equals(custRoleCode) && IConstants.DIRECTOR_ROLE_CODE.equals(ref2TypeCode)) {
			Map custObj = (Map) map.get(IGenericScreenConstants.APP_CONSUMER_CUSTOMER);
			String guarantorFlag = (String)custObj.get(IGenericScreenConstants.APP_CUSTOMER_CURR_GUARANTOR_FLAG);
			if (StringUtil.isEmpty(guarantorFlag)) {
				GenericMessage genericMessage = translationService.getGenericMessage(IGenericScreenConstants.REQUIRED_ERROR_CODE,
						languageCode,
						IGenericScreenConstants.APP_CUSTOMER_CURR_GUARANTOR_FLAG,
						IGenericScreenConstants.APP_CONSUMER_CUSTOMER,
						GenericMessage.ERROR);
				errorMessageList.add(genericMessage);
				((IErrorMessage)SessionManager.getInstance().getSessionData().getMessageStore()).addMessageMetaData(
						IGenericScreenConstants.APP_CONSUMER_CUSTOMER,
						IGenericScreenConstants.APP_CUSTOMER_CURR_GUARANTOR_FLAG, null);
			}
		}
	}

	/** remove empty set of financial data from financial list, so it won't be saved */
//	private Map<String, Object> validateComFinancials(Map<String, Object> map) {
//		List<Map> finList = (List<Map>) map.get(IGenericScreenConstants.APPLICATION_COMMERCIAL_FINANCIAL_LIST);
//		List<Map> newFinList = new ArrayList<Map>();
//
//		for (Iterator<Map> iter = finList.iterator(); iter.hasNext();){
//			Map finObj = iter.next();
//
//			if (!StringUtil.isEmpty((String)finObj.get(IGenericScreenConstants.APP_COMM_FINANCIAL_STATEMENT_YEAR))) {
//				newFinList.add(finObj);
//			}
//		}
//		map.put(IGenericScreenConstants.APPLICATION_COMMERCIAL_FINANCIAL_LIST, newFinList);
//		return map;
//	}

//	private void validateAddress(Map<String, Object> map, String languageCode, ArrayList<GenericMessage> errorMessageList){
//
//		String[] addressCpc = new String[]{IGenericScreenConstants.APPLICATION_ADDRESS,IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS,IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS2
//				,IGenericScreenConstants.APPLICATION_EMPLOYMENT_ADDRESS,IGenericScreenConstants.APPLICATION_CURRENT_EMPLOYMENT2_ADDRESS,IGenericScreenConstants.APPLICATION_TRADING_ADDRESS,IGenericScreenConstants.APPLICATION_PREVIOUS_TRADING_ADDRESS};
//		GenericMessage genericMessage = null;
//
//		for(int i=0; i<addressCpc.length; i++) {
//			Map addressObj = (Map) map.get(addressCpc[i]);
//			if (addressObj != null) {
//				String suiteNumber = addressObj.getString(IJPOSScreenConstants.ADDRESS_SUITE_NUMBER_FIELD_NAME);
//				String streetNumber = addressObj.getString(IJPOSScreenConstants.ADDRESS_STREET_NUMBER_FIELD_NAME);
//				if (StringUtil.isEmpty(suiteNumber) && StringUtil.isEmpty(streetNumber)) {
//					String suiteNumberLabel = translationService.getEntityTransNameByFieldNameAndControlPrefix(IJPOSScreenConstants.ADDRESS_SUITE_NUMBER_FIELD_NAME, addressCpc[i], languageCode);
//					String streetNumberLabel = translationService.getEntityTransNameByFieldNameAndControlPrefix(IJPOSScreenConstants.ADDRESS_STREET_NUMBER_FIELD_NAME, addressCpc[i], languageCode);
//
//					genericMessage = new GenericMessage(
//							translationService.getErrorDescriptionByLanguage(IJPOSScreenConstants.EITHER_ONE_REQUIRED_ERROR_CODE, suiteNumberLabel, streetNumberLabel, languageCode)
//							, GenericMessage.ERROR, "");
//
//					errorMessageList.add(genericMessage);
//					((IErrorMessage)SessionManager.getInstance().getSessionData().getMessageStore()).addMessageMetaData(
//							addressCpc[i],
//							IJPOSScreenConstants.ADDRESS_SUITE_NUMBER_FIELD_NAME, null);
//				}
//			}
//		}
//	}
}
