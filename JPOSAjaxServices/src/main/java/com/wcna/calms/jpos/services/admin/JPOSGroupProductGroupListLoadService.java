package com.wcna.calms.jpos.services.admin;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class JPOSGroupProductGroupListLoadService extends CalmsAjaxService {

	private IJPOSAdminPackageService service = null;
	private IJPOSAdminPlanService planService = null;
	private IJPOSAdminDealerService dealerService = null;
	public JPOSGroupProductGroupListLoadService(IJPOSAdminPackageService service,
			IJPOSAdminPlanService planService, IJPOSAdminDealerService dealerService) {
		this.service = service;
		this.planService = planService;
		this.dealerService = dealerService;
	}

	public Object invoke(Object object) {
		
		if (object instanceof Map) {
			Map map = (Map) object;
			if (map == null) return object;
			
			String liveOnly = (String)map.get("liveOnly");

			HashMap<String,Object> retMap = new HashMap<String,Object>();
		
			retMap.put("groupList", dealerService.getGroups("Y".equals(liveOnly)));
			retMap.put("planList", planService.getAllPlans());
			retMap.put("packageList", service.getCommissionPackages(false));

			return retMap;
		}
			return null;
	}

}
