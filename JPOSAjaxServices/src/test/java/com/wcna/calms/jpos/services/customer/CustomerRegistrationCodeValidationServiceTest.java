package com.wcna.calms.jpos.services.customer;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Locale;

import org.junit.Test;

import com.wcna.calms.service.validation.CustomValidationItem;
import com.wcna.calms.service.validation.CustomValidationResult;

public class CustomerRegistrationCodeValidationServiceTest {

	private static final String REGISTRATION_CODE_FIELDNAME = "registrationCode";
	private static final String REGISTRATION_CODE_PARENT = "applicationCustomer_type_company";
	private static String VALID_BRAZIL_TAXNUM_CNPJ = "09.289.003/0001-61";
	private static String INVALID_BRAZIL_TAXNUM_CNPJ = "09.289.003/0001-40";

	@Test
	public void testBrazilTanNumberValidation() {
		CustomerRegistrationCodeValidationService testService = new CustomerRegistrationCodeValidationService();
		testService.setValidationType(5);
		testService.setTaxFieldPath(REGISTRATION_CODE_PARENT+"."+REGISTRATION_CODE_FIELDNAME);
		//create a valid test map
		HashMap<String,Object> testMap = new HashMap<String,Object>();
		HashMap<String,Object> customerDataMap = new HashMap<String, Object>();
		testMap.put(REGISTRATION_CODE_PARENT, customerDataMap);

		customerDataMap.put(REGISTRATION_CODE_FIELDNAME, VALID_BRAZIL_TAXNUM_CNPJ);

		CustomValidationResult validResult = testService.validate(testMap, Locale.ENGLISH);

		//check result is as expected
		assertTrue(validResult.isValid());

		customerDataMap.remove(REGISTRATION_CODE_FIELDNAME);

		customerDataMap.put(REGISTRATION_CODE_FIELDNAME, INVALID_BRAZIL_TAXNUM_CNPJ);

		CustomValidationResult invalidResult = testService.validate(testMap, Locale.ENGLISH);

		//check invlaid result is as expected
		assertTrue(!invalidResult.isValid());
		CustomValidationItem invalidValidationITem = invalidResult.getValidationItems().get(0);
		assertEquals(REGISTRATION_CODE_PARENT,invalidValidationITem.getControlPrefix());
		assertEquals(REGISTRATION_CODE_FIELDNAME,invalidValidationITem.getFieldName());

	}

}
