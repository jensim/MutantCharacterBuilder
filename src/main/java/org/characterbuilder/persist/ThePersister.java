package org.characterbuilder.persist;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.characterbuilder.persist.entity.MutantBaseStat;
import org.characterbuilder.persist.entity.MutantBaseStatCharacter;
import org.characterbuilder.persist.entity.MutantCharacter;
import org.characterbuilder.persist.entity.RollspelLog;
import org.characterbuilder.persist.entity.RollspelLogType;
import org.characterbuilder.persist.entity.RollspelUser;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Stateless
public class ThePersister {

	public static final Integer LOG_INFO = 1;
	public static final Integer LOG_WARNING = 2;
	public static final Integer LOG_VIOLATION = 3;
	public static final Integer LOG_CRASH = 9;
	public static final String ENTITY_MANAGER_NAME = "CharacterBuilder";
	public static final String ENTITY_MANAGER_JNDI = "java:jboss/datasources/roleplay";

	private static EntityManagerFactory buildEntityManagerFactory(String emName) {
		try {
			return Persistence.createEntityManagerFactory(emName);
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
		try {
			//FIXME: Cannot find in EntityManager on .lookup(String)
			Object obj = new InitialContext().lookup(ENTITY_MANAGER_JNDI);
			if (obj != null) {
				return (EntityManager) obj;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return buildEntityManagerFactory(ENTITY_MANAGER_NAME).createEntityManager();
	}

	public static EntityManager getEntityManager(String enityManagerName) {
		return buildEntityManagerFactory(enityManagerName).createEntityManager();
	}

	public static UserTransaction getUserTransation() {
		InitialContext ic;
		try {
			ic = new InitialContext();
			Object utx = ic.lookup(" java:jboss/UserTransaction");
			if (utx instanceof UserTransaction) {
				return (UserTransaction) utx;
			}
		} catch (NamingException ex) {
			Logger.getLogger(ThePersister.class.getName()).log(Level.SEVERE, null, ex);
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
	 * @param exception
	 * @param user
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

	public static RollspelUser getRollspelUser(String userName) {
		EntityManager em = getEntityManager();
		try {
			return em.createQuery("SELECT u FROM RollspelUser u WHERE u.email = :usr",
					RollspelUser.class).setParameter("usr", userName).getSingleResult();
		} catch (Exception ex) {
			return null;
		}
	}
}
