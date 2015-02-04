package com.wcna.calms.jpos.services.customer;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.wcg.calms.service.customer.IDNumberConvertionService;
import com.wcg.product.services.session.GenericMessage;
import com.wcna.calms.jpos.services.application.IJPOSScreenConstants;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.validation.CustomValidationItem;
import com.wcna.calms.service.validation.CustomValidationResult;
import com.wcna.calms.service.validation.ICustomValidator;
import com.wcna.calms.service.validation.IRegexValidationService;
import com.wcna.lang.StringUtil;

public class CustomerPrivateIDNumberValidationService implements ICustomValidator {
	private final IRegexValidationService regexValidationService;
	private final IDNumberConvertionService idNumberConvertionService;

	public CustomerPrivateIDNumberValidationService(IRegexValidationService regexValidationService, IDNumberConvertionService idNumberConvertionService) {
		this.regexValidationService = regexValidationService;
		this.idNumberConvertionService = idNumberConvertionService;
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
		Map idProofObj = (Map) map.get(IGenericScreenConstants.APP_CUST_IDENTITY_PROOF);
		if (idProofObj != null) {
			String idNumber = (String) idProofObj.get(IGenericScreenConstants.APP_CUST_ID_NUMBER);
			String idDocType = (String) idProofObj.get(IGenericScreenConstants.APP_CUST_ID_DOC_TYPE_CODE);
			String fieldName = IGenericScreenConstants.APP_CUST_ID_NUMBER;
			if (StringUtil.isEmpty(idNumber)) {
				CustomValidationItem validationItem = new CustomValidationItem(
						IGenericScreenConstants.REQUIRED_ERROR_CODE, fieldName,
						IGenericScreenConstants.APP_CUST_IDENTITY_PROOF);
				validationItem.setIndex(0);
				validationResult.addValidationItem(validationItem);
				return validationResult;
			}

			if (IConstants.ID_DOCUMENT_TYPE_PRIVATE_ID.equals(idDocType)) {
				if(idNumber.toCharArray().length == 15){
					Map<String, String> parameters = new HashMap<String, String>();
					parameters.put("idNumber", idNumber);
					parameters.put("idDocTypeCode", idDocType);
					idNumber = (String)((Map<String, Object>)idNumberConvertionService.invoke(parameters)).get("idNumber");
				}
				if(!regexValidationService.validate(idNumber,IJPOSScreenConstants.RULE_LIBRARY_CODE_PRIVATE_ID_NUMBER)){
					CustomValidationItem validationItem = new CustomValidationItem(
							IGenericScreenConstants.INVALID_FORMAT_ERROR_CODE,
							fieldName,
							IGenericScreenConstants.APP_CUST_IDENTITY_PROOF);
					validationResult.addValidationItem(validationItem);
				}else {
					String mainPart = idNumber.substring(0, 17);
					String checkDigit = idNumber.substring(17);
					if(!checkDigit.equalsIgnoreCase(idNumberConvertionService.generateCheckDigit(mainPart.toCharArray()))){
						CustomValidationItem validationItem = new CustomValidationItem(
								IGenericScreenConstants.INVALID_FORMAT_ERROR_CODE,
								fieldName,
								IGenericScreenConstants.APP_CUST_IDENTITY_PROOF);
						validationResult.addValidationItem(validationItem);
					}
				}
			}
		}
		return validationResult;
	}
}
