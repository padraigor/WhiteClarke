package com.wcna.calms.service.asset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.product.services.session.GenericMessage;
import com.wcg.product.services.session.SessionManager;
import com.wcna.calms.log.Logger;
import com.wcna.calms.data.ApplicationBankData;
import com.wcna.calms.data._ApplicationAddressData;
import com.wcna.calms.data._ApplicationBankData;
import com.wcna.calms.data._ApplicationCustomerData;
import com.wcna.calms.jpos.services.asset.IJPOSVehicleCAPDetailsService;
import com.wcna.calms.jpos.services.asset.IJPOSVehicleCAPDetailsVO;
import com.wcna.calms.jpos.services.asset.IJPOSVehicleDetailsService;
import com.wcna.calms.jpos.services.asset.IVehiclePreSaveValidationService;
import com.wcna.calms.jpos.services.quote.JPOSSettlementContainerVO;
import com.wcna.calms.jpos.services.settlement.IJPOSSettlementContainer;
import com.wcna.calms.service.application.IApplicationAddressDataVO;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.bank.IApplicationBankDataVO;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.customer.IApplicationCustomerDataVO;
import com.wcna.calms.service.hpi.IHpiConstants;
import com.wcna.calms.service.validation.ICustomValidator;
import com.wcna.framework.validation.IBusinessRuleValidationService;

public class JPOSVehicleCAPDetailsSaveService extends CalmsAjaxService implements IBusinessRuleValidationService{

	private IJPOSVehicleCAPDetailsService vehCAPDetailsService;
	private final IVehiclePreSaveValidationService preSaveService;
	private String screenCode;

	public JPOSVehicleCAPDetailsSaveService(IJPOSVehicleCAPDetailsService vehCAPDetailsService,
			IVehiclePreSaveValidationService preSaveService){
		this.vehCAPDetailsService = vehCAPDetailsService;
		this.preSaveService = preSaveService;
	}

	public Object invoke(Object object) {
		Map<String, Object> dataMap = null;
		try{

		if (object != null && object instanceof Map) {
			dataMap = (Map<String, Object>)object;



			Map<String, String> vehicleCAPDetailsMap = (Map<String, String>)dataMap.get("capUpdate");
						boolean result = this.vehCAPDetailsService.save(vehicleCAPDetailsMap);
			if(!result){
				//error msg //Needs to get it into Error table
				/*
				GenericMessage message = new GenericMessage(GenericMessage.ERROR, "JPQQ0002")
				SessionManager.getInstance().getSessionData().getMessageStore().addMessage(message);
					}*/
			}


		}
		}catch(Exception er){

		}finally{

		}

		return dataMap;
	}



	public boolean validate(Map objectMap){
		if (preSaveService != null){
			objectMap.put(IGenericScreenConstants.SCREEN_CODE, getScreenCode());
			objectMap.put(IGenericScreenConstants.VALIDATION_EVENT_SOURCE, "SAVE");
			preSaveService.validate(objectMap);


		}
		if (SessionManager.getInstance().getSessionData().getMessageStore().getMessages().isEmpty()){
			return true;
		}
		return false;
	}

	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}

	public String getScreenCode() {
		return screenCode;
	}

}
