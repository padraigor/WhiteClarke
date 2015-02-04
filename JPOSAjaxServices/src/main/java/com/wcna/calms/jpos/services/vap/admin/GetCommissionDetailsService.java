package com.wcna.calms.jpos.services.vap.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class GetCommissionDetailsService extends CalmsAjaxService {

	private final IVapAdminService vapAdminService;
	
	public GetCommissionDetailsService(IVapAdminService vapAdminService) {
		this.vapAdminService = vapAdminService;
	}
	
	public Object invoke(Object parameter) {
		if (parameter != null && parameter instanceof Map) {
			String commissionVersionId = (String) ((Map) parameter).get("commissionVersionId");
			String commissionType = (String) ((Map) parameter).get("commissionType");
			String vapIdStr = (String) ((Map) parameter).get("vapId");
			if (!StringUtils.isBlank(commissionType) && !StringUtils.isBlank(commissionVersionId) && !StringUtils.isBlank(vapIdStr) && StringUtils.isNumeric(vapIdStr)) {
				long vapId = Long.valueOf(vapIdStr);
				List<IVapCommissionBean> vcbList = null;
				if ("1".equals(commissionType)) {
					vcbList = this.vapAdminService.getDealerCommissions(vapId);
				} else if ("2".equals(commissionType)) {
					vcbList = this.vapAdminService.getSalesmanCommissions(vapId);
				}
				IVapCommissionBean vcb = null;
				if (vcbList != null && !vcbList.isEmpty()) {
					for (IVapCommissionBean b : vcbList) {
						if (commissionVersionId.equals(b.getCommissionVersionId())) {
							vcb = b;
							break;
						}
					}
				}
				if (vcb != null) {
					Map<String, IVapCommissionBean> ret = new HashMap<String, IVapCommissionBean>();
					ret.put("details", vcb);
					return ret;
				}
			}
		}
		return null;
	}

}
