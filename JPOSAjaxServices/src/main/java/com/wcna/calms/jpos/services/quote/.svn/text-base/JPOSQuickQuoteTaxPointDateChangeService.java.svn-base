package com.wcna.calms.jpos.services.quote;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.common.util.Logger;
import com.wcna.calms.data.ITaxRateDataVO;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.util.SystemException;

public class JPOSQuickQuoteTaxPointDateChangeService extends CalmsAjaxService {

	private final IFormatService formatService;
	private final IJPOSQuoteVatService quoteVatService;
	private final IJPOSQuickQuoteAssetService assetService;
	
	public JPOSQuickQuoteTaxPointDateChangeService(IFormatService formatService, IJPOSQuoteVatService quoteVatService,
			IJPOSQuickQuoteAssetService assetService) {
		this.formatService = formatService;
		this.quoteVatService = quoteVatService;
		this.assetService = assetService;
	}
	
	public Object invoke(Object arg0) {
		if (arg0 != null && arg0 instanceof Map) {
			JPOSQuickQuoteAssetVO screenVO = new JPOSQuickQuoteAssetVO();
			try {
				org.apache.commons.beanutils.BeanUtils.populate(screenVO, (Map) ((Map) arg0).get("screenData"));
			} catch (Exception e) {
				Logger.error(e.getMessage(),e);
			}
			
			String taxPointDateStr = screenVO.getTaxPointDate();
			// when the user changes the date,
			// we will retrieve a new vat list and see if the currently selected vat
			// exists in the new list (via matching the tax type code)
			// if it does not, we default to the first entry in the list
			// if it does, we use the new tax rate
			try {
				Locale locale = this.getUserContainer().getLocale();
				Date taxPointDate = StringUtils.isBlank(taxPointDateStr) ? new Date() : formatService.parseDate(screenVO.getTaxPointDate(), locale, false, new Date());
				screenVO.setTaxPointDate(formatService.formatDate(taxPointDate, locale));
				List<ITaxRateDataVO> list = quoteVatService.getTaxRateDataVOList(false, !IConstants.FLAG_YES.equals(screenVO.getTaxIncludingFlag()), taxPointDate, locale.getCountry());
				ITaxRateDataVO current = null;
				if (!StringUtils.isBlank(screenVO.getTaxCode())) {
					current = this.quoteVatService.getTaxRateDataById(Long.valueOf(screenVO.getTaxCode()));
				}
				
//				ITaxRateDataVO target = null;
				// this list should not be empty
				if (list != null && !list.isEmpty()) {
					return this.process(current, list, screenVO, locale);
				} else {
					// if no list exists for the particular date, revert to the current system date
					// the list returned from the current date should not be null
					Date d = new Date();
					screenVO.setTaxPointDate(formatService.formatDate(d, locale));
					list = quoteVatService.getTaxRateDataVOList(false, !IConstants.FLAG_YES.equals(screenVO.getTaxIncludingFlag()), d, locale.getCountry());
					return this.process(current, list, screenVO, locale);
				}
				
			} catch (ParseException e) {
				throw new SystemException(e);
			}
		}
		return null;
	}

	private Map<String, Object> process(ITaxRateDataVO current, List<ITaxRateDataVO> list, JPOSQuickQuoteAssetVO screenVO, Locale locale) {
		ITaxRateDataVO target = null;
		if (current != null) {
			for (ITaxRateDataVO vo : list) {
				if (vo.getTaxTypeCode().equals(current.getTaxTypeCode())) {
					target = vo;
					break;
				}
			}
		}
		if (target == null) {
			target = list.get(0);
		}
		screenVO.setTaxCode(target.getId());
		screenVO.setExtraTaxCode(screenVO.getTaxCode());
		screenVO.setTaxableDFOptionsVatRate(screenVO.getTaxCode());
		screenVO.setDiscountVatRate(screenVO.getTaxCode());
		
		double taxRateValue = target.getRate();
		String taxRateValueStr = formatService.formatDouble(taxRateValue, locale);
		screenVO.setTaxRateValue(taxRateValueStr);
		
		
		String net = screenVO.getSalePrice();
		String vatAmt = assetService.getVatAmountFromNet(net, taxRateValueStr, locale);
		String grossAmt = assetService.getGrossAmount(net, vatAmt, locale);
		screenVO.setTaxAmt(vatAmt);
		screenVO.setGrossCost(grossAmt);
		
		
		net = screenVO.getExtraAmt();
		vatAmt = assetService.getVatAmountFromNet(net, taxRateValueStr, locale);
		grossAmt = assetService.getGrossAmount(net, vatAmt, locale);
		screenVO.setExtraTaxAmt(vatAmt);
		screenVO.setExtraTotalCost(grossAmt);			
		
		net = screenVO.getTaxableDFOptionsNet();
		vatAmt = assetService.getVatAmountFromNet(net, taxRateValueStr, locale);
		grossAmt = assetService.getGrossAmount(net, vatAmt, locale);
		screenVO.setTaxableDFOptionsVatAmt(vatAmt);
		screenVO.setTaxableDFOptionsGross(grossAmt);			
		
		IJPOSQuickQuoteAssetForm form = createBean(IJPOSQuickQuoteAssetForm.class);
		try {
			BeanUtils.copyProperties(form, screenVO);
		} catch (Exception e) {
			throw new SystemException(e);
		}					
		
		assetService.calcAndSetTotals(form, locale);
		
		screenVO.setDiscountNet(form.getDiscountNet());
		screenVO.setDiscountVatAmt(form.getDiscountVatAmt());
		screenVO.setDiscountGross(form.getDiscountGross());		
		
		screenVO.setTotalNet(form.getTotalNet());
		screenVO.setTotalVatAmt(form.getTotalVatAmt());
		screenVO.setTotalGross(form.getTotalGross());					
		
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("screenData", screenVO);
		ret.put("vatList", this.quoteVatService.getVatListOLV(list));
		
		return ret;		
	}
	
}
