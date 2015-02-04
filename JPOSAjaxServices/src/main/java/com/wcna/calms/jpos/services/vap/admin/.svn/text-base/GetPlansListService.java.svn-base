package com.wcna.calms.jpos.services.vap.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.util.OptionLabelValue;

public class GetPlansListService extends CalmsAjaxService {

	private final IVapAdminService vapAdminService;
	
	public GetPlansListService(IVapAdminService vapAdminService) {
		this.vapAdminService = vapAdminService;
	}
	
	public Object invoke(Object parameter) {
		Map<String, List<OptionLabelValue>> ret = new HashMap<String, List<OptionLabelValue>>();
		ret.put("plans", this.vapAdminService.getPlansOLV());
		ret.put("vaps", this.vapAdminService.getVaps(false));
		ret.put("roles", this.vapAdminService.getRoleTypesOLV());
		return ret;
	}

}
