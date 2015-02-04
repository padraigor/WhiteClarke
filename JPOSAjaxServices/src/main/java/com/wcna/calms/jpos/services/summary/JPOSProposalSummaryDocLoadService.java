package com.wcna.calms.jpos.services.summary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.data.DocReviewModelVO;
import com.wcna.calms.edm.EdmConstants;
import com.wcna.calms.service.application.IAppContainer;
import com.wcna.calms.service.application.IApplicationDataVO;
import com.wcna.calms.service.application.IApplicationService;
import com.wcna.calms.service.application.IDocReviewModel;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.document.IDocumentService;

public class JPOSProposalSummaryDocLoadService extends CalmsAjaxService {
	public static String DOC_VERIFICATION_STAGE_0="0";
	
	private final IApplicationService applicationService;
	private final IDocumentService documentService;
	
	public JPOSProposalSummaryDocLoadService(IApplicationService applicationService, IDocumentService documentService) {
		this.applicationService = applicationService;
		this.documentService = documentService;
	}
	
	public Object invoke(Object arg0) {

		Map<String, Object> ret = new HashMap<String, Object>();
		IAppContainer appContainer = this.getAppContainer();
		IApplicationDataVO applicationData = null;
		if (appContainer != null && appContainer.getAppID() != 0){
			if (!IConstants.LEAD_APPLICATION_TYPE.equals(appContainer.getAppTypeCode())) {
				applicationData = applicationService.getApplicationVOById(appContainer.getAppID());
			}
		}		
		if (applicationData != null) {
			boolean startedVerification = false;
			if(!DOC_VERIFICATION_STAGE_0.equals(applicationData.getDocumentVerifyCode()) || "Y".equals(applicationData.getStartVerificationFlag()))
				startedVerification = true;
	
			long applicationVersionNo = appContainer.getDocumentVersion();
			Map[] supplierInfo = documentService.getProposalSupplierInfo(appContainer.getAppID());
			List <IDocReviewModel> supportingDocumentList = (List <IDocReviewModel>) documentService.getUploadedDocuments(new Long(appContainer.getAppID()), EdmConstants.DOC_ITEM_TYPE_ATTACHED_FROM_KYC);
			ArrayList <DocReviewModelVO> supportDocs = new ArrayList<DocReviewModelVO>();
			if (supportingDocumentList != null){				
				supportingDocumentList = (ArrayList<IDocReviewModel>) documentService.filterSupportingDocumentList(supportingDocumentList, Long.valueOf(this.getUserContainer().getRoleID()));
				for (Iterator <IDocReviewModel>it = supportingDocumentList.iterator(); it.hasNext();){
					IDocReviewModel docModel = (IDocReviewModel)it.next();
					DocReviewModelVO docModelVO = new DocReviewModelVO();
					docModelVO.setDocId(docModel.getEdmDocId());
					docModelVO.setName(docModel.getDocName());
					docModelVO.setGuid(docModel.getEdmWebGuid());
					docModelVO.setItemType(docModel.getItemType());
					supportDocs.add(docModelVO);
				}
			}
			ret.put("supportingDocuments",supportDocs);		
		}
		
		return ret;
	}

}
