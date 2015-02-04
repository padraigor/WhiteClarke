package com.wcna.calms.jpos.services.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class JPOSCPIGroupDetailsLoadService extends CalmsAjaxService {

	private IJPOSAdminCPIService service = null;
	public JPOSCPIGroupDetailsLoadService(IJPOSAdminCPIService service) {
		this.service = service;
	}

	public Object invoke(Object object) {
		
		HashMap<String,Object> retMap = new HashMap<String,Object>();
		if (object instanceof Map) {
			Map map = (Map) object;
			if (map == null) return object;
			
			String cpiGroupId = (String)map.get("cpiGroupId");
					
			if (cpiGroupId==null) return null; 
			
			retMap.put("cpiGroupId", cpiGroupId);
			retMap.put("description", service.getDescription(Integer.parseInt(cpiGroupId)));
			retMap.put("cpiList", service.getCPIList(Integer.parseInt(cpiGroupId) ));
		}
		return retMap;
	}

}
