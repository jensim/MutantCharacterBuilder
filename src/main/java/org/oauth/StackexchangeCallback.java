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
public class StackexchangeCallback extends CallbackParent {

	public static final String providerCommonName = "stackexchange";
	private OauthProvider provider;

	@Override
	protected void init(VaadinRequest request) {
		String code = request.getParameter("code");
		if (code != null && !code.endsWith("")) {
			EntityManager em = ThePersister.getEntityManager();
			provider = em.createQuery(
					  "SELECT p FROM OauthProvider p "
					  + "WHERE p.name = :name", OauthProvider.class)
					  .setParameter("name", providerCommonName).getSingleResult();
			String ACCESS_TOKEN_URL = provider.getAccessTokenURL();
			String USER_URL = provider.getUserURL();
			String CLIENT_ID = provider.getClientID();
			String CLIENT_SECRET = provider.getClientSecret();

			try {
				String access_token = getAccessToken(code, ACCESS_TOKEN_URL, CLIENT_ID, CLIENT_SECRET);
				String user_id = getUserID(USER_URL, "?access_token=" + access_token);
				RollspelUser user = ThePersister.login(provider, user_id, access_token);
			} catch (Exception ex) {
			}
		}
	}

	@Override
	protected String getAccessToken(String access_token_url, String code,
			  String client_id, String client_secret) throws Exception {

		String access_token = darksidePostRequest(access_token_url,
				  new BasicNameValuePair("client_id", client_id),
				  new BasicNameValuePair("code", code),
				  new BasicNameValuePair("client_secret", client_secret),
				  new BasicNameValuePair("redirect_uri",
				  REDIRECT_URI_BASE + providerCommonName + "oauth"));
		return access_token;
	}

	@Override
	protected String getUserID(String user_url, String parameterset) throws Exception {
		return darksideGetRequest(user_url, parameterset);
	}
}
