package com.wcna.calms.jpos.services.search;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.jpos.services.quote.IJPOSQuickQuoteService;

public class SearchFinanceBreakdownService extends CalmsAjaxService {

	private IJPOSQuickQuoteService quickQuoteService;
	
	public SearchFinanceBreakdownService(IJPOSQuickQuoteService quickQuoteService) {
		this.quickQuoteService = quickQuoteService;
	}
	
	public Object invoke(Object parameter) {
		
		if (parameter != null && parameter instanceof Map) {
			Map<String, Object> m_out = new HashMap<String, Object>();
			
			Long dagId = Long.valueOf(this.getUserContainer().getCurrentDagID());
			
			String dateFilter = (String) ((Map) parameter).get("dateFilter");
			m_out.put("financeTypeFilter", quickQuoteService.getPieChartDataByFinanceType(dateFilter, dagId, this.getUserContainer().getLanguageCode()));
			m_out.put("quoteContextFilter", quickQuoteService.getPieChartDataByQuoteContext(dateFilter, dagId));
			
			return m_out;
		}
		
		return null;
	}

}
