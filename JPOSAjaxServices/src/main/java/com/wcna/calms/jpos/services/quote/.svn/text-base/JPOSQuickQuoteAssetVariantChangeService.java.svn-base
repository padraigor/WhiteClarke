package com.wcna.calms.jpos.services.quote;

//import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.common.util.Logger;
import com.wcna.calms.app.IClientConstants;
import com.wcna.calms.service.asset.ICatalogModelVariantData;
import com.wcna.calms.service.asset.IModelService;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.calms.web.IConstantsWeb;
import com.wcna.util.SystemException;

public class JPOSQuickQuoteAssetVariantChangeService extends CalmsAjaxService {

	private final IJPOSQuickQuoteAssetService quickQuoteAssetService;
	private final IJPOSQuoteVatService quoteVatService;
	private final IModelService modelService;
	private final IFormatService formatService;
	private final IJPOSQuickQuoteAssetCalculationService jposQuickQuoteCalculationService;

	public JPOSQuickQuoteAssetVariantChangeService(IJPOSQuickQuoteAssetService quickQuoteAssetService, IJPOSQuoteVatService quoteVatService,
								                   IModelService modelService, IFormatService formatService,
								                   IJPOSQuickQuoteAssetCalculationService jposQuickQuoteCalculationService) {
		this.quickQuoteAssetService = quickQuoteAssetService;
		this.quoteVatService = quoteVatService;
		this.modelService = modelService;
		this.formatService = formatService;
		this.jposQuickQuoteCalculationService=jposQuickQuoteCalculationService;
	}

	public Object invoke(Object arg0) {
		if (arg0 != null && arg0 instanceof Map) {
			Map inputMap = (Map) arg0;

			JPOSQuickQuoteAssetVO assetVO = new JPOSQuickQuoteAssetVO();
			try {
				org.apache.commons.beanutils.BeanUtils.populate(assetVO, inputMap);
			} catch (Exception e) {
				Logger.error(e.getMessage(),e);
			}

			IJPOSQuickQuoteAssetForm form = createBean(IJPOSQuickQuoteAssetForm.class);
//			form.setModelVariantId((String) inputMap.get("modelVariantId"));
//			form.setTaxCode((String) inputMap.get("taxCode"));

			try {
				BeanUtils.copyProperties(form, assetVO);
			} catch (Exception e) {
				throw new SystemException(e);
			}

//			String vatQualifying = (String) inputMap.get("taxIncludingFlag");
			String vatQualifying = form.getTaxIncludingFlag();
			String vatRate = "0";
			if (IConstants.FLAG_YES.equals(vatQualifying)) {
//				OptionLabelValue olv = (OptionLabelValue) quoteVatService.loadVatRateTypeTable().get(form.getTaxCode().split(";")[1]);
//				vatRate = olv.getValue().split(";")[0];
//				vatRate = quoteVatService.getVatRateById(Long.valueOf(form.getTaxCode()));
				vatRate = assetVO.getTaxRateValue();
			}

			String enviroImpactRatingCode  = null; //catalogModelVariantData.getEnviroImpactRatingCode();
			Locale locale = this.getUserContainer().getLocale();



			 //public void populateAssetPrice(IJPOSQuickQuoteAssetForm form, String vatRate )
			jposQuickQuoteCalculationService.populateAssetPrice(form, vatRate, locale);
			quickQuoteAssetService.calcAndSetTotals(form, locale);

			form.setEnviroImpactRatingCode(enviroImpactRatingCode);


			Map<String, String> rtnMap = new HashMap<String, String>();

		    rtnMap.put("vatAmtRFL", form.getRoadFundLicenseVatAmt());
		    rtnMap.put("grossRFL", form.getRoadFundLicenseGross());
			rtnMap.put("netRFL", form.getRoadFundLicenseNet());


			rtnMap.put("vatAmtFirstRegFee", form.getFirstRegistrationVatAmt());
			rtnMap.put("grossFirstRegFee", form.getFirstRegistrationGross());
			rtnMap.put("netFirstRegFee", form.getFirstRegistrationNet());





			rtnMap.put("vatAmt", form.getTaxAmt());
			rtnMap.put("gross", form.getGrossCost());
			rtnMap.put("net", form.getSalePrice());


			rtnMap.put("totalNet", form.getTotalNet());
			rtnMap.put("totalVatAmt", form.getTotalVatAmt());
			rtnMap.put("totalGross", form.getTotalGross());

			rtnMap.put("discountGross", form.getDiscountGross());
			rtnMap.put("discountNet", form.getDiscountNet());
			rtnMap.put("discountVatAmt", form.getDiscountVatAmt());

			rtnMap.put("enviroImpactRatingCode", form.getEnviroImpactRatingCode());

			rtnMap.put("lctSummary", form.getLctSummary());
			rtnMap.put("gstSummary", form.getGstSummary());

             /////////////

			rtnMap.put("extraAmt", form.getExtraAmt());
			rtnMap.put("extraTaxAmt", form.getExtraTaxAmt());
			rtnMap.put("extraTotalCost", form.getExtraTotalCost());

			rtnMap.put("taxableDFOptionsNet", form.getTaxableDFOptionsNet());
			rtnMap.put("taxableDFOptionsVatAmt", form.getTaxableDFOptionsVatAmt());
			rtnMap.put("taxableDFOptionsGross", form.getTaxableDFOptionsGross());

			rtnMap.put("nonTaxableDFOptionsNet", form.getNonTaxableDFOptionsNet());
			rtnMap.put("nonTaxableDFOptionsVatAmt", form.getNonTaxableDFOptionsVatAmt());
			rtnMap.put("nonTaxableDFOptionsGross", form.getNonTaxableDFOptionsGross());



			return rtnMap;
		}

		return null;
	}


}
