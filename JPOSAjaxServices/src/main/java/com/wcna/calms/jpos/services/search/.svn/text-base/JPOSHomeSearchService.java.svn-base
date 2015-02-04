package com.wcna.calms.jpos.services.search;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.jpos.services.application.IJPOSScreenConstants;
import com.wcna.calms.service.application.IApplicationService;
import com.wcna.calms.service.common.IConstants;

public class JPOSHomeSearchService extends CalmsAjaxService {

	private final BasicAdvancedSearchService basicAdvancedSearchService;
	private final IApplicationService applicationService;
	
	public JPOSHomeSearchService(BasicAdvancedSearchService basicAdvancedSearchService, IApplicationService applicationService) {
		this.basicAdvancedSearchService = basicAdvancedSearchService;
		this.applicationService = applicationService;
	}
	
	public Object invoke(Object parameter) {
		Map<String, Map> m_out = new HashMap<String, Map>();
		Map<String, Object> m_in = new HashMap<String, Object>();
//		List<String> statusList = (List<String>) ((Map) parameter).get("statusList");
		Map<String, List<String>> statusList = applicationService.getApplicationStatusPanels();
		
		// search 10 most recent proposals
		m_in.put("numberOfRecords", "10");
		m_in.put("searchTypeCode", IConstants.PROPOSAL_APPLICATION_TYPE);
		m_out.put("recent_proposals", (Map) basicAdvancedSearchService.invoke(m_in));
		
		// search 10 most recent quotes
		m_in.put("searchTypeCode", IConstants.LEAD_APPLICATION_TYPE);
		m_out.put("recent_quotes", (Map) basicAdvancedSearchService.invoke(m_in));
		
		m_in.put("searchTypeCode", IConstants.PROPOSAL_APPLICATION_TYPE);
		if (statusList != null && !statusList.isEmpty()) {
//			if (statusList.size() > 0) {
//				m_in.put("status", statusList.get(0));
//				m_out.put("additionalStatus1", (Map) basicAdvancedSearchService.invoke(m_in));
//			}
//			
//			if (statusList.size() > 1) {
//				m_in.put("status", statusList.get(1));
//				m_out.put("additionalStatus2", (Map) basicAdvancedSearchService.invoke(m_in));
//			}
			
			for (Iterator<String> it = statusList.keySet().iterator(); it.hasNext();) {
				String panel = it.next();
				if (IJPOSScreenConstants.SCREEN_PANEL.PANEL3.getPanel().equals(panel)) {
					m_in.put("statuses", statusList.get(panel));
					m_out.put("additionalStatus1", (Map) basicAdvancedSearchService.invoke(m_in));
				} else if (IJPOSScreenConstants.SCREEN_PANEL.PANEL4.getPanel().equals(panel)) {
					m_in.put("statuses", statusList.get(panel));
					m_out.put("additionalStatus2", (Map) basicAdvancedSearchService.invoke(m_in));
				}
			}
		}
		
		return m_out;
	}

}
