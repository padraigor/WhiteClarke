package com.wcna.calms.jpos.services.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.format.IFormatService;

public class JPOSProductSheetValidDatesLoadService extends CalmsAjaxService {

	private IJPOSAdminSheetService service = null;
	private IFormatService formatService = null;
	
	public JPOSProductSheetValidDatesLoadService(IJPOSAdminSheetService service, IFormatService formatService) {
		this.service = service;
		this.formatService = formatService;
	}

	public Object invoke(Object object) {
		
		HashMap<String,Object> retMap = new HashMap<String,Object>();

		if (object instanceof Map) {
			Map map = (Map) object;
			String productSheetGroupId = (String)map.get("productSheetGroupId");
			
			if (productSheetGroupId != null){
				List<IJPOSProductSheet> jposProductSheetValidDatesList = service.getProductSheetValidDateList(productSheetGroupId);
				retMap.put("productSheetValidDatesList", jposProductSheetValidDatesList);
			}
		}		
			

		return retMap;
	}

}
