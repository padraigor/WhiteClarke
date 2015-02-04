package com.wcna.calms.jpos.services.submit;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.service.app.SummaryService;
import com.wcna.calms.service.application.IApplicationDataVO;
import com.wcna.calms.service.application.IApplicationService;
import com.wcna.calms.service.notes.IProposalNotesService;
import com.wcna.calms.service.submit.ISubmitDetailService;
import com.wcna.lang.StringUtil;

public class SubmitProposalService extends CalmsAjaxService {

	private final IApplicationService applicationService;
	private final IProposalNotesService proposalNotesService;
	private final ISubmitDetailService submitDetailService;
	private final SummaryService summaryService;
	
	public SubmitProposalService(ISubmitDetailService submitDetailService,
			IProposalNotesService proposalNotesService, IApplicationService applicationService,
			SummaryService summaryService) {
		this.submitDetailService = submitDetailService;
		this.proposalNotesService = proposalNotesService;
		this.applicationService = applicationService;
		this.summaryService = summaryService;
	}

	public Object invoke(Object input) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String notes = null;
		if (input != null && input instanceof Map) {
			Object noteObj = ((Map) input).get("notes");
			if (noteObj != null && !StringUtil.isEmpty(String.valueOf(noteObj))){
				notes = String.valueOf(noteObj);
			}
		}
		
		if (notes == null)
			return resultMap;
			
		long appId = getAppContainer().getAppID();
		Long userId = Long.valueOf(getUserContainer().getUserID());
		IApplicationDataVO appData = applicationService.getApplicationVOById(new Long(appId));
		
		String roleIdString = this.getUserContainer().getRoleID();
		long roleId = 0;
		if (!StringUtil.isBlank(roleIdString)) {
			roleId = Long.valueOf(roleIdString);
		}
		String noteSubType = this.proposalNotesService.getNoteSubTypeCode(roleId);		
		
		proposalNotesService.addAppComment(appData, 
				null, 
				userId.toString(), 
				notes, 
				"PROP",  //TODO
				noteSubType,
				null);
		submitDetailService.setSubmitDateAndStatus(appId, userId);
		submitDetailService.doSubmit(userId.toString());
		
		return (HashMap<String, Object>)summaryService.invoke(input);
	}

}
