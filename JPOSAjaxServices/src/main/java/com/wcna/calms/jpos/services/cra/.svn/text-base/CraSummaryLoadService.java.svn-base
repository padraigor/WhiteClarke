package com.wcna.calms.jpos.services.cra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.application.IAppContainer;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.customer.IApplicationConsumerDataVO;
import com.wcna.calms.service.customer.IApplicationCustomerDataVO;
import com.wcna.calms.service.customer.ICustomerService;
import com.wcna.calms.util.OptionLabelValue;
import com.wcna.calms.web.app.CoApplicantDetailsDisplay;
import com.wcna.crareport.service.ICraSearchService;

public class CraSummaryLoadService extends CalmsAjaxService {

	private final ICustomerService customerService;
	private final ICraSearchService craSearchService;
	private final Properties projectProperties;

	public CraSummaryLoadService(
			ICustomerService customerService,
			ICraSearchService craSearchService,
			Properties projectProperties) {
		this.customerService = customerService;
		this.craSearchService = craSearchService;
		this.projectProperties = projectProperties;
	}

	public Object invoke(Object arg0) {
		Map<String, Object> proposalMap = new HashMap<String, Object>();

		IAppContainer appContainer = getAppContainer();

		IApplicationCustomerDataVO customerData = null;
		if (appContainer != null && appContainer.getAppID() != 0){
			customerData = customerService.getCustomerData(appContainer.getAppID(), IConstants.PRIMARY_APPLICANT_REF_ID);
		}

		if (customerData != null) {

			List<OptionLabelValue> requiredApplicantSummaries = new ArrayList<OptionLabelValue>();

			requiredApplicantSummaries.add(mainApplicantSummary(customerData));
			if(presentReportsForAdditionalApplicants()) {
				requiredApplicantSummaries.addAll(getAdditionalApplicantSummaries());
			}

			proposalMap.put("applicantOptionValues", requiredApplicantSummaries);
			proposalMap.put("adhocSearchOptionValues", craSearchService.getAdhocSearches(appContainer.getAppID()));

		}
		Map<String, Object> summaryMap = new HashMap<String, Object>();
		summaryMap.put("summary", proposalMap);
		return summaryMap;
	}

	private boolean presentReportsForAdditionalApplicants() {
		return Boolean.parseBoolean(projectProperties.getProperty("cra.show.reports.for.additional.applicants", "false"));
	}

	private List<OptionLabelValue> getAdditionalApplicantSummaries() {
		List<OptionLabelValue> additionalApplicantSummaries = new ArrayList<OptionLabelValue>();
		List <CoApplicantDetailsDisplay> additionalApplicants = new ArrayList<CoApplicantDetailsDisplay>();
		additionalApplicants.addAll(customerService.getGuarantors());
		additionalApplicants.addAll(customerService.getCoApplicants());
		additionalApplicants.addAll(customerService.getDirectorTypeReferences());
		additionalApplicants.addAll(customerService.getEffectiveOwnerTypeReferences());
		additionalApplicants.addAll(customerService.getPartnerTypeReferences());

		for (Iterator <CoApplicantDetailsDisplay>it = additionalApplicants.iterator(); it.hasNext();){
			CoApplicantDetailsDisplay disp = it.next();
			OptionLabelValue applicantSummary = new OptionLabelValue();
			if (customerTypeHasCommercialAspect(disp.getCustomerType())){
				applicantSummary.setLabel(disp.getCommonName());
			}else{
				applicantSummary.setLabel(disp.getFirstName() + " " +disp.getLastName() );
			}
			applicantSummary.setValue(disp.getCraReportId() + "-" + disp.getRefid()); // reportId and ref id
			additionalApplicantSummaries.add(applicantSummary);
		}
		return additionalApplicantSummaries;
	}

	private OptionLabelValue mainApplicantSummary(
			IApplicationCustomerDataVO customerData) {
		OptionLabelValue mainApplicantSummary = new OptionLabelValue();

		if (customerTypeHasCommercialAspect(customerData.getCustomerTypeCode())){
			mainApplicantSummary.setLabel(customerData.getCommonName());
		}else{
			mainApplicantSummary.setLabel(((IApplicationConsumerDataVO)customerData).getFirstName() + " " +	((IApplicationConsumerDataVO)customerData).getLastName() );
		}
		mainApplicantSummary.setValue(customerData.getCraReportId() + "-" + IConstants.PRIMARY_APPLICANT_REF_ID); // reportId and ref id
		return mainApplicantSummary;
	}

	public static final boolean customerTypeHasCommercialAspect(String customerType) {
		return IConstants.PARTNERSHIP_CUSTOMER_TYPE.equals(customerType)
		|| IConstants.COMMERCIAL_CUSTOMER_TYPE.equals(customerType)
		|| IConstants.SOLE_TRADER_CUSTOMER_TYPE.equals(customerType);
	}
}
