package com.wcna.calms.jpos.services.quote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.util.SystemException;

public class AdjustStructuredProfileService extends CalmsAjaxService {

	private final IJPOSQuickQuoteService quickQuoteService;
	private final JPOSQuoteUtil util;
	private final IFormatService formatService;

	public AdjustStructuredProfileService(IJPOSQuickQuoteService quickQuoteService, JPOSQuoteUtil util, IFormatService formatService) {
		this.quickQuoteService = quickQuoteService;
		this.util = util;
		this.formatService = formatService;
	}

	public Object invoke(Object parameter) {
		Map<String, List<StructuredPaymentScreenBean>> ret = null;
		if (parameter != null && parameter instanceof Map) {
			Map map = (Map) parameter;
			int quoteIdx = util.getQuoteIdx(map);
			String fromPaymentStr = (String) map.get("fromPayment");
			String ratioStr = (String) map.get("ratio");
			String paymentStr = (String) map.get("payment");
			String frequency = (String) map.get("frequency");
			String toPaymentStr = (String) map.get("toPayment");
			String sterm = (String) map.get("term");
			int term = 0;
			if (!StringUtils.isBlank(sterm)) {
				try {
					term = Integer.parseInt(sterm);
				} catch (NumberFormatException e) {
					throw new SystemException("error parsing term");
				}
			}
			Locale locale = getUserContainer().getLocale();

			int fromPayment = formatService.parseInteger(fromPaymentStr, locale, false, -1);
			double ratio = formatService.parseDouble(ratioStr, locale, false, 0d);
			double amount = formatService.parseDouble(paymentStr, locale, false, 0d);

			List<StructuredPaymentBean> currentList = quickQuoteService.genStructuredPayments(term, quickQuoteService.getInitializedContainerDate(), quickQuoteService.getFinanceProductFromContainer(quoteIdx));

			int toPayment = -1;
			if (StringUtils.isBlank(toPaymentStr)) {
				if (currentList != null) {
					toPayment = (currentList.size() - 1);
				}
			} else {
				toPayment = formatService.parseInteger(toPaymentStr, locale, false, -1);
			}

			List<StructuredPaymentBean> adjustedList = quickQuoteService.getAdjustedStructuredPayments(
					currentList,
					fromPayment,
					ratio,
					amount,
					frequency,
					toPayment,
					quickQuoteService.getFinanceProductFromContainer(quoteIdx));

			if (adjustedList != null && !adjustedList.isEmpty()) {
				ret = new HashMap<String, List<StructuredPaymentScreenBean>>();
				List<StructuredPaymentScreenBean> retList = new ArrayList<StructuredPaymentScreenBean>();
				ret.put("adjustedList", retList);
				for (StructuredPaymentBean bean : adjustedList) {
					StructuredPaymentScreenBean screenBean = new StructuredPaymentScreenBean();
					retList.add(screenBean);

					screenBean.setAmount(formatService.formatDouble(bean.getAmount(), locale));
					screenBean.setDate(formatService.formatDate(bean.getDate(), locale));
					double paymentNr = bean.getPaymentNr();
					// paymentNr is treated as array index in the front end
					// if not using advanced payments, paymentNr will start at 1, but we need it to start at 0
					if (!quickQuoteService.isUsingAdvancePayments(quickQuoteService.getFinanceProductFromContainer(quoteIdx))) {
						paymentNr--;
					}
					screenBean.setPaymentNr(paymentNr + "");
					screenBean.setRatio(formatService.formatDouble(bean.getRatio(), locale));

				}
			}


		}
		return ret;
	}
}
