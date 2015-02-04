package com.wcna.calms.jpos.services.vap.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.product.ajax.ajaxserver.security.SecuredHashMap;
import com.wcna.calms.service.common.IConstants;

public class SaveVapService extends CalmsAjaxService {

	private final IVapAdminService vapAdminService;
	
	public SaveVapService(IVapAdminService vapAdminService) {
		this.vapAdminService = vapAdminService;
	}
	
	public Object invoke(Object parameter) {
		if (parameter != null && parameter instanceof Map) {
			Map map = (Map) parameter;
			IVapBean vap = this.getVapBean((Map) map.get("vapData"));
			
//			List<IVapCommissionBean> dealerCommissions = null;
//			List<IVapCommissionBean> salesmanCommissions = null;
//			
//			List<Map> tmp = (List<Map>) map.get("dealerCommissions");
//			if (tmp != null && !tmp.isEmpty()) {
//				dealerCommissions = new ArrayList<IVapCommissionBean>();
//				for (Map _m : tmp) {
//					dealerCommissions.add(this.getVapCommissionBean(_m));
//				}
//			}
//			tmp = (List<Map>) map.get("salesmanCommissions");
//			if (tmp != null && !tmp.isEmpty()) {
//				salesmanCommissions = new ArrayList<IVapCommissionBean>();
//				for (Map _m : tmp) {
//					salesmanCommissions.add(this.getVapCommissionBean(_m));
//				}
//			}
			
			long vapId = this.vapAdminService.save(vap);
			Map<String, Object> ret = new HashMap<String, Object>();
			String success = vapId > 0 ? IConstants.FLAG_YES : IConstants.FLAG_NO;
			ret.put("success", success);
			ret.put("vapId", vapId + "");
			
			// on success, we will reload the vap list
			if (vapId > 0) {
				boolean isLive = IConstants.FLAG_YES.equals( (String) map.get("isLive") );
				ret.put("vapList", vapAdminService.getVaps(isLive));
				ret.put("vapKeys", vapAdminService.getOrderedVapKeys(vapId));
			}
			
			return ret;
		}
		return null;
	}

	private IVapCommissionBean getVapCommissionBean(Map source) {
		IVapCommissionBean ret = this.createBean(IVapCommissionBean.class);
		
		ret.setValidFrom((String) source.get("validFrom"));
		ret.setValidTo((String) source.get("validTo"));
		ret.setValue((String) source.get("value"));
		
		return ret;
	}
	
	private IVapBean getVapBean(Map source) {
		IVapBean vap = this.createBean(IVapBean.class);
		
		vap.setCalcType((String) source.get("calcType"));
		vap.setDlrCommissionType((String) source.get("dlrCommissionType"));
		vap.setPremiumApp((String) source.get("premiumApp"));
		vap.setSalesmanCommissionType((String) source.get("salesmanCommissionType"));
		vap.setScheduleNumber((String) source.get("scheduleNumber"));
		vap.setTaxRateCode((String) source.get("taxRateCode"));
		vap.setValidFrom((String) source.get("validFrom"));
		vap.setValidTo((String) source.get("validTo"));
		vap.setVapId((String) source.get("vapId"));
//		vap.setVapKeys((List<String>) source.get("vapKeys"));
		vap.setVapKeysInput(this.getVapKeys(source));
		vap.setVapName((String) source.get("vapName"));
		vap.setExtReference((String) source.get("extReference"));
		vap.setType((String) source.get("type"));
		vap.setPaymentProfile((String) source.get("paymentProfile"));
		vap.setTerm((String) source.get("term"));
		vap.setAffectsTaeg((String) source.get("affectsTaeg"));
		vap.setAffectsTeg((String) source.get("affectsTeg"));
		vap.setLicenceToSell((String) source.get("licenceToSell"));
		vap.setInformation((String) ((SecuredHashMap)source).managedGet("information", "", "Note", 2000, true));
		
		return vap;
	}
	
	private List<IVapKey> getVapKeys(Map source) {
		List<IVapKey> ret = null;
		List<Map<String, String>> vapKeys = (List<Map<String, String>>) source.get("vapKeys");
		if (vapKeys != null && !vapKeys.isEmpty()) {
			ret = new ArrayList<IVapKey>();
			for (Map<String, String> vapKeyMap : vapKeys) {
				IVapKey vapKey = this.createBean(IVapKey.class);
				vapKey.setOrder((String) vapKeyMap.get("order"));
				vapKey.setVapKeyId((String) vapKeyMap.get("vapKeyId"));
				ret.add(vapKey);
			}
		}
		return ret;
	}
	
}
