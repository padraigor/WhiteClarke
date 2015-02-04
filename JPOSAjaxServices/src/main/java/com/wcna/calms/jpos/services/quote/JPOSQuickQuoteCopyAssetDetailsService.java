package com.wcna.calms.jpos.services.quote;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.util.SystemException;

public class JPOSQuickQuoteCopyAssetDetailsService extends CalmsAjaxService {

	private final IJPOSQuickQuoteService jposQuickQuoteService;
	private final JPOSQuoteUtil jposQuoteUtil;
	
	public JPOSQuickQuoteCopyAssetDetailsService(IJPOSQuickQuoteService jposQuickQuoteService, JPOSQuoteUtil jposQuoteUtil) {
		this.jposQuickQuoteService = jposQuickQuoteService;
		this.jposQuoteUtil = jposQuoteUtil;
	}
	
	public Object invoke(Object arg0) {
		if (arg0 != null && arg0 instanceof Map) {
			Map map = (Map) arg0;
			int quoteIdx = jposQuoteUtil.getQuoteIdx(map);
			String target1 = (String) map.get("target1");
			String target2 = (String) map.get("target2");
			
			try {
				int targetIdx;
				if (!StringUtils.isBlank(target1)) {
					targetIdx = Integer.parseInt(target1) - 1;
					jposQuickQuoteService.copyAssetDetails(quoteIdx, targetIdx);
				}
				if (!StringUtils.isBlank(target2)) {
					targetIdx = Integer.parseInt(target2) - 1;
					jposQuickQuoteService.copyAssetDetails(quoteIdx, targetIdx);
				}
			} catch (Exception e) {
				throw new SystemException(e);
			}
			
		}
		return null;
	}

}
