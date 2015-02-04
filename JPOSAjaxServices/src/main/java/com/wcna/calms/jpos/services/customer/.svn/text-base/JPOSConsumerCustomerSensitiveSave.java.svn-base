package com.wcna.calms.jpos.services.customer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.common.constants.IServiceConstants;
import com.wcg.calms.service.app.SummaryService;
import com.wcna.calms.data.ApplicationStatusCode;
import com.wcna.calms.jpos.services.application.IJPOSAppStatusService;
import com.wcna.calms.service.application.IApplicationService;
import com.wcna.calms.service.customer.IGenericConsumerService;
import com.wcna.framework.sensitive.ISensitiveLoadService;

public class JPOSConsumerCustomerSensitiveSave extends JPOSConsumerCustomerSave {

	private final IApplicationService applicationService;
	private final IJPOSAppStatusService jposApplicationService;
	
	
	public JPOSConsumerCustomerSensitiveSave(
			IGenericConsumerService consumerService,
			ISensitiveLoadService loadService,
			IApplicationService applicationService,
			SummaryService summaryService, CalmsAjaxService preSaveService,
			CalmsAjaxService postSaveService,
			IJPOSAppStatusService jposApplicationService) {
		super(consumerService, loadService, applicationService, summaryService,
				preSaveService, postSaveService);
		this.applicationService = applicationService;
		this.jposApplicationService = jposApplicationService;
	}
	
	@Override
	public Object invoke(Object arg0) {
		Object ret = null;
		if (arg0 != null && arg0 instanceof Map) {
			long refId = 0;
			Map map = (Map) arg0;
			Object refIdStr = map.get(IServiceConstants.KEY_ID);
			if (refIdStr != null) {
				String _string = String.valueOf(refIdStr);
				if (!StringUtils.isBlank(_string)) {
					refId = Long.valueOf(_string);
				}
			}
			boolean isConfirmedSensitive = false;
			Object _confirmedSen = map.get("CONFIRMED_SENSITIVE");
			if (_confirmedSen != null && _confirmedSen instanceof Boolean) {
				isConfirmedSensitive = ((Boolean) _confirmedSen).booleanValue();
			}
			// if adding new customer
			if (refId == 0) {
				boolean isProposalPastWorking = this.applicationService.isAppBeyondStatus(this.getAppContainer().getApplicationStatus(), ApplicationStatusCode.WORKING_WO);
				if (!isProposalPastWorking || isConfirmedSensitive) {
					ret = super.invoke(arg0);
					if (isConfirmedSensitive) {
						// trigger sensitive revert
						this.jposApplicationService.revertApplicationStatus(ApplicationStatusCode.WORKING_WO, true, this.getScreenCode(), isConfirmedSensitive);
					}
				} else {
					ret = new HashMap<String, String>();
					((HashMap) ret).put("isConfirmRequired", "Y");
				}
			} else {
				ret = super.invoke(arg0);
			}
		}
		return ret;
	}

}
