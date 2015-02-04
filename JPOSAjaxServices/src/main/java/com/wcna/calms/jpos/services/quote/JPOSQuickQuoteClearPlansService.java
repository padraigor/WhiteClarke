package com.wcna.calms.jpos.services.quote;

import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class JPOSQuickQuoteClearPlansService extends CalmsAjaxService {

	private final IJPOSQuickQuoteService quickQuoteService;
	private final JPOSQuoteUtil jposQuoteUtil;
	
	public JPOSQuickQuoteClearPlansService(IJPOSQuickQuoteService quickQuoteService, 
			JPOSQuoteUtil jposQuoteUtil) {
		this.quickQuoteService = quickQuoteService;
		this.jposQuoteUtil = jposQuoteUtil;
		
	}
	
	public Object invoke(Object arg0) {
		if (arg0 != null && arg0 instanceof Map) {
			int quoteIdx = jposQuoteUtil.getQuoteIdx((Map) arg0);
			quickQuoteService.setFinanceProductToContainer(quoteIdx, null);
		}
			
		return null;
	}

}
