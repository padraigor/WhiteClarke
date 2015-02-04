package com.wcna.calms.jpos.services.vehicleeligibility;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;

public class VehicleEligibilityGetDetailService extends CalmsAjaxService {

	private final IVehicleEligibilityService vehicleEligibilityService;

	public VehicleEligibilityGetDetailService(IVehicleEligibilityService vehicleEligibilityService) {
		this.vehicleEligibilityService = vehicleEligibilityService;
	}

	public Object invoke(Object parameter) {
		Map<String, Object> payload = null;

		if (parameter != null && parameter instanceof Map) {
			payload = new HashMap<String, Object>();
			payload.put("details", vehicleEligibilityService.getVehicleEligibilityDetails((String) ((Map) parameter).get("groupId")));
		}

		return payload;
	}

}
