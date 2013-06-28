<%@page import="org.characterbuilder.persist.entity.RollspelStatus"%>
<%@page import="org.characterbuilder.persist.entity.service.UserService"%>
<%@page import="org.characterbuilder.persist.entity.RollspelUserRole"%>
<%@page import="org.characterbuilder.passwords.JBCrypt"%>
<%@page import="org.characterbuilder.mail.SendMailSSL"%>
<%@page import="org.characterbuilder.passwords.PasswordEncoder"%>
<%@page import="org.characterbuilder.passwords.Randomizer"%>
<%@page import="javax.persistence.NoResultException"%>
<%@page import="java.util.List"%>
<%@page import="org.characterbuilder.persist.entity.RollspelUser"%>
<%@page import="org.slf4j.LoggerFactory"%>
<%@page import="org.slf4j.Logger"%>
<%@page import="org.characterbuilder.persist.ThePersister"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="org.apache.commons.codec.digest.DigestUtils"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%
	Logger logger = LoggerFactory.getLogger(this.getClass());

	String uName = request.getParameter("email");
	String pass = request.getParameter("pass");
	boolean goOn = uName != null;
	boolean newUser = false;
	if (goOn) {
		try {
			logger.info("\n\n\nRequested account\n");
			logger.info("\n\n\nfetching entityManager\n");
			EntityManager em = ThePersister.getEntityManager();
			logger.info("\n\n\nLooking for peexisting user\n");
			RollspelUser user = null;
			try {
				user = em.createQuery("SELECT u FROM RollspelUser u WHERE u.email = :email",
						RollspelUser.class).setParameter("email", uName).getSingleResult();
			} catch (NoResultException ex2) {
			}

			/*String sso = new Randomizer().nextSessionId();
			 sso = PasswordEncoder.getInstance().encode(sso, JBCrypt.gensalt());
			 if (sso == null || sso.equals("")) {
			 System.out.println("sso == null");
			 goOn = false;
			 }
			 System.out.println("sso == '" + sso + "'.");
			 */
			if (user == null) { //NEW ACCOUNT
				logger.info("\n\n\nNew account\n");
				newUser = true;
				try {

					user = new RollspelUser();
					user.setRollspelUserRole(em.find(RollspelUserRole.class, 1));
					user.setRollspelStatus(em.find(RollspelStatus.class, 2));
					user.setEmail(uName);
					//user.setPasswordSSO(sso);
					user.setPassword(DigestUtils.md5Hex( pass ));
					UserService userService = new UserService(em);
					userService.create(user);

					/*
					 logger.info("\n\n\nSending email\n");
					 SendMailSSL.newUser(uName, sso);
					 logger.info("\n\n\nEmail sent\n");
					 */
				} catch (Exception ex) {
					goOn = false;
					logger.error("Unable to persist user entity");
					logger.debug(null, ex);
				}
			} else {
				//TODO: SET PASSWORD for user
				newUser = false;
				goOn = false;
			}

		} catch (Exception ex) {
			logger.info("\n\n\nRequested account FAILED\n");
			goOn = false;
			ex.printStackTrace();
		}
	}
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<%= !goOn
				? "<meta http-equiv=\"refresh\" content=\"1; url=..\">"
				: "<meta http-equiv=\"refresh\" content=\"1; url=../chargen/\">"%>
        <title>Processed</title>
    </head>
    <body>
        <h1><%= !goOn ? newUser ? "Already registered." : "Failed process try agin" : "Welcome!" %></h1>
    </body>
</html>
