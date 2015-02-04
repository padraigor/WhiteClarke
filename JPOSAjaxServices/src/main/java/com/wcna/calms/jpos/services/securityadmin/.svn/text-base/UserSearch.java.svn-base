/*
 * Copyright (c) 2012 White Clarke Group. All rights reserved.
 */

package com.wcna.calms.jpos.services.securityadmin;

import java.util.Arrays;
import java.util.Collection;

import com.wcg.calms.service.dealer.UserSearchCriteria;
import com.wcg.calms.service.dealer.UserSearchService;
import com.wcna.calms.service.common.IUserContainer;
import com.wcna.calms.service.search.ISearchService;
import com.wcna.util.SystemException;

/**
 * Dealer User Search. This can be performed by Dealers with the correct
 * privileges, but they can only find users within their own DAG.
 *
 * @author Rhys Parsons
 */
public class UserSearch extends UserSearchService {

	private final String[] roleTypeCodes;

	public UserSearch(ISearchService searchService, String[] roleTypeCodes) {
		super(searchService);
		this.roleTypeCodes = roleTypeCodes;
	}

	public Object invoke(Object args) {

		if (args instanceof UserSearchCriteria) {

			IUserContainer userContainer;
			UserSearchCriteria criteria;

			criteria      = (UserSearchCriteria) args;
			userContainer = getUserContainer();

			/*
			 * This is intended to be called at the dealer level. The user
			 * must not be able to search outside of their own DAG:
			 */
			criteria.setDag(userContainer.getCurrentDagID());

			// Can filter by role type codes. Can be null or empty to turn off.
			criteria.setRoleTypeCodes(roleTypeCodes);

			return super.invoke(args);

		} else {
			throw new SystemException(String.format("Exepcting 'args' to be '%s'", UserSearchCriteria.class.getName()));
		}
	}
}
