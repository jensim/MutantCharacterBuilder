package org.characterbuilder.pages.trudvang.admin;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import javax.persistence.EntityManager;
import org.characterbuilder.defaults.MyHorizontalLayout;
import org.characterbuilder.defaults.MyTextField;
import org.characterbuilder.defaults.MyVerticalLayout;
import org.characterbuilder.pages.abst.trudvang.admin.TrudvangManage;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.TrudvangElaboration;
import org.characterbuilder.persist.entity.TrudvangExceptionalLevel;
import org.characterbuilder.persist.entity.TrudvangMod;
import org.characterbuilder.persist.entity.TrudvangModType;
import org.characterbuilder.persist.entity.TrudvangSkill;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
public class TrudvangManageMods extends TrudvangManage {

	private JPAContainer<TrudvangMod> container = null;
	private MyHorizontalLayout topLayout = new MyHorizontalLayout();
	private MyVerticalLayout editLayout = new MyVerticalLayout();
	private TrudvangElaboration elaboration = null;
	private TrudvangExceptionalLevel exceptional = null;

	public TrudvangManageMods(TrudvangElaboration elaboration) {
		this.elaboration = elaboration;
		setUpTable();
		container.addContainerFilter(new Compare.Equal("elaborationId", elaboration));

		buildUI();
	}

	public TrudvangManageMods(TrudvangExceptionalLevel exceptional) {
		this.exceptional = exceptional;
		setUpTable();
		container.addContainerFilter(new Compare.Equal("exceptionalId", exceptional));

		buildUI();
	}

	private void setUpTable() {
		container = JPAContainerFactory.make(TrudvangMod.class, 
				  ThePersister.getEntityManager());
		table = new Table(null, container);
		fixTableSettings(container.getContainerPropertyIds(), 
				  new String[]{"typeId", "value", "skill"});
		table.addListener(new TableListener());
	}

	private void buildUI() {
		topLayout.addComponent(table);
		topLayout.addComponent(editLayout);
		editLayout.addComponent(new ModPanel());
		addComponent(topLayout);
	}

	private class TableListener implements Property.ValueChangeListener {

		@Override
		public void valueChange(ValueChangeEvent event) {
			editLayout.removeAllComponents();
			TrudvangMod selectedMod = getSelectedMod();
			if (selectedMod == null) {
				editLayout.addComponent(new ModPanel());
			} else {
				editLayout.addComponent(new ModPanel(selectedMod));
			}
		}
	}

	public TrudvangMod getSelectedMod() {
		try {
			return container.getItem(table.getValue()).getEntity();
		} catch (Exception ex) {
			return null;
		}
	}

	private class ModPanel extends Panel {

		Button add = new Button("Lägg till", new Adder());
		Button edit = new Button("Spara", new Editer());
		Button remove = new Button("Ta bort", new Remover());
		JPAContainer<TrudvangModType> typeContainer = null;
		JPAContainer<TrudvangSkill> skillContainer = null;
		NativeSelect typeSelect = null,
				  skillSelect = null;
		MyTextField valueField = new MyTextField("Värde", "5", 2);
		MyHorizontalLayout buttonLayout = null;
		MyVerticalLayout panelLayout = new MyVerticalLayout();
		TrudvangMod mod = null;

		public ModPanel() {
			setupSelect();
			buttonLayout = new MyHorizontalLayout(add);
			buildUI();


		}

		public ModPanel(TrudvangMod mod) { // EDIT / REMOVE
			this.mod = mod;
			setupSelect();
			buttonLayout = new MyHorizontalLayout(edit, remove);
			if (mod.getSkill() != null) {
				panelLayout.addComponent(skillSelect);
			}
			buildUI();
		}

		private void buildUI() {
			panelLayout.addComponent(typeSelect);
			panelLayout.addComponent(skillSelect);
			panelLayout.addComponent(valueField);
			panelLayout.addComponent(buttonLayout);
			setContent(panelLayout);
			setSpacing(true);
			setMargin(true);
			if (exceptional != null) {
				valueField.setValue(exceptional.getValue() + "");
			}
		}

		private void setupSelect() {
			skillContainer = JPAContainerFactory.make(TrudvangSkill.class,
					  ThePersister.ENTITY_MANAGER_NAME);
			skillSelect = new NativeSelect("Skill cost mod", skillContainer);
			skillSelect.setMultiSelect(false);
			skillSelect.setNullSelectionAllowed(true);
			skillSelect.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
			skillSelect.setItemCaptionPropertyId("name");

			skillSelect.select(null);
			skillSelect.setVisible(false);

			typeContainer = JPAContainerFactory.make(
					  TrudvangModType.class, ThePersister.getEntityManager());
			typeContainer.sort(new Object[]{"name"}, new boolean[]{true});
			typeSelect = new NativeSelect("Mod type", typeContainer);
			typeSelect.setMultiSelect(false);
			typeSelect.setNullSelectionAllowed(false);
			typeSelect.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
			typeSelect.setItemCaptionPropertyId("name");
			typeSelect.setImmediate(true);
			typeSelect.addValueChangeListener(new Property.ValueChangeListener() {
				@Override
				public void valueChange(ValueChangeEvent event) {
					try {
						TrudvangModType type = typeContainer.getItem(typeSelect.getValue()).getEntity();
						if (type.getName().equalsIgnoreCase("färdighet")) {
							skillSelect.select(null);
							skillSelect.setVisible(true);
						} else {
							skillSelect.select(null);
							skillSelect.setVisible(false);
						}
					} catch (Exception ex) {
						skillSelect.select(null);
						skillSelect.setVisible(false);
					}
				}
			});
			if (mod != null) {
				typeSelect.select(mod.getTypeId().getId());
			} else {
				typeSelect.select(typeContainer.getItemIds().iterator().next());
			}
		}

		public TrudvangModType getSelectedType() {
			try {
				return typeContainer.getItem(typeSelect.getValue()).getEntity();
			} catch (Exception ex) {
				return null;
			}
		}

		public TrudvangSkill getSelectedSkill() {
			try {
				return skillContainer.getItem(skillSelect.getValue()).getEntity();
			} catch (Exception ex) {
				return null;
			}
		}

		private class Adder implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					EntityManager em = ThePersister.getEntityManager();
					em.getTransaction().begin();
					TrudvangMod newMod = new TrudvangMod();
					if (elaboration != null) {
						elaboration = em.find(TrudvangElaboration.class, elaboration.getId());
						newMod.setElaborationId(elaboration);
					} else if (exceptional != null) {
						exceptional = em.find(TrudvangExceptionalLevel.class, exceptional.getId());
						newMod.setExceptionalId(exceptional);
					}
					newMod.setTypeId(getSelectedType());
					newMod.setSkill(getSelectedSkill());
					Integer value = Integer.parseInt(valueField.getValue().toString());
					newMod.setValue(value);
					em.persist(newMod);
					em.getTransaction().commit();
					container.refresh();
				} catch (Exception ex) {
					notifyError("Skill manager", "Failed adding mod", ex, null);
				}
			}
		}

		private class Remover implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					EntityManager em = ThePersister.getEntityManager();
					em.getTransaction().begin();
					TrudvangMod mod = em.find(TrudvangMod.class, getSelectedMod().getId());
					em.remove(mod);
					em.getTransaction().commit();
					container.refresh();
					table.select(null);
				} catch (Exception ex) {
					notifyError("Skill manager", "Failed removing mod", ex, null);
				}
			}
		}

		private class Editer implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					EntityManager em = ThePersister.getEntityManager();
					em.getTransaction().begin();
					TrudvangMod mod = getSelectedMod();
					mod.setTypeId(getSelectedType());
					mod.setSkill(getSelectedSkill());
					Integer value = Integer.parseInt(valueField.getValue().toString());
					mod.setValue(value);
					em.merge(mod);
					em.getTransaction().commit();

					container.refresh();
				} catch (Exception ex) {
					notifyError("Skill manager", "Failed editing mod", ex, null);
				}
			}
		}
	}
}
