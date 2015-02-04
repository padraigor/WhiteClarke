package com.wcna.calms.jpos.services.quote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.product.services.session.GenericMessage;
import com.wcg.product.services.session.SessionManager;
import com.wcna.calms.log.Logger;
import com.wcna.util.SystemException;



public class JPOSQuickQuoteAssetRentACarUploadService extends CalmsAjaxService {

	private final IJPOSQuickQuoteAssetService quickQuoteAssetService;
	private final JPOSQuoteUtil jposQuoteUtil;
	
	public JPOSQuickQuoteAssetRentACarUploadService(IJPOSQuickQuoteAssetService quickQuoteAssetService, JPOSQuoteUtil jposQuoteUtil) {
		this.quickQuoteAssetService = quickQuoteAssetService;
		this.jposQuoteUtil = jposQuoteUtil;
	}
	
	public Object invoke(Object arg0) {
		List<IRentACarBean> list = null;
		long roleId = Long.valueOf(getUserContainer().getRoleID());
		boolean isRentACarAllowed = this.quickQuoteAssetService.isRentACarAllowed(roleId);
		if (isRentACarAllowed && arg0 != null && arg0 instanceof Map) {
			Map map = (Map) arg0;
			int quoteIdx = jposQuoteUtil.getQuoteIdx(map);
			FileItem fileItem = (FileItem) map.get("csvData");
			try {
				list = this.quickQuoteAssetService.handleRentACarUpload(fileItem);
			} catch (SystemException e) {
				if (Logger.isDebugEnabled()) {
					Logger.error(e);
				}
				list = null;
				GenericMessage message = new GenericMessage(GenericMessage.ERROR, IJPOSQuickQuoteConstants.RENT_A_CAR_UPLOAD_FAIL_MSG_CODE);
				SessionManager.getInstance().getSessionData().getMessageStore().addMessage(message);
			}
		}
		Map<String, List<IRentACarBean>> ret = new HashMap<String, List<IRentACarBean>>();
		ret.put("list", list);
		
		return ret;
	}

}
