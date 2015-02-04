package com.wcna.calms.jpos.services.quote;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.product.services.session.GenericMessage;
import com.wcg.product.services.session.SessionManager;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.util.SystemException;

public class JPOSQuickQuoteCostInputChangeService extends CalmsAjaxService {

	private final IJPOSQuickQuoteService jposQuickQuoteService;
	private final IFormatService formatService;
	private final JPOSQuoteUtil jposQuoteUtil;

	public JPOSQuickQuoteCostInputChangeService(IJPOSQuickQuoteService jposQuickQuoteService, IFormatService formatService,
			JPOSQuoteUtil jposQuoteUtil) {
		this.jposQuickQuoteService = jposQuickQuoteService;
		this.formatService = formatService;
		this.jposQuoteUtil = jposQuoteUtil;
	}

	public Object invoke(Object parameter) {
		Map<String, String> ret = new HashMap<String, String>();
		String cost = null;
		boolean isValid = true;
		int quoteIdx = -1;
		if (parameter != null && parameter instanceof Map) {
			cost = (String) ((Map) parameter).get("cost");
			quoteIdx = jposQuoteUtil.getQuoteIdx(((Map) parameter));
		}

		if (quoteIdx < 0) {
			throw new SystemException("Invalid quote index for cost input change");
		}
		if (StringUtils.isBlank(cost) || formatService.parseDouble(cost, getUserContainer().getLocale(), true, null) == null) {
			isValid = false;
		} else {
			double _cost = formatService.parseDouble(cost, getUserContainer().getLocale(), true, null);
			isValid = jposQuickQuoteService.adjustCostInputChange(_cost, quoteIdx, getUserContainer().getLocale());
		}

		if (!isValid) {
			SessionManager.getInstance().getSessionData().getMessageStore().addMessage(new GenericMessage(GenericMessage.ERROR, "JPQQ0003"));
		}

		return ret;
	}

}
