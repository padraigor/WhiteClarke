package com.wcna.calms.jpos.services.customer;

import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.common.constants.IServiceConstants;
import com.wcg.product.services.session.SessionManager;
import com.wcna.calms.jpos.services.application.IJPOSScreenConstants;
import com.wcna.calms.log.Logger;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.customer.ICustomerService;
import com.wcna.calms.service.customer.IGenericScreenForm;
import com.wcna.calms.service.error.IErrorMessage;
import com.wcna.framework.validation.IValidationService;
import com.wcna.lang.StringUtil;

public class CustomerPostSaveService extends CalmsAjaxService {

	private final ICustomerService customerService;
	private final IValidationService validationService;
	private final CalmsAjaxService preSaveService;
	
	public CustomerPostSaveService(ICustomerService customerService, IValidationService validationService,
			CalmsAjaxService preSaveService) {
		this.customerService = customerService;
		this.validationService = validationService;
		this.preSaveService = preSaveService;
	}

	public Object invoke(Object object) {
		long refId = 0;
		boolean validated = true;
		String triggerAction = "";
		
		if(object instanceof Map) { 
			Map input = (Map)object;
			Object refIdObj = input.get(IServiceConstants.KEY_ID);
			if (refIdObj != null && !StringUtil.isEmpty(String.valueOf(refIdObj))) 
				refId = new Long(String.valueOf(refIdObj));
			
			Object trAction = input.get(IConstants.TR_ACTION_KEY);
			if (trAction != null && !StringUtil.isEmpty(String.valueOf(trAction)))
				triggerAction = String.valueOf(trAction);

			/** if saving without validation, pass empty triggerAction (default is "SAVE") and validate again 
			 * just to determine the systemValidatedFlag, so clear all messages */
			if (IJPOSScreenConstants.ACTION_SAVE_NO_VALIDATION.equals(triggerAction)) {
				input.put(IConstants.TR_ACTION_KEY, "");
				String screenCode = (String)input.get(IGenericScreenConstants.SCREEN_CODE);
				IGenericScreenForm form = createBean(IGenericScreenForm.class);
				IErrorMessage errMsg = validationService.validate(form, input, screenCode
						, getUserContainer().getLanguageCode(), getUserContainer().getCountryCode());
				if (errMsg.getErrorCount() > 0) {					
					validated = false;
					SessionManager.getInstance().getSessionData().setMessageStore(null);
				} else if (preSaveService != null) {
					preSaveService.invoke(input);
					if (!SessionManager.getInstance().getSessionData().getMessageStore().getMessages().isEmpty()) {
						validated = false;
						SessionManager.getInstance().getSessionData().setMessageStore(null);
					}
				}
			}
		}

		long appId = getAppContainer().getAppID();		
		customerService.setSystemValidatedFlag(appId, refId, validated);
		
		Logger.debug(this.getClass() + " -- App Id: " + appId + ", -- Ref Id: " + refId + ", -- SystemValidatedFlag: " + validated);
		return null;
	}

}
