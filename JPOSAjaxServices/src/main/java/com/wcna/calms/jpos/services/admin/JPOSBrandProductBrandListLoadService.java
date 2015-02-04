package com.wcna.calms.jpos.services.admin;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class JPOSBrandProductBrandListLoadService extends CalmsAjaxService {

	private IJPOSAdminPackageService service = null;
	private IJPOSAdminPlanService planService = null;
	private IJPOSAdminDealerService dealerService = null;
	public JPOSBrandProductBrandListLoadService(IJPOSAdminPackageService service,
			IJPOSAdminPlanService planService, IJPOSAdminDealerService dealerService) {
		this.service = service;
		this.planService = planService;
		this.dealerService = dealerService;
	}

	public Object invoke(Object object) {
		
		if (object instanceof Map) {
			Map map = (Map) object;
			if (map == null) return object;
			
			HashMap<String,Object> retMap = new HashMap<String,Object>();
		
			retMap.put("brandList", dealerService.getBrands());
			retMap.put("planList", planService.getAllPlans());
			retMap.put("packageList", service.getCommissionPackages(false));

			return retMap;
		}
			return null;
	}

}
