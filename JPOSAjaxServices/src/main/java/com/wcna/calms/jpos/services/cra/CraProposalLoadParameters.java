package com.wcna.calms.jpos.services.cra;

import java.util.Map;

import com.wcna.lang.StringUtil;

public class CraProposalLoadParameters {

	private static final int REFID_PART = 1;
	private static final int REPORTID_PART = 0;
	private Map<String, Object> craReportParameters;
	public static final String CRA_REPORT_PARAMETER = "craReport";
	public static final String PROPOSAL_NUMBER_PARAMETER = "proposalNumber";
	public static final String REPORT_ID_PARAMETER = "reportId";
	public static final String REPORT_TYPE_PARAMETER = "reportType";
	private static final Long DEFAULT_REPORT_TYPE = 3l;
	public static final String IS_ADHOC_PARAMETER = "isAdhoc";

	@SuppressWarnings("unchecked")
	public CraProposalLoadParameters(Map<String, Object> parameterMap) {
		craReportParameters = (Map<String, Object>) parameterMap.get(CRA_REPORT_PARAMETER);
	}

	public Long getProposalNumber() {
		if(craReportParameters.containsKey("proposalNumber")) {
			return Long.parseLong((String) craReportParameters.get(PROPOSAL_NUMBER_PARAMETER));
		}
		return 0l;
	}

	public Long getRefId() {
		return getPartOfReportIdRefId(REFID_PART);
	}


	public Long getReportType() {
		if(craReportParameters.containsKey(REPORT_TYPE_PARAMETER)) {
			return Long.parseLong((String) craReportParameters.get(REPORT_TYPE_PARAMETER));
		}
		return DEFAULT_REPORT_TYPE;
	}

	public Long getReportId() {
		return getPartOfReportIdRefId(REPORTID_PART);
	}

	public Boolean isAdHocReport() {
		if(craReportParameters.containsKey(IS_ADHOC_PARAMETER)) {
			return (Boolean) craReportParameters.get(IS_ADHOC_PARAMETER);
		}
		return false;
	}

	public Boolean isValidApplicantReportRequest() {
		return !isAdHocReport() && getProposalNumber() != 0 && getRefId() != null;
	}

	public Boolean isValidAdhocReportRequest() {
		return isAdHocReport() && getReportId() != null;
	}

	//this abominable parameter contains two bits of information separated by a hyphen
	private Long getPartOfReportIdRefId(int theBit) {
		Long refId = null;
		String reportIdRefId = (String) craReportParameters.get(REPORT_ID_PARAMETER);
		if (!StringUtil.isBlank(reportIdRefId)){
			String [] bits = reportIdRefId.split("-");
			refId = Long.parseLong(bits[theBit]);
		}
		return refId;
	}
}
