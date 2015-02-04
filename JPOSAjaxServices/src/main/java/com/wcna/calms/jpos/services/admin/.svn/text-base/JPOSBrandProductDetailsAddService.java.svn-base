package com.wcna.calms.jpos.services.admin;

import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.util.BeanConverterUtil;

public class JPOSBrandProductDetailsAddService extends CalmsAjaxService {

	private IJPOSAdminProductService service = null;
	private final BeanConverterUtil beanUtil;
	public JPOSBrandProductDetailsAddService(IJPOSAdminProductService service) {
		this.service = service;
		this.beanUtil = new BeanConverterUtil();
	}



	public Object invoke(Object object) {


		if (object != null && object instanceof Map) {
			Map<String, Object> dataMap = (Map<String, Object>)object;

			String planId = (String) dataMap.get("newPlanId");
			String packageId = (String) dataMap.get("newPackageId");
			dataMap.put("planId", planId);
			dataMap.put("packageId", packageId);
			
			IJPOSProductSettingVO setting = (IJPOSProductSettingVO) beanUtil.convertMapToBean(dataMap, createBean(IJPOSProductSettingVO.class).getClass());

			service.addBrandProduct (setting);

		}
		return null;
	}

}
