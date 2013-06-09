package org.characterbuilder.pages.admin;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import javax.persistence.EntityManager;
import org.characterbuilder.CharbuildApp;
import org.characterbuilder.pages.abst.WorkspaceLayout;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.RollspelUser;
import org.characterbuilder.persist.entity.RollspelUserRole;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@SuppressWarnings({"serial"})
public class AdminUsers extends WorkspaceLayout implements ValueChangeListener {

	private Panel thePanel = new Panel();
	private Table itemTable = new Table();
	private String[] headerNames = new String[]{
		"e-mail",
		"User role"
	};

	public AdminUsers() {

		removeAllComponents();

		itemTable.setMultiSelect(false);
		itemTable.setNullSelectionAllowed(true);
		itemTable.setColumnReorderingAllowed(false);
		itemTable.setColumnCollapsingAllowed(false);

		itemTable.addListener(this);
		itemTable.setImmediate(true);
		itemTable.setSelectable(true);
		for (String name : headerNames) {
			itemTable.addContainerProperty(name, String.class, null);
		}

		addComponent(itemTable);
		//POPULATE USERS
		EntityManager em = ThePersister.getEntityManager();
		try {
			List<RollspelUser> itemList = em.createQuery(
					  "SELECT r FROM RollspelUser r",
					  RollspelUser.class).getResultList();

			for (RollspelUser mi : itemList) {
				addItemToTabel(mi);
			}
		} catch (Exception e) {
			notifyError("Failed", "Loading news", e, null);
		}

		addComponent(thePanel);
		//thePanel.addComponent(new ItemView());

	}

	private void addItemToTabel(RollspelUser item) {
		String[] itemData = new String[]{
			item.toString(),
			item.getRollspelUserRole().getName()
		};

		itemTable.addItem(itemData, item);
	}

	private class ItemView extends VerticalLayout {

		RollspelUser user = null;
		ListSelect roleSelect = new ListSelect("User Role");
		//TextField emailField = new TextField("E-mail");
		Label usrNameLabel = null;
		Button saveButton = new Button("Spara användare");

		ItemView(RollspelUser ittem) {
			user = ittem;

			roleSelect.setMultiSelect(false);
			roleSelect.setNullSelectionAllowed(false);

			EntityManager em = ThePersister.getEntityManager();
			try {
				List<RollspelUserRole> roleList = em.createQuery(
						  "SELECT r FROM RollspelUserRole r",
						  RollspelUserRole.class).getResultList();
				for (RollspelUserRole roleSingle : roleList) {
					roleSelect.addItem(roleSingle);

					if (user != null) {
						RollspelUserRole userRole = user.getRollspelUserRole();
						if (roleSingle.getId() == userRole.getId()) {
							roleSelect.select(roleSingle);
						}
					}
				}
			} catch (Exception e) {
				notifyError("Mutant Class", "Unable to load..", e, null);
			}

			addComponent(new Label(user.toString()));
			addComponent(roleSelect);
			addComponent(saveButton);
			saveButton.addListener(new Saver());

		}

		private class Saver implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				RollspelUser user = ThePersister.getRollspelUser(VaadinService.getCurrentRequest().getUserPrincipal().getName());
				
				EntityManager em = ThePersister.getEntityManager();
				try {

					em.getTransaction().begin();
					Object obj = roleSelect.getValue();
					if (obj instanceof RollspelUserRole) {
						RollspelUserRole role = (RollspelUserRole) obj;
						user.setRollspelUserRole(role);
						//user.setEmail(emailField.getValue().toString());

						em.merge(user);

						em.getTransaction().commit();
						notifyInfo("Save user changes", "sucess", null, null);
					} else {
						notifyError("Failed", "Could not find User Role", null, null);
					}
				} catch (Exception e) {
					notifyError("Saving user", "failed", e, null);
				}

			}
		}
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		Object eventObject = event.getProperty().getValue();
		if (eventObject instanceof RollspelUser) {
			RollspelUser item = (RollspelUser) eventObject;
			thePanel.setVisible(true);
			thePanel.setContent(new ItemView(item));
		} else {
			thePanel.setVisible(false);
		}
	}
}
