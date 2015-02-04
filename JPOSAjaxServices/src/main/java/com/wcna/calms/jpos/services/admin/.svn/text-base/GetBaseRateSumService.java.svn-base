package com.wcna.calms.jpos.services.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.util.BeanConverterUtil;

public class GetBaseRateSumService extends CalmsAjaxService {

	private final BeanConverterUtil beanUtil;
	private final IJPOSAdminPackageService adminPackageService;
	
	public GetBaseRateSumService(IJPOSAdminPackageService adminPackageService) {
		this.beanUtil = new BeanConverterUtil();
		this.adminPackageService = adminPackageService;
	}
	
	public Object invoke(Object parameter) {
		if (parameter != null && parameter instanceof Map) {
			Map map = (Map) parameter;
			String baseRateId = (String) map.get("baseRateId");
			long _baseRateId = 0;
			List<Map<String, String>> rangeRates = (List<Map<String, String>>) map.get("rangeRates");
			List<IJPOSRangeRateVO> _rangeRates = null;
			
			if (!StringUtils.isBlank(baseRateId) 
					&& StringUtils.isNumeric(baseRateId)) {
				_baseRateId = Long.valueOf(baseRateId);
			}
			
			if (rangeRates != null && !rangeRates.isEmpty()) {
				_rangeRates = this.beanUtil.convertMapsToBeanList(rangeRates, createBean(IJPOSRangeRateVO.class));
			}
			
			Map<String, List<String>> ret = new HashMap<String, List<String>>();
			ret.put("readonlyValues", this.adminPackageService.getBaseRateSums(_baseRateId, _rangeRates));
			return ret;
			
		}
		return null;
	}

}
