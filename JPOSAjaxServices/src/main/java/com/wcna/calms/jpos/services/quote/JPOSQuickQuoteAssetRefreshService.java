package com.wcna.calms.jpos.services.quote;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.common.util.Logger;
import com.wcna.calms.app.IClientConstants;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.entity.IEntityAccessService;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.calms.util.OptionLabelValue;
import com.wcna.lang.StringUtil;
import com.wcna.util.SystemException;

public class JPOSQuickQuoteAssetRefreshService extends CalmsAjaxService {

	private final IJPOSQuickQuoteAssetService quickQuoteAssetService;
	private final IJPOSQuickQuoteService quickQuoteService;
	private final IFormatService formatService;



	public JPOSQuickQuoteAssetRefreshService(
			IJPOSQuickQuoteAssetService quickQuoteAssetService,
			IJPOSQuickQuoteService quickQuoteService, IFormatService formatService) {
		this.quickQuoteAssetService = quickQuoteAssetService;
		this.quickQuoteService = quickQuoteService;
		this.formatService = formatService;

	}

	public Object invoke(Object arg0) {
		HashMap<String, Object> assetMap = new HashMap<String, Object>();
		Map<String, Object> inputMap = (Map<String, Object>) arg0;
		JPOSQuickQuoteAssetVO inputVO = new JPOSQuickQuoteAssetVO();
		String field = null;

		try {
			org.apache.commons.beanutils.BeanUtils.populate(inputVO,
					(Map) inputMap.get("assetData"));
			field = (String) inputMap.get("field");
		} catch (Exception e) {
			Logger.error(e.getMessage(),e);
		}

		IJPOSQuickQuoteAssetForm form = createBean(IJPOSQuickQuoteAssetForm.class);

		try {
			BeanUtils.copyProperties(form, inputVO);
		} catch (Exception e) {
			throw new SystemException(e);
		}

		if(!doResetFields()){
		handleChangeAction(field, inputVO, form);
		}else{
			//Store the values that are required after resetting the JPOSQuickQuoteAssetVO object
			String newUsed = inputVO.getNewUsed();
			String regCode = inputVO.getRegistrationCode();
			String makeId = inputVO.getMakeId();
			String modelId = inputVO.getModelId();
			String assetType = inputVO.getAssetType();
			String freeFormatFlag = inputVO.getFreeFormatFlag();
			String hpiCallResult = inputVO.getHpiCallResult();
			String approvedUsedAssetCode = inputVO.getApprovedUsedAssetCode();

			////

			String taxIncludingFlag = inputVO.getTaxIncludingFlag();

			////


			inputVO = new JPOSQuickQuoteAssetVO();



			//set the default values for the JPOSQuickQuoteAssetVO object

			//quickQuoteAssetService.setDefaultAmounts(inputVO, null, null, null,this.quickQuoteService.getDefaultTaxRateId(), this.quickQuoteService.getDefaultZeroTaxRateId(), false);

			JPOSQuickQuoteAssetDefaultValuesVO defaultValuesVO= new JPOSQuickQuoteAssetDefaultValuesVO();
			defaultValuesVO.setInputVO(inputVO);
			//defaultValuesVO.setDefaultTaxRateId(this.quickQuoteService.getDefaultTaxRateId());
			String screenCode = "quickQuoteAsset";//this.getScreenCode(arg0);
			defaultValuesVO.setScreenCode(screenCode);
			if("newUsed".equals(field)){
				defaultValuesVO.setDeriveTaxIncludingFlag(true);

			}else{
				defaultValuesVO.setTaxIncludingFlag(taxIncludingFlag);
				defaultValuesVO.setDeriveTaxIncludingFlag(false);
			}

			defaultValuesVO.setDefaultZeroTaxRateId(this.quickQuoteService.getDefaultZeroTaxRateId());


			if(newUsed.equals("U")){
			defaultValuesVO.setSetDefaultAssetTypeOrMake(IClientConstants.EXECUTE.equals(projectProperties.getProperty(IClientConstants.VEHICLETYPE_USED_SET_DEFAULT_ASSETTYPE_OR_MAKE,"1")));//It is required to hold the default assettype/make
			}else{
				defaultValuesVO.setSetDefaultAssetTypeOrMake(IClientConstants.EXECUTE.equals(projectProperties.getProperty(IClientConstants.VEHICLETYPE_NEW_SET_DEFAULT_ASSETTYPE_OR_MAKE,"1")));//It is required to hold the default assettype/make
			}

			defaultValuesVO.setNewUsed(newUsed);

			quickQuoteAssetService.setDefaultAmounts(defaultValuesVO);
			inputVO.setHpiCallResult(hpiCallResult);

			//Restore the values of JPOSQuickQuoteAssetVO object from the previously stored local variables
			inputVO.setNewUsed(newUsed);
			if(!("newUsed".equals(field))){
				inputVO.setRegistrationCode(regCode);
				inputVO.setAssetType(assetType);
				inputVO.setFreeFormatFlag(freeFormatFlag);

				//inputVO.setMakeId(makeId);//This is required, as we now default the manufacturer based on the brand.
			}
			if ("makeId".equals(field)) {
				inputVO.setMakeId(makeId);
			} else if ("modelId".equals(field)) {
				inputVO.setMakeId(makeId);
				inputVO.setModelId(modelId);
			}

			if(newUsed!=null && newUsed.equals("U") && hpiCallResult!=null && hpiCallResult.equals("FAIL")){
				inputVO.setFreeFormatFlag("on");
			}

			inputVO.setApprovedUsedAssetCode(approvedUsedAssetCode);
			quickQuoteAssetService.populateAssetScreenVatDropDowns(inputVO, assetMap, inputVO.getTaxIncludingFlag());




		}
		//Load all the vehicle Info
		try {
			//.. We got to repeat this as the inputVO has changed in the previous block.
			BeanUtils.copyProperties(form, inputVO);
		} catch (Exception e) {
			throw new SystemException(e);
		}
		quickQuoteAssetService.loadVehicleInfo(form);

		//Populate the makeList
		ArrayList<OptionLabelValue> makeList = new ArrayList<OptionLabelValue>();
		String[] makeKeys = form.getManufacturerKeys();
		String[] makeValues = form.getManufacturerValues();
		if (makeKeys != null && makeKeys.length > 0) {
			boolean bFound = false;
			for (int i=0; i<makeKeys.length; i++) {
				OptionLabelValue make = new OptionLabelValue(makeValues[i],
						makeKeys[i]);
				makeList.add(make);
				if (StringUtils.equals(makeKeys[i], inputVO.getMakeId())) {
					bFound = true;
				}
			}
			if (!bFound) {
				inputVO.setMakeId(null);
			}
		} else {
			inputVO.setMakeId(null);
		}

		ArrayList<OptionLabelValue> modelList = new ArrayList<OptionLabelValue>();
		ArrayList<OptionLabelValue> variantList = new ArrayList<OptionLabelValue>();

		//Populate the modelList only, if the make and the model is changed
		if ((("makeId".equals(field)) || ("modelId".equals(field))) || (!doResetFields())
		|| (inputVO.getMakeId()!=null)////This is required, as we now default the manufacturer based on the brand.
		) {

		String[] modelKeys = form.getModelKeys();
		String[] modelValues = form.getModelValues();
		if (modelKeys != null && modelKeys.length > 0) {
			boolean bFound = false;
			for (int i=0; i<modelKeys.length; i++) {
					OptionLabelValue model = new OptionLabelValue(
							modelValues[i], modelKeys[i]);
				modelList.add(model);
				if (StringUtils.equals(modelKeys[i], inputVO.getModelId())) {
					bFound = true;
				}
			}
			if(!bFound || StringUtil.isEmpty(inputVO.getMakeId())) {
				inputVO.setModelId(null);
			}
		} else {
			inputVO.setModelId(null);
		}
		}

		//Populate the variantList only, if the model is changed
		if (("modelId".equals(field)) || (!doResetFields())) {

		String[] variKeys = form.getVariantKeys();
		String[] variValues = form.getVariantValues();
			if (variKeys != null && variKeys.length > 0
					&& !StringUtil.isEmpty(inputVO.getModelId())) {
			boolean bFound = false;
			for (int i=0; i<variKeys.length; i++) {
					OptionLabelValue variant = new OptionLabelValue(
							variValues[i], variKeys[i]);
				variantList.add(variant);
					if (StringUtils.equals(variKeys[i],
							inputVO.getModelVariantId())) {
					bFound = true;
				}
			}
			if (!bFound) {
				inputVO.setModelVariantId(null);
			}
		} else {
			inputVO.setModelVariantId(null);
		}
		}

		assetMap.put("assetDetails", inputVO);
		assetMap.put("makeList", makeList);
		assetMap.put("modelList", modelList);
		assetMap.put("variantList", variantList);
		assetMap.put("registrationOptions", form.getRegistrationOptions());
		assetMap.put("assetTypes", form.getAssetTypes());
		return assetMap;
	}


	private void handleChangeAction(String field, JPOSQuickQuoteAssetVO inputVO, IJPOSQuickQuoteAssetForm form) {
		if ("newUsed".equals(field) || "registrationCode".equals(field)) {
			inputVO.setMakeId(null);
			inputVO.setModelId(null);
			inputVO.setModelVariantId(null);
		}

		if ("newUsed".equals(field) && "U".equals(inputVO.getNewUsed())) {
			Locale locale = this.getUserContainer().getLocale();
			String formatted = formatService.formatDouble(0d, locale);
			form.setExtraAmt(formatted);
			form.setExtraTaxAmt(formatted);
			form.setExtraTotalCost(formatted);

			this.quickQuoteAssetService.calcAndSetTotals(form, locale);

			inputVO.setExtraAmt(form.getExtraAmt());
			inputVO.setExtraTaxAmt(form.getExtraTaxAmt());
			inputVO.setExtraTotalCost(form.getExtraTotalCost());

			inputVO.setDiscountNet(form.getDiscountNet());
			inputVO.setDiscountVatAmt(form.getDiscountVatAmt());
			inputVO.setDiscountGross(form.getDiscountGross());

			inputVO.setTotalNet(form.getTotalNet());
			inputVO.setTotalVatAmt(form.getTotalVatAmt());
			inputVO.setTotalGross(form.getTotalGross());
		}
	}

	/**
	 * Checks if the fields in the asset screen has to be reset on change of some fields
	 * @return
	 */
	private boolean doResetFields(){
		return IClientConstants.EXECUTE.equals(projectProperties.getProperty(IClientConstants.ONCHANGE_MAKE_MODEL_IMPL_RESET_PRICING));
}
}
