package com.wcg.calms.jpos.services.simulation;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.product.services.session.GenericMessage;
import com.wcg.product.services.session.IMessageStore;
import com.wcg.product.services.session.SessionManager;
import com.wcna.calms.data.ApplicationData;
import com.wcna.calms.jpos.services.quote.IErrorBean;
import com.wcna.calms.jpos.services.quote.IErrorList;
import com.wcna.calms.jpos.services.quote.IJPOSQuickQuoteService;
import com.wcna.calms.jpos.services.quote.IQuoteVehicleBean;
import com.wcna.calms.jpos.services.quote.JPOSQuickQuoteSimulationCalcService;
import com.wcna.calms.util.DataAccessSession;
import com.wcna.lang.StringUtil;

public class SimulationSaveService extends CalmsAjaxService {

	private final ISimulationService simulationService;
	private final JPOSQuickQuoteSimulationCalcService quoteSimulationCalcService;
	private final IJPOSQuickQuoteService jposQuickQuoteService;

	public SimulationSaveService(ISimulationService simulationService,
			JPOSQuickQuoteSimulationCalcService quoteSimulationCalcService, IJPOSQuickQuoteService jposQuickQuoteService) {
		this.simulationService = simulationService;
		this.quoteSimulationCalcService = quoteSimulationCalcService;
		this.jposQuickQuoteService	= jposQuickQuoteService;
	}

	public Object invoke(Object object) {
		Map<String, Object> dataMap = (Map<String, Object>) object;
		Map<String, Object> input = (Map) dataMap.get("simulation");
		String proposalNumber = (String) input.get("proposalNumber");

		if (!StringUtil.isBlank(proposalNumber)) {
			long appId = Long.parseLong(proposalNumber);
			Session hs = DataAccessSession.getInstance().getSession();
			ApplicationData appData = (ApplicationData) hs.get(
					ApplicationData.class, appId);
			if (appData != null && appData.getDefaultQuote() != null) {
				Object quoteBean = quoteSimulationCalcService.invoke(input);
				if (quoteBean != null)
				{
					IQuoteVehicleBean quoteVehicleBean = (IQuoteVehicleBean) quoteBean;

					IMessageStore messageStore = SessionManager.getInstance().getSessionData().getMessageStore();
					boolean isCalcError = false;
					IErrorList errorList = quoteVehicleBean.getErrorList();
					if (errorList != null && errorList.getErrorBeanList() != null && !errorList.getErrorBeanList().isEmpty()) {
						List<IErrorBean> errorBeanList = errorList.getErrorBeanList();
						int size = errorBeanList.size();
						isCalcError = true;
						for (int i = 0; i < size; i++) {
							IErrorBean errorBean = errorBeanList.get(i);
							messageStore.addMessage(new GenericMessage(jposQuickQuoteService.getCalcErrorTranslation(errorBean, this.getUserContainer().getLanguageCode()), GenericMessage.ERROR, null));
						}
					}
					if((!isCalcError) && (quoteVehicleBean.getQuoteBean() != null))
					{
						String simuDeposit = (String) input.get("deposit");
						simulationService.invoke(appId, quoteVehicleBean.getQuoteBean(), simuDeposit);
					}
				}
			}

		}


		return null;
	}

}
