package org.characterbuilder.pages.trudvang.admin.upbringing;

import com.vaadin.data.Property;
import com.vaadin.ui.Accordion;
import org.characterbuilder.pages.abst.WorkspaceLayout;
import org.characterbuilder.pages.trudvang.admin.TrudvangManageStartskills;
import org.characterbuilder.persist.entity.TrudvangUpbringing;

/**
 * TrudvangManageSuperUpbringing
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public class TrudvangManageSuperUpbringing extends WorkspaceLayout{

	private TrudvangManageUpbringing manageUpbringing = new TrudvangManageUpbringing();
	private TrudvangManageStartskills manageStartSkills = null;
	private Accordion accordion = new Accordion();
	private Accordion.Tab upbringingTab, skillTab;
	
	public TrudvangManageSuperUpbringing(){
		manageUpbringing.addListener(new ExcepListener());
		
		addComponent(accordion);
		upbringingTab = accordion.addTab(manageUpbringing, "Upgringing");
	}
	
	private class ExcepListener implements Property.ValueChangeListener{
		private static final String LEVEL_TAB_CAPSTART = "Start skills due to ";
		
		@Override
		public void valueChange(Property.ValueChangeEvent event) {
			if(skillTab != null){
				accordion.removeTab(skillTab);
				skillTab = null;
			}
			TrudvangUpbringing upbringing = manageUpbringing.getSelectedUpbringing();
			if(upbringing != null){
				manageStartSkills = new TrudvangManageStartskills(upbringing);
				skillTab = accordion.addTab(manageStartSkills, LEVEL_TAB_CAPSTART+upbringing.getName());
			}
		}
	}
}
