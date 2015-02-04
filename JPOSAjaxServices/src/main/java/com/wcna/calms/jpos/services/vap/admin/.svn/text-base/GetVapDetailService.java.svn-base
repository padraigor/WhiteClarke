package com.wcna.calms.jpos.services.vap.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class GetVapDetailService extends CalmsAjaxService {

	private final IVapAdminService vapAdminService;
	
	public GetVapDetailService(IVapAdminService vapAdminService) {
		this.vapAdminService = vapAdminService;
	}
	
	public Object invoke(Object parameter) {
		if (parameter != null && parameter instanceof Map) {
			
			String vapIdStr = (String) ((Map) parameter).get("vapId");
			if (!StringUtils.isBlank(vapIdStr) && StringUtils.isNumeric(vapIdStr)) {
				long vapId = Long.parseLong(vapIdStr);
				IVapBean vapBean = vapAdminService.getVap(vapId);
				if (vapBean != null) {
					List<IVapCommissionBean> dealerCommissions = vapAdminService.getDealerCommissions(vapId);
					List<IVapCommissionBean> salesmanCommissions = vapAdminService.getSalesmanCommissions(vapId);
					Map<String, Object> ret = new HashMap<String, Object>();
					ret.put("vap", vapBean);
					if (dealerCommissions != null && !dealerCommissions.isEmpty()) {
						ret.put("dealerCommissions", dealerCommissions);
					}
					if (salesmanCommissions != null && !salesmanCommissions.isEmpty()) {
						ret.put("salesmanCommissions", salesmanCommissions);
					}
					return ret;
				}
			}
			
		}
		return null;
			
	}

}
