package com.wcna.calms.jpos.services.settlement;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.jpos.services.quote.IJPOSQuickQuoteService;
import com.wcna.calms.jpos.services.quote.JPOSQuoteUtil;
import com.wcna.calms.service.format.IFormatService;

public class SettlementQuoteAcceptService extends CalmsAjaxService {

	private final JPOSQuoteUtil jposQuoteUtil;
	private final IJPOSQuickQuoteService jposQuickQuoteService;

	public SettlementQuoteAcceptService(JPOSQuoteUtil jposQuoteUtil, IJPOSQuickQuoteService jposQuickQuoteService) {
		this.jposQuoteUtil = jposQuoteUtil;
		this.jposQuickQuoteService = jposQuickQuoteService;
	}

	public Object invoke(Object parameter) {
		Map<String, String> ret = new HashMap<String, String>();
		if (parameter != null && parameter instanceof Map) {
			int quoteIdx = jposQuoteUtil.getQuoteIdx((Map) parameter);
			IJPOSSettlementContainer settlementContainer = jposQuickQuoteService.getTmpSettlementContainer(quoteIdx);
			jposQuickQuoteService.setSettlementQuoteToContainer(quoteIdx, settlementContainer);
		}
		return ret;
	}

}
