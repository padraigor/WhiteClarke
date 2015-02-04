package com.wcna.calms.jpos.services.summary;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.jpos.services.quote.IJPOSQuickQuoteService;
import com.wcna.lang.StringUtil;

public class JPOSProposalFinanceDetailsVehicleService extends CalmsAjaxService  {

	private final IJPOSQuickQuoteService jposQuickQuoteService;
	public JPOSProposalFinanceDetailsVehicleService(IJPOSQuickQuoteService jposQuickQuoteService){
		this.jposQuickQuoteService = jposQuickQuoteService;
	}
	public Object invoke(Object arg0) {

		Map<String, Object> proposalMap = new HashMap<String, Object>();
		proposalMap.put("financeDetailsSummary", jposQuickQuoteService.getQuoteSummaryBean(getAppContainer().getAppID(), Long.parseLong(getUserContainer().getRoleID())));
		String vehicleSummary = jposQuickQuoteService
				.getVehicleSummaryDesc(getAppContainer().getAppID() + "");
				if (!StringUtil.isEmpty(vehicleSummary)) {
			vehicleSummary = vehicleSummary.replaceAll("\\n", "<br/>");
		}
		proposalMap.put("financeDetailsVehicle", vehicleSummary);
		return proposalMap;
	}
}
