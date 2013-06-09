package org.characterbuilder.mail;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.characterbuilder.persist.ThePersister;

/**
 *
 * @author <a href="mailto:">jens brimberg</a>
 */
public class SendMailSSL {

	public static void newUser(String toMail, String password) {
		sendMail(
				toMail,
				"kungstroll@gmail.com",
				"Congrats on creating your account!\n\n"
				+ "Should you lose your email account,\n"
				+ "I wont fix your account at kungstroll,\n"
				+ "so hang on to these credentials.\n\n"
				+ "Username: " + toMail + " (your e-mail!)\n"
				+ "Password: " + password + "\n"
				+ "This password is temporary,\n"
				+ "you will be asked to change it.\n\n"
				+ "Enjoy!",
				"Registration E-mail, kungstroll");
	}

	public static void forgottenPassword(String toMail, String password) {
		sendMail(
				toMail,
				"kungstroll@gmail.com",
				"To lose ones password is bad!\n\n"
				+ "Lose it again at there will be\n"
				+ "No Soup for you!\n\n"
				+ "Password: " + password + " (You will be asked to change this.)\n\n"
				+ "Enjoy!",
				"Temporary password, kungstroll");
	}

	public static void sendMail(String toMail, String fromMail, String messageContent, String subject) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.localhost", "localhost");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("kungstroll", "Thyrone113");
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromMail));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toMail));
			message.setSubject(subject);
			message.setText(messageContent);

			Transport.send(message);

		} catch (MessagingException e) {
			ThePersister.logIt(9, "Send mail error", e, null);
			throw new RuntimeException(e);
		}
	}
}
