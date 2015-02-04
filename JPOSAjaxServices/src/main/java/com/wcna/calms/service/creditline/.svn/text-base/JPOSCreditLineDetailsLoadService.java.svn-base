package com.wcna.calms.service.creditline;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.common.constants.IServiceConstants;
import com.wcna.calms.data._AppCreditLimitData;
import com.wcna.calms.jpos.services.creditline.IJPOSCreditLineDetailsService;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.customer.IGenericScreenForm;
import com.wcna.framework.sensitive.ISensitiveLoadService;
import com.wcna.lang.StringUtil;

public class JPOSCreditLineDetailsLoadService  extends CalmsAjaxService implements ISensitiveLoadService {
	
	IJPOSCreditLineDetailsService creditLineDetailsService;
	
	private String screenCode;
	
	public String getScreenCode() {
		return screenCode;
	}

	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}
	
	JPOSCreditLineDetailsLoadService(IJPOSCreditLineDetailsService creditLineDetailsService){
		this.creditLineDetailsService = creditLineDetailsService;
	}
	
	public Object invoke(Object object) {
		
		Map<String,Object> retMap = new HashMap<String,Object>();
		
		Map<String, Object> dataMap = null;
		if (object != null && object instanceof Map) {
			dataMap = (Map<String, Object>)object;
			String creditLineId = (String)dataMap.get("selectedCreditLineId");
			long appCreditLineId = 0;
			if (!StringUtil.isBlank(creditLineId)){
				appCreditLineId = Long.parseLong(creditLineId);
			}
			long appId = getAppContainer().getAppID();
			if (appId == 0){
				String propNum = (String)dataMap.get("proposalNumber");
				if (!StringUtil.isBlank(propNum))
					appId = Long.parseLong(propNum);
			}
			
			retMap =  creditLineDetailsService.load(appId,appCreditLineId);
		}
		
		return retMap;
	}
	
	public Map getData() {
		Map<String, Object> returnMap = new HashMap<String, Object>();		
		
		
		Map <String, Object> creditLineDetailsMap = creditLineDetailsService.load(this.getAppContainer().getAppID(),this.getAppContainer().getRef2ID());
		
		_AppCreditLimitData appCreditLimitData = (_AppCreditLimitData)creditLineDetailsMap.get("selectedCreditLineData");
		
		IGenericScreenForm form = createBean(IGenericScreenForm.class);
		form.setDynaProperty("creditLineDetails", appCreditLimitData);
		
		returnMap.put(getScreenCode(), form.getHashMap());
		returnMap.put(IServiceConstants.KEY_ID, this.getAppContainer().getRef2ID());
		
		
		return returnMap;
		
	}
	
	

}
