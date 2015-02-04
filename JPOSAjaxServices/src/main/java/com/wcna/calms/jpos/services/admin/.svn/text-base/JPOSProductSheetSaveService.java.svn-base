package com.wcna.calms.jpos.services.admin;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.common.util.Logger;
import com.wcg.product.calms.security.CalmsSessionData;
import com.wcg.product.services.session.GenericMessage;
import com.wcg.product.services.session.SessionManager;
import com.wcna.calms.jpos.services.productsheet.IProductSheetDataLoadService;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.calms.util.BeanConverterUtil;
import com.wcna.lang.NumberUtil;
import com.wcna.lang.StringUtil;

public class JPOSProductSheetSaveService extends CalmsAjaxService {

	private IJPOSAdminSheetService service;
	private IProductSheetDataLoadService uploadService;
	private IFormatService formatService;

	public JPOSProductSheetSaveService(IJPOSAdminSheetService service, IProductSheetDataLoadService uploadService, IFormatService formatService) {
		this.service = service;
		this.uploadService = uploadService;
        this.formatService = formatService;
	}

	@SuppressWarnings("unchecked")
	public Object invoke(Object object) {
		HashMap<String,Object> returnMap = new HashMap<String,Object>();

		if (object instanceof Map) {
			Map<String, Object> dataMap = (Map<String, Object>) object;
			String saveType = (String)dataMap.get("saveType");
			if ("saveGroup".equalsIgnoreCase(saveType)){
				returnMap = this.saveExistingProductSheetGroup(returnMap, dataMap);
			}else if ("saveUpload".equalsIgnoreCase(saveType)){
				returnMap = this.saveUploadedProductSheet(returnMap, dataMap);
			}
		}
		return returnMap;
	}

	/**
	 * save the existing product sheet group
	 * @param returnMap
	 * @param dataMap
	 * @return
	 */
	private HashMap<String, Object> saveExistingProductSheetGroup(HashMap<String, Object> returnMap, Map<String, Object> dataMap){
		Logger.debugBeginMessage(this.getClass(), "saveUploadedProductSheet()");
		boolean isSaved = false;
		int productSheetGroupId = 0;
		int productSheetId = 0;
		String errorType = "system";
		String productSheetGroupIdString = (String)dataMap.get("productSheetGroupId");
		if (!StringUtil.isBlank(productSheetGroupIdString)){
			productSheetGroupId = NumberUtil.toIntPrimitive(productSheetGroupIdString, 0);
		}
		try{
			List rlArray = (List)dataMap.get("productSheetValidDatesList");
			if (rlArray!=null){
				Object[] validDates = rlArray.toArray();
				if (validDates != null && validDates.length > 0) {
					boolean isUpdated = true;
					for(int i=0; i<validDates.length; i++) {
						Map jsonObject = (Map) validDates[i];
						if (jsonObject != null){
							String dateValidFromString = (String)jsonObject.get("dateStart");
							String dateValidToString = (String)jsonObject.get("dateEnd");
							String productSheetIdString = (String)jsonObject.get("productSheetId");
							productSheetId = NumberUtil.toIntPrimitive(productSheetIdString, 0);
							Date dateValidFrom = (Date)formatService.parseDate(dateValidFromString, this.getUserContainer().getLocale(), true, null);
							Date dateValidTo = (Date)formatService.parseDate(dateValidToString, this.getUserContainer().getLocale(), true, null);
							if (!isValidDateRange(dateValidFrom, dateValidTo)){
								isUpdated = false;
								errorType = "dateRange";
								break;
							}
							isUpdated = service.updateProductSheetDates(productSheetId, dateValidFrom, dateValidTo);
						}
					}
					if (isUpdated){
						isSaved = true;
					}
				}
			}
		}catch(ParseException pe){
			errorType = "dateFormat";
		}catch(Exception ex){
			errorType = "system";
			ex.printStackTrace();
		}
		if (!isSaved){
			returnMap.put("status", "failed");
			returnMap.put("errorType", errorType);
		}else{
			returnMap.put("status", "ok");
			returnMap.put("productSheetGroupId", productSheetGroupId + "");
		}
		Logger.debugEndMessage(this.getClass(), "saveUploadedProductSheet()");
		return returnMap;
	}

	/**
	 * save the new product sheet group by uploading product sheet
	 * @param returnMap
	 * @param dataMap
	 * @return
	 */
	private HashMap<String, Object> saveUploadedProductSheet(HashMap<String, Object>returnMap, Map<String, Object> dataMap){
		Logger.debugBeginMessage(this.getClass(), "saveExistingProductSheetGroup()");
    	SessionManager sessionManager = SessionManager.getInstance();
//    	HttpSession session = (HttpSession) sessionManager.getExternalSession();
		boolean isSaved = false;
		int productSheetGroupId = 0;
		byte[] fileData = null;
    	String errorType = "system";

    	String productSheetGroupIdString = (String)dataMap.get("productSheetGroupId");
		String productSheetGroupNameString = getProductSheetGroupName((String)dataMap.get("productSheetGroupName"));
//		String dateValidFromString = getFormattedDateString((String)dataMap.get("dateValidFrom"));
//		String dateValidToString = getFormattedDateString((String)dataMap.get("dateValidTo"));
//		String dateValidFromString = getFormattedDateString((String)dataMap.get("dateValidFromHidden"));
//		String dateValidToString = getFormattedDateString((String)dataMap.get("dateValidToHidden"));
		String dateValidFromString = (String)dataMap.get("dateValidFromHidden");
		String dateValidToString = (String)dataMap.get("dateValidToHidden");

		if (StringUtil.isBlank(productSheetGroupIdString)){
			productSheetGroupId = service.createProductSheetGroup(productSheetGroupNameString);
		}else {
			productSheetGroupId = NumberUtil.toIntPrimitive(productSheetGroupIdString, 0);
		}
		if (productSheetGroupId != 0){
			try{

				Date dateValidFrom = (Date) formatService.parseDate(dateValidFromString, this.getUserContainer().getLocale(), true, null);
				Date dateValidTo = (Date) formatService.parseDate(dateValidToString, this.getUserContainer().getLocale(), true, null);
				if (this.isValidDateRange(dateValidFrom, dateValidTo)){
//					fileData = (byte[]) session.getAttribute("uploadFileBytes");
					fileData = service.getBytesFromStorage();
					uploadService.saveCsvFile(fileData);
					String filePathAndName = uploadService.getAbsoluteCsvFileName();
					uploadService.loadProductSheetFile(productSheetGroupId, dateValidFrom, dateValidTo, filePathAndName, this.getUserContainer().getUserID());
					isSaved = true;
				}else{
					errorType = "dateRange";
				}
			}catch(ParseException pe){
				errorType = "dateFormat";
			}catch(Exception ex){
				errorType = "system";
//				ex.printStackTrace();
				Logger.debug(ex);
			}
		}
		if (!isSaved){
			returnMap.put("status", "failed");
			returnMap.put("errorType", errorType);
		}else{
			returnMap.put("status", "ok");
			returnMap.put("productSheetGroupId", productSheetGroupId + "");
		}
		Logger.debugEndMessage(this.getClass(), "saveExistingProductSheetGroup()");
		return returnMap;
	}

	private String getProductSheetGroupName(String productSheetGroupNameString){
		Logger.debugBeginMessage(this.getClass(), "getProductSheetGroupName()");

		String productSheetGroupName = null;
		productSheetGroupName = productSheetGroupNameString.replaceAll("%20", " ");

		Logger.debugEndMessage(this.getClass(), "getProductSheetGroupName()");
		return productSheetGroupName;
	}

//	private String getFormattedDateString(String dateString){
//		Logger.debugBeginMessage(this.getClass(), "getFormattedDateString()");
//		String formattedDateString = null;
//		int index1 = dateString.indexOf("-");
//		int index2 = dateString.lastIndexOf("-");
//
//		formattedDateString = dateString.substring(index2 + 1) + "/" +
//								dateString.substring(index1 + 1, index2) + "/" +
//								dateString.substring(0, index1);
//
//		Logger.debugEndMessage(this.getClass(), "getFormattedDateString()");
//		return formattedDateString;
//	}

	private boolean isValidDateRange(Date from, Date to){
		Logger.debugBeginMessage(this.getClass(), "isValidDateRange()");
		if (from != null && to != null){
			if (to.getTime() >= from.getTime()){
				return true;
			}
		}
		Logger.debugEndMessage(this.getClass(), "isValidDateRange()");
		return false;
	}

}
