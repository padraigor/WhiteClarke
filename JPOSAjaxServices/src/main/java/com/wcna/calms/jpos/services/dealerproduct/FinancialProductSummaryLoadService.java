package com.wcna.calms.jpos.services.dealerproduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.data.BrandData;
import com.wcna.calms.data.BusinessDealerData;
import com.wcna.calms.data.businesspartner.BusinessPartnerBrandData;
import com.wcna.calms.service.businesspartner.IBusinessPartnerService;

public class FinancialProductSummaryLoadService extends CalmsAjaxService {

	private final IFinancialProductService financialProductService;
	private final IBusinessPartnerService businessPartnerService;

	public FinancialProductSummaryLoadService (IFinancialProductService financialProductService, IBusinessPartnerService businessPartnerService) {
		this.financialProductService = financialProductService;
		this.businessPartnerService = businessPartnerService;
	}

	public Object invoke(Object parameter) {

		if (parameter != null && parameter instanceof Map) {
			Map<String, List<IFinancialProductSummaryBean>> ret = new HashMap<String, List<IFinancialProductSummaryBean>>();
			List<IFinancialProductSummaryBean> productSummaryList = new ArrayList<IFinancialProductSummaryBean>();
			String dealerId = (String)((Map) parameter).get("dealerId");
			BusinessDealerData dealer = (BusinessDealerData)this.businessPartnerService.getBusinessPartner(Long.parseLong(dealerId));
			String groupId = "";
			if (dealer.getBusinessDealerGroup()!= null) {
				 groupId = dealer.getBusinessDealerGroup().getCode();
			}
			BusinessPartnerBrandData[] bizPartBrandArray = dealer.getBrandsArray();
			List<Integer> brands = new ArrayList<Integer>();
			Map<String,IFinancialProductSummaryBean> dealerLevelProductSummaryBeanMap = new HashMap<String,IFinancialProductSummaryBean>();
			Map<String,IFinancialProductSummaryBean> groupLevelProductSummaryBeanMap = new HashMap<String,IFinancialProductSummaryBean>();
			Map<String,IFinancialProductSummaryBean> brandLevelProductSummaryBeanMap = new HashMap<String,IFinancialProductSummaryBean>();
			if (bizPartBrandArray != null) {
				for (int i= 0;i<bizPartBrandArray.length; i++) {
					BrandData brand = bizPartBrandArray[i].getBrand();
					String brandCode = brand.getCode();
					brands.add(Integer.parseInt(brandCode));
				}
			}
			if (!StringUtils.isBlank(dealerId)) {
				dealerLevelProductSummaryBeanMap = this.financialProductService.getDealerLevelProductSummary(dealerId);
			}

			if (!StringUtils.isBlank(groupId)) {
				groupLevelProductSummaryBeanMap = this.financialProductService.getGroupLevelProductSummary(groupId);
			}

			if (!brands.isEmpty()) {
				brandLevelProductSummaryBeanMap = this.financialProductService.getBrandLevelProductSummary(brands);
			}
			brandLevelProductSummaryBeanMap.putAll(groupLevelProductSummaryBeanMap);
			brandLevelProductSummaryBeanMap.putAll(dealerLevelProductSummaryBeanMap);
			for (String key : brandLevelProductSummaryBeanMap.keySet()) {
				productSummaryList.add(brandLevelProductSummaryBeanMap.get(key));
			}
			ret.put("productSummaries", productSummaryList);
			return ret;
		}
		return null;
	}
}
