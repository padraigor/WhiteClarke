package com.wcna.calms.jpos.services.admin;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class JPOSPackageVersionListLoadService extends CalmsAjaxService {

	private IJPOSAdminPackageService service = null;
	public JPOSPackageVersionListLoadService(IJPOSAdminPackageService service) {
		this.service = service;
	}

	public Object invoke(Object object) {
		
		if (object instanceof Map) {
			Map map = (Map) object;
			if (map == null) return object;
			
			String packageIdString = (String)map.get("packageId");
			
			int packageId = 0;
			if (packageIdString != null && !"".equals(packageIdString)){
				packageId = Integer.valueOf(packageIdString).intValue();
			}

			HashMap<String,Object> retMap = new HashMap<String,Object>();
		
			retMap.put("versionList", service.getVersionCommissionPackages(packageId));

			return retMap;
		}
			return null;
	}

}
