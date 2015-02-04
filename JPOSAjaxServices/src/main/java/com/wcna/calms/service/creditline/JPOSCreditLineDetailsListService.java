package com.wcna.calms.service.creditline;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.jpos.services.creditline.IJPOSCreditLineDetailsService;

public class JPOSCreditLineDetailsListService  extends CalmsAjaxService {
	
	IJPOSCreditLineDetailsService creditLineDetailsService;
	
	JPOSCreditLineDetailsListService(IJPOSCreditLineDetailsService creditLineDetailsService){
		this.creditLineDetailsService = creditLineDetailsService;
	}
	
	public Object invoke(Object parameter) {
		
		Map <String,Object> retMap = new HashMap<String,Object>();
		if (parameter instanceof Map) {
//			Map map = (Map) parameter;
//			if (map == null) return parameter;
//			
//			String appId = (String)map.get("appId");
//					
//			if (appId==null) return null; 
			
			
			
			//retMap.put("appId", appId);
			retMap = creditLineDetailsService.getCreditLineList(this.getAppContainer().getAppID());
		}
		return retMap;
		
	}

}
