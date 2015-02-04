package com.wcna.calms.jpos.services.quote;

//import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.BooleanUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.common.util.Logger;
import com.wcna.calms.app.IClientConstants;
import com.wcna.calms.data.ITaxRateDataVO;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.calms.service.quote.IQuoteVatService;
import com.wcna.calms.util.OptionLabelValue;
import com.wcna.lang.StringUtil;
import com.wcna.util.SystemException;

public class JPOSQuickQuoteAssetTaxIncludeChangeService extends CalmsAjaxService {

	private final IJPOSQuickQuoteAssetService quickQuoteAssetService;
	private final IJPOSQuoteVatService quoteVatService;
	private final IFormatService formatService;
	private final IJPOSQuickQuoteService jposQuickQuoteService;
	private final IJPOSQuickQuoteAssetCalculationService jposQuickQuoteCalculationService;

	public JPOSQuickQuoteAssetTaxIncludeChangeService(IJPOSQuickQuoteAssetService quickQuoteAssetService, IJPOSQuoteVatService quoteVatService, IFormatService formatService,
			IJPOSQuickQuoteService jposQuickQuoteService, IJPOSQuickQuoteAssetCalculationService jposQuickQuoteCalculationService) {
		this.quickQuoteAssetService = quickQuoteAssetService;
		this.quoteVatService = quoteVatService;
		this.formatService = formatService;
		this.jposQuickQuoteService = jposQuickQuoteService;
		this.jposQuickQuoteCalculationService = jposQuickQuoteCalculationService;
	}

	public Object invoke(Object arg0) {
		if (arg0 != null && arg0 instanceof Map) {
			Map inputMap = (Map) arg0;
			JPOSQuickQuoteAssetVO inputVO = new JPOSQuickQuoteAssetVO();

			try {
				org.apache.commons.beanutils.BeanUtils.populate(inputVO, inputMap);
			} catch (Exception e) {
				Logger.error(e.getMessage(),e);
			}

			boolean bVat = IConstants.FLAG_YES.equals(inputVO.getTaxIncludingFlag());

			Locale locale = this.getUserContainer().getLocale();
			List<OptionLabelValue> vatList;
			try {
				vatList = this.quoteVatService.getVatListOLV(!bVat, formatService.parseDate(inputVO.getTaxPointDate(), locale, false, new Date()));
			} catch (ParseException e1) {
				throw new SystemException(e1);
			}


			List<OptionLabelValue> vatListAllValues;
			try {

				vatListAllValues = this.quoteVatService.getVatListOLV(formatService.parseDate(inputVO.getTaxPointDate(), locale, false, new Date()));
			} catch (ParseException e1) {
				throw new SystemException(e1);
			}


			// TODO, return this list

			if (vatList != null && !vatList.isEmpty()) {
				inputVO.setTaxCode(vatList.get(0).getValue());
			} else {
				inputVO.setTaxCode(null);
			}
			inputVO.setExtraTaxCode(inputVO.getTaxCode());
			inputVO.setTaxableDFOptionsVatRate(inputVO.getTaxCode());
			inputVO.setDiscountVatRate(inputVO.getTaxCode());
			// basic price
			String vatRate = "0";
			if (inputVO.getTaxCode() != null) {
//				OptionLabelValue olv = (OptionLabelValue) quoteVatService.loadVatRateTypeTable().get(inputVO.getTaxCode().split(";")[1]);
//				vatRate = olv.getValue().split(";")[0];
//				vatRate = quoteVatService.getVatRateById(Long.valueOf(inputVO.getTaxCode()));
				ITaxRateDataVO taxRateData = quoteVatService.getTaxRateDataById(Long.valueOf(inputVO.getTaxCode()));
				if (taxRateData != null) {
					vatRate = formatService.formatDouble(taxRateData.getRate(), locale);
				}
			}

			inputVO.setTaxRateValue(vatRate);

			boolean defVehTaxDFOptVatRateDDListSyncWithListPriVat =BooleanUtils.toBoolean(projectProperties.getProperty(IClientConstants.DEFAULT_VEHICLE_TAXDFOPTIONS_VATRATEDROPDOWN_SYNCWITH_LISTPRICE_VAT, "true"));
			boolean defVehTaxDFOptVatRateDDListZeroAndNonZero= IClientConstants.EXECUTE.equals(projectProperties.getProperty(IClientConstants.DEFAULT_VEHICLE_TAXDFOPTIONS_VATRATEDROPDOWN_LISTZEROANDNONZERO,"0"));
			boolean onChangeFFoFieldsImplCalcGross =BooleanUtils.toBoolean(projectProperties.getProperty(IClientConstants.ONCHANGE_FFOFIELDS_IMPL_CALCGROSS, "false"));



			if (BooleanUtils.toBoolean(projectProperties.getProperty(IClientConstants.ONCHANGE_VATRATE_SALEPRICE_IMPL_CALCNET)))
			{
				jposQuickQuoteCalculationService.preCalculateNet(inputVO, locale, defVehTaxDFOptVatRateDDListZeroAndNonZero, onChangeFFoFieldsImplCalcGross,defVehTaxDFOptVatRateDDListSyncWithListPriVat);
			} else {
				//jposQuickQuoteCalculationService.preCalculate(inputVO, field, row, locale, defVehTaxDFOptVatRateDDListZeroAndNonZero, onChangeFFoFieldsImplCalcGross);
				jposQuickQuoteCalculationService.preCalculateGross(inputVO,
						 locale, defVehTaxDFOptVatRateDDListZeroAndNonZero, onChangeFFoFieldsImplCalcGross, defVehTaxDFOptVatRateDDListSyncWithListPriVat);
			}


			IJPOSQuickQuoteAssetForm form = createBean(IJPOSQuickQuoteAssetForm.class);
			try {
				BeanUtils.copyProperties(form, inputVO);
			} catch (Exception e) {
				throw new SystemException(e);
			}

			quickQuoteAssetService.calcAndSetTotals(form, locale);

			inputVO.setDiscountNet(form.getDiscountNet());
			inputVO.setDiscountVatAmt(form.getDiscountVatAmt());
			inputVO.setDiscountGross(form.getDiscountGross());

			inputVO.setTotalNet(form.getTotalNet());
			inputVO.setTotalVatAmt(form.getTotalVatAmt());
			inputVO.setTotalGross(form.getTotalGross());

			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("screenData", inputVO);
			ret.put("vatList", vatList);


			if(defVehTaxDFOptVatRateDDListZeroAndNonZero){
				//ret.put("vatListAllValues", vatListAllValues);
				ret.put("vatListTaxableDFOptions", vatListAllValues);
			}else{
				ret.put("vatListTaxableDFOptions", vatList);
			}




			return ret;
		}
		return null;
	}

}
