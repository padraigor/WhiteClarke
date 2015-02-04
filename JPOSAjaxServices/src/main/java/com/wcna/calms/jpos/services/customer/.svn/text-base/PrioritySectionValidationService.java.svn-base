package com.wcna.calms.jpos.services.customer;

import java.util.Locale;
import java.util.Map;

import com.wcg.product.calms.security.CalmsSessionData;
import com.wcg.product.services.session.GenericMessage;
import com.wcg.product.services.session.SessionManager;
import com.wcna.calms.jpos.services.application.IJPOSScreenConstants;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.validation.CustomValidationItem;
import com.wcna.calms.service.validation.CustomValidationResult;
import com.wcna.calms.service.validation.ICustomValidator;
import com.wcna.calms.service.validation.IRegexValidationService;
import com.wcna.lang.StringUtil;
import com.wcna.calms.jpos.services.customer.CustomerPrioritySectionShowHideService;

public class PrioritySectionValidationService implements ICustomValidator {
	private final CustomerPrioritySectionShowHideService showHideService;
	private final IRegexValidationService regexValidationService;

	public PrioritySectionValidationService(CustomerPrioritySectionShowHideService showHideService, IRegexValidationService regexValidationService) {
		this.showHideService = showHideService;
		this.regexValidationService = regexValidationService;
	}

	/**
	 * If user is an internal user, priority section will show up
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
		CalmsSessionData sessionData = (CalmsSessionData) SessionManager.getInstance().getSessionData();
		if(showHideService.condition(sessionData.getUserContainer())){
			Map customer = (Map)map.get(IGenericScreenConstants.APP_CONSUMER_CUSTOMER);
			String root = IGenericScreenConstants.APP_CONSUMER_CUSTOMER;
			if(customer == null){
				customer = (Map)map.get(IGenericScreenConstants.APP_COMPANY_CUSTOMER);
				root = IGenericScreenConstants.APP_COMPANY_CUSTOMER;
			}
			if(customer != null){
				String priority = (String)customer.get(IGenericScreenConstants.APPLICATION_CUSTOMER_PRIORITY);
				String contributionToServiceFee = (String)customer.get(IGenericScreenConstants.APPLICATION_CUSTOMER_CONTRIBUTION_TO_SERVICE_FEE);
				if (StringUtil.isEmpty(priority)) {
					CustomValidationItem validationItem = new CustomValidationItem(
							IGenericScreenConstants.REQUIRED_ERROR_CODE, IGenericScreenConstants.APPLICATION_CUSTOMER_PRIORITY, root);
					validationResult.addValidationItem(validationItem);
				}
				if (StringUtil.isEmpty(contributionToServiceFee)) {
					CustomValidationItem validationItem = new CustomValidationItem(
							IGenericScreenConstants.REQUIRED_ERROR_CODE, IGenericScreenConstants.APPLICATION_CUSTOMER_CONTRIBUTION_TO_SERVICE_FEE, root);
					validationResult.addValidationItem(validationItem);
				}
			}
		}
		return validationResult;
	}
}
