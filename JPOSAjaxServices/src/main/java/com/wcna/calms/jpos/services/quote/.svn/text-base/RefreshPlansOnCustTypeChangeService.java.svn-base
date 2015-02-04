package com.wcna.calms.jpos.services.quote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.application.IAppContainer;
import com.wcna.calms.service.common.IConstants;

public class RefreshPlansOnCustTypeChangeService extends CalmsAjaxService {

	private final IJPOSQuickQuoteService jposQuickQuoteService;
	private final JPOSQuickQuotePlanRefreshService jposQuickQuotePlanRefreshService;
	private final JPOSQuoteUtil jposQuoteUtil;
	
	public RefreshPlansOnCustTypeChangeService(IJPOSQuickQuoteService jposQuickQuoteService, JPOSQuickQuotePlanRefreshService jposQuickQuotePlanRefreshService,
			JPOSQuoteUtil jposQuoteUtil) {
		this.jposQuickQuoteService = jposQuickQuoteService;
		this.jposQuickQuotePlanRefreshService = jposQuickQuotePlanRefreshService;
		this.jposQuoteUtil = jposQuoteUtil;
	}
	
	public Object invoke(Object arg0) {
		if (arg0 != null && arg0 instanceof Map) {
			List<Map<String, Object>> planTargets = (List<Map<String, Object>>) ((Map) arg0).get("planTargets");
			if (planTargets != null && !planTargets.isEmpty()) {
				Map<String, Object> ret = new HashMap<String, Object>();
				// for proposals, only one quote is available
				IAppContainer appContainer = this.getAppContainer();
				if (appContainer != null 
						&& IConstants.PROPOSAL_APPLICATION_TYPE.equals(jposQuickQuoteService.getApplicationType())) {
					ret.put("plan1", jposQuickQuotePlanRefreshService.invoke(planTargets.get(0)));
				} else {
					int size = planTargets.size();
					for (int i = 0; i < size; i++) {
						Map<String, Object> p = planTargets.get(i);
						if (p == null || this.noVehicleSelected(p)) {
							ret.put("plan" + (i + 1), null);
						} else {
							ret.put("plan" + (i + 1), jposQuickQuotePlanRefreshService.invoke(p));
						}
					}
				}
				return ret;
			}
		}
		return null;
	}

	private boolean noVehicleSelected(Map<String, Object> requestMap) {
		boolean bRet = true;
		int quoteIdx = this.jposQuoteUtil.getQuoteIdx(requestMap);
		IJPOSQuickQuoteAssetInputForm assetForm = this.jposQuickQuoteService.getAssetInputForm(quoteIdx);
		if (assetForm != null) {
			String ffeFlag = assetForm.getFreeFormatFlag();
			if ("on".equals(ffeFlag)) {
				// from the widget, no guarantee the ffeModelVariantId property is populated
//				if (!StringUtils.isBlank((String) vehicleMap.get("ffeModelVariantId"))) {
//					bRet = false;
//				} else if (!StringUtils.isBlank((String) vehicleMap.get("modelVariantId"))) {
//					bRet = false;
//				}
//				if (!StringUtils.isBlank(assetForm.getModelVariantId())) {
//					bRet = false;
//				}
				// for ffe vehicles, make, model, variant are not mandatory
				// so we have to rely on the fact that the user saved the vehicle dialog
				// because that's the only way the ffe flag can be checked
				bRet = false;
			} else {
				if (!StringUtils.isBlank(assetForm.getModelVariantId())) {
					bRet = false;
				}
			}
		}
		return bRet;
	}
}
