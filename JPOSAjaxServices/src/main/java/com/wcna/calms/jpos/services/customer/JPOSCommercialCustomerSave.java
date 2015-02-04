package com.wcna.calms.jpos.services.customer;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.common.constants.IServiceConstants;
import com.wcg.calms.service.app.SummaryService;
import com.wcg.calms.service.customer.CommercialCustomerSave;
import com.wcg.product.services.session.SessionManager;
import com.wcna.calms.service.application.IApplicationService;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.customer.IGenericCommercialService;
import com.wcna.framework.sensitive.ISensitiveLoadService;
import com.wcna.framework.validation.IBusinessRuleValidationService;
import com.wcna.lang.StringUtil;

public class JPOSCommercialCustomerSave extends CommercialCustomerSave implements IBusinessRuleValidationService {

	private IGenericCommercialService commercialService;
	private ISensitiveLoadService loadService;	
	private String screenCode;
	private String customerRoleCode;	
	private IApplicationService applicationService;
	private SummaryService summaryService;
	private final CalmsAjaxService preSaveService;
	private final CalmsAjaxService postSaveService;
	
	public JPOSCommercialCustomerSave(
			IGenericCommercialService commercialService,
			ISensitiveLoadService loadService,
			IApplicationService applicationService,
			SummaryService summaryService,
			CalmsAjaxService preSaveService,
			CalmsAjaxService postSaveService) {
		super(commercialService, loadService, applicationService, summaryService);
		this.preSaveService = preSaveService;
		this.postSaveService = postSaveService;
	}

	public Object invoke(Object object) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap = (HashMap)super.invoke(object);
		
		if(object instanceof Map) { 
			Map input = (Map)object;
			
			//get the new refId from resultMap and set to input map
			Object refIdObj = resultMap.get(IServiceConstants.KEY_ID);
			if (refIdObj != null && !StringUtil.isEmpty(String.valueOf(refIdObj))) {
				Long refId = new Long(String.valueOf(refIdObj));
				input.put(IServiceConstants.KEY_ID, refId);
			}
			input.put(IGenericScreenConstants.SCREEN_CODE, getScreenCode());
		}
		
		if (postSaveService != null)
			postSaveService.invoke(object);
		
		return resultMap;		
	}
	
	public boolean validate(Map objectMap){
		if (preSaveService != null){
			objectMap.put(IGenericScreenConstants.SCREEN_CODE, getScreenCode());
			preSaveService.invoke(objectMap);
		}
		if (SessionManager.getInstance().getSessionData().getMessageStore().getMessages().isEmpty()){
			return true;
		}
		return false;
	}

}
