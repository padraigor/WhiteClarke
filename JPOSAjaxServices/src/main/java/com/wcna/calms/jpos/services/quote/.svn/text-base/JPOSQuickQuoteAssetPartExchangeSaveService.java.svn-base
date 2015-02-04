package com.wcna.calms.jpos.services.quote;

import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class JPOSQuickQuoteAssetPartExchangeSaveService extends
		CalmsAjaxService {

	private final IJPOSQuickQuoteAssetService assetService;
	private final JPOSQuoteUtil jposQuoteUtil;

	public JPOSQuickQuoteAssetPartExchangeSaveService(
			IJPOSQuickQuoteAssetService assetService,
			JPOSQuoteUtil jposQuoteUtil) {
		this.assetService = assetService;
		this.jposQuoteUtil = jposQuoteUtil;
	}

	public Object invoke(Object arg0) {
		Map<String, Object> dataMap = null;

		if (arg0 != null && arg0 instanceof Map) {
			dataMap = (Map<String, Object>)arg0;
			Map<String, String> partExchangeDataMap = ((Map<String, Map>) arg0).get("partExchangeData");

			int quoteIdx = this.jposQuoteUtil.getQuoteIdx(dataMap);
			assetService.setPartExchangeToContainer(quoteIdx, partExchangeDataMap);
		}
		return null;
	}

}
