package com.wcna.calms.jpos.services.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class JPOSRuleGroupDetailsLoadService extends CalmsAjaxService {

	private IJPOSAdminRuleService service = null;
	public JPOSRuleGroupDetailsLoadService(IJPOSAdminRuleService service) {
		this.service = service;
	}

	public Object invoke(Object object) {
		
		HashMap<String,Object> retMap = new HashMap<String,Object>();
		if (object instanceof Map) {
			Map map = (Map) object;
			if (map == null) return object;
			
			String ruleGroupId = (String)map.get("ruleGroupId");
					
			if (ruleGroupId==null) return null; 
			
			retMap.put("ruleGroupId", ruleGroupId);
			retMap.put("description", service.getDescription(Integer.parseInt(ruleGroupId)));
			retMap.put("ruleList", service.getRuleList(Integer.parseInt(ruleGroupId) ));
		}
		return retMap;
	}

}
