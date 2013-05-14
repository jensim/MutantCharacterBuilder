package org.characterbuilder.pages.trudvang.admin.arktype;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Accordion;
import org.characterbuilder.pages.abst.WorkspaceLayout;
import org.characterbuilder.pages.trudvang.admin.TrudvangManageStartskills;
import org.characterbuilder.persist.entity.TrudvangArktype;

/**
 * TrudvangManageSuperArktype
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public class TrudvangManageSuperArktype extends WorkspaceLayout{

	private TrudvangManageArktype manageArktype = new TrudvangManageArktype();
	private TrudvangManageStartskills manageStartSkills = null;
	private Accordion accordion = new Accordion();
	private Accordion.Tab arktypeTab, skillTab;
	
	public TrudvangManageSuperArktype(){
		manageArktype.addListener(new ExcepListener());
		
		addComponent(accordion);
		arktypeTab = accordion.addTab(manageArktype, "Arktypes");
	}
	
	private class ExcepListener implements Property.ValueChangeListener{
		private static final String LEVEL_TAB_CAPSTART = "Start skills due to ";
		
		@Override
		public void valueChange(ValueChangeEvent event) {
			if(skillTab != null){
				accordion.removeTab(skillTab);
				skillTab = null;
			}
			TrudvangArktype arktype = manageArktype.getSelectedArktype();
			if(arktype != null){
				manageStartSkills = new TrudvangManageStartskills(arktype);
				skillTab = accordion.addTab(manageStartSkills, LEVEL_TAB_CAPSTART+arktype.getName());
			}
		}
	}
}
