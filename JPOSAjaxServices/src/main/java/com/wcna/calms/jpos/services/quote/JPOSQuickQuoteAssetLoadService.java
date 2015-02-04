package com.wcna.calms.jpos.services.quote;

import java.beans.BeanDescriptor;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.common.util.Logger;
import com.wcna.calms.app.IClientConstants;
import com.wcna.calms.data.ITaxRateDataVO;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.common.IUserContainer;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.calms.util.OptionLabelValue;
import com.wcna.lang.StringUtil;
import com.wcna.util.SystemException;

public class JPOSQuickQuoteAssetLoadService extends CalmsAjaxService {

	private final IJPOSQuickQuoteAssetService quickQuoteAssetService;
	private final IJPOSQuoteVatService jposQuoteVatService;
	private final IFormatService formatService;
	private final JPOSQuoteUtil jposQuoteUtil;
	private final IJPOSQuickQuoteService quickQuoteService;

	public JPOSQuickQuoteAssetLoadService(IJPOSQuickQuoteAssetService quickQuoteAssetService, IJPOSQuoteVatService jposQuoteVatService,
			IFormatService formatService, JPOSQuoteUtil jposQuoteUtil,  IJPOSQuickQuoteService quickQuoteService) {
		this.quickQuoteAssetService = quickQuoteAssetService;
		this.jposQuoteVatService = jposQuoteVatService;
		this.formatService = formatService;
		this.jposQuoteUtil = jposQuoteUtil;
		this.quickQuoteService = quickQuoteService;
	}

	public Object invoke(Object arg0) {
		HashMap assetMap = new HashMap();

		int quoteIdx = this.jposQuoteUtil.getQuoteIdx((Map) arg0);

		Map<String, Object> inputMap = (Map<String, Object>) arg0;
		String invokedByPricesScr="0";
		Locale locale = getUserContainer().getLocale();
		try {
		if(inputMap.get("invokedByPricesScr")!=null){
		invokedByPricesScr = (String) inputMap.get("invokedByPricesScr");
		}
		}catch(Exception e){
			Logger.error(e.getMessage(),e);
		}
		////////////--load for prices-start/////////
		if(invokedByPricesScr.equals("1")){
		String appId = this.getAppContainer() == null ? "" : this.getAppContainer().getAppID() <= 0 ? "" : this.getAppContainer().getAppID() + "";
		IJPOSApplicationAssetDataVO assetDataVo = null;
		quickQuoteService.initializeQuoteContainer();
		//IJPOSQuoteDataVO quoteVo = quickQuoteService.loadQuote(Long.valueOf(appId), locale);
		Map<String, Object> loadMap = new HashMap<String, Object>();
		assetDataVo= loadAssetSubProcess(assetDataVo,appId, loadMap);
		}

		//////////---load for prices - end /////////////



		String screenCode = this.getScreenCode(arg0);
//		System.err.println("=========>JPOSQuickQuoteAssetLoadService.invoke ((1.1)) screenCode=" + screenCode);
		JPOSQuickQuoteAssetVO assetVO = new JPOSQuickQuoteAssetVO();

		Logger.debug("getting asset info for quoteIdx = " + quoteIdx);

		IJPOSQuickQuoteAssetInputForm existingAssetVO = this.quickQuoteAssetService.getAssetForm(quoteIdx);
		Set<String> noAccessFields = this.entityAccessService.getNoAccessFields(screenCode);




		if (existingAssetVO == null){
			Logger.debug("got an existing asset VO which is NULL");

		} else {
			Logger.debug("got an existing asset VO with contents: "
					+"; DiscountVatAmt: "+existingAssetVO.getDiscountVatAmt()
					+"; DiscountVatRate: "+existingAssetVO.getDiscountVatRate()
					+"; MakeId: "+existingAssetVO.getMakeId()
					+"; ModelId: "+existingAssetVO.getModelId()
					);
		}



		//Copy all properties
		BeanUtils.copyProperties(existingAssetVO, assetVO);

		//Copy all properties from assetVO to form
		IJPOSQuickQuoteAssetForm form = createBean(IJPOSQuickQuoteAssetForm.class);
		BeanUtils.copyProperties(assetVO, form);

		//Copy only properties that only system can access to VO for display
		assetVO = new JPOSQuickQuoteAssetVO();
		BeanUtils.copyProperties(existingAssetVO, assetVO, noAccessFields.toArray(new String[noAccessFields.size()]));

		quickQuoteAssetService.loadVehicleInfo(form);

		ArrayList makeList = new ArrayList();
		String[] makeKeys = form.getManufacturerKeys();
		String[] makeValues = form.getManufacturerValues();
		if (makeKeys != null) {
			for (int i=0; i<makeKeys.length; i++) {
				OptionLabelValue make = new OptionLabelValue(makeValues[i], makeKeys[i]);
				makeList.add(make);
			}
		}
		ArrayList modelList = new ArrayList();
		String[] modelKeys = form.getModelKeys();
		String[] modelValues = form.getModelValues();
		if (modelKeys != null) {
			for (int i=0; i<modelKeys.length; i++) {
				OptionLabelValue model = new OptionLabelValue(modelValues[i], modelKeys[i]);
				modelList.add(model);
			}
		}
		ArrayList variantList = new ArrayList();
		String[] variKeys = form.getVariantKeys();
		String[] variValues = form.getVariantValues();
		if (variKeys != null) {
			for (int i=0; i<variKeys.length; i++) {
				OptionLabelValue variant = new OptionLabelValue(variValues[i], variKeys[i]);
				variantList.add(variant);
			}
		}

//		JPOSQuickQuoteAssetVO quoteAssetData = new JPOSQuickQuoteAssetVO();
//		BeanUtils.copyProperties(form, quoteAssetData,
//				new String[]{"manufacturerKeys", "manufacturerValues", "modelKeys", "modelValues", "variantKeys", "variantValues", "registrationOptions"});


//		assetMap.put("assetDetails", quoteAssetData);
		assetMap.put("assetTypes", form.getAssetTypes());
//		assetMap.put("vatRateTypes", form.getVatRateTypes());
		assetMap.put("makeList", makeList);
		assetMap.put("modelList", modelList);
		assetMap.put("variantList", variantList);
//		assetMap.put("options", form.getOptionAccessories());
		assetMap.put("registrationOptions", form.getRegistrationOptions());
		if (!"on".equals(assetVO.getFreeFormatFlag())
				&& !StringUtils.isBlank(assetVO.getModelVariantId())) {
			assetMap.put("originalPriceAmt", this.quickQuoteAssetService.getPriceAmtForVariant(Long.valueOf(assetVO.getModelVariantId())));
		}
		IUserContainer userContainer = this.getUserContainer();
		boolean isRentACarAllowed = this.quickQuoteAssetService.isRentACarAllowed(Long.valueOf(getUserContainer().getRoleID()));
		assetMap.put("isRentACarAvailable", isRentACarAllowed ? IConstants.FLAG_YES : IConstants.FLAG_NO);

		try {


			String taxIncludingFlag ="";
			//It is important to use deriveTaxIncludingFlag() , because when the taxincludingflag is hidden, it does get removed from the assetVO.

			if(assetVO.getTotalGross()!=null && formatService.parseDouble(assetVO.getTotalGross(), locale, false, 0d)>0){//Quote/Asset Exist, Assume to TotalGross will never be zero.
			taxIncludingFlag = quickQuoteAssetService.deriveTaxIncludingFlag("quickQuoteAsset", false, assetVO.getTaxIncludingFlag(), assetVO.getNewUsed());
			}else{//new Quote/Asset
				taxIncludingFlag = quickQuoteAssetService.deriveTaxIncludingFlag("quickQuoteAsset", true, assetVO.getTaxIncludingFlag(), assetVO.getNewUsed());
			}

			Date taxPointDate = formatService.parseDate(assetVO.getTaxPointDate(), this.getUserContainer().getLocale(), false, new Date());

			quickQuoteAssetService.populateAssetScreenVatDropDowns(assetVO, assetMap, taxIncludingFlag);

			if (assetVO == null){
				Logger.debug("got an assetVO which is NULL");

			} else {
				Logger.debug("got an assetVO with contents "
						+"; TaxCode: "+assetVO.getTaxCode()
						+"; TaxAmt: "+assetVO.getTaxAmt()
						+"; TaxRateValue: "+assetVO.getTaxRateValue()
						+"; SalePrice: "+assetVO.getSalePrice()
						);
			}

			// this should not be null
			Logger.debug("About to call jposQuoteVatService.getTaxRateDataById ");
			ITaxRateDataVO savedTaxRateVO = this.jposQuoteVatService.getTaxRateDataById(Long.valueOf(assetVO.getTaxCode()));
			double currentTaxRate = this.jposQuoteVatService.getTaxRate(savedTaxRateVO.getTaxTypeCode(), taxPointDate, userContainer.getCountryCode());
			double savedTaxRate = formatService.parseDouble(assetVO.getTaxRateValue(), userContainer.getLocale(), false, 0d);
			// compare the saved tax rate against the current rate for the tax code
			if (currentTaxRate != savedTaxRate) {
				List<ITaxRateDataVO> currentList = this.jposQuoteVatService.getTaxRateDataVOList(false, !IConstants.FLAG_YES.equals(taxIncludingFlag), taxPointDate, userContainer.getCountryCode());
				// should not be null
				if (currentList != null) {
					ITaxRateDataVO target = null;
					for (ITaxRateDataVO v : currentList) {
						if (v.getTaxTypeCode().equals(savedTaxRateVO.getTaxTypeCode())) {
							target = v;
							break;
						}
					}
					// in the case that this particular tax is not valid at this point in time,
					// we will default to the first one in the list
					if (target == null) {
						target = currentList.get(0);
					}
					assetVO.setTaxCode(target.getId());
					assetVO.setExtraTaxCode(assetVO.getTaxCode());
					assetVO.setTaxableDFOptionsVatRate(assetVO.getTaxCode());
					assetVO.setDiscountVatRate(assetVO.getTaxCode());
					assetVO.setTaxRateValue(formatService.formatDouble(target.getRate(), userContainer.getLocale()));
					//locale = userContainer.getLocale();

					String net = assetVO.getSalePrice();
					String vatAmt = quickQuoteAssetService.getVatAmountFromNet(net, assetVO.getTaxRateValue(), locale);
					String grossAmt = quickQuoteAssetService.getGrossAmount(net, vatAmt, locale);
					assetVO.setTaxAmt(vatAmt);
					assetVO.setGrossCost(grossAmt);
					form.setTaxAmt(assetVO.getTaxAmt());
					form.setGrossCost(assetVO.getGrossCost());

					net = assetVO.getExtraAmt();
					vatAmt = quickQuoteAssetService.getVatAmountFromNet(net, assetVO.getTaxRateValue(), locale);
					grossAmt = quickQuoteAssetService.getGrossAmount(net, vatAmt, locale);
					assetVO.setExtraTaxAmt(vatAmt);
					assetVO.setExtraTotalCost(grossAmt);
					form.setExtraTaxAmt(assetVO.getExtraTaxAmt());
					form.setExtraTotalCost(assetVO.getExtraTotalCost());

					net = assetVO.getTaxableDFOptionsNet();
					vatAmt = quickQuoteAssetService.getVatAmountFromNet(net, assetVO.getTaxRateValue(), locale);
					grossAmt = quickQuoteAssetService.getGrossAmount(net, vatAmt, locale);
					assetVO.setTaxableDFOptionsVatAmt(vatAmt);
					assetVO.setTaxableDFOptionsGross(grossAmt);
					form.setTaxableDFOptionsVatAmt(assetVO.getTaxableDFOptionsVatAmt());
					form.setTaxableDFOptionsGross(assetVO.getTaxableDFOptionsGross());


					quickQuoteAssetService.calcAndSetTotals(form, locale);

					assetVO.setDiscountNet(form.getDiscountNet());
					assetVO.setDiscountVatAmt(form.getDiscountVatAmt());
					assetVO.setDiscountGross(form.getDiscountGross());

					assetVO.setTotalNet(form.getTotalNet());
					assetVO.setTotalVatAmt(form.getTotalVatAmt());
					assetVO.setTotalGross(form.getTotalGross());

					assetMap.put("taxRateChange", "Y");
				}

			}
			assetMap.put("screenData", assetVO);
			assetMap.put("ffoList", this.quickQuoteAssetService.getFFOList(quoteIdx));
			List<IRentACarBean> rentACarList = this.quickQuoteAssetService.getRentACarList(quoteIdx);
			if (rentACarList != null && !rentACarList.isEmpty()) {
				assetMap.put("rentACarList", rentACarList);
			}
		} catch (ParseException e) {
			throw new SystemException(e);
		}

		return assetMap;
	}


	/***
	 * This is used by the prices screen which opens from the proposal summary screen.
	 * @param assetDataVo
	 * @param appId
	 * @param loadMap
	 * @return
	 */
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
