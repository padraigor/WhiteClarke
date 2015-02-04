package com.wcna.calms.jpos.services.search;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.calms.service.search.ISearchDetails;
import com.wcna.calms.service.search.ISearchListForm;
import com.wcna.calms.service.search.ISearchService;
import com.wcna.calms.web.constants.SearchViewNames;
import com.wcna.calms.web.constants.WebConstants;
import com.wcna.lang.NumberUtil;
import com.wcna.lang.StringUtil;
import com.wcna.sql.SearchQuery3;

public class BasicAdvancedSearchService extends CalmsAjaxService {

	private final ISearchService searchService;
	private final IFormatService formatService;

	public BasicAdvancedSearchService(ISearchService searchService, IFormatService formatService) {
		this.searchService = searchService;
		this.formatService = formatService;
	}

	public Object invoke(Object arg0) {
		if (arg0 != null && arg0 instanceof Map) {
			Map mapIn = (Map) arg0;

			Integer numberOfRecords = NumberUtil.toInteger((String) mapIn.get("numberOfRecords"), 100);

			if (numberOfRecords != null&& numberOfRecords > IConstants.MAX_SEARCH_RESULTS_ALLOWED)
				numberOfRecords = IConstants.MAX_SEARCH_RESULTS_ALLOWED;

			String searchTypeCode = (String) mapIn.get("searchTypeCode");
			String customerName = null;
			if(this.entityAccessService.isWritable(SearchViewNames.ADV_SEARCH_SCREEN_NAME, SearchViewNames.CUSTOMER_NAME_FIELD_NAME)) {
				customerName = (String) mapIn.get("customerName");
			} else {
				customerName = (String) mapIn.get("mainApplicantsName");
			}
			String dealerName = (String) mapIn.get("dealerName");
			String salesPerson = (String) mapIn.get("salesPerson");
			String status = (String) mapIn.get("status");
			String proposalNr = (String) mapIn.get("proposalNr");
			String registrationNr = (String) mapIn.get("registrationNr");
			String zipPostal = (String) mapIn.get("postCode");
			String dateRange = (String) mapIn.get("datesRadio");
			Object dateCriteria = mapIn.get("dateCriteria"); //a single date from basic search
			String fiscalNumber = (String) mapIn.get("fiscalNumber");
			String externalRefNr = (String) mapIn.get("externalRefNr");
			String identityNumber = (String) mapIn.get("identityNumber");
			String dateType = (String) mapIn.get("dateTypeToSearch");
			String dateFrom = null;
			String dateTo = null;
			List<String> statuses = (List<String>) mapIn.get("statuses");

			if (dateCriteria != null) {
				dateFrom = (String)dateCriteria;
				try {
					Date dateFrObj = formatService.parseDate(dateFrom, getUserContainer().getLocale(), true, null);
					Date dateToObj = DateUtils.addDays(dateFrObj, 1);
					dateTo = formatService.formatDate(dateToObj, getUserContainer().getLocale());
				} catch (ParseException e) {
				}
				dateRange = "R";
			} else {
				Object o = mapIn.get("rangeFrom");
				if (o != null) {
					dateFrom = (String) o;
				}
				o = mapIn.get("rangeTo");
				if (o != null) {
					dateTo = (String) o;
				}
			}
			IAdvancedSearchListForm formIn = createBean(IAdvancedSearchListForm.class);
			formIn.setSearchMessage("");

			if (!StringUtil.isEmpty(searchTypeCode)) {
				formIn.setSearchType(searchTypeCode);
			}
			if (!StringUtil.isEmpty(customerName)) {
				formIn.setCustomerName(customerName);
			}
			if (!StringUtil.isEmpty(dealerName) && !"-1".equals(dealerName)) {
				formIn.setDagName(dealerName);
			}
			if (!StringUtil.isEmpty(salesPerson) && !"-1".equals(salesPerson)) {
				formIn.setSalesPerson(salesPerson);
			}
			if (!StringUtil.isEmpty(status)) {
				formIn.setStatus(status);
			}
			if (!StringUtil.isEmpty(proposalNr)) {
				formIn.setProposalLeadNo(proposalNr);
			}
			if (!StringUtil.isEmpty(registrationNr)) {
				formIn.setRegistrationNo(registrationNr);
			}
			if (!StringUtil.isEmpty(zipPostal)) {
				formIn.setPostCode(zipPostal);
			}
			if (!StringUtil.isEmpty(fiscalNumber)) {
				formIn.setCustomerSsnId(fiscalNumber);
			}
			if (!StringUtil.isEmpty(externalRefNr)) {
				formIn.setExternalRefNr(externalRefNr);
			}
			if (!StringUtil.isEmpty(identityNumber)) {
				formIn.setIdentityNumber(identityNumber);
			}
			if (!StringUtil.isEmpty(dateType)) {
				formIn.setDateType(dateType);
			}
			formIn.setStatuses(statuses);

			if (!StringUtil.isEmpty(dateRange)) {
				formIn.setDateSearchOptions(WebConstants.DATE_SEARCH_OPTIONS_DATE);
				if ("R".equals(dateRange)) {
					formIn.setDatesSelected(WebConstants.DATE_SEARCH_OPTIONS_DATE_RANGE);
				} else if ("M".equals(dateRange)) {
					formIn.setDatesSelected(ISearchListForm.LESS_THAN_A_MONTH);
				} else if ("Y".equals(dateRange)) {
					formIn.setDatesSelected(ISearchListForm.LESS_THAN_A_YEAR);
				}
				formIn.setFromDate(dateFrom);
				formIn.setToDate(dateTo);
			}

			SearchQuery3 sq3 = new SearchQuery3();

			searchService.populateSearchQuery(sq3, getUserContainer(), formIn, null, "SearchListForm", numberOfRecords);
			formIn.setSearchQuery(sq3);

			Map<String, Object> returnMap = new HashMap<String, Object>();
			ISearchDetails[] results = searchService.search(formIn.getSearchQueries(), new int [] { 0 }, numberOfRecords.intValue(), getUserContainer().getLocale(), numberOfRecords.intValue(), false);
			returnMap.put("results", results[0].getDataStorage().getData());
			return returnMap;
		}

		return null;
	}

}
