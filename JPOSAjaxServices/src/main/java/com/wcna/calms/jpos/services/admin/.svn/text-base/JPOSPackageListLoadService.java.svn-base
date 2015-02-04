package com.wcna.calms.jpos.services.admin;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class JPOSPackageListLoadService extends CalmsAjaxService {

	private IJPOSAdminPackageService service = null;
	private IJPOSAdminSheetService sheetService = null;
	public JPOSPackageListLoadService(IJPOSAdminPackageService service, IJPOSAdminSheetService sheetService) {
		this.service = service;
		this.sheetService = sheetService;
	}

	public Object invoke(Object object) {
		
		if (object instanceof Map) {
			Map map = (Map) object;
			if (map == null) return object;
			
			String liveOnly = (String)map.get("liveOnly");

			HashMap<String,Object> retMap = new HashMap<String,Object>();
		
			retMap.put("packageList", service.getCommissionPackages("Y".equals(liveOnly)));

			retMap.put("productSheetGroupList", sheetService.getProductSheetGroups());

			return retMap;
		}
			return null;
	}

}
