package org.oauth;

import com.vaadin.server.VaadinRequest;
import javax.persistence.EntityManager;
import org.apache.http.message.BasicNameValuePair;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.OauthProvider;
import org.characterbuilder.persist.entity.RollspelUser;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public class GoogleCallback extends CallbackParent {

	protected String providerCommonName = "google";
	protected OauthProvider provider;

	@Override
	protected void init(VaadinRequest request) {
		String code = request.getParameter("code");
		if (code != null && !code.equals("")) {
			EntityManager em = ThePersister.getEntityManager();
			provider = em.createQuery(
					  "SELECT p FROM OauthProvider p "
					  + "WHERE p.name = :name", OauthProvider.class)
					  .setParameter("name", providerCommonName).getSingleResult();
			String access_token_url = provider.getAccessTokenURL();
			String user_url = provider.getUserURL();
			String client_id = provider.getClientID();
			String client_secret = provider.getClientSecret();

			try {
				String access_token = getAccessToken(code, access_token_url, client_id, client_secret);
				String user_id = getUserID(user_url, "?access_token=" + access_token
						  + "&redirect_uri=" + REDIRECT_URI_BASE + providerCommonName);
				RollspelUser user = ThePersister.login(provider, user_id, access_token);

			} catch (Exception ex) {
				ThePersister.logIt(ThePersister.LOG_CRASH,
						  "Callback failed for OauthProvider '"
						  + provider.getName() + "'.", ex, null);
			}
		}
		getPage().open(LOGOUT_URL, null);
	}

	@Override
	protected String getAccessToken(String access_token_url, String code,
			  String client_id, String client_secret) throws Exception {

		String access_token = darksidePostRequest(access_token_url,
				  new BasicNameValuePair("client_id", client_id),
				  new BasicNameValuePair("code", code),
				  new BasicNameValuePair("client_secret", client_secret),
				  new BasicNameValuePair("redirect_uri",
				  REDIRECT_URI_BASE + "googleoauth"));

		return access_token;
	}

	@Override
	protected String getUserID(String user_url, String parameterSet) throws Exception {
		return darksideGetRequest(user_url, parameterSet);
	}
}
