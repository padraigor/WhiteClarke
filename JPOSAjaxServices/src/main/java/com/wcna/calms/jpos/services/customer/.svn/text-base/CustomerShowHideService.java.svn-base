package com.wcna.calms.jpos.services.customer;

import java.util.Map;

import com.wcna.calms.service.common.IUserContainer;
import com.wcna.calms.service.customer.ICustomerShowHideService;

public abstract class CustomerShowHideService implements ICustomerShowHideService {


	public void showHide(Map<String, Object> map, IUserContainer userContainer) {
		boolean condition = condition(userContainer);
		if (!condition) {
			String[] fields = getFieldNames();
			for (String field : fields) {
				map.remove(field);
			}
		}
		map.put(getFlagName(), condition ? "Y" : "N");
	}

}
