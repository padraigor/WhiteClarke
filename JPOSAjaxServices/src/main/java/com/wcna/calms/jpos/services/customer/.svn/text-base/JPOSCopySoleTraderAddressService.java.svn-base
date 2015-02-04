package com.wcna.calms.jpos.services.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.service.customer.MainApplicantLoadService;
import com.wcna.calms.app.IClientConstants;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.customer.IGenericConsumerService;
import com.wcna.lang.StringUtil;

public class JPOSCopySoleTraderAddressService extends MainApplicantLoadService {

	private IGenericConsumerService consumerService;
	private CalmsAjaxService postLoadService;
	private String screenCode;

	// private String origScreenCode = null;

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

	public JPOSCopySoleTraderAddressService(
			IGenericConsumerService consumerService,
			CalmsAjaxService postLoadService) {
		super(consumerService);
		this.postLoadService = postLoadService;
	}

	public Object invoke(Object object) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		if (object instanceof Map) {
			String _screenCode = this.getScreenCode();
			map = (HashMap) super.invoke(object);

			if (postLoadService != null) {
				map.put("screenCode", _screenCode);
				map = (HashMap) postLoadService.invoke(map);
				map.remove("screenCode");
			}

			if ("A".equals(this.getAppContainer().getAppTypeCode())) {
				Map tempMap = (Map) map.get(_screenCode);
				map.put(this.screenCode, tempMap);
				map.remove(_screenCode);
			}
			// Copies the current address from the main app to the current
			// address and business address of coapplicants
			HashMap<String, Object> sectionMap = new HashMap<String, Object>();
			sectionMap.put(IGenericScreenConstants.APPLICATION_ADDRESS,
					((HashMap) map.get(screenCode))
							.get(IGenericScreenConstants.APPLICATION_ADDRESS));
			sectionMap.put(IGenericScreenConstants.APPLICATION_BILLING_ADDRESS,
					((HashMap) map.get(screenCode))
							.get(IGenericScreenConstants.APPLICATION_ADDRESS));
			resultMap.put(screenCode, sectionMap);

			//Copies the phone number from the main app to the coapplicant
			String phoneTypes = (String) super.projectProperties
					.get(IClientConstants.COPY_PHONE_DETAILS_FROM_SOLE_TRADER_MAIN_APP);

			List<Map> phoneList = (List<Map>) ((HashMap) map.get(screenCode))
					.get(IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST);

			if ((StringUtil.isBlank(phoneTypes))
					|| (phoneList == null)) {
				sectionMap.put(IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST,
						phoneList);
			} else {
				boolean flag = false;
				String[] phoneType = phoneTypes.split(",");
				List<Map> phoneArray = new ArrayList<Map>();
				for (Iterator<Map> iter = phoneList.iterator(); iter.hasNext();) {
					Map phone = iter.next();
					String phoneTypeCode = (String) phone
							.get(IGenericScreenConstants.APP_CUSTOMER_PHONE_TYPE_FIELD_NAME);
					for (int i = 0; i < phoneType.length; i++) {
						if ((!StringUtil.isBlank(phoneTypeCode))
								&& (phoneTypeCode.equals(phoneType[i]))) {
							phoneArray.add(phone);
							flag = true;
							break;
						}
					}
				}
				if(flag){
					sectionMap.put(IGenericScreenConstants.APP_CUSTOMER_PHONE_LIST,
						phoneArray);
				}
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
