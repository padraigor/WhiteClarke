package com.wcna.calms.jpos.services.vap.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.common.IConstants;

public class SavePlanVapAssociations extends CalmsAjaxService {

	private final IVapAdminService vapAdminService;
	
	public SavePlanVapAssociations(IVapAdminService vapAdminService) {
		this.vapAdminService = vapAdminService;
	}
	
	public Object invoke(Object parameter) {
		if (parameter != null && parameter instanceof Map) {
			Map map = (Map) parameter;
			String planId = (String) map.get("planId");
			List<Map<String, Object>> planVaps = (List<Map<String, Object>>) map.get("planVaps");
			List<IPlanVapAssociationBean> planVapAssociationBeans = new ArrayList<IPlanVapAssociationBean>();
			if (planVaps != null && !planVaps.isEmpty()) {
				for (Map<String, Object> m : planVaps) {
					planVapAssociationBeans.add( this.getPlanVapAssociationBean(m, planId) );
				}
			}
			boolean success = this.vapAdminService.savePlanVapAssociations(planVapAssociationBeans, planId);
			Map<String, String> ret = new HashMap<String, String>();
			ret.put("success", success ? IConstants.FLAG_YES : IConstants.FLAG_NO);
			return ret;
		}
		return null;
	}

	private IPlanVapAssociationBean getPlanVapAssociationBean(Map<String, Object> map, String planId) {
		IPlanVapAssociationBean bean = this.createBean(IPlanVapAssociationBean.class);
		bean.setCustomerTypes( (List<String>) map.get("customerTypes") );
		bean.setDisclosed((String) map.get("disclosed"));
		bean.setGroupId((String) map.get("group"));
		bean.setPlanId(planId);
		List<Map<String, Object>> roleStatuses = (List<Map<String, Object>>) map.get("roles");
		if (roleStatuses != null && !roleStatuses.isEmpty()) {
			List<IVapRoleStatusBean> roleStatusBeans = new ArrayList<IVapRoleStatusBean>();
			bean.setRoleStatuses(roleStatusBeans);
			for (Map<String, Object> m : roleStatuses) {
				IVapRoleStatusBean roleStatus = this.createBean(IVapRoleStatusBean.class);
				roleStatusBeans.add(roleStatus);
				roleStatus.setExtRoleIds((List<String>) m.get("roles"));
				roleStatus.setStatus((String) m.get("status"));
			}
		}
		bean.setVapId((String) map.get("vapId"));
		bean.setUpgradeVapId((String) map.get("upgradeVapId"));
		return bean;
	}
	
}
