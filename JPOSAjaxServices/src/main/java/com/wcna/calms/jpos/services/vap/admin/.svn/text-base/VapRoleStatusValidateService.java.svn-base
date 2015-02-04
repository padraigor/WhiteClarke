package com.wcna.calms.jpos.services.vap.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.common.IConstants;

public class VapRoleStatusValidateService extends CalmsAjaxService {

	private final IVapAdminService vapAdminService;
	
	public VapRoleStatusValidateService(IVapAdminService vapAdminService) {
		this.vapAdminService = vapAdminService;
	}
	
	public Object invoke(Object parameter) {
		if (parameter != null && parameter instanceof Map) {
			List<Map<String, Object>> roleStatuses = (List<Map<String, Object>>) ((Map) parameter).get("roleStatuses");
			List<IVapRoleStatusBean> roleStatusBeanList = new ArrayList<IVapRoleStatusBean>();
			if (roleStatuses != null && !roleStatuses.isEmpty()) {
				for (Map<String, Object> m : roleStatuses) {
					IVapRoleStatusBean b = this.createBean(IVapRoleStatusBean.class);
					roleStatusBeanList.add(b);
					b.setStatus((String) m.get("status"));
					List<String> roles = (List<String>) m.get("roles");
					if (roles != null && !roles.isEmpty()) {
						b.setExtRoleIds(roles);
					}
				}
			}
			boolean bValid = this.vapAdminService.validateRoleStatuses(roleStatusBeanList);
			Map<String, String> ret = new HashMap<String, String>();
			ret.put("isValid", bValid ? IConstants.FLAG_YES : IConstants.FLAG_NO);
			return ret;
			
		}
		return null;
	}

}
