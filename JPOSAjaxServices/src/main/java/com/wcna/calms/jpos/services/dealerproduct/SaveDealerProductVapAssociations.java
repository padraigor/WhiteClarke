package com.wcna.calms.jpos.services.dealerproduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.jpos.services.dealerproduct.IDealerProductVapAssociationBean;
import com.wcna.calms.jpos.services.dealerproduct.IDealerProductVapCustTypeBean;
import com.wcna.calms.jpos.services.dealerproduct.IDealerProductVapRoleStatusBean;
import com.wcna.calms.jpos.services.vap.admin.IVapAdminService;
import com.wcna.calms.service.common.IConstants;

public class SaveDealerProductVapAssociations extends CalmsAjaxService {

	private final IDealerProductVapService dealerProductVapService;

	public SaveDealerProductVapAssociations(IDealerProductVapService dealerProductVapService) {
		this.dealerProductVapService = dealerProductVapService;
	}

	public Object invoke(Object parameter) {
		if (parameter != null && parameter instanceof Map) {
			Map map = (Map) parameter;
			String dealerProductId = (String) map.get("dealerProductId");
			List<Map<String,Object>> dealerProductVaps = (List<Map<String,Object>>) map.get("dealerProductVaps");
			List<IDealerProductVapAssociationBean> dpvab = new ArrayList<IDealerProductVapAssociationBean>();
			if (dealerProductVaps != null && !dealerProductVaps.isEmpty()){
				for (Map<String,Object> m: dealerProductVaps) {
					dpvab.add(this.getDealerProductVapAssociationBean(m, dealerProductId));
				}
			}
			boolean success = this.dealerProductVapService.saveDealerProductVaps(dpvab, dealerProductId);
			Map<String,String> ret = new HashMap<String,String>();
			ret.put("success", success? IConstants.FLAG_YES: IConstants.FLAG_NO);
			return ret;
		}
		return null;
	}

	private IDealerProductVapAssociationBean getDealerProductVapAssociationBean(Map<String,Object>map, String dealerProductId){
		IDealerProductVapAssociationBean bean = this.createBean(IDealerProductVapAssociationBean.class);
		List<String> customerTypes = (List<String>) map.get("customerTypes");
		if (customerTypes != null && !customerTypes.isEmpty()) {
			List<IDealerProductVapCustTypeBean> custTypeBeanList = new ArrayList<IDealerProductVapCustTypeBean>();
			for(String customerType : customerTypes) {
				IDealerProductVapCustTypeBean dpvctb = this.createBean(IDealerProductVapCustTypeBean.class);
				dpvctb.setExtCustomerType(customerType);
				custTypeBeanList.add(dpvctb);
			}
			bean.setCustomerTypes(custTypeBeanList);
		}
		bean.setDisclosed((String)map.get("disclosed"));
		bean.setGroupId((String)map.get("group"));
		bean.setDealerPlanId((String)map.get("dealerProductId"));
		List<Map<String,Object>> roleStatuses = (List<Map<String,Object>>)map.get("roles");
		if (roleStatuses != null && !roleStatuses.isEmpty()) {
			List<IDealerProductVapRoleStatusBean> roleStatusBeanList = new ArrayList<IDealerProductVapRoleStatusBean>();
			for(Map<String,Object> m: roleStatuses) {
				IDealerProductVapRoleStatusBean roleStatusBean = this.createBean(IDealerProductVapRoleStatusBean.class);
				roleStatusBean.setExtRoleIds((List<String>) m.get("roles"));
				roleStatusBean.setStatus((String)m.get("status"));
				roleStatusBeanList.add(roleStatusBean);
			}
			bean.setRoleStatuses(roleStatusBeanList);
		}
		bean.setVapId((String)map.get("vapId"));
		bean.setUpgradeVapId((String)map.get("upgradeVapId"));
		return bean;
	}


}
