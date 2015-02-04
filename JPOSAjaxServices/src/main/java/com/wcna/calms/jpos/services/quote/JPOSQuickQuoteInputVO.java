package com.wcna.calms.jpos.services.quote;

import java.math.BigDecimal;

public class JPOSQuickQuoteInputVO {

	private String mileage;
	private String cost;

	// which field will be calculated
	private String rollbackTarget;

	private String lumpSum;
	private String lumpSumAsPct;
	private String deposit;
	private String depositAsPct;
	private String partExchange;
	private String settlement;
	private String term;
	private String frequency;
	private String rental;
	private String numberInAdvance;
	private String finalRental;
	private String finalRentalAsPct;
	private String buyback;
	private String buybackAsPct;
	private String payment;
	private String balloon;
	private String balloonAsPct;
	private String gfv;
	private String gfvAsPct;
	private String taeg;
	private String taegFlag;
	private String apr;
	private String aprFlag;
	private String inceptionDate;
	private String firstPaymentDate;
	private String firstPaymentDay;
	private String plan;
	private String assetsUsageCode;
	private String buybackerType;
	private String includeIVA;
	private String initialPymtHoliday;
	private String isFinancedFees;
	private String numberOfVehicles;
	private String isStructured;
	private String planCodeType;

//	private String		warranty;
//	private String		gapRti;
//	private String		otherInsurance;
//	private String		otherNonInsurance;
//	private String      ppCode;

	public JPOSQuickQuoteInputVO() {
		String defaultVal = new BigDecimal("0").setScale(2).toString();
		this.mileage = defaultVal;
		this.cost = defaultVal;
		this.lumpSum = defaultVal;
		this.deposit = defaultVal;
		this.partExchange = defaultVal;
		this.settlement = defaultVal;
		this.rental = defaultVal;
		this.numberInAdvance = "0";
		this.finalRental = defaultVal;
		this.buyback = defaultVal;
		this.payment = defaultVal;
		this.balloon = defaultVal;
		this.gfv = defaultVal;
		this.apr = defaultVal;
		this.plan = defaultVal;
		this.assetsUsageCode = IJPOSQuickQuoteConstants.ASSET_USAGE_PRIVATE;
		this.firstPaymentDay = "";
		this.planCodeType = IJPOSQuickQuoteConstants.PLAN_CODE_TYPE_PLAN;
	}

	public JPOSQuickQuoteInputVO(String defaultVal) {
		this.mileage = defaultVal;
		this.cost = defaultVal;
		this.lumpSum = defaultVal;
		this.deposit = defaultVal;
		this.partExchange = defaultVal;
		this.settlement = defaultVal;
		this.rental = defaultVal;
		this.numberInAdvance = "0";
		this.finalRental = defaultVal;
		this.buyback = defaultVal;
		this.payment = defaultVal;
		this.balloon = defaultVal;
		this.gfv = defaultVal;
		this.apr = defaultVal;
		this.plan = defaultVal;
		this.assetsUsageCode = IJPOSQuickQuoteConstants.ASSET_USAGE_PRIVATE;
		this.firstPaymentDay = "";
		this.planCodeType = IJPOSQuickQuoteConstants.PLAN_CODE_TYPE_PLAN;
	}

	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getRollbackTarget() {
		return rollbackTarget;
	}
	public void setRollbackTarget(String rollbackTarget) {
		this.rollbackTarget = rollbackTarget;
	}
	public String getLumpSum() {
		return lumpSum;
	}
	public void setLumpSum(String lumpSum) {
		this.lumpSum = lumpSum;
	}
	public String getLumpSumAsPct() {
		return lumpSumAsPct;
	}
	public void setLumpSumAsPct(String lumpSumAsPct) {
		this.lumpSumAsPct = lumpSumAsPct;
	}
	public String getDeposit() {
		return deposit;
	}
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}
	public String getDepositAsPct() {
		return depositAsPct;
	}

	public void setDepositAsPct(String depositAsPct) {
		this.depositAsPct = depositAsPct;
	}

	public String getPartExchange() {
		return partExchange;
	}
	public void setPartExchange(String partExchange) {
		this.partExchange = partExchange;
	}
	public String getSettlement() {
		return settlement;
	}
	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getRental() {
		return rental;
	}
	public void setRental(String rental) {
		this.rental = rental;
	}
	public String getNumberInAdvance() {
		return numberInAdvance;
	}
	public void setNumberInAdvance(String numberInAdvance) {
		this.numberInAdvance = numberInAdvance;
	}
	public String getFinalRental() {
		return finalRental;
	}
	public void setFinalRental(String finalRental) {
		this.finalRental = finalRental;
	}
	public String getFinalRentalAsPct() {
		return finalRentalAsPct;
	}

	public void setFinalRentalAsPct(String finalRentalAsPct) {
		this.finalRentalAsPct = finalRentalAsPct;
	}

	public String getBuyback() {
		return buyback;
	}
	public void setBuyback(String buyback) {
		this.buyback = buyback;
	}
	public String getBuybackAsPct() {
		return buybackAsPct;
	}

	public void setBuybackAsPct(String buybackAsPct) {
		this.buybackAsPct = buybackAsPct;
	}

	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getBalloon() {
		return balloon;
	}
	public void setBalloon(String balloon) {
		this.balloon = balloon;
	}
	public String getBalloonAsPct() {
		return balloonAsPct;
	}

	public void setBalloonAsPct(String balloonAsPct) {
		this.balloonAsPct = balloonAsPct;
	}

	public String getGfv() {
		return gfv;
	}
	public void setGfv(String gfv) {
		this.gfv = gfv;
	}
	public String getGfvAsPct() {
		return gfvAsPct;
	}

	public void setGfvAsPct(String gfvAsPct) {
		this.gfvAsPct = gfvAsPct;
	}

	public String getTaeg() {
		return taeg;
	}
	public void setTaeg(String taeg) {
		this.taeg = taeg;
	}
	public String getTaegFlag() {
		return taegFlag;
	}
	public void setTaegFlag(String taegFlag) {
		this.taegFlag = taegFlag;
	}
	public String getApr() {
		return apr;
	}
	public void setApr(String apr) {
		this.apr = apr;
	}
	public String getAprFlag() {
		return aprFlag;
	}
	public void setAprFlag(String aprFlag) {
		this.aprFlag = aprFlag;
	}
	public String getInceptionDate() {
		return inceptionDate;
	}
	public void setInceptionDate(String inceptionDate) {
		this.inceptionDate = inceptionDate;
	}
	public String getFirstPaymentDate() {
		return firstPaymentDate;
	}
	public void setFirstPaymentDate(String firstPaymentDate) {
		this.firstPaymentDate = firstPaymentDate;
	}
	public String getFirstPaymentDay() {
		return firstPaymentDay;
	}

	public void setFirstPaymentDay(String firstPaymentDay) {
		this.firstPaymentDay = firstPaymentDay;
	}

	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
//	public String getWarranty() {
//		return warranty;
//	}
//	public void setWarranty(String warranty) {
//		this.warranty = warranty;
//	}
//	public String getGapRti() {
//		return gapRti;
//	}
//	public void setGapRti(String gapRti) {
//		this.gapRti = gapRti;
//	}
//	public String getOtherInsurance() {
//		return otherInsurance;
//	}
//	public void setOtherInsurance(String otherInsurance) {
//		this.otherInsurance = otherInsurance;
//	}
//	public String getOtherNonInsurance() {
//		return otherNonInsurance;
//	}
//	public void setOtherNonInsurance(String otherNonInsurance) {
//		this.otherNonInsurance = otherNonInsurance;
//	}
//	public String getPpCode() {
//		return ppCode;
//	}
//	public void setPpCode(String ppCode) {
//		this.ppCode = ppCode;
//	}

	public String getAssetsUsageCode() {
		return assetsUsageCode;
	}

	public void setAssetsUsageCode(String assetsUsageCode) {
		this.assetsUsageCode = assetsUsageCode;
	}

	public String getBuybackerType() {
		return buybackerType;
	}

	public void setBuybackerType(String buybackerType) {
		this.buybackerType = buybackerType;
	}

	public String getIncludeIVA() {
		return includeIVA;
	}

	public void setIncludeIVA(String includeIVA) {
		this.includeIVA = includeIVA;
	}

	public String getInitialPymtHoliday() {
		return initialPymtHoliday;
	}

	public void setInitialPymtHoliday(String initialPymtHoliday) {
		this.initialPymtHoliday = initialPymtHoliday;
	}

	public String getIsFinancedFees() {
		return isFinancedFees;
	}

	public void setIsFinancedFees(String isFinancedFees) {
		this.isFinancedFees = isFinancedFees;
	}

	public String getNumberOfVehicles() {
		return numberOfVehicles;
	}

	public void setNumberOfVehicles(String numberOfVehicles) {
		this.numberOfVehicles = numberOfVehicles;
	}

	public String getIsStructured() {
		return isStructured;
	}

	public void setIsStructured(String isStructured) {
		this.isStructured = isStructured;
	}

	public String getPlanCodeType() {
		return planCodeType;
	}

	public void setPlanCodeType(String planCodeType) {
		this.planCodeType = planCodeType;
	}
}
