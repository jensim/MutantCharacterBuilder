package org.characterbuilder.pages.trudvang.createcharacter;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.characterbuilder.defaults.MyVerticalLayout;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.TrudvangSkill;
import org.characterbuilder.persist.entity.TrudvangSkillGroup;
import org.characterbuilder.persist.entity.TrudvangSkillInGroup;
import org.characterbuilder.persist.entity.TrudvangSkillStart;

/**
 * TrudvangInnerPageAssignStartSkills
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public final class TrudvangInnerPageAssignStartSkills extends MyVerticalLayout {

	private final List<SkillPanelGeneric> bigSkillList = new ArrayList();
	private final Button addButton = new Button("Lägg till rad", new AddListener());
	private final VerticalLayout startSkillLayout = new VerticalLayout();
	private SkillPanelGeneric inSwitch = null;

	public TrudvangInnerPageAssignStartSkills(
			  List<TrudvangSkillStart> startSkillList) {

		//System.out.println("Assign Skill elements: "+startSkillList.size());
		for (TrudvangSkillStart skill : startSkillList) {
			if (skill.getGroup() != null) {
				GroupPanel groupComp = new GroupPanel(skill.getGroup());
				bigSkillList.add(groupComp);
				startSkillLayout.addComponent(groupComp);
			} else if (skill.getSkillId() != null) {
				SkillPanel skillComp = new SkillPanel(skill.getSkillId());
				bigSkillList.add(skillComp);
				startSkillLayout.addComponent(skillComp);
			}
		}
		addComponent(new Label("<h2>Tilldela färdigheter</h2>", ContentMode.HTML));
		addComponent(startSkillLayout);
		addComponent(addButton);
	}

	/**
	 * If not done choosing skills, then this throws exception of some/any kind
	 *
	 * @return
	 * @throws NullPointerException
	 * @throws Exception
	 */
	public Map<TrudvangSkill, Integer> getSelectedSkillsAndValues()
			  throws NullPointerException, Exception {

		HashMap<TrudvangSkill, Integer> skillMap = new HashMap();
		for (SkillPanelGeneric panel : bigSkillList) {
			TrudvangSkill skill = panel.getSkill();
			if (skill == null) {
				throw new NullPointerException("Not all skills had set value");
			} else if (skillMap.containsKey(skill)) {
				//Add to existing value
				skillMap.put(skill, skillMap.get(skill) + panel.getSkillValue());
			} else {
				skillMap.put(skill, panel.getSkillValue());
			}
		}
		return skillMap;
	}

	private class AddListener implements Button.ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			SkillPanel panel = new SkillPanel();
			bigSkillList.add(panel);
			startSkillLayout.addComponent(panel);
		}
	}

	private abstract class SkillPanelGeneric extends Panel {

		private final SkillPanelGeneric self = this;
		public static final String SWITCH_BUTTON_NORMAL = "Byt värde",
				  SWITCH_BUTTON_INSWITCH = "Återställ";
		//protected final HorizontalLayout veriMainLayout = new HorizontalLayout();
		private final GridLayout veriMainLayout = new GridLayout(4, 1);
		protected final HorizontalLayout identLayout = new HorizontalLayout();
		protected final Label valueVield = new Label("", ContentMode.HTML);
		protected final Button delButton = new Button("Delete", new DelListener());
		protected final Button switchButton = new Button(SWITCH_BUTTON_NORMAL, new SwitchListener());

		public SkillPanelGeneric() {
			setRandom(1, 6);
			veriMainLayout.setMargin(true);
			veriMainLayout.setSpacing(true);
			veriMainLayout.addComponent(identLayout);
			veriMainLayout.addComponent(valueVield);
			veriMainLayout.setComponentAlignment(valueVield, Alignment.MIDDLE_CENTER);
			veriMainLayout.addComponent(switchButton);
			veriMainLayout.setComponentAlignment(switchButton, Alignment.MIDDLE_CENTER);
			veriMainLayout.addComponent(delButton);
			veriMainLayout.setComponentAlignment(delButton, Alignment.MIDDLE_CENTER);
			setContent(veriMainLayout);
		}

		public abstract TrudvangSkill getSkill();

		public final void setRandom(int min, int max) {
			if (Math.abs(min) > 99 || Math.abs(max) > 99) {
				return;
			}
			int val = (int) (min + (Math.random() * (max - min + 1)));
			valueVield.setValue("" + val);
		}

		public final Integer getSkillValue() {
			try {
				return Integer.parseInt(valueVield.getValue());
			} catch (Exception ex) {
				return 0;
			}
		}

		public final void setSkillValue(Integer skillValue) {
			valueVield.setValue(skillValue.toString());
		}

		private void resetSwitchButton() {
			switchButton.setCaption(SWITCH_BUTTON_NORMAL);
		}

		private void delSelf() {
			startSkillLayout.removeComponent(self);
			bigSkillList.remove(self);
		}

		private class DelListener implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				delSelf();
			}
		}

		private class SwitchListener implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				switch (switchButton.getCaption()) {
					case SWITCH_BUTTON_NORMAL:
						if (inSwitch != null) {
							Integer other = inSwitch.getSkillValue();
							inSwitch.setSkillValue(getSkillValue());
							inSwitch.resetSwitchButton();
							setSkillValue(other);
							inSwitch = null;

						} else {
							switchButton.setCaption(SWITCH_BUTTON_INSWITCH);
							inSwitch = self;
						}
						break;
					case SWITCH_BUTTON_INSWITCH:

						switchButton.setCaption(SWITCH_BUTTON_NORMAL);
						break;
				}
			}
		}
	}

	private final class SkillPanel extends SkillPanelGeneric {

		protected JPAContainer<TrudvangSkill> container = null;
		private NativeSelect select = null;
		private TrudvangSkill skill = null;

		public SkillPanel() {
			super();
			this.container = JPAContainerFactory.make(TrudvangSkill.class,
					  ThePersister.ENTITY_MANAGER_NAME);
			this.select = new NativeSelect(null, container);
			this.select.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
			this.select.setItemCaptionPropertyId("name");
			this.select.select(this.container.getItemIds().iterator().next());
			this.select.setNullSelectionAllowed(false);

			identLayout.addComponent(new Label("<h3>Färdighet: </h3>", ContentMode.HTML));
			identLayout.addComponent(this.select);
		}

		public SkillPanel(TrudvangSkill skill) {
			super();
			this.skill = skill;
			identLayout.addComponent(new Label("<h3>Färdighet: " + skill.getName()
					  + "</h3>", ContentMode.HTML));
		}

		@Override
		public TrudvangSkill getSkill() {
			if (skill != null) {
				return skill;
			} else if (select != null) {
				try {
					Object selected = select.getValue();
					return container.getItem(selected).getEntity();
				} catch (Exception ex) {
				}
			}
			return null;
		}
	}

	private final class GroupPanel extends SkillPanelGeneric {

		protected JPAContainer<TrudvangSkillInGroup> container = null;
		private NativeSelect select = null;

		public GroupPanel(TrudvangSkillGroup group) {
			super();
			this.container = JPAContainerFactory.make(TrudvangSkillInGroup.class,
					  ThePersister.ENTITY_MANAGER_NAME);
			this.container.addContainerFilter(new Compare.Equal("group", group));
			this.select = new NativeSelect(group.getName(), container);

			this.select.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
			this.select.setItemCaptionPropertyId("skill");
			this.select.select(this.container.getItemIds().iterator().next());
			this.select.setNullSelectionAllowed(false);

			identLayout.addComponent(new Label("<h3>Färdighet: </h3>", ContentMode.HTML));
			identLayout.addComponent(this.select);
		}

		@Override
		public TrudvangSkill getSkill() {
			try {
				Object selected = select.getValue();
				return container.getItem(selected).getEntity().getSkill();
			} catch (Exception ex) {
				return null;
			}
		}
	}
}
