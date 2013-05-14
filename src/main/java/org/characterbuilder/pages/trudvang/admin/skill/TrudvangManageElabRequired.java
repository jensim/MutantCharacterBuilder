package org.characterbuilder.pages.trudvang.admin.skill;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Item;
import com.vaadin.data.util.filter.Compare.Equal;
import com.vaadin.data.util.filter.Not;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import javax.persistence.EntityManager;
import org.characterbuilder.pages.abst.trudvang.admin.TrudvangManage;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.TrudvangElaboration;
import org.characterbuilder.persist.entity.TrudvangElaborationRequire;

/**
 *
* @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
public class TrudvangManageElabRequired extends TrudvangManage {

	private JPAContainer<TrudvangElaborationRequire> containerHas = null;
	private JPAContainer<TrudvangElaboration> containerHasnt = null;
	private Table tableHasnt = null;
	
	private HorizontalLayout mainLayout = new HorizontalLayout();
	private VerticalLayout requiredLayout = new VerticalLayout(),
			  elabLayout = new VerticalLayout();
	
	private Button addButt = new Button("Add.."),
			  removeButt = new Button("Remove..");
	
	private TrudvangElaboration elaboration;

	public TrudvangManageElabRequired(TrudvangElaboration elaboration) {
		super();
		this.elaboration = elaboration;
		containerHas = JPAContainerFactory.make(TrudvangElaborationRequire.class,
				  ThePersister.ENTITY_MANAGER_NAME);
		containerHas.addNestedContainerProperty("requiredId.name");
		
		table = new Table("Required elaborations", containerHas);
		fixTableSettings(containerHas.getContainerPropertyIds(), new String[]{"requiredId.name"});
		
		containerHasnt = JPAContainerFactory.make(TrudvangElaboration.class,
				  ThePersister.ENTITY_MANAGER_NAME);
		tableHasnt = new Table("Other elaborations", containerHasnt);
		tableHasnt.setColumnCollapsingAllowed(true);
		for (String id : containerHasnt.getContainerPropertyIds()) {
			tableHasnt.setColumnCollapsed(id, true);
		}
		tableHasnt.setColumnCollapsed("name", false);
		
		tableHasnt.setEnabled(true);
		tableHasnt.setMultiSelect(false);
		tableHasnt.setNullSelectionAllowed(true);
		tableHasnt.setSelectable(true);
		tableHasnt.setImmediate(true);

		addComponent(mainLayout);
		mainLayout.addComponent(requiredLayout);
		mainLayout.addComponent(elabLayout);


		requiredLayout.addComponent(table);
		requiredLayout.addComponent(removeButt);
		removeButt.addClickListener(new Remover());
		elabLayout.addComponent(tableHasnt);
		elabLayout.addComponent(addButt);
		addButt.addClickListener(new Adder());
		
		updateFilters();
	}
	
	private void updateFilters(){
		containerHas.removeAllContainerFilters();
		containerHas.addContainerFilter(new Equal("elaborationId", elaboration));
		/* * * * * * * * * * * * * * * * * * * */
		//SET FILTERS FOR POSSIBLE ELABORATIONS
		/* * * * * * * * * * * * * * * * * * * */
		containerHasnt.removeAllContainerFilters();
		
		containerHasnt.addNestedContainerProperty("skillId.id");
		
		//SAME SKILL
		containerHasnt.addContainerFilter(new Equal("skillId", elaboration.getSkillId()));
		//NOT ITSELT
		containerHasnt.addContainerFilter(new Not(new Equal("id", elaboration.getId())));
		//NOT LOOPING 1st DEGREE
		for(TrudvangElaborationRequire req : elaboration.getElabRequireList()){
			containerHasnt.addContainerFilter(new Not(new Equal("id", req.getRequiredId().getId())));
		}
	}

	public TrudvangElaborationRequire getSelectedRequired() {
		try {
			return containerHas.getItem(table.getValue()).getEntity();
		} catch (Exception ex) {
			return null;
		}
	}

	public TrudvangElaboration getSelectedElaboration() {
		try {
			return containerHasnt.getItem(tableHasnt.getValue()).getEntity();
		} catch (Exception ex) {
			return null;
		}
	}

	private class Adder implements Button.ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			System.out.println("BUTTON KLIKKED!");
			TrudvangElaboration elab = getSelectedElaboration();
			if (elab != null) {
				try {
					EntityManager em = ThePersister.getEntityManager();
					em.getTransaction().begin();
					TrudvangElaborationRequire newRequire = new TrudvangElaborationRequire();
					newRequire.setElaborationId(elaboration);
					newRequire.setRequiredId(elab);
					
					System.out.println("elab: "+elaboration.toString()+"\nrequired: "+elab.toString());
					
					em.persist(newRequire);
					em.getTransaction().commit();
					
					containerHas.refresh();
					containerHasnt.refresh();
				} catch (Exception ex) {
					notifyError("Manage Elaboration Requirement",
							  "Exception occured adding requirement", ex, null);
				}
			}
		}
	}

	private class Remover implements Button.ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			TrudvangElaborationRequire req = getSelectedRequired();
			if (req != null) {
				try {
					EntityManager em = ThePersister.getEntityManager();
					em.getTransaction().begin();
					req = em.find(TrudvangElaborationRequire.class, req.getId());
					em.remove(req);
					em.getTransaction().commit();
					
					containerHas.refresh();
					containerHasnt.refresh();
				} catch (Exception ex) {
					notifyError("Manage Elaboration Requirement",
							  "Exception occured removing requirement", ex, null);
				}
			}
		}
	}
}
