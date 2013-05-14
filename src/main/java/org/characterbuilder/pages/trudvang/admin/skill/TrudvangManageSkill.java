package org.characterbuilder.pages.trudvang.admin.skill;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import javax.persistence.EntityManager;
import org.characterbuilder.CharbuildApp;
import org.characterbuilder.defaults.MyTextArea;
import org.characterbuilder.pages.abst.trudvang.admin.TrudvangManage;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.TrudvangElaboration;
import org.characterbuilder.persist.entity.TrudvangSkill;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
public class TrudvangManageSkill extends TrudvangManage {

	private JPAContainer<TrudvangSkill> container = null;
	private String[] COL_HEADERS = new String[]{"name"};
	private VerticalLayout editLayout = new VerticalLayout();
	private HorizontalLayout mainLayout = new HorizontalLayout();

	public TrudvangManageSkill() {
		super();

		container = JPAContainerFactory.make(
				  TrudvangSkill.class, ThePersister.ENTITY_MANAGER_NAME);
		table = new Table("Trudvang Skills", container);
		fixTableSettings(container.getContainerPropertyIds(), COL_HEADERS);

		table.addListener(new TableListener());
		editLayout.addComponent(new SkillPanel());

		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);

		addComponent(mainLayout);
		mainLayout.addComponent(table);
		mainLayout.addComponent(editLayout);
	}

	private class TableListener implements Property.ValueChangeListener {

		@Override
		public void valueChange(ValueChangeEvent event) {
			editLayout.removeAllComponents();
			TrudvangSkill selectedSkill = getSelectedSkill();
			if (selectedSkill == null) {
				editLayout.addComponent(new SkillPanel());
			} else {
				editLayout.addComponent(new SkillPanel(selectedSkill));
			}
		}
	}

	public TrudvangSkill getSelectedSkill() {
		try {
			return container.getItem(table.getValue()).getEntity();
		} catch (Exception ex) {
			return null;
		}
	}

	private class SkillPanel extends Panel {

		private Button add = new Button("Lägg till", new Adder());
		private Button edit = new Button("Spara", new Editer());
		private Button remove = new Button("Ta bort", new Remover());
		private TextField name = new TextField("Färdighet");
		private MyTextArea description = new MyTextArea("Beskrivning");
		private TrudvangSkill skill = null;
		private VerticalLayout panelLayout = new VerticalLayout();

		public SkillPanel() {
			buildUI();
			panelLayout.addComponent(add);
		}

		public SkillPanel(TrudvangSkill skill) {
			this.skill = skill;
			buildUI();
			name.setValue(skill.getName());
			description.setValue(skill.getDescription());
			panelLayout.addComponent(edit);
			panelLayout.addComponent(remove);
			List<TrudvangElaboration> elaborationList = skill.getTrudvangElaborationList();
			StringBuilder sb = new StringBuilder("<h2>Fördjupningar</h2><br/><ul>");
			for (TrudvangElaboration elaboration : elaborationList) {
				sb.append("<li>")
						  .append(elaboration.getName())
						  .append(".</li>");
			}
			sb.append("</ul>");
			panelLayout.addComponent(new Label(sb.toString(), Label.CONTENT_XHTML));
		}

		private void buildUI() {
			panelLayout.addComponent(name);
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
					TrudvangSkill newSkill = new TrudvangSkill();
					newSkill.setName(name.getValue().toString());
					newSkill.setDescription(description.getValue().toString());
					em.persist(newSkill);
					em.getTransaction().commit();

					container.refresh();
					table.select(null);
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
					TrudvangSkill skill = em.find(TrudvangSkill.class, getSelectedSkill().getId());
					em.remove(skill);
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
					em.getTransaction().begin();
					TrudvangSkill newSkill = getSelectedSkill();
					newSkill.setName(name.getValue().toString());
					newSkill.setDescription(description.getValue().toString());
					em.merge(newSkill);
					em.getTransaction().commit();

					container.refresh();
				} catch (Exception ex) {
					notifyError("Skill manager", "Failed editing skill", ex, null);
				}
			}
		}
	}
}