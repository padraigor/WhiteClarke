package com.wcna.calms.jpos.services.cra;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.product.services.session.GenericMessage;
import com.wcg.product.services.session.IMessageStore;
import com.wcg.product.services.session.SessionManager;
import com.wcna.calms.jpos.services.JPosFormProvider;
import com.wcna.calms.jpos.services.customer.DateParser;
import com.wcna.calms.service.application.IApplicationAddressDataVO;
import com.wcna.calms.service.application.IGenericScreenConstants;
import com.wcna.calms.service.common.IConstants;
import com.wcna.calms.service.customer.IApplicationConsumerDataVO;
import com.wcna.calms.service.customer.IGenericScreenForm;
import com.wcna.calms.service.error.IErrorMessage;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.calms.service.udt.IUdtTranslationService;
import com.wcna.crareport.service.ICraSearchCriteria;
import com.wcna.crareport.service.ICraSearchResponse;
import com.wcna.crareport.service.ICraSearchService;
import com.wcna.util.SystemException;

public class AdHocCraSearchSave extends CalmsAjaxService {

	static final String SEARCH_RESPONSE_KEY = "searchResponse";
	private final ICraSearchService searchService;
	private final IFormatService formatService;
	private final JPosFormProvider formProvider;
	private final IMessageStore messageStore;

	public AdHocCraSearchSave(ICraSearchService searchService, IFormatService formatService, JPosFormProvider jPosFormProvider, IMessageStore messageStore) {
		this.searchService = searchService;
		this.formatService = formatService;
		this.formProvider = jPosFormProvider;
		this.messageStore = messageStore;

	}

	public Map<String, Object> invoke(Object dataObject) {
		try {
			IGenericScreenForm form = formProvider.getForm(dataObject, this);
			ICraSearchCriteria searchCriteria = getSearchCriteria(form);
			ICraSearchResponse searchResponse = searchService
					.search(searchCriteria);
			return toMap(searchResponse);
		} catch(SystemException e) {
		//TODO: special error response to allow attempt submission again, and unlock controls
			messageStore.addMessage(new GenericMessage(e.getMessage(), 0, null));
			return new HashMap<String, Object>();
		}
	}

	private HashMap<String, Object> toMap(ICraSearchResponse searchResponse) {
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put(SEARCH_RESPONSE_KEY, searchResponse);
		return returnMap;
	}

	private ICraSearchCriteria getSearchCriteria(IGenericScreenForm form) {
		final IApplicationConsumerDataVO consumerData = ((IApplicationConsumerDataVO) form
		.getDynaProperty(IGenericScreenConstants.APP_CONSUMER_CUSTOMER));
		final IApplicationAddressDataVO currentAddress = ((IApplicationAddressDataVO) form
		.getDynaProperty(IGenericScreenConstants.APPLICATION_ADDRESS));
		final IApplicationAddressDataVO previousAddress = ((IApplicationAddressDataVO) form
		.getDynaProperty(IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS));
		final IApplicationAddressDataVO secondPreviousAddress = ((IApplicationAddressDataVO) form
		.getDynaProperty(IGenericScreenConstants.APPLICATION_PREVIOUS_ADDRESS2));

		ICraSearchCriteria searchCriteria = new CraSearchCriteria(
				currentAddress,
				consumerData,
				previousAddress,
				secondPreviousAddress,
				new DateParser() {
					public Date parse(String dateString) throws ParseException {
						return formatService.parseDate(dateString, getUserContainer().getLocale(), true, null);
					}
				});

		return searchCriteria;
	}
}

class CraSearchCriteria implements ICraSearchCriteria {
	private final IApplicationAddressDataVO currentAddress;
	private final IApplicationConsumerDataVO consumerData;
	private final IApplicationAddressDataVO previousAddress;
	private final IApplicationAddressDataVO secondPreviousAddress;
	private final DateParser dateParser;

	public CraSearchCriteria(IApplicationAddressDataVO currentAddress,
			IApplicationConsumerDataVO consumerData,
			IApplicationAddressDataVO previousAddress,
			IApplicationAddressDataVO secondPreviousAddress,
			DateParser dateParser) {
		this.currentAddress = currentAddress;
		this.consumerData = consumerData;
		this.previousAddress = previousAddress;
		this.secondPreviousAddress = secondPreviousAddress;
		this.dateParser = dateParser;
	}

	public String getTitle() {
		return consumerData.getTitle();
	}

	public String getSurname() {
		return consumerData.getLastName();
	}

	public IApplicationAddressDataVO getSecondPreviousAddress() {
		return secondPreviousAddress;
	}

	public IApplicationAddressDataVO getPreviousAddress() {
		return previousAddress;
	}

	public String getInitial() {
		return consumerData.getMiddleInitial();
	}

	public String getGender() {
		return consumerData.getGenderCode();
	}

	public String getForeName() {
		return consumerData.getFirstName();
	}

	public Date getDateOfBirth() {
		try {
			return dateParser.parse(consumerData.getDateOfBirth());
		} catch (ParseException e) {
			throw new RuntimeException("could not parse date: "+ consumerData.getDateOfBirth(), e);
		}
	}

	public IApplicationAddressDataVO getCurrentAddress() {
		return currentAddress;
	}
}
