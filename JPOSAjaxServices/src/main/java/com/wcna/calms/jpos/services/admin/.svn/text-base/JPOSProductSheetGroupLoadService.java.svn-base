package com.wcna.calms.jpos.services.admin;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class JPOSProductSheetGroupLoadService extends CalmsAjaxService {

	private IJPOSAdminSheetService service = null;
	public JPOSProductSheetGroupLoadService(IJPOSAdminSheetService service) {
		this.service = service;
	}

	public Object invoke(Object object) {
		
		HashMap<String,Object> retMap = new HashMap<String,Object>();

		if (object instanceof Map) {
			Map map = (Map) object;
			String productSheetGroupId = (String)map.get("productSheetGroupId");
			
			if (productSheetGroupId != null){
				IJPOSProductSheetGroup jposProdSheetGroup = service.getProductSheetGroup(productSheetGroupId);
				retMap.put("productSheetContents", jposProdSheetGroup);
			}
		}		
			

		return retMap;
	}

}
