package com.wcg.calms.service.customer.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.jpos.services.customer.JPOSConsumerCustomerLoad;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.customer.ICustomerService;
import com.wcna.calms.service.customer.IGenericConsumerService;
import com.wcna.calms.web.app.CoApplicantDetailsDisplay;

public class JPOSIndividualEffectiveOwnerCopyDirector extends JPOSConsumerCustomerLoad {

	private final ICustomerService customerService;

	public JPOSIndividualEffectiveOwnerCopyDirector(ICustomerService customerService, IGenericConsumerService consumerService, CalmsAjaxService postLoadService){
		super(consumerService, postLoadService);
		this.customerService = customerService;
	}
	public Object invoke(Object object) {
		HashMap<String, Object> inputMap = (HashMap<String, Object>) object;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		if (object instanceof Map) {
			List <CoApplicantDetailsDisplay> directors = customerService.getDirectorTypeReferences();
			if(directors != null && !directors.isEmpty()){
				CoApplicantDetailsDisplay obj = directors.get(0);
				inputMap.put("keyID", obj.getRefid());
			}
			resultMap = (HashMap<String, Object>) super.invoke(inputMap);
			resultMap.put("keyID", "0");
			Object effectiveOwnerObj = resultMap.get(IGenericScreenConstants.EFFECTIVE_OWNER);
			if (effectiveOwnerObj != null) {
				Object customerObj = ((HashMap)effectiveOwnerObj).get(IGenericScreenConstants.APP_CONSUMER_CUSTOMER);
				if(customerObj != null){
					((HashMap)customerObj).put(IGenericScreenConstants.APP_CUSTOMER_ROLE_CODE,null);
					((HashMap)customerObj).put(IGenericScreenConstants.APP_CUSTOMER_REF2_TYPE_CODE, "");
					((HashMap)customerObj).put(IGenericScreenConstants.APP_CUSTOMER_ROLE_CODE,null);
				}
			}

		}
		return resultMap;
	}

}
