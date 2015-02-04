package com.wcna.calms.jpos.services.cra;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


public class CraProposalLoadParametersTest {

	private CraProposalLoadParameters parameters;
	private HashMap<String, Object> craReportParameterMap;

	@Before
	public void setup() {
		HashMap<String, Object> parameterMap = new HashMap<String, Object>();
		craReportParameterMap = new HashMap<String, Object>();
		parameterMap.put(CraProposalLoadParameters.CRA_REPORT_PARAMETER, craReportParameterMap);

		parameters = new CraProposalLoadParameters(parameterMap);
	}

	@Test
	public void getApplicationIdOfZeroWhenNoProposalNumberForApplicant() {
		assertThat(parameters.getProposalNumber(), equalTo(0l));
	}

	@Test
	public void getApplicationIdWhenProposalNumberForApplicant() {
		craReportParameterMap.put(CraProposalLoadParameters.PROPOSAL_NUMBER_PARAMETER, "2233");

		assertThat(parameters.getProposalNumber(), equalTo(2233l));
	}

	@Test
	public void getRefIdWhenProposalNumberForApplicant() {
		craReportParameterMap.put(CraProposalLoadParameters.REPORT_ID_PARAMETER, "null-234");

		assertThat(parameters.getRefId(), equalTo(234l));
	}

	@Test
	public void getReportTypeDefaultWhenNotSet() {
		assertThat(parameters.getReportType(), equalTo(3l));
	}

	@Test
	public void getReportTypeWhenNotSet() {
		craReportParameterMap.put(CraProposalLoadParameters.REPORT_TYPE_PARAMETER, "5");
		assertThat(parameters.getReportType(), equalTo(5l));
	}

	@Test
	public void getReportIdFoAdHocSearch() {
		craReportParameterMap.put(CraProposalLoadParameters.REPORT_ID_PARAMETER, "5522-null");

		assertThat(parameters.getReportId(), equalTo(5522l));
	}

	@Test
	public void isNotAdHocReportByDefault() throws Exception {
		assertThat(parameters.isAdHocReport(), equalTo(false));
	}

	@Test
	public void getIsAdHocReport() throws Exception {
		craReportParameterMap.put(CraProposalLoadParameters.IS_ADHOC_PARAMETER, true);
		assertThat(parameters.isAdHocReport(), equalTo(true));
	}


	@Test
	public void getIsValidApplicantReportRequestWithProposalNumberAndRefId() throws Exception {
		assertThat(parameters.isValidApplicantReportRequest(), equalTo(false));

		craReportParameterMap.put(CraProposalLoadParameters.PROPOSAL_NUMBER_PARAMETER, "2233");
		craReportParameterMap.put(CraProposalLoadParameters.REPORT_ID_PARAMETER, "null-234");

		assertThat(parameters.isValidApplicantReportRequest(), equalTo(true));
	}

	@Test
	public void getIsValidAdhocReportRequestWithReportId() throws Exception {
		assertThat(parameters.isValidApplicantReportRequest(), equalTo(false));

		craReportParameterMap.put(CraProposalLoadParameters.IS_ADHOC_PARAMETER, true);
		craReportParameterMap.put(CraProposalLoadParameters.REPORT_ID_PARAMETER, "234567-12");

		assertThat(parameters.isValidAdhocReportRequest(), equalTo(true));
	}
}
