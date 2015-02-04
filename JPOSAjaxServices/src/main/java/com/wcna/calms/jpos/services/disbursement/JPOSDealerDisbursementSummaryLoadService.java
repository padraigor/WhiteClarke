package com.wcna.calms.jpos.services.disbursement;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.disbursement.IDisbursementService;

public class JPOSDealerDisbursementSummaryLoadService extends CalmsAjaxService {
	private IDisbursementService service = null;

	public JPOSDealerDisbursementSummaryLoadService(IDisbursementService service) {
		this.service = service;
	}

	public Object invoke(Object object) {
		Object retMap = new HashMap<String, Object>();

		if (object instanceof Map) {
			Map map = (Map) object;

			String dealerId = (String)map.get("dealerId");

			if (dealerId==null || "".equals(dealerId)){
				return null;
			}

			retMap = service.getDealerDisbursementSummary(new Long(dealerId), this.getUserContainer().getLanguageCode());
		}

		return retMap;
	}
}