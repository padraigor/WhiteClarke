package com.wcna.calms.jpos.services.quote;

import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.BooleanUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.common.util.Logger;
import com.wcna.calms.app.IClientConstants;
import com.wcna.calms.data.ITaxRateDataVO;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.lang.StringUtil;
import com.wcna.util.SystemException;

public class JPOSQuickQuoteCostRefreshService extends CalmsAjaxService {

	private final IJPOSQuickQuoteAssetService quickQuoteAssetService;
	private final IJPOSQuoteVatService quoteVatService;
	private final IJPOSQuickQuoteService jposQuickQuoteService;
	private final IFormatService formatService;
	private final IJPOSQuickQuoteAssetCalculationService jposQuickQuoteCalculationService;

	public JPOSQuickQuoteCostRefreshService(
			IJPOSQuickQuoteAssetService quickQuoteAssetService,
			IJPOSQuoteVatService quoteVatService,
			IJPOSQuickQuoteService jposQuickQuoteService,
			IJPOSQuickQuoteAssetCalculationService jposQuickQuoteCalculationService,
			IFormatService formatService) {
		this.quickQuoteAssetService = quickQuoteAssetService;
		this.quoteVatService = quoteVatService;
		this.jposQuickQuoteService = jposQuickQuoteService;
		this.jposQuickQuoteCalculationService = jposQuickQuoteCalculationService;
		this.formatService = formatService;
	}

	public Object invoke(Object arg0) {
		if (arg0 != null && arg0 instanceof Map) {
			Map inputMap = (Map) arg0;
			String field = (String) inputMap.get("the_field");
			String row = (String) inputMap.get("the_row");

			JPOSQuickQuoteAssetVO inputVO = new JPOSQuickQuoteAssetVO();

			try {
				org.apache.commons.beanutils.BeanUtils.populate(inputVO,
						inputMap);
			} catch (Exception e) {
				Logger.error(e.getMessage(), e);
			}

			inputVO.setExtraTaxCode(inputVO.getTaxCode());


			boolean defVehTaxDFOptVatRateDDListZeroAndNonZero= IClientConstants.EXECUTE.equals(projectProperties.getProperty(IClientConstants.DEFAULT_VEHICLE_TAXDFOPTIONS_VATRATEDROPDOWN_LISTZEROANDNONZERO,"0"));
			boolean defVehTaxDFOptVatRateDDListSyncWithListPriVat =BooleanUtils.toBoolean(projectProperties.getProperty(IClientConstants.DEFAULT_VEHICLE_TAXDFOPTIONS_VATRATEDROPDOWN_SYNCWITH_LISTPRICE_VAT, "true"));
			boolean onChangeFFoFieldsImplCalcGross =BooleanUtils.toBoolean(projectProperties.getProperty(IClientConstants.ONCHANGE_FFOFIELDS_IMPL_CALCGROSS, "false"));

			if(defVehTaxDFOptVatRateDDListSyncWithListPriVat){
				inputVO.setTaxableDFOptionsVatRate(inputVO.getTaxCode());
			}

			inputVO.setDiscountVatRate(inputVO.getTaxCode());

			Locale locale = this.getUserContainer().getLocale();



			if (BooleanUtils.toBoolean(projectProperties.getProperty(IClientConstants.ONCHANGE_VATRATE_SALEPRICE_IMPL_CALCNET)))
			{
				jposQuickQuoteCalculationService.preCalculateNet(inputVO, locale, defVehTaxDFOptVatRateDDListZeroAndNonZero, onChangeFFoFieldsImplCalcGross, defVehTaxDFOptVatRateDDListSyncWithListPriVat);
			} else {
				jposQuickQuoteCalculationService.preCalculate(inputVO, field, row, locale, defVehTaxDFOptVatRateDDListZeroAndNonZero, onChangeFFoFieldsImplCalcGross, defVehTaxDFOptVatRateDDListSyncWithListPriVat);
			}


			IJPOSQuickQuoteAssetForm form = createBean(IJPOSQuickQuoteAssetForm.class);
			try {
				BeanUtils.copyProperties(form, inputVO);
			} catch (Exception e) {
				throw new SystemException(e);
			}

			if (IClientConstants.EXECUTE
							.equals(projectProperties
									.getProperty(IClientConstants.ONCHANGE_SALEPRICE_IMPL_GROSS_DISCOUNT_ZERO))) {
				if("grossCost".equals(field)){
					form.setDiscountGross("0.0");
				}
				quickQuoteAssetService.calcAndSetTotals(form, locale, true);
			} else {
				quickQuoteAssetService.calcAndSetTotals(form, locale);
			}

			inputVO.setDiscountNet(form.getDiscountNet());
			inputVO.setDiscountVatAmt(form.getDiscountVatAmt());
			inputVO.setDiscountGross(form.getDiscountGross());

			inputVO.setTotalNet(form.getTotalNet());
			inputVO.setTotalVatAmt(form.getTotalVatAmt());
			inputVO.setTotalGross(form.getTotalGross());

			inputVO.setLctSummary(form.getLctSummary());
			inputVO.setGstSummary(form.getGstSummary());

			return inputVO;
		}
		return null;
	}



			}
