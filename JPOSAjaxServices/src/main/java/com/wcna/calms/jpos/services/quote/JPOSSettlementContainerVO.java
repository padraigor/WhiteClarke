package com.wcna.calms.jpos.services.quote;

import java.util.Date;

import com.wcna.calms.jpos.services.settlement.IJPOSSettlementContainer;

public class JPOSSettlementContainerVO implements IJPOSSettlementContainer {

	private long id;
	private double settlementAmount;
	private java.util.Date settlementExpiryDate;
	private java.lang.String cmsAgreementNumber;
	private long cmsScheduleNumber;
	private java.lang.String cmsInvoicingCustBpid;
	private java.lang.String cmsInvoicingCustBillAddr;
	private java.lang.String cmsQuotationNumber;
	private java.lang.String cmsTrackingKey;
	private java.lang.String customerName;
	private java.util.Date settlementRequestDate;
	private String sourceSystem;
	private double rebate;
	private String vehicleDescription;
	private boolean isInsertNote = false;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getSettlementAmount() {
		return settlementAmount;
	}
	public void setSettlementAmount(double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	public java.util.Date getSettlementExpiryDate() {
		return settlementExpiryDate;
	}
	public void setSettlementExpiryDate(java.util.Date settlementExpiryDate) {
		this.settlementExpiryDate = settlementExpiryDate;
	}
	public java.lang.String getCmsAgreementNumber() {
		return cmsAgreementNumber;
	}
	public void setCmsAgreementNumber(java.lang.String cmsAgreementNumber) {
		this.cmsAgreementNumber = cmsAgreementNumber;
	}
	public long getCmsScheduleNumber() {
		return cmsScheduleNumber;
	}
	public void setCmsScheduleNumber(long cmsScheduleNumber) {
		this.cmsScheduleNumber = cmsScheduleNumber;
	}
	public java.lang.String getCmsInvoicingCustBpid() {
		return cmsInvoicingCustBpid;
	}
	public void setCmsInvoicingCustBpid(java.lang.String cmsInvoicingCustBpid) {
		this.cmsInvoicingCustBpid = cmsInvoicingCustBpid;
	}
	public java.lang.String getCmsInvoicingCustBillAddr() {
		return cmsInvoicingCustBillAddr;
	}
	public void setCmsInvoicingCustBillAddr(
			java.lang.String cmsInvoicingCustBillAddr) {
		this.cmsInvoicingCustBillAddr = cmsInvoicingCustBillAddr;
	}
	public java.lang.String getCmsQuotationNumber() {
		return cmsQuotationNumber;
	}
	public void setCmsQuotationNumber(java.lang.String cmsQuotationNumber) {
		this.cmsQuotationNumber = cmsQuotationNumber;
	}
	public java.lang.String getCmsTrackingKey() {
		return cmsTrackingKey;
	}
	public void setCmsTrackingKey(java.lang.String cmsTrackingKey) {
		this.cmsTrackingKey = cmsTrackingKey;
	}
	public java.lang.String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(java.lang.String customerName) {
		this.customerName = customerName;
	}
	public java.util.Date getSettlementRequestDate() {
		return settlementRequestDate;
	}
	public void setSettlementRequestDate(java.util.Date settlementRequestDate) {
		this.settlementRequestDate = settlementRequestDate;
	}
	public String getSourceSystem() {
		return sourceSystem;
	}
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}
	public double getRebate() {
		return rebate;
	}
	public void setRebate(double rebate) {
		this.rebate = rebate;
	}
	public String getVehicleDescription() {
		return vehicleDescription;
	}
	public void setVehicleDescription(String vehicleDescription) {
		this.vehicleDescription = vehicleDescription;
	}
	public boolean isInsertNote() {
		return isInsertNote;
	}
	public void setInsertNote(boolean isInsertNote) {
		this.isInsertNote = isInsertNote;
	}

}
