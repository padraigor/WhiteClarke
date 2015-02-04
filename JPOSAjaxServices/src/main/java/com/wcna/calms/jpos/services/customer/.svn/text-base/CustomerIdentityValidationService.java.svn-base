package com.wcna.calms.jpos.services.customer;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import com.ibm.icu.util.Calendar;
import com.wcg.product.services.session.GenericMessage;
import com.wcna.calms.jpos.services.application.IJPOSScreenConstants;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.calms.service.validation.CustomValidationItem;
import com.wcna.calms.service.validation.CustomValidationResult;
import com.wcna.calms.service.validation.ICustomValidator;
import com.wcna.calms.service.validation.IRegexValidationService;
import com.wcna.lang.StringUtil;

public class CustomerIdentityValidationService implements ICustomValidator {

	private final IRegexValidationService regexValidationService;
	private IFormatService formatService;

	public CustomerIdentityValidationService(
			IRegexValidationService regexValidationService,
			IFormatService formatService) {
		this.regexValidationService = regexValidationService;
		this.formatService = formatService;
	}

	/**
	 * 1) if ID doc type is ID Card or Citizen Card, ID doc number must be
	 * numeric 2) if ID doc type is ID Card, Date Issued is mandatory 3) check
	 * mandatory here since label needs to be dynamically output
	 */
	public CustomValidationResult validate(Map<String, Object> map,
			Locale locale) {
		CustomValidationResult validationResult = new CustomValidationResult();
		String triggerAction = "";
		Object actionObj = map.get(IConstants.TR_ACTION_KEY);
		if (actionObj != null && !StringUtil.isEmpty(String.valueOf(actionObj))) {
			triggerAction = String.valueOf(actionObj);
		}
		if (IJPOSScreenConstants.ACTION_SAVE_NO_VALIDATION
				.equals(triggerAction)) {
			return validationResult;
		}
		Map idProofObj = (Map) map
				.get(IGenericScreenConstants.APP_CUST_IDENTITY_PROOF);
		if (idProofObj != null) {
			GenericMessage genericMessage = null;
			String idNumber = (String) idProofObj
					.get(IGenericScreenConstants.APP_CUST_ID_NUMBER);
			String idDocType = (String) idProofObj
					.get(IGenericScreenConstants.APP_CUST_ID_DOC_TYPE_CODE);
			String fieldName = IGenericScreenConstants.APP_CUST_ID_NUMBER;
			if (IConstants.ID_DOCUMENT_TYPE_CITIZEN_CARD.equals(idDocType))
				fieldName = IGenericScreenConstants.APP_CUST_CITIZEN_CARD;
			else if (IConstants.ID_DOCUMENT_TYPE_PASSPORT.equals(idDocType))
				fieldName = IGenericScreenConstants.APP_CUST_PASSPORT;
			else if (IConstants.ID_DOCUMENT_TYPE_RESIDENCY_TITLE
					.equals(idDocType))
				fieldName = IGenericScreenConstants.APP_CUST_RESIDENCY_TITLE;

			if (StringUtil.isEmpty(idNumber)) {
				CustomValidationItem validationItem = new CustomValidationItem(
						IGenericScreenConstants.REQUIRED_ERROR_CODE, fieldName,
						IGenericScreenConstants.APP_CUST_IDENTITY_PROOF);
				validationResult.addValidationItem(validationItem);
				/*
				 * genericMessage =
				 * translationService.getGenericMessage(IGenericScreenConstants
				 * .REQUIRED_ERROR_CODE, languageCode, fieldName,
				 * IGenericScreenConstants.APP_CUST_IDENTITY_PROOF,
				 * GenericMessage.ERROR); errorMessageList.add(genericMessage);
				 * (
				 * (IErrorMessage)SessionManager.getInstance().getSessionData().
				 * getMessageStore()).addMessageMetaData(
				 * IGenericScreenConstants.APP_CUST_IDENTITY_PROOF,
				 * IGenericScreenConstants.APP_CUST_ID_NUMBER, null);
				 */
				return validationResult;
			}

			if (IConstants.ID_DOCUMENT_TYPE_CITIZEN_CARD.equals(idDocType)) {
				try {
					Long.parseLong(idNumber);
				} catch (NumberFormatException e) {
					CustomValidationItem validationItem = new CustomValidationItem(
							IGenericScreenConstants.INVALID_FORMAT_ERROR_CODE,
							fieldName,
							IGenericScreenConstants.APP_CUST_IDENTITY_PROOF);
					validationResult.addValidationItem(validationItem);
					/*
					 * genericMessage =
					 * translationService.getGenericMessage(IGenericScreenConstants
					 * .INVALID_FORMAT_ERROR_CODE, languageCode, fieldName,
					 * IGenericScreenConstants.APP_CUST_IDENTITY_PROOF,
					 * GenericMessage.ERROR);
					 * errorMessageList.add(genericMessage);
					 * ((IErrorMessage)SessionManager
					 * .getInstance().getSessionData
					 * ().getMessageStore()).addMessageMetaData(
					 * IGenericScreenConstants.APP_CUST_IDENTITY_PROOF,
					 * IGenericScreenConstants.APP_CUST_ID_NUMBER, null);
					 */
				}
			} else if (IConstants.ID_DOCUMENT_TYPE_ID_CARD.equals(idDocType)) {
				String idCheckDigit = (String) idProofObj
						.get(IGenericScreenConstants.APP_CUST_ID_CHECK_DIGIT);
				if (StringUtil.isEmpty(idCheckDigit)) {
					CustomValidationItem validationItem = new CustomValidationItem(
							IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.APP_CUST_ID_CHECK_DIGIT,
							IGenericScreenConstants.APP_CUST_IDENTITY_PROOF);
					validationResult.addValidationItem(validationItem);
					/*
					 * genericMessage =
					 * translationService.getGenericMessage(IGenericScreenConstants
					 * .REQUIRED_ERROR_CODE, languageCode,
					 * IGenericScreenConstants.APP_CUST_ID_CHECK_DIGIT,
					 * IGenericScreenConstants.APP_CUST_IDENTITY_PROOF,
					 * GenericMessage.ERROR);
					 * errorMessageList.add(genericMessage);
					 * ((IErrorMessage)SessionManager
					 * .getInstance().getSessionData
					 * ().getMessageStore()).addMessageMetaData(
					 * IGenericScreenConstants.APP_CUST_IDENTITY_PROOF,
					 * IGenericScreenConstants.APP_CUST_ID_CHECK_DIGIT, null);
					 */
				} else {
					if (!regexValidationService.validate(idNumber
							+ idCheckDigit,
							IJPOSScreenConstants.RULE_LIBRARY_CODE_ID_CARD)) {
						CustomValidationItem validationItem = new CustomValidationItem(
								IGenericScreenConstants.INVALID_FORMAT_ERROR_CODE,
								fieldName,
								IGenericScreenConstants.APP_CUST_IDENTITY_PROOF);
						validationResult.addValidationItem(validationItem);
						/*
						 * genericMessage =
						 * translationService.getGenericMessage(
						 * IGenericScreenConstants.INVALID_FORMAT_ERROR_CODE,
						 * languageCode, fieldName,
						 * IGenericScreenConstants.APP_CUST_IDENTITY_PROOF,
						 * GenericMessage.ERROR);
						 * errorMessageList.add(genericMessage);
						 * ((IErrorMessage)
						 * SessionManager.getInstance().getSessionData
						 * ().getMessageStore()).addMessageMetaData(
						 * IGenericScreenConstants.APP_CUST_IDENTITY_PROOF,
						 * IGenericScreenConstants.APP_CUST_ID_NUMBER, null);
						 */
					}
				}
				if (StringUtil.isEmpty((String) idProofObj
						.get(IGenericScreenConstants.APP_CUST_ID_ISSUED_DATE))) {
					CustomValidationItem validationItem = new CustomValidationItem(
							IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.APP_CUST_ID_ISSUED_DATE,
							IGenericScreenConstants.APP_CUST_IDENTITY_PROOF);
					validationResult.addValidationItem(validationItem);
					/*
					 * genericMessage =
					 * translationService.getGenericMessage(IGenericScreenConstants
					 * .REQUIRED_ERROR_CODE, languageCode,
					 * IGenericScreenConstants.APP_CUST_ID_ISSUED_DATE,
					 * IGenericScreenConstants.APP_CUST_IDENTITY_PROOF,
					 * GenericMessage.ERROR);
					 * errorMessageList.add(genericMessage);
					 * ((IErrorMessage)SessionManager
					 * .getInstance().getSessionData
					 * ().getMessageStore()).addMessageMetaData(
					 * IGenericScreenConstants.APP_CUST_IDENTITY_PROOF,
					 * IGenericScreenConstants.APP_CUST_ID_ISSUED_DATE, null);
					 */
				}

			}
			expiryDateBeforeCurrent(idProofObj, validationResult, locale);

		}
		return validationResult;
	}

	private void expiryDateBeforeCurrent(Map idProofObj,
			CustomValidationResult validationResult, Locale locale) {
		String sExpiryDate = (String) idProofObj
				.get(IGenericScreenConstants.APP_CUST_ID_EXPIRY_DATE);
		String sIssueDate = (String) idProofObj
				.get(IGenericScreenConstants.APP_CUST_ID_ISSUED_DATE);
		Date expiryDate = null;
		Date issueDate = null;
		try {
			issueDate = formatService.parseDate(sIssueDate, locale, true, null);
			expiryDate = formatService.parseDate(sExpiryDate, locale, true,
					null);
		} catch (ParseException e) {
			CustomValidationItem validationItem = new CustomValidationItem(
					IGenericScreenConstants.INVALID_FORMAT_ERROR_CODE,
					IGenericScreenConstants.APP_CUST_ID_EXPIRY_DATE,
					IGenericScreenConstants.APP_CUST_IDENTITY_PROOF);
			validationResult.addValidationItem(validationItem);
		}
		Date now = DateUtils.truncate(new Date(), Calendar.DATE);
		if(issueDate==null){
			CustomValidationItem validationItem = new CustomValidationItem(
					IGenericScreenConstants.REQUIRED_ERROR_CODE,
					IGenericScreenConstants.APP_CUST_ID_ISSUED_DATE,
					IGenericScreenConstants.APP_CUST_IDENTITY_PROOF);
			validationResult.addValidationItem(validationItem);
		}
		if(expiryDate==null){
			CustomValidationItem validationItem = new CustomValidationItem(
					IGenericScreenConstants.REQUIRED_ERROR_CODE,
					IGenericScreenConstants.APP_CUST_ID_EXPIRY_DATE,
					IGenericScreenConstants.APP_CUST_IDENTITY_PROOF);
			validationResult.addValidationItem(validationItem);
		}
		if (expiryDate.before(issueDate)) {
			CustomValidationItem validationItem = new CustomValidationItem(
					IJPOSScreenConstants.IDENTITY_CARD_ISSUE_EXPIRY_DATE,
					IGenericScreenConstants.APP_CUST_ID_EXPIRY_DATE,
					IGenericScreenConstants.APP_CUST_IDENTITY_PROOF);
			validationResult.addValidationItem(validationItem);
		} else if (expiryDate.before(now)) {
			CustomValidationItem validationItem = new CustomValidationItem(
					IGenericScreenConstants.INVALID_FORMAT_ERROR_CODE,
					IGenericScreenConstants.APP_CUST_ID_EXPIRY_DATE,
					IGenericScreenConstants.APP_CUST_IDENTITY_PROOF);
			validationResult.addValidationItem(validationItem);
		}
	}

}
