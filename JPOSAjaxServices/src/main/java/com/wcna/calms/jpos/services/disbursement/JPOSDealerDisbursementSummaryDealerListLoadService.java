package com.wcna.calms.jpos.services.disbursement;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.jpos.services.admin.IJPOSAdminDealerService;
import com.wcna.calms.service.businesspartner.IBusinessPartnerService;

public class JPOSDealerDisbursementSummaryDealerListLoadService extends CalmsAjaxService {
	private IBusinessPartnerService businessPartnerService = null;

	public JPOSDealerDisbursementSummaryDealerListLoadService(IBusinessPartnerService businessPartnerService) {
		this.businessPartnerService = businessPartnerService;
	}

	public Object invoke(Object object) {
		if (object instanceof Map) {
			Map map = (Map)object;

			Object liveOnlyUser = map.get("liveOnly");
			String liveOnly = "Y";
			if (liveOnlyUser instanceof String) {
				liveOnly = (String)liveOnlyUser;
			}

			HashMap<String,Object> retMap = new HashMap<String,Object>();

			retMap.put("dealerList", businessPartnerService.getDealersByUserDag("Y".compareToIgnoreCase((liveOnly)) == 0));

			return retMap;
		}

			return null;
	}
}