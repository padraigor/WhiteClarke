/*
 * Copyright (c) 2012 White Clarke Group. All rights reserved.
 */

package com.wcna.calms.jpos.services.securityadmin;

import org.apache.log4j.Logger;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcg.calms.service.dealer.UserReference;
import com.wcg.product.calms.security.CalmsSessionData;
import com.wcg.product.services.session.GenericMessage;
import com.wcg.product.services.session.SessionManager;
import com.wcna.calms.service.security.IUserService;
import com.wcna.calms.service.security.NoSuchUserException;
import com.wcna.calms.service.security.UserInUseException;

/**
 * Deletes a user.

 * @author Rhys Parsons
 */
public class UserDelete extends CalmsAjaxService {

    static Logger logger = Logger.getLogger(UserDelete.class);

    private IUserService userService;

    public UserDelete(IUserService userService) {
        this.userService = userService;
    }

    public Object invoke(Object args) {

        if (args instanceof UserReference) {

            long userId = Long.parseLong(((UserReference) args).getUserId());

            try {

                userService.removeUser(userId);

            } catch (NoSuchUserException e) {
                addError("AC0032");
            } catch (UserInUseException e) {
                addError("AC0032");
            }

        } else {
            logger.error(
                String.format("Expecting args to be of type '%s', but it's of type '%s'",
                              UserReference.class.getName(),
                              args == null ? "null" : args.getClass().getName())
            );
        }

        return null;
    }

    private void addError(String errorCode) {
        CalmsSessionData sessionData = (CalmsSessionData) SessionManager.getInstance().getSessionData();
        sessionData.getMessageStore().addMessage(new GenericMessage(null, GenericMessage.ERROR, errorCode));
    }
}
