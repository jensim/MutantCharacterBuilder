package org.characterbuilder.pages.trudvang.admin.skill.group;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import javax.persistence.EntityManager;
import org.characterbuilder.pages.abst.trudvang.admin.TrudvangManage;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.TrudvangSkill;
import org.characterbuilder.persist.entity.TrudvangSkillGroup;
import org.characterbuilder.persist.entity.TrudvangSkillInGroup;

/**
 * TrudvangManageSkillGroupAssign
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public class TrudvangManageSkillGroupAssign extends TrudvangManage {

	private JPAContainer<TrudvangSkillInGroup> inGroupContainer = null;
	private TrudvangSkillGroup group = null;
	private HorizontalLayout bottomLayout = new HorizontalLayout();

	public TrudvangManageSkillGroupAssign(TrudvangSkillGroup group) {
		this.group = group;
		inGroupContainer = JPAContainerFactory.make(TrudvangSkillInGroup.class, ThePersister.ENTITY_MANAGER_NAME);
		inGroupContainer.addContainerFilter(new Compare.Equal("group", group));

		table = new Table("Set skills", inGroupContainer);
		fixTableSettings(inGroupContainer.getContainerPropertyIds(), new String[]{"skill"});
		table.addValueChangeListener(new TableListener());

		bottomLayout.addComponent(new GroupPanel());
		addComponent(table);
		addComponent(bottomLayout);
	}

	private class TableListener implements Property.ValueChangeListener {

		@Override
		public void valueChange(ValueChangeEvent event) {
			bottomLayout.removeAllComponents();
			TrudvangSkillInGroup inGroup = getSelectedValue();
			if (inGroup != null && inGroup instanceof TrudvangSkillInGroup) {
				bottomLayout.addComponent(new GroupPanel(inGroup));
			} else {
				bottomLayout.addComponent(new GroupPanel());
			}
		}
	}

	private TrudvangSkillInGroup getSelectedValue() {
		try {
			return inGroupContainer.getItem(table.getValue()).getEntity();
		} catch (Exception ex) {
			return null;
		}
	}

	private class GroupPanel extends Panel {

		private HorizontalLayout theLayout = new HorizontalLayout();
		private NativeSelect skillSelect = null;
		private JPAContainer<TrudvangSkill> skillContainer = null;
		private Button addButton = new Button("Add skill", new AddListener());
		private Button remButton = new Button("Remove", new RemListener());

		public GroupPanel() {
			super();

			skillContainer = JPAContainerFactory.make(TrudvangSkill.class, ThePersister.ENTITY_MANAGER_NAME);
			skillSelect = new NativeSelect("skill", skillContainer);
			skillSelect.setItemCaptionMode(Select.ITEM_CAPTION_MODE_ITEM);

			addComponent(skillSelect);
			addComponent(addButton);

			setContent(theLayout);
		}

		public GroupPanel(TrudvangSkillInGroup skillInGroup) {
			addComponent(remButton);

			setContent(theLayout);
		}

		private void addComponent(Component comp) {
			theLayout.addComponent(comp);
		}

		private class AddListener implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					TrudvangSkill skill = skillContainer.getItem(skillSelect.getValue()).getEntity();
					EntityManager em = ThePersister.getEntityManager();
					if (skill != null) {

						TrudvangSkillInGroup inGroup = new TrudvangSkillInGroup();
						inGroup.setGroup(group);
						inGroup.setSkill(skill);

						em.getTransaction().begin();
						em.persist(inGroup);
						em.getTransaction().commit();

						inGroupContainer.refresh();

					}
				} catch (Exception ex) {
					notifyError("Manage skill group - skill assign",
							  "<br/>Failed adding selected...", ex, null);
				}
			}
		}

		private class RemListener implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					TrudvangSkillInGroup inGroup = inGroupContainer.getItem(table.getValue()).getEntity();
					EntityManager em = ThePersister.getEntityManager();
					if (inGroup != null) {

						em.getTransaction().begin();
						inGroup = em.find(TrudvangSkillInGroup.class, inGroup.getId());
						//em.merge(inGroup);
						em.remove(inGroup);
						em.getTransaction().commit();

						inGroupContainer.refresh();
						table.select(null);

					}
				} catch (Exception ex) {
					notifyError("Manage Skill group - skill assign",
							  "<br/>Failed removing selected..", ex, null);
				}
			}
		}
	}
}