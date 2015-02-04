package com.wcna.calms.jpos.services.quote;

import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.common.util.Logger;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.util.SystemException;

public class JPOSQuickQuoteAssetTotalPriceChangeService extends
		CalmsAjaxService {

	private final IJPOSQuickQuoteAssetService quickQuoteAssetService;
	private final IFormatService formatService;

	public JPOSQuickQuoteAssetTotalPriceChangeService(
			IJPOSQuickQuoteAssetService quickQuoteAssetService, IFormatService formatService) {
		this.quickQuoteAssetService = quickQuoteAssetService;
		this.formatService = formatService;
	}

	public Object invoke(Object arg0) {
		if (arg0 != null && arg0 instanceof Map) {
			Map<String, String> inputMap = (Map) arg0;

			JPOSQuickQuoteAssetVO inputVO = new JPOSQuickQuoteAssetVO();

			try {
				org.apache.commons.beanutils.BeanUtils.populate(inputVO,
						inputMap);
			} catch (Exception e) {
				Logger.error(e.getMessage(), e);
			}

			Locale locale = this.getUserContainer().getLocale();

			IJPOSQuickQuoteAssetForm form = createBean(IJPOSQuickQuoteAssetForm.class);
			try {
				BeanUtils.copyProperties(form, inputVO);
			} catch (Exception e) {
				throw new SystemException(e);
			}

			quickQuoteAssetService.calculateDiscountAmounts(form, locale);

			inputVO.setDiscountNet(form.getDiscountNet());
			inputVO.setDiscountVatAmt(form.getDiscountVatAmt());
			inputVO.setDiscountGross(form.getDiscountGross());
			Double totalGrossFmt = formatService.parseDouble(form.getTotalGross(), locale, false, 0d);
			inputVO.setTotalGross(formatService.formatDouble(totalGrossFmt, locale));
			return inputVO;
		}
		return null;
	}

}
