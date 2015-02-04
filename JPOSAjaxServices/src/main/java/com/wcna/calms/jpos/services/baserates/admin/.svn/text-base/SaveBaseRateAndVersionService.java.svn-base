package com.wcna.calms.jpos.services.baserates.admin;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.common.IConstants;

public class SaveBaseRateAndVersionService extends CalmsAjaxService {

	private IBaseRateAdminService baseRateAdminService;
	
	public SaveBaseRateAndVersionService(IBaseRateAdminService baseRateAdminService) {
		this.baseRateAdminService = baseRateAdminService;
	}
	
	public Object invoke(Object parameter) {
		if (parameter != null && parameter instanceof Map) {
			Map map = (Map) parameter;
			IBaseRateBean baseRateBean = this.createBean(IBaseRateBean.class);
			IBaseRateVersionBean baseRateVersionBean = this.createBean(IBaseRateVersionBean.class);
			String baseRateId = (String) map.get("baseRateId");
			String baseRateVersionId = (String) map.get("baseRateVersionId");
			
			baseRateBean.setDescription((String) map.get("description"));
			baseRateBean.setBaseRateId(baseRateId);
			
			baseRateVersionBean.setBaseRateId(baseRateId);
			baseRateVersionBean.setBaseRateVersionId(baseRateVersionId);
			baseRateVersionBean.setRate((String) map.get("rate"));
			baseRateVersionBean.setValidFrom((String) map.get("validFrom"));
			baseRateVersionBean.setValidTo((String) map.get("validTo"));
			
			if (baseRateAdminService.save(baseRateBean, baseRateVersionBean)) {
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("success", IConstants.FLAG_YES);
				ret.put("baseRates", baseRateAdminService.getBaseRatesOLV());
				return ret;
			}
		}
		return null;
	}

}
