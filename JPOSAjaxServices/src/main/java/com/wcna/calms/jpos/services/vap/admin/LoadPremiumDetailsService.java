package com.wcna.calms.jpos.services.vap.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class LoadPremiumDetailsService extends CalmsAjaxService {

	private final IVapAdminService vapAdminService;
	
	public LoadPremiumDetailsService(IVapAdminService vapAdminService) {
		this.vapAdminService = vapAdminService;
	}
	
	public Object invoke(Object parameter) {
		if (parameter != null && parameter instanceof Map) {
			String vapValueVersionIdStr = (String) ((Map) parameter).get("vapValueVersionId");
			String vapIdStr = (String) ((Map) parameter).get("vapId");
			if (!StringUtils.isBlank(vapValueVersionIdStr) && StringUtils.isNumeric(vapValueVersionIdStr) &&
					!StringUtils.isBlank(vapIdStr) && StringUtils.isNumeric(vapIdStr)) {
				
				long vapValueVersionId = Long.valueOf(vapValueVersionIdStr);
				long vapId = Long.valueOf(vapIdStr);
				IVapValueVersionBean vvv = this.vapAdminService.getVapValueVersion(vapValueVersionId, vapId);
				if (vvv != null) {
					Map<String, Object> ret = new HashMap<String, Object>();
					ret.put("vvv", vvv);
					
					List<IVapValueBean> vvList = this.vapAdminService.getVapValues(vapValueVersionId);
					if (vvList != null && !vvList.isEmpty()) {
						List<Integer> vapKeysInt = this.vapAdminService.getOrderedVapKeys(vapId);
						int size = vapKeysInt == null ? 0 : vapKeysInt.size();
						List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
						for (IVapValueBean vv : vvList) {
							Map<String, String> row = new HashMap<String, String>();
							rows.add(row);
							
							row.put("customerCost", vv.getCustomerPremium());
							row.put("dealerCost", vv.getDealerPremium());
							row.put("baseCost", vv.getBasePremium());
							row.put("selected", "");
							if (size > 0) {
								row.put("vapKey" + vapKeysInt.get(0).toString(), vv.getCondition1());
							}
							if (size > 1) {
								row.put("vapKey" + vapKeysInt.get(1).toString(), vv.getCondition2());
							}
							if (size > 2) {
								row.put("vapKey" + vapKeysInt.get(2).toString(), vv.getCondition3());
							}
							if (size > 3) {
								row.put("vapKey" + vapKeysInt.get(3).toString(), vv.getCondition4());
							}
							if (size > 4) {
								row.put("vapKey" + vapKeysInt.get(4).toString(), vv.getCondition5());
							}							
						}
						ret.put("rows", rows);
					}
					
					return ret;
				}
			}
		}
		return null;
	}

}
