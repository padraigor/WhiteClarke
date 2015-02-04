package com.wcna.calms.jpos.services.quote;

import java.io.Serializable;
import java.util.List;

import com.wcg.calms.service.quote.QuickQuoteAssetVO;
import com.wcna.calms.service.asset.ICatalogNodeData;
import com.wcna.calms.service.common.IConstants;

public class JPOSQuickQuoteAssetVO extends QuickQuoteAssetVO implements IJPOSQuickQuoteAssetInputForm, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2233505199146592841L;

	public JPOSQuickQuoteAssetVO() {
		this.setNewUsed(IJPOSQuickQuoteConstants.VEHICLE_NEW);
		this.setAssetType(ICatalogNodeData.CATALOG_NODE_TYPE_CARS);
		this.setTaxCode(IJPOSQuickQuoteConstants.UDT_VAT_RATE_STANDARD);
		this.setTaxIncludingFlag(IConstants.FLAG_YES);
		this.setExtraTaxCode(this.getTaxCode());
		this.setTaxableDFOptionsVatRate(this.getTaxCode());
		this.setNonTaxableDFOptionsVatRate(IJPOSQuickQuoteConstants.UDT_VAT_RATE_EXEMPT);
		this.setRoadFundLicenseVatRate(IJPOSQuickQuoteConstants.UDT_VAT_RATE_EXEMPT);
		this.setFirstRegistrationVatRate(IJPOSQuickQuoteConstants.UDT_VAT_RATE_EXEMPT);
		this.setKilometrage("0");
		this.setAssetCategory(IJPOSQuickQuoteConstants.UDT_VEHICLE_USAGE_PRIVATE);
	}



	private String registrationDate;
	private String taxableDFOptionsNet;
	private String taxableDFOptionsVatRate;
	private String taxableDFOptionsVatAmt;
	private String taxableDFOptionsGross;

	private String nonTaxableDFOptionsNet;
	private String nonTaxableDFOptionsVatRate;
	private String nonTaxableDFOptionsVatAmt;
	private String nonTaxableDFOptionsGross;

	private String roadFundLicenseNet;
	private String roadFundLicenseVatRate;
	private String roadFundLicenseVatAmt;
	private String roadFundLicenseGross;

	private String firstRegistrationNet;
	private String firstRegistrationVatRate;
	private String firstRegistrationVatAmt;
	private String firstRegistrationGross;

	private String totalNet;
	private String totalVatAmt;
	private String totalGross;

	private String registrationNumber;
	private String kilometrage;
	private String vin;

	private String assetCategory;
	private String taxPointDate;
	private String taxRateValue;

	private String discountAmtIsPct = "";
	private String discountAmt = "";
	private String discountPct = "";

	private String discountNet;
	private String discountVatRate;
	private String discountVatAmt;
	private String discountGross;

	private String assetUsageCode;
	private String approvedUsedAssetCode;
	private String enviroImpactRatingCode;
	private String enviroImpactRatingAmount;
	private String taxHorsePowerRating;

	private String hpiCallResult;
	private String totalRvUpliftPercentage;

    private String manufactureDate;
	private String mortgageRegDate;

	private String lct;
	private String lctSummary;
	private String gstSummary;

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getTaxableDFOptionsNet() {
		return taxableDFOptionsNet;
	}

	public void setTaxableDFOptionsNet(String taxableDFOptionsNet) {
		this.taxableDFOptionsNet = taxableDFOptionsNet;
	}

	public String getTaxableDFOptionsVatRate() {
		return taxableDFOptionsVatRate;
	}

	public void setTaxableDFOptionsVatRate(String taxableDFOptionsVatRate) {
		this.taxableDFOptionsVatRate = taxableDFOptionsVatRate;
	}

	public String getTaxableDFOptionsVatAmt() {
		return taxableDFOptionsVatAmt;
	}

	public void setTaxableDFOptionsVatAmt(String taxableDFOptionsVatAmt) {
		this.taxableDFOptionsVatAmt = taxableDFOptionsVatAmt;
	}

	public String getTaxableDFOptionsGross() {
		return taxableDFOptionsGross;
	}

	public void setTaxableDFOptionsGross(String taxableDFOptionsGross) {
		this.taxableDFOptionsGross = taxableDFOptionsGross;
	}

	public String getNonTaxableDFOptionsNet() {
		return nonTaxableDFOptionsNet;
	}

	public void setNonTaxableDFOptionsNet(String nonTaxableDFOptionsNet) {
		this.nonTaxableDFOptionsNet = nonTaxableDFOptionsNet;
	}

	public String getNonTaxableDFOptionsVatRate() {
		return nonTaxableDFOptionsVatRate;
	}

	public void setNonTaxableDFOptionsVatRate(String nonTaxableDFOptionsVatRate) {
		this.nonTaxableDFOptionsVatRate = nonTaxableDFOptionsVatRate;
	}

	public String getNonTaxableDFOptionsVatAmt() {
		return nonTaxableDFOptionsVatAmt;
	}

	public void setNonTaxableDFOptionsVatAmt(String nonTaxableDFOptionsVatAmt) {
		this.nonTaxableDFOptionsVatAmt = nonTaxableDFOptionsVatAmt;
	}

	public String getNonTaxableDFOptionsGross() {
		return nonTaxableDFOptionsGross;
	}

	public void setNonTaxableDFOptionsGross(String nonTaxableDFOptionsGross) {
		this.nonTaxableDFOptionsGross = nonTaxableDFOptionsGross;
	}

	public String getRoadFundLicenseNet() {
		return roadFundLicenseNet;
	}

	public void setRoadFundLicenseNet(String roadFundLicenseNet) {
		this.roadFundLicenseNet = roadFundLicenseNet;
	}

	public String getRoadFundLicenseVatRate() {
		return roadFundLicenseVatRate;
	}

	public void setRoadFundLicenseVatRate(String roadFundLicenseVatRate) {
		this.roadFundLicenseVatRate = roadFundLicenseVatRate;
	}

	public String getRoadFundLicenseVatAmt() {
		return roadFundLicenseVatAmt;
	}

	public void setRoadFundLicenseVatAmt(String roadFundLicenseVatAmt) {
		this.roadFundLicenseVatAmt = roadFundLicenseVatAmt;
	}

	public String getRoadFundLicenseGross() {
		return roadFundLicenseGross;
	}

	public void setRoadFundLicenseGross(String roadFundLicenseGross) {
		this.roadFundLicenseGross = roadFundLicenseGross;
	}

	public String getFirstRegistrationNet() {
		return firstRegistrationNet;
	}

	public void setFirstRegistrationNet(String firstRegistrationNet) {
		this.firstRegistrationNet = firstRegistrationNet;
	}

	public String getFirstRegistrationVatRate() {
		return firstRegistrationVatRate;
	}

	public void setFirstRegistrationVatRate(String firstRegistrationVatRate) {
		this.firstRegistrationVatRate = firstRegistrationVatRate;
	}

	public String getFirstRegistrationVatAmt() {
		return firstRegistrationVatAmt;
	}

	public void setFirstRegistrationVatAmt(String firstRegistrationVatAmt) {
		this.firstRegistrationVatAmt = firstRegistrationVatAmt;
	}

	public String getFirstRegistrationGross() {
		return firstRegistrationGross;
	}

	public void setFirstRegistrationGross(String firstRegistrationGross) {
		this.firstRegistrationGross = firstRegistrationGross;
	}

	public String getTotalNet() {
		return totalNet;
	}

	public void setTotalNet(String totalNet) {
		this.totalNet = totalNet;
	}

	public String getTotalVatAmt() {
		return totalVatAmt;
	}

	public void setTotalVatAmt(String totalVatAmt) {
		this.totalVatAmt = totalVatAmt;
	}

	public String getTotalGross() {
		return totalGross;
	}

	public void setTotalGross(String totalGross) {
		this.totalGross = totalGross;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getKilometrage() {
		return kilometrage;
	}

	public void setKilometrage(String kilometrage) {
		this.kilometrage = kilometrage;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getAssetCategory() {
		return assetCategory;
	}

	public void setAssetCategory(String assetCategory) {
		this.assetCategory = assetCategory;
	}

	public String getTaxPointDate() {
		return taxPointDate;
	}

	public void setTaxPointDate(String taxPointDate) {
		this.taxPointDate = taxPointDate;
	}

	public String getTaxRateValue() {
		return taxRateValue;
	}

	public void setTaxRateValue(String taxRateValue) {
		this.taxRateValue = taxRateValue;
	}

	public String getDiscountAmtIsPct() {
		return discountAmtIsPct;
	}

	public void setDiscountAmtIsPct(String discountAmtIsPct) {
		this.discountAmtIsPct = discountAmtIsPct;
	}

	public String getDiscountAmt() {
		return discountAmt;
	}

	public void setDiscountAmt(String discountAmt) {
		this.discountAmt = discountAmt;
	}

	public String getDiscountPct() {
		return discountPct;
	}

	public void setDiscountPct(String discountPct) {
		this.discountPct = discountPct;
	}

	public String getDiscountNet() {
		return discountNet;
	}

	public void setDiscountNet(String discountNet) {
		this.discountNet = discountNet;
	}

	public String getDiscountVatRate() {
		return discountVatRate;
	}

	public void setDiscountVatRate(String discountVatRate) {
		this.discountVatRate = discountVatRate;
	}

	public String getDiscountVatAmt() {
		return discountVatAmt;
	}

	public void setDiscountVatAmt(String discountVatAmt) {
		this.discountVatAmt = discountVatAmt;
	}

	public String getDiscountGross() {
		return discountGross;
	}

	public void setDiscountGross(String discountGross) {
		this.discountGross = discountGross;
	}


	public String getAssetUsageCode() {
		return assetUsageCode;
	}

	public void setAssetUsageCode(String assetUsageCode) {
		this.assetUsageCode = assetUsageCode;
	}

	public String getApprovedUsedAssetCode() {
		return approvedUsedAssetCode;
	}

	public void setApprovedUsedAssetCode(String approvedUsedAssetCode) {
		this.approvedUsedAssetCode = approvedUsedAssetCode;
	}

	public String getEnviroImpactRatingCode() {
		return enviroImpactRatingCode;
	}

	public void setEnviroImpactRatingCode(String enviroImpactRatingCode) {
		this.enviroImpactRatingCode = enviroImpactRatingCode;
	}

	public String getEnviroImpactRatingAmount() {
		return enviroImpactRatingAmount;
	}

	public void setEnviroImpactRatingAmount(String enviroImpactRatingAmount) {
		this.enviroImpactRatingAmount = enviroImpactRatingAmount;
	}

	public String getTaxHorsePowerRating() {
		return taxHorsePowerRating;
	}

	public void setTaxHorsePowerRating(String taxHorsePowerRating) {
		this.taxHorsePowerRating = taxHorsePowerRating;
	}


	public String getHpiCallResult() {
		return hpiCallResult;
	}

	public void setHpiCallResult(String hpiCallResult) {
		this.hpiCallResult = hpiCallResult;
	}




	public String getTotalRvUpliftPercentage() {
		return totalRvUpliftPercentage;
	}

	public void setTotalRvUpliftPercentage(String totalRvUpliftPercentage) {
		this.totalRvUpliftPercentage = totalRvUpliftPercentage;
	}




	public String getManufactureDate() {
		return manufactureDate;
	}

	public void setManufactureDate(String manufactureDate) {
		this.manufactureDate = manufactureDate;
	}

	public String getMortgageRegDate() {
		return mortgageRegDate;
	}

	public void setMortgageRegDate(String mortgageRegDate) {
		this.mortgageRegDate = mortgageRegDate;
	}

	public String getLct() {
		return lct;
	}

	public void setLct(String lct) {
		this.lct = lct;
	}

	public String getLctSummary() {
		return lctSummary;
	}

	public void setLctSummary(String lctSummary) {
		this.lctSummary = lctSummary;
	}

	public String getGstSummary() {
		return gstSummary;
	}

	public void setGstSummary(String gstSummary) {
		this.gstSummary = gstSummary;
	}

	public void reset() {
		// TODO Auto-generated method stub

	}


}
