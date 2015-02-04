package com.wcna.calms.jpos.services.quote;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.customer.IApplicationCustomerDataVO;
import com.wcna.calms.service.customer.ICustomerService;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.calms.util.OptionLabelValue;
import com.wcna.lang.NumberUtil;
import com.wcna.lang.StringUtil;
import com.wcna.util.SystemException;

public class JPOSQuickQuotePlanChangeService extends JPOSBasePlanAlterService {

	private final IJPOSQuickQuoteService jposQuickQuoteService;
	private final IFormatService formatService;
	private final ICustomerService customerService;
	private final JPOSQuoteUtil jposQuoteUtil;

	public JPOSQuickQuotePlanChangeService(IJPOSQuickQuoteService jposQuickQuoteService, IFormatService formatService, ICustomerService customerService,
			JPOSQuoteUtil jposQuoteUtil) {
		super(formatService, jposQuickQuoteService);
		this.jposQuickQuoteService = jposQuickQuoteService;
		this.formatService = formatService;
		this.customerService = customerService;
		this.jposQuoteUtil = jposQuoteUtil;
	}

	public Object invoke(Object arg0) {
		if (arg0 != null && arg0 instanceof Map) {
			Map map = (Map) arg0;
			int quoteIdx = this.jposQuoteUtil.getQuoteIdx(map);
			IRentACarBean rentACarBean = this.jposQuickQuoteService.getCommonRentACarBean(quoteIdx);
			IJPOSQuickQuoteAssetInputForm assetForm = this.jposQuickQuoteService.getAssetInputForm(quoteIdx);

			String planId = (String) map.get("planId");

//			String custTypeCode = null;
////			if (IConstants.FLAG_YES.equals(isProposal)) {
//			if (IConstants.PROPOSAL_APPLICATION_TYPE.equals(jposQuickQuoteService.getApplicationType())) {
//				IApplicationCustomerDataVO custData = customerService.getCustomerData(getAppContainer().getAppID(), IConstants.PRIMARY_APPLICANT_REF_ID);
//				custTypeCode = custData.getCustomerTypeCode();
//			} else {
//				custTypeCode = (String) map.get("custTypeCode");
//			}
			String custTypeCode = this.jposQuickQuoteService.getCustomerData().getCustomerType();

			Map<String, Object> mapOut = new HashMap<String, Object>();
//			IPlan plan = jposQuickQuoteService.getPlan(planId);
//			int dealerId = 7046677;
//			int dealerId = 7043440;

//			int dagId = formatService.parseInteger(this.getUserContainer().getCurrentDagID(), this.getUserContainer().getLocale(), false, 0);
//			int dealerId = jposQuickQuoteService.getDealerIdFromDealerDagId(dagId);
//			int jtrId = jposQuickQuoteService.getJtrIdFromDealerDagId(dagId);
//			int brokerBrandId = formatService.parseInteger(this.getUserContainer().getCurrentBrandCode(), this.getUserContainer().getLocale(), false, 0);

			long modelVariantId = 0;
			if (rentACarBean != null) {
				modelVariantId = rentACarBean.getVariantId();
			} else if (!"on".equals(assetForm.getFreeFormatFlag())) {
				modelVariantId = formatService.parseLong(assetForm.getModelVariantId(), getUserContainer().getLocale(), false, 0L);
			}

			IFinanceProduct financeProduct = jposQuickQuoteService.getFinanceProduct(planId, this.getUserContainer().getLocale(), custTypeCode, modelVariantId);
			this.jposQuickQuoteService.setFinanceProductToContainer(quoteIdx, financeProduct);
			if (financeProduct == null) {
//				String appId = (String) ((Map) arg0).get("appId");
				String appId = this.getAppContainer() == null ? "" : this.getAppContainer().getAppID() <= 0 ? "" : this.getAppContainer().getAppID() + "";
				if (!StringUtil.isEmpty(appId)) {
					Locale locale = this.getUserContainer().getLocale();
					IPlan plan = jposQuickQuoteService.getPlan(planId);
//					boolean bLease = plan.getFinanceType().getIntCode() == IJPOSQuickQuoteConstants.FINANCE_TYPE_FINANCE_LEASE;
					boolean bLease = this.jposQuickQuoteService.isFinanceTypeLease(plan.getFinanceType().getIntCode());
					Long appId_long = Long.valueOf(appId);
//					int cpiProductGroupId = jposQuickQuoteService.getCpiProductGroupIdFromDealerDagId(locale);

					mapOut.put("productExpired", IConstants.FLAG_YES);
					IJPOSQuoteDataVO quoteVo = jposQuickQuoteService.loadQuote(appId_long, locale);
					IQuoteVehicleBean qvb = jposQuickQuoteService.constructQuoteVehicleBean(quoteVo, jposQuickQuoteService.loadAsset(appId_long, locale, false), bLease, locale);
					IJPOSProfileDisplay display = null;
//					if (bLease) {
//						display = jposQuickQuoteService.getProfileLeaseDisplay(qvb, custTypeCode, plan, locale, quoteVo.isShowTaxIncludeFlag());
//					} else {
//						display = jposQuickQuoteService.getProfileDisplay(qvb, custTypeCode, plan, locale);
//					}
					display = jposQuickQuoteService.genProfileDisplay(qvb, custTypeCode, plan, locale, quoteVo.isShowTaxIncludeFlag(), false);

					mapOut.put("profileDisplay", display);
					mapOut.put("isLease", bLease ? IConstants.FLAG_YES : "");
					return mapOut;
				}
			}

			mapOut.put("plan", financeProduct);
			List<OptionLabelValue> frequencyList = jposQuickQuoteService.getFrequencyList(financeProduct);
			mapOut.put("frequencyList", frequencyList);

			int financeType = financeProduct.getFinanceType().getIntCode();

			mapOut.put("showDeposit", jposQuickQuoteService.showDeposit(financeType));
			mapOut.put("showLumpSum", jposQuickQuoteService.showLumpSum(financeType));
			mapOut.put("showPayment", jposQuickQuoteService.showPayment(financeProduct));
			mapOut.put("showRental", jposQuickQuoteService.showRental(financeProduct));
			mapOut.put("showFinalRental", jposQuickQuoteService.showFinalRental(financeProduct));
			mapOut.put("showGFV", jposQuickQuoteService.showGFV(financeProduct));
			mapOut.put("showBalloon", jposQuickQuoteService.showBalloon(financeProduct));
//			mapOut.put("showBuyback", financeProduct.getBuybackerType() > 0);
			mapOut.put("showBuyback", this.jposQuickQuoteService.isShowBuyback(financeProduct));
			mapOut.put("showInceptionAndFirstPaymentDay", jposQuickQuoteService.isShowingInceptionAndFirstPaymentDay(financeType));
			mapOut.put("showInitialPymtHoliday", jposQuickQuoteService.isShowInitialPymtHoliday(financeProduct));
			mapOut.put("showDisplayInclIVA", jposQuickQuoteService.isShowDisplayInclIVA(financeProduct));
			mapOut.put("showFinancedFees", jposQuickQuoteService.isShowFinancedFees(financeProduct));
			mapOut.put("showCalcTarget", jposQuickQuoteService.isUsingCalcTarget(financeProduct));

//			boolean bCPIAvailableForCustomerType = jposQuickQuoteService.isCPIAvailableForCustomerType(custTypeCode);
//
//			if (financeProduct.isCPIAvailable() && bCPIAvailableForCustomerType) {
//				int cpiProductGroupId = jposQuickQuoteService.getCpiProductGroupIdFromDealerDagId(dagId, this.getUserContainer().getLocale());
//
//				mapOut.put("cpiList", jposQuickQuoteService.getCPIList(cpiProductGroupId));
//			}
			long roleId = formatService.parseLong(this.getUserContainer().getRoleID(), this.getUserContainer().getLocale(), false, 0L);
			ValidVapsContainer validVapsContainer = jposQuickQuoteService.getVapScreenMetaData(planId, roleId, new Date(), custTypeCode);
			jposQuickQuoteService.setValidVapsToContainer(quoteIdx, validVapsContainer);
			mapOut.put("vapMetaData", validVapsContainer.getDisplayVaps());

			mapOut.put("defaultPlanData", getDefaultPlanData(financeProduct, frequencyList, assetForm.getTotalNet(), assetForm.getTotalGross(), rentACarBean, this.jposQuickQuoteService.getRentACarSize(quoteIdx), quoteIdx));
//			mapOut.put("cpiAvailableForCustomerType", bCPIAvailableForCustomerType);

			mapOut.put("minMaxSliderData", jposQuickQuoteService.getMinMaxCommSubOrRate(financeProduct));

			return mapOut;
		}

		return null;
	}

}
