package com.wcna.calms.jpos.services.usura.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class UsuraAdminGetVersionDetailsService extends CalmsAjaxService {

	private final IUsuraAdminService usuraAdminService;

	public UsuraAdminGetVersionDetailsService(IUsuraAdminService usuraAdminService) {
		this.usuraAdminService = usuraAdminService;
	}

	public Object invoke(Object parameter) {
		if (parameter != null && parameter instanceof Map) {
			String _versionId = (String) ((Map) parameter).get("usuraVersionId");
			if (!StringUtils.isBlank(_versionId)
					&& StringUtils.isNumeric(_versionId)) {
				long versionId = Long.valueOf(_versionId);
				IUsuraVersionBean bean = this.usuraAdminService.getUsuraVersionBean(versionId);
				if (bean != null) {
					Map<String, Object> ret = new HashMap<String, Object>();

					Map<String, String> versionDetails = new HashMap<String, String>();
					ret.put("versionDetails", versionDetails);

					versionDetails.put("validFrom", bean.getValidFrom());
					versionDetails.put("validTo", bean.getValidTo());
					versionDetails.put("versionId", bean.getVersionId());

					List<Map<String, String>> usuraRecords = new ArrayList<Map<String, String>>();
					ret.put("usuraRecords", usuraRecords);

					List<IUsuraLimitBean> limits = bean.getUsuraLimits();
					if (limits != null && !limits.isEmpty()) {
						for (IUsuraLimitBean p : limits) {
							Map<String, String> map = new HashMap<String, String>();
							usuraRecords.add(map);

							map.put("taeg", p.getTaegValue());
							map.put("maxAge", p.getAgeCeiling());
							map.put("finTypeId", p.getFinanceTypeId());
							map.put("extCustomerTypeId", p.getExtCustomerTypeId());
							map.put("amountFinanced",p.getAmountFinanced());
						}
					}

					return ret;
				}
			}
		}
		return null;
	}

}
