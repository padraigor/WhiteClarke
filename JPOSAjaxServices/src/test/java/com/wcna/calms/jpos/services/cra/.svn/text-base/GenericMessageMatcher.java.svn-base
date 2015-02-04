package com.wcna.calms.jpos.services.cra;

import org.hamcrest.Description;
import org.junit.internal.matchers.TypeSafeMatcher;

import com.wcg.product.services.session.GenericMessage;

public class GenericMessageMatcher extends
		TypeSafeMatcher<GenericMessage> {
	/**
	 *
	 */
	private final String description;

	public GenericMessageMatcher(String description) {
		this.description = description;
	}

	public void describeTo(Description description) {
		description.appendText("GenericMessage with description: " + description);
	}

	@Override
	public boolean matchesSafely(GenericMessage item) {
		return item.getDesc().equals(description);
	}
}