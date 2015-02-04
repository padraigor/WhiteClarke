package com.wcna.calms.jpos.services.admin.tieredplan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.common.IConstants;

public class TieredPlanSaveService extends CalmsAjaxService {

	private final ITieredPlanService tieredPlanService;

	public TieredPlanSaveService(ITieredPlanService tieredPlanService) {
		this.tieredPlanService = tieredPlanService;
	}

	public Object invoke(Object parameter) {
		Map<String, String> ret = new HashMap<String, String>();
		if (parameter != null && parameter instanceof Map) {
			Map map = (Map) parameter;
			String frequency = (String) map.get("frequency");
			String planId = (String) map.get("planId");
			List<TieredPlanBean> tieredPlanList = getTieredPlanList(map);
			boolean isSave = tieredPlanService.save(planId, frequency, tieredPlanList, getUserContainer().getLocale());
			ret.put("isSaveSuccess", isSave ? IConstants.FLAG_YES : IConstants.FLAG_NO);
		}
		return ret;
	}

	private List<TieredPlanBean> getTieredPlanList(Map map) {
		List<TieredPlanBean> ret = null;
		List<Map<String, String>> inputList = (List<Map<String, String>>) map.get("tieredPlanList");
		if (inputList != null && !inputList.isEmpty()) {
			ret = new ArrayList<TieredPlanBean>();
			TieredPlanBean bean;
			for (Map<String, String> input : inputList) {
				bean = new TieredPlanBean();
				ret.add(bean);

				bean.setNumPays(input.get("numPays"));
				bean.setPercentageOfPrincipal(input.get("percentageOfPrincipal"));
			}
		}
		return ret;
	}

}
