package com.wcna.calms.jpos.services.quote;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.common.IConstants;

public class JPOSQuickQuoteDocumentLoadService extends CalmsAjaxService {

	private final IJPOSQuickQuoteService jposQuickQuoteService;

	public JPOSQuickQuoteDocumentLoadService(IJPOSQuickQuoteService jposQuickQuoteService) {
		this.jposQuickQuoteService = jposQuickQuoteService;
	}

	public Object invoke(Object parameter) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("isEmailQuoteDoc", jposQuickQuoteService.isEmailQuoteDoc() ? IConstants.FLAG_YES : "");
		return ret;
	}

}
