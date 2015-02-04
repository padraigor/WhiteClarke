package com.wcna.calms.jpos.services.quote;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.common.util.Logger;
import com.wcna.calms.service.format.IFormatService;

public class GetMoreStructuredPaymentsService extends CalmsAjaxService {

	private final IFormatService formatService;

	public GetMoreStructuredPaymentsService(IFormatService formatService) {
		this.formatService = formatService;
	}

	public Object invoke(Object parameter) {
		Map<String, List<StructuredPaymentScreenBean>> ret = null;
		if (parameter != null && parameter instanceof Map) {
			String amtStr = (String) ((Map) parameter).get("amount");
			String lastDateStr = (String) ((Map) parameter).get("lastDate");
			String lastPaymentNoStr = (String) ((Map) parameter).get("lastPaymentNo");
			if (!StringUtils.isBlank(amtStr)) {
				try {
					int amt = Integer.parseInt(amtStr);
					int lastPaymentNo = Integer.parseInt(lastPaymentNoStr);
					Locale locale = getUserContainer().getLocale();
					Date lastDate = null;
					try {
						lastDate = formatService.parseDate(lastDateStr, locale, true, null);
					} catch (ParseException e) {
						Logger.debug(e);
						lastDate = null;
					}

					if (amt > 0 && lastPaymentNo > 0 && lastDate != null) {
						ret = new HashMap<String, List<StructuredPaymentScreenBean>>();
						List<StructuredPaymentScreenBean> list = new ArrayList<StructuredPaymentScreenBean>();
						ret.put("list", list);
						StructuredPaymentScreenBean bean;
						Calendar cal = Calendar.getInstance();
						cal.setTime(lastDate);

						for (int i = 0; i < amt; i++) {
							bean = new StructuredPaymentScreenBean();
							list.add(bean);
							cal.add(Calendar.MONTH, 1);

							bean.setAmount(formatService.formatDouble(0d, locale));
							bean.setPaymentNr((lastPaymentNo + (i + 1)) + "");
							bean.setDate(formatService.formatDate(cal.getTime(), locale));
							bean.setRatio(formatService.formatDouble(1d, locale));
						}
					}
				} catch (NumberFormatException nfe) {
					Logger.debug(nfe);
					ret = null;
				}
			}
		}
		return ret;
	}

}
