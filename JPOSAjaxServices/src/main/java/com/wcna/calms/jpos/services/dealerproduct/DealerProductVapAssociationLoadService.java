package com.wcna.calms.jpos.services.dealerproduct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.jpos.services.dealerproduct.IDealerProductVapAssociationBean;
import com.wcna.calms.jpos.services.dealerproduct.IDealerProductVapService;

public class DealerProductVapAssociationLoadService extends CalmsAjaxService {

	private final IDealerProductVapService dealerProductVapService;

	public DealerProductVapAssociationLoadService(IDealerProductVapService dealerProductVapService) {
		this.dealerProductVapService = dealerProductVapService;
	}

	public Object invoke(Object parameter) {
		if (parameter != null && parameter instanceof Map) {
			String dealerProductId = (String)((Map) parameter).get("dealerProductId");
			if (!StringUtils.isBlank(dealerProductId)) {
				Map<String, List<IDealerProductVapAssociationBean>> ret = new HashMap<String, List<IDealerProductVapAssociationBean>>();
				ret.put("dealerProductVaps", this.dealerProductVapService.getDealerProductVapAssociations(dealerProductId));
				return ret;
			}
		}
		return null;
	}

}
