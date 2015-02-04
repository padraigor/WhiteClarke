package com.wcna.calms.jpos.services.admin.tieredplan;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class TieredPlanLoadService extends CalmsAjaxService {

	private final ITieredPlanService tieredPlanService;

	public TieredPlanLoadService(ITieredPlanService tieredPlanService) {
		this.tieredPlanService = tieredPlanService;
	}

	public Object invoke(Object parameter) {
		Map<String, Object> ret = new HashMap<String, Object>();
		if (parameter != null && parameter instanceof Map) {
			String planId = (String) ((Map) parameter).get("planId");
			if (!StringUtils.isBlank(planId)) {
				TieredPlanContainer container = tieredPlanService.loadTieredPlanList(planId, getUserContainer().getLocale());
				if (container != null) {
					ret.put("tieredPlanList", container.getTieredPlanBeanList());
					ret.put("frequency", container.getFrequency());
				}
			}
		}
		return ret;
	}

}
