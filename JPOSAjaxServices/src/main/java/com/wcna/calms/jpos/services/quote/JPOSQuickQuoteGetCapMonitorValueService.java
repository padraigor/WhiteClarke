package com.wcna.calms.jpos.services.quote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.util.SystemException;

public class JPOSQuickQuoteGetCapMonitorValueService extends CalmsAjaxService {

	private final IFormatService formatService;
	private final IJPOSQuickQuoteService jposQuickQuoteService;
	private final JPOSQuoteUtil jposQuoteUtil;
	
	public JPOSQuickQuoteGetCapMonitorValueService(IFormatService formatService, IJPOSQuickQuoteService jposQuickQuoteService,
			JPOSQuoteUtil jposQuoteUtil) {
		this.formatService = formatService;
		this.jposQuickQuoteService = jposQuickQuoteService;
		this.jposQuoteUtil = jposQuoteUtil;
	}
	
	public Object invoke(Object arg0) {
		double capMonitorValue = 0;
		
		if (arg0 != null && arg0 instanceof Map) {
			Map mapIn = (Map) arg0;
			
			// gather the screen data
			JPOSQuickQuoteInputVO voIn = new JPOSQuickQuoteInputVO();
			
			try {
				org.apache.commons.beanutils.BeanUtils.populate(voIn, (Map) mapIn.get("quoteInput"));
			} catch (Exception e) {
				throw new SystemException(e);
			}				
			
			IJPOSQuickQuoteInputForm quoteForm = createBean(IJPOSQuickQuoteInputForm.class);
			BeanUtils.copyProperties(voIn, quoteForm);		
			
//			String planId = (String) mapIn.get("planId");
//			String customerType = (String) mapIn.get("customerType");
			String customerType = this.jposQuickQuoteService.getCustomerData().getCustomerType();
			int idx = this.jposQuoteUtil.getQuoteIdx(mapIn);
			
			capMonitorValue = this.jposQuickQuoteService.getCapMonitorValue(idx, quoteForm, customerType);
		}
		
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("capMonitorValue", formatService.formatDouble(capMonitorValue, this.getUserContainer().getLocale()));
		
		return ret;
	}

}
