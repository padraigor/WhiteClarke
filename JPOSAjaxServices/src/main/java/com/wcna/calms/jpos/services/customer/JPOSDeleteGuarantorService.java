package com.wcna.calms.jpos.services.customer;

import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.service.customer.DeleteGuarantorService;
import com.wcna.calms.jpos.services.application.IAppButton;
import com.wcna.calms.jpos.services.application.IAppButtonKey;
import com.wcna.calms.jpos.services.application.IProposalSummaryService;
import com.wcna.calms.jpos.services.summary.JPOSProposalSummaryService;
import com.wcna.calms.util.PropertiesUtil;

public class JPOSDeleteGuarantorService extends CalmsAjaxService implements IAppButtonKey {

//	private final IProposalSummaryService proposalSummaryService;
	private final DeleteGuarantorService deleteGuarantorService;
//	private final int MAX_JOINT_APPS;
//	private final int MAX_GUARANTORS;
//	private final int MAX_DIRECTORS;
//	private final int MAX_PARTNERS;
	private String buttonKey;
	private String DELETE_COAPP_FLAG;

	public String getButtonKey() {
		return buttonKey;
	}

	public void setButtonKey(String buttonKey) {
		this.buttonKey = buttonKey;
	}

	public JPOSDeleteGuarantorService(
			DeleteGuarantorService deleteGuarantorService
			) {
//		this.proposalSummaryService = proposalSummaryService;
		this.deleteGuarantorService = deleteGuarantorService;
//		this.MAX_GUARANTORS = PropertiesUtil.getProperties(JPOSProposalSummaryService.class).getIntegerProperty("MAX_GUARANTORS");
//		this.MAX_JOINT_APPS = PropertiesUtil.getProperties(JPOSProposalSummaryService.class).getIntegerProperty("MAX_JOINT_APPS");
//		this.MAX_DIRECTORS = PropertiesUtil.getProperties(JPOSProposalSummaryService.class).getIntegerProperty("MAX_DIRECTORS");
//		this.MAX_PARTNERS = PropertiesUtil.getProperties(JPOSProposalSummaryService.class).getIntegerProperty("MAX_PARTNERS");
		this.DELETE_COAPP_FLAG = PropertiesUtil.getProperties(JPOSDeleteGuarantorService.class).getStringProperty("DELETE_COAPP_FLAG");
	}

	public Object invoke(Object arg0) {
		Map paramMap = (Map)arg0;
		paramMap.put("deleteCoAppFlag", this.DELETE_COAPP_FLAG);
		this.deleteGuarantorService.invoke(paramMap);
//		Map<String, Map<String, IAppButton>> map = this.proposalSummaryService.getProposalSummaryButtons(MAX_JOINT_APPS, MAX_GUARANTORS, MAX_DIRECTORS, MAX_PARTNERS);
		Map<String, IAppButton> ret = null;
//		if (map != null) {
//			ret = map.get(this.buttonKey);
//		}
		return ret;
	}

}
