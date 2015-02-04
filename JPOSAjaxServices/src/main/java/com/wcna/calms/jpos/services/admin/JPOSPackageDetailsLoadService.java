package com.wcna.calms.jpos.services.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.jpos.services.vehicleeligibility.IVehicleEligibilityService;
import com.wcna.calms.util.OptionLabelValue;

public class JPOSPackageDetailsLoadService extends CalmsAjaxService {

	private IJPOSAdminPackageService service = null;
	private IVehicleEligibilityService vehEligibleService = null;

	public JPOSPackageDetailsLoadService(IJPOSAdminPackageService service,IVehicleEligibilityService vehEligibleService) {
		this.service = service;
		this.vehEligibleService = vehEligibleService;
	}

	public Object invoke(Object object) {

		HashMap<String,Object> retMap = new HashMap<String,Object>();
		if (object instanceof Map) {
			Map map = (Map) object;
			if (map == null) return object;

			String packageId = (String)map.get("packageId");
			String version = (String)map.get("version");

			if (packageId==null || "".equals(packageId) || version == null || "".equals(version)){
				return null;
			}

			IJPOSCommissionPackage commissionPackage = service.getCommissionPackage(Integer.valueOf(packageId), Integer.valueOf(version));

			List<OptionLabelValue> vehEligibilityDropDownItems = this.vehEligibleService.getGroupList();

			retMap.put("packageDetails", commissionPackage);
			retMap.put("vehicleEligibilityGroup", vehEligibilityDropDownItems);
		}
		return retMap;
	}

}
