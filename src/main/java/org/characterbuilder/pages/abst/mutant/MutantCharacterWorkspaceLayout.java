package org.characterbuilder.pages.abst.mutant;

import com.vaadin.data.Property;
import java.util.Date;
import javax.persistence.EntityManager;
import org.characterbuilder.pages.abst.WorkspaceLayout;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.MutantCharacter;
import org.characterbuilder.persist.entity.RollspelSession;

/**
 * 
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public abstract class MutantCharacterWorkspaceLayout extends WorkspaceLayout implements Property.ValueChangeListener {

	private static final long serialVersionUID = -3440045388969118931L;
	protected MutantCharacter mutantCharacter = null;

	public MutantCharacterWorkspaceLayout(MutantCharacter mc) {
		super();
		updateCharacter(mc);
		addComponent(infoPanel);
	}

	protected void updateCharacter(MutantCharacter mc) {
		EntityManager em = ThePersister.getEntityManager();
		try {
			em.getTransaction().begin();
			mutantCharacter = em.find(MutantCharacter.class, mc.getId());
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
			em.merge(mutantCharacter);
			em.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			notifyError("Character fail", "Failed commiting changes", e, null);
		}
	}
}
