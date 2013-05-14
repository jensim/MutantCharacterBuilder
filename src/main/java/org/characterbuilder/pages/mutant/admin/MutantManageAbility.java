package org.characterbuilder.pages.mutant.admin;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import javax.persistence.EntityManager;
import org.characterbuilder.defaults.MyTextArea;
import org.characterbuilder.pages.abst.WorkspaceLayout;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.MutantAbility;
import org.characterbuilder.persist.entity.MutantClass;
import org.characterbuilder.persist.entity.MutantSkill;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@SuppressWarnings("serial")
public class MutantManageAbility extends WorkspaceLayout implements ValueChangeListener {

	private Panel thePanel = new Panel();
	private Table abilityTable = new Table();
	private String[] headerNames = new String[]{
		"Förmåga",
		"Klass",
		"Kostnad",
		"Räckvidd",
		"Varaktighet",};

	public MutantManageAbility() {

		removeAllComponents();

		abilityTable.setMultiSelect(false);
		abilityTable.setNullSelectionAllowed(true);
		abilityTable.setColumnReorderingAllowed(false);
		abilityTable.setColumnCollapsingAllowed(false);

		abilityTable.addListener(this);
		abilityTable.setImmediate(true);
		abilityTable.setSelectable(true);
		for (String name : headerNames) {
			abilityTable.addContainerProperty(name, String.class, null);
		}

		addComponent(abilityTable);
		//POPULATE ABILITIES
		EntityManager em = ThePersister.getEntityManager();
		try {
			List<MutantAbility> abilityList = em.createQuery(
					  "SELECT r FROM MutantAbility r",
					  MutantAbility.class).getResultList();

			for (MutantAbility ma : abilityList) {
				addAbilityToTabel(ma);
			}
		} catch (Exception e) {
			notifyError("Mutant Abilities", "Unable to load..", e, null);
		}

		addComponent(thePanel);
		thePanel.setContent(new AbilityLayout());
	}

	private void addAbilityToTabel(MutantAbility ability) {
		String[] abilityData = new String[]{
			ability.getName(),
			ability.getMutantClass().getName(),
			ability.getCost().toString(),
			ability.getReach(),
			ability.getDuration()
		};

		abilityTable.addItem(abilityData, ability);
	}

	private class AbilityLayout extends VerticalLayout {

		MutantAbility ability = null;
		TextField nameField = new TextField("Namn", "");
		TextField activationField = new TextField("Aktivering", "Personlig.");
		MyTextArea descriptionField = new MyTextArea("Beskrivning", "");
		TextField durationFiled = new TextField("Varaktighet", "Permanent.");
		MyTextArea effectField = new MyTextArea("Effekt", "");
		TextField reachField = new TextField("Räckvidd", "Beröring.");
		TextField costField = new TextField("Kostnad", "2");
		ComboBox classSelect = new ComboBox("Klass");
		ComboBox skillSelect = new ComboBox("Färdighet");
		Button addButton = new Button("Lägg till färdighet..");
		Button comitButton = new Button("Spara färdighet..");
		Button removeButton = new Button("Ta bort färdighet..");

		public AbilityLayout() {
			buildUI();
			addComponent(addButton);
			addButton.addListener(new AddListener());
		}

		public AbilityLayout(MutantAbility ability) {
			this.ability = ability;
			buildUI();

			addComponent(removeButton);
			removeButton.addListener(new Remover());
			addComponent(comitButton);
			comitButton.addListener(new ComitListener());
		}

		private void buildUI() {
			removeAllComponents();

			addComponent(nameField);
			addComponent(costField);
			addComponent(activationField);
			addComponent(reachField);
			addComponent(effectField);
			addComponent(durationFiled);
			addComponent(descriptionField);

			addComponent(classSelect);
			addComponent(skillSelect);

			classSelect.setMultiSelect(false);
			classSelect.setNullSelectionAllowed(false);

			skillSelect.setMultiSelect(false);
			skillSelect.setNullSelectionAllowed(true);

			EntityManager em = ThePersister.getEntityManager();
			try {
				List<MutantClass> classList = em.createQuery(
						  "SELECT r FROM MutantClass r",
						  MutantClass.class).getResultList();
				for (MutantClass classSingle : classList) {
					classSelect.addItem(classSingle);

					if (ability != null) {
						MutantClass abilClass = ability.getMutantClass();
						if (classSingle.getId() == abilClass.getId()) {
							classSelect.select(classSingle);
						}
					}
				}
			} catch (Exception e) {
				notifyError("Mutant Class", "Unable to load..", e, null);
			}

			try {
				List<MutantSkill> skillList = em.createQuery(
						  "SELECT r FROM MutantSkill r",
						  MutantSkill.class).getResultList();
				for (MutantSkill skillSingle : skillList) {
					skillSelect.addItem(skillSingle);

					if (ability != null) {
						MutantSkill abilSkill = ability.getMutantSkill();
						if (abilSkill != null) {
							if (skillSingle.getId() == abilSkill.getId()) {
								skillSelect.select(skillSingle);
							}
						}
					}
				}
			} catch (Exception e) {
				notifyError("Mutant Skill", "Unable to load..", e, null);
			}

			//POPULATE with data, if it is a persisted entity
			if (ability != null) {

				if (ability.getMutantSkill() != null) {
					skillSelect.select(ability.getMutantSkill());
				}

				nameField.setValue(ability.getName());
				activationField.setValue(ability.getActivation());
				descriptionField.setValue(ability.getDescription());
				durationFiled.setValue(ability.getDuration());
				effectField.setValue(ability.getEffect());
				reachField.setValue(ability.getReach());
				costField.setValue(ability.getCost().toString());
				classSelect.select(ability.getMutantClass());

				if (ability.getMutantSkill() != null) {
					skillSelect.select(ability.getMutantSkill());
				}
			}
		}

		private class ComitListener implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {

				EntityManager em = ThePersister.getEntityManager();
				try {
					em.getTransaction().begin();

					ability.setName(nameField.getValue().toString());
					ability.setActivation(activationField.getValue().toString());
					ability.setDescription(descriptionField.getValue().toString());
					ability.setDuration(durationFiled.getValue().toString());
					ability.setEffect(effectField.getValue().toString());
					ability.setReach(reachField.getValue().toString());
					ability.setCost(Integer.parseInt(costField.getValue().toString()));

					Object classobj = classSelect.getValue();
					if (classobj instanceof MutantClass) {
						MutantClass mClass = (MutantClass) classobj;
						ability.setMutantClass(mClass);

						Object skillobj = skillSelect.getValue();
						if (skillobj instanceof MutantSkill) {
							MutantSkill theSkill = (MutantSkill) skillobj;
							ability.setMutantSkill(theSkill);
						} else {
							ability.setMutantSkill(null);
						}

						em.merge(ability);
						em.getTransaction().commit();
						notifyInfo("Ability", "edited", null, null);
					} else {
						notifyError("Failed", "Class choise", null, null);
					}

				} catch (Exception e) {
					notifyError("Failed", "Persistence", e, null);

				}


			}
		}

		private class AddListener implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {

				EntityManager em = ThePersister.getEntityManager();
				try {
					em.getTransaction().begin();

					MutantAbility abil = new MutantAbility();
					abil.setName(nameField.getValue().toString());
					abil.setActivation(activationField.getValue().toString());
					abil.setDescription(descriptionField.getValue().toString());
					abil.setDuration(durationFiled.getValue().toString());
					abil.setEffect(effectField.getValue().toString());
					abil.setReach(reachField.getValue().toString());
					abil.setCost(Integer.parseInt(costField.getValue().toString()));

					Object classobj = classSelect.getValue();
					if (classobj instanceof MutantClass) {
						MutantClass mClass = (MutantClass) classobj;
						abil.setMutantClass(mClass);

						Object skillobj = skillSelect.getValue();
						if (skillobj instanceof MutantSkill) {
							MutantSkill theSkill = (MutantSkill) skillobj;
							abil.setMutantSkill(theSkill);
						}

						em.persist(abil);
						em.getTransaction().commit();

						addAbilityToTabel(abil);

						notifyInfo("Ability", "added", null, null);
					} else {
						notifyError("Failed", "Class choise", null, null);
					}

				} catch (Exception e) {
					notifyError("Failed", "Persistence", e, null);
				}

			}
		}

		private class Remover implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				EntityManager em = ThePersister.getEntityManager();
				try {
					em.getTransaction().begin();
					Object obj = abilityTable.getValue();
					MutantAbility abil = (MutantAbility) obj;
					abil = em.find(MutantAbility.class, abil.getId());
					em.remove(abil);
					em.getTransaction().commit();

					abilityTable.removeItem(obj);
				} catch (Exception e) {
					notifyError("Failed", "What did you do?!...", e, null);
				}

			}
		}
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		Object eventObject = event.getProperty().getValue();
		abilityTable.setWidth("100%");
		if (eventObject instanceof MutantAbility) {
			MutantAbility ability = (MutantAbility) eventObject;

			//TODO: Stuff
			thePanel.setContent(new AbilityLayout(ability));
		} else {
			thePanel.setContent(new AbilityLayout());
		}
	}
}
