package com.wcna.calms.jpos.services.quote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.product.calms.security.CalmsSessionData;
import com.wcg.product.services.session.SessionManager;
import com.wcna.calms.service.asset.IApplicationAssetDataVO;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.calms.service.quote.IQuoteDataVO;
import com.wcna.calms.util.OptionLabelValue;
import com.wcna.lang.StringUtil;
import com.wcna.util.SystemException;

public class JPOSQuickQuotePlanRefreshService extends JPOSBasePlanAlterService {

	private final IJPOSQuickQuoteService jposQuickQuoteService;
	private final IFormatService formatService;
	private final JPOSQuoteUtil jposQuoteUtil;

	public JPOSQuickQuotePlanRefreshService(IJPOSQuickQuoteService jposQuickQuoteService, IFormatService formatService, JPOSQuoteUtil jposQuoteUtil) {
		super(formatService, jposQuickQuoteService);
		this.jposQuickQuoteService = jposQuickQuoteService;
		this.formatService = formatService;
		this.jposQuoteUtil = jposQuoteUtil;
	}

	public Object invoke(Object arg0) {
		if (arg0 != null && arg0 instanceof Map) {
			Map in = (Map) arg0;
			int quoteIdx = jposQuoteUtil.getQuoteIdx(in);
//			String quoteIdx = (String) in.get("quoteIdx");
//			if (StringUtils.isEmpty(quoteIdx)) {
//				return null;
//			}
//
//			IQuoteDataVO quote = ((CalmsSessionData) SessionManager.getInstance().getSessionData()).getQuoteContainer().getQuote(Integer.valueOf(quoteIdx).intValue());
//			if (quote == null || quote.getAssets() == null || quote.getAssets().isEmpty()) {
//				return null;
//			}
//			IRentACarBean rentACarBean = null;
//			int rentACarSize = 0;
//			Map<String, Object> v_in = (Map<String, Object>) in.get("vehicle");
//			if (v_in.containsKey("rentACarList")) {
//				List<Map<String, Object>> rentACarList = (List<Map<String, Object>>) v_in.get("rentACarList");
//				if (rentACarList != null && !rentACarList.isEmpty()) {
//					rentACarSize = rentACarList.size();
//					Map<String, Object> rentACarMap = rentACarList.get(0);
//					if (rentACarMap != null) {
//						rentACarBean = this.createBean(IRentACarBean.class);
//						try {
//							org.apache.commons.beanutils.BeanUtils.populate(rentACarBean, rentACarMap);
//						} catch (Exception e) {
//							throw new SystemException(e);
//						}
//					}
//				}
//			}
			IJPOSQuickQuoteAssetInputForm assetForm = this.jposQuickQuoteService.getAssetInputForm(quoteIdx);
			IRentACarBean rentACarBean = this.jposQuickQuoteService.getCommonRentACarBean(quoteIdx);

			String planId = (String) in.get("planId");
			String expiredPlanId = (String) in.get("expiredPlanId");
			// we can only retrieve the appropriate plans once a vehicle has been selected
//			IApplicationAssetDataVO asset = (IApplicationAssetDataVO) quote.getAssets().iterator().next();
//			if (asset != null && asset.getModelVariantData() != null) {
				// obviously these parameters will change, just hard coded for now
//				int dealerId = 7055239;
			String variant = assetForm.getModelVariantId();
			String ffe = assetForm.getFreeFormatFlag();
//			int dealerId = 7046677;
//			int dealerId = 7043440;

//			int dagId = formatService.parseInteger(this.getUserContainer().getCurrentDagID(), this.getUserContainer().getLocale(), false, 0);
//			int dealerId = jposQuickQuoteService.getDealerIdFromDealerDagId(dagId);
//			int jtrId = jposQuickQuoteService.getJtrIdFromDealerDagId(dagId);
//			int brokerBrandId = formatService.parseInteger(this.getUserContainer().getCurrentBrandCode(), this.getUserContainer().getLocale(), false, 0);

			long modelVariantId = 0;

			if (rentACarBean != null) {
				modelVariantId = rentACarBean.getVariantId();
			} else if (!"on".equals(ffe)) {
				modelVariantId = formatService.parseLong(variant, this.getUserContainer().getLocale(), false, 0L);

			}



			String customerTypeCode = this.jposQuickQuoteService.getCustomerData().getCustomerType();

			List<IPlan> plans = jposQuickQuoteService.getAllPlansForDealer(assetForm, this.getUserContainer().getLocale(), (String) in.get("promoCode"), customerTypeCode, rentACarBean);

			Map<String, Object> rtnMap = new HashMap<String, Object>();

			boolean bFound = false;
			boolean isPlansEmpty = plans == null || plans.isEmpty();

			if (!isPlansEmpty) {
				List<OptionLabelValue> out = new ArrayList<OptionLabelValue>();
				int size = plans.size();
				for (int i = 0; i < size; i++) {
					IPlan p = plans.get(i);
					OptionLabelValue olv = new OptionLabelValue(p.getPlanType() + " " + p.getPlanSubType(), p.getPlanId());
					out.add(olv);
					if (p.getPlanId().equals(planId)) {
						bFound = true;
					}
				}

//				return out;
				rtnMap.put("planList", out);
			}

			rtnMap.put("currentPlanFound", bFound);
			IFinanceProduct newSelProduct = null;

			if (!StringUtil.isEmpty(planId)) {
				IFinanceProduct fp = jposQuickQuoteService.getFinanceProduct(planId, getUserContainer().getLocale(), customerTypeCode, modelVariantId);
				if (!isPlansEmpty && bFound) {
					newSelProduct = fp;
				}
				boolean bProductExpired = false;
				if (fp == null || !StringUtil.isEmpty(expiredPlanId)) {
//					throw new SystemException("Cannot load FinanceProduct->" + planId);
					if (fp == null) {
						bProductExpired = true;
					}
					List<OptionLabelValue> out = new ArrayList<OptionLabelValue>();
					if (!isPlansEmpty) {
						int size = plans.size();
						for (int i = 0; i < size; i++) {
							IPlan p = plans.get(i);
							OptionLabelValue olv = new OptionLabelValue(p.getPlanType() + " " + p.getPlanSubType(), p.getPlanId());
							out.add(olv);
						}
					}
					IPlan plan = jposQuickQuoteService.getPlan(fp == null ? planId : expiredPlanId);
					if (plan != null) {
						// only add the expired product to the list if the plan type (i.e. promo/standard) has not changed
						String _promoCode = (String) in.get("promoCode");
						boolean isPromo = IJPOSQuickQuoteConstants.PROMO_PRODUCT_PROMO.equals(_promoCode);
						if (isPromo == plan.isPromo()) {
							out.add(new OptionLabelValue(plan.getPlanType() + " " + plan.getPlanSubType() + " - Read Only", plan.getPlanId()));
						}
					}
					rtnMap.put("listWithExpiredItem", out);
					if (fp == null) {
						rtnMap.put("productExpired", IConstants.FLAG_YES);
					} else {
						rtnMap.put("listContainsExpiredItem", IConstants.FLAG_YES);
					}
				}

				if (!bProductExpired) {
					if (rentACarBean != null) {
						rtnMap.put("rentACarInput", this.getDefaultPlanData(fp, jposQuickQuoteService.getFrequencyList(fp),
								assetForm.getTotalNet(), assetForm.getTotalGross(), rentACarBean, this.jposQuickQuoteService.getRentACarSize(quoteIdx), quoteIdx));
					} else {
//						String gross = assetForm.getTotalNet();
						String net = assetForm.getTotalNet();
						String gross = assetForm.getTotalGross();
						String cost = null;
						String c_type = jposQuickQuoteService.determineQuoteCostField(fp);
	//						if (IJPOSQuickQuoteConstants.QUOTE_ASSET_GROSS.equals(c_type)) {
	//						cost = gross;
	//					} else if (IJPOSQuickQuoteConstants.QUOTE_ASSET_NET.equals(c_type)) {
	//						cost = net;
	//					}
						double dcost = jposQuickQuoteService.getCostValue(fp, quoteIdx, getUserContainer().getLocale());
						cost = formatService.formatDouble(dcost, getUserContainer().getLocale());
						rtnMap.put("cost", cost);
	//					double depositPercent = fp.getDefaultDepositPercentage();
	//					double cost_d = formatService.parseDouble(cost, getUserContainer().getLocale(), false, 0d);
	//					String deposit_s = formatService.formatDouble((depositPercent / 100) * cost_d, getUserContainer().getLocale());
						String deposit_s;

						double depositPercent = fp.getDefaultDepositPercentage();
						double cost_d = formatService.parseDouble(cost, getUserContainer().getLocale(), false, 0d);
						deposit_s = formatService.formatDouble((depositPercent / 100) * cost_d, getUserContainer().getLocale());

						if (jposQuickQuoteService.showDeposit(fp.getFinanceType().getIntCode())) {
							rtnMap.put("deposit", deposit_s);
						}  else if (jposQuickQuoteService.showLumpSum(fp.getFinanceType().getIntCode())) {
							rtnMap.put("lumpSum", deposit_s);
						}
					}
				}

//				rtnMap.put("deposit", formatService.formatDouble((depositPercent / 100) * cost_d, getUserContainer().getLocale()));
			}

			this.jposQuickQuoteService.setFinanceProductToContainer(quoteIdx, newSelProduct);
//			}
			return rtnMap;
		}

		return null;
	}

}
