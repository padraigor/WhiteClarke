package com.wcna.calms.jpos.services.cae;

import java.util.List;
import java.util.TreeMap;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.udt.IUdtTranslationService;
import com.wcna.calms.util.OptionLabelValue;

public class LoadCaeListService extends CalmsAjaxService {

	private final IUdtTranslationService udtTranService;
	
	public LoadCaeListService(IUdtTranslationService udtTranService) {
		this.udtTranService = udtTranService;
	}

	public Object invoke(Object arg0) {
		
		TreeMap<String,OptionLabelValue> sortedCae = new TreeMap<String,OptionLabelValue>();
		
		List<OptionLabelValue> sicCodeList =  udtTranService.getTranslatedCodeValues("INDUSTRY_CODE", getUserContainer().getLanguageCode(), getUserContainer().getCountryCode());
		
		//label displayed has both code and value
		for (OptionLabelValue item : sicCodeList) {
			OptionLabelValue newItem = new OptionLabelValue();
			newItem.setValue(item.getValue());
			newItem.setLabel(item.getValue() + "  " + item.getLabel());
			sortedCae.put(item.getValue(), newItem);
		}
		
		return sortedCae.values();
	}

}
