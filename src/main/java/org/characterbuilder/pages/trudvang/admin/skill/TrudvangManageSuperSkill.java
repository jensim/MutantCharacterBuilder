package org.characterbuilder.pages.trudvang.admin.skill;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Accordion;
import org.characterbuilder.pages.abst.WorkspaceLayout;
import org.characterbuilder.pages.trudvang.admin.TrudvangManageMods;
import org.characterbuilder.persist.entity.TrudvangElaboration;
import org.characterbuilder.persist.entity.TrudvangSkill;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public class TrudvangManageSuperSkill extends WorkspaceLayout {

	private Accordion acoordian = new Accordion();
	private Accordion.Tab skillTab = null,
			  elaborationTab = null,
			  requiredTab = null,
			  powerTab = null,
			  vitnerTab = null,
			  modTab = null;
	private TrudvangManageSkill skillManager = null;
	private TrudvangManageElaboration elaborationManager = null;
	private TrudvangManageElabRequired elabReqManager = null;
	private TrudvangManagePower powerManager = null;
	private TrudvangManageVitner vitnerManager = null;
	private TrudvangManageMods modManager = null;

	public TrudvangManageSuperSkill() {
		skillManager = new TrudvangManageSkill();
		skillManager.addListener(new SkillListener());

		skillTab = acoordian.addTab(skillManager, "Skills");

		addComponent(acoordian);
	}

	/**
	 * if the selection changes in the skill table, a new elaboration view is
	 * set.
	 */
	private class SkillListener implements Property.ValueChangeListener {

		final String ELAB_TAB_CAPTION = "Elaborations for skill ";

		@Override
		public void valueChange(ValueChangeEvent event) {
			if (requiredTab != null) {
				acoordian.removeTab(requiredTab);
				requiredTab = null;
			}
			if (powerTab != null) {
				acoordian.removeTab(powerTab);
				powerTab = null;
			}
			if (vitnerTab != null) {
				acoordian.removeTab(vitnerTab);
				vitnerTab = null;
			}
			if (modTab != null) {
				acoordian.removeTab(modTab);
				modTab = null;
			}
			if (elaborationTab != null) {
				acoordian.removeTab(elaborationTab);
				elaborationTab = null;
			}
			if (elaborationManager != null) {
				for (Object listener : elaborationManager.getListeners(Property.ValueChangeListener.class)) {
					elaborationManager.removeListener((Property.ValueChangeListener) listener);
				}
				elaborationManager = null;
			}

			TrudvangSkill skill = skillManager.getSelectedSkill();
			if (skill != null) {
				elaborationManager = new TrudvangManageElaboration(skill);
				elaborationManager.addListener(new ElaborationListener());


				if (elaborationTab == null) {
					elaborationTab = acoordian.addTab(elaborationManager);
				}
				elaborationTab.setCaption(ELAB_TAB_CAPTION + skill.getName());
			}
		}
	}

	private class ElaborationListener implements Property.ValueChangeListener {

		final String REQUIRED_TAB_CAPTION = "Set required elaborations for ";
		final String POWER_TAB_CAPTION = "Powers for ";
		final String VITNER_TAB_CAPTION = "Vitner for ";
		final String MOD_TAB_CAPTION = "Mods for ";

		@Override
		public void valueChange(ValueChangeEvent event) {
			TrudvangElaboration elaboration = elaborationManager.getSelectedEaboration();
			String skillName = null;
			if (elaboration != null && elaboration.getSkillId() != null) {
				skillName = elaboration.getSkillId().getName();
			}
			//Drop tabs if null selection

			if (requiredTab != null) {
				acoordian.removeTab(requiredTab);
				requiredTab = null;
			}
			if (vitnerTab != null) {
				acoordian.removeTab(vitnerTab);
				vitnerTab = null;
			}
			if (powerTab != null) {
				acoordian.removeTab(powerTab);
				powerTab = null;
			}
			if (modTab != null) {
				acoordian.removeTab(modTab);
				modTab = null;
			}
			if (elaboration != null) {
				//REQUIREMENTS
				elabReqManager = new TrudvangManageElabRequired(elaboration);
				if (requiredTab == null) {
					requiredTab = acoordian.addTab(elabReqManager);
				}
				requiredTab.setVisible(true);
				requiredTab.setCaption(REQUIRED_TAB_CAPTION + elaboration.getName());
				//MODS
				modManager = new TrudvangManageMods(elaboration);
				if (modTab == null) {
					modTab = acoordian.addTab(modManager, MOD_TAB_CAPTION + elaboration.getName());
				}
				modTab.setVisible(true);

				//POWERS
				if (skillName != null && skillName.equals("Religion")) {
					powerManager = new TrudvangManagePower(elaboration);
					if (powerTab == null) {
						powerTab = acoordian.addTab(powerManager, POWER_TAB_CAPTION + elaboration.getName());
					}
					powerTab.setVisible(true);
				}
				//VITNER
				if (skillName != null && skillName.equals("Besvärjelsekonst")) {
					vitnerManager = new TrudvangManageVitner(elaboration);
					if (vitnerTab == null) {
						vitnerTab = acoordian.addTab(vitnerManager, VITNER_TAB_CAPTION + elaboration.getName());
					}
					vitnerTab.setVisible(true);
				}
			}
		}
	}
}
