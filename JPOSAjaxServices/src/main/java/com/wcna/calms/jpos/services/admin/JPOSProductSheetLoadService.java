package com.wcna.calms.jpos.services.admin;

import java.util.HashMap;
import java.util.List;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.util.OptionLabelValue;

public class JPOSProductSheetLoadService extends CalmsAjaxService {

	private IJPOSAdminSheetService service = null;
	public JPOSProductSheetLoadService(IJPOSAdminSheetService service) {
		this.service = service;
	}

	public Object invoke(Object object) {
		
		HashMap<String,Object> retMap = new HashMap<String,Object>();

			
			List <OptionLabelValue>list = service.getProductSheetGroups();
			
			
			retMap.put("productSheetGroupList", list);

		return retMap;
	}

}
