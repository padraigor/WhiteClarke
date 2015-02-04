package com.wcna.calms.jpos.services.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.product.calms.security.CalmsSessionData;
import com.wcg.product.services.session.GenericMessage;
import com.wcg.product.services.session.SessionManager;
import com.wcg.service.util.ApplicationUtil;
import com.wcna.calms.jpos.services.quote.IJPOSQuickQuoteService;
import com.wcna.calms.service.application.IAppCreditLimitDataVO;
import com.wcna.calms.service.application.IApplicationAddressDataVO;
import com.wcna.calms.service.application.IApplicationDataVO;
import com.wcna.calms.service.application.IApplicationService;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.common.IUserContainer;
import com.wcna.calms.service.customer.IApplicationCustomerDataVO;
import com.wcna.calms.service.customer.ICustomerService;
import com.wcna.calms.service.security.IDAGService;

public class GetCustomerDetailsService extends CalmsAjaxService {

	private final IApplicationService appService;
	private final ICustomerService customerService;
	private final IJPOSSearchService jposSearchService;
	private final IJPOSQuickQuoteService jposQuickQuoteService;


	public GetCustomerDetailsService(IApplicationService appService, ICustomerService customerService, IJPOSSearchService jposSearchService,
									 IJPOSQuickQuoteService jposQuickQuoteService) {
		this.customerService = customerService;
		this.appService = appService;
		this.jposSearchService = jposSearchService;
		this.jposQuickQuoteService = jposQuickQuoteService;
	}

	public Object invoke(Object arg0) {
		String appId = null;
		if (arg0 != null && arg0 instanceof Map) {
			appId = (String) ((Map) arg0).get("appId");
		}

		if (appId == null) {
			return null;
		}

		//For security, check if the user has access to the application or not
		CalmsSessionData sessionData = (CalmsSessionData) SessionManager.getInstance().getSessionData();
		IUserContainer userContainer = sessionData.getUserContainer();
		IDAGService dagService = ApplicationUtil.getInstance().getBean(IDAGService.class);
		if (!dagService.isWritableByUser(appId,userContainer)) {
			sessionData.getMessageStore().addMessage(new GenericMessage(GenericMessage.ERROR, "W0012"));
			return null;
		}

		long appId_long = Long.valueOf(appId).longValue();

		IApplicationDataVO appData = appService.getApplicationVOById(appId_long);
		IApplicationCustomerDataVO custData = customerService.getCustomerData(appId_long, IConstants.PRIMARY_APPLICANT_REF_ID);
		IApplicationAddressDataVO addressData = null;
		if (custData != null) {
			addressData = customerService.getCustomerAddress(appId_long, IConstants.PRIMARY_APPLICANT_REF_ID, customerService.getCustomerAddressType(custData.getCustomerTypeCode()));
		}

		List<IAppCreditLimitDataVO> appCreditlines = appService.getAppCreditLineVOById(appId_long);
		String creditLines = jposSearchService.getCreditLineSummaryDesc(appCreditlines);

		StringBuffer sb = new StringBuffer();
		if (IConstants.PROPOSAL_APPLICATION_TYPE.equals(appData.getAppTypeCode())) {
			sb.append(jposSearchService.getApplicationSummaryDesc(appData));
			sb.append("\n\n");
		}
		sb.append(jposSearchService.getCustomerSummaryDesc(custData));
		sb.append("\n");
		sb.append(jposSearchService.getAddressSummaryDesc(addressData));

		Map<String, String> rtn = new HashMap<String, String>();
		rtn.put("customerSummary", sb.toString().replaceAll("\\n", "<br/>"));
//		rtn.put("financeSummary", jposSearchService.getQuoteSummaryDesc(null));
		sb = new StringBuffer();
		sb.append(jposQuickQuoteService.getVehicleSummaryDescWithDealership(appId));
//		sb.append(jposQuickQuoteService.getPlanSummaryDesc(null));
		String desc = jposQuickQuoteService.getQuoteSummaryDesc(appId);
		if (desc == null) {
			desc = "";
		}
		desc = desc.replaceAll("&&", "<br/>");
		sb.append(desc);
		rtn.put("financeSummary", sb.toString().replaceAll("\\n", "<br/>"));

//		rtn.put("appType", jposQuickQuoteService.getApplicationType(appId_long));
		rtn.put("appType", appData.getAppTypeCode());

		rtn.put("creditLineSummary", creditLines.replaceAll("\\n", "<br/>"));

		rtn.put("subTypeCode", appData.getSubTypeCode());

		rtn.put("creditLineSubTypeCode", IConstants.CREDIT_LINE_SUBTYPE_CODE);

		return rtn;
	}

}
