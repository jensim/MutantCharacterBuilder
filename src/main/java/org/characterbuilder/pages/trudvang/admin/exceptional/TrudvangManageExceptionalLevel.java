package org.characterbuilder.pages.trudvang.admin.exceptional;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import javax.persistence.EntityManager;
import org.characterbuilder.defaults.MyHorizontalLayout;
import org.characterbuilder.defaults.MyTextArea;
import org.characterbuilder.defaults.MyVerticalLayout;
import org.characterbuilder.pages.abst.trudvang.admin.TrudvangManage;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.TrudvangExceptional;
import org.characterbuilder.persist.entity.TrudvangExceptionalLevel;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
public class TrudvangManageExceptionalLevel extends TrudvangManage {

	private JPAContainer<TrudvangExceptionalLevel> container = null;
	private MyHorizontalLayout mainLayout = new MyHorizontalLayout();
	private MyVerticalLayout editArea = new MyVerticalLayout();
	TrudvangExceptional excep;

	public TrudvangManageExceptionalLevel(TrudvangExceptional excep) {
		this.excep = excep;
		container = JPAContainerFactory.make(TrudvangExceptionalLevel.class, ThePersister.ENTITY_MANAGER_NAME);
		container.addContainerFilter(new Compare.Equal("exceptionalId", this.excep));
		
		table = new Table(null, container);
		table.addListener(new TableListener());
		fixTableSettings(container.getContainerPropertyIds(), new String[]{"name","value"});
		
		addComponent(mainLayout);
		mainLayout.addComponent(table);
		mainLayout.addComponent(editArea);
		editArea.addComponent(new LevelPanel());
	}

	private class TableListener implements Property.ValueChangeListener {

		@Override
		public void valueChange(Property.ValueChangeEvent event) {
			editArea.removeAllComponents();
			TrudvangExceptionalLevel selectedExcep = getSelectedLevel();
			if (selectedExcep == null) {
				editArea.addComponent(new LevelPanel());
			} else {
				editArea.addComponent(new LevelPanel(selectedExcep));
			}
		}
	}

	public TrudvangExceptionalLevel getSelectedLevel() {
		Item itm = table.getItem(table.getValue());
		if (itm != null) {
			try {
				EntityManager em = ThePersister.getEntityManager();
				TrudvangExceptionalLevel level = em.find(TrudvangExceptionalLevel.class,
						  Integer.parseInt(itm.getItemProperty("id").toString()));
				return level;
			} catch (Exception ex) {
			}
		}
		return null;
	}

	private class LevelPanel extends Panel {

		private TextField nameField = new TextField("Name");
		private MyTextArea descriptionArea = new MyTextArea("Description");
		private TextField valueField = new TextField("Value");
		private Button addButt = new Button("Add");
		private Button editButt = new Button("Edit");
		private Button remButt = new Button("Remove");
		private MyVerticalLayout panelLayout = new MyVerticalLayout();
		private MyHorizontalLayout buttLayout = new MyHorizontalLayout();

		public LevelPanel() {
			super();
			buildUI();
			buttLayout.addComponent(addButt);
		}

		public LevelPanel(TrudvangExceptionalLevel level) {
			super();
			buildUI();
			buttLayout.addComponent(editButt);
			buttLayout.addComponent(remButt);
			
			nameField.setValue(level.getName());
			descriptionArea.setValue(level.getDescription());
			valueField.setValue(level.getValue().toString());
		}

		private void buildUI() {

			panelLayout.addComponent(nameField);
			panelLayout.addComponent(descriptionArea);
			panelLayout.addComponent(valueField);

			panelLayout.addComponent(buttLayout);
			setContent(panelLayout);
		}

		private class AddListener implements Button.ClickListener {

			@Override
			public void buttonClick(Button.ClickEvent event) {
				try {
					EntityManager em = ThePersister.getEntityManager();
					em.getTransaction().begin();
					TrudvangExceptionalLevel newLevel = new TrudvangExceptionalLevel();
					newLevel.setExceptionalId(excep);
					newLevel.setName(nameField.getValue().toString());
					newLevel.setDescription(descriptionArea.getValue().toString());
					newLevel.setValue(Integer.parseInt(valueField.getValue()));
					em.persist(newLevel);
					em.getTransaction().commit();

					table.select(null);
				} catch (NumberFormatException nfe) {
					notifyWarning("Manager Exceptional",
							  "Bad number format adding new exceptionalLevel", nfe, null);
				} catch (Exception ex) {
					notifyError("Manager ExceptionalLevel",
							  "Suffered an exceptionn while adding a new exceptionalLevel",
							  ex, null);
				}
			}
		}

		private class EditListener implements Button.ClickListener {

			@Override
			public void buttonClick(Button.ClickEvent event) {
				try {
					TrudvangExceptionalLevel edit = getSelectedLevel();
					if (edit != null) {
						EntityManager em = ThePersister.getEntityManager();
						em.getTransaction().begin();
						edit = em.find(TrudvangExceptionalLevel.class,edit.getId());
						edit.setName(nameField.getValue().toString());
						edit.setDescription(descriptionArea.getValue().toString());
						edit.setValue(Integer.parseInt(valueField.getValue()));
						em.getTransaction().commit();
					}
				} catch (Exception ex) {
					notifyError("Manage ExceptionalLevel",
							  "Exception occured editing exceptionalLevel", ex, null);
				}
			}
		}

		private class RemoveListener implements Button.ClickListener {

			@Override
			public void buttonClick(Button.ClickEvent event) {
				try {
					TrudvangExceptionalLevel rem = getSelectedLevel();
					if (rem != null) {
						EntityManager em = ThePersister.getEntityManager();
						em.getTransaction().begin();
						em.remove(rem);
						em.getTransaction().commit();
					}
				} catch (Exception ex) {
					notifyError("Manage ExceptionalLevel",
							  "Exception occured removing exceptionalLevel", ex, null);
				}
			}
		}
	}
}
