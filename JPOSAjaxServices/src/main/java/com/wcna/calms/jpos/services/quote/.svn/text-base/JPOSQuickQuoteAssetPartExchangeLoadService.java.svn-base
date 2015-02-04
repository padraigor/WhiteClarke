package com.wcna.calms.jpos.services.quote;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class JPOSQuickQuoteAssetPartExchangeLoadService extends
		CalmsAjaxService {

	private final IJPOSQuickQuoteAssetService assetService;
	private final JPOSQuoteUtil jposQuoteUtil;

	public JPOSQuickQuoteAssetPartExchangeLoadService(
			IJPOSQuickQuoteAssetService assetService,
			JPOSQuoteUtil jposQuoteUtil) {
		this.assetService = assetService;
		this.jposQuoteUtil = jposQuoteUtil;

	}

	public Object invoke(Object parameter) {
		Map<String, Object> ret = new HashMap<String, Object>();
		if (parameter != null && parameter instanceof Map) {

			int quoteIdx = this.jposQuoteUtil.getQuoteIdx((Map) parameter);

			IAssetPartExchangeVO assetPartExchangeVO = assetService
					.getPartExchangeData(quoteIdx);

			ret.put("partExchangeData", assetPartExchangeVO);

		}
		return ret;
	}

}
