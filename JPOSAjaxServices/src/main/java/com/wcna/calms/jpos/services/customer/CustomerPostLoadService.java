package com.wcna.calms.jpos.services.customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.app.IClientConstants;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.customer.ICustomerShowHideService;
import com.wcna.calms.service.udt.IUdtTableValueData;
import com.wcna.calms.service.udt.IUdtTranslationService;
import com.wcna.calms.util.BeanConverterUtil;
import com.wcna.lang.StringUtil;

public class CustomerPostLoadService extends CalmsAjaxService {

	private final IUdtTranslationService udtTransService;
	private final BeanConverterUtil beanUtil;
	private final ICustomerShowHideService showHideService;

	public CustomerPostLoadService(IUdtTranslationService udtTransService, ICustomerShowHideService showHideService) {
		this.udtTransService = udtTransService;
		this.showHideService = showHideService;
		this.beanUtil = new BeanConverterUtil();
	}

	public Object invoke(Object data) {

		Map dataMap = null;

		if (data instanceof Map)
			dataMap = (Map)data;

		if (dataMap == null)
			return data;
		int totalBanks = NumberUtils.toInt((String) projectProperties.get(IClientConstants.CUSTOMER_BANKSECTION_COUNT), 2);
		String screenCode = (String)dataMap.get("screenCode");

		/* output 2 banks: CHQ(Primary); SAV(Other) in this order */
		/*
		 * After discussing with David Ji on 12/06/2012, we found out this part of business logic is originally
		 * designed for Fiat Portugal. So I have been told to comment them out because Fiat Portugal is moved
		 * to branch.
		 */
		Map customer = (Map)dataMap.get(screenCode);/*
		Object bankObj = customer.get(IGenericScreenConstants.APPLICATION_BANK_LIST);
		if (bankObj != null) {
			TreeMap<String,IApplicationBankDataVO> newMap = new TreeMap<String,IApplicationBankDataVO>();
			List bankList = (List)bankObj;
			for (Iterator itr=bankList.iterator(); itr.hasNext();) {
				IApplicationBankDataVO bank = (IApplicationBankDataVO)itr.next();
				if (IConstants.BANK_ACCOUNT_TYPE_CHECKING.equals(bank.getAccountTypeCode()) ||
						IConstants.BANK_ACCOUNT_TYPE_SAVING.equals(bank.getAccountTypeCode())) {
					newMap.put(bank.getAccountTypeCode(), bank);
				}
			}
			int newBanks = totalBanks - newMap.size();
			if (newBanks > 0) { //if only 1 bank saved, assume second one is saving
				for (int i = 0; i < newBanks; i++) {
					IApplicationBankDataVO bank = createBean(IApplicationBankDataVO.class);
					newMap.put(IConstants.BANK_ACCOUNT_TYPE_SAVING + "_" + i, bank);
				}
			}
			if (!newMap.isEmpty()) {
//				customer.put(IGenericScreenConstants.APPLICATION_BANK_LIST, newMap.values());
				List<IApplicationBankDataVO> list = new ArrayList<IApplicationBankDataVO>(newMap.values());
				customer.put(IGenericScreenConstants.APPLICATION_BANK_LIST, list);
			}
		}
*/
		String custDataKey = getCustomerDataKey(customer);

		boolean isIgnoreDefaults = false;
		String s = (String) dataMap.get("isIgnoreDefaults");
		if (IConstants.FLAG_YES.equals(s)) {
			isIgnoreDefaults = true;
		}
		if (!isIgnoreDefaults) {
			Map custMap = (Map)customer.get(custDataKey);

			Object sysValidatedFlagObj = custMap.get(IGenericScreenConstants.SYSTEM_VALIDATED_FLAG_FIELD_NAME);
			/* set some default values if it's new */
			showHideService.showHide(custMap, getUserContainer());
			if (sysValidatedFlagObj == null || StringUtil.isEmpty((String)sysValidatedFlagObj)) {
				if (custDataKey.indexOf("consumer") > -1) {
					IUdtTableValueData udtTableValueData = udtTransService.getDefaultUDT(getUserContainer().getCountryCode(), "NATIONALITY");
					if (udtTableValueData != null) {
						custMap.put(IGenericScreenConstants.NATIONALITY_CODE_FIELD_NAME, udtTableValueData.getCode());
					}
					udtTableValueData = udtTransService.getDefaultUDT(getUserContainer().getCountryCode(), "CUSTOMER_COUNTRY");
					if (udtTableValueData != null) {
						custMap.put(IGenericScreenConstants.BIRTH_COUNTRY_CODE_FIELD_NAME, udtTableValueData.getCode());
					}
				}
				setDefaultCountry(customer, customer.get(IGenericScreenConstants.APPLICATION_ADDRESS));
				setDefaultCountry(customer, customer.get(IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS));
				setDefaultCountry(customer, customer.get(IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS2));

				IUdtTableValueData udtTableValueData = udtTransService.getDefaultUDT(getUserContainer().getCountryCode(), "PAYMENT_METHOD");
				Object banks = customer.get(IGenericScreenConstants.APPLICATION_BANK_LIST);
				if (banks != null) {
					Collection<Map<String, String>> bankList = (Collection<Map<String, String>>)banks;
					/* first one is always CHQ */
					for (Map<String, String> bank : bankList) {
						if (StringUtil.isEmpty(bank.get(IGenericScreenConstants.BANK_PAYMENT_METHOD_CODE)) && udtTableValueData != null) {
							bank.put(IGenericScreenConstants.BANK_PAYMENT_METHOD_CODE, udtTableValueData.getCode());
							break;
						}
					}
					/*for (Iterator itr=bankList.iterator(); itr.hasNext();) {
						IApplicationBankDataVO bank = (IApplicationBankDataVO) itr.next();
						if (udtTableValueData != null) {
							bank.setPaymentMethodCode(udtTableValueData.getCode());
							break;
						}
					}*/
				}
			}
		}
		return dataMap;
	}

	private String getCustomerDataKey(Map customer) {
		String custDataKey = "";
		for (Iterator itr=customer.keySet().iterator(); itr.hasNext();) {
			String key = (String)itr.next();
			if (key.startsWith(IGenericScreenConstants.APPLICATION_CUSTOMER + IGenericScreenConstants.DELIMITER + IGenericScreenConstants.DELIMITER_TYPE)) {
				custDataKey = key;
				break;
			}
		}
		return custDataKey;
	}

	private void setDefaultCountry(Map customer, Object addresses) {

		if(addresses != null){
			HashMap address = (HashMap)addresses;
			IUdtTableValueData udtTableValueData = udtTransService.getDefaultUDT(getUserContainer().getCountryCode(), "LEGAL_LOCATION");
			if(udtTableValueData != null){
				address.put(IGenericScreenConstants.RESIDENCE_COUNTRY_FIELD_NAME, udtTableValueData.getCode());
			}
		}
	}

}