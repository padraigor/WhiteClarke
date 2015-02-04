package com.wcna.calms.jpos.services.baserates.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.util.OptionLabelValue;

public class GetBaseRatesService extends CalmsAjaxService {

	private final IBaseRateAdminService baseRateAdminService;
	
	public GetBaseRatesService(IBaseRateAdminService baseRateAdminService) {
		this.baseRateAdminService = baseRateAdminService;
	}
	
	public Object invoke(Object parameter) {
		Map<String, List<OptionLabelValue>> ret = new HashMap<String, List<OptionLabelValue>>();
		ret.put("baseRates", baseRateAdminService.getBaseRatesOLV());
		return ret;
	}

}
