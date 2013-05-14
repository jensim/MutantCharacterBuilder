package org.characterbuilder.pages;

import com.vaadin.event.MouseEvents;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.OauthProvider;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
public class OauthButton extends Panel {

	public static final String redirecturl =
			  "http%3A%2F%2Fkungstroll.no-ip.org%2FCharacterBuilder%2F";
	private String redirectURL = null;

	public OauthButton(OauthProvider provider) {

		redirectURL = provider.getAuthorize_url()
				  + "?client_id=" + provider.getClientID();

		//String redirecturl = "http%3A%2F%2F127.0.0.1%3A8080%2FCharacterBuilder%2F";

		if (provider.getName().equals("google")) { //GOOGLE
			redirectURL += "&response_type=code"
					  //+"&redirect_uri=googleoauth"
					  + "&redirect_uri=" + redirecturl + "googleoauth"
					  + "&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile";
		} else if (provider.getName().equals("facebook")) { //FACEBOOK
			redirectURL +=
					  "&redirect_uri=" + redirecturl + "facebookoauth"
					  + "&state=" + java.util.UUID.randomUUID().toString();
		} else if (provider.getName().equals("stackexchange")) {
			redirectURL += "&scope=no_expiry"
					  + "&redirect_uri=" + redirecturl + "stackexchangeoauth";
		}

		setContent(new Label(provider.getName()));
		setSizeUndefined();
		addListener(new OAuthButtonListener());
	}

	private class OAuthButtonListener implements MouseEvents.ClickListener {

		@Override
		public void click(MouseEvents.ClickEvent event) {
			UI ui = getUI();
			if (ui != null) {
				try {
					ui.getPage().open(redirectURL, null);
				} catch (Exception ex) {
					ThePersister.logIt(ThePersister.LOG_CRASH,
							  "OauthButton::Failed redirecting user to::"
							  + redirectURL, ex, null);
				}
			}
		}
	}
}
