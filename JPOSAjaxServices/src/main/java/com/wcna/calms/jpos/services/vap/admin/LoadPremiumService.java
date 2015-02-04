package com.wcna.calms.jpos.services.vap.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class LoadPremiumService extends CalmsAjaxService {

	private final IVapAdminService vapAdminService;
	
	public LoadPremiumService(IVapAdminService vapAdminService) {
		this.vapAdminService = vapAdminService;
	}
	
	public Object invoke(Object parameter) {
		if (parameter != null && parameter instanceof Map) {
			String vapIdStr = (String) ((Map) parameter).get("vapId");
			if (!StringUtils.isBlank(vapIdStr) && StringUtils.isNumeric(vapIdStr)) {
				long vapId = Long.valueOf(vapIdStr);
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("vvvList", vapAdminService.getVapValueVersionsOLV(vapId));
				List<Integer> vapKeysInt = vapAdminService.getOrderedVapKeys(vapId);
				if (vapKeysInt != null && !vapKeysInt.isEmpty()) {
					List<String> vapKeysStr = new ArrayList<String>();
					for (Integer i : vapKeysInt) {
						vapKeysStr.add(i.toString());
					}
					ret.put("vapKeys", vapKeysStr);	
				}
				return ret;
			}
		}
		return null;
	}

}
