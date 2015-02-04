package com.wcna.calms.jpos.services.customer;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.service.app.CreateNewProposalService;
import com.wcg.calms.service.app.SummaryService;
import com.wcna.calms.component.IThirdPartyLookupService;
import com.wcna.calms.component.ITpdbUtilService;
import com.wcna.calms.service.application.IApplicationService;
import com.wcna.calms.service.thirdparty.IThirdPartyApplicationService;

public class JPOSCreateNewProposalService extends CreateNewProposalService {
	private IApplicationService applicationService;
	private IThirdPartyLookupService tpLookupService;
	private IThirdPartyApplicationService tpApplicationService;
	private ITpdbUtilService tpdbUtilService;
	private SummaryService summaryService;
	private final CalmsAjaxService proposalSummaryService;
	
	public JPOSCreateNewProposalService(IApplicationService applicationService,
			IThirdPartyLookupService tpLookupService,
			IThirdPartyApplicationService tpApplicationService,
			ITpdbUtilService tpdbUtilService, SummaryService summaryService,
			CalmsAjaxService proposalSummaryService) {
		super(applicationService, tpLookupService, tpApplicationService,
				tpdbUtilService, summaryService);
		this.proposalSummaryService = proposalSummaryService;
	}

	public Object invoke(Object object) {
		Map rtnMap = (Map)super.invoke(object);
		
		// we don't need this anymore
//		Map moduleAccessMap = new HashMap();
//		Map propSummaryMap = (Map)proposalSummaryService.invoke(null);
//		if (propSummaryMap.get("summary") != null && propSummaryMap.get("summary") instanceof Map) {
//			Object maMap = ((Map)propSummaryMap.get("summary")).get("proposalModuleAccess");
//			if (maMap != null && maMap instanceof Map)
//				moduleAccessMap = (Map)maMap;
//		}
//		
//		rtnMap.put("proposalModuleAccess", moduleAccessMap);
		
		return rtnMap;
	}
}
