package com.wcna.calms.jpos.services.admin;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class JPOSPlanDetailsLoadService extends CalmsAjaxService {

	private IJPOSAdminPlanService service = null;
	public JPOSPlanDetailsLoadService(IJPOSAdminPlanService service) {
		this.service = service;
	}

	public Object invoke(Object object) {
		
		HashMap<String,Object> retMap = new HashMap<String,Object>();
		if (object instanceof Map) {
			Map map = (Map) object;
			if (map == null) return object;
			
			String planId = (String)map.get("planId");
					
			if (planId==null) return null; 
			
			IJPOSPlan plan = service.getPlan(planId);
					
			retMap.put("planDetails", plan);
		}
		return retMap;
	}

}
