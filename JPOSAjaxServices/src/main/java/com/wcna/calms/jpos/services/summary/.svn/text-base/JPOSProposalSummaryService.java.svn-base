package com.wcna.calms.jpos.services.summary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.data.BusinessPartnerData;
import com.wcna.calms.jpos.services.application.IAppButtonService;
import com.wcna.calms.jpos.services.application.IAppDisplayService;
import com.wcna.calms.jpos.services.application.IJPOSModulePermissionService;
import com.wcna.calms.jpos.services.application.IProposalSummaryService;
import com.wcna.calms.jpos.services.quote.IJPOSQuickQuoteService;
import com.wcna.calms.service.application.IAppContainer;
import com.wcna.calms.service.application.IApplicationAddressDataVO;
import com.wcna.calms.service.application.IApplicationDataVO;
import com.wcna.calms.service.application.IApplicationService;
import com.wcna.calms.service.application.ICustomerApplicantService;
import com.wcna.calms.service.businesspartner.IBusinessPartnerData;
import com.wcna.calms.service.businesspartner.IBusinessPartnerDealerData;
import com.wcna.calms.service.businesspartner.IBusinessPartnerService;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.common.IModuleConstants;
import com.wcna.calms.service.customer.IAppCustomerPhoneData;
import com.wcna.calms.service.customer.IApplicationConsumerDataVO;
import com.wcna.calms.service.customer.IApplicationCustomerDataVO;
import com.wcna.calms.service.customer.ICustomerNameService;
import com.wcna.calms.service.customer.ICustomerService;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.calms.service.notes.IApplicationCommentsDataVO;
import com.wcna.calms.service.notes.IProposalNotesService;
import com.wcna.calms.service.quote.IAssetService;
import com.wcna.calms.service.quote.IQuoteDataVO;
import com.wcna.calms.service.security.IRoleService;
import com.wcna.calms.service.security.IRoleTypeData;
import com.wcna.calms.service.security.IUserData;
import com.wcna.calms.service.translation.ITranslationService;
import com.wcna.calms.service.udt.IUdtTranslationService;
import com.wcna.calms.util.PropertiesUtil;
import com.wcna.calms.web.app.CoApplicantDetailsDisplay;
import com.wcna.framework.authorization.IAuthorizationService;
import com.wcna.lang.StringUtil;

public class JPOSProposalSummaryService extends CalmsAjaxService {

	private final IApplicationService applicationService;
	private final ICustomerService customerService;
	private final IJPOSQuickQuoteService jposQuickQuoteService;
	private final IAuthorizationService calmsAuthorizationService;
	private final IUdtTranslationService udtTransService;
	private final IProposalNotesService proposalNotesService;
	private final IJPOSModulePermissionService jposModulePermissionService;
	private final IBusinessPartnerService businessPartnerService;
	private final ICustomerNameService customerNameService;
	private final IAppButtonService appButtonService;
	private final IProposalSummaryService proposalSummaryService;
	private final IAppDisplayService appDisplayService;
	private final ITranslationService translationService;
	private final IRoleService roleService;
	private final IAssetService assetService;
	private final IFormatService formatService;
	private final Properties projectProperties;

	private final int MAX_JOINT_APPS;
	private final int MAX_GUARANTORS;
	private final int MAX_DIRECTORS;
	private final int MAX_PARTNERS;
	private final int MAX_EFFECTIVE_OWNERS;

	private final int NO_ACCESS = 0;
	private final int READ_ACCESS = 1;
	private final int WRITE_ACCESS = 2;
	public static String DOC_VERIFICATION_STAGE_0="0";

	public JPOSProposalSummaryService(IApplicationService applicationService,
			ICustomerService customerService,
			IJPOSQuickQuoteService jposQuickQuoteService,
			IAuthorizationService calmsAuthorizationService,
			IUdtTranslationService udtTransService,
			IProposalNotesService proposalNotesService,
			IJPOSModulePermissionService jposModulePermissionService,
			IBusinessPartnerService businessPartnerService,
			ICustomerNameService customerNameService,
			IAppButtonService appButtonService,
			IProposalSummaryService proposalSummaryService,
			IAppDisplayService appDisplayService,
			ITranslationService translationService,
			IRoleService roleService,
			IAssetService assetService,
			IFormatService formatService,
			Properties projectProperties) {
		this.applicationService = applicationService;
		this.customerService = customerService;
		this.jposQuickQuoteService = jposQuickQuoteService;
		this.calmsAuthorizationService = calmsAuthorizationService;
		this.udtTransService = udtTransService;
		this.proposalNotesService = proposalNotesService;
		this.jposModulePermissionService = jposModulePermissionService;
		this.businessPartnerService = businessPartnerService;
		this.customerNameService = customerNameService;
		this.MAX_GUARANTORS = PropertiesUtil.getProperties(ICustomerApplicantService.class).getIntegerProperty("MAX_GUARANTORS");
		this.MAX_JOINT_APPS = PropertiesUtil.getProperties(ICustomerApplicantService.class).getIntegerProperty("MAX_JOINT_APPS");
		this.MAX_DIRECTORS = PropertiesUtil.getProperties(ICustomerApplicantService.class).getIntegerProperty("MAX_DIRECTORS");
		this.MAX_PARTNERS = PropertiesUtil.getProperties(ICustomerApplicantService.class).getIntegerProperty("MAX_PARTNERS");
		this.MAX_EFFECTIVE_OWNERS = PropertiesUtil.getProperties(ICustomerApplicantService.class).getIntegerProperty("MAX_EFFECTIVE_OWNERS");
		this.appButtonService = appButtonService;
		this.proposalSummaryService = proposalSummaryService;
		this.appDisplayService = appDisplayService;
		this.translationService = translationService;
		this.roleService = roleService;
		this.assetService = assetService;
		this.formatService = formatService;
		this.projectProperties = projectProperties;
	}

	public Object invoke(Object arg0) {
		Map<String, Object> summaryMap = new HashMap<String, Object>();
		Map<String, Object> proposalMap = new HashMap<String, Object>();
		JPOSProposalSummaryVO proposalSummaryBean = null;

		IAppContainer appContainer = getAppContainer();
		IApplicationDataVO applicationData = null;
		// IQuoteData quoteData = null;
		Long defaultQuoteId = null;
		long appId = 0;
		IApplicationCustomerDataVO customerData = null;
		if (appContainer != null && appContainer.getAppID() != 0){
			appId = appContainer.getAppID();
			applicationData = applicationService.getApplicationVOById(appContainer.getAppID());
			customerData = customerService.getCustomerData(appContainer.getAppID(), IConstants.PRIMARY_APPLICANT_REF_ID);
		}

		if (applicationData != null) {
//			defaultQuoteId = new Long(applicationData.getDefaultQuoteId());
			IQuoteDataVO quoteVo = jposQuickQuoteService.loadQuote(appContainer.getAppID(), this.getUserContainer().getLocale());
			if (quoteVo != null) {
				defaultQuoteId = new Long(quoteVo.getId());
				// required in simulation screen, show current term and deposit
				proposalMap.put("currentTerm", quoteVo.getTerm());
				proposalMap.put("totalDeposit", quoteVo.getTotalDeposit());
				proposalMap.put("applicationId", applicationData.getId());
			}

			proposalMap.put("subTypeCode",applicationData.getSubTypeCode());
			proposalMap.put("creditLineSubTypeCode",IConstants.CREDIT_LINE_SUBTYPE_CODE);
			proposalMap.put("assetSubTypeCode",IConstants.ASSET_SUBTYPE_CODE);
		}

		// Main customer data load

		if (customerData != null) {

			IApplicationAddressDataVO addressData = customerService.getCustomerAddress( appContainer.getAppID(), IConstants.PRIMARY_APPLICANT_REF_ID, customerData.getCustomerTypeCode());

			proposalSummaryBean = new JPOSProposalSummaryVO();

			proposalSummaryBean.setCustomerType(customerData.getCustomerTypeCode());
			proposalSummaryBean.setSystemValidatedFlag(customerData.getSystemValidatedFlag());
			proposalSummaryBean.setCreditScore(customerData.getCreditScore());
			proposalSummaryBean.setCustomerName(this.customerNameService.getCustomerName(customerData, true, false));

			if (IConstants.SOLE_TRADER_CUSTOMER_TYPE.equals(customerData.getCustomerTypeCode())){
				proposalSummaryBean.setSoleTraderUser("Y");
			}else{
				proposalSummaryBean.setSoleTraderUser("");
			}

			// Set ID Number
			String idNumber = customerData.getIdNumber();


			if (customerData instanceof IApplicationConsumerDataVO){
				proposalSummaryBean.setMotability(((IApplicationConsumerDataVO)customerData).getMotabilityEligibleFlag());
			}




			proposalSummaryBean.setEntityType(udtTransService.getUdtDesc(customerData.getCustomerTypeCode(), "CUSTOMER_TYPE", getUserContainer().getCountryCode(), getUserContainer().getLanguageCode()));


			proposalSummaryBean.setCustomerDetailsSummary(getCustomerSummaryDetails(customerData, addressData));

			proposalMap.put("mainCustomer", proposalSummaryBean);

			List<JPOSApplicantInfoDisplayVO> applicantsDisplay = new ArrayList<JPOSApplicantInfoDisplayVO>();

			String lblValidCust = translationService.getEntityTransLabelByFieldName("lblCustValidated", getUserContainer().getLanguageCode());
			String lblNotValidCust = translationService.getEntityTransLabelByFieldName("lblCustNotValidated", getUserContainer().getLanguageCode());

			IRoleTypeData roleType = roleService.getRoleTypeByRoleId(Long.parseLong(getUserContainer().getRoleID()));
			boolean internalUserFlag = roleType.isIsInternallFlag();

			applicantsDisplay.add(this.getMainApplicantDisplay(proposalSummaryBean, lblValidCust, lblNotValidCust, idNumber, internalUserFlag, customerData.getExistingCustomerFlag()));

			// Guarantors
			List <CoApplicantDetailsDisplay>guarantors = customerService.getGuarantors();
			proposalMap.put("guarantors", guarantors);

			for (Iterator <CoApplicantDetailsDisplay>it = guarantors.iterator(); it.hasNext();){
				CoApplicantDetailsDisplay disp = it.next();

				String phoneType = "";

				if (IConstants.PARTNERSHIP_CUSTOMER_TYPE.equals(disp.getCustomerType())
						|| IConstants.COMMERCIAL_CUSTOMER_TYPE.equals(disp.getCustomerType())){
					phoneType = IConstants.PHONE_TYPE_BUSINESS;
				}else if (IConstants.SOLE_TRADER_CUSTOMER_TYPE.equals(disp.getCustomerType())){
					phoneType = IConstants.PHONE_TYPE_HOME;
				}else{
					phoneType = IConstants.PHONE_TYPE_HOME;
				}

				Set<IAppCustomerPhoneData> phones = disp.getPhoneList();
				for(IAppCustomerPhoneData phone : phones) {
					if (phoneType.equals(phone.getPhoneTypeCode())) {
						disp.setPhone(phone.getPhoneNumber());
						disp.setPhoneTypeCode(phone.getPhoneTypeCode());
						break;
					}
				}
			}

			// CoApplicants, directors and partners
			List <CoApplicantDetailsDisplay>coapps = customerService.getCoApplicants();
			List <CoApplicantDetailsDisplay>directors = customerService.getDirectorTypeReferences();
			List <CoApplicantDetailsDisplay>effectiveOwners = customerService.getEffectiveOwnerTypeReferences();
			List <CoApplicantDetailsDisplay>partners = customerService.getPartnerTypeReferences();
			if (directors.size() > 0)
				coapps.addAll(directors);
			if (partners.size() > 0)
				coapps.addAll(partners);

			proposalMap.put("jointApplicants", coapps);

			for(int i=0;i<coapps.size();i++) {
				CoApplicantDetailsDisplay jointApplicant = coapps.get(i);
				applicantsDisplay.add(this.getJointApplicantDisplay(jointApplicant,proposalSummaryBean, lblValidCust, lblNotValidCust, internalUserFlag));
			}

			for(int i=0;i<guarantors.size();i++) {
				CoApplicantDetailsDisplay guarantor = guarantors.get(i);
				applicantsDisplay.add(this.getGuarantorDisplay(guarantor, lblValidCust, lblNotValidCust, internalUserFlag));
			}

			for(int i=0;i<effectiveOwners.size();i++) {
				CoApplicantDetailsDisplay effectiveOwner = effectiveOwners.get(i);
				applicantsDisplay.add(this.getEffectiveOwnerDisplay(effectiveOwner, lblValidCust, lblNotValidCust, internalUserFlag));
			}


			for (Iterator <CoApplicantDetailsDisplay>it = coapps.iterator(); it.hasNext();){
				CoApplicantDetailsDisplay disp = it.next();

				String phoneType = "";

				if (IConstants.PARTNERSHIP_CUSTOMER_TYPE.equals(disp.getCustomerType())
						|| IConstants.COMMERCIAL_CUSTOMER_TYPE.equals(disp.getCustomerType())){
					phoneType = IConstants.PHONE_TYPE_BUSINESS;
				}else if (IConstants.SOLE_TRADER_CUSTOMER_TYPE.equals(disp.getCustomerType())){
					phoneType = IConstants.PHONE_TYPE_HOME;
				}else{
					phoneType = IConstants.PHONE_TYPE_HOME;
				}

				Set<IAppCustomerPhoneData> phones = disp.getPhoneList();
				if (phones != null) {
					for(IAppCustomerPhoneData phone : phones) {
						if (phoneType.equals(phone.getPhoneTypeCode())) {
							disp.setPhone(phone.getPhoneNumber());
							disp.setPhoneTypeCode(phone.getPhoneTypeCode());
							break;
						}
					}
				}
			}

			proposalMap.put("allApplicantsInfo", applicantsDisplay);


//			proposalMap.put("guarantorLimit", this.MAX_GUARANTORS);
//			if(IConstants.CONSUMER_CUSTOMER_TYPE.equals(customerData.getCustomerTypeCode()) ||
//					IConstants.SOLE_TRADER_CUSTOMER_TYPE.equals(customerData.getCustomerTypeCode())) {
//				proposalMap.put("jointApplicantLimit", this.MAX_JOINT_APPS);
//			} else if (IConstants.COMMERCIAL_CUSTOMER_TYPE.equals(customerData.getCustomerTypeCode())) {
//				proposalMap.put("jointApplicantLimit", this.MAX_DIRECTORS);
//			} else if (IConstants.PARTNERSHIP_CUSTOMER_TYPE.equals(customerData.getCustomerTypeCode())) {
//				proposalMap.put("jointApplicantLimit", this.MAX_PARTNERS);
//			}
		}

		String hasQuote = (defaultQuoteId == null) ? "N" : "Y";

		proposalMap.put("hasQuote", hasQuote);

		/*
		StringBuffer sb = new StringBuffer();
		//sb.append(jposQuickQuoteService.getPlanSummaryDesc(null));
		String desc = jposQuickQuoteService.getQuoteSummaryDesc(getAppContainer().getAppID() + "");
		if (desc == null) {
			desc = "";
		}
		desc = desc.replaceAll("&&", "<br/>");
		sb.append(desc);

		String financeDesc = sb.toString();
		financeDesc = financeDesc.replaceAll("\\n", "<br/>");
		 */

		proposalMap.put("financeDetailsSummary", jposQuickQuoteService.getQuoteSummaryBean(getAppContainer().getAppID(), Long.parseLong(getUserContainer().getRoleID())));
		String vehicleSummary = jposQuickQuoteService
				.getVehicleSummaryDesc(getAppContainer().getAppID() + "");
		// since we display this as html text, we need to indicate line breaks
		// via <br/> tag
		if (!StringUtil.isEmpty(vehicleSummary)) {
			vehicleSummary = vehicleSummary.replaceAll("\\n", "<br/>");
		}
		proposalMap.put("financeDetailsVehicle", vehicleSummary);

		String listPrice= jposQuickQuoteService.getVehicleSummaryListPrice(getAppContainer().getAppID() + "");
		proposalMap.put("assetListPrice", listPrice);

//		Map<String,Integer> modAccessMap = initModuleAccess();
		Map<String,Integer> sectionModAccessMap = initSectionModuleAccess();

//		for (Iterator itr=modAccessMap.keySet().iterator(); itr.hasNext();) {
//			String moduleCode = (String)itr.next();
//			if (calmsAuthorizationService.isWritable(moduleCode, true, IApplicationDataVO.class))
//				modAccessMap.put(moduleCode, WRITE_ACCESS);
//			else if (calmsAuthorizationService.isReadable(moduleCode, true, IApplicationDataVO.class))
//				modAccessMap.put(moduleCode, READ_ACCESS);
//		}

		Map<String,Integer> modAccessMap = this.jposModulePermissionService.initModuleAccess();
		for (Iterator itr=sectionModAccessMap.keySet().iterator(); itr.hasNext();) {
			String moduleCode = (String)itr.next();
			if (calmsAuthorizationService.isWritable(moduleCode, true, IApplicationDataVO.class, false))
				sectionModAccessMap.put(moduleCode, WRITE_ACCESS);
			else if (calmsAuthorizationService.isReadable(moduleCode, true, IApplicationDataVO.class, false))
				sectionModAccessMap.put(moduleCode, READ_ACCESS);
		}

//		String appTypeCode = appContainer.getAppTypeCode();
//		boolean isLead = IConstants.LEAD_APPLICATION_TYPE.equals(appTypeCode);
//		if (!isLead) {
//			// logic taken from DocumentLoadService in DocumentManagement project
//			boolean startedVerification = false;
//			if(!DOC_VERIFICATION_STAGE_0.equals(applicationData.getDocumentVerifyCode()) || "Y".equals(applicationData.getStartVerificationFlag()))
//				startedVerification = true;
//
//			long applicationVersionNo = appContainer.getDocumentVersion();
//			Map[] supplierInfo = documentService.getProposalSupplierInfo(appContainer.getAppID());
//			ArrayList returnList = documentService.load(startedVerification, applicationVersionNo, supplierInfo);
//			ArrayList <DocReviewModelVO> supportDocs = new ArrayList<DocReviewModelVO>();
//			if (returnList != null){
//				ArrayList <IDocReviewModel>supportingDocumentList = (ArrayList<IDocReviewModel>) returnList.get(2);
//				supportingDocumentList = (ArrayList<IDocReviewModel>) documentService.filterSupportingDocumentList(supportingDocumentList, Long.valueOf(this.getUserContainer().getRoleID()));
//				for (Iterator <IDocReviewModel>it = supportingDocumentList.iterator(); it.hasNext();){
//					IDocReviewModel docModel = (IDocReviewModel)it.next();
//					DocReviewModelVO docModelVO = new DocReviewModelVO();
//					docModelVO.setDocId(docModel.getEdmDocId());
//					docModelVO.setName(docModel.getDocName());
//					docModelVO.setGuid(docModel.getEdmWebGuid());
//					docModelVO.setItemType(docModel.getItemType());
//					supportDocs.add(docModelVO);
//				}
//			}
//			proposalMap.put("supportingDocuments",supportDocs);
//		}

		// from NotesSearchService
		String noteType = "ALL";
		String noteSubType = "ALL";
		String viewNotesFor = "TPRCV";
		String appCustomerID = "ALL";
		String dateFrom = null;
		String dateTo = null;
		ArrayList appCustomers = proposalNotesService.getAppCustomers(getAppContainer().getAppID(), getUserContainer(), false);
		List<IApplicationCommentsDataVO> noteResults = proposalNotesService.getCatalystNotes(noteType, noteSubType, viewNotesFor, false, null, appCustomerID, dateFrom, dateTo, appCustomers);
		proposalMap.put("automatedNotes",noteResults);

		if (applicationData != null) {
			if (!StringUtil.isBlank(getUserContainer().getCurrentDagID())){
				long partnerId = businessPartnerService.findPartnerIdByDagId(Long.parseLong(getAppContainer().getDagID()));
				IBusinessPartnerData bizPartnerVO = businessPartnerService.getBusinessPartner(partnerId);
				if (bizPartnerVO != null){
					proposalMap.put("dealerName", bizPartnerVO.getName());
					if (BusinessPartnerData.BIZ_TYPE_DEALER.equals(bizPartnerVO.getPartnerType())) {
						 IBusinessPartnerDealerData bizPartnerDealerVO = businessPartnerService.getBusinessPartnerDealer(partnerId);
						 //IUserData mainContact = bizPartnerDealerVO.getMainContact();
						 IUserData mainContact = null;
						 if (mainContact != null){
							String fullName = (!StringUtil.isBlank(mainContact.getFirstName()) ? mainContact.getFirstName() : "") +
									(!StringUtil.isBlank(mainContact.getMiddleName()) ? " " + mainContact.getMiddleName() : "") +
										 (!StringUtil.isBlank(mainContact.getLastName()) ? " " + mainContact.getLastName() : "");
							proposalMap.put("mainContactName", fullName);
							proposalMap.put("contactMobilePhone", mainContact.getMobilePhone());
							proposalMap.put("contactEmail", mainContact.getEmail());
						 }
					}
				}
			}
		}

		String capValue = null;
		String ltv = null;
		boolean showUnderwriter = Boolean.getBoolean(projectProperties.getProperty("show.summary.screen.underwriter.labels", "false"));
		if (roleService.isInternalRole(Long.parseLong(getUserContainer().getRoleID())) && showUnderwriter) {
			capValue = formatService.formatDouble(assetService.getCapValue(appId), getUserContainer().getLocale());
			ltv = formatService.formatDouble(assetService.getLtv(appId), getUserContainer().getLocale());
			Long finalScore = applicationService.getFinalScore(appId);
			proposalMap.put("capValue", capValue);
			proposalMap.put("ltv", ltv);
			proposalMap.put("score", finalScore == null ? null : finalScore.toString());
			proposalMap.put("band", applicationService.getScoreBandCodeDesc(appId, getUserContainer().getLanguageCode(), getUserContainer().getCountryCode()));
		}


		proposalMap.put("proposalModuleAccess", modAccessMap);
		proposalMap.put("sectionModuleAccess", sectionModAccessMap);
		summaryMap.put("summary", proposalMap);

		if (applicationData != null) {
			String statusDesc = this.udtTransService.getUdtDesc(applicationData.getApplicationStatusCode(), IConstants.APPLICATION_STATUS_UDT_TABLE, this.getUserContainer().getCountryCode(), this.getUserContainer().getLanguageCode());
			if (!StringUtils.isBlank(statusDesc)) {
				summaryMap.put("appStatusDesc", statusDesc);
			}
		}

		summaryMap.put("buttonList", appButtonService.getApplicationButtons());
		summaryMap.put("sectionButtons", this.proposalSummaryService.getProposalSummaryButtons(MAX_JOINT_APPS, MAX_GUARANTORS, MAX_DIRECTORS, MAX_PARTNERS, MAX_EFFECTIVE_OWNERS));
		summaryMap.put("isShowingCreditStatus", appDisplayService.isShowingCreditStatus() ? IConstants.FLAG_YES : "");
		summaryMap.put("documentVersion", appContainer.getDocumentVersion() + "");

		return summaryMap;
	}

//	private Map<String,Integer> initModuleAccess() {
//		Map<String,Integer> modAccessMap = new HashMap<String,Integer>();
//
//		modAccessMap.put(IModuleConstants.MAIN_APPLICANT_MODULE_CODE, NO_ACCESS);
//		modAccessMap.put(IModuleConstants.CO_APPLICANT_MODULE_CODE, NO_ACCESS);
//		modAccessMap.put(IModuleConstants.DS_PROPOSAL_QUOTE_MODULE_CODE, NO_ACCESS);
//		//side bar nav buttons
//		modAccessMap.put(IModuleConstants.SUBMIT_FOR_DECISION_MODULE_CODE, NO_ACCESS);
//		modAccessMap.put(IModuleConstants.ACTIVATE_MODULE_CODE, NO_ACCESS); //Activate
//		modAccessMap.put(IModuleConstants.SUBMIT_TO_PAYOUT_MODULE_CODE, NO_ACCESS);
//		modAccessMap.put(IModuleConstants.PROPOSAL_SUMMARY_MODULE_CODE, NO_ACCESS);
//		modAccessMap.put(IModuleConstants.TO_DO_LIST_MODULE_CODE, NO_ACCESS);
//		modAccessMap.put(IModuleConstants.DS_DOCUMENTS_MODULE_CODE, NO_ACCESS);
//		modAccessMap.put(IModuleConstants.ACTION_MODULE_CODE, NO_ACCESS);
//		modAccessMap.put(IModuleConstants.NOTES_MODULE_CODE, NO_ACCESS);
//		modAccessMap.put(IModuleConstants.ASSIGN_SALESPERSON_MODULE_CODE, NO_ACCESS);
//		modAccessMap.put(IModuleConstants.SAVE_BUSPARTNER_MODULE_CODE, NO_ACCESS);
//		modAccessMap.put(IModuleConstants.TRACK_RECORD_MODULE_CODE, NO_ACCESS);
//		modAccessMap.put(IModuleConstants.SEND_BP_TO_CMS_MODULE_CODE, NO_ACCESS);
//		modAccessMap.put("simulation", NO_ACCESS);
//		modAccessMap.put("cra", NO_ACCESS);
//
//		return modAccessMap;
//	}

	private Map<String,Integer> initSectionModuleAccess() {
		Map<String,Integer> sectionModAccessMap = new HashMap<String,Integer>();

		sectionModAccessMap.put(IModuleConstants.AUDIT_MODULE_CODE, NO_ACCESS);
		sectionModAccessMap.put(IModuleConstants.PS_MAIN_APPLICANT_MODULE_CODE, NO_ACCESS);
		sectionModAccessMap.put(IModuleConstants.PS_CO_APPLICANT_MODULE_CODE, NO_ACCESS);
		sectionModAccessMap.put(IModuleConstants.PS_GUARANTOR_MODULE_CODE, NO_ACCESS);
		sectionModAccessMap.put(IModuleConstants.PS_FINANCE_DETAILS_MODULE_CODE, NO_ACCESS);
		sectionModAccessMap.put(IModuleConstants.PS_CREDIT_LINE_DETAILS_MODULE_CODE, NO_ACCESS);
		sectionModAccessMap.put(IModuleConstants.PS_SUPPORTING_DOC_DETAILS_MODULE_CODE, NO_ACCESS);

		return sectionModAccessMap;
	}

	private String getCustomerSummaryDetails (IApplicationCustomerDataVO customerData, IApplicationAddressDataVO addressData){
		StringBuffer details = new StringBuffer();

		String customerName =  getCustomerName(customerData);
		String address = getCustomerAddress(addressData);

		details.append(customerName);

		if (!StringUtils.isEmpty(customerName) && !StringUtils.isEmpty(address)){
//			details.append(" of ");
			String lblOf = this.translationService.getEntityTransNameByFieldNameAndControlPrefix("of", "summary", this.getUserContainer().getLanguageCode());
			details.append(" ").append(lblOf).append(" ");
		}

		details.append(address);

		return details.toString();
	}

	private String getCustomerName(IApplicationCustomerDataVO customerData){
//		StringBuffer custName = new StringBuffer();
//
//		custName.append(customerData.getTaxNum() + ", ");
//		if (customerData instanceof IApplicationConsumerDataVO){
//			IApplicationConsumerDataVO consumerData = (IApplicationConsumerDataVO) customerData;
//
//			if (!StringUtil.isEmpty(consumerData.getTitle())){
//				String titleDesc = udtTransService.getUdtDesc(consumerData.getTitle(), "TITLE", getUserContainer().getCountryCode(), getUserContainer().getLanguageCode());
//				custName.append(titleDesc + " ");
//			}
//			if (!StringUtil.isEmpty(customerData.getCommonName())) {
//				custName.append(customerData.getCommonName() + " ");
//			} else {
//				if (!StringUtil.isEmpty(consumerData.getFirstName())){
//					custName.append(consumerData.getFirstName() + " ");
//				}
//				if (!StringUtil.isEmpty(consumerData.getLastName())){
//					custName.append(consumerData.getLastName() + " ");
//				}
//			}
//			if (!StringUtil.isEmpty(consumerData.getTradeName())){
//				custName.append(consumerData.getTradeName() + " ");
//			}
//		} else if (customerData instanceof IApplicationCommercialDataVO) {
//			IApplicationCommercialDataVO commercialData = (IApplicationCommercialDataVO) customerData;
//
//			if (!StringUtil.isEmpty(commercialData.getTradeName())){
//				custName.append(commercialData.getTradeName() + " ");
//			}
//			if (!StringUtil.isEmpty(commercialData.getCommonName())){
//				custName.append(commercialData.getCommonName() + " ");
//			}
//		}
//		return custName.toString();
		return customerData.getTaxNum() + ", " + this.customerNameService.getCustomerName(customerData, true, false);
	}

	private String getCustomerAddress(IApplicationAddressDataVO addressData){
		String languageCode = this.getUserContainer().getLanguageCode();
		String countryCode = this.getUserContainer().getCountryCode();
		return customerService.getCustomerFullAddress(addressData, countryCode, languageCode);
			}

	private JPOSApplicantInfoDisplayVO getMainApplicantDisplay(JPOSProposalSummaryVO mainApplicant, String lblValidCust, String lblNotValidCust, String idNumber, boolean internalUserFlag, String existingCustomerFlag){
		JPOSApplicantInfoDisplayVO applicantInfoDisplay = new JPOSApplicantInfoDisplayVO();
		applicantInfoDisplay.setName(mainApplicant.getCustomerName());
		String mainApplicantLabel = this.translationService.getEntityTransLabelByFieldName("mainCustomerLabel", this.getUserContainer().getLanguageCode());
		applicantInfoDisplay.setApplicantType(mainApplicantLabel);
		applicantInfoDisplay.setIsValidate(IConstants.FLAG_YES.equals(mainApplicant.getSystemValidatedFlag()) ? lblValidCust : lblNotValidCust);
		applicantInfoDisplay.setSelected("");
		applicantInfoDisplay.setRefId(String.valueOf(IConstants.PRIMARY_APPLICANT_REF_ID));
		applicantInfoDisplay.setRoleCode(IConstants.APPLICANT_PRIMARY_ROLE_CODE);
		applicantInfoDisplay.setCustomerTypeCode(mainApplicant.getCustomerType());
		applicantInfoDisplay.setCreditScore(getCreditScore(mainApplicant.getCreditScore(), internalUserFlag));
		applicantInfoDisplay.setIdNumber(idNumber);
		applicantInfoDisplay.setAddress(getFormattedAddressByRefId(IConstants.PRIMARY_APPLICANT_REF_ID, mainApplicant.getCustomerType()));
		applicantInfoDisplay.setExistingCustomerFlag(getExistingCustomerFlagWithLabel(existingCustomerFlag));
		return applicantInfoDisplay;
	}

	private JPOSApplicantInfoDisplayVO getJointApplicantDisplay(CoApplicantDetailsDisplay jointApplicant,JPOSProposalSummaryVO mainApplicant, String lblValidCust, String lblNotValidCust, boolean internalUserFlag){
		JPOSApplicantInfoDisplayVO applicantInfoDisplay = new JPOSApplicantInfoDisplayVO();
		String jointApplicantLabel = this.translationService.getEntityTransLabelByFieldName("jointAppLabel", this.getUserContainer().getLanguageCode());
		String directorLabel = this.translationService.getEntityTransLabelByFieldName("directorLabel", this.getUserContainer().getLanguageCode());
		String partnerLabel = this.translationService.getEntityTransLabelByFieldName("busPartnerLabel", this.getUserContainer().getLanguageCode());
		if(mainApplicant.getCustomerType().equals(IConstants.COMMERCIAL_CUSTOMER_TYPE)) {
			if (IConstants.COAPPLICANT_ROLE_CODE.equals(jointApplicant.getCustomerRoleCode())) {
				applicantInfoDisplay.setApplicantType(jointApplicantLabel);
				if(IConstants.COMMERCIAL_CUSTOMER_TYPE.equals(jointApplicant.getCustomerTypeCode())){
					applicantInfoDisplay.setName(jointApplicant.getLegalName());
				}else {
					applicantInfoDisplay.setName(jointApplicant.getCommonName());
				}
			}
			else {
				applicantInfoDisplay.setApplicantType(directorLabel);
				applicantInfoDisplay.setName(jointApplicant.getName());
			}
		}else if (mainApplicant.getCustomerType().equals(IConstants.PARTNERSHIP_CUSTOMER_TYPE)){
			applicantInfoDisplay.setApplicantType(partnerLabel);
			applicantInfoDisplay.setName(jointApplicant.getName());
		} else {
			applicantInfoDisplay.setApplicantType(jointApplicantLabel);
			if(IConstants.COMMERCIAL_CUSTOMER_TYPE.equals(jointApplicant.getCustomerTypeCode())){
				applicantInfoDisplay.setName(jointApplicant.getLegalName());
			}else {
				applicantInfoDisplay.setName(jointApplicant.getCommonName());
			}
		}
		applicantInfoDisplay.setIsValidate(IConstants.FLAG_YES.equals(jointApplicant.getSystemValidatedFlag()) ? lblValidCust : lblNotValidCust);
		applicantInfoDisplay.setSelected("");
		applicantInfoDisplay.setRefId(jointApplicant.getRefid());
		applicantInfoDisplay.setRoleCode(jointApplicant.getCustomerRoleCode());
		applicantInfoDisplay.setRef2TypeCode(jointApplicant.getRef2TypeCode() != null? jointApplicant.getRef2TypeCode(): null);
		applicantInfoDisplay.setCustomerTypeCode(jointApplicant.getCustomerTypeCode());
		applicantInfoDisplay.setCreditScore(getCreditScore(jointApplicant.getCreditScore(), internalUserFlag));
		applicantInfoDisplay.setIdNumber(jointApplicant.getIdNumber());
		applicantInfoDisplay.setAddress(getFormattedAddressByRefId(Long.parseLong(jointApplicant.getRefid()), jointApplicant.getCustomerType()));
		applicantInfoDisplay.setExistingCustomerFlag(getExistingCustomerFlagWithLabel(jointApplicant.getExistingCustomerFlag()));
		return applicantInfoDisplay;
	}

	private JPOSApplicantInfoDisplayVO getGuarantorDisplay(CoApplicantDetailsDisplay guarantorApplicant, String lblValidCust, String lblNotValidCust, boolean internalUserFlag){
		JPOSApplicantInfoDisplayVO applicantInfoDisplay = new JPOSApplicantInfoDisplayVO();
		applicantInfoDisplay.setName(guarantorApplicant.getName());
		String guarantorLabel = this.translationService.getEntityTransLabelByFieldName("guarantorLabel", this.getUserContainer().getLanguageCode());
		applicantInfoDisplay.setApplicantType(guarantorLabel);
		applicantInfoDisplay.setIsValidate(IConstants.FLAG_YES.equals(guarantorApplicant.getSystemValidatedFlag()) ? lblValidCust : lblNotValidCust);
		applicantInfoDisplay.setSelected("");
		applicantInfoDisplay.setRefId(guarantorApplicant.getRefid());
		applicantInfoDisplay.setRoleCode(guarantorApplicant.getCustomerRoleCode());
		applicantInfoDisplay.setCustomerTypeCode(guarantorApplicant.getCustomerTypeCode());
		applicantInfoDisplay.setCreditScore(getCreditScore(guarantorApplicant.getCreditScore(), internalUserFlag));
		applicantInfoDisplay.setIdNumber(guarantorApplicant.getIdNumber());
		applicantInfoDisplay.setAddress(getFormattedAddressByRefId(Long.parseLong(guarantorApplicant.getRefid()), guarantorApplicant.getCustomerType()));
		applicantInfoDisplay.setExistingCustomerFlag(getExistingCustomerFlagWithLabel(guarantorApplicant.getExistingCustomerFlag()));
		return applicantInfoDisplay;
	}

	/**
	 * set the applicant info details for Effective Owner
	 * @param effectiveOwnerApplicant
	 * @param lblValidCust
	 * @param lblNotValidCust
	 * @return
	 */
	private JPOSApplicantInfoDisplayVO getEffectiveOwnerDisplay(CoApplicantDetailsDisplay effectiveOwnerApplicant, String lblValidCust, String lblNotValidCust, boolean internalUserFlag){
		JPOSApplicantInfoDisplayVO applicantInfoDisplay = new JPOSApplicantInfoDisplayVO();
		applicantInfoDisplay.setName(effectiveOwnerApplicant.getName());
		String effectiveOwnerLabel = this.translationService.getEntityTransLabelByFieldName("effectiveOwnerLabel", this.getUserContainer().getLanguageCode());
		applicantInfoDisplay.setApplicantType(effectiveOwnerLabel);
		applicantInfoDisplay.setIsValidate(IConstants.FLAG_YES.equals(effectiveOwnerApplicant.getSystemValidatedFlag()) ? lblValidCust : lblNotValidCust);
		applicantInfoDisplay.setSelected("");
		applicantInfoDisplay.setRefId(effectiveOwnerApplicant.getRefid());
		applicantInfoDisplay.setRoleCode(effectiveOwnerApplicant.getCustomerRoleCode());
		applicantInfoDisplay.setCustomerTypeCode(effectiveOwnerApplicant.getCustomerTypeCode());
		applicantInfoDisplay.setRef2TypeCode(effectiveOwnerApplicant.getRef2TypeCode());
		applicantInfoDisplay.setCreditScore(getCreditScore(effectiveOwnerApplicant.getCreditScore(),internalUserFlag));
		applicantInfoDisplay.setIdNumber(effectiveOwnerApplicant.getIdNumber());
		applicantInfoDisplay.setAddress(getFormattedAddressByRefId(Long.parseLong(effectiveOwnerApplicant.getRefid()), effectiveOwnerApplicant.getCustomerType()));
		applicantInfoDisplay.setExistingCustomerFlag(getExistingCustomerFlagWithLabel(effectiveOwnerApplicant.getExistingCustomerFlag()));
		return applicantInfoDisplay;
	}

	private String getFormattedAddressByRefId(long refId, String customerTypeCode) {
		IApplicationAddressDataVO addressData = customerService.getCustomerAddress( getAppContainer().getAppID(), refId, customerTypeCode );
		return getCustomerAddress(addressData);
	}

	private String getCreditScore(String creditScore, boolean internalUserFlag) {
		String result = "";
		if (internalUserFlag) {
			result = translationService.getEntityTransLabelByFieldName("creditScore", getUserContainer().getLanguageCode());
			if (creditScore != null)
				result += " " + creditScore;
		}
		return result;
	}

	private String getExistingCustomerFlagWithLabel(String existingCustomerFlag) {
		String result = translationService.getEntityTransLabelByFieldName("existingCustomerFlag", getUserContainer().getLanguageCode());
		if (existingCustomerFlag != null && IConstants.FLAG_YES.equals(existingCustomerFlag)) {
			result += " " + existingCustomerFlag;
		}
		else {
			result += " " + IConstants.FLAG_NO;
		}
		return result;
	}

}
