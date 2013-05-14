package org.characterbuilder.pages.trudvang.admin.skill.group;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Accordion;
import org.characterbuilder.pages.abst.WorkspaceLayout;
import org.characterbuilder.persist.entity.TrudvangSkillGroup;

/**
 * TrudvangManageSuperSkillInGroup
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public class TrudvangManageSuperSkillInGroup extends WorkspaceLayout{
	private Accordion acoordian = new Accordion();
	private Accordion.Tab groupTab = null,
			  inGroupTab = null;
	private TrudvangManageSkillGroup groupManage = null;
	private TrudvangManageSkillGroupAssign inGroupManage = null;
	
	public TrudvangManageSuperSkillInGroup(){
		groupManage = new TrudvangManageSkillGroup();
		groupManage.addValueChangeListener(new GroupListener());
		
		groupTab = acoordian.addTab(groupManage, "Manage skill groups");
		addComponent(acoordian);
	}
	
	private class GroupListener implements Property.ValueChangeListener{
		private static final String IN_GROUP_CAP = "Assign skills to group ";
		@Override
		public void valueChange(ValueChangeEvent event) {
			if(inGroupTab != null){
				acoordian.removeTab(inGroupTab);
				inGroupTab = null;
			}
			TrudvangSkillGroup group = groupManage.getSelectedSkill();
			if(group != null){
				inGroupManage = new TrudvangManageSkillGroupAssign(group);
				inGroupTab = acoordian.addTab(inGroupManage, IN_GROUP_CAP + group.getName());
				
			}
		}
	}
}
