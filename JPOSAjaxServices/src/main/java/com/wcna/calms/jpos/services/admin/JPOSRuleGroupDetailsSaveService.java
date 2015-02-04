package com.wcna.calms.jpos.services.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.product.services.session.GenericMessage;
import com.wcg.product.services.session.IMessageStore;
import com.wcg.product.services.session.SessionManager;
import com.wcna.calms.service.error.IErrorMessage;
import com.wcna.calms.util.BeanConverterUtil;

public class JPOSRuleGroupDetailsSaveService extends CalmsAjaxService {

	private IJPOSAdminRuleService service = null;
	private final BeanConverterUtil beanUtil;
	public JPOSRuleGroupDetailsSaveService(IJPOSAdminRuleService service) {
		this.service = service;
		this.beanUtil = new BeanConverterUtil();
	}

	public Object invoke(Object object) {


		if (object != null && object instanceof Map) {
			Map<String, Object> dataMap = (Map<String, Object>)object;
			Map<String, Object> rulesDetails = (Map<String, Object>) dataMap.get("rulesDetails");
			
			String ruleGroup = (String) rulesDetails.get("ruleGroupId");
			int ruleGroupId = 0;
			if (ruleGroup!= null && !"".equals(ruleGroup)){
				ruleGroupId = Integer.parseInt(ruleGroup);
			}

			String description = (String)rulesDetails.get("description");

			List rlArray = (List)rulesDetails.get("ruleList");

			List<IJPOSBusinessRule> ruleList = new ArrayList<IJPOSBusinessRule>();

			IMessageStore store = SessionManager.getInstance().getSessionData().getMessageStore();
			IErrorMessage errorStore = (IErrorMessage) store;
			boolean isNoRules = true;
			boolean isRuleValid = true; // A rule is valid if its Attribute, Operator,and Value values are not empty
			if (rlArray!=null){
				Object[] rules = rlArray.toArray();
				if (rules != null && rules.length > 0) {
					isNoRules = false;
					for(int i=0; i<rules.length && isRuleValid; i++) {
						Map jsonObject = (Map) rules[i];
						if( "".equals(jsonObject.get("value")) || "".equals(jsonObject.get("attributeId")) || "".equals(jsonObject.get("operatorId")) ){
							if("".equals(jsonObject.get("value")))
								errorStore.addMessage(new GenericMessage(GenericMessage.ERROR, "M00001"), "value", "rulesDetails");
							if("".equals(jsonObject.get("attributeId")))
								errorStore.addMessage(new GenericMessage(GenericMessage.ERROR, "M00001"), "attribute", "rulesDetails");
							if("".equals(jsonObject.get("operatorId")))
								errorStore.addMessage(new GenericMessage(GenericMessage.ERROR, "M00001"), "operator", "rulesDetails");

							isRuleValid = false;
						}else{
						IJPOSBusinessRule businessRule = (IJPOSBusinessRule) beanUtil.convertMapToBean(jsonObject, createBean(IJPOSBusinessRule.class).getClass());
						ruleList.add(businessRule);
					}
				}
			}
			}

			if (isNoRules) {
				errorStore.addMessage(new GenericMessage(GenericMessage.ERROR, "M00001"), "conditionsTitle", "rulesDetails");
			}
			else if (isRuleValid){
			
				int newId = service.saveRuleGroup(ruleGroupId, description, ruleList);
	
	
				HashMap<String,Object> retMap = new HashMap<String,Object>();
				retMap.put("ruleGroupId", newId+"");
	
				return retMap;
			}

		}
		return null;

	}

}
