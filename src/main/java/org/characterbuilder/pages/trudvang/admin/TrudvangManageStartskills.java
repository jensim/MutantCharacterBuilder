package org.characterbuilder.pages.trudvang.admin;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import javax.persistence.EntityManager;
import org.characterbuilder.defaults.MyHorizontalLayout;
import org.characterbuilder.defaults.MyVerticalLayout;
import org.characterbuilder.pages.abst.trudvang.admin.TrudvangManage;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.TrudvangArktype;
import org.characterbuilder.persist.entity.TrudvangPeople;
import org.characterbuilder.persist.entity.TrudvangSkill;
import org.characterbuilder.persist.entity.TrudvangSkillGroup;
import org.characterbuilder.persist.entity.TrudvangSkillStart;
import org.characterbuilder.persist.entity.TrudvangUpbringing;

/**
 * TrudvangManageStartskills
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public class TrudvangManageStartskills extends TrudvangManage {

	private JPAContainer<TrudvangSkillStart> container = null;
	private MyHorizontalLayout topLayout = new MyHorizontalLayout();
	private MyVerticalLayout editLayout = new MyVerticalLayout();
	private TrudvangUpbringing upbringing = null;
	private TrudvangArktype arktype = null;
	private TrudvangPeople people = null;

	public TrudvangManageStartskills(TrudvangUpbringing upbringing) {
		this.upbringing = upbringing;
		setUpTable();
		container.addContainerFilter(new Compare.Equal("upbringingId", upbringing));

		buildUI();
	}

	public TrudvangManageStartskills(TrudvangArktype arktype) {
		this.arktype = arktype;
		setUpTable();
		container.addContainerFilter(new Compare.Equal("arktypeId", this.arktype));
		buildUI();
	}

	public TrudvangManageStartskills(TrudvangPeople people) {
		this.people = people;
		setUpTable();
		container.addContainerFilter(new Compare.Equal("peopleId", this.people));

		buildUI();
	}

	private void setUpTable() {
		container = JPAContainerFactory.make(TrudvangSkillStart.class, ThePersister.ENTITY_MANAGER_NAME);
		table = new Table(null, container);
		fixTableSettings(container.getContainerPropertyIds(), new String[]{"skillId", "group"});
		table.addListener(new TableListener());
	}

	private void buildUI() {
		topLayout.addComponent(table);
		topLayout.addComponent(editLayout);
		editLayout.addComponent(new SkillPanel());
		addComponent(topLayout);
	}

	private class TableListener implements Property.ValueChangeListener {
		@Override
		public void valueChange(Property.ValueChangeEvent event) {
			editLayout.removeAllComponents();
			TrudvangSkillStart skill = getSelectedStartSkill();
			if (skill == null) {
				editLayout.addComponent(new SkillPanel());
			} else {
				editLayout.addComponent(new SkillPanel(skill));
			}
		}
	}

	public TrudvangSkillStart getSelectedStartSkill() {
		try {
			return container.getItem(table.getValue()).getEntity();
		} catch (Exception ex) {
			return null;
		}
	}

	private class SkillPanel extends Panel {

		Button addSkill = new Button("Lägg till", new Adder());
		Button addGroup = new Button("Lägg till", new Adder());
		Button remove = new Button("Ta bort", new Remover());
		JPAContainer<TrudvangSkill> skillContainer = null;
		JPAContainer<TrudvangSkillGroup> groupContainer = null;
		NativeSelect skillSelect = null;
		NativeSelect groupSelect = null;
		MyVerticalLayout fieldLayout = new MyVerticalLayout();
		MyVerticalLayout panelLayout = new MyVerticalLayout();

		public SkillPanel() {
			setupSelect();
			buildUI();

			fieldLayout.addComponent(new MyHorizontalLayout(skillSelect, addSkill));
			fieldLayout.addComponent(new MyHorizontalLayout(groupSelect, addGroup));
		}

		public SkillPanel(TrudvangSkillStart skill) { // EDIT / REMOVE
			buildUI();
			String skillSlashGroup =
					  skill.getGroup() != null ? "Färdighetsgrupp"
					  : skill.getSkillId() != null ? "Färdighet"
					  : null;
			if (skillSlashGroup == null) {
				fieldLayout.addComponent(remove);
			} else {
				fieldLayout.addComponent(new HorizontalLayout(
						  new Label("<b><u>" + skillSlashGroup + "</u></b>: " + skill.toString(),
						  ContentMode.HTML), remove));
			}
		}

		private void buildUI() {
			panelLayout.addComponent(fieldLayout);
			setContent(panelLayout);
			setSpacing(true);
			setMargin(true);
		}

		private void setupSelect() {
			//Skill
			skillContainer = JPAContainerFactory.make(
					  TrudvangSkill.class, ThePersister.ENTITY_MANAGER_NAME);
			skillContainer.sort(new Object[]{"name"}, new boolean[]{true});
			skillSelect = new NativeSelect("Skill", skillContainer);
			skillSelect.setMultiSelect(false);
			skillSelect.setNullSelectionAllowed(false);
			try {
				skillSelect.select(skillSelect.getItemIds().iterator().next());
			} catch (Exception ex) {
			}
			skillSelect.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
			skillSelect.setItemCaptionPropertyId("name");
			//SkillGroup
			groupContainer = JPAContainerFactory.make(
					  TrudvangSkillGroup.class, ThePersister.ENTITY_MANAGER_NAME);
			groupContainer.sort(new Object[]{"name"}, new boolean[]{true});
			groupSelect = new NativeSelect("Skill Group", groupContainer);
			groupSelect.setMultiSelect(false);
			groupSelect.setNullSelectionAllowed(false);
			try {
				groupSelect.select(groupSelect.getItemIds().iterator().next());
			} catch (Exception ex) {
			}
			groupSelect.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
			groupSelect.setItemCaptionPropertyId("name");
		}

		public TrudvangSkill getSelectedSkill() {
			Item itm = skillSelect.getItem(skillSelect.getValue());
			if (itm != null) {
				try {
					EntityManager em = ThePersister.getEntityManager();
					TrudvangSkill type = em.find(TrudvangSkill.class,
							  Integer.parseInt(itm.getItemProperty("id").toString()));
					return type;
				} catch (Exception ex) {
				}
			}
			return null;
		}

		public TrudvangSkillGroup getSelectedGroup() {
			Item itm = groupSelect.getItem(groupSelect.getValue());
			if (itm != null) {
				try {
					EntityManager em = ThePersister.getEntityManager();
					TrudvangSkillGroup type = em.find(TrudvangSkillGroup.class,
							  Integer.parseInt(itm.getItemProperty("id").toString()));
					return type;
				} catch (Exception ex) {
				}
			}
			return null;
		}

		private class Adder implements Button.ClickListener {

			@Override
			public void buttonClick(Button.ClickEvent event) {

				try {
					EntityManager em = ThePersister.getEntityManager();
					em.getTransaction().begin();
					TrudvangSkillStart newSkill = new TrudvangSkillStart();
					if (upbringing != null) {
						upbringing = em.find(TrudvangUpbringing.class, upbringing.getId());
						newSkill.setUpbringingId(upbringing);
					} else if (people != null) {
						people = em.find(TrudvangPeople.class, people.getId());
						newSkill.setPeopleId(people);
					} else if (arktype != null) {
						arktype = em.find(TrudvangArktype.class, arktype.getId());
						newSkill.setArktypeId(arktype);
					}

					if (event.getButton() == addSkill) {
						newSkill.setSkillId(getSelectedSkill());
					} else if (event.getButton() == addGroup) {
						newSkill.setGroup(getSelectedGroup());
					} else {
						throw new Exception("Unsigned button pressed.");
					}
					em.persist(newSkill);
					em.getTransaction().commit();

					container.refresh();
					table.select(null);
					table.setSizeUndefined();
				} catch (Exception ex) {
					notifyError("Skill manager", "Failed adding mod", ex, null);
				}
			}
		}

		private class Remover implements Button.ClickListener {

			@Override
			public void buttonClick(Button.ClickEvent event) {
				try {
					EntityManager em = ThePersister.getEntityManager();
					em.getTransaction().begin();
					TrudvangSkillStart startSkill = em.find(TrudvangSkillStart.class, getSelectedStartSkill().getId());
					em.remove(startSkill);
					em.getTransaction().commit();

					container.refresh();
					table.select(null);
					table.setSizeUndefined();
				} catch (Exception ex) {
					notifyError("Skill manager", "Failed removing mod", ex, null);
				}
			}
		}
	}
}
