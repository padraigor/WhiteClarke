package com.wcna.calms.jpos.services.profile;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.product.calms.security.CalmsSessionData;
import com.wcg.product.services.session.SessionManager;
import com.wcna.calms.service.common.IUserContainer;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.calms.service.security.IUserService;
import com.wcna.calms.service.udt.IUdtTranslationService;
import com.wcna.util.SystemException;

public class ProfileSaveService extends CalmsAjaxService {

	private final IFormatService formatService;
	private final IUserService userService;
	private final IUdtTranslationService udtTranslationService;
	
	public ProfileSaveService(IFormatService formatService, IUserService userService, IUdtTranslationService udtTranslationService) {
		this.formatService = formatService;
		this.userService = userService;
		this.udtTranslationService = udtTranslationService;
	}
	
	public Object invoke(Object parameter) {
		if (parameter != null && parameter instanceof Map) {
			Map map = (Map) parameter;
			String languageCode = (String) map.get("languageCode");
			String brandCode = (String) map.get("brandCode");
			long userId = Long.valueOf(this.getUserContainer().getUserID());
			long dagId = Long.valueOf(this.getUserContainer().getCurrentDagID());
			String countryCode = this.getUserContainer().getCountryCode();			
			
			if (!this.userService.isBrandAvailableForUser(userId, countryCode, dagId, languageCode, brandCode)) {
				throw new SystemException("Brand is not available for this user");
			}
			
			IUserContainer userContainer = ((CalmsSessionData) SessionManager.getInstance().getSessionData()).getUserContainer(); 
			Locale current = userContainer.getLocale();
			userContainer.setLocale(new Locale(languageCode.toLowerCase(), current.getCountry()));
			userContainer.setCurrentBrandCode(brandCode);
			
			udtTranslationService.initUDTs(userContainer.getCountryCode(), userContainer.getLanguageCode());
			
			Map<String, Object> out = new HashMap<String, Object>();
			out.put("brandCode", this.getUserContainer().getCurrentBrandCode());
			out.put("datePattern", formatService.getDateFormatPattern(getUserContainer().getLocale()));			
			return out;
		}
		return null;
	}

}
