package com.wcna.calms.jpos.services.settlement;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.jpos.services.quote.IJPOSQuickQuoteService;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.format.IFormatService;

public class SettlementQuoteLoadService extends CalmsAjaxService {

	private final ISettlementQuoteService settlementQuoteService;
	private final IJPOSQuickQuoteService jposQuickQuoteService;
	private final IFormatService formatService;

	public SettlementQuoteLoadService(ISettlementQuoteService settlementQuoteService, IJPOSQuickQuoteService jposQuickQuoteService,
			IFormatService formatService) {
		this.settlementQuoteService = settlementQuoteService;
		this.jposQuickQuoteService = jposQuickQuoteService;
		this.formatService = formatService;
	}

	public Object invoke(Object parameter) {

		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("isQuotable", settlementQuoteService.isQuotable(Long.parseLong(getUserContainer().getCurrentDagID())));
		if (parameter != null && parameter instanceof Map) {
			int quoteIdx = getQuoteIndex((String) ((Map) parameter).get("quoteIdx"));
			// valid quote index indicated launched from quote screen
			if (quoteIdx >= 0) {
				IJPOSSettlementContainer container = jposQuickQuoteService.getSettlementContainer(quoteIdx);
				jposQuickQuoteService.setTmpSettlementQuoteToContainer(quoteIdx, container);
				if (container != null) {
					ret.put("settlementDetails", settlementQuoteService.getSettlementForDisplay(container, getUserContainer().getLocale()));
					ret.put("isEnablePrint", IConstants.FLAG_YES);
				}
			}
		}
		return ret;

	}

	private int getQuoteIndex(String s) {
		int ret = -1;
		if (!StringUtils.isBlank(s) && StringUtils.isNumeric(s)) {
			ret = Integer.parseInt(s);
			// front end will send 1 or 2, but index in quote container starts at 0
			ret -= 1;
		}
		return ret;
	}

}
