package org.characterbuilder.pages.mutant;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.characterbuilder.defaults.MyTextArea;
import org.characterbuilder.pages.abst.mutant.MutantCharacterWorkspaceLayout;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.MutantBaseStatCharacter;
import org.characterbuilder.persist.entity.MutantCharacter;
import org.characterbuilder.persist.entity.MutantSkill;
import org.characterbuilder.persist.entity.MutantSkillCharacter;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@SuppressWarnings("serial")
public class MutantSkills extends MutantCharacterWorkspaceLayout {

	private static final long serialVersionUID = 7435186495912103812L;
	private VerticalLayout infoLayout = new VerticalLayout();
	private VerticalLayout costLayout = new VerticalLayout();
	private SkillPanel panel = new SkillPanel();
	private SkillTabel ownTable = new SkillTabel();
	private SkillTabel otherTable = new SkillTabel();

	public MutantSkills(MutantCharacter mc) {
		super(mc);

		setInfo("Färdigheter, " + mutantCharacter.getName());
		infoPanel.setContent(costLayout);

		ownTable.addListener(this);
		otherTable.addListener(this);

		addComponent(ownTable);
		infoLayout.addComponent(panel);
		addComponent(infoLayout);
		addComponent(otherTable);

		//POPULATE ITEM
		EntityManager em = ThePersister.getEntityManager();
		try {
			//OWN SKILLS
			List<MutantSkillCharacter> ownItemList = em.createQuery(
					  "SELECT skill "
					  + "FROM MutantSkillCharacter skill "
					  + "WHERE skill.mutantCharacter.id = :charID",
					  MutantSkillCharacter.class)
					  .setParameter("charID", mutantCharacter.getId())
					  .getResultList();
			//Set<MutantSkillCharacter> ownItemSet = mutantCharacter.getMutantSkillCharacters();
			for (MutantSkillCharacter mwc : ownItemList) {
				ownTable.addLine(mwc);
			}

			//OTHER SKILLS
			List<MutantSkill> skillList = em.createQuery(
					  "SELECT r "
					  + "FROM MutantSkill r "
					  + "ORDER BY r.name",
					  MutantSkill.class).getResultList();

			for (MutantSkill skill : skillList) {
				here:
				if (skill.getMutantAbilities().isEmpty()) {
					for (MutantSkillCharacter mwc : ownItemList) {
						if (skill.getName().equalsIgnoreCase(mwc.getName())) {
							break here;
						}
					}
					otherTable.addLine(skill);
				}
			}

		} catch (Exception e) {
			notifyError("Mutant skills", "Exception occured populating page.", e, null);
		}

		updateCosts();
	}

	private class SkillTabel extends Table {

		private String[] headerNames = new String[]{
			"Färdighet",
			"Grundegenskap",
			"Naturlig"
		};

		public SkillTabel() {
			setMultiSelect(false);
			setNullSelectionAllowed(true);
			setColumnReorderingAllowed(false);
			setColumnCollapsingAllowed(false);
			setImmediate(true);
			setSelectable(true);
			for (String name : headerNames) {
				addContainerProperty(name, String.class, null);
			}
		}

		public void addLine(MutantSkill skill) {
			String[] skillData = new String[]{
				skill.getName(),
				skill.getMutantBaseStat().getName(),
				skill.getNaturalSkill().toString()
			};

			addItem(skillData, skill);
		}

		public void addLine(MutantSkillCharacter skill) {
			String[] skillData = new String[]{
				skill.getName(),
				skill.getMutantBaseStatCharacter().getMutantBaseStat().getName(),
				skill.getNaturalSkill().toString()
			};

			addItem(skillData, skill);
		}
	}

	private class SkillPanel extends VerticalLayout {

		private TextField name = new TextField("Färdighet");
		private CheckBox natural = new CheckBox("Naturlig färdighet");
		private Select baseStat = new Select("Grundegenskap");
		private MyTextArea description = new MyTextArea("Beskrivning");
		private TextField baseVal = new TextField("*GE köpt", "0");
		private TextField bonusVal = new TextField("*GE från annan källa", "0");
		private TextField trainedVal = new TextField("Tränade nivåer", "0");
		private CheckBox trained = new CheckBox("Färdighet från karaktärsskapelse");
		private Label total = new Label("0", Label.CONTENT_XHTML);
		private Button addButton = new Button("Add");
		private Button remButton = new Button("Remove");
		private Button edtButton = new Button("Commit changes");
		private MutantSkillCharacter itmChar = null;
		private MutantSkill itm = null;

		public SkillPanel() {
			super();
			itmChar = new MutantSkillCharacter();

			buildUI();
			trained.setValue(true);

			addComponent(addButton);
			addButton.addListener(new Adder());
		}

		public SkillPanel(MutantSkill skill) {
			super();
			itm = skill;

			addComponent(new Label("<b><u>Färdighet:</u></b> " + skill.getName().replace("\n", "<br/>"), Label.CONTENT_XHTML));
			addComponent(new Label("<b><u>Naturlig:</u></b> " + (skill.getNaturalSkill() ? "ja" : "nej"), Label.CONTENT_XHTML));
			addComponent(new Label("<b><u>Beskrivning:</u></b> " + skill.getDescription().replace("\n", "<br/>"), Label.CONTENT_XHTML));

			addComponent(addButton);
			addButton.addListener(new Adder());
		}

		public SkillPanel(MutantSkillCharacter skill) {
			super();
			itmChar = skill;

			buildUI();

			addComponent(baseVal);
			addComponent(bonusVal);
			addComponent(trainedVal);
			addComponent(trained);
			addComponent(total);

			bonusVal.setDescription("*GE nivåer erhållna från t.ex. förmågor,<br/>"
					  + "eller ifall av IMM som har 1 gratis nivå<br/>"
					  + "utöver vanlig gräns i två st färdigheter.<br/>");
			trained.setDescription("En färdighet från karaktärens skapelse får<br/>"
					  + "automatiskt en \"gratis\" nivå om den är naturlig<br/>"
					  + "eller kommer från en förmåga.<br/>"
					  + "Om en färdighet tränas i efterhand kostar denna<br/>"
					  + "normalt ERF och krediter.<br/>"
					  + "ERF-kostnaden beräknas för, krediterna är upp till<br/>"
					  + "dig och din spelledare, då detta är en förhandling<br/>"
					  + "med din läromästare.");

			name.setValue(skill.getName());
			description.setValue(skill.getDescription());

			natural.setValue(skill.getNaturalSkill());
			natural.setImmediate(true);
			natural.addListener(new SkillWatch());

			baseVal.setValue(skill.getBaseValue().toString());
			baseVal.setImmediate(true);
			baseVal.addListener(new SkillWatch());

			bonusVal.setValue(skill.getBaseBonus().toString());
			bonusVal.setImmediate(true);
			bonusVal.addListener(new SkillWatch());

			trainedVal.setValue(skill.getTrainedValue().toString());
			trainedVal.setImmediate(true);
			trainedVal.addListener(new SkillWatch());

			trained.setValue(skill.getCreationTrained());
			trained.setImmediate(true);
			trained.addListener(new SkillWatch());

			total.setCaption("Totalt värde:");
			updateTotal();

			addComponent(edtButton);
			edtButton.addListener(new Editer());
			addComponent(remButton);
			remButton.addListener(new Remover());
		}

		private void enableFields(boolean b) {
			name.setEnabled(b);
			natural.setEnabled(b);
			baseStat.setEnabled(b);
			description.setEnabled(b);

			baseVal.setEnabled(b);
			bonusVal.setEnabled(b);
			trainedVal.setEnabled(b);
			trained.setEnabled(b);

		}

		private void buildUI() {
			setMargin(true);
			setSpacing(true);

			baseStat.setNullSelectionAllowed(false);
			baseStat.setMultiSelect(false);

			addComponent(name);
			addComponent(natural);
			addComponent(baseStat);
			addComponent(description);

			Set<MutantBaseStatCharacter> statSet = mutantCharacter.getMutantBaseStatCharacters();
			for (MutantBaseStatCharacter stat : statSet) {
				baseStat.addItem(stat);
				if (itmChar != null) {
					if (itmChar.getMutantBaseStatCharacter() != null) {
						if (itmChar.getMutantBaseStatCharacter().getId() == stat.getId()) {
							baseStat.select(stat);
						}
					}
				} else if (itm != null) {
					if (itm.getMutantBaseStat() != null) {
						if (itm.getMutantBaseStat().getId() == stat.getId()) {
							baseStat.select(stat);
						}
					}
				}
			}
		}

		private void updateTotal() {
			try {
				itmChar.setBaseValue(Integer.parseInt(baseVal.getValue().toString()));
				itmChar.setBaseBonus(Integer.parseInt(bonusVal.getValue().toString()));
				itmChar.setTrainedValue(Integer.parseInt(trainedVal.getValue().toString()));
				itmChar.setCreationTrained(trained.booleanValue());
				itmChar.setNaturalSkill(natural.booleanValue());
				Object obj = baseStat.getValue();
				if (obj instanceof MutantBaseStatCharacter) {
					itmChar.setMutantBaseStatCharacter((MutantBaseStatCharacter) obj);
				}

				total.setValue(itmChar.getTotal().toString());
			} catch (Exception e) {
				notifyError("Mutant skills", "failed updating total value", e, null);
			}
		}

		private class SkillWatch implements ValueChangeListener {

			@Override
			public void valueChange(ValueChangeEvent event) {
				updateTotal();
			}
		}

		/*
		 * ADDER
		 */
		private class Adder implements Button.ClickListener {

			public void buttonClick(ClickEvent event) {
				if (itmChar == null) {
					Object obj = otherTable.getValue();
					if (obj instanceof MutantSkill) {
						MutantSkill skill = (MutantSkill) obj;
						EntityManager em = ThePersister.getEntityManager();
						try {
							em.getTransaction().begin();
							MutantSkillCharacter newItem = new MutantSkillCharacter(mutantCharacter, skill);
							em.persist(newItem);
							em.getTransaction().commit();

							ownTable.addLine(newItem);
							ownTable.select(newItem);

							updateCosts();
						} catch (Exception e) {
							notifyError("Mutant skill", "error adding new skill to character", e, null);
						}
					} else {
						notifyError("Mytant skill", "error try reloading page..", null, null);
					}
				} else {
					EntityManager em = ThePersister.getEntityManager();
					try {
						em.getTransaction().begin();

						itmChar.setName(name.getValue().toString());
						itmChar.setNaturalSkill(natural.booleanValue());
						MutantBaseStatCharacter stat = (MutantBaseStatCharacter) baseStat.getValue();
						itmChar.setMutantBaseStatCharacter(stat);
						itmChar.setDescription(description.getValue().toString());
						itmChar.setBaseValue(Integer.parseInt(baseVal.getValue().toString()));
						itmChar.setBaseBonus(Integer.parseInt(bonusVal.getValue().toString()));
						itmChar.setTrainedValue(Integer.parseInt(trainedVal.getValue().toString()));
						itmChar.setCreationTrained(trained.booleanValue());
						itmChar.setMutantCharacter(mutantCharacter);

						em.persist(itmChar);
						em.getTransaction().commit();

						ownTable.addLine(itmChar);
						ownTable.select(itmChar);

						updateCosts();
					} catch (Exception e) {
						notifyError("mutant skill", "error adding new skill to character", e, null);
					}
				}
			}
		}

		/*
		 * REMOVER
		 */
		private class Remover implements Button.ClickListener {

			public void buttonClick(ClickEvent event) {
				Object obj = ownTable.getValue();
				if (obj instanceof MutantSkillCharacter) {
					ownTable.removeItem(obj);
					EntityManager em = ThePersister.getEntityManager();
					try {
						em.getTransaction().begin();
						MutantSkillCharacter skill = em.find(MutantSkillCharacter.class, itmChar.getId());
						em.remove(skill);
						em.getTransaction().commit();

						updateCosts();
					} catch (Exception e) {
						notifyError("mutant skill", "error removing skill from character", e, null);
					}
				}
			}
		}

		/*
		 * FIXME: EDITER
		 */
		private class Editer implements Button.ClickListener {

			public void buttonClick(ClickEvent event) {
				EntityManager em = ThePersister.getEntityManager();
				try {
					em.getTransaction().begin();
					MutantSkillCharacter skill = em.find(MutantSkillCharacter.class, itmChar.getId());

					skill.setName(name.getValue().toString());
					skill.setNaturalSkill(natural.booleanValue());
					MutantBaseStatCharacter stat = em.find(
							  MutantBaseStatCharacter.class,
							  ((MutantBaseStatCharacter) baseStat.getValue()).getId());
					skill.setMutantBaseStatCharacter(stat);
					skill.setDescription(description.getValue().toString());
					skill.setBaseValue(Integer.parseInt(baseVal.getValue().toString()));
					skill.setBaseBonus(Integer.parseInt(bonusVal.getValue().toString()));
					skill.setTrainedValue(Integer.parseInt(trainedVal.getValue().toString()));
					skill.setCreationTrained(trained.booleanValue());


					em.getTransaction().commit();
					ownTable.removeItem(itmChar);
					itmChar = skill;
					ownTable.addLine(itmChar);
					ownTable.select(itmChar);

					updateCosts();
				} catch (Exception e) {
					notifyError("mutant skill", "Failed updating information on character skill", e, null);
				}
			}
		}
	}

	private void updateCosts() {
		costLayout.removeAllComponents();
		Integer erfSpent = mutantCharacter.getErfSpent();
		Integer spSpent = mutantCharacter.getSpSpent();
		Integer spMax = mutantCharacter.getCreationPoints();
		StringBuilder sb = new StringBuilder("SP spenderade: ");
		sb.append(spSpent.toString());
		sb.append(" / ");
		sb.append(spMax.toString());
		sb.append("\nERF spenderade: ");
		sb.append(erfSpent.toString());
		Label costLabel = new Label(sb.toString(), Label.CONTENT_PREFORMATTED);
		costLayout.addComponent(costLabel);
	}

	public void valueChange(ValueChangeEvent event) {
		Object eventObject = event.getProperty().getValue();
		if (eventObject instanceof MutantSkill) {
			MutantSkill skill = (MutantSkill) eventObject;
			infoLayout.removeAllComponents();
			infoLayout.addComponent(new SkillPanel(skill));
		} else if (eventObject instanceof MutantSkillCharacter) {
			MutantSkillCharacter skill = (MutantSkillCharacter) eventObject;
			infoLayout.removeAllComponents();
			infoLayout.addComponent(new SkillPanel(skill));
		} else {
			infoLayout.removeAllComponents();
			infoLayout.addComponent(new SkillPanel());
		}
	}
}