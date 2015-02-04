package com.wcna.calms.jpos.services.settlement;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.product.services.session.GenericMessage;
import com.wcg.product.services.session.SessionManager;
import com.wcna.calms.jpos.services.quote.IJPOSQuickQuoteService;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.calms.service.settlement.ISettlementService;
import com.wcna.calms.service.settlement.SettlementDataVO;

public class SettlementQuoteRetrieveService extends CalmsAjaxService {

	private final ISettlementQuoteService settlementQuoteService;
	private final IJPOSQuickQuoteService jposQuickQuoteService;
	private final IFormatService formatService;
	private final ISettlementService settlementService;

	private static final String RESPONSE_KEY = "results";

	public SettlementQuoteRetrieveService(ISettlementQuoteService settlementQuoteService, IJPOSQuickQuoteService jposQuickQuoteService,
			IFormatService formatService, ISettlementService settlementService) {
		this.settlementQuoteService = settlementQuoteService;
		this.jposQuickQuoteService = jposQuickQuoteService;
		this.formatService = formatService;
		this.settlementService = settlementService;
	}

	public Object invoke(Object parameter) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Long dagId = Long.parseLong(getUserContainer().getCurrentDagID());
		if (settlementQuoteService.isQuotable(dagId) && parameter != null && parameter instanceof Map) {
			Map map = (Map) parameter;
			int quoteIdx = getQuoteIndex((String) map.get("quoteIdx"));
			String overrideFlag = (String) map.get("override");
			String custAuthFlag = (String) map.get("custAuth");
			String agreementNumber = (String) map.get("agreementNumber");
			// if quoteIdx >= 0, indicates this is launched from quote screen
			boolean isLaunchedFromQuote = quoteIdx >= 0;
			if (isLaunchedFromQuote &&
					jposQuickQuoteService.isPreviousSettlementExists(quoteIdx) &&
					!IConstants.FLAG_YES.equals(overrideFlag)) {
				// require settlement override confirmation
				ret.put("overrideConfirm", IConstants.FLAG_YES);
			} else if (!IConstants.FLAG_YES.equals(custAuthFlag)) {
				// require cust authorization confirmation
				ret.put("custAuthConfirm", IConstants.FLAG_YES);
			} else if (!settlementService.isValidAgreementNumber(agreementNumber)) {
				SessionManager.getInstance().getSessionData().getMessageStore().addMessage(new GenericMessage(GenericMessage.ERROR, "SQ_0001"));
				ret.put("invalidAgreementNumber", IConstants.FLAG_YES);
			} else {
				// we can trigger the settlement quote
//				IJPOSSettlementContainer settlementContainer = settlementQuoteService.getSettlementQuote(agreementNumber);
				IJPOSSettlementContainer settlementContainer = getSettlementQuote(agreementNumber, getUserContainer().getUserID());
				if (settlementContainer != null) {
					settlementQuoteService.setSettlementQuoteToStorage(settlementContainer);
					if (isLaunchedFromQuote) {
						settlementContainer.setInsertNote(true);
						jposQuickQuoteService.setTmpSettlementQuoteToContainer(quoteIdx, settlementContainer);
					}
					ret.put("settlementDetails", settlementQuoteService.getSettlementForDisplay(settlementContainer, getUserContainer().getLocale()));
				} else {
					ret.put("noSettlement", IConstants.FLAG_YES);
				}
			}
		}
		return ret;
	}

	private int getQuoteIndex(String s) {
		int ret = -1;
		if (!StringUtils.isBlank(s) && StringUtils.isNumeric(s)) {
			ret = Integer.parseInt(s);
			// front end will send 1 or 2, but index in quote container starts at 0
			ret -= 1;
		}
		return ret;
	}

	private IJPOSSettlementContainer getSettlementQuote(String agreementNumber, String userId) {
		IJPOSSettlementContainer ret = null;
		if (settlementService != null) {
			String dealerName = jposQuickQuoteService.getDealerNameForSettlement();
			Map<String, Object> requestMap = new HashMap<String, Object>();
			requestMap.put("agreementNumber", agreementNumber);
			requestMap.put("dealerName", dealerName);
			SettlementDataVO responseVO = settlementService.getSettlement(agreementNumber, 0, dealerName, userId);
			if (responseVO != null) {
				ret = settlementQuoteService.getContainerFromVO(responseVO, agreementNumber);
			}
		}
		return ret;
	}

}
