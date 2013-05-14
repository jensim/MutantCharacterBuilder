/*package org.characterbuilder.pages;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutAction.KeyCode;
import javax.persistence.EntityManager;

import org.characterbuilder.JBCrypt;
import org.characterbuilder.defaults.MenuButton;
import org.characterbuilder.mail.SendMailSSL;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.RollspelStatus;
import org.characterbuilder.persist.entity.RollspelUser;
import org.characterbuilder.persist.entity.RollspelUserRole;


import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

@SuppressWarnings("serial")
public class RegisterPage extends VerticalLayout {
	//private		VerticalLayout 	registerForm 		= new VerticalLayout();

	private TextField emailField = new TextField("e-mail");
	private Button registerButton = new Button("Register user");
	private CheckBox acceptBox = new CheckBox();
	private MenuButton loginButton = new MenuButton("Back to Login");
	private Panel licenceArea = new Panel();
	private Panel varningPanel = new Panel();
	private Panel acceptPanel = new Panel();
	private static final Label acceptLabel = new Label(
			"<b><u>I have read and agree to the license agreement.</u></b>",
			Label.CONTENT_XHTML);
	private static final Label varningLabel = new Label(
			"<h3>Detta är inte ett substitit för rollspelsliteratur.</h3>"
			+ "<p>Jag är en programmerare som har pluggat spelutveckling.<br/>"
			+ "Regelsystem är något som jag tycker är mer än underhållande,<br/>"
			+ "och jag har skrivit detta med mig som slutkund.</p>"
			+ "<p>Med detta sagt så vill jag upplysa er om att de verktyg jag<br/>"
			+ "utvecklar är till för <u>kunniga</u> till <u>mycket kunniga</u><br/>"
			+ "spelare av de specifika regelsystemen.</p>"
			+ "<p>I mina verktyg så tillåter jag till exempen spelare att gå över<br/>"
			+ "satta poänggränser i skapande av karaktärer, då dessa kan skilja sig<br/>"
			+ "beroende på grupp och specifika tillfällen. Till detta har jag inte<br/>"
			+ "heller brytt mig om att programmera in upplysningar om när man<br/>"
			+ "bryter mot skrivna regler, utan endast lämnat vissa indikationer i<br/>"
			+ "form av poängräknare etc.</p>"
			+ "<p>Om jag inte skrämt bort er vid det här laget så hoppas jag att ni<br/>"
			+ "som jag uppskattar potensen i det verktyg jag har byggt.<br/>"
			+ "Det är inte skryt när jag säger att det är möjligt att göra en klar<br/>"
			+ "karaktär på sekunder, och få den skriven till dynamisk PDF.</p>",
			Label.CONTENT_XHTML);
	private static final Label licenceString = new Label(
			"<h3>Copyright 2012-2013 Jens Brimberg</h3>"
			+ "<p>Licensed under the Apache License, Version 2.0 (the \"License\");<br/>"
			+ "you may not use this file except in compliance with the License.<br/>"
			+ "You may obtain a copy of the License at</p>"
			+ "<p><a href=\"http://www.apache.org/licenses/LICENSE-2.0\">"
			+ "http://www.apache.org/licenses/LICENSE-2.0</a></p>"
			+ "<p>Unless required by applicable law or agreed to in writing, software<br/>"
			+ "distributed under the License is distributed on an \"AS IS\" BASIS,<br/>"
			+ "<u><b>WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND</b></u>,<br/>"
			+ "either express or implied.<br/>"
			+ "See the License for the specific language governing permissions and<br/>"
			+ "limitations under the License.</p>", Label.CONTENT_XHTML);

	public RegisterPage() {
		setSpacing(true);
		setMargin(true);

		addComponent(emailField);
		emailField.addShortcutListener(new Button.ClickShortcut(registerButton, KeyCode.ENTER));

		addComponent(registerButton);
		registerButton.addListener(new RegisterButtonListener());

		addComponent(loginButton);

		varningPanel.setSizeUndefined();
		varningPanel.addComponent(varningLabel);
		addComponent(varningPanel);

		licenceArea.addComponent(licenceString);
		licenceArea.setSizeUndefined();
		addComponent(licenceArea);
		
		acceptPanel.addComponent(acceptBox);
		acceptPanel.addComponent(acceptLabel);
		acceptPanel.setSizeUndefined();
		addComponent(acceptPanel);
		

		loginButton.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Window w = getApplication().getMainWindow();
				w.removeAllComponents();
				w.addComponent(new LoginPage());
			}
		});
		emailField.focus();
	}

	private void notifyError(int logtype, String type, String info, Exception ex) {
		getWindow().showNotification(type, info, Notification.TYPE_WARNING_MESSAGE);
		ThePersister.logIt(logtype, type + ": " + info, ex, null);
	}

	private class RegisterButtonListener implements Button.ClickListener {

		public void buttonClick(ClickEvent event) {
			
			String email = emailField.getValue().toString().toLowerCase();
			if(!(acceptBox.getValue() instanceof Boolean)){
				getWindow().showNotification("Error server side", "Bad programmer! Bad.", Window.Notification.TYPE_HUMANIZED_MESSAGE);
			}else if(!(Boolean)acceptBox.getValue()){
				getWindow().showNotification("agree.. checkbox.. terms.. read..", "", Window.Notification.TYPE_HUMANIZED_MESSAGE);
			}else if (!email.contains("@")) {
				notifyError(2,"Email field", "Bad input", null);

			} else {
				
				getWindow().showNotification("Sending request..", "", Window.Notification.TYPE_HUMANIZED_MESSAGE);

				EntityManager em = ThePersister.getEntityManager();
				try {
					em.getTransaction().begin();

					// Create new user
					RollspelUser newUser = new RollspelUser();
					newUser.setEmail(email);
					String password = java.util.UUID.randomUUID().toString();
					newUser.setSingleSignonPass(JBCrypt.hashpw(password, JBCrypt.gensalt()));
					newUser.setRollspelUserRole(em.find(RollspelUserRole.class, 1));
					newUser.setRollspelStatus(em.find(RollspelStatus.class, 2));

					em.persist(newUser);
					em.getTransaction().commit();

					SendMailSSL.newUser(email, password);
					getWindow().showNotification("Request Sent", "Check your email.", Window.Notification.TYPE_HUMANIZED_MESSAGE);

					ThePersister.logIt(1, "New account created",null, newUser);

					Window w = getApplication().getMainWindow();
					w.removeAllComponents();
					w.addComponent(new SSOPassword(email));
				} catch (Exception e) {
					//ThePersister.logIt(9, "registration failed", e, null);
					notifyError(2,"Registration", "Failed creating account."
							+(email!=null?email:""), e);
				}
			}
		}
	}
}
*/