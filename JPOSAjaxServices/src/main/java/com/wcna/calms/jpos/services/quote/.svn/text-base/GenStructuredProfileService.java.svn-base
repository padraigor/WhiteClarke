package com.wcna.calms.jpos.services.quote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.common.util.Logger;
import com.wcna.calms.service.format.IFormatService;

public class GenStructuredProfileService extends CalmsAjaxService {

	private final IJPOSQuickQuoteService quickQuoteService;
	private final JPOSQuoteUtil util;
	private final IFormatService formatService;

	public GenStructuredProfileService(IJPOSQuickQuoteService quickQuoteService, JPOSQuoteUtil util, IFormatService formatService) {
		this.quickQuoteService = quickQuoteService;
		this.util = util;
		this.formatService = formatService;
	}

	public Object invoke(Object parameter) {
		Map<String, Object> ret = null;
		if (parameter != null && parameter instanceof Map) {
			String sterm = (String) ((Map) parameter).get("term");
			int quoteIdx = util.getQuoteIdx((Map) parameter);
			List<StructuredPaymentBean> beanList = null;
			if (!StringUtils.isBlank(sterm)) {
				try {
					int term = Integer.parseInt(sterm);
					beanList = quickQuoteService.genStructuredPayments(term, quickQuoteService.getInitializedContainerDate(), quickQuoteService.getFinanceProductFromContainer(quoteIdx));

					if (beanList != null && !beanList.isEmpty()) {
						ret = new HashMap<String, Object>();
						List<StructuredPaymentScreenBean> screenBeans = new ArrayList<StructuredPaymentScreenBean>();
						ret.put("structuredPayments", screenBeans);
						StructuredPaymentScreenBean screenBean;
						Locale locale = getUserContainer().getLocale();

						for (StructuredPaymentBean bean : beanList) {
							screenBean = new StructuredPaymentScreenBean();
							screenBeans.add(screenBean);

							screenBean.setAmount(formatService.formatDouble(bean.getAmount(), locale));
							screenBean.setDate(formatService.formatDate(bean.getDate(), locale));
							screenBean.setPaymentNr(bean.getPaymentNr() + "");
							screenBean.setRatio(formatService.formatDouble(bean.getRatio(), locale));
						}
					}
				} catch (NumberFormatException nfe) {
					Logger.debug(nfe);
					ret = null;
					beanList = null;
				}
			}
		}
		return ret;
	}

}
