package com.wcna.calms.jpos.services.quote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.util.SystemException;

public class JPOSQuickQuoteAssetSaveFFOService extends CalmsAjaxService {

	private final IJPOSQuickQuoteAssetService assetService;
	private final IFormatService formatService;
	
	public JPOSQuickQuoteAssetSaveFFOService(IJPOSQuickQuoteAssetService assetService, IFormatService formatService) {
		this.assetService = assetService;
		this.formatService = formatService;		
	}
	
	public Object invoke(Object arg0) {
		if (arg0 != null && arg0 instanceof Map) {
			List<Map<String, Object>> ffoList = (List<Map<String, Object>>) ((Map) arg0).get("ffoList");
			String userInput = (String) ((Map) arg0).get("userInput");
			Locale locale = this.getUserContainer().getLocale();
			Map<String, String> ret = new HashMap<String, String>();
//			if (formatService.parseDouble(userInput, locale, false, 0d) <= 0) {
				ret.put("overrideUserInput", "Y");
				if (ffoList != null && !ffoList.isEmpty()) {
					List<IVehicleFFOBean> inputList = new ArrayList<IVehicleFFOBean>();
					for (Map<String, Object> m : ffoList) {
						IVehicleFFOBean bean =  createBean(IVehicleFFOBean.class);
						try {
							org.apache.commons.beanutils.BeanUtils.populate(bean, m);
						} catch (Exception e) {
							throw new SystemException(e);
						}		
						inputList.add(bean);
					}
					ret.put("total", this.assetService.getFFOTotalAsString(inputList));
					//AssetContainer ac= this.assetService.getFFOTotalAsString(inputList)
					return ret;
				} else {
					ret.put("total", this.formatService.formatDouble(0d, this.getUserContainer().getLocale()));
					return ret;
				}
//			}
		}
		return null;
	}

}
