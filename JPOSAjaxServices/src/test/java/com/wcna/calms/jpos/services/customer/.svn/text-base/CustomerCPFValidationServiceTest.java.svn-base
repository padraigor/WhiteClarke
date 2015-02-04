package com.wcna.calms.jpos.services.customer;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Locale;

import org.junit.Test;

import com.wcna.calms.service.validation.CustomValidationItem;
import com.wcna.calms.service.validation.CustomValidationResult;

public class CustomerCPFValidationServiceTest {

	private static final String REGISTRATION_CODE_FIELDNAME = "salespersonTaxNum";
	private static final String REGISTRATION_CODE_PARENT = "applicationCustomer_type_company";
	private static String VALID_BRAZIL_CPJ = "092.698.378-40";
	private static String INVALID_BRAZIL_CPJ = "092.698.378-23";

	@Test
	public void testBrazilCpfValidation() {
		CustomerCPFValidationService testService = new CustomerCPFValidationService();
		testService.setValidationType(5);
		testService.setCpfFieldName(REGISTRATION_CODE_PARENT+"."+REGISTRATION_CODE_FIELDNAME);
		//create a valid test map
		HashMap<String,Object> testMap = new HashMap<String,Object>();
		HashMap<String,Object> customerDataMap = new HashMap<String, Object>();
		testMap.put(REGISTRATION_CODE_PARENT, customerDataMap);

		customerDataMap.put(REGISTRATION_CODE_FIELDNAME, VALID_BRAZIL_CPJ);

		CustomValidationResult validResult = testService.validate(testMap, Locale.ENGLISH);

		//check result is as expected
		assertTrue(validResult.isValid());

		customerDataMap.remove(REGISTRATION_CODE_FIELDNAME);

		customerDataMap.put(REGISTRATION_CODE_FIELDNAME, INVALID_BRAZIL_CPJ);

		CustomValidationResult invalidResult = testService.validate(testMap, Locale.ENGLISH);

		//check invlaid result is as expected
		assertTrue(!invalidResult.isValid());
		CustomValidationItem invalidValidationITem = invalidResult.getValidationItems().get(0);
		assertEquals(REGISTRATION_CODE_PARENT,invalidValidationITem.getControlPrefix());
		assertEquals(REGISTRATION_CODE_FIELDNAME,invalidValidationITem.getFieldName());

	}

}
