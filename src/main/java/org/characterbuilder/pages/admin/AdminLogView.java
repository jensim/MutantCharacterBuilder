package org.characterbuilder.pages.admin;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.MouseEvents;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.characterbuilder.pages.abst.WorkspaceLayout;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.RollspelLog;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
public class AdminLogView extends WorkspaceLayout {

	private final String[] COL_HEADERS = new String[]{
		"id",
		"rollspelUser",
		"rollspelLogType",
		"logtime"};
	private JPAContainer<RollspelLog> logContainer = null;
	private Table table = null;
	private Panel expandedLogPanel = new Panel("Log info");
	private VerticalLayout expandedLogHolder = new VerticalLayout();

	public AdminLogView() {
		super();

		EntityManager em = ThePersister.getEntityManager();
		logContainer = JPAContainerFactory.make(RollspelLog.class, em);
		logContainer.sort(new String[]{"id"}, new boolean[]{false});

		table = new Table("log", logContainer);
		table.setColumnCollapsingAllowed(true);
		table.setSelectable(true);
		table.setMultiSelect(false);
		table.setImmediate(true);
		table.setColumnReorderingAllowed(true);

		for (String propertyID : logContainer.getContainerPropertyIds()) {
			table.setColumnCollapsed(propertyID, true);
		}
		for (String propertyID : COL_HEADERS) {
			table.setColumnCollapsed(propertyID, false);
		}

		table.addListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				expandedLogHolder.removeAllComponents();
				for (Component comp : getTableData()) {
					expandedLogHolder.addComponent(comp);
				}
				table.setWidth("100%");
				expandedLogPanel.setWidth("100%");
			}
		});
		addComponent(table);
		addComponent(expandedLogPanel);
		expandedLogPanel.setWidth("100%");
		expandedLogPanel.setHeight("300px");
		expandedLogPanel.setContent(expandedLogHolder);
		table.setWidth("100%");

		expandedLogPanel.addListener(new MouseEvents.ClickListener() {
			@Override
			public void click(ClickEvent event) {
				Window window = new Window();
				VerticalLayout windowLayout = new VerticalLayout();
				for (Component comp : getTableData()) {
					windowLayout.addComponent(comp);
				}
				window.setContent(windowLayout);
				getUI().addWindow(window);
			}
		});
	}

	private List<Component> getTableData() {
		ArrayList<Component> list = new ArrayList<>();
		Item item = table.getItem(table.getValue());
		if (item == null) {
			return list;
		}
		for (Object obj : item.getItemPropertyIds()) {
			if (obj instanceof String) {
				try {
					list.add(new Label(
							  "<b><u>" + ((String) obj) + ":</u></b> "
							  + item.getItemProperty(obj).toString()
							  .replace("\n", "<br/>"),
							  Label.CONTENT_XHTML));
				} catch (Exception ex) {
				}
			} else {
				expandedLogHolder.addComponent(new Label("Failed"));
				break;
			}
		}
		return list;
	}
}
