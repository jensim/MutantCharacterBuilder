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
	
	@Override
	protected void init(VaadinRequest request) {
		
		//TODO: set SSO Pass for user to null

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
}
