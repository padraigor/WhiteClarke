package com.wcna.calms.service.asset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.jpos.services.asset.IJPOSVehicleDetailsService;
import com.wcna.calms.jpos.services.quote.JPOSQuickQuoteAssetVO;
import com.wcna.util.SystemException;

public class JPOSVehicleDetailsLoadService  extends CalmsAjaxService {

	IJPOSVehicleDetailsService vehDetailsService;

	JPOSVehicleDetailsLoadService(IJPOSVehicleDetailsService vehDetailsService){
		this.vehDetailsService = vehDetailsService;
	}

	public Object invoke(Object parameter) {
		return vehDetailsService.load();
	}
}
