package com.wcna.calms.jpos.services.admin;

import java.util.HashMap;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class JPOSCPIGroupListLoadService extends CalmsAjaxService {

	private IJPOSAdminCPIService service = null;
	public JPOSCPIGroupListLoadService(IJPOSAdminCPIService service) {
		this.service = service;
	}

	public Object invoke(Object object) {
		
		HashMap<String,Object> retMap = new HashMap<String,Object>();

		retMap.put("cpiGroupList", service.getCPIProductGroups());

		return retMap;
	}

}
