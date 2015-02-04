package com.wcna.calms.jpos.services.baserates.admin;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class GetBaseRateVersionDetailsService extends CalmsAjaxService {

	private final IBaseRateAdminService baseRateAdminService;
	
	public GetBaseRateVersionDetailsService(IBaseRateAdminService baseRateAdminService) {
		this.baseRateAdminService = baseRateAdminService;
	}
	
	public Object invoke(Object parameter) {
		if (parameter != null && parameter instanceof Map) {
			String baseRateVersionId = (String) ((Map) parameter).get("baseRateVersionId");
			String baseRateId = (String) ((Map) parameter).get("baseRateId");
			if (!StringUtils.isBlank(baseRateVersionId)
					&& StringUtils.isNumeric(baseRateVersionId)
					&& !StringUtils.isBlank(baseRateId)
					&& StringUtils.isNumeric(baseRateId)) {
				long _baseRateVersionId = Long.valueOf(baseRateVersionId);
				long _baseRateId = Long.valueOf(baseRateId);
				Map<String, IBaseRateVersionBean> ret = new HashMap<String, IBaseRateVersionBean>();
				ret.put("details", baseRateAdminService.getBaseRateVersion(_baseRateVersionId, _baseRateId));
				return ret;
				
			}
		}
		return null;
	}

}
