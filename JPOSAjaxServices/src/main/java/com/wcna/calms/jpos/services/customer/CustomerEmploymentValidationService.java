package com.wcna.calms.jpos.services.customer;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import org.apache.commons.lang.math.NumberUtils;

import com.wcg.exception.SystemException;
import com.wcg.product.services.session.GenericMessage;
import com.wcna.calms.jpos.services.application.IJPOSScreenConstants;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.validation.CustomValidationItem;
import com.wcna.calms.service.validation.CustomValidationResult;
import com.wcna.calms.service.validation.ICustomValidator;
import com.wcna.calms.service.validation.IRegexValidationService;
import com.wcna.lang.StringUtil;

public class CustomerEmploymentValidationService implements ICustomValidator {

	private final IRegexValidationService regexValidationService;
	private int validationType;
	private final Properties projectProperties;

	public CustomerEmploymentValidationService(
			IRegexValidationService regexValidationService,Properties projectProperties) {
		this.regexValidationService = regexValidationService;
		this.projectProperties = projectProperties;
	}
	public void setValidationType(int validationType) {
		this.validationType = validationType;
	}

    public CustomValidationResult validate(Map<String, Object> map,
            Locale locale) {
    	 CustomValidationResult validationResult = new CustomValidationResult();


		String triggerAction = "";
		Object actionObj = map.get(IConstants.TR_ACTION_KEY);
		if (actionObj != null && !StringUtil.isEmpty(String.valueOf(actionObj))) {
			triggerAction = String.valueOf(actionObj);
		}
		if (IJPOSScreenConstants.ACTION_SAVE_NO_VALIDATION.equals(triggerAction)) {
			return validationResult;
		}
		if(validationType==0){
			validateEmploymentData1(map, locale, validationResult);
	        validateEmploymentAddressData(map, locale, validationResult);
	        return validationResult;
		}
		if(validationType==1){
			validateEmploymentData2(map, locale, validationResult);
	        validateEmploymentAddressData(map, locale, validationResult);
	        return validationResult;
			}
		if(validationType==2){
			validateEmploymentData3(map, locale, validationResult);
	        validateEmploymentAddressData(map, locale, validationResult);
	        return validationResult;
			}
		if(validationType ==3){
    		validateEmploymentAddressData1(map,locale,validationResult);
    		return validationResult;
    	}
		if(validationType == 5) {
    		return validationResult;
		}
		throw new SystemException("Invalid validation type " + validationType);
    	}

    private void validateEmploymentData3(Map<String, Object> map, Locale locale, CustomValidationResult validationResult) {
    	Map<String, String> emplObj = (Map<String, String>) map. get(IGenericScreenConstants.APPLICATION_EMPLOYMENT);
    	int cnt = 0;
            String occupationCode = (String)emplObj.get(IGenericScreenConstants.INCOME_SOURCE_TYPE_FIELD_NAME);
          //  String employmentType = (String)emplObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_TYPE_CODE);

            Map<String, String> emplPrevObj = (Map<String, String>) map.get(IGenericScreenConstants.APPLICATION_PREVIOUS_EMPLOYMENT);

            String occupationCodePre = (String)emplPrevObj.get(IGenericScreenConstants.INCOME_SOURCE_TYPE_FIELD_NAME);
            int yearWithCurrentEmployment = NumberUtils.toInt(emplObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_YEAR_WITH_EMPLOYER));
    		int monthWithCurrentEmployemnt = NumberUtils.toInt(emplObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_MONTH_WITH_EMPLOYER));
    		int maxYears = NumberUtils.toInt((String) projectProperties.get("customerScreenEmploymentDetailConfiguration." + IGenericScreenConstants.APPLICATION_EMPLOYMENT + ".showWhenLessThanYears"), 100);
    		int maxMonths = NumberUtils.toInt((String) projectProperties.get("customerScreenEmploymentDetailConfiguration." + IGenericScreenConstants.APPLICATION_EMPLOYMENT + ".showWhenLessThanMonths"), 12);

    		if (StringUtil.isEmpty(occupationCode)) {
				CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE,
						IGenericScreenConstants.INCOME_SOURCE_TYPE_FIELD_NAME,
						IGenericScreenConstants.APPLICATION_EMPLOYMENT);
				validationItem.setIndex(cnt);
				validationResult.addValidationItem(validationItem);
			}
            if("UI".equals(occupationCode) || "VV".equals(occupationCode) || "RR".equals(occupationCode)|| "HH".equals(occupationCode) || "UN".equals(occupationCode) || "HM".equals(occupationCode)){

            	if (StringUtil.isBlank((String) emplObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_YEAR_WITH_EMPLOYER))) {
					CustomValidationItem validationItem = new CustomValidationItem(
							IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_YEAR_WITH_EMPLOYER,
							IGenericScreenConstants.APPLICATION_PREVIOUS_EMPLOYMENT);
					validationResult.addValidationItem(validationItem);
				}
    			if (StringUtil.isBlank((String) emplObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_MONTH_WITH_EMPLOYER))) {
					CustomValidationItem validationItem = new CustomValidationItem(
							IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_MONTH_WITH_EMPLOYER,
							IGenericScreenConstants.APPLICATION_PREVIOUS_EMPLOYMENT);
					validationResult.addValidationItem(validationItem);
				}
            }
            else
            {
            	if (StringUtil.isEmpty((String)emplObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYER_NAME_FIELD_NAME))) {
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.INCOME_SOURCE_EMPLOYER_NAME_FIELD_NAME,
							IGenericScreenConstants.APPLICATION_EMPLOYMENT);
					validationItem.setIndex(cnt);
					validationResult.addValidationItem(validationItem);
				}

            	if (StringUtil.isEmpty((String)emplObj.get(IGenericScreenConstants.INCOME_SOURCE_GROSS_PAY_MONTHLY_FIELD_NAME))) {
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.INCOME_SOURCE_GROSS_PAY_MONTHLY_FIELD_NAME,
							IGenericScreenConstants.APPLICATION_EMPLOYMENT);
					validationItem.setIndex(cnt);
					validationResult.addValidationItem(validationItem);
				}
            	if (StringUtil.isBlank((String) emplObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_TYPE_CODE))) {
    				CustomValidationItem validationItem = new CustomValidationItem(
    						IGenericScreenConstants.REQUIRED_ERROR_CODE,
    						IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_TYPE_CODE,
    						IGenericScreenConstants.APPLICATION_EMPLOYMENT);
    				validationResult.addValidationItem(validationItem);
    			}
            	if (StringUtil.isBlank((String) emplObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_YEAR_WITH_EMPLOYER))) {
					CustomValidationItem validationItem = new CustomValidationItem(
							IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_YEAR_WITH_EMPLOYER,
							IGenericScreenConstants.APPLICATION_PREVIOUS_EMPLOYMENT);
					validationResult.addValidationItem(validationItem);
				}
    			if (StringUtil.isBlank((String) emplObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_MONTH_WITH_EMPLOYER))) {
					CustomValidationItem validationItem = new CustomValidationItem(
							IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_MONTH_WITH_EMPLOYER,
							IGenericScreenConstants.APPLICATION_PREVIOUS_EMPLOYMENT);
					validationResult.addValidationItem(validationItem);
				}
            }

            if (yearWithCurrentEmployment< maxYears && monthWithCurrentEmployemnt<maxMonths) {

            	if (StringUtil.isBlank((String)emplPrevObj.get(IGenericScreenConstants.INCOME_SOURCE_TYPE_FIELD_NAME))) {
    				CustomValidationItem validationItem = new CustomValidationItem(
    						IGenericScreenConstants.REQUIRED_ERROR_CODE,
    						IGenericScreenConstants.INCOME_SOURCE_TYPE_FIELD_NAME,
    						IGenericScreenConstants.APPLICATION_PREVIOUS_EMPLOYMENT);
    				validationResult.addValidationItem(validationItem);
    			}

    			if ("UI".equals(occupationCodePre) || "HH".equals(occupationCodePre) || "RR".equals(occupationCodePre) || "UN".equals(occupationCodePre) || "VV".equals(occupationCodePre) || "HM".equals(occupationCodePre)) {
    				if (StringUtil.isBlank((String) emplPrevObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_YEAR_WITH_EMPLOYER))) {
    					CustomValidationItem validationItem = new CustomValidationItem(
    							IGenericScreenConstants.REQUIRED_ERROR_CODE,
    							IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_YEAR_WITH_EMPLOYER,
    							IGenericScreenConstants.APPLICATION_PREVIOUS_EMPLOYMENT);
    					validationResult.addValidationItem(validationItem);
    				}
        			if (StringUtil.isBlank((String) emplPrevObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_MONTH_WITH_EMPLOYER))) {
    					CustomValidationItem validationItem = new CustomValidationItem(
    							IGenericScreenConstants.REQUIRED_ERROR_CODE,
    							IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_MONTH_WITH_EMPLOYER,
    							IGenericScreenConstants.APPLICATION_PREVIOUS_EMPLOYMENT);
    					validationResult.addValidationItem(validationItem);
    				}
    			}
    			else {
    				if (StringUtil.isBlank((String) emplPrevObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_TYPE_CODE))) {
    					CustomValidationItem validationItem = new CustomValidationItem(
    							IGenericScreenConstants.REQUIRED_ERROR_CODE,
    							IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_TYPE_CODE,
    							IGenericScreenConstants.APPLICATION_PREVIOUS_EMPLOYMENT);
    					validationResult.addValidationItem(validationItem);
    				}
    				if (StringUtil.isEmpty((String)emplPrevObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYER_NAME_FIELD_NAME))) {
    					CustomValidationItem validationItem = new CustomValidationItem(
    							IGenericScreenConstants.REQUIRED_ERROR_CODE,
    							IGenericScreenConstants.INCOME_SOURCE_EMPLOYER_NAME_FIELD_NAME,
    							IGenericScreenConstants.APPLICATION_PREVIOUS_EMPLOYMENT);
    					validationResult.addValidationItem(validationItem);
    				}
    				if (StringUtil.isBlank((String) emplPrevObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_YEAR_WITH_EMPLOYER))) {
    					CustomValidationItem validationItem = new CustomValidationItem(
    							IGenericScreenConstants.REQUIRED_ERROR_CODE,
    							IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_YEAR_WITH_EMPLOYER,
    							IGenericScreenConstants.APPLICATION_PREVIOUS_EMPLOYMENT);
    					validationResult.addValidationItem(validationItem);
    				}
        			if (StringUtil.isBlank((String) emplPrevObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_MONTH_WITH_EMPLOYER))) {
    					CustomValidationItem validationItem = new CustomValidationItem(
    							IGenericScreenConstants.REQUIRED_ERROR_CODE,
    							IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_MONTH_WITH_EMPLOYER,
    							IGenericScreenConstants.APPLICATION_PREVIOUS_EMPLOYMENT);
    					validationResult.addValidationItem(validationItem);
    				}
    			}
            }
    }
    private void validateEmploymentData2(Map<String, Object> map, Locale locale, CustomValidationResult validationResult) {
    	Map emplObj = (Map) map.get(IGenericScreenConstants.APPLICATION_EMPLOYMENT);
            String occupationCode = (String)emplObj.get(IGenericScreenConstants.INCOME_SOURCE_TYPE_FIELD_NAME);
          //  String employmentType = (String)emplObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_TYPE_CODE);
            if (StringUtil.isEmpty(occupationCode)) {
				CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE,
						IGenericScreenConstants.INCOME_SOURCE_TYPE_FIELD_NAME,
						IGenericScreenConstants.APPLICATION_EMPLOYMENT);
				validationResult.addValidationItem(validationItem);
			}
            if(occupationCode.equals("HO") || occupationCode.equals("SD") || occupationCode.equals("UI")|| occupationCode.equals("UN") || occupationCode.equals("PR")){

            }
            else
            {
            	if (StringUtil.isEmpty((String)emplObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYER_NAME_FIELD_NAME))) {
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.INCOME_SOURCE_EMPLOYER_NAME_FIELD_NAME,
							IGenericScreenConstants.APPLICATION_EMPLOYMENT);
					validationResult.addValidationItem(validationItem);
				}
            	if (StringUtil.isEmpty((String)emplObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_YEAR_WITH_EMPLOYER))) {
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_YEAR_WITH_EMPLOYER,
							IGenericScreenConstants.APPLICATION_EMPLOYMENT);
					validationResult.addValidationItem(validationItem);
				}
            	if (StringUtil.isEmpty((String)emplObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_MONTH_WITH_EMPLOYER))) {
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_MONTH_WITH_EMPLOYER,
							IGenericScreenConstants.APPLICATION_EMPLOYMENT);
					validationResult.addValidationItem(validationItem);
				}
            	if (StringUtil.isEmpty((String)emplObj.get(IGenericScreenConstants.INCOME_SOURCE_GROSS_PAY_MONTHLY_FIELD_NAME))) {
					CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.INCOME_SOURCE_GROSS_PAY_MONTHLY_FIELD_NAME,
							IGenericScreenConstants.APPLICATION_EMPLOYMENT);
					validationResult.addValidationItem(validationItem);
				}
            }
    }
    /*
     * if employment type is 'CONTRATO' or 'EFECTIVO', some fields are required
     */
    private void validateEmploymentData1(Map<String, Object> map, Locale locale, CustomValidationResult validationResult) {
        //GenericMessage genericMessage = null;

        Map emplObj = (Map) map.get(IGenericScreenConstants.APPLICATION_EMPLOYMENT);
        if (emplObj != null) {
            String appType = map.get("appType")==null? "":(String)map.get("appType");
            String employmentType = (String)emplObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_TYPE_CODE);
            if (!appType.equals("A") && (IJPOSScreenConstants.APP_EMPLOYMENT_TYPE_CONTRATO.equals(employmentType) ||
                    IJPOSScreenConstants.APP_EMPLOYMENT_TYPE_EFECTIVO.equals(employmentType))) {
                if (StringUtil.isEmpty((String)emplObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYER_NAME_FIELD_NAME))) {
                	CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE, IGenericScreenConstants.INCOME_SOURCE_EMPLOYER_NAME_FIELD_NAME, IGenericScreenConstants.APPLICATION_EMPLOYMENT);
                	validationResult.addValidationItem(validationItem);
                    /*genericMessage = translationService.getGenericMessage(IGenericScreenConstants.REQUIRED_ERROR_CODE,
                            languageCode,
                            IGenericScreenConstants.INCOME_SOURCE_EMPLOYER_NAME_FIELD_NAME,
                            IGenericScreenConstants.APPLICATION_EMPLOYMENT,
                            GenericMessage.ERROR);
                    errorMessageList.add(genericMessage);
                    ((IErrorMessage)SessionManager.getInstance().getSessionData().getMessageStore()).addMessageMetaData(
                            IGenericScreenConstants.APPLICATION_EMPLOYMENT,
                            IGenericScreenConstants.INCOME_SOURCE_EMPLOYER_NAME_FIELD_NAME, null);*/
                }
                if (StringUtil.isEmpty((String)emplObj.get(IGenericScreenConstants.INCOME_SOURCE_GROSS_PAY_MONTHLY_FIELD_NAME))) {
                	CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE, IGenericScreenConstants.INCOME_SOURCE_GROSS_PAY_MONTHLY_FIELD_NAME, IGenericScreenConstants.APPLICATION_EMPLOYMENT);
                	validationResult.addValidationItem(validationItem);
                    /*genericMessage = translationService.getGenericMessage(IGenericScreenConstants.REQUIRED_ERROR_CODE,
                            languageCode,
                            IGenericScreenConstants.INCOME_SOURCE_GROSS_PAY_MONTHLY_FIELD_NAME,
                            IGenericScreenConstants.APPLICATION_EMPLOYMENT,
                            GenericMessage.ERROR);
                    errorMessageList.add(genericMessage);
                    ((IErrorMessage)SessionManager.getInstance().getSessionData().getMessageStore()).addMessageMetaData(
                            IGenericScreenConstants.APPLICATION_EMPLOYMENT,
                            IGenericScreenConstants.INCOME_SOURCE_GROSS_PAY_MONTHLY_FIELD_NAME, null);*/
                }

            }
        }
    }

    /*
     * if employment type is 'CONTRATO' or 'EFECTIVO', employment address fields are required
     */
    private void validateEmploymentAddressData(Map<String, Object> map, Locale locale, CustomValidationResult validationResult) {
//        GenericMessage genericMessage = null;

        Map emplObj = (Map) map.get(IGenericScreenConstants.APPLICATION_EMPLOYMENT);
        Map emplAddrObj = (Map) map.get(IGenericScreenConstants.APPLICATION_EMPLOYMENT_ADDRESS);

        if (emplAddrObj != null) {
            String employmentType = (String)emplObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_TYPE_CODE);				if (IJPOSScreenConstants.APP_EMPLOYMENT_TYPE_CONTRATO.equals(employmentType) ||
                    IJPOSScreenConstants.APP_EMPLOYMENT_TYPE_EFECTIVO.equals(employmentType)) {

                if (StringUtil.isEmpty((String)emplAddrObj.get(IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME)) ||
                        StringUtil.isEmpty((String)emplAddrObj.get(IGenericScreenConstants.RESIDENCE_ZIP2_FIELD_NAME))) {
                	CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE, IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME, IGenericScreenConstants.APPLICATION_EMPLOYMENT_ADDRESS);
                	validationResult.addValidationItem(validationItem);
                    /*genericMessage = translationService.getGenericMessage(IGenericScreenConstants.REQUIRED_ERROR_CODE,
                            languageCode,
                            IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME,
                            IGenericScreenConstants.APPLICATION_EMPLOYMENT_ADDRESS,
                            GenericMessage.ERROR);
                    errorMessageList.add(genericMessage);
                    ((IErrorMessage)SessionManager.getInstance().getSessionData().getMessageStore()).addMessageMetaData(
                            IGenericScreenConstants.APPLICATION_EMPLOYMENT_ADDRESS,
                            IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME, null);*/
                }
                if (StringUtil.isEmpty((String)emplAddrObj.get(IGenericScreenConstants.APP_ADDRESS_LINE2))) {
                	CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE, IGenericScreenConstants.APP_ADDRESS_LINE2, IGenericScreenConstants.APPLICATION_EMPLOYMENT_ADDRESS);
                	validationResult.addValidationItem(validationItem);
                    /*genericMessage = translationService.getGenericMessage(IGenericScreenConstants.REQUIRED_ERROR_CODE,
                            languageCode,
                            IGenericScreenConstants.APP_ADDRESS_LINE2,
                            IGenericScreenConstants.APPLICATION_EMPLOYMENT_ADDRESS,
                            GenericMessage.ERROR);
                    errorMessageList.add(genericMessage);
                    ((IErrorMessage)SessionManager.getInstance().getSessionData().getMessageStore()).addMessageMetaData(
                            IGenericScreenConstants.APPLICATION_EMPLOYMENT_ADDRESS,
                            IGenericScreenConstants.APP_ADDRESS_LINE2, null);*/
                }
                if (StringUtil.isEmpty((String)emplAddrObj.get(IGenericScreenConstants.RESIDENCE_CITY_FIELD_NAME))) {
                	CustomValidationItem validationItem = new CustomValidationItem(IGenericScreenConstants.REQUIRED_ERROR_CODE, IGenericScreenConstants.RESIDENCE_CITY_FIELD_NAME, IGenericScreenConstants.APPLICATION_EMPLOYMENT_ADDRESS);
                	validationResult.addValidationItem(validationItem);
                    /*genericMessage = translationService.getGenericMessage(IGenericScreenConstants.REQUIRED_ERROR_CODE,
                            languageCode,
                            IGenericScreenConstants.RESIDENCE_CITY_FIELD_NAME,
                            IGenericScreenConstants.APPLICATION_EMPLOYMENT_ADDRESS,
                            GenericMessage.ERROR);
                    errorMessageList.add(genericMessage);
                    ((IErrorMessage)SessionManager.getInstance().getSessionData().getMessageStore()).addMessageMetaData(
                            IGenericScreenConstants.APPLICATION_EMPLOYMENT_ADDRESS,
                            IGenericScreenConstants.RESIDENCE_CITY_FIELD_NAME, null);*/
                }
            }
        }
    }

	private void validateEmploymentAddressData1(Map<String, Object> map,
			Locale locale, CustomValidationResult validationResult) {

		Map<String, String> empDetails = (Map<String, String>) map
				.get(IGenericScreenConstants.APPLICATION_EMPLOYMENT);
		Map<String, String> prevEmpDetails = (Map<String, String>) map
				.get(IGenericScreenConstants.APPLICATION_PREVIOUS_EMPLOYMENT);

		Map<String, String> prevEmpAddressDetails = (Map<String, String>) map
				.get(IGenericScreenConstants.APPLICATION_PREVIOUS_EMPLOYMENT_ADDRESS);

		validateEmploymentAddress(validationResult, map,
				IGenericScreenConstants.APPLICATION_EMPLOYMENT,
				IGenericScreenConstants.APPLICATION_EMPLOYMENT_ADDRESS);

		int totalMonthsOfResidence = 0;
		totalMonthsOfResidence = getTotalMonthsOfResidence(empDetails,
				totalMonthsOfResidence);
		totalMonthsOfResidence = getTotalMonthsOfResidence(prevEmpDetails,
				totalMonthsOfResidence);

		if ((totalMonthsOfResidence < 36) && (isEmployed(empDetails))) {

			if (StringUtils
					.isBlank(prevEmpDetails
							.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_TYPE_CODE))) {
				CustomValidationItem validationItem = new CustomValidationItem(
						IJPOSScreenConstants.TOTAL_MONTHS_EMPLOYMENT_ERROR_CODE,
						"",
						IGenericScreenConstants.APPLICATION_EMPLOYMENT_ADDRESS,
						new String[] { String.valueOf(totalMonthsOfResidence) });
				validationResult.addValidationItem(validationItem);
			}
			String employmentTypeCode = ((String) prevEmpDetails
					.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_TYPE_CODE));
			if (StringUtils.isBlank(employmentTypeCode)) {
				CustomValidationItem validationItem1 = new CustomValidationItem(
						IGenericScreenConstants.REQUIRED_ERROR_CODE,
						IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_TYPE_CODE,
						IGenericScreenConstants.APPLICATION_PREVIOUS_EMPLOYMENT);
				validationResult.addValidationItem(validationItem1);
			}

			validateEmploymentAddress(
					validationResult,
					map,
					IGenericScreenConstants.APPLICATION_PREVIOUS_EMPLOYMENT,
					IGenericScreenConstants.APPLICATION_PREVIOUS_EMPLOYMENT_ADDRESS);
		}
	}

	private boolean isEmployed(Map<String, String> employmentDetails) {
		String employmentTypeCode = ((String) employmentDetails
				.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_TYPE_CODE));
		if ((StringUtils.isBlank(employmentTypeCode)) ||(employmentTypeCode.equals(IConstants.EMP_TYPE_UNEMPLOYED))
				|| (employmentTypeCode.equals(IConstants.EMP_TYPE_RETIRED))
				|| (employmentTypeCode.equals(IConstants.EMP_TYPE_HOMEMAKER))
				|| (employmentTypeCode.equals(IConstants.EMP_TYPE_STUDENT))) {
			return false;
		}
		return true;

	}
	private int getTotalMonthsOfResidence(Map<String, String> address, int total) {
		if (address != null) {
			int yearWithCurrentEmployment = NumberUtils
					.toInt(address
							.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_YEAR_WITH_EMPLOYER));
			int monthWithCurrentEmployemnt = NumberUtils
					.toInt(address
							.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_MONTH_WITH_EMPLOYER));
			total += yearWithCurrentEmployment * 12;
			total += monthWithCurrentEmployemnt;
		}
		return total;
	}
	private void validateEmploymentAddress(
			CustomValidationResult validationResult, Map<String, Object> map,
			String empDetails, String empAddress) {
		Map<String,String> emplObj = (Map<String,String>) map.get(empDetails);
		Map<String,String> emplAddrObj = (Map<String,String>) map.get(empAddress);
		if ((emplObj != null) && (isEmployed(emplObj))) {
			if (StringUtils
					.isBlank((String)emplObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_DESCRIPTION_FIELD_NAME))) {
				CustomValidationItem validationItem = new CustomValidationItem(
						IGenericScreenConstants.REQUIRED_ERROR_CODE,
						IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_DESCRIPTION_FIELD_NAME,
						empDetails);
				validationResult.addValidationItem(validationItem);
			}
			if (StringUtils
					.isBlank((String)emplObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYER_NAME_FIELD_NAME))) {
				CustomValidationItem validationItem = new CustomValidationItem(
						IGenericScreenConstants.REQUIRED_ERROR_CODE,
						IGenericScreenConstants.INCOME_SOURCE_EMPLOYER_NAME_FIELD_NAME,
						empDetails);
				validationResult.addValidationItem(validationItem);
			}
			if (emplAddrObj != null) {
				if (StringUtil
						.isBlank((String) emplAddrObj
								.get(IGenericScreenConstants.RESIDENCE_STREET_NUMBER_FIELD_NAME))
						&& StringUtil
								.isEmpty((String) emplAddrObj
										.get(IGenericScreenConstants.APP_ADDRESS_LINE3))) {
					CustomValidationItem validationItem = new CustomValidationItem(
							IGenericScreenConstants.REQUIRED_CURR_EMP_ADDRESS_ERROR_CODE,
							"", empAddress);
					validationResult.addValidationItem(validationItem);
				}
				if (StringUtils
						.isBlank((String)emplAddrObj.get(IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME))) {
					CustomValidationItem validationItem = new CustomValidationItem(
							IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.RESIDENCE_ZIP_FIELD_NAME,
							empAddress);
					validationResult.addValidationItem(validationItem);
				}
				if (StringUtils
						.isBlank((String)emplAddrObj.get(IGenericScreenConstants.RESIDENCE_STREET_NAME_FIELD_NAME))) {
					CustomValidationItem validationItem = new CustomValidationItem(
							IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.RESIDENCE_STREET_NAME_FIELD_NAME,
							empAddress);
					validationResult.addValidationItem(validationItem);
				}
				if (StringUtils
						.isBlank((String)emplAddrObj.get(IGenericScreenConstants.APP_ADDRESS_LINE4))) {
					CustomValidationItem validationItem = new CustomValidationItem(
							IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.APP_ADDRESS_LINE4,
							empAddress);
					validationResult.addValidationItem(validationItem);
				}

	    		if (StringUtils
						.isBlank((String)emplObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_YEAR_WITH_EMPLOYER))) {
					CustomValidationItem validationItem = new CustomValidationItem(
							IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_YEAR_WITH_EMPLOYER,
							empDetails);
					validationResult.addValidationItem(validationItem);
				}

	    		if (StringUtils
						.isBlank((String)emplObj.get(IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_MONTH_WITH_EMPLOYER))) {
					CustomValidationItem validationItem = new CustomValidationItem(
							IGenericScreenConstants.REQUIRED_ERROR_CODE,
							IGenericScreenConstants.INCOME_SOURCE_EMPLOYMENT_MONTH_WITH_EMPLOYER,
							empDetails);
					validationResult.addValidationItem(validationItem);
				}
			}
		}

	}
}
