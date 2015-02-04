package com.wcna.calms.jpos.services.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.util.BeanConverterUtil;

public class JPOSCPIGroupDetailsSaveService extends CalmsAjaxService {

	private IJPOSAdminCPIService service = null;
	private final BeanConverterUtil beanUtil;
	public JPOSCPIGroupDetailsSaveService(IJPOSAdminCPIService service) {
		this.service = service;
		this.beanUtil = new BeanConverterUtil();
	}

	public Object invoke(Object object) {


		if (object != null && object instanceof Map) {
			Map<String, Object> dataMap = (Map<String, Object>)object;

			Map<String, Object> cpiDetails = (Map<String, Object>) dataMap.get("cpiDetails");
			
			String cpiGroup = (String) cpiDetails.get("cpiGroupId");
			int cpiGroupId = 0;
			if (cpiGroup!= null && !"".equals(cpiGroup)){
				cpiGroupId = Integer.parseInt(cpiGroup);
			}

			String description = (String)cpiDetails.get("description");

			List rlArray = (List)cpiDetails.get("cpiList");

			List<IJPOSCPI> cpiList = new ArrayList<IJPOSCPI>();

			if (rlArray!=null){
				Object[] cpis = rlArray.toArray();
				if (cpis != null && cpis.length > 0) {
					for(int i=0; i<cpis.length; i++) {
						Map jsonObject = (Map) cpis[i];
						IJPOSCPI cpi = (IJPOSCPI) beanUtil.convertMapToBean(jsonObject, createBean(IJPOSCPI.class).getClass());
						cpiList.add(cpi);
					}
				}
			}

			int newId = service.saveCPIGroup(cpiGroupId, description, cpiList);

			HashMap<String,Object> retMap = new HashMap<String,Object>();
			retMap.put("cpiGroupId", newId+"");

			return retMap;

		}
		return null;

	}

}
