package org.characterbuilder.persist;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import org.characterbuilder.CharbuildApp;
import org.characterbuilder.persist.entity.MutantBaseStat;
import org.characterbuilder.persist.entity.MutantBaseStatCharacter;
import org.characterbuilder.persist.entity.MutantCharacter;
import org.characterbuilder.persist.entity.OauthProvider;
import org.characterbuilder.persist.entity.RollspelLog;
import org.characterbuilder.persist.entity.RollspelLogType;
import org.characterbuilder.persist.entity.RollspelSession;
import org.characterbuilder.persist.entity.RollspelStatus;
import org.characterbuilder.persist.entity.RollspelUser;
import org.characterbuilder.persist.entity.RollspelUserRole;
import org.oauth.HttpTool;

/**
 * 
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public class ThePersister {

	public static final Integer LOG_INFO = 1;
	public static final Integer LOG_WARNING = 2;
	public static final Integer LOG_VIOLATION = 3;
	public static final Integer LOG_CRASH = 9;
	public static final String ENTITY_MANAGER_NAME = "CharacterBuilder";

	private static EntityManagerFactory buildEntityManagerFactory() {
		try {
			return Persistence.createEntityManagerFactory(ENTITY_MANAGER_NAME);
		} catch (Exception ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Get an entity manager to the rollspel database.
	 *
	 * @return
	 */
	public static EntityManager getEntityManager() {
		return buildEntityManagerFactory().createEntityManager();
	}

	/**
	 * Logout single session
	 *
	 * @param sessionID
	 */
	public static void logout(String sessionID) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();

			RollspelSession session = em.createQuery(
					  "SELECT s FROM RollspelSession s "
					  + "WHERE s.sessionID = :sessionID",
					  RollspelSession.class).setParameter("sessionID", sessionID)
					  .getSingleResult();
			String logoutURL = session.getUser().getOauthProvider().getLogoutURL();
			if (logoutURL != null) {
				HttpTool.darksideGetRequest(logoutURL,
						  "?access_token=" + session.getOauthAccessToken());
			}

			em.remove(session);
			em.getTransaction().commit();
		} catch (NoResultException nre) {
			logIt(1, "logout action, no user found for session id \"" + sessionID + "\"", null, null);
		} catch (Exception e) {
			logIt(9, "logout action, exception for session id \"" + sessionID + "\"", null, null);
		}
	}

	/**
	 * Log out current session
	 */
	public static void logout() {
		logout(getUser(CharbuildApp.getSessionID()));
	}

	/**
	 * log out all of given users sessions
	 *
	 * @param user
	 */
	public static void logout(RollspelUser user) {
		try {
			EntityManager em = getEntityManager();
			em.getTransaction().begin();
			List<RollspelSession> sessionList = em.createQuery(
					  "SELECT s FROM RollspelSession s "
					  + "WHERE s.user = :user", RollspelSession.class)
					  .setParameter("user", user).getResultList();
			String logoutURL = null;
			for (RollspelSession session : sessionList) {
				em.remove(session);
				if (logoutURL == null) {
					logoutURL = session.getUser().getOauthProvider().getLogoutURL();
					if (logoutURL != null) {
						HttpTool.darksideGetRequest(logoutURL,
								  "?access_token=" + session.getOauthAccessToken());
					}
				}
			}

			em.getTransaction().commit();
		} catch (Exception ex) {
			logIt(LOG_CRASH, "User " + user + " failed complete logout.", ex, user);
		}
	}

	public static RollspelSession getSession() {
		return getEntityManager().createQuery(
				  "SELECT s FROM RollspelSession s "
				  + "WHERE s.sessionID = :id",
				  RollspelSession.class)
				  .setParameter("id", CharbuildApp.getSessionID())
				  .getSingleResult();
	}

	/**
	 * Get the User that has has loggin in on the given sessionID.
	 *
	 * @param sessionID
	 * @return user corresponding to the sessionID
	 */
	public static RollspelUser getUser(String sessionID) {
		if (sessionID.equalsIgnoreCase("")) {
			return null;
		}
		EntityManager em = getEntityManager();
		try {
			RollspelUser user = em.createQuery(
					  "SELECT u FROM RollspelSession s "
					  + "JOIN s.user u "
					  + "WHERE s.sessionID = :id", RollspelUser.class)
					  .setParameter("id", sessionID).getSingleResult();
			return user;

		} catch (NoResultException nre) {
			logIt(1, "getUser action, no user found for session id \"" + sessionID + "\"", null, null);
		} catch (Exception e) {
			logIt(9, "getUser action, exception for session id \"" + sessionID + "\"", null, null);
		}
		return null;
	}

	public static RollspelUser login(OauthProvider provider,
			  String providerUserID, String access_token) throws Exception {
		if (provider == null) {
			throw new Exception("provider parameter had null value.");
		}
		if (providerUserID == null) {
			throw new Exception("providerUserID parameter had null value.");
		}
		if (access_token == null) {
			throw new Exception("access_token parameter had null value.");
		}
		//OLD USER - RETURN
		try {
			EntityManager em = getEntityManager();
			try {
				RollspelUser user = em.createQuery(
						  "SELECT u FROM RollspelUser u "
						  + "WHERE u.oauthProvider.id = :id "
						  + "AND u.oauthUserID = :userID", RollspelUser.class)
						  .setParameter("id", provider.getId())
						  .setParameter("userID", providerUserID).getSingleResult();
				if (user != null) { // USER EXISTS, CREATE A SESSION Entity.
					em.getTransaction().begin();
					RollspelSession session = new RollspelSession();
					session.setSessionID(CharbuildApp.getSessionID());
					session.setUser(user);
					session.setOauthAccessToken(access_token);
					session.setIpAddr(CharbuildApp.getIP());
					em.persist(session);
					em.getTransaction().commit();

					return user;
				}
			} catch (NoResultException ex) {
			} catch (Exception ex) {
				throw ex;
			}
			//NEW USER - CREATE AND RETURN
			try {
				em.getTransaction().begin();
				//NEW USER
				RollspelUserRole role = em.find(RollspelUserRole.class, 1);
				RollspelStatus status = em.find(RollspelStatus.class, 2);

				RollspelUser user = new RollspelUser();
				user.setRollspelStatus(status);
				user.setRollspelUserRole(role);
				user.setOauthProvider(provider);
				user.setOauthUserID(providerUserID);
				em.persist(user);
				//NEW SESSION
				RollspelSession session = new RollspelSession();
				session.setSessionID(CharbuildApp.getSessionID());
				session.setUser(user);
				session.setOauthAccessToken(access_token);
				em.persist(session);
				logIt(LOG_INFO, "Created user " + user.toString(), null, user);
				em.getTransaction().commit();
				return user;
			} catch (Exception ex) {
				logIt(LOG_CRASH, "Create new user failed.", ex, null);
			}


		} catch (Exception ex) {
			logIt(LOG_CRASH, "Login functing failed.. ", ex, null);
		}
		return null;
	}

	public static MutantBaseStatCharacter getCharacterBaseStat(MutantCharacter mutantCharacter, int stat_id) throws Exception {
		if (stat_id < 1 || stat_id > 7) {
			throw new Exception("");
		}
		try {
			EntityManager em = getEntityManager();
			MutantBaseStat sto = em.find(MutantBaseStat.class, stat_id);
			MutantBaseStatCharacter statChar = em.createQuery(
					  "SELECT r "
					  + "FROM MutantBaseStatCharacter r "
					  + "WHERE r.mutantCharacter = :char "
					  + "AND r.mutantBaseStat = :stat",
					  MutantBaseStatCharacter.class)
					  .setParameter("char", mutantCharacter)
					  .setParameter("stat", sto)
					  .getSingleResult();
			return statChar;
		} catch (Exception e) {
			throw new Exception("");
		}
	}

	/**
	 *
	 * @param logType use static field of this class
	 * @param data describe issue
	 * @param stackTrace
	 */
	public static void logIt(Integer logType, String data, Throwable exception, RollspelUser user) {
		try {
			EntityManager em = getEntityManager();
			em.getTransaction().begin();

			RollspelLog logEntry = new RollspelLog();
			RollspelLogType type = em.find(RollspelLogType.class, logType);
			logEntry.setRollspelLogType(type);
			logEntry.setRollspelUser(user);
			logEntry.setLogInfo(data);
			if (exception != null) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				exception.printStackTrace(pw);
				logEntry.setStackTrace(sw.toString());
			}

			em.persist(logEntry);
			em.getTransaction().commit();
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder("Database loggin failed:");
			sb.append("\nLogtype: ");
			sb.append(logType);
			sb.append("\nData: ");
			sb.append(data);
			sb.append("\nStacktrace:");
			if (exception != null) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				exception.printStackTrace(pw);
				sb.append("\n");
				sb.append(sw.toString());
			}
			System.err.println(sb.toString());
		}
	}
}
