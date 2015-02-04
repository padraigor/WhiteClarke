package com.wcna.calms.jpos.services.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.service.customer.CommercialCustomerLoad;
import static com.wcna.calms.app.IClientConstants.*;
import static com.wcna.calms.service.application.IGenericScreenConstants.*;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.customer.IGenericCommercialService;
import com.wcna.lang.StringUtil;

public class JPOSCopyCompanyAddressService extends CommercialCustomerLoad {

    private CalmsAjaxService postLoadService;
    private String screenCode;

    public String getScreenCode() {
        String ret;
        if ("A".equals(this.getAppContainer().getAppTypeCode())
                && screenCode != null
                && !"Bpm".equals(screenCode.substring(0, 3))) {
            ret = "Bpm" + screenCode;
        } else {
            ret = screenCode;
        }
        return ret;
    }

    public void setScreenCode(String screenCode) {
        this.screenCode = screenCode;
    }

    public JPOSCopyCompanyAddressService(IGenericCommercialService commercialService, CalmsAjaxService postLoadService) {
        super(commercialService);
        this.postLoadService = postLoadService;
    }

    @SuppressWarnings("unchecked")
    public Object invoke(Object object) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        if (object instanceof Map) {
            String contextualScreenCode = this.getScreenCode();

            /*
             * Jira WCGCALMSTWO-3972:
             * We need to preserve the Ref ID (the KeyId in the UserContainer), otherwise the value
             * will be wrong when the next customer load happens.
             */
            Object keyId = getUserContainer().getKeyId();
            try {
                map = (HashMap<String, Object>) super.invoke(object);
            } finally {
                getUserContainer().setKeyId(keyId);
            }

            invokePostLoadService(map, contextualScreenCode);

            updateScreenCode(map, contextualScreenCode);

            HashMap<String, Object> sectionMap = new HashMap<String, Object>();

            copyAddress(map, contextualScreenCode, sectionMap);
            copyPhoneList(map, contextualScreenCode, sectionMap);

            resultMap.put(contextualScreenCode, sectionMap);
        }
        return resultMap;
    }

    @SuppressWarnings("unchecked")
    private <T> T getTwoDeepMapValue(Map<String,Object> map, String key1, String key2) {
        return (T) ((Map<String,Object>) map.get(key1)).get(key2);
    }

    /**
     * Copies the business address from the main app to the current address and business address of coapplicants
     */
    private void copyAddress(HashMap<String, Object> map,
                             String contextualScreenCode,
                             HashMap<String, Object> sectionMap) {
        Map<String,Object> appBillingAddress;

        appBillingAddress = getTwoDeepMapValue(map,
                                               contextualScreenCode,
                                               APPLICATION_BILLING_ADDRESS);

        sectionMap.put(APPLICATION_BILLING_ADDRESS, appBillingAddress);
        sectionMap.put(APPLICATION_ADDRESS,         appBillingAddress);
    }

    private void copyPhoneList(HashMap<String, Object> map,
    		                   String contextualScreenCode,
    		                   HashMap<String, Object> sectionMap) {
        String phoneTypes;
        List<Map<String,Object>> phoneList;
        phoneTypes = (String) projectProperties.get(COPY_PHONE_DETAILS_FROM_COMPANY_MAIN_APP);

        phoneList = getTwoDeepMapValue(map,
                                       contextualScreenCode,
                                       APP_CUSTOMER_PHONE_LIST);

        //Copies the phone number from the main app to the coapplicant
        if (StringUtil.isBlank(phoneTypes) || phoneList == null) {
            sectionMap.put(APP_CUSTOMER_PHONE_LIST, phoneList);
        } else {
            boolean addedAPhone = false;
            String[] phoneType = phoneTypes.split(",");
            List<Map<String,Object>> phoneArray = new ArrayList<Map<String,Object>>();

            for (Map<String, Object> phone : phoneList) {
                String phoneTypeCode = (String) phone.get(APP_CUSTOMER_PHONE_TYPE_FIELD_NAME);

                for (int i = 0; i < phoneType.length; i++) {
                    if (!StringUtil.isBlank(phoneTypeCode) && phoneTypeCode.equals(phoneType[i])) {
                        phoneArray.add(phone);
                        addedAPhone = true;
                        break;
                    }
                }
            }

            if (addedAPhone) {
                sectionMap.put(APP_CUSTOMER_PHONE_LIST, phoneArray);
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Map getData() {
        Map<String,Object> ret = super.getData();
        if (this.postLoadService != null) {
            ret.put("screenCode", this.getScreenCode());
            ret.put("isIgnoreDefaults", IConstants.FLAG_YES);
            this.postLoadService.invoke(ret);
            ret.remove("screenCode");
            ret.remove("isIgnoreDefaults");
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    private void invokePostLoadService(HashMap<String, Object> map, String contextualScreenCode) {
        if (postLoadService != null) {
            map.put("screenCode", contextualScreenCode);
            map = (HashMap<String, Object>) postLoadService.invoke(map);
            map.remove("screenCode");
        }
    }

    @SuppressWarnings("rawtypes")
    private void updateScreenCode(HashMap<String, Object> map, String contextualScreenCode) {
        if ("A".equals(this.getAppContainer().getAppTypeCode())) {
            Map tempMap = (Map) map.get(contextualScreenCode);
            map.put(this.screenCode, tempMap);
            map.remove(contextualScreenCode);
        }
    }
}
