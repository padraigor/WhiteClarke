package com.wcna.calms.jpos.services.quote;

public class UpgradeQuoteSaveRequestWrapper implements IUpgradeQuoteSaveRequestWrapper {

	private IJPOSQuickQuoteInputForm quoteForm;
	private IJPOSCalcResultsBean calcResultsBean;
	
	public IJPOSQuickQuoteInputForm getQuoteForm() {
		return quoteForm;
	}
	public void setQuoteForm(IJPOSQuickQuoteInputForm quoteForm) {
		this.quoteForm = quoteForm;
	}
	public IJPOSCalcResultsBean getCalcResultsBean() {
		return calcResultsBean;
	}
	public void setCalcResultsBean(IJPOSCalcResultsBean calcResultsBean) {
		this.calcResultsBean = calcResultsBean;
	}
	
}
