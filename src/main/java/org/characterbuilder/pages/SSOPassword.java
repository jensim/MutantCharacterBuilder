/*
package org.characterbuilder.pages;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutAction.KeyCode;
import javax.persistence.EntityManager;

import org.characterbuilder.CharbuildApp;
import org.characterbuilder.JBCrypt;
import org.characterbuilder.defaults.MenuButton;
import org.characterbuilder.mail.SendMailSSL;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.RollspelUser;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class SSOPassword extends VerticalLayout {

    private TextField emailField = new TextField("e-mail");
    private TextField ssoField = new TextField("Single signon password");
    private PasswordField passField = new PasswordField("New password");
    private Button comitButton = new Button("Set new password");
    private MenuButton requestButton = new MenuButton("Request new password");
    private MenuButton backButton = new MenuButton("Back to login");
    private HorizontalLayout emailLayout = new HorizontalLayout();

    public SSOPassword() {
        buildUI();
    }

    public SSOPassword(String email) {
        buildUI();
        emailField.setValue(email);
    }

    public SSOPassword(String email, String sso) {
        buildUI();
        emailField.setValue(email);
        ssoField.setValue(sso);
    }

    private void buildUI() {
        setSpacing(true);
        setMargin(true);

        addComponent(emailLayout);
        emailLayout.addComponent(emailField);
        emailLayout.addComponent(requestButton);
        addComponent(ssoField);
        addComponent(passField);
        addComponent(comitButton);
        addComponent(backButton);

        requestButton.addListener(new Requester());
        comitButton.addListener(new Comitter());
        backButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Window w = getApplication().getMainWindow();
                w.removeAllComponents();
                w.addComponent(new LoginPage());
            }
        });
		
		comitButton.addShortcutListener(new Button.ClickShortcut(comitButton, KeyCode.ENTER));
    }

    private class Requester implements Button.ClickListener {

        @Override
        public void buttonClick(ClickEvent event) {
            String email = emailField.getValue().toString().toLowerCase();
            EntityManager em = ThePersister.getEntityManager();

            try {
                RollspelUser user = em.createQuery(
                        "SELECT r FROM RollspelUser r WHERE r.email = :email",
                        RollspelUser.class)
                        .setParameter("email", email)
                        .getSingleResult();

                if (!email.contains("@")) {
                    notifyError(2, "Lost password page.", "Failed; Bad email address.", null);
                } else {
                    em.getTransaction().begin();
                    String password = java.util.UUID.randomUUID().toString();
                    //user.setSingleSignonPass(JBCrypt.hashpw(password, JBCrypt.gensalt()));
                    em.getTransaction().commit();

                    SendMailSSL.forgottenPassword(email, password);
                }

            } catch (Exception e) {
                notifyError(9, "Lost password page", "Exception; It's probably your fault.", null);
            }
        }
    }

    private void notifyError(int logtype, String type, String info, Exception ex) {
        getWindow().showNotification(type, info, Notification.TYPE_WARNING_MESSAGE);
        ThePersister.logIt(logtype, type + ": " + info, ex, null);
    }

    private class Comitter implements Button.ClickListener {

        @Override
        public void buttonClick(ClickEvent event) {
            String email = emailField.getValue().toString();
            String sso = ssoField.getValue().toString();
            String pass = passField.getValue().toString();

            EntityManager em = ThePersister.getEntityManager();

            try {

                if (!email.contains("@")) {
                    throw new IllegalArgumentException("No email.");
                } else if (sso.equalsIgnoreCase("")) {
                    throw new IllegalArgumentException("No sso.");
                } else if (pass.equalsIgnoreCase("")) {
                    throw new IllegalArgumentException("No pass.");
                } else {

                    RollspelUser user = em.createQuery(
                            "SELECT r FROM RollspelUser r WHERE r.email = :email",
                            RollspelUser.class)
                            .setParameter("email", email)
                            .getSingleResult();

                    if (JBCrypt.checkpw(sso, user.getSingleSignonPass())) {
                        //SSOPassword checks out - set the real pass - and sessionID
                        em.getTransaction().begin();
                        user.setSingleSignonPass("");
                        user.setPassHash(JBCrypt.hashpw(pass, JBCrypt.gensalt()));
                        user.setSessionKey(CharbuildApp.getSessionID());
                        em.getTransaction().commit();

                        //goTo TopPage
                        Window w = getApplication().getMainWindow();
                        w.removeAllComponents();
                        w.addComponent(new TopPage());
                    } else {
                        notifyError(2, "Lost password page", "email and password incorrect", null);
                    }
                }

            } catch (IllegalArgumentException e) {
                notifyError(3, "Lost password page", "Exception cought, rollback", e);
            } catch (Exception e) {
                notifyError(9, "Lost password page", "unable to perform action", e);
            }


        }
    }
}
*/