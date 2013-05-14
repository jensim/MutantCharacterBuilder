package org.characterbuilder;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import org.characterbuilder.pages.trudvang.createcharacter.TrudvangPageCreateCharacter;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.RollspelUser;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
//@Theme("green-chameleon")
public class CharbuildApp extends UI {

	private static final long serialVersionUID = 1695235185603122706L;
	private static String sessionID = "";
	private static String ipAddr = "";

	@Override
	protected void init(VaadinRequest request) {

		try {
			sessionID = getSession().getSession().getId();
			ipAddr = request.getRemoteAddr();
		} catch (NullPointerException npe) {
		}
		
		setContent(new TrudvangPageCreateCharacter());
		//setContent(new TrudvangManageSuperArktype());
		//setContent(new TrudvangManageSuperPeople());
		//setContent(new TrudvangManageSuperUpbringing());
		//setContent(new TrudvangManageSuperExceptional());
		/*
		RollspelUser user = getRollspelUser();

		VerticalLayout mainwLayout = new VerticalLayout();
		getPage().setTitle("Kungstroll Rollspelsportal");
		if (user != null) {
			TopPage mp = new TopPage();
			setContent(mp);
		} else {
			LoginPage lp = new LoginPage();
			setContent(lp);
		}
		*/
	}

	public static void setIP(String ip) {
		ipAddr = ip;
	}

	public static String getIP() {
		return ipAddr;
	}

	public static String getSessionID() {
		return sessionID;
	}

	public static RollspelUser getRollspelUser() {
		return ThePersister.getUser(sessionID);
	}

	public static void logout() {
		ThePersister.logout(sessionID);
	}
}
