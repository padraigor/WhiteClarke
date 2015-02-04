package com.wcna.calms.jpos.services.quote;

import java.util.HashMap;
import java.util.Map;


import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.component.IThirdPartyLookupService;
import com.wcna.calms.service.application.IApplicationAddressData;
import com.wcna.calms.service.application.IApplicationService;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.common.IUserContainer;
import com.wcna.calms.service.thirdparty.IThirdPartyApplicationService;
import com.wcna.calms.service.thirdparty.ITpdbCustomerInfo;
import com.wcna.lang.StringUtil;

public class JPOSQuickQuoteCustomerDetailLoadService extends CalmsAjaxService{

	private final IApplicationService applicationService;
	private final IThirdPartyLookupService tpLookupService;
	private final IThirdPartyApplicationService bpmApplicationService;

	public JPOSQuickQuoteCustomerDetailLoadService(IApplicationService applicationService,IThirdPartyApplicationService bpmApplicationService,IThirdPartyLookupService tpLookupService){
		this.applicationService = applicationService;
		this.bpmApplicationService = bpmApplicationService;
		this.tpLookupService = tpLookupService;
	}
	public Object invoke(Object arg0) {
		Map rtnMap = new HashMap();
		Map input = (Map)arg0;
		String thirdPartyId = null;
		String addressAlfaId = null;
		long refId = IConstants.PRIMARY_APPLICANT_REF_ID;
		String custTypeCode = (String) input.get("custTypeCode");
		String custSubTypeCode = (String) input.get("custSubTypeCode");
		String appTypeCode = (String) input.get("appTypeCode");
		String custRoleCode = (String) input.get("custRoleCode");
		String proposalId = (String)input.get("proposalId");
		String appSubTypeCode = (String) input.get("appSubTypeCode");
		String ref2TypeCode = (String) input.get("ref2TypeCode");
		Object tpId = input.get("tpId");
		Object addrAlfaId = input.get("addressAlfaId");
		if (input.get("refId") != null) {
			refId = Long.parseLong( ((String)input.get("refId")) );
		}

		JPOSQuickQuoteCustomerVO screenVo = new JPOSQuickQuoteCustomerVO();
	//	IJPOSQuickQuoteCustomerForm form = new JPOSQuickQuoteCustomerForm();
		if (tpId != null) thirdPartyId = (String)tpId;
		if (addrAlfaId != null) addressAlfaId = (String)addrAlfaId;
		if (StringUtil.isEmpty(appTypeCode)) appTypeCode = IConstants.PROPOSAL_APPLICATION_TYPE;
		if (StringUtil.isEmpty(custRoleCode)) custRoleCode = IConstants.APPLICANT_PRIMARY_ROLE_CODE;

		IUserContainer userContainer = getUserContainer();
		if (thirdPartyId != null) {
			ITpdbCustomerInfo tpCustInfo = tpLookupService.retrieveThirdPartyInfo(Long.parseLong(thirdPartyId), addressAlfaId, appTypeCode, custTypeCode, userContainer.getLocale());
			screenVo.setCustomerType(tpCustInfo.getCUSTOMER_TYPE());
			screenVo.setTpId(thirdPartyId);
			IApplicationAddressData addressVo = tpCustInfo.getCurrAddress();
		// IApplicationAddressDataVO addressVo = (IApplicationAddressDataVO) tpCustInfo.getCurrAddress();
		if (IConstants.SOLE_TRADER_CUSTOMER_TYPE.equals(custTypeCode)) {
			screenVo.setTradingAs(tpCustInfo.getTRADE_NAME());
			screenVo.setTitle(tpCustInfo.getTITLE());
			screenVo.setForename(tpCustInfo.getFIRST_NAME());
			screenVo.setInitial(tpCustInfo.getMIDDLE_INITIAL());
			screenVo.setSurname(tpCustInfo.getLAST_NAME());
			screenVo.setName(tpCustInfo.getFIRST_NAME()+ " " + tpCustInfo.getLAST_NAME());
			screenVo.setNationalityId(tpCustInfo.getNATIONALITY_CODE());
			screenVo.setTaxNum(tpCustInfo.getTAX_NUM());
			screenVo.setDateOfBirth(tpCustInfo.getDATE_OF_BIRTH());
		} else if (IConstants.CONSUMER_CUSTOMER_TYPE.equals(custTypeCode)) {
			screenVo.setTitle(tpCustInfo.getTITLE());
			screenVo.setForename(tpCustInfo.getFIRST_NAME());
			screenVo.setInitial(tpCustInfo.getMIDDLE_INITIAL());
			screenVo.setSurname(tpCustInfo.getLAST_NAME());
			screenVo.setName(tpCustInfo.getFIRST_NAME()+ " " + tpCustInfo.getLAST_NAME());
			screenVo.setTaxNum(tpCustInfo.getTAX_NUM());
			screenVo.setNationalityId(tpCustInfo.getNATIONALITY_CODE());
			screenVo.setDateOfBirth(tpCustInfo.getDATE_OF_BIRTH());
		} else if (IConstants.PARTNERSHIP_CUSTOMER_TYPE.equals(custTypeCode) || IConstants.COMMERCIAL_CUSTOMER_TYPE.equals(custTypeCode)) {
			screenVo.setCompany(tpCustInfo.getCOMMON_NAME());
			screenVo.setTaxNum(tpCustInfo.getTAX_ID_NUMBER());
		//	screenVo.setRegistrationCode(tpCustInfo.getRegistrationCode());
		//	screenVo.setEstablishedDate(custVo.getEstablishedDate());
			addressVo = (IApplicationAddressData)tpCustInfo.getBillingAddressList().get(0);
		}

		screenVo.setPostCode(addressVo.getZipPostal());
		screenVo.setBuildingName(addressVo.getAddressLine3());
		screenVo.setNumber(addressVo.getStreetNumber());
		screenVo.setStreet(addressVo.getStreetName());
		screenVo.setLocality(addressVo.getCity());
		screenVo.setPostTown(addressVo.getCounty());
		screenVo.setCountry(addressVo.getCountry());
		screenVo.setAddressLine2(addressVo.getAddressLine2());
		screenVo.setCity(addressVo.getCity());
		screenVo.setZipPostal(addressVo.getZipPostal());
		screenVo.setZipPostal2(addressVo.getZipPostal2());
		screenVo.setStateProvince(addressVo.getStateProvince());
		screenVo.setAddressLine3(addressVo.getAddressLine3());
		screenVo.setAddressLine4(addressVo.getAddressLine4());
		screenVo.setStreetName(addressVo.getStreetName());
		screenVo.setCounty(addressVo.getCounty());
		screenVo.setSuiteNumber(addressVo.getSuiteNumber());
		screenVo.setStreetNumber(addressVo.getStreetNumber());
		}
		rtnMap.put("customerInfo", screenVo);
		return rtnMap;
	}


}
