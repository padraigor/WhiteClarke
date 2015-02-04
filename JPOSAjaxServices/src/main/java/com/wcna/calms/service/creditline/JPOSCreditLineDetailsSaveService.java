package com.wcna.calms.service.creditline;

import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.jpos.services.application.IAppButtonService;
import com.wcna.calms.jpos.services.creditline.IJPOSCreditLineDetailsService;
import com.wcna.framework.sensitive.ISensitiveLoadService;
import com.wcna.framework.sensitive.ISensitiveSaveService;

public class JPOSCreditLineDetailsSaveService extends CalmsAjaxService implements ISensitiveSaveService{

	private IJPOSCreditLineDetailsService creditLineDetailsService;
	private final IAppButtonService appButtonService;
	private ISensitiveLoadService loadService;
	
	JPOSCreditLineDetailsSaveService(IJPOSCreditLineDetailsService creditLineDetailsService,
			IAppButtonService appButtonService, ISensitiveLoadService loadService){
		this.creditLineDetailsService = creditLineDetailsService;
		this.appButtonService = appButtonService;
		this.loadService = loadService;
	}

	public Object invoke(Object object) {
		Map<String, Object> dataMap = null;

		if (object != null && object instanceof Map) {
			dataMap = (Map<String, Object>)object;
			long appId = getAppContainer().getAppID();
			// we should always respect the app id from app container
//			if (appId == 0){
//				String propNum = (String)dataMap.get("proposalNumber");
//				if (!StringUtil.isBlank(propNum)){
//					appId = Long.parseLong(propNum);
//				}
//			}
			
			
			dataMap = (Map<String, Object>) dataMap.get("creditLineDetails");

			JPOSCreditLineDetailsVO vo = new JPOSCreditLineDetailsVO();

			
			String advanceType = (String) dataMap.get("advanceType");
			String advanceTypeValue = (String) dataMap.get("advanceTypeValue");
			String effectiveDate = (String) dataMap.get("effectiveDate");
			String existingProposal = (String) dataMap.get("existingProposalNum");
			String expiryDate = (String) dataMap.get("expiryDate");
			String limitAmount = (String) dataMap.get("limitAmount");
			String maxAssetAge = (String) dataMap.get("maxAssetYears");
			String maxTermMonth = (String) dataMap.get("maxTermMonths");
			String newOrExistProp = (String) dataMap.get("newExistingFlag");
			String productType = (String) dataMap.get("productType");
			String relationshipManager = (String) dataMap.get("relationshipManager");
			String sanctionDate = (String) dataMap.get("sanctionedDate");
			String sanctionedUser = (String) dataMap.get("sanctionedUser");
			String creditLineId = (String) dataMap.get("creditLineId");
			
//			Map selectedCreditLine = (Map) dataMap.get("selectedCreditLineData");
//			String creditLineId = "";
//			if (selectedCreditLine != null){
//				creditLineId = (String) selectedCreditLine.get("creditLineId");
//			}
			
			vo.setAdvanceType(advanceType);
			vo.setAdvanceTypeValue(advanceTypeValue);
			vo.setEffectiveDate(effectiveDate);
			vo.setExistingProposal(existingProposal);
			vo.setExpiryDate(expiryDate);
			vo.setLimitAmount(limitAmount);
			vo.setMaxAssetAge(maxAssetAge);
			vo.setMaxTermMonth(maxTermMonth);
			vo.setNewOrExistProp(newOrExistProp);
			vo.setProductType(productType);
			vo.setRelationshipManager(relationshipManager);
			vo.setSanctionDate(sanctionDate);
			vo.setSanctionedUser(sanctionedUser);
			vo.setCreditLineId(creditLineId);
			
			
			if (this.creditLineDetailsService.save(appId,vo)) {
				// refresh the navigation menu
				dataMap.put("buttonList", this.appButtonService.getApplicationButtons());
			}

		}

		return dataMap;
	}
	
	
	public ISensitiveLoadService getLoadService() {
		return loadService;
	}

}
