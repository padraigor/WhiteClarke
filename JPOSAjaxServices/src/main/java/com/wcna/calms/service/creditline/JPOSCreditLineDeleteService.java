package com.wcna.calms.service.creditline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.jpos.services.application.IAppButtonKey;
import com.wcna.calms.jpos.services.application.IAppButtonService;
import com.wcna.calms.jpos.services.creditline.IJPOSCreditLineDetailsService;
import com.wcna.calms.jpos.services.creditline.IJPOSCreditLineDetailsVO;
import com.wcna.lang.StringUtil;

public class JPOSCreditLineDeleteService  extends CalmsAjaxService implements IAppButtonKey {
	
	IJPOSCreditLineDetailsService creditLineDetailsService;
//	private final IAppButtonService appButtonService;
	
	JPOSCreditLineDeleteService(IJPOSCreditLineDetailsService creditLineDetailsService){
		this.creditLineDetailsService = creditLineDetailsService;
//		this.appButtonService = appButtonService;
	}
	
	public Object invoke(Object parameter) {
		List<IJPOSCreditLineDetailsVO> vos = new ArrayList<IJPOSCreditLineDetailsVO>();
		HashMap<String,Object> retMap = new HashMap<String,Object>();
		if (parameter instanceof Map) {
			Map map = (Map) parameter;
			if (map == null) return parameter;
			
			String propNum = (String)map.get("proposalNumber");
//			long appId = 0;
//			if (!StringUtil.isBlank(propNum)){
//				appId = Long.parseLong(propNum);
//			}
			long appId = this.getAppContainer().getAppID();
			
			List <Map>appCreditLines = (List<Map>) map.get("appCreditLines");
			for (Iterator <Map>it = appCreditLines.iterator(); it.hasNext();){
				Map creditLine = (Map) it.next();
				
				if (creditLine.get("selected") != null){
					if (creditLine.get("selected").toString() == "true"){
						JPOSCreditLineDetailsVO vo = new JPOSCreditLineDetailsVO();
						vo.setAdvanceType((String) creditLine.get("advanceType"));
						vo.setNewOrExistProp((String) creditLine.get("newExistingFlag"));
						vo.setSanctionedUser((String) creditLine.get("sanctionedUser"));
						vo.setMaxTermMonth((String) creditLine.get("maxTermMonths"));
						vo.setEffectiveDate((String) creditLine.get("effectiveDate"));
						vo.setSanctionDate((String) creditLine.get("sanctionedDate"));
						vo.setCreditLineId((String) creditLine.get("creditLineId"));
						vo.setExpiryDate((String) creditLine.get("expiryDate"));
						vo.setProductType((String) creditLine.get("productType"));
						vo.setAdvanceTypeValue((String) creditLine.get("advanceTypeValue"));
						vo.setLimitAmount((String) creditLine.get("limitAmount"));
						vo.setMaxAssetAge((String) creditLine.get("maxAssetYears"));
						vos.add(vo);
					}
				}
				
			}
			
			if (creditLineDetailsService.deleteCreditLines(appId,vos)) {
				// refresh the navigation menu
//				retMap.put("buttonList", this.appButtonService.getApplicationButtons());
			}
		}
		return retMap;
		
	}

	public String getButtonKey() {
		// no buttons to refresh on the credit line section of proposal summary
		// when deleting a credit line
		return null;
	}

}
