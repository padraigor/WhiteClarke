package com.wcna.calms.jpos.services.usura.admin;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class UsuraAdminLoadService extends CalmsAjaxService {

	private final IUsuraAdminService usuraAdminService;
	
	public UsuraAdminLoadService(IUsuraAdminService usuraAdminService) {
		this.usuraAdminService = usuraAdminService;
	}
	
	public Object invoke(Object parameter) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("financeTypes", usuraAdminService.getFinanceTypesOLV());
		ret.put("usuraVersions", usuraAdminService.getUsuraVersionsOLV());
		return ret;
	}

}
