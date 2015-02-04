package com.wcna.calms.jpos.services.quote;

import java.util.HashMap;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.component.IThirdPartyLookupService;
import com.wcna.calms.service.application.IApplicationAddressDataVO;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.thirdparty.ITpdbCustomerInfo;
import com.wcna.lang.StringUtil;
import com.wcna.util.SystemException;

public class JPOSQuickQuoteSelTPDBCustomerService extends CalmsAjaxService {

	private final IThirdPartyLookupService tpLookupService;
	private final IJPOSQuickQuoteService jposQuickQuoteService;
	
	public JPOSQuickQuoteSelTPDBCustomerService(IThirdPartyLookupService tpLookupService, IJPOSQuickQuoteService jposQuickQuoteService) {
		this.tpLookupService = tpLookupService;
		this.jposQuickQuoteService = jposQuickQuoteService;
	}
	
	public Object invoke(Object arg0) {
		if (arg0 != null && arg0 instanceof Map) {
			Map input = (Map) arg0;
			Map customerMap = (Map) input.get("customerDetails");
			JPOSQuickQuoteCustomerVO custVo = new JPOSQuickQuoteCustomerVO();
//			IJPOSQuickQuoteCustomerForm custVo = this.jposQuickQuoteService.getCustomerData();
			
			try {
				org.apache.commons.beanutils.BeanUtils.populate(custVo, customerMap);
			} catch (Exception e) {
				throw new SystemException(e);
			}			
			custVo.setTpId((String) input.get("tpId"));
			custVo.setTpAddressId((String) input.get("tpAddressId"));
			custVo.setCustSubTypeCode((String) input.get("custSubTypeCode"));
			
			// even though we pass lead application type, from the service impl I cannot see it being used anywhere
			ITpdbCustomerInfo custInfo = this.tpLookupService.retrieveThirdPartyInfo(Long.parseLong(custVo.getTpId()), custVo.getTpAddressId(), 
																		             IConstants.LEAD_APPLICATION_TYPE, custVo.getCustomerType(), 
																		             this.getUserContainer().getLocale());
			
			if (custInfo != null) {
				
//				if (IConstants.SOLE_TRADER_CUSTOMER_TYPE.equals(custVo.getCustomerType())) {
//					custVo.setTradingAs(custInfo.getTRADE_NAME());
//				} else 
				// consumer and sole trader should use the same format
				if (IConstants.CONSUMER_CUSTOMER_TYPE.equals(custVo.getCustomerType())
						|| IConstants.SOLE_TRADER_CUSTOMER_TYPE.equals(custVo.getCustomerType())) {
					custVo.setForename(custInfo.getFIRST_NAME());
					custVo.setInitial(custInfo.getMIDDLE_INITIAL());
					custVo.setSurname(custInfo.getLAST_NAME());
//					custVo.setName(custInfo.getCOMMON_NAME());
					String name = StringUtil.toNotNullString(custInfo.getFIRST_NAME()) + " " + StringUtil.toNotNullString(custInfo.getMIDDLE_INITIAL()) + " " + StringUtil.toNotNullString(custInfo.getLAST_NAME());
					// for sole trader, the front end expects 'trading as' property
					if (IConstants.CONSUMER_CUSTOMER_TYPE.equals(custVo.getCustomerType())) {
						custVo.setName(name);
					} else {
						custVo.setTradingAs(name);
					}
					custVo.setTitle(custInfo.getTITLE());
				} else if (IConstants.PARTNERSHIP_CUSTOMER_TYPE.equals(custVo.getCustomerType())
								|| IConstants.COMMERCIAL_CUSTOMER_TYPE.equals(custVo.getCustomerType())) {
					custVo.setCompany(custInfo.getCOMMON_NAME());
				}
				
				IApplicationAddressDataVO addressData = this.jposQuickQuoteService.getAddressDataFromTpdb(custVo.getCustomerType(), custInfo);
				if (addressData != null) {
					custVo.setPostCode(addressData.getZipPostal());
					custVo.setBuildingName(addressData.getSuiteNumber());
					custVo.setNumber(addressData.getStreetNumber());
					custVo.setStreet(addressData.getStreetName());
					custVo.setLocality(addressData.getCity());
					custVo.setPostTown(addressData.getCounty());
					custVo.setCountry(addressData.getCountry());
					custVo.setAddressLine2(addressData.getAddressLine2());
					custVo.setCity(addressData.getCity());
					custVo.setZipPostal(addressData.getZipPostal());
					custVo.setZipPostal2(addressData.getZipPostal2());
				}
				
				Map<String, IJPOSQuickQuoteCustomerForm> ret = new HashMap<String, IJPOSQuickQuoteCustomerForm>();
				ret.put("custInfo", custVo);
				
				return ret;
			}
			
		}
		return null;
	}

}
