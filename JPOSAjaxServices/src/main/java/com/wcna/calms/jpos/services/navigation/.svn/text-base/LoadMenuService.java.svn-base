package com.wcna.calms.jpos.services.navigation;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.navigation.IMenuService;

public class LoadMenuService extends CalmsAjaxService {
	
	private IMenuService menuService;

	public LoadMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}
	
	public Object invoke(Object arg0) {
		return menuService.getAllMenus();
	}
}
