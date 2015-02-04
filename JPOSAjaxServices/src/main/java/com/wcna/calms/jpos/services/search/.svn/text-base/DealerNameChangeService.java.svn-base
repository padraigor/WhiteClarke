package com.wcna.calms.jpos.services.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.security.IUserData;
import com.wcna.calms.service.security.IUserService;
import com.wcna.calms.util.OptionLabelValue;
import com.wcna.lang.StringUtil;

public class DealerNameChangeService extends CalmsAjaxService {

	private final IUserService userService;
	
	public DealerNameChangeService(IUserService userService) {
		this.userService = userService;
	}
	
	public Object invoke(Object arg0) {
		List<OptionLabelValue> out = new ArrayList<OptionLabelValue>();
		out.add(new OptionLabelValue("----", "-1"));		
		if (arg0 != null && arg0 instanceof Map) {
			String dagId = (String) ((Map) arg0).get("dagId");
			if (!StringUtil.isEmpty(dagId)) {
				List<IUserData> list = userService.getAllUsersByDagId(dagId, true);
				if (list != null && !list.isEmpty()) {
					int size = list.size();
					for (int i = 0; i < size; i++) {
						IUserData user = list.get(i);
						out.add(new OptionLabelValue(user.getFullName(), user.getGuid()));
					}
				}
			}
		}
		
		return out;
	}

}
