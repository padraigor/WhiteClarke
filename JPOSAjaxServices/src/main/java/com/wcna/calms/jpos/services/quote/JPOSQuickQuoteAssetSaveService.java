package com.wcna.calms.jpos.services.quote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.common.util.Logger;
import com.wcg.product.services.session.SessionManager;
import com.wcna.calms.jpos.services.asset.IVehiclePreSaveValidationService;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.framework.validation.IBusinessRuleValidationService;
import com.wcna.util.SystemException;

public class JPOSQuickQuoteAssetSaveService extends CalmsAjaxService implements IBusinessRuleValidationService  {
	private final IJPOSQuickQuoteAssetService quickQuoteAssetService;
	private final JPOSQuoteUtil jposQuoteUtil;
	private final IFormatService formatService;
	//private final CalmsAjaxService preSaveService;
	private final IVehiclePreSaveValidationService preSaveService;
	private IJPOSQuickQuoteService jposQuickQuoteService;


	public JPOSQuickQuoteAssetSaveService(
			IJPOSQuickQuoteAssetService quickQuoteAssetService,
			IVehiclePreSaveValidationService preSaveService, JPOSQuoteUtil jposQuoteUtil,
			IFormatService formatService, IJPOSQuickQuoteService jposQuickQuoteService) {
		this.quickQuoteAssetService = quickQuoteAssetService;
		this.preSaveService = preSaveService;
		this.jposQuoteUtil = jposQuoteUtil;
		this.formatService = formatService;
		this.jposQuickQuoteService= jposQuickQuoteService;
	}

	public Object invoke(Object arg0) {
		Map inputMap = (Map)arg0;
		int quoteIdx = this.jposQuoteUtil.getQuoteIdx(inputMap);
		String screenCode = this.getScreenCode(arg0);
		JPOSQuickQuoteAssetVO inputVO = new JPOSQuickQuoteAssetVO();
		Map<String, String> quickQuoteAssetMap = (Map<String, String>)inputMap.get("quickQuoteAsset");

		String invokedByPricesScr="0";
		Locale locale = getUserContainer().getLocale();
		try {
		if(inputMap.get("invokedByPricesScr")!=null){
		invokedByPricesScr = (String) inputMap.get("invokedByPricesScr");
		}
		}catch(Exception e){
			Logger.error(e.getMessage(),e);
		}


		try {
//			System.err.println("=========>JPOSQuickQuoteAssetSaveService.invoke ((1.3)) screenCode=" + screenCode);
			Map<String, String> screenFieldAccesses= this.entityAccessService.getAccess(screenCode);
			if(screenFieldAccesses != null) {
				Set<String> fieldNames = new HashSet<String>(quickQuoteAssetMap.keySet());
				for(String fieldName : fieldNames) {
					if( !this.entityAccessService.isWritable( screenFieldAccesses.get(fieldName) ) ) {
//						System.err.println("=========>JPOSQuickQuoteAssetSaveService.invoke ((2)) field["+fieldName+"] has been removed.");
						quickQuoteAssetMap.remove(fieldName);
					}
				}
			}
			org.apache.commons.beanutils.BeanUtils.populate(inputVO, quickQuoteAssetMap);
		} catch (Exception e) {
			Logger.error(e.getMessage(),e);
		}
//		String quoteIndex = inputVO.getQuoteIndex();
//		IQuoteContainer qc = ((CalmsSessionData)SessionManager.getInstance().getSessionData()).getQuoteContainer();
//		IQuoteDataVO quoteVo = qc.getQuote(new Integer(quoteIndex).intValue());

		// validation passed,
		if (SessionManager.getInstance().getSessionData().getMessageStore().getMessages() == null ||
				SessionManager.getInstance().getSessionData().getMessageStore().getMessages().isEmpty()) {
			inputVO.setVin(inputVO.getVin().toUpperCase());

			List<IRentACarBean> rentACarList = this.getRentACarList((Map)inputMap.get("quickQuoteAsset"));
			List<IVehicleFFOBean> ffoBeanList = null;
			if (rentACarList == null || rentACarList.isEmpty()) {
				ffoBeanList = this.getFFOList(inputMap, inputVO);
			}

			this.quickQuoteAssetService.setAssetFormToContainer(quoteIdx, inputVO);
			this.quickQuoteAssetService.setRentACarListToContainer(quoteIdx, rentACarList);
			this.quickQuoteAssetService.setFFOListToContainer(quoteIdx, ffoBeanList);

			if(invokedByPricesScr.equals("1")){
				this.saveQuoteAssetDetails(inputVO);
			}


		}

		return null;
	}


	private boolean hasRentACar(Map<String, Object> assetMap) {
		boolean bRet = false;
		List<Map<String, Object>> rentACarList = (List<Map<String, Object>>) assetMap.get("rentACarList");
		if (rentACarList != null && !rentACarList.isEmpty()) {
			bRet = true;
		}
		return bRet;
	}

	private List<IVehicleFFOBean> getFFOList(Map inputMap, IJPOSQuickQuoteAssetInputForm inputVO) {
		List<IVehicleFFOBean> ffoBeanList = null;
		List<Map> ffoList = null;
		inputVO.setTotalRvUpliftPercentage(null);//this is to reset in case the selected ffo are unselected the second time.
		if (inputMap.containsKey("ffoList") && inputMap.get("ffoList") instanceof List) {
			ffoList = (List) inputMap.get("ffoList");
			if (ffoList != null && !ffoList.isEmpty()) {
				ffoBeanList = new ArrayList<IVehicleFFOBean>();
				int size = ffoList.size();
				double totUpliftPer = 0;
				String rvUplift = "";
				Locale locale = this.getUserContainer().getLocale();
				for (int i = 0; i < size; i++) {
					Map ffo = ffoList.get(i);
					IVehicleFFOBean bean = createBean(IVehicleFFOBean.class);
					bean.setDescription((String) ffo.get("description"));
					bean.setId((String) ffo.get("id"));
					bean.setListPrice((String) ffo.get("listPrice"));
//					bean.setOverride((String) ffo.get("override"));
					bean.setSalePrice((String) ffo.get("salePrice"));
					// the widget will only send selected ffo's
					bean.setSelect(IConstants.FLAG_YES);
					ffoBeanList.add(bean);
				}
				inputVO.setTotalRvUpliftPercentage(quickQuoteAssetService.getFFOTotalUpLiftPercentageAsString(ffoBeanList, inputVO.getModelVariantId()));
			}
		}
		return ffoBeanList;
	}

	private List<IRentACarBean> getRentACarList(Map<String, Object> assetDetailsMap) {
		List<IRentACarBean> ret = null;

		if (assetDetailsMap != null && assetDetailsMap.containsKey("rentACarList")) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) assetDetailsMap.get("rentACarList");
			if (list != null && !list.isEmpty()) {
				ret = new ArrayList<IRentACarBean>();
				for (Map<String, Object> map : list) {
					IRentACarBean bean = this.createBean(IRentACarBean.class);
					ret.add(bean);

					try {
						org.apache.commons.beanutils.BeanUtils.populate(bean, map);
					} catch (Exception e) {
						throw new SystemException(e);
					}
				}
			}
		}

		return ret;
	}

	public boolean validate(Map objectMap){
		if (preSaveService != null){
			objectMap.put(IGenericScreenConstants.SCREEN_CODE, this.getScreenCode(objectMap));
			preSaveService.validate(objectMap);
		}
		if (SessionManager.getInstance().getSessionData().getMessageStore().getMessages().isEmpty()){
			return true;
		}
		return false;
	}




	private void saveQuoteAssetDetails(IJPOSQuickQuoteAssetInputForm inputVO){
		Locale locale = this.getUserContainer().getLocale();
			String ret = this.jposQuickQuoteService.savePrices(inputVO);



	}
}
