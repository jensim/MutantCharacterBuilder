package org.characterbuilder.pages.abst.trudvang.admin;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Table;
import java.util.Collection;
import org.characterbuilder.pages.abst.WorkspaceLayout;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
public class TrudvangManage extends WorkspaceLayout implements Property.ValueChangeNotifier {

	protected Table table = null;
	//protected VerticalLayout tableLayout = new VerticalLayout();
	//protected VerticalLayout bottomLayout = new VerticalLayout();

	public TrudvangManage() {
		//addComponent(tableLayout);
		//addComponent(bottomLayout);
		/*
		 RollspelUser user = CharbuildApp.getRollspelUser();
		 EntityManager em = ThePersister.getEntityManager();
		 Integer roleID = em.find(RollspelUser.class, user.getId()).getRollspelUserRole().getId();
		
		 if(roleID < 4){
		 CharbuildApp.logout();
		 }
		 */
	}

	protected void fixTableSettings(Collection<String> containerPropertyIds, String[] colHeaders) {
		table.setColumnCollapsingAllowed(true);
		for (String id : containerPropertyIds) {
			table.setColumnCollapsed(id, true);
		}
		for (String id : colHeaders) {
			table.setColumnCollapsed(id, false);
		}

		table.setEnabled(true);
		table.setMultiSelect(false);
		table.setNullSelectionAllowed(true);
		table.setSelectable(true);

		table.setImmediate(true);

		/*try {
		 table.select(table.getItemIds().iterator().next());
		 } catch (Exception ex) {
		 }*/
		//table.setWidth("100%");
		//tableLayout.addComponent(table);
	}

	@Override
	public void addValueChangeListener(ValueChangeListener listener) {
		table.addListener(listener);
	}

	@Override
	public void addListener(ValueChangeListener listener) {
		table.addListener(listener);
	}

	@Override
	public void removeValueChangeListener(ValueChangeListener listener) {
		table.removeListener(listener);
	}

	@Override
	public void removeListener(ValueChangeListener listener) {
		table.removeListener(listener);
	}
}
