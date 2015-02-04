package com.wcna.calms.jpos.services.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.product.calms.security.CalmsSessionData;
import com.wcg.product.services.session.GenericMessage;
import com.wcg.product.services.session.SessionManager;
import com.wcna.calms.service.application.IDocConditionModel;
import com.wcna.calms.service.error.IErrorMessage;
import com.wcna.calms.util.BeanConverterUtil;
import com.wcna.framework.validation.IBusinessRuleValidationService;

public class JPOSPackageDetailsSaveService extends CalmsAjaxService implements IBusinessRuleValidationService{

	private IJPOSAdminPackageService service = null;
	private final BeanConverterUtil beanUtil;
	public JPOSPackageDetailsSaveService(IJPOSAdminPackageService service) {
		this.service = service;
		this.beanUtil = new BeanConverterUtil();
	}



	public Object invoke(Object object) {


		if (object != null && object instanceof Map) {
			Map<String, Object> dataMap = (Map<String, Object>)object;

			Map<String, Object> packageDetails = (Map<String, Object>) dataMap.get("packageDetails");
			IJPOSCommissionPackage commissionPackage = (IJPOSCommissionPackage) beanUtil.convertMapToBean(packageDetails, createBean(IJPOSCommissionPackage.class).getClass());

			List rangeRateList = beanUtil.convertMapsToBeanList((List) dataMap.get("rangeRateList"), createBean(IJPOSRangeRateVO.class));

			commissionPackage.setRangeRates(rangeRateList);

			Boolean createNewFlag = (Boolean)dataMap.get("createNewFlag");

			IJPOSCommissionPackage newPackage = service.saveCommissionPackage(commissionPackage, createNewFlag);


			HashMap<String,Object> retMap = new HashMap<String,Object>();
			retMap.put("packageId", newPackage.getPackageId());
			retMap.put("version", newPackage.getVersion());
			
						
			return retMap;
		}
		return null;
	}



	public boolean validate(Map objectMap) {
		return this.service.validate(objectMap);
	}

}
