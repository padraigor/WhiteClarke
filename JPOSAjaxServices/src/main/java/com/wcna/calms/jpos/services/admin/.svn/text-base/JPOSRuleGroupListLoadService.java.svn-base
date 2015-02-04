package com.wcna.calms.jpos.services.admin;

import java.util.HashMap;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class JPOSRuleGroupListLoadService extends CalmsAjaxService {

	private IJPOSAdminRuleService service = null;
	public JPOSRuleGroupListLoadService(IJPOSAdminRuleService service) {
		this.service = service;
	}

	public Object invoke(Object object) {
		
		HashMap<String,Object> retMap = new HashMap<String,Object>();

		retMap.put("ruleGroupList", service.getAllRuleGroups());

		return retMap;
	}

}
