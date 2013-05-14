package org.characterbuilder.pages;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import org.characterbuilder.defaults.MyHorizontalLayout;
import org.characterbuilder.defaults.Popup;
import org.characterbuilder.defaults.PopupContent;
import org.characterbuilder.pages.footer.FootLicence;
import org.characterbuilder.pages.footer.Footer;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.FrontPageNews;
import org.characterbuilder.persist.entity.OauthProvider;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public class LoginPage extends VerticalLayout {

	private static final long serialVersionUID = 4308378800292677461L;
	private MyHorizontalLayout buttonHolder = new MyHorizontalLayout(),
			  policyHolder1 = new MyHorizontalLayout(),
			  policyHolder2 = new MyHorizontalLayout();
	private VerticalLayout newslayLayout = new VerticalLayout(),
			  loginLayout = new VerticalLayout();
	private Panel loginPanel = new Panel(loginLayout);

	@SuppressWarnings("serial")
	public LoginPage() {
		setSpacing(true);
		setMargin(true);



		addComponent(new Label("<h1>Kungstroll rollspelsportal</h1>"
				  + "<p>Logga in med ditt konto från Fejjan eller <br/>"
				  + "annan sida jag har stöd för.</p>"
				  + "<p>Om du loggar in hos oss, accepterar du även de vilkor<br/>"
				  + "som vi håller oss med här.</p>", Label.CONTENT_XHTML));

		//TODO: Privacy policy
		//TODO: User agreement
		//TODO: License information
		String privacy = readFromFile("/policies/privacy.xhtml");
		if (privacy != null) {
			policyHolder1.addComponent(new Popup(new PopupContent("integritetspolicy", privacy)));
		}
		String userAgree = readFromFile("/policies/userAgree.xhtml");
		if (privacy != null) {
			policyHolder1.addComponent(new Popup(new PopupContent("Användarvilkor", userAgree)));
			//new InfoButton("Användarvilkor", "Användarvilkor", userAgree));
		}
		String licence = readFromFile("/policies/licence.xhtml");
		if (privacy != null) {
			policyHolder2.addComponent(new Popup(new PopupContent("Tjänstavtal", licence)));
			//new InfoButton("Tjänstavtal", "Tjänstavtal", licence));
		}
		String inherrentLicence = readFromFile("/policies/inherentLicence.xhtml");
		if (privacy != null) {
			policyHolder2.addComponent(new Popup(new PopupContent("Nedärvda licenser", inherrentLicence)));
			//new InfoButton("Nedärvda licenser", "Ärvda licenser", inherrentLicence));
		}


		try {
			EntityManager em = ThePersister.getEntityManager();
			/* * * * * * * */
			/* * *Login* * */
			/* * * * * * * */
			List<OauthProvider> providerList = em.createQuery(
					  "SELECT op "
					  + "FROM OauthProvider op", OauthProvider.class)
					  .getResultList();
			for (OauthProvider provider : providerList) {
				if (provider.getActive()) {
					buttonHolder.addComponent(new OauthButton(provider));
				} else {
					System.err.println("Oauth provider incomplete in database '"
							  + provider.toString() + "',");
				}
			}



			/* * * * * * * */
			/* * *NEWS * * */
			/* * * * * * * */
			newslayLayout.addComponent(new Label("<hr/><h2>Nyheter..</h2><hr/>",
					  Label.CONTENT_XHTML));
			List<FrontPageNews> newsList = em.createQuery(
					  "SELECT r FROM FrontPageNews r ORDER BY r.postTime DESC",
					  FrontPageNews.class).getResultList();
			for (FrontPageNews news : newsList) {

				if (news.getVisible() && news.getPostTime().before(new Date())) {
					try {
						Timestamp timestamp = news.getDeathTime();
						if (timestamp.after(new Date())) {
							addPost(news);
						}
					} catch (NullPointerException e) {
						addPost(news);
					}
				}
			}
		} catch (Exception e) {
			ThePersister.logIt(1, "Error Loading news feed", e, null);
		}

		loginLayout.setSizeUndefined();
		loginLayout.addComponent(policyHolder1);
		loginLayout.addComponent(policyHolder2);
		loginLayout.addComponent(buttonHolder);
		addComponent(loginPanel);
		addComponent(newslayLayout);
		addComponent(new FootLicence());
		newslayLayout.setSizeFull();
	}

	private String readFromFile(String path) {
		try (InputStream inputStream = this.getClass().getResourceAsStream(path)) {
			StringBuilder sb = new StringBuilder();
			while (inputStream.available() > 0) {
				sb.append((char) inputStream.read());
			}
			return sb.toString();
		} catch (Exception ex) {
		}
		return null;
	}

	private void addPost(FrontPageNews news) {
		addPost(news.getHeader(), news.getContent(), news.getPostTime());
	}

	private void addPost(String header, String content, Timestamp timestamp) {
		VerticalLayout vl = new VerticalLayout();
		vl.addComponent(new Label("<h3>" + header + "</h3>", Label.CONTENT_XHTML));
		vl.addComponent(new Label("<p><b>Posted:</b> "
				  + timestamp.toString().substring(0, 16)
				  + "</p>",
				  Label.CONTENT_XHTML));

		vl.addComponent(new Label(content, Label.CONTENT_PREFORMATTED));

		Panel p = new Panel(vl);
		p.setSizeUndefined();
		newslayLayout.addComponent(p);
	}
}
