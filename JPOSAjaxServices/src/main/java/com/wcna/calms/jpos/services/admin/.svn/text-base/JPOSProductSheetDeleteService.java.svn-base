package com.wcna.calms.jpos.services.admin;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.common.util.Logger;
import com.wcg.product.calms.security.CalmsSessionData;
import com.wcg.product.services.session.GenericMessage;
import com.wcg.product.services.session.SessionManager;
import com.wcna.calms.jpos.services.productsheet.IProductSheetDataLoadService;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.calms.util.BeanConverterUtil;
import com.wcna.lang.NumberUtil;
import com.wcna.lang.StringUtil;

public class JPOSProductSheetDeleteService extends CalmsAjaxService {

	private IJPOSAdminSheetService service;

	public JPOSProductSheetDeleteService(IJPOSAdminSheetService service) {
		this.service = service;
	}

	public Object invoke(Object object) {
		HashMap<String,Object> retMap = new HashMap<String,Object>();
		CalmsSessionData sessionData = (CalmsSessionData) SessionManager.getInstance().getSessionData();
		GenericMessage genericMessage = null;

		if (object instanceof Map) {
			Map map = (Map) object;

			int productSheetGroupId = NumberUtil.toIntPrimitive((String)map.get("productSheetGroupId"), 0);

			if (productSheetGroupId != 0){
				boolean isDeleted = service.deleteProductSheetGroup(productSheetGroupId);
			}
		}
		return retMap;
	}
}
