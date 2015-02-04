package com.wcna.calms.jpos.services.admin.residualvalue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.util.SystemException;

public class ResidualValueSaveService extends CalmsAjaxService {

	private final IResidualValueService residualValueService;

	public ResidualValueSaveService(IResidualValueService residualValueService) {
		this.residualValueService = residualValueService;
	}

	public Object invoke(Object parameter) {
		Map<String, String> payload = new HashMap<String, String>();
		if (parameter != null && parameter instanceof Map) {
			String planId = (String) ((Map) parameter).get("planId");
			List<ResidualValueBean> beanList = null;
			List<Map<String, Object>> reqList = (List<Map<String, Object>>) ((Map) parameter).get("residualValueConfig");
			if (reqList != null && !reqList.isEmpty()) {
				beanList = new ArrayList<ResidualValueBean>();
				for (Map<String, Object> map : reqList) {
					beanList.add(getResidualValueBean(map));
				}
			}

			payload.put("isSuccess", this.residualValueService.saveResidualValues(planId, beanList, this.getUserContainer().getLocale()) ? "Y" : "");

		}
		return payload;
	}

	private ResidualValueBean getResidualValueBean(Map<String, Object> map) {
		ResidualValueBean bean = new ResidualValueBean();

		bean.setApplyPercentageTo((String) map.get("applyPercentageTo"));
		bean.setBasedOn((String) map.get("basedOn"));
		bean.setCalcMethod((String) map.get("calcMethod"));
		bean.setCustomerTypes((List<String>) map.get("customerTypes"));
		bean.setPercentageToUse((String) map.get("percentageToUse"));
		bean.setSetUsed((String) map.get("setUsed"));

		return bean;
	}

}
