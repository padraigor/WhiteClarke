package com.wcna.calms.jpos.services.dealerinfo;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.security.IOperationalStatusData;
import com.wcna.calms.service.security.IOperationalStatusService;

public class GetDealerInfoMessage extends CalmsAjaxService {

	private final IOperationalStatusService operationalStatusService;
	
	public GetDealerInfoMessage(IOperationalStatusService operationalStatusService) {
		this.operationalStatusService = operationalStatusService;
	}
	
	public Object invoke(Object arg0) {
		
		IOperationalStatusData data = operationalStatusService.getOperationalStatusData();
		Map<String, String> out = new HashMap<String, String>();
		out.put("theMessage", data.getWelcomeMessage());
		
		return out;
	}

}
