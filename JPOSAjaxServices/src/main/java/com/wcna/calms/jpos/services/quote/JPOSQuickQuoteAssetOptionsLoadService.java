package com.wcna.calms.jpos.services.quote;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.app.IClientConstants;
import com.wcna.calms.data.catalog.ICatalogVariantOptionDataVO;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.lang.StringUtil;

public class JPOSQuickQuoteAssetOptionsLoadService extends CalmsAjaxService {

	private final IJPOSQuickQuoteAssetService assetService;
	private final IFormatService formatService;

	public JPOSQuickQuoteAssetOptionsLoadService(IJPOSQuickQuoteAssetService assetService, IFormatService formatService) {
		this.assetService = assetService;
		this.formatService = formatService;
	}

	public Object invoke(Object parameter) {
		if (parameter != null && parameter instanceof Map) {
			Map<String, Object> ret = new HashMap<String, Object>();
			double total = 0;
			String modelVariantId = (String) ((Map) parameter).get("modelVariantId");
			String ffoTaxCode = (String) ((Map) parameter).get("ffoTaxCode");

			if (!StringUtil.isEmpty(modelVariantId)) {
				List selOptions = (List) ((Map) parameter).get("selOptions");
				if (selOptions == null) {
					selOptions = new ArrayList();
				}
				int size1 = selOptions.size();
//				IJPOSQuickQuoteAssetForm form = createBean(IJPOSQuickQuoteAssetForm.class);
//				form.setModelId(modelId);
//				assetService.setModelOptionsAccessories(form);
//				List optionsList = form.getOptionAccessories();
				Date now = new Date();
				List<ICatalogVariantOptionDataVO> optionsList = assetService.getCatalogVariantOptions(now, now, Long.valueOf(modelVariantId));
				if (optionsList != null && !optionsList.isEmpty()) {
					List<Map<String, String>> out = new ArrayList<Map<String, String>>();
					int size = optionsList.size();
					for (int i = 0; i < size; i++) {
						Map map = new HashMap();
						ICatalogVariantOptionDataVO option = optionsList.get(i);
						String desc = "";
						if (option.getDescription() != null) {
							//desc = option.getDescription().getDescription() + " " + option.getDescription().getLongDescription();
							desc=getDescription(option);
						}
						map.put("description", desc);
						if((BooleanUtils.toBoolean(projectProperties.getProperty(IClientConstants.VEHICLE_FFO_DISPLAY_GROSS_LP))) && (ffoTaxCode != null) && (option.getOptionCost() != null)){
							String ffoGrossAmt = assetService.getFFOGrossAmount(option.getOptionCost(), ffoTaxCode, this.getUserContainer().getLocale());
							map.put("listPrice", ffoGrossAmt);
						}
						else{
						map.put("listPrice", formatService.formatDouble(option.getOptionCost(), this.getUserContainer().getLocale()));
						}
						map.put("id", option.getOptionId() + "");
//						map.put("override", "");
						map.put("select", "");
						map.put("salePrice", "");
						map.put("rvUpliftPer", option.getRvUpliftPercentage() );//siddy

						String id = option.getOptionId() + "";

						for (int j = 0; j < size1; j++) {
							Map selOption = (Map) selOptions.get(j);
							if (id.equals(selOption.get("id"))) {
//								map.put("override", selOption.get("override"));
								String salePrice = (String) selOption.get("salePrice");
								if (!StringUtils.isBlank(salePrice)
										&& formatService.parseDouble(salePrice, this.getUserContainer().getLocale(), true, null) == null) {
									salePrice = "";
								}
								map.put("salePrice", salePrice);
								map.put("select", IConstants.FLAG_YES);
								String amt = (String) map.get("listPrice");
								if (IConstants.FLAG_YES.equals(map.get("select"))
										&& !StringUtils.isBlank((String) map.get("salePrice"))) {
									amt = (String) map.get("salePrice");
								}
								map.put("select", true);
								total += formatService.parseDouble(amt, this.getUserContainer().getLocale(), false, 0d);
							}
						}

						out.add(map);
					}

//					return out;
					ret.put("ffoList", out);
				}
			}
			ret.put("total", formatService.formatDouble(total, this.getUserContainer().getLocale()));
			return ret;
		}
		return null;
	}



	private String getDisplayFormatFromProperties() {
		return projectProperties.getProperty(IClientConstants.VEHICLE_FFO_DESCRIPTION_DISPLAY_FORMAT, "S");
	}

	private String getDescription(ICatalogVariantOptionDataVO option) {
		String des ="";

		//Recommended by Mark B, Better for Readability as oppose to performance.
		String discriptionDisFormat = getDisplayFormatFromProperties();

		for(int i=0;i<discriptionDisFormat.length();i++){
			if(discriptionDisFormat.charAt(i)=='S')
			{
				des+=option.getDescription().getDescription();
			}else if(discriptionDisFormat.charAt(i)=='L')
			{
				des+=option.getDescription().getLongDescription();
			}else if(discriptionDisFormat.charAt(i)=='-')
			{
				des+="-";
			}else if(discriptionDisFormat.charAt(i)==' ')
			{
				des+=" ";
			}
		}

		return des;
	}

}
