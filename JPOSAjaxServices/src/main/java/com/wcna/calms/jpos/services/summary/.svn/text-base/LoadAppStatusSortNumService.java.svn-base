package com.wcna.calms.jpos.services.summary;

import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.udt.IUdtTranslationService;

public class LoadAppStatusSortNumService extends CalmsAjaxService {

	private final IUdtTranslationService udtTranslationService;
	
	public LoadAppStatusSortNumService(IUdtTranslationService udtTranslationService) {
		this.udtTranslationService = udtTranslationService;
	}
	
	public Object invoke(Object arg0) {
		Map<String, Integer> sortCodeMap = this.udtTranslationService.getUDTCodeSortNumByTableName(IConstants.APPLICATION_STATUS_UDT_TABLE, this.getUserContainer().getCountryCode(), this.getUserContainer().getLanguageCode());
		return sortCodeMap;
	}

}
