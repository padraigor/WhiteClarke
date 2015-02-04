package com.wcna.calms.jpos.services.quote;

import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.quote.IQuoteQuickInputForm;
import com.wcna.calms.service.quote.IQuoteQuickService;

public class InitializeNewQuotesService extends CalmsAjaxService {

	private final IQuoteQuickService quickQuoteService;
	
	public InitializeNewQuotesService(IQuoteQuickService quickQuoteService) {
		this.quickQuoteService = quickQuoteService;
	}
	
	public Object invoke(Object arg0) {
		if (arg0 != null && arg0 instanceof Map) {
			Map inMap = (Map) arg0;
			List<String> idxList = (List) inMap.get("idxList");
			if (idxList != null && !idxList.isEmpty()) {
				int size = idxList.size();
				for (int i = 0; i < size; i++) {
					IQuoteQuickInputForm form = createBean(IQuoteQuickInputForm.class);
					form.setQuoteIndex(idxList.get(i));
					quickQuoteService.loadQuote(form, false);
				}
			}
		}
		return null;
	}

}
