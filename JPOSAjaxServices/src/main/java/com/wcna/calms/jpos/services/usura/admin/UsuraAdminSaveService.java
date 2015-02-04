package com.wcna.calms.jpos.services.usura.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.common.IConstants;

public class UsuraAdminSaveService extends CalmsAjaxService {

	private final IUsuraAdminService usuraAdminService;

	public UsuraAdminSaveService(IUsuraAdminService usuraAdminService) {
		this.usuraAdminService = usuraAdminService;
	}

	public Object invoke(Object parameter) {
		if (parameter != null && parameter instanceof Map) {
			Map map = (Map) parameter;
			IUsuraVersionBean usuraVersion = this.createBean(IUsuraVersionBean.class);
			usuraVersion.setVersionId((String) map.get("usuraVersionId"));
			usuraVersion.setValidFrom((String) map.get("validFrom"));
			usuraVersion.setValidTo((String) map.get("validTo"));

			List<IUsuraLimitBean> usuraLimits = new ArrayList<IUsuraLimitBean>();
			usuraVersion.setUsuraLimits(usuraLimits);

			List<Map<String, String>> limitsMap = (List<Map<String, String>>) map.get("usuraRecords");
			if (limitsMap != null && !limitsMap.isEmpty()) {
				for (Map<String, String> p : limitsMap) {
					IUsuraLimitBean limit = this.createBean(IUsuraLimitBean.class);
					usuraLimits.add(limit);

					limit.setAgeCeiling(p.get("maxAge"));
					limit.setTaegValue(p.get("taeg"));
					limit.setFinanceTypeId(p.get("finTypeId"));
					limit.setExtCustomerTypeId(p.get("extCustomerTypeId"));
					limit.setAmountFinanced(p.get("amountFinanced"));
				}
			}

			boolean bSave = this.usuraAdminService.save(usuraVersion);
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("successful", bSave ? IConstants.FLAG_YES : "");
			if (bSave) {
				ret.put("usuraVersions", this.usuraAdminService.getUsuraVersionsOLV());
			}
			return ret;
		}
		return null;
	}

}
