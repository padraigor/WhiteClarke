package com.wcna.calms.jpos.services.vap.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class PlanVapAssociationLoadService extends CalmsAjaxService {

	private final IVapAdminService vapAdminService;
	
	public PlanVapAssociationLoadService(IVapAdminService vapAdminService) {
		this.vapAdminService = vapAdminService;
	}
	
	public Object invoke(Object parameter) {
		if (parameter != null && parameter instanceof Map) {
			String planIdStr = (String) ((Map) parameter).get("planId");
			if (!StringUtils.isBlank(planIdStr)) {
				Map<String, List<IPlanVapAssociationBean>> ret = new HashMap<String, List<IPlanVapAssociationBean>>();
				ret.put("planVaps", this.vapAdminService.getPlanVapAssociations(planIdStr));
				return ret;
			}
		}
		return null;
	}

}
