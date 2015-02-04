package com.wcna.calms.jpos.services.quote;

public class StructuredPaymentScreenBean {

	private String paymentNr;
	private String date;
	private String amount;
	private String ratio;

	public String getPaymentNr() {
		return paymentNr;
	}
	public void setPaymentNr(String paymentNr) {
		this.paymentNr = paymentNr;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getRatio() {
		return ratio;
	}
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

}
