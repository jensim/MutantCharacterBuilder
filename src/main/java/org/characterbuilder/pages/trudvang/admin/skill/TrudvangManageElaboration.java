package org.characterbuilder.pages.trudvang.admin.skill;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Property;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import javax.persistence.EntityManager;
import org.characterbuilder.defaults.MyTextArea;
import org.characterbuilder.pages.abst.trudvang.admin.TrudvangManage;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.TrudvangBook;
import org.characterbuilder.persist.entity.TrudvangElaboration;
import org.characterbuilder.persist.entity.TrudvangElaborationLevel;
import org.characterbuilder.persist.entity.TrudvangSkill;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
public class TrudvangManageElaboration extends TrudvangManage {

	private JPAContainer<TrudvangElaboration> container = null;
	private String[] COL_HEADERS = new String[]{"name"};
	private TrudvangSkill skill = null;
	private VerticalLayout editArea = new VerticalLayout();
	private HorizontalLayout mainLayout = new HorizontalLayout();

	public TrudvangManageElaboration(TrudvangSkill skill) {
		super();
		this.skill = skill;

		container = JPAContainerFactory.make(TrudvangElaboration.class, ThePersister.getEntityManager());

		table = new Table("Fördjupningar", container);
		fixTableSettings(container.getContainerPropertyIds(), COL_HEADERS);
		container.setApplyFiltersImmediately(true);
		container.removeAllContainerFilters();
		container.addContainerFilter(new Compare.Equal("skillId", skill));

		table.addListener(new TableListener());

		addComponent(mainLayout);
		mainLayout.addComponent(table);
		mainLayout.addComponent(editArea);
		editArea.addComponent(new ElaborationPanel());

		/*
		 table.addListener(new Property.ValueChangeListener() {
		 @Override
		 public void valueChange(ValueChangeEvent event) {
		 container.refresh();
		 }
		 });
		 */
	}

	private class TableListener implements Property.ValueChangeListener {

		@Override
		public void valueChange(Property.ValueChangeEvent event) {
			editArea.removeAllComponents();
			TrudvangElaboration selectedElaboration = getSelectedEaboration();
			if (selectedElaboration == null) {
				editArea.addComponent(new ElaborationPanel());
			} else {
				editArea.addComponent(new ElaborationPanel(selectedElaboration));
			}
		}
	}

	public TrudvangElaboration getSelectedEaboration() {
		try {
			return container.getItem(table.getValue()).getEntity();
		} catch (Exception ex) {
			return null;
		}
	}

	private class ElaborationPanel extends Panel {

		private TextField nameField = new TextField("Name");
		private MyTextArea descriptionArea = new MyTextArea("Description");
		//private TextField costField = new TextField("Cost");
		private JPAContainer<TrudvangElaborationLevel> levelContainer = 
				  JPAContainerFactory.make(TrudvangElaborationLevel.class, 
				  ThePersister.ENTITY_MANAGER_NAME);
		private NativeSelect skillLevelSelect = new NativeSelect("Level", levelContainer);
		private NativeSelect bookSelect = new NativeSelect("Book source");
		private Button addButt = new Button("Add", new AddListener());
		private Button remButt = new Button("Remove", new RemoveListener());
		private Button edtButt = new Button("Edit", new EditListener());
		private VerticalLayout panelLayout = new VerticalLayout();
		private HorizontalLayout buttLayout = new HorizontalLayout();

		public ElaborationPanel() {
			buildUI();
			buttLayout.addComponent(addButt);
		}

		public ElaborationPanel(TrudvangElaboration elaboration) {
			buildUI();

			TrudvangBook book = elaboration.getBookId();
			bookSelect.select(book);

			nameField.setValue(elaboration.getName());
			descriptionArea.setValue(elaboration.getDescription());
			skillLevelSelect.select(levelContainer.getItemIds().iterator().next());
			//costField.setValue(elaboration.getCost().toString());

			buttLayout.addComponent(edtButt);
			buttLayout.addComponent(remButt);
		}

		private void buildUI() {
			bookSelect.setNullSelectionAllowed(false);
			bookSelect.setMultiSelect(false);

			panelLayout.addComponent(nameField);
			panelLayout.addComponent(descriptionArea);
			//panelLayout.addComponent(costField);
			panelLayout.addComponent(skillLevelSelect);
			panelLayout.addComponent(bookSelect);
			panelLayout.addComponent(buttLayout);
			addBooksToSelect();
			setContent(panelLayout);
		}

		private void addBooksToSelect() {
			for (TrudvangBook book : getBooks()) {
				bookSelect.addItem(book);
				if (bookSelect.getValue() == null) {
					bookSelect.select(book);
				}
			}
		}

		private List<TrudvangBook> getBooks() {
			try {
				EntityManager em = ThePersister.getEntityManager();
				return em.createQuery("SELECT b FROM TrudvangBook b ",
						  TrudvangBook.class).getResultList();
			} catch (Exception ex) {
				return null;
			}
		}

		private class AddListener implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					EntityManager em = ThePersister.getEntityManager();
					em.getTransaction().begin();
					TrudvangElaboration newElaboration = new TrudvangElaboration();
					newElaboration.setBookId((TrudvangBook) bookSelect.getValue());
					newElaboration.setName(nameField.getValue().toString());
					newElaboration.setDescription(descriptionArea.getValue().toString());
					newElaboration.setSkillLevel(levelContainer.getItem(skillLevelSelect.getValue()).getEntity());
					newElaboration.setSkillId(skill);
					em.persist(newElaboration);
					em.getTransaction().commit();
					
					container.refresh();
					table.select(null);
				} catch (NumberFormatException nfe) {
					notifyWarning("Manager Elaboration add",
							  "Bad number format adding new elaboration", nfe, null);
				} catch (Exception ex) {
					notifyError("Manager Elaboration add",
							  "Suffered an exceptionn while adding a new elaboration",
							  ex, null);
				}
			}
		}

		private class EditListener implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					TrudvangElaboration edit = getSelectedEaboration();
					if (edit != null) {
						EntityManager em = ThePersister.getEntityManager();
						em.getTransaction().begin();
						edit = em.find(TrudvangElaboration.class,
								  edit.getId());
						Integer idnr = ((TrudvangBook) bookSelect.getValue()).getId();
						TrudvangBook selectedBook = em.find(TrudvangBook.class, idnr);
						edit.setBookId(selectedBook);
						edit.setName(nameField.getValue().toString());
						edit.setDescription(descriptionArea.getValue().toString());
						edit.setSkillLevel(levelContainer.getItem(skillLevelSelect.getValue()).getEntity());
						em.getTransaction().commit();

						container.refresh();
					}
				} catch (Exception ex) {
					notifyError("Manage Elaboration",
							  "Exception occured editing elaboration", ex, null);
				}
			}
		}

		private class RemoveListener implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					TrudvangElaboration toRemove = getSelectedEaboration();
					if (toRemove != null) {
						EntityManager em = ThePersister.getEntityManager();
						em.getTransaction().begin();
						toRemove = em.find(TrudvangElaboration.class,
								  toRemove.getId());
						em.remove(toRemove);
						em.getTransaction().commit();

						container.refresh();
						table.select(null);
					}
				} catch (Exception ex) {
					notifyError("Manage Elaboration",
							  "Exception occured removing elaboration", ex, null);
				}
			}
		}
	}
}
