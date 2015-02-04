package com.wcna.calms.jpos.services.quote;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.util.SystemException;

public class JPOSChangeCustTypeService extends CalmsAjaxService {

	private final IJPOSQuickQuoteService jposQuickQuoteService;
	private final IJPOSQuickQuoteCustomerValidatorService jposQuickQuoteCustomerValidatorService;

	public JPOSChangeCustTypeService(IJPOSQuickQuoteService jposQuickQuoteService, IJPOSQuickQuoteCustomerValidatorService jposQuickQuoteCustomerValidatorService) {
		this.jposQuickQuoteService = jposQuickQuoteService;
		this.jposQuickQuoteCustomerValidatorService = jposQuickQuoteCustomerValidatorService;
	}

	public Object invoke(Object parameter) {
		if (parameter != null && parameter instanceof Map) {
			Map map = (Map) ((Map) parameter).get("custData");

			if(jposQuickQuoteCustomerValidatorService.validate(map, jposQuickQuoteService.getApplicationType())){
				IJPOSQuickQuoteCustomerForm form = this.jposQuickQuoteService.getCustomerData();
				try {
					org.apache.commons.beanutils.BeanUtils.populate(form, map);
				} catch (Exception e) {
					throw new SystemException(e);
				}

				String custType = form.getCustomerType();

	//			List<String> planIds = (List<String>) ((Map) parameter).get("planIds");
				int size = this.jposQuickQuoteService.getQuoteContainerSize();
				if (!StringUtils.isBlank(custType) && size > 0) {
					long roleId = Long.valueOf(this.getUserContainer().getRoleID());
					Map<String, List<IVapMetaData>> ret = new HashMap<String, List<IVapMetaData>>();
					for (int i = 0; i < size; i++) {
	//					String planId = planIds.get(i);
						String planId = null;
						IFinanceProduct product = this.jposQuickQuoteService.getFinanceProductFromContainer(i);
						if (product != null) {
							planId = product.getPlanId();
							ValidVapsContainer validVapsContainer = this.jposQuickQuoteService.getVapScreenMetaData(planId, roleId, new Date(), custType);
							this.jposQuickQuoteService.setValidVapsToContainer(i, validVapsContainer);
							ret.put("vapMetaData" + i, validVapsContainer.getDisplayVaps());
						} else {
							ret.put("vapMetaData" + i, null);
						}

					}
					return ret;
				}
			}
		}
		return null;
	}

}
