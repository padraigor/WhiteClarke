package com.wcna.calms.jpos.services.quote;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.format.IFormatService;

public class JPOSQuickQuoteSaveService extends CalmsAjaxService {

	private final IJPOSQuickQuoteService jposQuickQuoteService;
	private final IJPOSQuickQuoteCustomerValidatorService jposQuickQuoteCustomerValidatorService;
	private final IFormatService formatService;
	private final JPOSQuoteUtil jposQuoteUtil;

	public JPOSQuickQuoteSaveService(IJPOSQuickQuoteService jposQuickQuoteService, IJPOSQuickQuoteCustomerValidatorService jposQuickQuoteCustomerValidatorService, IFormatService formatService, JPOSQuoteUtil jposQuoteUtil) {
		this.jposQuickQuoteService = jposQuickQuoteService;
		this.jposQuickQuoteCustomerValidatorService = jposQuickQuoteCustomerValidatorService;
		this.formatService = formatService;
		this.jposQuoteUtil = jposQuoteUtil;
	}

	public Object invoke(Object parameter) {
		if (parameter != null && parameter instanceof Map) {
			Map input = (Map) parameter;
			int quoteIdx = jposQuoteUtil.getQuoteIdx(input);
			Locale locale = this.getUserContainer().getLocale();
			if (jposQuickQuoteCustomerValidatorService.validate(jposQuickQuoteService.getCustomerData(), jposQuickQuoteService.getApplicationType())) {
				int dagId = formatService.parseInteger(this.getUserContainer().getCurrentDagID(), this.getUserContainer().getLocale(), false, 0);
				int dealerId = this.jposQuickQuoteService.getDealerIdFromDealerDagId(dagId);
				int jtrId = this.jposQuickQuoteService.getJtrIdFromDealerDagId(dagId);
				int brokerBrandId = formatService.parseInteger(this.getUserContainer().getCurrentBrandCode(), this.getUserContainer().getLocale(), false, 0);
				String appId = getAppContainer() == null ? "0" : getAppContainer().getAppID() + "";

				String ret = jposQuickQuoteService.save(appId, locale, dealerId, jtrId, brokerBrandId, IConstants.FLAG_YES.equals((String) input.get("isConfirmSensitive")), quoteIdx);

				Map<String, Object> m_out = new HashMap<String, Object>();
				m_out.put("appId", getAppContainer().getAppID() + "");
				m_out.put("appType", jposQuickQuoteService.getApplicationType());

				if (IJPOSQuickQuoteConstants.CONFIRM_REQUIRED.equals(ret)) {
					m_out.put("isConfirmRequired", IConstants.FLAG_YES);
				}

				return m_out;
			}
		}
		return null;
	}
}
