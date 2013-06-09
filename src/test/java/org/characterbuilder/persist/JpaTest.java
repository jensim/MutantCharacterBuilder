/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.characterbuilder.persist;

import javax.persistence.EntityManager;
import junit.framework.Assert;
import org.characterbuilder.persist.entity.RollspelLog;
import org.characterbuilder.persist.entity.RollspelLogType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
public class JpaTest {

	EntityManager em = null;

	@Before
	public void setUp() throws Exception {
		em = ThePersister.getEntityManager(ThePersister.ENTITY_MANAGER_NAME+"Test");
	}

	@After
	public void tearDown() throws Exception {
		if (em != null) {
			try {
				em.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			em = null;
		}
	}

	@Test
	public void testJPA() {
		em.getTransaction().begin();
		RollspelLog log = new RollspelLog();
		log.setRollspelLogType(em.find(RollspelLogType.class, 1));
		log.setLogInfo("Running JPA test.");
		em.persist(log);
		Assert.assertNotNull(log.getId());
		RollspelLog gotten = em.find(RollspelLog.class, log.getId());
		Assert.assertNotNull(gotten);
		System.out.println("New log entry id: " + gotten.getId());
	}

}
