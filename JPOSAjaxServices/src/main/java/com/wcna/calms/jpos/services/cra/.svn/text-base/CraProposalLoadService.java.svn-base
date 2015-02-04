package com.wcna.calms.jpos.services.cra;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.service.submitdetails.SubmitPayoutService;
import com.wcna.calms.service.application.IAppContainer;
import com.wcna.calms.service.application.IProposalButtonsPermission;
import com.wcna.calms.service.common.IUserContainer;
import com.wcna.util.SystemException;

public class CraProposalLoadService extends CalmsAjaxService {

	private final static Logger logger = Logger.getLogger(CraProposalLoadService.class);

	private final ICraService craService;
	private final IProposalButtonsPermission proposalButtonsPermission;

	public CraProposalLoadService(ICraService craService, IProposalButtonsPermission proposalButtonsPermission)
	{
		this.craService = craService;
		this.proposalButtonsPermission = proposalButtonsPermission;
	}

	@SuppressWarnings("unchecked")
	public Object invoke(Object object) {

		// should have two values, one is the report id, then other is the type of template/CRA to use, third value is the cust ref id
		Map<String, Object> dataMap = (Map<String, Object>) object;
		CraProposalLoadParameters parameters = new CraProposalLoadParameters(dataMap);

		IAppContainer appContainer = getAppContainer();
		String originalAppStatus = appContainer.getApplicationStatus();



		if(proposalButtonsPermission.hasCraButtonPermission()){

			logger.info("No permission to do this action. Current Application status - "+originalAppStatus +" Action done - CRA");
			return new SystemException("No permission to do this action. ");
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (parameters.isValidApplicantReportRequest()){
			String rep = craService.retrieveReportForApplicant(parameters.getProposalNumber(), parameters.getReportType(), parameters.getRefId());
			returnMap.put("craReport", rep);
		} else if(parameters.isValidAdhocReportRequest()) {
			String rep = craService.retrieveReportForAdhocSearch(parameters.getProposalNumber(), parameters.getReportId());
			returnMap.put("craReport", rep);
		}

		return returnMap;
	}


}
