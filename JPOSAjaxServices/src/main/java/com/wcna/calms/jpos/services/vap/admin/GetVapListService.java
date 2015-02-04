package com.wcna.calms.jpos.services.vap.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.jpos.services.quote.IJPOSQuoteVatService;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.util.OptionLabelValue;

public class GetVapListService extends CalmsAjaxService {

	private final IVapAdminService vapAdminService;
	private final IJPOSQuoteVatService quoteVatService;
	
	public GetVapListService(IVapAdminService vapAdminService, IJPOSQuoteVatService quoteVatService) {
		this.vapAdminService = vapAdminService;
		this.quoteVatService = quoteVatService;
	}
	
	public Object invoke(Object parameter) {
		boolean isLive = false;
		if (parameter != null && parameter instanceof Map) {
			Map<String, List<OptionLabelValue>> ret = new HashMap<String, List<OptionLabelValue>>();
			String isLiveFlag = (String) ((Map) parameter).get("isLive");
			isLive = IConstants.FLAG_YES.equals(isLiveFlag);
			List<OptionLabelValue> vapsList = vapAdminService.getVaps(isLive);
			if (vapsList != null && !vapsList.isEmpty()) {
				ret.put("vapsList", vapsList);
			}
//			String getVats = (String) ((Map) parameter).get("getVats");
//			if (IConstants.FLAG_YES.equals(getVats)) {
//				List vatRateTypeList = quoteVatService.loadVatRateTypeList();
//				if (vatRateTypeList != null && !vatRateTypeList.isEmpty()) {
//					ret.put("vatRateTypes", vatRateTypeList);
//				}				
//			}
			return ret;
		}

		return null;
	}

}
