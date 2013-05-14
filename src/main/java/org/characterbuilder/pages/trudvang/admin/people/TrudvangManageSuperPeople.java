package org.characterbuilder.pages.trudvang.admin.people;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Accordion;
import org.characterbuilder.pages.abst.WorkspaceLayout;
import org.characterbuilder.pages.trudvang.admin.TrudvangManageStartskills;
import org.characterbuilder.persist.entity.TrudvangPeople;

/**
 * TrudvangManageSuperPeople
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public class TrudvangManageSuperPeople extends WorkspaceLayout{

	private TrudvangManagePeople managePeople = new TrudvangManagePeople();
	private TrudvangManageStartskills manageStartSkills = null;
	private Accordion accordion = new Accordion();
	private Accordion.Tab arktypeTab, skillTab;
	
	public TrudvangManageSuperPeople(){
		managePeople.addListener(new ExcepListener());
		
		addComponent(accordion);
		arktypeTab = accordion.addTab(managePeople, "People");
	}
	
	private class ExcepListener implements Property.ValueChangeListener{
		private static final String LEVEL_TAB_CAPSTART = "Start skills due to ";
		
		@Override
		public void valueChange(ValueChangeEvent event) {
			if(skillTab != null){
				accordion.removeTab(skillTab);
				skillTab = null;
			}
			TrudvangPeople arktype = managePeople.getSelectedPeople();
			if(arktype != null){
				manageStartSkills = new TrudvangManageStartskills(arktype);
				skillTab = accordion.addTab(manageStartSkills, LEVEL_TAB_CAPSTART+arktype.getName());
			}
		}
	}
}
