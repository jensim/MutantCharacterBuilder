package org.characterbuilder.pages.abst.trudvang;

import javax.persistence.EntityManager;
import org.characterbuilder.pages.abst.WorkspaceLayout;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.TrudvangCharacter;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
public abstract class TrudvangWorkspaceLayout extends WorkspaceLayout {

	TrudvangCharacter character = null;

	public TrudvangWorkspaceLayout(TrudvangCharacter character) {
		super();
	}

	protected void commitCharacter() {
		try {
			EntityManager em = ThePersister.getEntityManager();
			em.getTransaction().begin();
			em.merge(character);
			em.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			notifyError("Character fail", "Failed commiting changes", e, null);
		}
	}
}
