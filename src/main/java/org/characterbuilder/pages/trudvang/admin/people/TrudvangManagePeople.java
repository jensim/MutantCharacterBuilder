package org.characterbuilder.pages.trudvang.admin.people;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import java.util.List;
import javax.persistence.EntityManager;
import org.characterbuilder.defaults.MyHorizontalLayout;
import org.characterbuilder.defaults.MyTextArea;
import org.characterbuilder.defaults.MyVerticalLayout;
import org.characterbuilder.pages.abst.trudvang.admin.TrudvangManage;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.TrudvangBook;
import org.characterbuilder.persist.entity.TrudvangPeople;
import org.characterbuilder.persist.entity.TrudvangUpbringing;

/**
 * TrudvangManagePeople
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public class TrudvangManagePeople extends TrudvangManage{
	private JPAContainer<TrudvangPeople> container = null;
	private MyHorizontalLayout mainLayout = new MyHorizontalLayout();
	private MyVerticalLayout editArea = new MyVerticalLayout();

	public TrudvangManagePeople() {
		container = JPAContainerFactory.make(TrudvangPeople.class, ThePersister.ENTITY_MANAGER_NAME);
		table = new Table(null, container);
		fixTableSettings(container.getContainerPropertyIds(), new String[]{"name"});
		table.addListener(new TableListener());
		
		addComponent(mainLayout);
		mainLayout.addComponent(table);
		mainLayout.addComponent(editArea);
		editArea.addComponent(new ExceptionalPanel());
	}
	
	private class TableListener implements Property.ValueChangeListener {

		@Override
		public void valueChange(Property.ValueChangeEvent event) {
			editArea.removeAllComponents();
			TrudvangPeople selectedExcep = getSelectedPeople();
			if (selectedExcep == null) {
				editArea.addComponent(new ExceptionalPanel());
			} else {
				editArea.addComponent(new ExceptionalPanel(selectedExcep));
			}
		}
	}

	public TrudvangPeople getSelectedPeople() {
		try {
			return container.getItem(table.getValue()).getEntity();
		} catch (Exception ex) {
			return null;
		}
	}

	private class ExceptionalPanel extends Panel {

		private TextField nameField = new TextField("Name");
		private MyTextArea descriptionArea = new MyTextArea("Description");
		private NativeSelect bookSelect = new NativeSelect("Book source");
		private Button addButt = new Button("Add", new AddListener());
		private Button editButt = new Button("Edit", new EditListener());
		private Button remButt = new Button("Remove", new RemoveListener());
		private MyVerticalLayout panelLayout = new MyVerticalLayout();
		private MyHorizontalLayout buttLayout = new MyHorizontalLayout();

		public ExceptionalPanel() {
			super();
			buildUI();
			buttLayout.addComponent(addButt);
		}

		public ExceptionalPanel(TrudvangPeople excep) {
			super();
			buildUI();
			
			nameField.setValue(excep.getName());
			descriptionArea.setValue(excep.getDescription());
			bookSelect.select(excep.getBookId());
			
			buttLayout.addComponent(editButt);
			buttLayout.addComponent(remButt);
		}

		private void buildUI() {
			bookSelect.setNullSelectionAllowed(false);
			bookSelect.setMultiSelect(false);

			panelLayout.addComponent(nameField);
			panelLayout.addComponent(descriptionArea);
			panelLayout.addComponent(bookSelect);

			panelLayout.addComponent(buttLayout);
			setContent(panelLayout);
			addBooksToSelect();
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
					TrudvangPeople newExceptional = new TrudvangPeople();
					newExceptional.setBookId((TrudvangBook) bookSelect.getValue());
					newExceptional.setName(nameField.getValue().toString());
					newExceptional.setDescription(descriptionArea.getValue().toString());
					em.persist(newExceptional);
					em.getTransaction().commit();

					table.select(null);
				} catch (NumberFormatException nfe) {
					notifyWarning("Manager Exceptional add",
							  "Bad number format adding new exceptional", nfe, null);
				} catch (Exception ex) {
					notifyError("Manager Exceptional add",
							  "Suffered an exceptionn while adding a new exceptional",
							  ex, null);
				}
			}
		}

		private class EditListener implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					TrudvangPeople edit = getSelectedPeople();
					if (edit != null) {
						EntityManager em = ThePersister.getEntityManager();
						em.getTransaction().begin();
						edit = em.find(TrudvangPeople.class,
								  edit.getId());
						Integer idnr = ((TrudvangBook) bookSelect.getValue()).getId();
						TrudvangBook selectedBook = em.find(TrudvangBook.class, idnr);
						edit.setBookId(selectedBook);
						edit.setName(nameField.getValue().toString());
						edit.setDescription(descriptionArea.getValue().toString());
						em.getTransaction().commit();
					}
				} catch (Exception ex) {
					notifyError("Manage Exceptional",
							  "Exception occured editing exceptional ability", ex, null);
				}
			}
		}

		private class RemoveListener implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					TrudvangPeople rem = getSelectedPeople();
					if (rem != null) {
						EntityManager em = ThePersister.getEntityManager();
						em.getTransaction().begin();
						em.remove(rem);
						em.getTransaction().commit();
					}
				}catch(Exception ex){
					notifyError("Manage Exceptional",
							  "Exception occured removing exceptional", ex, null);
				}
			}
		}
	}
}
