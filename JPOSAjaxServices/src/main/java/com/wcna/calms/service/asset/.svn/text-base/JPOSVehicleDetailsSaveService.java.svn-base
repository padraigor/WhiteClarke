package com.wcna.calms.service.asset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.product.services.session.SessionManager;
import com.wcna.calms.data._ApplicationAddressData;
import com.wcna.calms.data._ApplicationBankData;
import com.wcna.calms.data._ApplicationCustomerData;
import com.wcna.calms.jpos.services.application.IAppButton;
import com.wcna.calms.jpos.services.application.IAppButtonService;
import com.wcna.calms.jpos.services.application.IProposalSummaryService;
import com.wcna.calms.jpos.services.asset.IJPOSVehicleDetailsService;
import com.wcna.calms.jpos.services.asset.IVehiclePreSaveValidationService;
import com.wcna.calms.jpos.services.quote.JPOSSettlementContainerVO;
import com.wcna.calms.jpos.services.settlement.IJPOSSettlementContainer;
import com.wcna.calms.log.Logger;
import com.wcna.calms.service.application.IApplicationAddressDataVO;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.bank.IApplicationBankDataVO;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.customer.IApplicationCustomerDataVO;
import com.wcna.framework.validation.IBusinessRuleValidationService;
import com.wcna.util.SystemException;

public class JPOSVehicleDetailsSaveService extends CalmsAjaxService implements IBusinessRuleValidationService{

	private IJPOSVehicleDetailsService vehDetailsService;
	private final IVehiclePreSaveValidationService preSaveService;
    private final IProposalSummaryService proposalSummaryService;
	private String screenCode;

	public JPOSVehicleDetailsSaveService(IJPOSVehicleDetailsService vehDetailsService,
			IVehiclePreSaveValidationService preSaveService, IProposalSummaryService proposalSummaryService){
		this.vehDetailsService = vehDetailsService;
		this.preSaveService = preSaveService;
        this.proposalSummaryService = proposalSummaryService;
	}

	public Object invoke(Object object) {
		Map<String, Object> dataMap = null;

		if (object != null && object instanceof Map) {
			dataMap = (Map<String, Object>)object;


			JPOSVehicleDetailsVO vehicleDetailsVO = new JPOSVehicleDetailsVO();
			IApplicationBankDataVO applicationBankDataVO = new _ApplicationBankData();
			IApplicationAddressDataVO applicationAddressDataVO = new _ApplicationAddressData();
			IApplicationCustomerDataVO applicationCustomerDataVO = new _ApplicationCustomerData();
			// JPOSContraSettlementVO ContraSettlementVO = new JPOSContraSettlementVO();
			IJPOSSettlementContainer settlementContainerVO = new JPOSSettlementContainerVO();
			Map<String, String> vehicleDetailsMap = (Map<String, String>)dataMap.get("vehicleDetailsPopup");
			List<Map<String, String>> applicationBankMap = (ArrayList<Map<String, String>>)(dataMap.get("applicationBankList"));
			List<Map<String, String>> applicationBankAddressMap = (ArrayList<Map<String, String>>)dataMap.get("applicationBankAddressList");
			Map<String ,String> applicationCustomerMap = (Map<String, String>)dataMap.get("applicationCustomer_type_consumer");
			Map<String, String> ContraSettlementMap = (Map<String, String>)dataMap.get("settlementQuote");

			try {
				org.apache.commons.beanutils.BeanUtils.populate(vehicleDetailsVO, vehicleDetailsMap);

		//		for(Map<String, String> applicationBankMap1 :applicationBankMap){
				org.apache.commons.beanutils.BeanUtils.populate(applicationBankDataVO, applicationBankMap.get(0));
		//		}

		//		for(Map<String, String> applicationBankAddressMap1 :applicationBankAddressMap){
				org.apache.commons.beanutils.BeanUtils.populate(applicationAddressDataVO, applicationBankAddressMap.get(0));
		//		}

				org.apache.commons.beanutils.BeanUtils.populate(applicationCustomerDataVO, applicationCustomerMap);
				org.apache.commons.beanutils.BeanUtils.populate(settlementContainerVO, ContraSettlementMap);

			} catch (Exception e) {
				if (Logger.isDebugEnabled())
					Logger.debug(JPOSVehicleDetailsSaveService.class.getName() + ":" + e.getMessage());
			}

			vehicleDetailsVO.setConfirmSave(IConstants.FLAG_YES.equals((String) dataMap.get("isConfirmSave")));
			String ret = this.vehDetailsService.save(vehicleDetailsVO,applicationBankDataVO,applicationAddressDataVO,applicationCustomerDataVO,settlementContainerVO);
			if (IJPOSVehicleDetailsService.CONFIRM_REQUIRED.equals(ret)) {
				dataMap.put("notifyUser", IConstants.FLAG_YES);
			} else if (IJPOSVehicleDetailsService.SAVE_SUCCESSFUL_DOC_VERSION_UPDATED.equals(ret)) {
				dataMap.put("newDocVersion", this.getAppContainer().getDocumentVersion() + "");
			}

		}

		return dataMap;
	}

	public boolean validate(Map objectMap){

        Map<String, Map<String, IAppButton>> buttonsMap;
        Map<String, IAppButton> buttons;
        IAppButton button;

        buttonsMap = proposalSummaryService.getProposalSummaryButtons(1, 1, 1, 1, 1); // we don't care about the counts...
        buttons    = buttonsMap.get("finance");
        button     = buttons.get("updateVehicleDetails");

        if (!button.isVisible() || button.isReadonly()) {
            throw new SystemException("Access denied");
        }

		if (preSaveService != null){
			objectMap.put(IGenericScreenConstants.SCREEN_CODE, getScreenCode());
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
