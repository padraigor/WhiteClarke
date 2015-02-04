package com.wcna.calms.jpos.services.admin;

import java.util.HashMap;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class JPOSPlanListLoadService extends CalmsAjaxService {

	private IJPOSAdminRuleService ruleService = null;
	private IJPOSAdminPlanService planService = null;
	public JPOSPlanListLoadService(IJPOSAdminPlanService planService, IJPOSAdminRuleService ruleService) {
		this.ruleService = ruleService;
		this.planService = planService;
	}

	public Object invoke(Object object) {
		
		HashMap<String,Object> retMap = new HashMap<String,Object>();
			
		retMap.put("planList", planService.getAllPlans());
		retMap.put("ruleGroupList", ruleService.getAllRuleGroups());
		retMap.put("financeTypeList", planService.getFinanceTypes());

		return retMap;
	}

}
