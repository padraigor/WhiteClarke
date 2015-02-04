package com.wcna.calms.jpos.services.vap.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.common.IConstants;

public class SavePremiumService extends CalmsAjaxService {

	private final IVapAdminService vapAdminService;
	
	public SavePremiumService(IVapAdminService vapAdminService) {
		this.vapAdminService = vapAdminService;
	}
	
	public Object invoke(Object parameter) {
		if (parameter != null && parameter instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) parameter;
			String vapIdStr = (String) map.get("vapId");
			if (!StringUtils.isBlank(vapIdStr) && StringUtils.isNumeric(vapIdStr)) {
				long vapId = Long.valueOf(vapIdStr);
				Map<String, Object> premiumData = (Map<String, Object>) map.get("premiumData");
				IVapValueVersionBean vvvBean = this.getVapValueVersionBean(premiumData, vapId);
				List<String> vapKeys = (List<String>) map.get("vapKeys");
				if (vapKeys == null) {
					vapKeys = new ArrayList<String>();
				}
				List<Integer> vapKeysInt = new ArrayList<Integer>();
				for (String s : vapKeys) {
					vapKeysInt.add(Integer.valueOf(s));
				}
				// natural sorting
//				Collections.sort(vapKeysInt);
				List<Map<String, String>> vapValuesList = (List<Map<String, String>>) premiumData.get("premiumDetailsList");
				List<IVapValueBean> vapValueBeanList = new ArrayList<IVapValueBean>();
				if (vapValuesList != null && !vapValuesList.isEmpty()) {
					for (Map<String, String> vv : vapValuesList) {
						vapValueBeanList.add( this.getVapValueBean(vv, vapId, vapKeysInt) );
					}
				}
				List<String> sortedVapKeys = new ArrayList<String>();
				for (Integer i : vapKeysInt) {
					sortedVapKeys.add(i.toString());
				}
				long vapValueVersionId = vapAdminService.saveVapValueVersion(vvvBean, vapValueBeanList, sortedVapKeys);
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("success", vapValueVersionId == 0 ? IConstants.FLAG_NO : IConstants.FLAG_YES);
				ret.put("vapValueVersionId", vapValueVersionId);
				return ret;
			}
		}
		return null;
	}

	private IVapValueVersionBean getVapValueVersionBean(Map<String, Object> dataMap, long vapId) {
		IVapValueVersionBean ret = this.createBean(IVapValueVersionBean.class);
		ret.setValidFrom((String) dataMap.get("validFrom"));
		ret.setValidTo((String) dataMap.get("validTo"));
		ret.setVapId(vapId);
		long vapValueVersionId = 0;
		String str = (String) dataMap.get("premiumVersionId");
		if (!StringUtils.isBlank(str) && StringUtils.isNumeric(str)) {
			vapValueVersionId = Long.valueOf(str);
		}
		ret.setVapValueVersionId(vapValueVersionId);
		
		return ret;
	}
	
	private IVapValueBean getVapValueBean(Map<String, String> dataMap, long vapId, List<Integer> vapKeysList) {
		IVapValueBean ret = this.createBean(IVapValueBean.class);
		ret.setBasePremium(dataMap.get("baseCost"));
		ret.setCustomerPremium(dataMap.get("customerCost"));
		ret.setDealerPremium(dataMap.get("dealerCost"));
		
		int idx = 1;
		for (Integer i : vapKeysList) {
			String value = dataMap.get("vapKey" + i);
			switch (idx) {
			case 1:
				ret.setCondition1(value);
				break;
			case 2:
				ret.setCondition2(value);
				break;
			case 3:
				ret.setCondition3(value);
				break;
			case 4:
				ret.setCondition4(value);
				break;
			case 5:
				ret.setCondition5(value);
				break;
			}
			idx++;
		}
		
		return ret;
	}
	
}
