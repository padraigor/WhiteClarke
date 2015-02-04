package com.wcna.calms.jpos.services.quote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.common.util.Logger;
import com.wcg.product.services.session.GenericMessage;
import com.wcg.product.services.session.IMessageStore;
import com.wcg.product.services.session.SessionManager;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.customer.IApplicationCustomerDataVO;
import com.wcna.calms.service.customer.ICustomerService;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.calms.service.translation.ITranslationService;
import com.wcna.calms.web.util.FormatFactory;
import com.wcna.util.SystemException;

public class JPOSQuickQuoteCalculateService extends CalmsAjaxService {

	private final IJPOSQuickQuoteService jposQuickQuoteService;
	private final IFormatService formatService;
	private final ICustomerService customerService;
	private final ITranslationService translationService;
	private final JPOSQuoteUtil jposQuoteUtil;

	public JPOSQuickQuoteCalculateService(IJPOSQuickQuoteService jposQuickQuoteService, IFormatService formatService, ICustomerService customerService,
										  ITranslationService translationService, JPOSQuoteUtil jposQuoteUtil) {
		this.jposQuickQuoteService = jposQuickQuoteService;
		this.formatService = formatService;
		this.customerService = customerService;
		this.translationService = translationService;
		this.jposQuoteUtil = jposQuoteUtil;
	}

	public Object invoke(Object arg0) {
		if (arg0 != null && arg0 instanceof Map) {
			Map mapIn = (Map) arg0;
			int quoteIdx = this.jposQuoteUtil.getQuoteIdx(mapIn);
			IJPOSQuickQuoteAssetInputForm assetVo = this.jposQuickQuoteService.getAssetInputForm(quoteIdx);
			IRentACarBean rentACarBean = this.jposQuickQuoteService.getCommonRentACarBean(quoteIdx);

			// gather the screen data
			JPOSQuickQuoteInputVO voIn = new JPOSQuickQuoteInputVO();
			List<IVapItemRequestBean> vaps = null;

			try {
				org.apache.commons.beanutils.BeanUtils.populate(voIn, (Map) mapIn.get("quickQuoteInput"));
				List<Map<String, String>> vapList = (List<Map<String, String>>) ((Map) mapIn).get("vapInput");
				if (vapList != null) {
					vaps = new ArrayList<IVapItemRequestBean>();
					for (Map<String, String> vap : vapList) {
						IVapItemRequestBean b = this.createBean(IVapItemRequestBean.class);
						org.apache.commons.beanutils.BeanUtils.populate(b, vap);
						vaps.add(b);
					}
				}

			} catch (Exception e) {
				throw new SystemException(e);
			}

			IJPOSQuickQuoteInputForm formIn = createBean(IJPOSQuickQuoteInputForm.class);
			BeanUtils.copyProperties(voIn, formIn);

//			String planId = (String) mapIn.get("planId");
//			String customerType = null;
//			String isProposal = (String) mapIn.get("isProposal");
//			if (IConstants.PROPOSAL_APPLICATION_TYPE.equals(this.jposQuickQuoteService.getApplicationType())) {
//				IApplicationCustomerDataVO custData = customerService.getCustomerData(getAppContainer().getAppID(), IConstants.PRIMARY_APPLICANT_REF_ID);
//				customerType = custData.getCustomerTypeCode();
//			} else {
//				customerType = (String) mapIn.get("customerType");
//			}
			String customerType = this.jposQuickQuoteService.getCustomerData().getCustomerType();

//			int dealerId = 7046677;
//			int dealerId = 7043440;
//			int dagId = formatService.parseInteger(this.getUserContainer().getCurrentDagID(), this.getUserContainer().getLocale(), false, 0);
//			int dealerId = jposQuickQuoteService.getDealerIdFromDealerDagId(dagId);
//			int jtrId = jposQuickQuoteService.getJtrIdFromDealerDagId(dagId);
//			int brokerBrandId = formatService.parseInteger(this.getUserContainer().getCurrentBrandCode(), this.getUserContainer().getLocale(), false, 0);

			String capCode = null;
			if (!"on".equals(assetVo.getFreeFormatFlag())) {
				capCode = jposQuickQuoteService.getCapCodeFromModelVariantId(assetVo.getModelVariantId(), this.getUserContainer().getLocale());
			}

			List<StructuredPaymentBean> structuredPayments = null;
//			if (IConstants.FLAG_YES.equals(formIn.getIsStructured())) {
			if (jposQuickQuoteService.isStructuredPlan(quoteIdx)) {
				int term = 0;
				try {
					term = Integer.parseInt(formIn.getTerm());
				} catch (NumberFormatException nfe) {
					throw new SystemException(nfe);
				}
				List<Map<String, String>> requestMap = (List<Map<String, String>>) mapIn.get("structuredProfile");
				structuredPayments = jposQuickQuoteService.genStructuredPayments(term, jposQuickQuoteService.getInitializedContainerDate(), jposQuickQuoteService.getFinanceProductFromContainer(quoteIdx));
				if (structuredPayments != null && !structuredPayments.isEmpty()) {
					if (requestMap == null || requestMap.size() != structuredPayments.size()) {
						SessionManager.getInstance().getSessionData().getMessageStore().addMessage(new GenericMessage(
								GenericMessage.ERROR, "QQ0001"));
						return null;
					}
					int size = structuredPayments.size();
					Locale locale = getUserContainer().getLocale();
					for (int i = 0; i < size; i++) {
						StructuredPaymentBean bean = structuredPayments.get(i);
						Map<String, String> req = requestMap.get(i);
						bean.setAmount(formatService.parseDouble(req.get("payment"), locale, false, 0d));
						bean.setRatio(formatService.parseDouble(req.get("ratio"), locale, false, 0d));
					}
				}
			}

			formIn.setStructuredPayments(structuredPayments);

			IVehicleRequest vehicleIn = jposQuickQuoteService.prepareVehicleRequest(assetVo, this.getUserContainer().getLocale(), rentACarBean);
			IQuoteRequest quoteIn = jposQuickQuoteService.prepareQuoteRequest(formIn, quoteIdx, this.getUserContainer().getLocale(), capCode, customerType, vehicleIn, rentACarBean);
			IDealer dealerIn = jposQuickQuoteService.prepareDealerRequest();
			IQuoteVehicleBean quoteVehicleBean = null;
			IQuoteVehicleBean upgradeQuoteVehicleBean = null;
			String langCode = this.getUserContainer().getLanguageCode();

			if (!jposQuickQuoteService.isValidVehicleCost(quoteIdx, getUserContainer().getLocale())) {
				SessionManager.getInstance().getSessionData().getMessageStore().addMessage(new GenericMessage(
						GenericMessage.ERROR, "JPQQ0003"));
				return null;
			}

			try {
				IQuoteVehicleBeanWrapper wrapper = jposQuickQuoteService.calculate(
						quoteIn,
						vehicleIn,
						dealerIn,
						SessionManager.getInstance().getSessionData().getLocale(),
						vaps,
						jposQuickQuoteService.getValidVapsFromContainer(quoteIdx),
						Long.valueOf(this.getUserContainer().getRoleID()),
						customerType,
						quoteIdx);
				quoteVehicleBean = wrapper.getQuoteVehicleBean();
				upgradeQuoteVehicleBean = wrapper.getUpgradeVehicleBean();
			} catch (SystemException e) {
				Logger.error(e);
//				GenericMessage gm = new GenericMessage("Cannot complete the calculation. Please ensure all required fields are valid", GenericMessage.ERROR, null);
//				SessionManager.getInstance().getSessionData().getMessageStore().addMessage(gm);
//				return null;
				Map<String, Object> errorMap = new HashMap<String, Object>();
				String trans = this.translationService.getEntityTransNameByFieldNameAndControlPrefix("calcErrorInvalidFields", "quickQuoteInput", langCode);
				errorMap.put("calcError", trans);
				return errorMap;
			} catch (VapCalcException e) {
				Logger.error(e);
				Map<String, Object> errorMap = new HashMap<String, Object>();
				String trans = this.translationService.getEntityTransNameByFieldNameAndControlPrefix("calcErrorInvalidVapTaxRate", "quickQuoteInput", langCode);
				errorMap.put("calcError", trans);
				return errorMap;
			} catch (UpgradeVapPremiumAppException e) {
				Logger.error(e);
				Map<String, Object> errorMap = new HashMap<String, Object>();
				String trans = this.translationService.getEntityTransNameByFieldNameAndControlPrefix("calcErrorUpgradeVapPremium", "quickQuoteInput", langCode);
				errorMap.put("calcError", trans);
				return errorMap;
			} catch (UpgradeVapExpiredException e) {
				Logger.error(e);
				Map<String, Object> errorMap = new HashMap<String, Object>();
				String trans = this.translationService.getEntityTransNameByFieldNameAndControlPrefix("calcErrorUpgradeVapExpired", "quickQuoteInput", langCode);
				errorMap.put("calcError", trans);
				return errorMap;
			} catch (VapUpgradeCalcException e) {
				Logger.error(e);
				String vapName = e.getVapName();
				Map<String, Object> errorMap = new HashMap<String, Object>();
				String trans = this.translationService.getEntityTransNameByFieldNameAndControlPrefix("calcErrorUpgradeVap", "quickQuoteInput", langCode);
				errorMap.put("calcError", trans + " (" + vapName + ")");
				return errorMap;
			}

			IMessageStore messageStore = SessionManager.getInstance().getSessionData().getMessageStore();
			boolean isCalcError = false;

			IErrorList errorList = quoteVehicleBean.getErrorList(0);
			if (errorList != null && errorList.getErrorBeanList() != null && !errorList.getErrorBeanList().isEmpty()) {
				List<IErrorBean> errorBeanList = errorList.getErrorBeanList();
				int size = errorBeanList.size();
				isCalcError = true;
				for (int i = 0; i < size; i++) {
					IErrorBean errorBean = errorBeanList.get(i);
					messageStore.addMessage(new GenericMessage(jposQuickQuoteService.getCalcErrorTranslation(errorBean, this.getUserContainer().getLanguageCode()), GenericMessage.ERROR, null));
				}
			}

//			IFinanceProduct financeProduct = jposQuickQuoteService.getFinanceProduct(planId, dealerId, capCode, this.getUserContainer().getLocale(), jtrId, brokerBrandId);
			IFinanceProduct financeProduct = jposQuickQuoteService.getFinanceProductFromContainer(quoteIdx);
			if (financeProduct == null) {
				throw new SystemException("Invalid plan chosen when trying to calculate");
			}

			IPlan plan = jposQuickQuoteService.getPlan(financeProduct.getPlanId());

			IJPOSProfileDisplay p_out = null;
//			boolean bLease = financeProduct.getFinanceType().getIntCode() == IJPOSQuickQuoteConstants.FINANCE_TYPE_FINANCE_LEASE;
			boolean bLease = this.jposQuickQuoteService.isFinanceTypeLease(financeProduct.getFinanceType().getIntCode());
//
//			if (!bLease) {
//				p_out = jposQuickQuoteService.getProfileDisplay(quoteVehicleBean, customerType, plan, this.getUserContainer().getLocale());
//			} else {
//				p_out = jposQuickQuoteService.getProfileLeaseDisplay(quoteVehicleBean, customerType, plan, this.getUserContainer().getLocale(), IConstants.FLAG_YES.equals(voIn.getIncludeIVA()));
//			}
			if (!isCalcError) {
				p_out = jposQuickQuoteService.genProfileDisplay(quoteVehicleBean, customerType, plan, this.getUserContainer().getLocale(), IConstants.FLAG_YES.equals(voIn.getIncludeIVA()), financeProduct.isShowDepositSupportAmt());
			}

			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("profileDisplay", p_out);
//			rtnMap.put("quoteVehicleBean", quoteVehicleBean);
			rtnMap.put("isLease", bLease ? IConstants.FLAG_YES : IConstants.FLAG_NO);

//			if (upgradeQuoteVehicleBean != null) {
//				Map<String, Object> upgradeData = new HashMap<String, Object>();
//				upgradeData.put("upgradeQuoteVehicleBean", upgradeQuoteVehicleBean);
//				JPOSQuickQuoteInputVO upgradeVO = new JPOSQuickQuoteInputVO();
//				BeanUtils.copyProperties(voIn, upgradeVO);
//				getUpdatedScreenVo(upgradeVO, upgradeQuoteVehicleBean, this.getUserContainer().getLocale(), financeProduct, quoteIn);
//				upgradeData.put("upgradeScreenVO", upgradeVO);
//				rtnMap.put("vapUpgrade", upgradeData);
//			}

			getUpdatedScreenVo(voIn, quoteVehicleBean, this.getUserContainer().getLocale(), financeProduct, quoteIn);
			rtnMap.put("inputData", voIn);

			if (this.getAppContainer() != null) {
				rtnMap.put("applicationType", jposQuickQuoteService.getApplicationType());
			}

			if (quoteVehicleBean.getQuoteBean().getSubsidy1() > 0 &&
					jposQuickQuoteService.getFinanceProductFromContainer(quoteIdx).isSubsidyRequiredWarningMessage()) {
				rtnMap.put("subsidyRequiredWarning", IConstants.FLAG_YES);
			}

			return rtnMap;
		}

		return null;
	}


	private void getUpdatedScreenVo(JPOSQuickQuoteInputVO vo, IQuoteVehicleBean qvBean, Locale locale, IFinanceProduct fp, IQuoteRequest quoteRequest) {
		IQuoteBean quoteBean = qvBean.getQuoteBean();

		vo.setApr(formatService.formatDouble(quoteBean.getCustomerAPR(), locale));
		vo.setCost(formatService.formatDouble(quoteBean.getAssetPrice(), locale));

		int financeType = fp.getFinanceType().getIntCode();
		String deposit = formatService.formatDouble(quoteBean.getCashDeposit(), locale);
		if (jposQuickQuoteService.showDeposit(financeType)) {
			vo.setDeposit(deposit);
		} else if (jposQuickQuoteService.showLumpSum(financeType)) {
			vo.setLumpSum(deposit);
		}
		vo.setDepositAsPct("");
		vo.setLumpSumAsPct("");

		vo.setPartExchange(formatService.formatDouble(quoteBean.getPartExchange(), locale));
		vo.setTerm(formatService.formatInteger(quoteBean.getPeriod(), locale));

		String payment = formatService.formatDouble(quoteBean.getPayment(), locale);
		if (jposQuickQuoteService.showPayment(fp)) {
			vo.setPayment(payment);
		} else if (jposQuickQuoteService.showRental(fp)) {
			vo.setRental(payment);
		}

		String finalPayment = formatService.formatDouble(quoteBean.getFinalPayment(), locale);
		if (jposQuickQuoteService.showFinalRental(fp)) {
			vo.setFinalRental(finalPayment);
		} else if (jposQuickQuoteService.showGFV(fp)) {
			vo.setGfv(finalPayment);
		} else if (jposQuickQuoteService.showBalloon(fp)) {
			vo.setBalloon(finalPayment);
		}
		vo.setFinalRentalAsPct("");
		vo.setBalloonAsPct("");
		vo.setGfvAsPct("");

		vo.setAprFlag("");
		if (quoteRequest.isFromAPR()) {
//			vo.setRollbackTarget("14");
			vo.setPlanCodeType(IJPOSQuickQuoteConstants.PLAN_CODE_TYPE_PLAN);
		}

		int commissionType = fp.getCommissionTypeId();
		if (jposQuickQuoteService.isRequiredRate(commissionType)) {
//			vo.setPlan(formatService.formatDouble(quoteBean.getRequiredRate(), locale, FormatFactory.RAT_CODE));
			vo.setPlan(jposQuickQuoteService.getFormattedPlanCode(fp, quoteBean.getRequiredRate(), locale));
		} else if (jposQuickQuoteService.isCommissionSubsidy(commissionType)) {
//			vo.setPlan(formatService.formatDouble(quoteBean.getCommSub(), locale));
			// in the prepare quote request, the logic is modified to always set the requiredRate attribute,
			// and not the comm sub in any scenario
//			vo.setPlan(formatService.formatDouble(quoteBean.getRequiredRate(), locale, FormatFactory.RAT_CODE));
			vo.setPlan(jposQuickQuoteService.getFormattedPlanCode(fp, quoteBean.getRequiredRate(), locale));
		}

		vo.setBuybackAsPct("");
		vo.setBuyback(formatService.formatDouble(quoteBean.getBuyback(), locale));
		vo.setBuybackerType(fp.getBuybackerType() + "");

		if (this.jposQuickQuoteService.isShowingInceptionAndFirstPaymentDay(financeType)) {
			if (quoteRequest.getInceptionDate() != null) {
				vo.setInceptionDate(formatService.formatDate(quoteRequest.getInceptionDate(), locale));
			}
//			vo.setFirstPaymentDay(formatService.formatInteger(quoteRequest.getFirstPaymentDay(), locale));
		}

		if (this.jposQuickQuoteService.isShowInitialPymtHoliday(fp)) {
			vo.setInitialPymtHoliday(formatService.formatInteger(fp.getInitialPymtHoliday().intValue(), locale));
		}
	}
}
