package com.wcna.calms.jpos.services.admin;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class JPOSGroupProductDetailsLoadService extends CalmsAjaxService {

	private IJPOSAdminProductService service = null;
	public JPOSGroupProductDetailsLoadService(IJPOSAdminProductService service) {
		this.service = service;
	}

	public Object invoke(Object object) {
		
		HashMap<String,Object> retMap = new HashMap<String,Object>();
		if (object instanceof Map) {
			Map map = (Map) object;
			if (map == null) return object;
			
			String groupId = (String)map.get("groupId");
					
			if (groupId==null || "".equals(groupId)){
				return null;
			}  
			
			retMap.put("groupProductList", service.getGroupProductList(Integer.valueOf(groupId)));
		}
		return retMap;
	}

}
