package com.wcna.calms.jpos.services.customer;

import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.common.IUserContainer;
import com.wcna.calms.service.security.IRoleService;

public class CustomerPrioritySectionShowHideService extends
		CustomerShowHideService {

	private final IRoleService roleService;

	public String[] getFieldNames() {
		return new String[] {IGenericScreenConstants.APP_CUSTOMER_PRIORITY, IGenericScreenConstants.APP_CUSTOMER_SERVICE_FEE_CONTRIBUTION};
	}

	public String getFlagName() {
		return "internalUser";
	}

	public CustomerPrioritySectionShowHideService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public boolean condition(IUserContainer userContainer) {
		boolean internalFlag = roleService.isInternalRole(Long.parseLong(userContainer.getRoleID()));
		return internalFlag;
	}

}
