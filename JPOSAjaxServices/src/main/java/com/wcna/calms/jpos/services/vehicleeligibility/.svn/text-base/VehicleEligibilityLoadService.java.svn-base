package com.wcna.calms.jpos.services.vehicleeligibility;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class VehicleEligibilityLoadService extends CalmsAjaxService {

	private final IVehicleEligibilityService vehicleEligibilityService;

	public VehicleEligibilityLoadService(IVehicleEligibilityService vehicleEligibilityService) {
		this.vehicleEligibilityService = vehicleEligibilityService;
	}

	public Object invoke(Object parameter) {
		Map<String, Object> payload = new HashMap<String, Object>();
		payload.put("groupList", vehicleEligibilityService.getGroupList());
		return payload;
	}

}
