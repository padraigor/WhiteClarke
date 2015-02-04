package com.wcna.calms.jpos.services;

import java.util.List;

import com.wcg.product.services.session.GenericMessage;
import com.wcg.product.services.session.IMessageStore;
import com.wcg.product.services.session.SessionManager;
/**
 * proxy all calls to Session message store
 * @author mburnett
 *
 */
public class SessionMessageStore implements IMessageStore {

	public void addMessage(GenericMessage arg0) {
		messageStore().addMessage(arg0);
	}

	public void addMessages(List<GenericMessage> arg0) {
		messageStore().addMessages(arg0);
	}

	public List<GenericMessage> getMessages() {
		return messageStore().getMessages();
	}

	private IMessageStore messageStore() {
		return SessionManager.getInstance().getSessionData().getMessageStore();
	}

}
