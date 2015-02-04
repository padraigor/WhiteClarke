package com.wcna.calms.jpos.services.quote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.product.calms.security.CalmsSessionData;
import com.wcg.product.services.session.SessionManager;
import com.wcna.util.SystemException;

public class JPOSQuickQuoteSimulationCalcService extends CalmsAjaxService {

	private final JPOSQuickQuoteLoadService quickQuoteLoadService;
	private final IJPOSQuickQuoteService jposQuickQuoteService;

	public JPOSQuickQuoteSimulationCalcService(JPOSQuickQuoteLoadService quickQuoteLoadService, IJPOSQuickQuoteService jposQuickQuoteService) {
		this.quickQuoteLoadService = quickQuoteLoadService;
		this.jposQuickQuoteService = jposQuickQuoteService;
	}

	public Object invoke(Object parameter) {

		IQuoteVehicleBean ret = null;
		if (getAppContainer() != null && getAppContainer().getAppID() > 0) {
			String term = null;
			String deposit = null;
			if (parameter != null && parameter instanceof Map) {
				Map pmap = (Map) parameter;
				term = (String) pmap.get("term");
				deposit = (String) pmap.get("deposit");
			}

			try {
				Object obj = loadQuote();
				JPOSQuickQuoteInputVO quoteInputVO = getQuoteInputVO(obj);
				IJPOSQuickQuoteInputForm quoteIn = createBean(IJPOSQuickQuoteInputForm.class);
				BeanUtils.copyProperties(quoteInputVO, quoteIn);

				quoteIn.setTerm(term);
				quoteIn.setDeposit(deposit);

				IJPOSQuickQuoteAssetInputForm assetVo = this.jposQuickQuoteService.getAssetInputForm(0);
				String capCode = null;
				if (!"on".equals(assetVo.getFreeFormatFlag())) {
					capCode = jposQuickQuoteService.getCapCodeFromModelVariantId(assetVo.getModelVariantId(), this.getUserContainer().getLocale());
				}
				IVehicleRequest vehicleIn = jposQuickQuoteService.prepareVehicleRequest(assetVo, this.getUserContainer().getLocale(), null);
				IDealer dealerIn = jposQuickQuoteService.prepareDealerRequest();
				IQuoteRequest quoteReq = jposQuickQuoteService.prepareQuoteRequest(
						quoteIn,
						0,
						getUserContainer().getLocale(),
						capCode,
						jposQuickQuoteService.getCustomerData().getCustomerType(),
						vehicleIn,
						null);

				ValidVapsContainer vvc = jposQuickQuoteService.getValidVapsFromContainer(0);


				try {
					IQuoteVehicleBeanWrapper wrapper = jposQuickQuoteService.calculate(
							quoteReq,
							vehicleIn,
							dealerIn,
							getUserContainer().getLocale(),
							getVapItems(vvc),
							vvc,
							Long.parseLong(getUserContainer().getRoleID()),
							jposQuickQuoteService.getCustomerData().getCustomerType(),
							0);

					if (wrapper != null) {
						ret = wrapper.getQuoteVehicleBean();
					}
				} catch (SystemException e) {
					throw new SystemException(e);
				} catch (NumberFormatException e) {
					throw new SystemException(e);
				} catch (VapCalcException e) {
					throw new SystemException(e);
				} catch (UpgradeVapPremiumAppException e) {
					throw new SystemException(e);
				} catch (UpgradeVapExpiredException e) {
					throw new SystemException(e);
				} catch (VapUpgradeCalcException e) {
					throw new SystemException(e);
				}


			} finally {
				removeContainer();
			}
		}
		return ret;
	}

	private void removeContainer() {
		CalmsSessionData csd = (CalmsSessionData) SessionManager.getInstance().getSessionData();
		csd.setComponentSessionData(IJPOSQuickQuoteService.COMPONENT_QUOTE_CONTAINER, null);
	}

	private List<IVapItemRequestBean> getVapItems(ValidVapsContainer vvc) {
		List<IVapItemRequestBean> ret = null;
		if (vvc != null && vvc.getDisplayVaps() != null && !vvc.getDisplayVaps().isEmpty()) {
			ret = new ArrayList<IVapItemRequestBean>();
			IVapItemRequestBean reqBean;
			for (IVapMetaData bean : vvc.getDisplayVaps()) {
				reqBean = createBean(IVapItemRequestBean.class);
				reqBean.setUserDefinedValue(bean.getTxtValue());
				reqBean.setVap(bean.getValue());
				ret.add(reqBean);
			}
		}
		return ret;
	}

	private Object loadQuote() {
		return quickQuoteLoadService.invoke(new HashMap<String, Object>());
	}

	private JPOSQuickQuoteInputVO getQuoteInputVO(Object obj) {
		JPOSQuickQuoteInputVO ret = null;
		if (obj != null && obj instanceof Map) {
			Map loadMap = (Map) ((Map) obj).get("dataLoad");
			if (loadMap != null) {
				ret = (JPOSQuickQuoteInputVO) loadMap.get("defaultPlanData");
			}
		}
		return ret;
	}

}
