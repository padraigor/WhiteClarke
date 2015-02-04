package com.wcna.calms.jpos.services.admin;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.jpos.services.vap.admin.IVapAdminService;
import com.wcna.calms.service.businesspartner.IBusinessPartnerService;

public class JPOSDealerProductDealerListLoadService extends CalmsAjaxService {

	private IJPOSAdminPackageService service = null;
	private IJPOSAdminPlanService planService = null;
	private IBusinessPartnerService businessPartnerService = null;
	private IVapAdminService vapService = null;


	public JPOSDealerProductDealerListLoadService(IJPOSAdminPackageService service,
			IJPOSAdminPlanService planService, IBusinessPartnerService businessPartnerService, IVapAdminService vapService) {
		this.service = service;
		this.planService = planService;
		this.businessPartnerService = businessPartnerService;
		this.vapService = vapService;
	}

	public Object invoke(Object object) {
		
		if (object instanceof Map) {
			Map map = (Map) object;
			if (map == null) return object;
			
			String liveOnly = (String)map.get("liveOnly");

			HashMap<String,Object> retMap = new HashMap<String,Object>();
		
			retMap.put("dealerList", businessPartnerService.getDealers("Y".equals(liveOnly)));
			retMap.put("planList", planService.getAllPlans());
			retMap.put("packageList", service.getCommissionPackages(false));
			retMap.put("vapList", this.vapService.getVaps(false));
			retMap.put("roleList", this.vapService.getRoleTypesOLV());
			return retMap;
		}
			return null;
	}

}
