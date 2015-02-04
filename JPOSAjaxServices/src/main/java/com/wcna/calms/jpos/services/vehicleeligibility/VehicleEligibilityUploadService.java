package com.wcna.calms.jpos.services.vehicleeligibility;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.common.IConstants;

public class VehicleEligibilityUploadService extends CalmsAjaxService {

	private final IVehicleEligibilityService vehicleEligibilityService;

	public VehicleEligibilityUploadService(IVehicleEligibilityService vehicleEligibilityService) {
		this.vehicleEligibilityService = vehicleEligibilityService;
	}

	public Object invoke(Object parameter) {
		Map<String, Object> payload = new HashMap<String, Object>();

		if (parameter != null && parameter instanceof Map) {
			String groupId = (String) ((Map) parameter).get("groupId");
			String groupName = (String) ((Map) parameter).get("groupName");
			FileItem fileItem = (FileItem) ((Map) parameter).get("csvData");
			String isConfirmed = (String) ((Map) parameter).get("CONFIRM_OVERRIDE");

			long retGroupId = vehicleEligibilityService.uploadFile(groupId, groupName, fileItem, IConstants.FLAG_YES.equals(isConfirmed));
			if (retGroupId > 0) {
				payload.put("isSuccess", "Y");
				payload.put("groupId", retGroupId + "");
				payload.put("groupList", vehicleEligibilityService.getGroupList());
			} else {
				if (retGroupId == -2) {
					payload.put("CONFIRM_OVERRIDE_REQ", "Y");
				} else if (retGroupId == -1) {
					payload.put("SYSTEM_ERROR", "");
				}
			}

		}

		return payload;
	}

}
