package com.wcna.calms.jpos.services.admin.residualvalue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class ResidualValueLoadService extends CalmsAjaxService {

	private final IResidualValueService residualValueService;

	public ResidualValueLoadService(IResidualValueService residualValueService) {
		this.residualValueService = residualValueService;
	}

	public Object invoke(Object parameter) {
		Map<String, List<ResidualValueBean>> payload = new HashMap<String, List<ResidualValueBean>>();
		if (parameter != null && parameter instanceof Map) {
			String planId = (String) ((Map) parameter).get("planId");
			if (!StringUtils.isBlank(planId)) {
				payload.put("rvList", residualValueService.loadResidualValues(planId, this.getUserContainer().getLocale()));
			}
		}
		return payload;
	}

}
