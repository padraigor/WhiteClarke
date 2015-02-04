package com.wcna.calms.service.asset;


import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.jpos.services.asset.IJPOSVehicleCAPDetailsService;


public class JPOSVehicleCAPDetailsLoadService  extends CalmsAjaxService {

	IJPOSVehicleCAPDetailsService vehCAPDetailsService;

	JPOSVehicleCAPDetailsLoadService(IJPOSVehicleCAPDetailsService vehCAPDetailsService){
		this.vehCAPDetailsService = vehCAPDetailsService;
	}

	public Object invoke(Object parameter) {
		return vehCAPDetailsService.load();
	}

}
