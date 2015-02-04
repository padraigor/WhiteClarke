package com.wcna.calms.jpos.services.admin;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class JPOSDealerProductDetailsLoadService extends CalmsAjaxService {

	private IJPOSAdminProductService service = null;
	public JPOSDealerProductDetailsLoadService(IJPOSAdminProductService service) {
		this.service = service;
	}

	public Object invoke(Object object) {
		
		HashMap<String,Object> retMap = new HashMap<String,Object>();
		if (object instanceof Map) {
			Map map = (Map) object;
			if (map == null) return object;
			
			String dealerId = (String)map.get("dealerId");
					
			if (dealerId==null || "".equals(dealerId)){
				return null;
			}  
			
			retMap.put("dealerProductList", service.getDealerProductList(Integer.valueOf(dealerId),this.getUserContainer().getLanguageCode()));
		}
		return retMap;
	}

}
