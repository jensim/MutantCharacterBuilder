package org.characterbuilder.pages.abst.trudvang;

import java.util.Date;
import javax.persistence.EntityManager;
import org.characterbuilder.pages.abst.WorkspaceLayout;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.RollspelSession;
import org.characterbuilder.persist.entity.TrudvangCharacter;

/**
 * TrudvangCharacterWorkspaceLayout
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public abstract class TrudvangCharacterWorkspaceLayout extends WorkspaceLayout {

	private TrudvangCharacter character = null;

	public TrudvangCharacterWorkspaceLayout(TrudvangCharacter character) {
		super();

	}

	protected void updateCharacter(TrudvangCharacter character) {
		try {
			EntityManager em = ThePersister.getEntityManager();
			em.getTransaction().begin();
			this.character = em.find(TrudvangCharacter.class, character.getId());
			RollspelSession session = ThePersister.getSession();
			session.setSessionActivityTimestamp(new Date());
			em.getTransaction().commit();
		} catch (Exception e) {
			notifyError("Exception", "Failed getting updated character.", e, null);
		}
	}

	protected void commitCharacter() {
		EntityManager em = ThePersister.getEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(character);
			em.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			notifyError("Character fail", "Failed commiting changes", e, null);
		}
	}
}
