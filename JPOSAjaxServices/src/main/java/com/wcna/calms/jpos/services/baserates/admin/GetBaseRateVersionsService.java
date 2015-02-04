package com.wcna.calms.jpos.services.baserates.admin;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class GetBaseRateVersionsService extends CalmsAjaxService {

	private final IBaseRateAdminService baseRateAdminService;
	
	public GetBaseRateVersionsService(IBaseRateAdminService baseRateAdminService) {
		this.baseRateAdminService = baseRateAdminService;
	}
	
	public Object invoke(Object parameter) {
		if (parameter != null && parameter instanceof Map) {
			String baseRateId = (String) ((Map) parameter).get("baseRateId");
			if (!StringUtils.isBlank(baseRateId) 
					&& StringUtils.isNumeric(baseRateId)) {
				long _baseRateId = Long.valueOf(baseRateId);
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("baseRateVersions", baseRateAdminService.getBaseRateVersionsOLV(_baseRateId));
				IBaseRateBean bean = baseRateAdminService.getBaseRate(_baseRateId);
				if (bean != null) {
					ret.put("description", bean.getDescription());
				}
				return ret;
			}
		}
		return null;
	}

}
