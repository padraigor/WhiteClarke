package com.wcna.calms.jpos.services.admin;

import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.util.BeanConverterUtil;

public class JPOSBrandProductDetailsDeleteService extends CalmsAjaxService {

	private IJPOSAdminProductService service = null;
	private final BeanConverterUtil beanUtil;
	public JPOSBrandProductDetailsDeleteService(IJPOSAdminProductService service) {
		this.service = service;
		this.beanUtil = new BeanConverterUtil();
	}



	public Object invoke(Object object) {


		if (object != null && object instanceof Map) {
			Map<String, Object> dataMap = (Map<String, Object>)object;



			List brandProductList = beanUtil.convertMapsToBeanList((List) dataMap.get("brandProductList"), createBean(IJPOSProductSettingVO.class));

			service.deleteBrandProductList(brandProductList);
		}
		return null;
	}

}
