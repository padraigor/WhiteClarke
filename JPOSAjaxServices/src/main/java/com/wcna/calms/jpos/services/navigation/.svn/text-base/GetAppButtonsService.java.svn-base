package com.wcna.calms.jpos.services.navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.jpos.services.application.IAppButtonService;
import com.wcna.calms.jpos.services.application.IAppDisplayService;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.notes.IApplicationCommentsDataVO;
import com.wcna.calms.service.notes.IProposalNotesService;

public class GetAppButtonsService extends CalmsAjaxService {

	private final IAppButtonService appButtonService;
	private final IAppDisplayService appDisplayService;
	private final IProposalNotesService proposalNotesService;
	
	public GetAppButtonsService(IAppButtonService appButtonService,
			IAppDisplayService appDisplayService, IProposalNotesService proposalNotesService) {
		this.appButtonService = appButtonService;
		this.appDisplayService = appDisplayService;
		this.proposalNotesService = proposalNotesService;
	}
	
	public Object invoke(Object arg0) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("buttonList", appButtonService.getApplicationButtons());
		ret.put("isShowingCreditStatus", appDisplayService.isShowingCreditStatus() ? IConstants.FLAG_YES : "");
		ret.put("documentVersion", this.getAppContainer().getDocumentVersion() + "");
		
		if (arg0 != null && arg0 instanceof Map) {
			Map map = (Map) arg0;
			if (map.containsKey("additionalInfo")) {
				Map additionalInfo = (Map) map.get("additionalInfo");
				if (additionalInfo != null) {
					if (IConstants.FLAG_YES.equals((String) additionalInfo.get("updateNotes"))) {
						ret.put("notes", this.getProposalNotes());
					}
				}
			}
		}
		
		return ret;
	}

	private List<IApplicationCommentsDataVO> getProposalNotes() {
		String noteType = "ALL";
		String noteSubType = "ALL";
		String viewNotesFor = "TPRCV";
		String appCustomerID = "ALL";
		String dateFrom = null;
		String dateTo = null;
		ArrayList appCustomers = proposalNotesService.getAppCustomers(getAppContainer().getAppID(), getUserContainer(), false);
		List<IApplicationCommentsDataVO> noteResults = proposalNotesService.getCatalystNotes(noteType, noteSubType, viewNotesFor, false, null, appCustomerID, dateFrom, dateTo, appCustomers);
		return noteResults;
	}
	
}
