package com.wcna.calms.jpos.services.cra;

import static com.wcna.calms.jpos.services.cra.AdHocCraSearchSave.SEARCH_RESPONSE_KEY;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.product.services.session.IMessageStore;
import com.wcna.calms.jpos.services.JPosFormProvider;
import com.wcna.calms.service.customer.IGenericScreenForm;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.crareport.service.ICraSearchCriteria;
import com.wcna.crareport.service.ICraSearchResponse;
import com.wcna.crareport.service.ICraSearchService;
import com.wcna.util.SystemException;

public class AdHocCraSearchSaveTest {

	@Mock
	private ICraSearchService searchService;
	@Mock
	private IFormatService formatService;
	@Mock
	private JPosFormProvider jPosFormProvider;
	@Mock
	private IMessageStore messageStore;
	@Mock
	private IGenericScreenForm someForm;

	private AdHocCraSearchSave adHocCraSearchSave;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		adHocCraSearchSave = new AdHocCraSearchSave(searchService, formatService, jPosFormProvider, messageStore);
		when(jPosFormProvider.getForm(anyObject(), (CalmsAjaxService) anyObject())).thenReturn(someForm);
	}

	@Test
	public void putsResponseInPayload() throws Exception {
		ICraSearchResponse searchResponse = mock(ICraSearchResponse.class);
		when(searchService.search((ICraSearchCriteria) anyObject())).thenReturn(searchResponse);
		Map<String, Object> actualResult = adHocCraSearchSave.invoke(new HashMap<String,Object>());

		assertThat(actualResult.get(SEARCH_RESPONSE_KEY), sameInstance((Object)searchResponse));
	}


	@Test
	public void putsErrorInPayload() throws Exception {
		ICraSearchResponse searchResponse = mock(ICraSearchResponse.class);
		when(searchService.search((ICraSearchCriteria) anyObject())).thenThrow(new SystemException("error message"));
		Map<String, Object> actualResult = adHocCraSearchSave.invoke(new HashMap<String,Object>());

		assertThat(actualResult.size(), equalTo(0));
		verify(messageStore).addMessage(argThat(hasDescription()));
	}

	private GenericMessageMatcher hasDescription() {
		return new GenericMessageMatcher("error message");
	}

}
