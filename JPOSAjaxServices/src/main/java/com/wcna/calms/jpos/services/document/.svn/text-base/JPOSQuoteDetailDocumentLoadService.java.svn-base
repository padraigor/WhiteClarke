package com.wcna.calms.jpos.services.document;

import java.util.HashMap;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.jpos.services.documents.IJPOSDocumentService;
import com.wcna.calms.service.application.IAppContainer;

public class JPOSQuoteDetailDocumentLoadService extends CalmsAjaxService {

	private IJPOSDocumentService documentService;
	
	public JPOSQuoteDetailDocumentLoadService(IJPOSDocumentService documentService){
		this.documentService = documentService;
	}
	
	public Object invoke(Object arg0) {
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		IAppContainer appContainer = getAppContainer();
		String status = "failed";
		
		if (documentService.generateQuoteDetailDocument(appContainer.getAppCode(), appContainer.getAppID())){
			status = "ready";
		}
		returnMap.put("status", status);
		
		return returnMap;
	}

}
