package com.wcna.calms.jpos.services.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.util.BeanConverterUtil;

public class JPOSPlanDetailsSaveService extends CalmsAjaxService {

	private IJPOSAdminPlanService service = null;
	private final BeanConverterUtil beanUtil;
	private String screenCode;

	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}

	public JPOSPlanDetailsSaveService(IJPOSAdminPlanService service) {
		this.service = service;
		this.beanUtil = new BeanConverterUtil();
	}



	public Object invoke(Object object) {


		if (object != null && object instanceof Map) {
			Map<String, Object> dataMap = (Map<String, Object>)object;

			Map<String, Object> planDetails = (Map<String, Object>) dataMap.get("planDetails");
			List<String> customerTypes = (List<String>) planDetails.remove("customerTypes");
			IJPOSPlan plan = (IJPOSPlan) beanUtil.convertMapToBean(planDetails, createBean(IJPOSPlan.class).getClass());
			plan.setCustomerTypes(customerTypes);
			Boolean createNewFlag = (Boolean)dataMap.get("createNewFlag");

			String newId = service.savePlan(createNewFlag,plan, screenCode);

			HashMap<String,Object> retMap = new HashMap<String,Object>();
			retMap.put("planId", newId);

			return retMap;
		}
		return null;
	}

}
