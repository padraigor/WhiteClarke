package com.wcna.calms.jpos.services.general;

import java.util.Locale;
import java.util.Map;

import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.validation.CustomValidationItem;
import com.wcna.calms.service.validation.CustomValidationResult;
import com.wcna.calms.service.validation.ICustomValidator;

public class TwoFieldEitherOrMandatoryValidationService implements
		ICustomValidator {

	private String mandatoryFieldPath = "";
	private String replacementMandatoryFieldPath = "";

	public void setMandatoryFieldPath(String mandatoryFieldPath) {
		this.mandatoryFieldPath = mandatoryFieldPath;
	}

	public void setReplacementMandatoryFieldPath(
			String replacementMandatoryFieldPath) {
		this.replacementMandatoryFieldPath = replacementMandatoryFieldPath;
	}

	public CustomValidationResult validate(Map<String, Object> map,
			Locale locale) {
		String mandatoryValue = null;
		String replacementMandatoryValue = null;

		String[] mandFieldParts = mandatoryFieldPath.split("\\.");
		String mandatoryFieldParent = mandFieldParts[0];
		String mandatoryFieldName = mandFieldParts[1];

		String[] replaceMandFieldParts = replacementMandatoryFieldPath.split("\\.");
		String replacementMandatoryFieldParent = replaceMandFieldParts[0];
		String replacementMandatoryFieldName = replaceMandFieldParts[1];

		if (map.containsKey(mandatoryFieldParent)) {
			@SuppressWarnings("unchecked")
			Map<String,Object> parent = (Map<String,Object>) map.get(mandatoryFieldParent);
			if (parent.containsKey(mandatoryFieldName)){
				mandatoryValue = (String)parent.get(mandatoryFieldName);
			}
		}
		if (map.containsKey(replacementMandatoryFieldParent)) {
			@SuppressWarnings("unchecked")
			Map<String,Object> parent = (Map<String,Object>) map.get(replacementMandatoryFieldParent);
			if (parent.containsKey(replacementMandatoryFieldName)){
				replacementMandatoryValue = (String)parent.get(replacementMandatoryFieldName);
			}
		}

		CustomValidationResult validationResult = new CustomValidationResult();
		if ((mandatoryValue == null || mandatoryValue.trim().length() == 0)
				&& (replacementMandatoryValue == null || replacementMandatoryValue
						.trim().length() == 0)) {
			String[] messageArgs = {mandatoryFieldName,replacementMandatoryFieldName};
			CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.EITHER_OR_FIELD_REQUIRED_ERROR_CODE, mandatoryFieldName,mandatoryFieldParent);
			//validationItem.setIndex(0);
			validationItem.setArgs(messageArgs);
			validationResult.addValidationItem(validationItem);
			validationItem = new CustomValidationItem(IGenericScreenConstants.EITHER_OR_FIELD_REQUIRED_ERROR_CODE, replacementMandatoryFieldName,replacementMandatoryFieldParent);
			//validationItem.setIndex(1);
			validationItem.setArgs(messageArgs);
			validationResult.addValidationItem(validationItem);
		}

		return validationResult;

	}

}
