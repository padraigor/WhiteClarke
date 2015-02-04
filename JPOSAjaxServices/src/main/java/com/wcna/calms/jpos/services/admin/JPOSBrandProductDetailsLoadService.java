package com.wcna.calms.jpos.services.admin;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class JPOSBrandProductDetailsLoadService extends CalmsAjaxService {

	private IJPOSAdminProductService service = null;
	public JPOSBrandProductDetailsLoadService(IJPOSAdminProductService service) {
		this.service = service;
	}

	public Object invoke(Object object) {
		
		HashMap<String,Object> retMap = new HashMap<String,Object>();
		if (object instanceof Map) {
			Map map = (Map) object;
			if (map == null) return object;
			
			String brandId = (String)map.get("brandId");
					
			if (brandId==null || "".equals(brandId)){
				return null;
			}  
			
			retMap.put("brandProductList", service.getBrandProductList(Integer.valueOf(brandId)));
		}
		return retMap;
	}

}
