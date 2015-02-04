package com.wcna.calms.jpos.services.customer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.service.customer.DeleteGuarantorService;
import com.wcna.calms.data.ApplicationStatusCode;
import com.wcna.calms.jpos.services.application.IAppButton;
import com.wcna.calms.jpos.services.application.IAppButtonKey;
import com.wcna.calms.jpos.services.application.IAppButtonService;
import com.wcna.calms.jpos.services.application.IJPOSAppStatusService;
import com.wcna.calms.jpos.services.application.IProposalSummaryService;
import com.wcna.calms.jpos.services.summary.JPOSProposalSummaryService;
import com.wcna.calms.service.application.IApplicationService;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.udt.IUdtTranslationService;
import com.wcna.calms.util.PropertiesUtil;
import com.wcna.calms.service.application.ICustomerApplicantService;

public class JPOSDeleteSensitiveService extends CalmsAjaxService {

	private final IJPOSAppStatusService jposAppStatusService;
	private final IApplicationService applicationService;
	private final IAppButtonService appButtonService;
	private final IUdtTranslationService udtTranslationService;
	private final IProposalSummaryService proposalSummaryService;
	private final CalmsAjaxService deleteService;
	private final int MAX_JOINT_APPS;
	private final int MAX_GUARANTORS;
	private final int MAX_DIRECTORS;
	private final int MAX_PARTNERS;
	private final int MAX_EFFECTIVE_OWNERS;

	public JPOSDeleteSensitiveService(
			IProposalSummaryService proposalSummaryService,
			IJPOSAppStatusService jposAppStatusService,
			IApplicationService applicationService,
			IAppButtonService appButtonService,
			IUdtTranslationService udtTranslationService,
			CalmsAjaxService deleteService) {

		this.jposAppStatusService = jposAppStatusService;
		this.applicationService = applicationService;
		this.appButtonService = appButtonService;
		this.udtTranslationService = udtTranslationService;
		this.proposalSummaryService = proposalSummaryService;
		this.deleteService = deleteService;

		this.MAX_GUARANTORS = PropertiesUtil.getProperties(ICustomerApplicantService.class).getIntegerProperty("MAX_GUARANTORS");
		this.MAX_JOINT_APPS = PropertiesUtil.getProperties(ICustomerApplicantService.class).getIntegerProperty("MAX_JOINT_APPS");
		this.MAX_DIRECTORS = PropertiesUtil.getProperties(ICustomerApplicantService.class).getIntegerProperty("MAX_DIRECTORS");
		this.MAX_PARTNERS = PropertiesUtil.getProperties(ICustomerApplicantService.class).getIntegerProperty("MAX_PARTNERS");
		this.MAX_EFFECTIVE_OWNERS = PropertiesUtil.getProperties(ICustomerApplicantService.class).getIntegerProperty("MAX_EFFECTIVE_OWNERS");
	}

	public Object invoke(Object arg0) {
		Object ret = null;
		if (arg0 != null && arg0 instanceof Map) {
			Map map = (Map) arg0;
			boolean isConfirmedSensitive = false;
			Object _confirmedSen = map.get("CONFIRMED_SENSITIVE");
			if (_confirmedSen != null && _confirmedSen instanceof Boolean) {
				isConfirmedSensitive = ((Boolean) _confirmedSen).booleanValue();
			}
			boolean isProposalPastWorking = this.applicationService.isAppBeyondStatus(this.getAppContainer().getApplicationStatus(), ApplicationStatusCode.WORKING_WO);
			if (!isProposalPastWorking || isConfirmedSensitive) {
				ret = new HashMap();
//				Object superInvoke = super.invoke(arg0);
				this.deleteService.invoke(arg0);
				if (isConfirmedSensitive) {
					// trigger sensitive revert
					this.jposAppStatusService.revertApplicationStatus(ApplicationStatusCode.WORKING_WO, true, null, isConfirmedSensitive);
					((HashMap) ret).put("buttonList", this.appButtonService.getApplicationButtons());
					((HashMap) ret).put("status", this.getAppContainer().getApplicationStatus());
					((HashMap) ret).put("statusDesc", this.udtTranslationService.getUdtDesc(this.getAppContainer().getApplicationStatus(),
							IConstants.APPLICATION_STATUS_UDT_TABLE, this.getUserContainer().getCountryCode(), this.getUserContainer().getLanguageCode()));
				}
				Object buttons = null;
				if (this.deleteService instanceof IAppButtonKey) {
					String appButtonKey = ((IAppButtonKey) this.deleteService).getButtonKey();
					if (!StringUtils.isBlank(appButtonKey)) {
						Map<String, Map<String, IAppButton>> buttonMap = this.proposalSummaryService.getProposalSummaryButtons(MAX_JOINT_APPS, MAX_GUARANTORS, MAX_DIRECTORS, MAX_PARTNERS, MAX_EFFECTIVE_OWNERS);
						if (buttonMap != null) {
							buttons = buttonMap.get(appButtonKey);
						}
					}
				}
				((HashMap) ret).put("summaryButtons", buttons);
			} else {
				ret = new HashMap<String, String>();
				((HashMap) ret).put("isConfirmRequired", "Y");
			}
		}
		return ret;
	}

}
