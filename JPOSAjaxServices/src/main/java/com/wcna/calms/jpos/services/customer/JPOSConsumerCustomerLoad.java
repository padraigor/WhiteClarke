package com.wcna.calms.jpos.services.customer;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.service.customer.ConsumerCustomerLoad;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.customer.IGenericConsumerService;

public class JPOSConsumerCustomerLoad extends ConsumerCustomerLoad {

	private IGenericConsumerService consumerService;
	private CalmsAjaxService postLoadService;
	private String screenCode;
//	private long refID;
//	private String origScreenCode = null;

	public String getScreenCode() {
		String ret;
		if ("A".equals(this.getAppContainer().getAppTypeCode())
				&& screenCode != null
				&& !"Bpm".equals(screenCode.substring(0, 3))) {
			ret = "Bpm" + screenCode;
		} else {
			ret = screenCode;
		}
		return ret;
	}

	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}

	public JPOSConsumerCustomerLoad(IGenericConsumerService consumerService, CalmsAjaxService postLoadService) {
		super(consumerService);
		this.postLoadService = postLoadService;
	}

	public Object invoke(Object object) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		//check appType (Bpm or Prop) and modify screenCode
		if (object instanceof Map) {
			/*
			Map inputMap = (Map)object;
			String appType = inputMap.get("appType")==null? "": (String)inputMap.get("appType");
			if (origScreenCode == null)
				origScreenCode = getScreenCode();

			if (appType.equals("A")) {
				if (!screenCode.substring(0,3).equals("Bpm")) {
					this.setScreenCode("Bpm"+getScreenCode());
				}
			} else if (screenCode.substring(0,3).equals("Bpm")) {
				this.setScreenCode(screenCode.substring(3));
			}
			*/
			String _screenCode = this.getScreenCode();
			resultMap = (HashMap)super.invoke(object);

			if (postLoadService != null) {
				resultMap.put("screenCode", _screenCode);
				resultMap = (HashMap)postLoadService.invoke(resultMap);
				resultMap.remove("screenCode");
			}
			if ("A".equals(this.getAppContainer().getAppTypeCode())) {
				Map tempMap = (Map)resultMap.get(_screenCode);
				resultMap.put(this.screenCode, tempMap);
				resultMap.remove(_screenCode);
			}
		}
		return resultMap;
	}
	
	@Override
	public Map getData() {
		Map ret = super.getData();
		if (this.postLoadService != null) {
			ret.put("screenCode", this.getScreenCode());
			ret.put("isIgnoreDefaults", IConstants.FLAG_YES);
			this.postLoadService.invoke(ret);
			ret.remove("screenCode");
			ret.remove("isIgnoreDefaults");
		}
		return ret;
	}	
}
