<%-- 
    Document   : register_processed
    Created on : Jun 9, 2013, 6:53:12 PM
    Author     : jens
--%>


<%@page import="javax.persistence.PersistenceContext"%>
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
<%@page import="javax.transaction.UserTransaction"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	String uName = request.getParameter("email");
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
			} catch (NoResultException ex2) {}

			if (user == null) { //NEW ACCOUNT
				logger.info("\n\n\nNew account\n");
				newUser = true;
				user = new RollspelUser();
				user.setEmail(uName);
				logger.info("\n\n\nBeginning transaction\n");
				
				em.persist(user);
				logger.info("\n\n\nCommiting transaction\n");

				logger.info("\n\n\nTransaction commited\n");
			}
			String sso = new Randomizer().nextSessionId();
			sso = PasswordEncoder.getInstance().encode(sso, uName);
			logger.info("\n\n\nBeginning transaction\n");
			user.setPasswordSSO(sso);
			em.merge(user);
			logger.info("\n\n\nCommiting transaction\n");
			logger.info("\n\n\nTransaction commited\n");
			logger.info("\n\n\nSending email\n");
			SendMailSSL.newUser(uName, sso);
			logger.info("\n\n\nEmail sent\n");
			
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
		<meta http-equiv="refresh" content="1; url=login.jsp">
        <title>Processed</title>
    </head>
    <body>
        <h1><%= !goOn ? "Failed process try agin" : newUser ? "Welcome, password sent by mail" : "New password sent to your inbox" %></h1>
    </body>
</html>
