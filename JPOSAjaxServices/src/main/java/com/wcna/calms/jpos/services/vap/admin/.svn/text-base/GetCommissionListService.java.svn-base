package com.wcna.calms.jpos.services.vap.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.util.OptionLabelValue;

public class GetCommissionListService extends CalmsAjaxService {

	private final IVapAdminService vapAdminService;
	
	public GetCommissionListService(IVapAdminService vapAdminService) {
		this.vapAdminService = vapAdminService;
	}
	
	public Object invoke(Object parameter) {
		if (parameter != null && parameter instanceof Map) {
			String type = (String) ((Map) parameter).get("commissionType");
			String vapIdStr = (String) ((Map) parameter).get("vapId");
			if (!StringUtils.isBlank(type) && !StringUtils.isBlank(vapIdStr) && StringUtils.isNumeric(vapIdStr)) {
				long vapId = Long.valueOf(vapIdStr);
				Map<String, List<OptionLabelValue>> ret = new HashMap<String, List<OptionLabelValue>>();
				List<OptionLabelValue> list = new ArrayList<OptionLabelValue>();
				ret.put("retList", list);
				List<IVapCommissionBean> vcbList = null;
				if ("1".equals(type)) {
					vcbList = this.vapAdminService.getDealerCommissions(vapId);
				} else if ("2".equals(type)) {
					vcbList = this.vapAdminService.getSalesmanCommissions(vapId);
				}
				if (vcbList != null && !vcbList.isEmpty()) {
					for (IVapCommissionBean vcb : vcbList) {
						list.add( new OptionLabelValue(vcb.getValidFrom() + " - " + vcb.getValidTo(), vcb.getCommissionVersionId()) );
					}
				}
				return ret;
			}
		}
		return null;
	}

}
