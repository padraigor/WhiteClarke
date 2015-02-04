package com.wcna.calms.jpos.services.settlement;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class SettlementQuoteCloseService extends CalmsAjaxService {

	private final ISettlementQuoteService settlementQuoteService;

	public SettlementQuoteCloseService(ISettlementQuoteService settlementQuoteService) {
		this.settlementQuoteService = settlementQuoteService;
	}

	public Object invoke(Object parameter) {
		settlementQuoteService.setSettlementQuoteToStorage(null);
		return null;
	}

}
