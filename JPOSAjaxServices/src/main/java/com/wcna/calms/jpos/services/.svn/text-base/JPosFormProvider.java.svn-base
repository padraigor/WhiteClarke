package com.wcna.calms.jpos.services;

import java.util.Map;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.service.customer.IGenericScreenForm;

public class JPosFormProvider {

	public IGenericScreenForm getForm(Object dataObject, CalmsAjaxService beanProvider) {
		IGenericScreenForm form = beanProvider.createBean(IGenericScreenForm.class);
		form.convertMapsToBeans((Map<String, Object>) dataObject);
		return form;
	}

	
}
