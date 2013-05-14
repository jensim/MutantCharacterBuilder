package org.characterbuilder.pages.trudvang.admin.exceptional;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Accordion;
import org.characterbuilder.pages.abst.WorkspaceLayout;
import org.characterbuilder.pages.trudvang.admin.TrudvangManageMods;
import org.characterbuilder.persist.entity.TrudvangExceptional;
import org.characterbuilder.persist.entity.TrudvangExceptionalLevel;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
public class TrudvangManageSuperExceptional extends WorkspaceLayout {
	
	private TrudvangManageExceptional manageExcep = new TrudvangManageExceptional();
	private TrudvangManageExceptionalLevel manageExcepLevel = null;
	private TrudvangManageMods manageMods = null;
	private Accordion accordion = new Accordion();
	private Accordion.Tab excepTab, levelTab, modTab;
	
	public TrudvangManageSuperExceptional(){
		manageExcep.addListener(new ExcepListener());
		
		addComponent(accordion);
		excepTab = accordion.addTab(manageExcep, "Exceptional attributes");
	}
	
	private class ExcepListener implements Property.ValueChangeListener{
		private static final String LEVEL_TAB_CAPSTART = "Levels for exceptional attribute ";
		
		@Override
		public void valueChange(ValueChangeEvent event) {
			if(levelTab != null){
				accordion.removeTab(levelTab);
				levelTab = null;
			}
			if(modTab != null){
				accordion.removeTab(modTab);
				modTab = null;
			}
			TrudvangExceptional exeptional = manageExcep.getSelectedExceptional();
			if(exeptional != null){
				manageExcepLevel = new TrudvangManageExceptionalLevel(exeptional);
				levelTab = accordion.addTab(manageExcepLevel, LEVEL_TAB_CAPSTART+exeptional.getName());
				manageExcepLevel.addListener(new LevelListener());
			}
		}
	}
	
	private class LevelListener implements Property.ValueChangeListener{
		private static final String MOD_TAB_CAPSTART = "Modifications for level ";
		
		@Override
		public void valueChange(ValueChangeEvent event) {
			if(modTab != null){
				accordion.removeTab(modTab);
				modTab = null;
			}
			TrudvangExceptionalLevel level = manageExcepLevel.getSelectedLevel();
			if(level != null){
				manageMods = new TrudvangManageMods(level);
				modTab = accordion.addTab(manageMods, MOD_TAB_CAPSTART + level.getName()+ '(' + level.getValue() +')');
			}
		}
	}
}
