package com.wcna.calms.jpos.services.quote;

//import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.app.IClientConstants;
import com.wcna.calms.jpos.services.settlement.IJPOSSettlementContainer;
import com.wcna.calms.service.application.IApplicationAddressDataVO;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.customer.IApplicationConsumerDataVO;
import com.wcna.calms.service.customer.IApplicationCustomerDataVO;
import com.wcna.calms.service.customer.ICustomerService;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.calms.service.quote.IQuoteVatService;
import com.wcna.calms.service.udt.IUdtTranslationService;
import com.wcna.calms.util.OptionLabelValue;
import com.wcna.lang.StringUtil;

public class JPOSQuickQuoteLoadService extends CalmsAjaxService {

	private final IQuoteVatService quoteVatService;
	private final IJPOSQuickQuoteService quickQuoteService;
	private final IFormatService formatService;
	private final ICustomerService customerService;
	private final IUdtTranslationService udtService;
	private final IJPOSQuickQuoteAssetService quickQuoteAssetService;

	public JPOSQuickQuoteLoadService(IQuoteVatService quoteVatService, IJPOSQuickQuoteService quickQuoteService, IFormatService formatService,
			ICustomerService customerService, IUdtTranslationService udtService, IJPOSQuickQuoteAssetService quickQuoteAssetService) {
		this.quoteVatService = quoteVatService;
		this.quickQuoteService = quickQuoteService;
		this.formatService = formatService;
		this.customerService = customerService;
		this.udtService = udtService;
		this.quickQuoteAssetService = quickQuoteAssetService;
	}

	/**
	 * Load quote, customer, asset details
	 */
	public Object invoke(Object arg0) {
		if (arg0 != null && arg0 instanceof Map) {
//			String appId = (String) ((Map) arg0).get("appId");
			String appId = this.getAppContainer() == null ? "" : this.getAppContainer().getAppID() <= 0 ? "" : this.getAppContainer().getAppID() + "";
			Map<String, Object> out = new HashMap<String, Object>();

			boolean bQuoteExists = false;
			String promo1 = null;
			boolean isShowDisplayIncIVA = false;
			boolean isDisplayResultsIncIVAOnLoad = false;
			boolean isShowDepositSupport = false;
			quickQuoteService.initializeQuoteContainer();
			boolean isExistingCustomer = false;

			if (!StringUtil.isEmpty(appId)) {
				Locale locale = getUserContainer().getLocale();

				IJPOSQuoteDataVO quoteVo = quickQuoteService.loadQuote(Long.valueOf(appId), locale);
				IJPOSApplicationAssetDataVO assetDataVo = null;
				IFinanceProduct financeProduct = null;
				boolean bProductExpired = false;
				String appTypeCode = quickQuoteService.getApplicationType();
				List<IRentACarBean> rentACarList = null;

				IApplicationCustomerDataVO custVo = customerService.getCustomerData(Long.valueOf(appId), IConstants.PRIMARY_APPLICANT_REF_ID);
				isExistingCustomer = custVo != null;

//				int dagId = formatService.parseInteger(this.getUserContainer().getCurrentDagID(), this.getUserContainer().getLocale(), false, 0);
//				int cpiProductGroupId = quickQuoteService.getCpiProductGroupIdFromDealerDagId(dagId, locale);
				IPlan plan = null;

				if (quoteVo != null) {
//					int dealerId = 7046677;
//					int dealerId = 7043440;
//					int dealerId = 7043440;
					Map<String, Object> loadMap = new HashMap<String, Object>();
					assetDataVo= loadAssetSubProcess(assetDataVo,appId, loadMap );
					long catalogModelVariantId = 0;
					if (!"on".equals(assetDataVo.getFreeFormatFlag()) && !StringUtils.isBlank(assetDataVo.getModelVariantId())) {
						catalogModelVariantId = formatService.parseLong(assetDataVo.getModelVariantId(), locale, false, 0L);
					}
//					if (!"on".equals(assetDataVo.getFreeFormatFlag())) {
//						capCode = quickQuoteService.getCapCodeFromModelVariantId(assetDataVo.getModelVariantId(), locale);
//					}

//					int dealerId = quickQuoteService.getDealerIdFromDealerDagId(dagId);
//					int jtrId = quickQuoteService.getJtrIdFromDealerDagId(dagId);
//					int brokerBrandId = formatService.parseInteger(this.getUserContainer().getCurrentBrandCode(), locale, false, 0);


					out.put("dataLoad", loadMap);

					plan = quickQuoteService.getPlan(quoteVo.getXrefPlaCode());
					// plan should never be null
					if (plan != null) {
						promo1 = plan.isPromo() ? IJPOSQuickQuoteConstants.PROMO_PRODUCT_PROMO : IJPOSQuickQuoteConstants.PROMO_PRODUCT_STANDARD;
					}

					financeProduct = quickQuoteService.getFinanceProduct(quoteVo.getXrefPlaCode(), this.getUserContainer().getLocale(), custVo.getCustomerTypeCode(), catalogModelVariantId);
					if (financeProduct == null) {
						bProductExpired = true;
					}

					this.quickQuoteService.setFinanceProductToContainer(0, financeProduct);

					bQuoteExists = true;
					out.put("quoteId", quoteVo.getId() + "");
					out.put("planId", quoteVo.getXrefPlaCode());
					rentACarList = quickQuoteService.getRentACarList(Long.valueOf(appId));

					JPOSQuickQuoteInputVO quoteScreenVo = null;

					if (!bProductExpired) {

						int financeType = financeProduct.getFinanceType().getIntCode();
						isShowDepositSupport = financeProduct.isShowDepositSupportAmt();

						quoteScreenVo = new JPOSQuickQuoteInputVO();
						quoteScreenVo.setFrequency(quoteVo.getFrequency() + "");
						quoteScreenVo.setMileage(quoteVo.getEstimatedAnnualMeter());

						String totalDeposit = formatService.formatDouble(quoteVo.getTotalCashDeposit(), locale);
						if (quickQuoteService.showDeposit(financeType)) {
							quoteScreenVo.setDeposit(totalDeposit);
						} else if (quickQuoteService.showLumpSum(financeType)) {
							quoteScreenVo.setLumpSum(totalDeposit);
						}

						quoteScreenVo.setPartExchange(quoteVo.getTotalPartExchangeAmount());
						quoteScreenVo.setSettlement(quoteVo.getTotalSettlementAmount());
						quoteScreenVo.setTerm(quoteVo.getTerm() == null ? "" : quoteVo.getTerm() + "");

						String payment = formatService.formatDouble(quoteVo.getPayment(), locale);
						if (quickQuoteService.showPayment(financeProduct)) {
							quoteScreenVo.setPayment(payment);
						} else if (quickQuoteService.showRental(financeProduct)) {
							quoteScreenVo.setRental(payment);
						}

						quoteScreenVo.setNumberInAdvance(quoteVo.getNumberPaymentsInAdvance() + "");

						String finalPayment = formatService.formatDouble(quoteVo.getBalloon(), locale);
						if (quickQuoteService.showBalloon(financeProduct)) {
							quoteScreenVo.setBalloon(finalPayment);
						} else if (quickQuoteService.showFinalRental(financeProduct)) {
							quoteScreenVo.setFinalRental(finalPayment);
						} else if (quickQuoteService.showGFV(financeProduct)) {
							quoteScreenVo.setGfv(finalPayment);
						}

//						quoteScreenVo.setApr(quoteVo.getApr() + "");
						quoteScreenVo.setApr(formatService.formatDouble(quoteVo.getApr(), locale));
						quoteScreenVo.setAprFlag(quoteVo.getAprBasedCalcFlag());

//						if (financeType == IJPOSQuickQuoteConstants.FINANCE_TYPE_FINANCE_LEASE) {
//							quoteScreenVo.setRollbackTarget(IJPOSQuickQuoteConstants.CALC_RENTAL + "");
//						} else {
//							quoteScreenVo.setRollbackTarget(IJPOSQuickQuoteConstants.CALC_TERM + "");
//						}
						quoteScreenVo.setRollbackTarget(this.quickQuoteService.getDefaultRollbackTarget(financeType, financeProduct.getStructureType()));
						if (rentACarList != null && !rentACarList.isEmpty()) {
							quoteScreenVo.setRollbackTarget("14");
							quoteScreenVo.setNumberOfVehicles(rentACarList.size() + "");
						}

//						Map<String, Object> loadMap = new HashMap<String, Object>();
//						out.put("dataLoad", loadMap);
//						out.put("planId", quoteVo.getXrefPlaCode());

//						String c_type = quickQuoteService.determineQuoteCostField(financeProduct);
//						if (IJPOSQuickQuoteConstants.QUOTE_ASSET_GROSS.equals(c_type)) {
//							quoteScreenVo.setCost(formatService.formatDouble(quoteVo.getTotalCost(), locale));
//						} else if (IJPOSQuickQuoteConstants.QUOTE_ASSET_NET.equals(c_type)) {
//							quoteScreenVo.setCost(formatService.formatDouble(quoteVo.getTotalCostNet(), locale));
//						}
	//					quoteScreenVo.setCost(quoteVo.getTotalCost() + "");

						int commissionType = financeProduct.getCommissionTypeId();
						String reqRateOrCommSub = null;
						if (quickQuoteService.isRequiredRate(commissionType)) {
//							reqRateOrCommSub = quoteVo.getRequiredRate();
							reqRateOrCommSub = quickQuoteService.getFormattedPlanCode(financeProduct, quoteVo.getRequiredRateDouble() == null ? 0 : quoteVo.getRequiredRateDouble(), locale);
						} else if (quickQuoteService.isCommissionSubsidy(commissionType)) {
//							reqRateOrCommSub = quoteVo.getCommissionSubsidy();
							reqRateOrCommSub = quickQuoteService.getFormattedPlanCode(financeProduct, quoteVo.getCommissionSubsidyDouble() == null ? 0 : quoteVo.getCommissionSubsidyDouble(), locale);
						}
						quoteScreenVo.setPlan(reqRateOrCommSub);
						quoteScreenVo.setAssetsUsageCode(quoteVo.getAssetsUsageCode());
						quoteScreenVo.setBuyback(formatService.formatDouble(quoteVo.getBuyBackOrResidual(), locale));

//						if (financeProduct.getBuybackerType() > 0) {
						boolean isShowBuyback = quickQuoteService.isShowBuyback(financeProduct);
						if (isShowBuyback) {
//							quoteScreenVo.setBuybackerType(financeProduct.getBuybackerType() + "");
							if (!StringUtils.isBlank(quoteVo.getBuybackerType())) {
								quoteScreenVo.setBuybackerType(quoteVo.getBuybackerType());
							} else {
								quoteScreenVo.setBuybackerType(financeProduct.getBuybackerType() + "");
							}
						}

						if (quickQuoteService.isShowingInceptionAndFirstPaymentDay(financeType)) {
							quoteScreenVo.setInceptionDate(formatService.formatDate(quoteVo.getInceptionDate(), locale));
//							quoteScreenVo.setFirstPaymentDay(quoteVo.getFirstPaymentDay() == null ? "" : quoteVo.getFirstPaymentDay() + "");
						}

						loadMap.put("frequencyList", quickQuoteService.getFrequencyList(financeProduct));
						loadMap.put("defaultPlanData", quoteScreenVo);

	//					int financeType = financeProduct.getFinanceType().getIntCode();

						boolean isShowInitialPymtHoliday = quickQuoteService.isShowInitialPymtHoliday(financeProduct);
						if (isShowInitialPymtHoliday) {
//							quoteScreenVo.setInitialPymtHoliday(formatService.formatInteger(financeProduct.getInitialPymtHoliday().intValue(), locale));
							// in the case that this is an existing quote whose finance product previously did not
							// show the initial payment holiday field, but now does, we need to set
							// the value to the value of the finance product
							String initialPymtHoliday;
							if (quoteVo.getPaymentHoliday() != null) {
								initialPymtHoliday = formatService.formatInteger(quoteVo.getPaymentHoliday(), locale);
							} else {
								initialPymtHoliday = formatService.formatInteger(financeProduct.getInitialPymtHoliday().intValue(), locale);
							}
							quoteScreenVo.setInitialPymtHoliday(initialPymtHoliday);
						}

						boolean isShowFinancedFees = quickQuoteService.isShowFinancedFees(financeProduct);
						if (isShowFinancedFees && quoteVo.isOpeningFeesFinancedFlag()) {
							quoteScreenVo.setIsFinancedFees(IConstants.FLAG_YES);
						}

						isShowDisplayIncIVA = quickQuoteService.isShowDisplayInclIVA(financeProduct);
						if (isShowDisplayIncIVA && quoteVo.isShowTaxIncludeFlag()) {
							quoteScreenVo.setIncludeIVA(IConstants.FLAG_YES);
							isDisplayResultsIncIVAOnLoad = true;
						}

						if (quoteVo.getProfileStructureTypeId() == IQuoteBean.PROFILE_TYPE_STRUCTURED_LONG) {
							quoteScreenVo.setIsStructured(IConstants.FLAG_YES);
							List<StructuredPaymentBean> structuredPayments = quickQuoteService.loadStructuredPayments(quoteVo.getId());
							if (structuredPayments != null && !structuredPayments.isEmpty()) {
								List<StructuredPaymentScreenBean> screenList = new ArrayList<StructuredPaymentScreenBean>();
								loadMap.put("structuredPayments", screenList);
								StructuredPaymentScreenBean screenBean;
								for (StructuredPaymentBean bean : structuredPayments) {
									screenBean = new StructuredPaymentScreenBean();
									screenList.add(screenBean);

									screenBean.setAmount(formatService.formatDouble(bean.getAmount(), locale));
									screenBean.setDate(formatService.formatDate(bean.getDate(), locale));
									screenBean.setPaymentNr(bean.getPaymentNr() + "");
									screenBean.setRatio(formatService.formatDouble(bean.getRatio(), locale));
								}
							}
						}

						loadMap.put("showDeposit", quickQuoteService.showDeposit(financeType));
						loadMap.put("showLumpSum", quickQuoteService.showLumpSum(financeType));
						loadMap.put("showPayment", quickQuoteService.showPayment(financeProduct));
						loadMap.put("showRental", quickQuoteService.showRental(financeProduct));
						loadMap.put("showFinalRental", quickQuoteService.showFinalRental(financeProduct));
						loadMap.put("showGFV", quickQuoteService.showGFV(financeProduct));
						loadMap.put("showBalloon", quickQuoteService.showBalloon(financeProduct));
						loadMap.put("showBuyback", isShowBuyback);
						loadMap.put("showInceptionAndFirstPaymentDay", quickQuoteService.isShowingInceptionAndFirstPaymentDay(financeType));
						loadMap.put("showInitialPymtHoliday", isShowInitialPymtHoliday);
						loadMap.put("showDisplayInclIVA", isShowDisplayIncIVA);
						loadMap.put("showFinancedFees", isShowFinancedFees);
						loadMap.put("showCalcTarget", quickQuoteService.isUsingCalcTarget(financeProduct));

						loadMap.put("plan", financeProduct);


						String custTypeCode = null;
						if (custVo != null) {
							custTypeCode = custVo.getCustomerTypeCode();
						}

						IVapMetaDataWrapper wrapper = quickQuoteService.loadVapsAndMetaData(plan.getPlanId(), Long.valueOf(this.getUserContainer().getRoleID()), new Date(), custTypeCode, quoteVo.getId());
						quickQuoteService.setValidVapsToContainer(0, wrapper.getValidVapsContainer());
						loadMap.put("vapMetaDataWrapper", wrapper);

						loadMap.put("minMaxSliderData", quickQuoteService.getMinMaxCommSubOrRate(financeProduct));
					}


					IJPOSQuickQuoteAssetInputForm assetForm = this.quickQuoteService.getAssetInputForm(0);

					if (quoteScreenVo != null) {
						quoteScreenVo.setCost(formatService.formatDouble(quickQuoteService.getCostValue(financeProduct, 0, locale), locale));
					}

					if (StringUtils.isBlank(promo1)) {
						promo1 = IJPOSQuickQuoteConstants.PROMO_PRODUCT_PROMO;
					}

					IRentACarBean rentACarBean = null;
					if (rentACarList != null && !rentACarList.isEmpty()) {
						rentACarBean = rentACarList.get(0);

					}

					List<IPlan> planList = quickQuoteService.getAllPlansForDealer(assetForm, this.getUserContainer().getLocale(), promo1,
																				  custVo == null ? null : custVo.getCustomerTypeCode(),
																				  rentACarBean);
					List<OptionLabelValue> plans = new ArrayList<OptionLabelValue>();
					if (planList != null && !planList.isEmpty()) {
						int size = planList.size();
						for (int i = 0; i < size; i++) {
							IPlan p = planList.get(i);
							OptionLabelValue olv = new OptionLabelValue(p.getPlanType() + " " + p.getPlanSubType(), p.getPlanId());
							plans.add(olv);
						}
					}

					if (bProductExpired) {
						out.put("productExpired", IConstants.FLAG_YES);
						plans.add(new OptionLabelValue(plan.getPlanType() + " " + plan.getPlanSubType() + " - Read Only", plan.getPlanId()));
					}

					loadMap.put("planList", plans);
				}

//				String appTypeCode = quickQuoteService.getApplicationType(Long.valueOf(appId));
//
//				IApplicationCustomerDataVO custVo = customerService.getCustomerData(Long.valueOf(appId), IConstants.PRIMARY_APPLICANT_REF_ID);
				quickQuoteService.setSettlementQuoteToContainer(0, quickQuoteService.loadSettlementContainer(Long.parseLong(appId)));

				if (IConstants.LEAD_APPLICATION_TYPE.equals(appTypeCode)) {
					IJPOSSettlementContainer settlementContainer = quickQuoteService.getSettlementContainer(0);
					if (settlementContainer != null) {
						settlementContainer.setInsertNote(true);
					}

					JPOSQuickQuoteCustomerVO screenVo = new JPOSQuickQuoteCustomerVO();
					if (custVo != null) {
//						JPOSQuickQuoteCustomerVO screenVo = new JPOSQuickQuoteCustomerVO();

						String custTypeCode = custVo.getCustomerTypeCode();
						screenVo.setCustomerType(custTypeCode);
						String tpId = this.quickQuoteService.getTpId(Long.valueOf(appId));
						if (!StringUtils.isBlank(tpId)) {
							screenVo.setTpId(tpId);
						}

						if (IConstants.SOLE_TRADER_CUSTOMER_TYPE.equals(custTypeCode)) {
							IApplicationConsumerDataVO consumerVo = (IApplicationConsumerDataVO) custVo;
							screenVo.setTradingAs(custVo.getTradeName());
							screenVo.setTitle(consumerVo.getTitle());
							screenVo.setForename(consumerVo.getFirstName());
							screenVo.setInitial(consumerVo.getMiddleInitial());
							screenVo.setSurname(consumerVo.getLastName());
							screenVo.setNationalityId(consumerVo.getNationalityCode());
							screenVo.setTaxNum(consumerVo.getTaxNum());
							screenVo.setDateOfBirth(consumerVo.getDateOfBirth());
						} else if (IConstants.CONSUMER_CUSTOMER_TYPE.equals(custTypeCode)) {
							IApplicationConsumerDataVO consumerVo = (IApplicationConsumerDataVO) custVo;
							screenVo.setTitle(consumerVo.getTitle());
							screenVo.setForename(consumerVo.getFirstName());
							screenVo.setInitial(consumerVo.getMiddleInitial());
							screenVo.setSurname(consumerVo.getLastName());
							screenVo.setName(consumerVo.getCommonName());
							screenVo.setNationalityId(consumerVo.getNationalityCode());
							screenVo.setDateOfBirth(consumerVo.getDateOfBirth());
						} else if (IConstants.PARTNERSHIP_CUSTOMER_TYPE.equals(custTypeCode) || IConstants.COMMERCIAL_CUSTOMER_TYPE.equals(custTypeCode)) {
							screenVo.setCompany(custVo.getCommonName());
							screenVo.setTaxNum(custVo.getTaxNum());
							screenVo.setRegistrationCode(custVo.getRegistrationCode());
							screenVo.setEstablishedDate(custVo.getEstablishedDate());
						}

						IApplicationAddressDataVO addressVo = customerService.getCustomerAddress(Long.valueOf(appId), IConstants.PRIMARY_APPLICANT_REF_ID, customerService.getCustomerAddressType(custTypeCode));
						if (addressVo != null) {
							screenVo.setPostCode(addressVo.getZipPostal());
							screenVo.setBuildingName(addressVo.getAddressLine3());
							screenVo.setNumber(addressVo.getStreetNumber());
							screenVo.setStreet(addressVo.getStreetName());
							screenVo.setLocality(addressVo.getCity());
							screenVo.setPostTown(addressVo.getCounty());
							screenVo.setCountry(addressVo.getCountry());
							screenVo.setAddressLine2(addressVo.getAddressLine2());
							screenVo.setCity(addressVo.getCity());
							screenVo.setZipPostal(addressVo.getZipPostal());
							screenVo.setZipPostal2(addressVo.getZipPostal2());
							screenVo.setStateProvince(addressVo.getStateProvince());
							screenVo.setAddressLine3(addressVo.getAddressLine3());
							screenVo.setAddressLine4(addressVo.getAddressLine4());
							screenVo.setStreetName(addressVo.getStreetName());
							screenVo.setCounty(addressVo.getCounty());
							screenVo.setSuiteNumber(addressVo.getSuiteNumber());
							screenVo.setStreetNumber(addressVo.getStreetNumber());
							//screenVo.setNeighbourhood(addressVo.getNeighbourhood());
						}

						out.put("custDetails", screenVo);
						out.put("custNameOutline", quickQuoteService.getCustomerNameDesc(custVo));
						Map<String, OptionLabelValue> custTypeMap = udtService.getTranslatedCodeValuesMap("CUSTOMER_TYPE", getUserContainer().getLanguageCode(), getUserContainer().getCountryCode());
						OptionLabelValue olv = custTypeMap.get(custTypeCode);
						out.put("custTypeOutline", olv == null ? "" : olv.getLabel());
					} else {
						out.put("custDetails", screenVo);
					}

					this.quickQuoteService.setCustomerDetailsToContainer(screenVo);

					// we should always try to generate a profile display
					// in the case that this screen is readonly for the user
//					if (bProductExpired) {
						if (quoteVo != null && assetDataVo != null && custVo != null && plan != null) {
//							boolean bLease = plan.getFinanceType().getIntCode() == IJPOSQuickQuoteConstants.FINANCE_TYPE_FINANCE_LEASE;
							boolean bLease = this.quickQuoteService.isFinanceTypeLease(plan.getFinanceType().getIntCode());
							IQuoteVehicleBean quoteVehicleBean = quickQuoteService.constructQuoteVehicleBean(quoteVo, assetDataVo, bLease, getUserContainer().getLocale());

							IJPOSProfileDisplay p_out = null;
//							if (!bLease) {
//								p_out = quickQuoteService.getProfileDisplay(quoteVehicleBean, custVo.getCustomerTypeCode(), plan, this.getUserContainer().getLocale());
//							} else {
//								p_out = quickQuoteService.getProfileLeaseDisplay(quoteVehicleBean, custVo.getCustomerTypeCode(), plan, this.getUserContainer().getLocale(), isDisplayResultsIncIVAOnLoad);
//							}
							p_out = quickQuoteService.genProfileDisplay(quoteVehicleBean, custVo.getCustomerTypeCode(), plan, this.getUserContainer().getLocale(), isDisplayResultsIncIVAOnLoad, isShowDepositSupport);

							out.put("profileDisplay", p_out);
							out.put("isLease", bLease ? IConstants.FLAG_YES : IConstants.FLAG_NO);
						}
//					}

				} else if (IConstants.PROPOSAL_APPLICATION_TYPE.equals(appTypeCode)) {
					out.put("isProposal", "Y");
					if (custVo != null) {
						JPOSQuickQuoteCustomerVO screenVo = new JPOSQuickQuoteCustomerVO();
						screenVo.setCustomerType(custVo.getCustomerTypeCode());
						out.put("custDetails", screenVo);
						this.quickQuoteService.setCustomerDetailsToContainer(screenVo);
					}
					if (quoteVo != null && assetDataVo != null && custVo != null && plan != null) {
//						boolean bLease = plan.getFinanceType().getIntCode() == IJPOSQuickQuoteConstants.FINANCE_TYPE_FINANCE_LEASE;
						boolean bLease = this.quickQuoteService.isFinanceTypeLease(plan.getFinanceType().getIntCode());
						IQuoteVehicleBean quoteVehicleBean = quickQuoteService.constructQuoteVehicleBean(quoteVo, assetDataVo, bLease, getUserContainer().getLocale());

						IJPOSProfileDisplay p_out = null;
//						if (!bLease) {
//							p_out = quickQuoteService.getProfileDisplay(quoteVehicleBean, custVo.getCustomerTypeCode(), plan, this.getUserContainer().getLocale());
//						} else {
//							p_out = quickQuoteService.getProfileLeaseDisplay(quoteVehicleBean, custVo.getCustomerTypeCode(), plan, this.getUserContainer().getLocale(), isDisplayResultsIncIVAOnLoad);
//						}
						p_out = quickQuoteService.genProfileDisplay(quoteVehicleBean, custVo.getCustomerTypeCode(), plan, this.getUserContainer().getLocale(), isDisplayResultsIncIVAOnLoad, isShowDepositSupport);

						out.put("profileDisplay", p_out);
						out.put("isLease", bLease ? IConstants.FLAG_YES : IConstants.FLAG_NO);
					}
				}
			}

			String defaultTaxPointDate = formatService.formatDate(new Date(), this.getUserContainer().getLocale());
			String brandCode = this.getUserContainer().getCurrentBrandCode();
			String defaultAssetType = this.quickQuoteAssetService.getDefaultAssetType(brandCode);
			String defaultMake = this.quickQuoteAssetService.getDefaultMake(brandCode, defaultAssetType);

			if (!bQuoteExists) {
				JPOSQuickQuoteAssetVO asset = new JPOSQuickQuoteAssetVO();
//				convertVatRateCodes(asset);

				//this.quickQuoteAssetService.setDefaultAmounts(asset,defaultTaxPointDate, defaultAssetType, defaultMake,this.quickQuoteService.getDefaultTaxRateId(), this.quickQuoteService.getDefaultZeroTaxRateId(), true);

				JPOSQuickQuoteAssetDefaultValuesVO defaultValuesVO= new JPOSQuickQuoteAssetDefaultValuesVO();
				defaultValuesVO.setInputVO(asset);
				defaultValuesVO.setDefaultTaxPointDate(defaultTaxPointDate);
				defaultValuesVO.setDefaultAssetType(defaultAssetType);
				defaultValuesVO.setDefaultMake(defaultMake);
				//defaultValuesVO.setDefaultTaxRateId(this.quickQuoteService.getDefaultTaxRateId());
				defaultValuesVO.setDefaultZeroTaxRateId(this.quickQuoteService.getDefaultZeroTaxRateId());
				defaultValuesVO.setSetDefaultAssetTypeOrMake(true);
				defaultValuesVO.setNewUsed(asset.getNewUsed());

				defaultValuesVO.setDeriveTaxIncludingFlag(true);
				String screenCode = "quickQuoteAsset";
				defaultValuesVO.setScreenCode(screenCode);

				if(asset.getNewUsed().equals("U")){
				defaultValuesVO.setSetDefaultAssetTypeOrMake(IClientConstants.EXECUTE.equals(projectProperties.getProperty(IClientConstants.VEHICLETYPE_USED_SET_DEFAULT_ASSETTYPE_OR_MAKE,"1")));//It is required to hold the default assettype/make
				}else{
					defaultValuesVO.setSetDefaultAssetTypeOrMake(IClientConstants.EXECUTE.equals(projectProperties.getProperty(IClientConstants.VEHICLETYPE_NEW_SET_DEFAULT_ASSETTYPE_OR_MAKE,"1")));//It is required to hold the default assettype/make
				}


				this.quickQuoteAssetService.setDefaultAmounts(defaultValuesVO);



				out.put("asset1", asset);
//				JPOSQuickQuoteCustomerVO custVo = new JPOSQuickQuoteCustomerVO();
				JPOSQuickQuoteCustomerVO custVo = null;
				if (isExistingCustomer) {
					custVo = (JPOSQuickQuoteCustomerVO) out.get("custDetails");
				} else {
					custVo = new JPOSQuickQuoteCustomerVO();
					String defaultCustType = quickQuoteService.getDefaultCustType(Long.parseLong(getUserContainer().getCurrentDagID()));
					if (!StringUtils.isBlank(defaultCustType)) {
						custVo.setCustomerType(defaultCustType);
						Map<String, OptionLabelValue> custTypeMap = udtService.getTranslatedCodeValuesMap("CUSTOMER_TYPE", getUserContainer().getLanguageCode(), getUserContainer().getCountryCode());
						OptionLabelValue olv = custTypeMap.get(defaultCustType);
						out.put("custTypeOutline", olv == null ? "" : olv.getLabel());
					}
					out.put("custDetails", custVo);
				}
				this.quickQuoteService.setCustomerDetailsToContainer(custVo);

				this.quickQuoteService.setAssetDetailsToContainer(0, asset, null, null, this.quickQuoteService.getDefaultPartExchange());
			}

//			JPOSQuickQuoteAssetVO asset = new JPOSQuickQuoteAssetVO();
////			convertVatRateCodes(asset);
//			setDefaultAmounts(asset, defaultTaxPointDate);
//			out.put("asset2", asset);
//			out.put("asset3", asset);

			int size = this.quickQuoteService.getQuoteContainerSize();
			for (int i = 1; i < size; i++) {
				JPOSQuickQuoteAssetVO asset = new JPOSQuickQuoteAssetVO();
				//this.quickQuoteAssetService.setDefaultAmounts(asset, defaultTaxPointDate, defaultAssetType, defaultMake,this.quickQuoteService.getDefaultTaxRateId(), this.quickQuoteService.getDefaultZeroTaxRateId(), true);

				JPOSQuickQuoteAssetDefaultValuesVO defaultValuesVO= new JPOSQuickQuoteAssetDefaultValuesVO();
				defaultValuesVO.setInputVO(asset);
				defaultValuesVO.setDefaultTaxPointDate(defaultTaxPointDate);
				defaultValuesVO.setDefaultAssetType(defaultAssetType);
				defaultValuesVO.setDefaultMake(defaultMake);
				//defaultValuesVO.setDefaultTaxRateId(this.quickQuoteService.getDefaultTaxRateId());
				defaultValuesVO.setDefaultZeroTaxRateId(this.quickQuoteService.getDefaultZeroTaxRateId());
				defaultValuesVO.setSetDefaultAssetTypeOrMake(true);
				defaultValuesVO.setNewUsed(asset.getNewUsed());

				defaultValuesVO.setDeriveTaxIncludingFlag(true);
				String screenCode = "quickQuoteAsset";
				defaultValuesVO.setScreenCode(screenCode);

				if(asset.getNewUsed().equals("U")){
				defaultValuesVO.setSetDefaultAssetTypeOrMake(IClientConstants.EXECUTE.equals(projectProperties.getProperty(IClientConstants.VEHICLETYPE_USED_SET_DEFAULT_ASSETTYPE_OR_MAKE,"1")));//It is required to hold the default assettype/make
				}else{
					defaultValuesVO.setSetDefaultAssetTypeOrMake(IClientConstants.EXECUTE.equals(projectProperties.getProperty(IClientConstants.VEHICLETYPE_NEW_SET_DEFAULT_ASSETTYPE_OR_MAKE,"1")));//It is required to hold the default assettype/make
				}



				this.quickQuoteAssetService.setDefaultAmounts(defaultValuesVO);


				out.put("asset" + (i + 1), asset);
				this.quickQuoteService.setAssetDetailsToContainer(i, asset, null, null,null);
			}

			if (StringUtils.isBlank(promo1)) {
				promo1 = IJPOSQuickQuoteConstants.PROMO_PRODUCT_PROMO;
			}

			out.put("promo1", promo1);
			out.put("promo2", IJPOSQuickQuoteConstants.PROMO_PRODUCT_PROMO);
			out.put("promo3", IJPOSQuickQuoteConstants.PROMO_PRODUCT_PROMO);

			return out;
		}

		return null;
	}

//	private void convertVatRateCodes(JPOSQuickQuoteAssetVO v_in) {
//		v_in.setTaxCode(quoteVatService.getVatRateTypeValueByCode(v_in.getTaxCode()));
//		v_in.setExtraTaxCode(quoteVatService.getVatRateTypeValueByCode(v_in.getExtraTaxCode()));
//		v_in.setTaxableDFOptionsVatRate(quoteVatService.getVatRateTypeValueByCode(v_in.getTaxableDFOptionsVatRate()));
//		v_in.setNonTaxableDFOptionsVatRate(quoteVatService.getVatRateTypeValueByCode(v_in.getNonTaxableDFOptionsVatRate()));
//		v_in.setRoadFundLicenseVatRate(quoteVatService.getVatRateTypeValueByCode(v_in.getRoadFundLicenseVatRate()));
//		v_in.setFirstRegistrationVatRate(quoteVatService.getVatRateTypeValueByCode(v_in.getFirstRegistrationVatRate()));
//	}



	/////WARNING: PENDING REFACTORING TASK.....THIS METHODS NEEDS TO BE MOVED TO THE SERVICE LAYER...I DID HAVE SOME TROUBLE WHILE I DID TRY TO MOVE IT IN MY FIRST ATTEMPT..
	/////CURRENTLY THE SAME CODE IS DUPLICATED IN JPOSQuickQuoteLoadService.java AND JPOSQuickQuoteAssetLoadService.java
	////PLEASE DO CHANGE IN BOTH CLASSES IF ANY CHANGES ARE INTRODUCED TO THIS METHOD.
	private IJPOSApplicationAssetDataVO loadAssetSubProcess(IJPOSApplicationAssetDataVO assetDataVo, String appId, Map<String, Object> loadMap){

		List<IRentACarBean> rentACarList = null;
		IJPOSQuickQuoteAssetInputForm assetForm = null;
		int modelVariantId = 0;
		if (!StringUtil.isEmpty(appId)) {
			Locale locale = getUserContainer().getLocale();
			assetDataVo = quickQuoteService.loadAsset(Long.valueOf(appId), locale, false);
			rentACarList = quickQuoteService.getRentACarList(Long.valueOf(appId));

			assetForm = new JPOSQuickQuoteAssetVO();
							if (assetDataVo != null) {
								JPOSQuickQuoteAssetVO screenVo = new JPOSQuickQuoteAssetVO();
								screenVo.setNewUsed(assetDataVo.getNewOrUsed());
								screenVo.setRegistrationCode(assetDataVo.getRegPlateId());
								screenVo.setFreeFormatFlag(assetDataVo.getFreeFormatFlag());
								screenVo.setMakeId(assetDataVo.getMakeId());
								screenVo.setModelId(assetDataVo.getModelId());
								screenVo.setModelVariantId(assetDataVo.getModelVariantId());

								if (!"on".equals(screenVo.getFreeFormatFlag())) {
									if (!StringUtil.isEmpty(screenVo.getModelVariantId())) {
										modelVariantId = formatService.parseInteger(screenVo.getModelVariantId(), locale, false, 0);
			}
								} else {
									if(!IClientConstants.EXECUTE.equals(projectProperties.getProperty(IClientConstants.ONCHANGE_FFE_CHKBOX_IMPL_DISABLE_MAKE_TOGGLE))){
										screenVo.setMakeId(assetDataVo.getMakeDesc());
									}
									screenVo.setModelId(assetDataVo.getModelDesc());
									screenVo.setModelVariantId(assetDataVo.getModelVariantDesc());
								}

								screenVo.setTaxIncludingFlag(assetDataVo.getIsTaxable());
								screenVo.setRegistrationNumber(assetDataVo.getRegistrationNumber());
								Date d = assetDataVo.getRegistrationDate();
								if (d != null) {
									screenVo.setRegistrationDate(formatService.formatDate(d, locale));
								}

								screenVo.setAssetType(assetDataVo.getAssetType());

								Double meter = assetDataVo.getMeterValue();
								if (meter == null) {
									meter = new Double(0);
								}
								screenVo.setKilometrage(formatService.formatInteger(meter.intValue(), locale));
								screenVo.setVin(assetDataVo.getVin());


								screenVo.setManufactureDate(formatService.formatDate(assetDataVo.getManufactureDate(), locale));
								screenVo.setMortgageRegDate(formatService.formatDate(assetDataVo.getMortgageRegDate(), locale));

								screenVo.setAssetUsageCode(assetDataVo.getAssetUsageCode());
								screenVo.setApprovedUsedAssetCode(assetDataVo.getApprovedUsedAssetCode());
								screenVo.setEnviroImpactRatingCode(assetDataVo.getEnviroImpactRatingCode());
								screenVo.setEnviroImpactRatingAmount(formatService.formatDouble(assetDataVo.getEnviroImpactRatingAmount(), locale));
								screenVo.setTaxHorsePowerRating(formatService.formatDouble(assetDataVo.getTaxHorsePowerRating(), locale));

								screenVo.setSalePrice(formatService.formatDouble(assetDataVo.getSalePrice(), locale));
								screenVo.setTaxCode(assetDataVo.getTaxCode());
								screenVo.setTaxAmt(formatService.formatDouble(assetDataVo.getTaxAmt(), locale));
								screenVo.setGrossCost(formatService.formatDouble(assetDataVo.getGrossCost(), locale));

								screenVo.setExtraAmt(formatService.formatDouble(assetDataVo.getExtraAmount(), locale));
								screenVo.setExtraTaxCode(assetDataVo.getExtraTaxCode());
								screenVo.setExtraTaxAmt(formatService.formatDouble(assetDataVo.getExtraTaxAmount(), locale));
								screenVo.setExtraTotalCost(assetDataVo.getExtraTotalCost());

								screenVo.setTaxableDFOptionsGross(assetDataVo.getTaxableDFOptionsGross());
								screenVo.setTaxableDFOptionsNet(assetDataVo.getTaxableDFOptionsNet());
								String defaultZero = formatService.formatDouble(0d, locale);
								// this is to avoid validation issues if this is a rent-a-car deal
								if (StringUtils.isBlank(screenVo.getTaxableDFOptionsNet())) {
									screenVo.setTaxableDFOptionsNet(defaultZero);
								}
								if (StringUtils.isBlank(screenVo.getTaxableDFOptionsGross())) {
									screenVo.setTaxableDFOptionsGross(defaultZero);
								}

								screenVo.setTaxableDFOptionsVatAmt(assetDataVo.getTaxableDFOptionsVatAmt());
								screenVo.setTaxableDFOptionsVatRate(assetDataVo.getTaxableDFOptionsVatRate());

								screenVo.setNonTaxableDFOptionsGross(assetDataVo.getNonTaxableDFOptionsGross());
								screenVo.setNonTaxableDFOptionsNet(assetDataVo.getNonTaxableDFOptionsNet());
								if (StringUtils.isBlank(screenVo.getNonTaxableDFOptionsNet())) {
									screenVo.setNonTaxableDFOptionsNet(defaultZero);
								}
								if (StringUtils.isBlank(screenVo.getNonTaxableDFOptionsGross())) {
									screenVo.setNonTaxableDFOptionsGross(defaultZero);
								}

								screenVo.setNonTaxableDFOptionsVatAmt(assetDataVo.getNonTaxableDFOptionsVatAmt());
								screenVo.setNonTaxableDFOptionsVatRate(assetDataVo.getNonTaxableDFOptionsVatRate());

								screenVo.setRoadFundLicenseGross(assetDataVo.getRoadFundLicenseGross());
								screenVo.setRoadFundLicenseNet(assetDataVo.getRoadFundLicenseNet());
								screenVo.setRoadFundLicenseVatAmt(assetDataVo.getRoadFundLicenseVatAmt());
								screenVo.setRoadFundLicenseVatRate(assetDataVo.getRoadFundLicenseVatRate());

								screenVo.setFirstRegistrationGross(assetDataVo.getFirstRegistrationGross());
								screenVo.setFirstRegistrationNet(assetDataVo.getFirstRegistrationNet());
								screenVo.setFirstRegistrationVatAmt(assetDataVo.getFirstRegistrationVatAmt());
								screenVo.setFirstRegistrationVatRate(assetDataVo.getFirstRegistrationVatRate());

								screenVo.setTotalNet(assetDataVo.getTotalNet());
								screenVo.setTotalGross(formatService.formatDouble(assetDataVo.getTotalCost(), locale));
								screenVo.setTotalVatAmt(formatService.formatDouble(assetDataVo.getTotalVat(), locale));

								screenVo.setDiscountNet(assetDataVo.getDiscountNet());
								screenVo.setDiscountVatRate(assetDataVo.getDiscountVatRate());
								screenVo.setDiscountVatAmt(assetDataVo.getDiscountVatAmt());
								screenVo.setDiscountGross(assetDataVo.getDiscountGross());

								if (IConstants.FLAG_YES.equals(assetDataVo.getDiscountAmtIsPct())) {
									screenVo.setDiscountAmtIsPct(IConstants.FLAG_YES);
								} else {
									screenVo.setDiscountAmtIsPct("");
								}

								if (assetDataVo.getDiscountAmt() != null) {
									screenVo.setDiscountAmt(assetDataVo.getDiscountAmt());
								} else {
									screenVo.setDiscountAmt("");
								}

								if (assetDataVo.getDiscountPct() != null) {
									screenVo.setDiscountPct(assetDataVo.getDiscountPct());
								} else {
									screenVo.setDiscountPct("");
								}


								screenVo.setTaxPointDate(formatService.formatDate(assetDataVo.getTaxPointDate(), locale));
								screenVo.setTaxRateValue(formatService.formatDouble(assetDataVo.getTaxRateValue(), locale));

								// tfs demo
								screenVo.setLctSummary(formatService.formatDouble(assetDataVo.getLctSummary(), locale));
								screenVo.setGstSummary(formatService.formatDouble(assetDataVo.getGstSummary(), locale));
								screenVo.setLct(assetDataVo.getLct());
								if (StringUtils.isBlank(screenVo.getLct())) {
									screenVo.setLct(IJPOSQuickQuoteConstants.LCT_NONE);
								}
								// end tfs demo

								loadMap.put("assetDetails", screenVo);
								loadMap.put("vehicleOutline", quickQuoteService.getVehicleDescription(Long.valueOf(appId)));

								loadMap.put("rentACarList", rentACarList);

								List<IVehicleFFOBean> ffoList = assetDataVo.getFfoList();
								if (ffoList == null) {
									ffoList = new ArrayList<IVehicleFFOBean>();
								}
								loadMap.put("ffoList", ffoList);
								screenVo.setTotalRvUpliftPercentage(assetDataVo.getTotalRvUpliftPercentage());


								BeanUtils.copyProperties(screenVo, assetForm);
							}

							IAssetPartExchangeVO assetPartExchangeVO = (assetDataVo
									.getAssetPartExchange() == null) ? quickQuoteService
									.getDefaultPartExchange() : assetDataVo
									.getAssetPartExchange();
							this.quickQuoteService.setAssetDetailsToContainer(0, assetForm, assetDataVo.getFfoList(), rentACarList, assetPartExchangeVO);




		}
		return assetDataVo;
	}



			}
