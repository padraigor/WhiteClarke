package com.wcna.calms.jpos.services.security;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.security.IDAGService;
import com.wcna.calms.service.security.IRoleService;
import com.wcna.calms.service.security.IRoleTypeData;

public class UnproxyService extends CalmsAjaxService {

	private IDAGService dagService;
	private IRoleService roleService;

	public UnproxyService(IDAGService dagService, IRoleService roleService) {
		this.dagService = dagService;
		this.roleService = roleService;
	}

	public Object invoke(Object arg0) {
		this.dagService.unproxyDag();
		this.roleService.unproxyRole();

		IRoleTypeData roleType = roleService.getRoleTypeByRoleId(Long.valueOf(this.getUserContainer().getRoleID()));

		Map<String, String> ret = new HashMap<String, String>();
		ret.put("brandCode", this.getUserContainer().getCurrentBrandCode());
		ret.put("isInternalUser", (roleType != null && roleType.isIsInternallFlag())? IConstants.FLAG_YES:IConstants.FLAG_NO);

		return ret;
	}

}
