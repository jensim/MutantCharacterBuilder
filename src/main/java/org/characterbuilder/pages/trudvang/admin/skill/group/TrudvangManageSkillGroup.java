package org.characterbuilder.pages.trudvang.admin.skill.group;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import javax.persistence.EntityManager;
import org.characterbuilder.defaults.MyTextArea;
import org.characterbuilder.pages.abst.trudvang.admin.TrudvangManage;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.TrudvangSkillGroup;

/**
 * TrudvangManageSkillGroup
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public class TrudvangManageSkillGroup extends TrudvangManage {

	private JPAContainer<TrudvangSkillGroup> container = null;
	private String[] COL_HEADERS = new String[]{"name"};
	private VerticalLayout editLayout = new VerticalLayout();
	private HorizontalLayout mainLayout = new HorizontalLayout();

	public TrudvangManageSkillGroup() {
		super();

		container = JPAContainerFactory.make(
				  TrudvangSkillGroup.class, ThePersister.ENTITY_MANAGER_NAME);
		table = new Table("Trudvang Skill Groups", container);
		fixTableSettings(container.getContainerPropertyIds(), COL_HEADERS);

		table.addListener(new TableListener());
		
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);

		addComponent(mainLayout);
		mainLayout.addComponent(table);
		mainLayout.addComponent(editLayout);
		
		editLayout.addComponent(new SkillGroupPanel());
	}

	private class TableListener implements ValueChangeListener {

		@Override
		public void valueChange(ValueChangeEvent event) {
			editLayout.removeAllComponents();
			TrudvangSkillGroup selectedSkill = getSelectedSkill();
			if (selectedSkill == null) {
				editLayout.addComponent(new SkillGroupPanel());
			} else {
				editLayout.addComponent(new SkillGroupPanel(selectedSkill));
			}
		}
	}
	
	protected TrudvangSkillGroup getSelectedSkill() {
		try {
			return container.getItem(table.getValue()).getEntity();
		} catch (Exception ex) {
			return null;
		}
	}

	private class SkillGroupPanel extends Panel {

		private Button add = new Button("Lägg till", new Adder());
		private Button edit = new Button("Spara", new Editer());
		private Button remove = new Button("Ta bort", new Remover());
		private TextField nameField = new TextField("Grupp");
		private MyTextArea description = new MyTextArea("Beskrivning");
		private TrudvangSkillGroup skillGroup = null;
		private VerticalLayout panelLayout = new VerticalLayout();

		public SkillGroupPanel() {
			buildUI();
			panelLayout.addComponent(add);
		}

		public SkillGroupPanel(TrudvangSkillGroup skillGroup) {
			this.skillGroup = skillGroup;
			buildUI();
			nameField.setValue(skillGroup.getName());
			description.setValue(skillGroup.getDescription());
			panelLayout.addComponent(edit);
			panelLayout.addComponent(remove);
		}

		private void buildUI() {
			panelLayout.addComponent(nameField);
			panelLayout.addComponent(description);
			panelLayout.setSpacing(true);
			panelLayout.setMargin(true);
			setContent(panelLayout);
		}

		private class Adder implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					EntityManager em = ThePersister.getEntityManager();
					em.getTransaction().begin();
					TrudvangSkillGroup newSkillGroup = new TrudvangSkillGroup();
					newSkillGroup.setName(nameField.getValue().toString());
					newSkillGroup.setDescription(description.getValue().toString());
					em.persist(newSkillGroup);
					em.getTransaction().commit();

					container.refresh();
					table.select(newSkillGroup);
				} catch (Exception ex) {
					notifyError("Skill manager", "Failed adding skill", ex, null);
				}
			}
		}

		private class Remover implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					EntityManager em = ThePersister.getEntityManager();
					em.getTransaction().begin();
					TrudvangSkillGroup skillGroup = em.find(TrudvangSkillGroup.class, getSelectedSkill().getId());
					em.remove(skillGroup);
					em.getTransaction().commit();

					container.refresh();
					table.select(null);
				} catch (Exception ex) {
					notifyError("Skill manager", "Failed removing skill", ex, null);
				}
			}
		}

		private class Editer implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					EntityManager em = ThePersister.getEntityManager();
					TrudvangSkillGroup oldSkillGroup = getSelectedSkill();
					//TrudvangSkillGroup oldSkillGroup = new TrudvangSkillGroup();
					oldSkillGroup.setName(nameField.getValue().toString());
					oldSkillGroup.setDescription(description.getValue().toString());
					em.getTransaction().begin();
					em.merge(oldSkillGroup);
					em.getTransaction().commit();

					container.refresh();
				} catch (Exception ex) {
					notifyError("Skill manager", "Failed editing skill", ex, null);
				}
			}
		}
	}
}
