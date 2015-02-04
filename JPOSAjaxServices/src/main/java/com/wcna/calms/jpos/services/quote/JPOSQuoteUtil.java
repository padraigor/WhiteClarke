package com.wcna.calms.jpos.services.quote;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcna.util.SystemException;

public class JPOSQuoteUtil {

	public int getQuoteIdx(Map map) {
		String v = (String) map.get("quoteIdx");
		int ret = -1;
		if (!StringUtils.isBlank(v)) {
			ret = Integer.parseInt(v);
		}
		if (ret <= 0) {
			throw new SystemException("Invalid quote index to retrieve");
		}
		// quote from screen is assumed to be 1,2,3,etc
		// but in the quote container, the index starts at 0, so we will adjust
		return ret - 1;
	}
}
