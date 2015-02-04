package com.wcna.calms.jpos.services.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.businesspartner.IBusinessPartnerData;
import com.wcna.calms.service.businesspartner.IBusinessPartnerService;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.security.IUserData;
import com.wcna.calms.service.security.IUserService;
import com.wcna.calms.util.OptionLabelValue;

public class InitiateAdvancedSearchListsService extends CalmsAjaxService {

	private final IAdvancedSearchService advancedSearchService;
	private final IBusinessPartnerService businessPartnerService;
	private final IUserService userService;
	
	public InitiateAdvancedSearchListsService(IAdvancedSearchService advancedSearchService, IBusinessPartnerService businessPartnerService,
											  IUserService userService) {
		this.advancedSearchService = advancedSearchService;
		this.businessPartnerService = businessPartnerService;
		this.userService = userService;
	}
	
	public Object invoke(Object arg0) {
		
		Map<String, Object> out = new HashMap<String, Object>();
		String disableDealerDropDown = null;
		String disableUserDropDown = null;
		String dealerOption = null;
		List<OptionLabelValue> dealerList = null;
		List<OptionLabelValue> saleUserList = new ArrayList<OptionLabelValue>();
		String disableCopyButton = IConstants.FLAG_YES;
		saleUserList.add(new OptionLabelValue("----", "-1"));
		
		String dagId = getUserContainer().getCurrentDagID();
		String businessPartnerType = businessPartnerService.findPartnerTypeByDagId(Long.valueOf(dagId).longValue());
		if (getUserContainer().hasModuleWriteAccessByCode(IConstants.COPY_APPLICATION)) {
			disableCopyButton = IConstants.FLAG_NO;
		}
		if ("D".equals(businessPartnerType)) {
			disableDealerDropDown = IConstants.FLAG_YES;
			dealerOption = dagId;
			dealerList = new ArrayList<OptionLabelValue>();

			IBusinessPartnerData businessPartnerData = businessPartnerService.getBusinessPartner(businessPartnerService.findPartnerIdByDagId(Long.valueOf(dagId).longValue()));

			dealerList.add(new OptionLabelValue(businessPartnerData.getName()
					+ " ("+businessPartnerData.getPartnerCode()
	    			+ ")", dagId));


			List<IUserData> userList = userService.getAllUsersByDagId(dagId, true);
			if (userList != null && !userList.isEmpty()) {
				int size = userList.size();
				for (int i = 0; i < size; i++) {
					IUserData u = userList.get(i);
					saleUserList.add(new OptionLabelValue(u.getFullName(), u.getGuid()));
				}
			}
		} else {
			disableUserDropDown = IConstants.FLAG_YES;
			dealerList = advancedSearchService.getDealerSearchList(dagId);
			if (dealerList == null) {
				dealerList = new ArrayList<OptionLabelValue>();
			}
			dealerList.add(0, new OptionLabelValue("", "-1"));
		}
		
		out.put("disableDealerDropDown", disableDealerDropDown);
		out.put("disableUserDropDown", disableUserDropDown);
		out.put("disableCopyButton", disableCopyButton);
		out.put("dealerOption", dealerOption);
		out.put("dealerList", dealerList);
		out.put("saleUserList", saleUserList);
		
		return out;
	}
}
