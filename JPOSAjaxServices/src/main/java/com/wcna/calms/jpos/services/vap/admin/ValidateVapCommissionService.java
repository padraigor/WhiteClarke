package com.wcna.calms.jpos.services.vap.admin;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.common.IConstants;

public class ValidateVapCommissionService extends CalmsAjaxService {

	private final IVapAdminService vapAdminService;
	
	public ValidateVapCommissionService(IVapAdminService vapAdminService) {
		this.vapAdminService = vapAdminService;
	}
	
	public Object invoke(Object parameter) {
		boolean bRet = false;
		if (parameter != null && parameter instanceof Map) {
			Map<String, String> map = (Map<String, String>) parameter;
			IVapCommissionBean bean = this.createBean(IVapCommissionBean.class);
			bean.setValidFrom(map.get("validFrom"));
			bean.setValidTo(map.get("validTo"));
			bean.setValue(map.get("value"));
			bean.setCommissionVersionId(map.get("commissionVersionId"));
			bean.setVapId(map.get("vapId"));
//			bRet = vapAdminService.isCommissionBeanValid(bean);
			bRet = vapAdminService.saveCommissionVersion(bean, map.get("commissionType"));
		}
		
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("success", bRet ? IConstants.FLAG_YES : IConstants.FLAG_NO);
		return ret;
	}

}
