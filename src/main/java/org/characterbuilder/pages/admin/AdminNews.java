package org.characterbuilder.pages.admin;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import org.characterbuilder.defaults.MyTextArea;
import org.characterbuilder.pages.abst.WorkspaceLayout;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.FrontPageNews;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@SuppressWarnings({"serial"})
public class AdminNews extends WorkspaceLayout implements ValueChangeListener {

	private Panel thePanel = new Panel();
	private Table itemTable = new Table();
	private String[] headerNames = new String[]{
		"Header",
		"Post time",
		"Visible",
		"Death_time"
	};

	public AdminNews() {
		super();

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
		//POPULATE NEWS
		EntityManager em = ThePersister.getEntityManager();
		try {
			List<FrontPageNews> itemList = em.createQuery(
					  "SELECT r FROM FrontPageNews r",
					  FrontPageNews.class).getResultList();

			for (FrontPageNews mi : itemList) {
				addItemToTabel(mi);
			}
		} catch (Exception e) {
			notifyError(
					  "Failed",
					  "Loading news",
					  e,
					  null);
		}

		addComponent(thePanel);
		thePanel.setContent(new ItemView());

	}

	private void addItemToTabel(FrontPageNews item) {


		String[] itemData = new String[]{
			item.getHeader(),
			item.getPostTime().toString(),
			item.getVisible().toString(),
			item.getDeathTime() != null ? item.getDeathTime().toString() : ""
		};

		itemTable.addItem(itemData, item);
	}

	private class ItemView extends VerticalLayout {

		FrontPageNews item = null;
		TextField headerField = new TextField("Header");
		//InlineDateField postTime = new InlineDateField("Post time");
		MyTextArea contentArea = new MyTextArea("Message");
		CheckBox visibleBox = new CheckBox("Visible");
		Button addButton = new Button("Lägg till föremål");
		Button saveButton = new Button("Spara föremål");

		ItemView() {
			buildUI();
		}

		ItemView(FrontPageNews ittem) {
			item = ittem;
			buildUI();
		}

		private void buildUI() {
			addComponent(headerField);
			//addComponent(postTime);
			addComponent(contentArea);
			addComponent(visibleBox);

			if (item != null) {
				headerField.setValue(item.getHeader());
				//postTime.setValue(new java.util.Date(item.getPostTime().getTime()));
				contentArea.setValue(item.getContent());
				visibleBox.setValue(item.getVisible());

				addComponent(saveButton);
				saveButton.addListener(new Saver());
			} else {
				addComponent(addButton);
				addButton.addListener(new Adder());
			}
		}

		private class Adder implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				EntityManager em = ThePersister.getEntityManager();
				try {
					em.getTransaction().begin();
					FrontPageNews newItem = new FrontPageNews();

					newItem.setHeader(headerField.getValue().toString());
					//TODO: POST TIME
					newItem.setPostTime(new Timestamp(new Date().getTime()));
					newItem.setContent(contentArea.getValue().toString());
					newItem.setVisible(visibleBox.booleanValue());

					em.persist(newItem);

					em.getTransaction().commit();
					addItemToTabel(newItem);

					notifyInfo("new item", "saved", null, null);
				} catch (Exception e) {
					notifyError("Failed", "Saving news", e, null);
				}

			}
		}

		private class Saver implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				EntityManager em = ThePersister.getEntityManager();
				try {
					em.getTransaction().begin();

					item.setHeader(headerField.getValue().toString());

					item.setContent(contentArea.getValue().toString());
					item.setVisible(visibleBox.booleanValue());

					em.merge(item);

					em.getTransaction().commit();
					notifyInfo("save item", "sucess", null, null);
				} catch (Exception e) {
					notifyError("Failed", "Saving news", e, null);
				}

			}
		}
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		Object eventObject = event.getProperty().getValue();
		if (eventObject instanceof FrontPageNews) {
			FrontPageNews item = (FrontPageNews) eventObject;
			thePanel.setContent(new ItemView(item));
		} else {
			thePanel.setContent(new ItemView());
		}
	}
}
