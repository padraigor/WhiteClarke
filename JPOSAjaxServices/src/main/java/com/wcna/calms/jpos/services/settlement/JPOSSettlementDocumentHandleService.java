package com.wcna.calms.jpos.services.settlement;

import java.rmi.RemoteException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.xalan.templates.TemplateList;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.exception.SystemException;
import com.wcg.product.calms.security.CalmsSessionData;
import com.wcg.product.services.session.SessionManager;
import com.wcna.calms.core.Setup;
import com.wcna.calms.edm.Calms3EdmWebClient;
import com.wcna.calms.edm.EdmConstants;
import com.wcna.calms.jpos.services.documents.IJPOSDocumentService;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.document.IDocumentService;
import com.wcna.calms.service.udt.IUdtTranslationService;
import com.wcna.calms.util.OptionLabelValue;
import com.wcna.edms.wsedm.client.DocumentOption;
import com.wcna.edms.wsedm.data.DocAttribute;
import com.wcna.edms.wsedm.data.EdmsDocResponseData;
import com.wcna.edms.wsedm.data.EdmsDocSaveRequest;
import com.wcna.lang.StringUtil;

public class JPOSSettlementDocumentHandleService extends CalmsAjaxService
		implements IJPOSSettlementDocumentHandleService {

	private final Calms3EdmWebClient calms3EdmWebClient;
	private final IDocumentService documentService;
	private final IUdtTranslationService udtTranslationService;
	private final IJPOSDocumentService jposDocumentService;

	public JPOSSettlementDocumentHandleService(
			Calms3EdmWebClient calms3EdmWebClient,
			IDocumentService documentService,
			IUdtTranslationService udtTranslationService,
			IJPOSDocumentService jposDocumentService) {
		this.calms3EdmWebClient = calms3EdmWebClient;
		this.documentService = documentService;
		this.udtTranslationService = udtTranslationService;
		this.jposDocumentService = jposDocumentService;
	}

	public Object invoke(Object arg0) {

		Map<String, String> ret = new HashMap<String, String>();
		// Get template name from udt table
		ArrayList templateList = this.udtTranslationService.getTranslatedCodeValues(ISettlementQuoteService.SETTLEMENT_UDT_TABLE_NAME, this.getUserContainer().getLanguageCode(), this.getUserContainer().getCountryCode());
		String templateFileName;
		if (templateList.size() > 1) {
			OptionLabelValue labelValue = (OptionLabelValue) templateList.get(1);
			templateFileName = labelValue.getValue();
		} else {
			throw new SystemException(
					"No Settlment Letter Template Name in UDT table");
		}

		long edmDocId = this.jposDocumentService.findSettlementEdmDocId();
		String guid = null;
		Long templateVersion = this.jposDocumentService.findSettlementTemplateVersion(templateFileName);
		if (edmDocId == 0) {
			edmDocId = this.jposDocumentService.saveNewSettlementDoc(
					ISettlementQuoteService.SETTLEMENT_UDT_TABLE_NAME,
					IConstants.FLAG_NO);
		} else {
			if (StringUtil.isBlank(guid)) {
				if(this.jposDocumentService.deleteSettlementDocs()) {
					edmDocId = this.jposDocumentService.saveNewSettlementDoc(
							ISettlementQuoteService.SETTLEMENT_UDT_TABLE_NAME,
							IConstants.FLAG_NO);
				}
			} else {
			guid = this.documentService.getGuidFromEdmDocId(edmDocId);
		}
		}
		//Get settlement letter information from session
		IJPOSSettlementContainer settlementContainer = (IJPOSSettlementContainer) ((CalmsSessionData)SessionManager.getInstance().getSessionData()).getComponentSessionData(ISettlementQuoteService.COMPONENT_SETTLEMENT_CONTAINER);
		if (settlementContainer == null) {
			throw new SystemException("No Quote Container is initialized");
		}

		String xml = getXml(settlementContainer);

		EdmsDocSaveRequest edmsDocSaveRequest = this.getEdmsDocSaveRequest(
				templateFileName, guid, edmDocId, templateVersion);
		EdmsDocResponseData docResponse = null;

		try {
			ArrayList retList = this.calms3EdmWebClient.generateDocuments(
					new EdmsDocSaveRequest[] { edmsDocSaveRequest }, xml,
					Setup.getSourceSystem());
			if (retList != null && retList.size() > 1) {
				byte[] pdfBytes = (byte[]) retList.get(0);
				if (pdfBytes != null) {
					documentService.setDocByteToSession(pdfBytes);
					ret.put("status", "ready");
				}
				EdmsDocResponseData[] responses = (EdmsDocResponseData[]) retList
						.get(1);
				if (responses != null && responses.length > 0) {
					docResponse = responses[0];
				}
			}
		} catch (RemoteException e) {
			throw new SystemException(e);
		}

		if (docResponse == null) {
			throw new SystemException("No Response From EDMS");
		}

		if (StringUtils.isBlank(guid)) {
			String[] edmWebGuids = new String[] {docResponse.getGuid()};
			if (!this.jposDocumentService.updateEdmSettlementDoc(edmDocId,edmWebGuids[0])) {
				documentService.revertEdmWebChange(edmWebGuids);
			}
		}
		return ret;
	}

	private String getXml(IJPOSSettlementContainer settlementContainer) {
		Format xmlDateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		StringBuffer xmlBuffer = new StringBuffer();
		xmlBuffer.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
		xmlBuffer.append("<data>");
		xmlBuffer.append("<settlementAmount>" + settlementContainer.getSettlementAmount() + "</settlementAmount>");
		if (settlementContainer.getSettlementExpiryDate()!= null) {
			xmlBuffer.append("<settlementExpiryDate>" + xmlDateFormatter.format(settlementContainer.getSettlementExpiryDate()) + "</settlementExpiryDate>");
		} else {
			xmlBuffer.append("<settlementExpiryDate/>");
		}
		if (settlementContainer.getCmsAgreementNumber() != null) {
			xmlBuffer.append("<cmsAgreementNumber>" + settlementContainer.getCmsAgreementNumber() + "</cmsAgreementNumber>");
		} else {
			xmlBuffer.append("<cmsAgreementNumber/>");
		}
		xmlBuffer.append("<cmsScheduleNumber>" + settlementContainer.getCmsScheduleNumber() + "</cmsScheduleNumber>");
		if (settlementContainer.getCmsInvoicingCustBpid() != null) {
			xmlBuffer.append("<cmsInvoicingCustBpid>" + settlementContainer.getCmsInvoicingCustBpid() + "</cmsInvoicingCustBpid>");
		} else {
			xmlBuffer.append("<cmsInvoicingCustBpid/>");
		}
		if (settlementContainer.getCmsInvoicingCustBillAddr() != null) {
			xmlBuffer.append("<cmsInvoicingCustBillAddr>" + settlementContainer.getCmsInvoicingCustBillAddr() + "</cmsInvoicingCustBillAddr>");
		} else {
			xmlBuffer.append("<cmsInvoicingCustBillAddr/>");
		}
		if (settlementContainer.getCmsQuotationNumber() != null) {
			xmlBuffer.append("<cmsQuotationNumber>" + settlementContainer.getCmsQuotationNumber() + "</cmsQuotationNumber>");
		} else {
			xmlBuffer.append("<cmsQuotationNumber/>");
		}
		if (settlementContainer.getCmsTrackingKey() != null) {
			xmlBuffer.append("<cmsTrackingKey>" + settlementContainer.getCmsTrackingKey() + "</cmsTrackingKey>");
		} else {
			xmlBuffer.append("<cmsTrackingKey/>");
		}
		if (settlementContainer.getCustomerName() != null) {
			xmlBuffer.append("<customerName>" + settlementContainer.getCustomerName() + "</customerName>");
		} else {
			xmlBuffer.append("<customerName/>");
		}
		if (settlementContainer.getSettlementRequestDate() != null) {
			xmlBuffer.append("<settlementRequestDate>" + xmlDateFormatter.format(settlementContainer.getSettlementRequestDate()) + "</settlementRequestDate>");
		} else {
			xmlBuffer.append("<settlementRequestDate/>");
		}
		xmlBuffer.append("</data>");
		return xmlBuffer.toString();
	}

	private EdmsDocSaveRequest getEdmsDocSaveRequest(String templateName,
			String guid, long edmDocId, Long templateVersion) {
		EdmsDocSaveRequest ret = new EdmsDocSaveRequest();
		DocAttribute[] docAttributes = new DocAttribute[1];
		if (!StringUtils.isBlank(guid)) {
			ret.setGuid(guid);
		} else {
			DocAttribute docAttribute = new DocAttribute();
			docAttribute.setAttributeKey(EdmConstants.SOURCE_SYSTEM_ID);
			//Separate settlement doc id from quote doc id, append "S-"
			docAttribute.setDataChar("S-"+Long.toString(edmDocId));
			docAttributes[0] = docAttribute;
			ret.setSourceSystemAttributes(docAttributes);
		}
		ret.setDocName(ISettlementQuoteService.SETTLEMENT_UDT_TABLE_NAME);
		ret.setDocOption(DocumentOption.DOCUMENT_OPTION_GENERATE);
		ret.setTemplateFilename(templateName);
		ret.setTemplateVersion(templateVersion);
		ret.setDocVersion(templateVersion);
		return ret;
	}
}
