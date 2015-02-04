package com.wcna.calms.jpos.services.quote;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.app.exception.ApplicationDataFetchMissingIdException;
import com.wcna.calms.component.IDocumentXMLGenerator;
import com.wcna.calms.core.Setup;
import com.wcna.calms.edm.Calms3EdmWebClient;
import com.wcna.calms.jpos.services.documents.IJPOSDocumentService;
import com.wcna.calms.service.application.IAppContainer;
import com.wcna.calms.service.document.IDocumentService;
import com.wcna.edms.wsedm.client.DocumentOption;
import com.wcna.edms.wsedm.data.EdmsDocResponseData;
import com.wcna.edms.wsedm.data.EdmsDocSaveRequest;
import com.wcna.util.SystemException;

public class JPOSQuickQuoteDocumentHandleService extends CalmsAjaxService {

	private final IDocumentXMLGenerator xmlGen;
	private final Calms3EdmWebClient calms3EdmWebClient;
	private final IDocumentService documentService;
	private final IJPOSDocumentService jposDocumentService;
	private final IJPOSQuickQuoteService jposQuickQuoteService;

	public JPOSQuickQuoteDocumentHandleService(IDocumentXMLGenerator xmlGen, Calms3EdmWebClient calms3EdmWebClient, IDocumentService documentService,
			IJPOSDocumentService jposDocumentService, IJPOSQuickQuoteService jposQuickQuoteService) {
		this.xmlGen = xmlGen;
		this.calms3EdmWebClient = calms3EdmWebClient;
		this.documentService = documentService;
		this.jposDocumentService = jposDocumentService;
		this.jposQuickQuoteService = jposQuickQuoteService;
	}

	public Object invoke(Object arg0) {
		Map<String, String> ret = new HashMap<String, String>();
		if (arg0 != null && arg0 instanceof Map) {
			Map map = (Map) arg0;
			String method = (String) map.get("method");
			String template = (String) map.get("templateName");
			String email = (String) map.get("emailAddress");
			IAppContainer appContainer = this.getAppContainer();

			// P is print, E is email (email address required)
			if (appContainer != null && appContainer.getAppID() > 0
					&& ("P".equals(method) || ("E".equals(method) && !StringUtils.isBlank(email)))) {
				try {
					long edmDocId = this.documentService.findEdmDocIdFromAppId(appContainer.getAppID());
					String guid = null;
					Long currentSourceSystemVersion = this.documentService.getCurrentSourceSystemVersion(new Long(appContainer.getAppID()));
					if (edmDocId == 0) {
//						edmDocId = this.elecDocMgmtApp.saveNewEdmDocAndAttr(null, edmDocAttrs, null, "JPOS_QUOSE", IConstants.FLAG_NO);
						edmDocId = this.jposDocumentService.saveNewEdmDocAndAttr();
					} else {
						guid = this.documentService.getGuidFromEdmDocId(edmDocId);
					}

					String xml = xmlGen.getDocumentXMLString(appContainer.getAppID(), null, null, null, null);
					EdmsDocSaveRequest edmsDocSaveRequest = this.getEdmsDocSaveRequest(template, guid, edmDocId, currentSourceSystemVersion);
					EdmsDocResponseData docResponse = null;

					if ("P".equals(method)) {
						// print the document
						try {
							ArrayList retList = this.calms3EdmWebClient.generateDocuments(new EdmsDocSaveRequest[]{edmsDocSaveRequest}, xml, Setup.getSourceSystem());
							if (retList != null && retList.size() > 1) {
								byte[] pdfBytes = (byte[]) retList.get(0);
								if (pdfBytes != null) {
									documentService.setDocByteToSession(pdfBytes);
									ret.put("status", "ready");
								}
//								docResponse = ((EdmsDocResponseData[]) retList.get(1))[0];
								EdmsDocResponseData[] responses = (EdmsDocResponseData[]) retList.get(1);
								if (responses != null && responses.length > 0) {
									docResponse = responses[0];
								}
							}
						} catch (RemoteException e) {
							throw new SystemException(e);
						}
					} else {
						// email the document
						if (!jposQuickQuoteService.isEmailQuoteDoc()) {
							throw new SystemException("Email attempted but not allowed");
						}

						try {
							EdmsDocResponseData[] response = this.calms3EdmWebClient.generateSendEmail(new EdmsDocSaveRequest[]{edmsDocSaveRequest}, xml, Setup.getSourceSystem(),
									email, null, null, null, null);
							if (response != null && response.length > 0) {
								docResponse = response[0];
								ret.put("status", "emailSuccess");
							}
						} catch (RemoteException e) {
							throw new SystemException(e);
						}
					}

					if (docResponse == null) {
						throw new SystemException("No Response From EDMS");
					}
					// only update with the returned guid if the existing edmdoc record did not have one before
					if (StringUtils.isBlank(guid)) {
						String[] edmWebGuids = new String[] {docResponse.getGuid()};
						if (!documentService.updateEdmDocs(new String[] {edmDocId + ""}, edmWebGuids, new DocumentOption[] {DocumentOption.DOCUMENT_OPTION_GENERATE})) {
							documentService.revertEdmWebChange(edmWebGuids);
						}
					}

				} catch (ApplicationDataFetchMissingIdException e) {
					throw new SystemException(e);
				}
			}

		}
		return ret;
	}

	private EdmsDocSaveRequest getEdmsDocSaveRequest(String templateName, String guid, long edmDocId, Long docVersion) {
		EdmsDocSaveRequest ret = new EdmsDocSaveRequest();
		if (!StringUtils.isBlank(guid)) {
			ret.setGuid(guid);
		} else {
			ret.setSourceSystemAttributes(this.documentService.getNewBasicDocAttributes(edmDocId));
		}
		ret.setDocName(IJPOSDocumentService.JPOS_QUOTE_DOC_NAME);
		ret.setDocOption(DocumentOption.DOCUMENT_OPTION_GENERATE);
		ret.setTemplateFilename(templateName);
		ret.setDocVersion(docVersion);
		return ret;
	}
}
