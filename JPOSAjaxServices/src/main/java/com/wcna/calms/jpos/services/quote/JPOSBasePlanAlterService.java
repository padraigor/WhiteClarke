package com.wcna.calms.jpos.services.quote;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.calms.util.OptionLabelValue;
import com.wcna.calms.web.util.FormatFactory;

public abstract class JPOSBasePlanAlterService extends CalmsAjaxService {

	private final IFormatService formatService;
	private final IJPOSQuickQuoteService jposQuickQuoteService;

	public JPOSBasePlanAlterService(IFormatService formatService, IJPOSQuickQuoteService jposQuickQuoteService) {
		this.formatService = formatService;
		this.jposQuickQuoteService = jposQuickQuoteService;
	}

	public JPOSQuickQuoteInputVO getDefaultPlanData(IFinanceProduct in, List<OptionLabelValue> frequencies, String totalNet, String totalGross, IRentACarBean rentACarBean, int rentACarSize, int quoteIdx) {
		JPOSQuickQuoteInputVO out = new JPOSQuickQuoteInputVO(formatService.formatDouble(0d, getUserContainer().getLocale()));

//		out.setMileage((in.getDefaultAnnualMileage() / 1000) + "");
		if (in.isDistanceOption()) {
			if (rentACarBean != null) {
				out.setMileage(formatService.formatInteger(rentACarBean.getAnnualDistance() / 1000, getUserContainer().getLocale()));
			} else {
				out.setMileage(formatService.formatInteger(in.getDefaultAnnualMileage() / 1000, getUserContainer().getLocale()));
			}
		}
		if (rentACarBean != null) {
			out.setTerm(rentACarBean.getTerm() + "");
		} else {
//			out.setTerm(in.getDefaultPeriod() + "");
			out.setTerm(jposQuickQuoteService.getDefaultTerm(in) + "");
		}
//		out.setPlan(in.getDefaultPlanCode() + "");
		if (rentACarBean != null) {
//			out.setPlan(formatService.formatDouble(rentACarBean.getTan(), this.getUserContainer().getLocale(), FormatFactory.RAT_CODE));
			out.setPlan(jposQuickQuoteService.getFormattedPlanCode(in, rentACarBean.getTan(), getUserContainer().getLocale()));
		} else {
//			out.setPlan(formatService.formatDouble(in.getDefaultPlanCode(), this.getUserContainer().getLocale(), FormatFactory.RAT_CODE));
			out.setPlan(jposQuickQuoteService.getFormattedPlanCode(in, in.getDefaultPlanCode(), getUserContainer().getLocale()));
		}
		// set the default calc target to 'Payment' or 'Rental'
		int financeType = in.getFinanceType().getIntCode();
//		if (financeType == IJPOSQuickQuoteConstants.FINANCE_TYPE_FINANCE_LEASE) {
//			out.setRollbackTarget(IJPOSQuickQuoteConstants.CALC_RENTAL + "");
//		} else {
//			out.setRollbackTarget(IJPOSQuickQuoteConstants.CALC_TERM + "");
//		}
		if (rentACarBean != null) {
			out.setRollbackTarget("14"); // they can only solve for the 'plan' field
		} else {
			out.setRollbackTarget(this.jposQuickQuoteService.getDefaultRollbackTarget(financeType, in.getStructureType()));
		}

		// set the default frequency to the first available option
		if (frequencies != null && !frequencies.isEmpty()) {
			OptionLabelValue olv = frequencies.get(0);
			if (olv != null) {
				out.setFrequency(olv.getValue());
			}
			// respect the frequency setting from the rent-a-car upload
			if (rentACarBean != null) {
				String frequencyCode = rentACarBean.getFrequencyCode();
				for (OptionLabelValue frequency : frequencies) {
					if (!StringUtils.isBlank(frequency.getValue()) && frequency.getValue().equals(frequencyCode)) {
						out.setFrequency(frequencyCode);
						break;
					}
				}
			}
		}

		if (rentACarBean != null) {
			String c_type = jposQuickQuoteService.determineQuoteCostField(in);
			if (IJPOSQuickQuoteConstants.QUOTE_ASSET_GROSS.equals(c_type)) {
//				out.setCost(totalGross);
//				if (rentACarBean != null) {
				out.setCost(formatService.formatDouble(rentACarBean.getAmtWithTax(), getUserContainer().getLocale()));
//				}
			} else if (IJPOSQuickQuoteConstants.QUOTE_ASSET_NET.equals(c_type)) {
//				out.setCost(totalNet);
//				if (rentACarBean != null) {
				out.setCost(formatService.formatDouble(rentACarBean.getVehicleCost(), getUserContainer().getLocale()));
//				}
			}
		} else {
			double costd = jposQuickQuoteService.getCostValue(in, quoteIdx, getUserContainer().getLocale());
			out.setCost(formatService.formatDouble(costd, getUserContainer().getLocale()));
		}

//		String deposit = formatService.formatDouble((in.getDefaultDepositPercentage() / 100) * formatService.parseDouble(out.getCost(), getUserContainer().getLocale(), false, 0d), getUserContainer().getLocale());
		String deposit;
		if (rentACarBean != null) {
			deposit = formatService.formatDouble(rentACarBean.getDownpayment(), getUserContainer().getLocale());
		} else {
			deposit = formatService.formatDouble((in.getDefaultDepositPercentage() / 100) * formatService.parseDouble(out.getCost(), getUserContainer().getLocale(), false, 0d), getUserContainer().getLocale());
		}
		if (jposQuickQuoteService.showDeposit(financeType)) {
			out.setDeposit(deposit);
		} else if (jposQuickQuoteService.showLumpSum(financeType)) {
			out.setLumpSum(deposit);
		}

//		if (in.getBuybackerType() > 0) {
		if (jposQuickQuoteService.isShowBuyback(in)) {
			out.setBuybackerType(in.getBuybackerType() + "");
			if (rentACarBean != null) {
				out.setBuybackerType(rentACarBean.getBuybackerTypeCode());
				out.setBuyback(formatService.formatDouble(rentACarBean.getBuyback(), getUserContainer().getLocale()));
			}

		}

		if (jposQuickQuoteService.isShowingInceptionAndFirstPaymentDay(financeType)) {
//			out.setFirstPaymentDay(jposQuickQuoteService.getDefaultFirstPaymentDay());
			String inceptionDate = null;
			if (rentACarBean != null && !StringUtils.isBlank(rentACarBean.getStartDate())) {
				inceptionDate = rentACarBean.getStartDate();
			} else {
				Date now = new Date();
				inceptionDate = formatService.formatDate(now, getUserContainer().getLocale());
			}
			out.setInceptionDate(inceptionDate);
		}

		if (jposQuickQuoteService.isShowInitialPymtHoliday(in)) {
			out.setInitialPymtHoliday(formatService.formatInteger(in.getInitialPymtHoliday().intValue(), getUserContainer().getLocale()));
		}

		// populate some other fields that are read-only in a rent-a-car scenario
		if (rentACarBean != null) {
			if (jposQuickQuoteService.showPayment(in)) {
				out.setPayment(formatService.formatDouble(rentACarBean.getRental(), getUserContainer().getLocale()));
			} else if (jposQuickQuoteService.showRental(in)) {
				out.setRental(formatService.formatDouble(rentACarBean.getRental(), getUserContainer().getLocale()));
			}
			if (in.isAdvancePayments()) {
				out.setNumberInAdvance(formatService.formatInteger(rentACarBean.getNumberInAdvance(), getUserContainer().getLocale()));
			}
			if (in.isFinalPaymentAllowed()) {
				String finalPymt = formatService.formatDouble(rentACarBean.getFinalPayment(), getUserContainer().getLocale());
				if (jposQuickQuoteService.showGFV(in)) {
					out.setGfv(finalPymt);
				} else if (jposQuickQuoteService.showBalloon(in)) {
					out.setBalloon(finalPymt);
				} else if (jposQuickQuoteService.showFinalRental(in)) {
					out.setFinalRental(finalPymt);
				}
			}
			out.setNumberOfVehicles(rentACarSize + "");
		}

		out.setIsStructured(jposQuickQuoteService.isStructuredPlan(in) ? IConstants.FLAG_YES : "");

		return out;
	}

}
