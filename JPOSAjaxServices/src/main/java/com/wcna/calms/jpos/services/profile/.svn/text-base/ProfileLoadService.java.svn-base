package com.wcna.calms.jpos.services.profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.data.IBrandDataVO;
import com.wcna.calms.service.security.IUserService;
import com.wcna.calms.util.OptionLabelValue;

public class ProfileLoadService extends CalmsAjaxService {

	private final IUserService userService;
	
	public ProfileLoadService(IUserService userService) {
		this.userService = userService;
	}
	
	public Object invoke(Object parameter) {		
		Map<String, Object> out = new HashMap<String, Object>();
		out.put("languages", userService.getLanguageOptions());		
		out.put("currentLanguage", this.getUserContainer().getLanguageCode());		
		long userId = Long.valueOf(this.getUserContainer().getUserID());
		long dagId = Long.valueOf(this.getUserContainer().getCurrentDagID());
		String countryCode = this.getUserContainer().getCountryCode();
//		out.put("brands", userService.getAvailableUserBrands(userId, countryCode, dagId));
		List<IBrandDataVO> brands = userService.getAvailableUserBrands(userId, countryCode, dagId,  this.getUserContainer().getLanguageCode());
		if (brands != null && !brands.isEmpty()) {
			List<OptionLabelValue> olvs = new ArrayList<OptionLabelValue>();
			for (IBrandDataVO brand : brands) {
				String desc = brand.getDescTrans();
				if (StringUtils.isBlank(desc)) {
					desc = brand.getDescription();
				}
				OptionLabelValue olv = new OptionLabelValue(desc, brand.getCode());
				olvs.add(olv);
			}
			out.put("brands", olvs);
			out.put("currentBrand", this.getUserContainer().getCurrentBrandCode());
		}
		return out;
	}
}
