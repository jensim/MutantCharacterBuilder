package org.characterbuilder.pages.mutant;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import javax.persistence.EntityManager;
import org.characterbuilder.defaults.MyTextArea;
import org.characterbuilder.pages.abst.mutant.MutantCharacterWorkspaceLayout;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.MutantAbility;
import org.characterbuilder.persist.entity.MutantAbilityCharacter;
import org.characterbuilder.persist.entity.MutantCharacter;
import org.characterbuilder.persist.entity.MutantSkillCharacter;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@SuppressWarnings("serial")
public class MutantAbilities extends MutantCharacterWorkspaceLayout {

	private static final long serialVersionUID = 4825040966845105007L;
	private VerticalLayout infoLayout = new VerticalLayout();
	private VerticalLayout costLayout = new VerticalLayout();
	private ItemPanel panel = new ItemPanel();
	private ItemTabel ownTable = new ItemTabel();
	private ItemTabel otherTable = new ItemTabel();

	public MutantAbilities(MutantCharacter mc) {
		super(mc);

		setInfo("Förmågor, " + mutantCharacter.getName());
		infoPanel.setContent(costLayout);
		updateCosts();

		ownTable.addListener(this);
		otherTable.addListener(this);

		addComponent(ownTable);
		infoLayout.addComponent(panel);
		addComponent(infoLayout);
		addComponent(otherTable);

		//POPULATE ABILITIES
		EntityManager em = ThePersister.getEntityManager();
		try {
			//OTHER ABILITIES
			List<MutantAbility> itemList = em.createQuery(
					  "SELECT r "
					  + "FROM MutantAbility r "
					  + "WHERE r.mutantClass = :klass "
					  + "ORDER BY r.name",
					  MutantAbility.class)
					  .setParameter("klass", mutantCharacter.getMutantClass())
					  .getResultList();

			for (MutantAbility item : itemList) {
				otherTable.addLine(item);
			}

			List<MutantAbilityCharacter> ownItemList = em.createQuery(
					  "SELECT abil "
					  + "FROM MutantAbilityCharacter abil "
					  + "WHERE abil.mutantCharacter.id = :charID",
					  MutantAbilityCharacter.class)
					  .setParameter("charID", mutantCharacter.getId())
					  .getResultList();
			//OWN ABILITIES
			//Set<MutantAbilityCharacter> ownItemSet = mutantCharacter.getMutantAbilityCharacters();
			for (MutantAbilityCharacter mwc : ownItemList) {
				ownTable.addLine(mwc);
			}

		} catch (Exception e) {
			notifyError("Items", "Exception occured populating page.", e, null);
		}
	}

	private class ItemTabel extends Table {

		private String[] headerNames = new String[]{
			"Förmåga",
			"Kostnad"
		};

		public ItemTabel() {
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

		public void addLine(MutantAbility item) {
			String[] weaponData = new String[]{
				item.getName(),
				item.getCost() + ""};

			addItem(weaponData, item);
		}

		public void addLine(MutantAbilityCharacter item) {
			String[] weaponData = new String[]{
				item.getName(),
				item.getCost() + ""};

			addItem(weaponData, item);
		}
	}

	private class ItemPanel extends VerticalLayout {

		private TextField name = new TextField("Förmåga");
		private TextField cost = new TextField("Kostnad", "1");
		private TextField activation = new TextField("Aktivering");
		private TextField reach = new TextField("Räckvidd");
		private TextField effect = new TextField("Effekt");
		private TextField duration = new TextField("Varaktighet");
		private MyTextArea description = new MyTextArea("Beskrivning");
		private Button addButton = new Button("Add");
		private Button remButton = new Button("Remove");
		private Button edtButton = new Button("Commit changes");
		private MutantAbilityCharacter itmChar = null;
		private MutantAbility itm = null;

		public ItemPanel() {
			super();
			itmChar = new MutantAbilityCharacter();

			buildUI();

			addComponent(addButton);
			addButton.addListener(new Adder());
		}

		public ItemPanel(MutantAbility item) {
			super();
			itm = item;

			addComponent(new Label("<b><u>Förmåga:</u></b> " + itm.getName().replace("\n", "<br/>"), Label.CONTENT_XHTML));
			addComponent(new Label(("<b><u>Kostnad:</u></b> " + itm.getCost()).replace("\n", "<br/>"), Label.CONTENT_XHTML));
			addComponent(new Label("<b><u>Aktivering:</u></b> " + itm.getActivation().replace("\n", "<br/>"), Label.CONTENT_XHTML));
			addComponent(new Label("<b><u>Räckvidd:</u></b> " + itm.getReach().replace("\n", "<br/>"), Label.CONTENT_XHTML));
			addComponent(new Label("<b><u>Effekt:</u></b> " + itm.getEffect().replace("\n", "<br/>"), Label.CONTENT_XHTML));
			addComponent(new Label("<b><u>Varaktighet:</u></b> " + itm.getDuration().replace("\n", "<br/>"), Label.CONTENT_XHTML));
			addComponent(new Label("<b><u>Beskrivning:</u></b> " + itm.getDescription().replace("\n", "<br/>"), Label.CONTENT_XHTML));

			addComponent(addButton);
			addButton.addListener(new Adder());
		}

		public ItemPanel(MutantAbilityCharacter item) {
			super();
			itmChar = item;

			buildUI();

			name.setValue(item.getName());
			cost.setValue(item.getCost().toString());
			activation.setValue(item.getActivation());
			reach.setValue(item.getReach());
			effect.setValue(item.getEffect());
			duration.setValue(item.getDuration());
			description.setValue(item.getDescription());

			addComponent(edtButton);
			edtButton.addListener(new Editer());
			addComponent(remButton);
			remButton.addListener(new Remover());
		}

		private void buildUI() {
			addComponent(name);
			addComponent(cost);
			addComponent(activation);
			addComponent(reach);
			addComponent(effect);
			addComponent(duration);
			addComponent(description);
		}

		/*
		 * ADDER
		 */
		private class Adder implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				if (itmChar == null) {
					Object obj = otherTable.getValue();
					if (obj instanceof MutantAbility) {
						MutantAbility item = (MutantAbility) obj;
						EntityManager em = ThePersister.getEntityManager();
						try {
							em.getTransaction().begin();
							MutantAbilityCharacter newItem = new MutantAbilityCharacter();
							newItem.setMutantCharacter(mutantCharacter);
							newItem.setActivation(item.getActivation());
							newItem.setCost(item.getCost());
							newItem.setDescription(item.getDescription());
							newItem.setDuration(item.getDuration());
							newItem.setEffect(item.getEffect());
							newItem.setName(item.getName());
							newItem.setReach(item.getReach());
							if (item.getMutantSkill() != null) {
								MutantSkillCharacter mcs = new MutantSkillCharacter(mutantCharacter, item.getMutantSkill());
								//MutantSkillCharacter mcs = new MutantSkillCharacter();
								em.persist(mcs);
							}
							em.persist(newItem);
							em.getTransaction().commit();

							ownTable.addLine(newItem);
							ownTable.select(newItem);
							updateCosts();
						} catch (Exception e) {
							notifyError("Error", "adding new ability to character", e, null);
						}
					} else {
						notifyError("Error", "try reloading page..", null, null);
					}
				} else {
					EntityManager em = ThePersister.getEntityManager();
					try {
						em.getTransaction().begin();

						itmChar.setName(name.getValue().toString());
						itmChar.setCost(Integer.parseInt(cost.getValue().toString()));
						itmChar.setActivation(activation.getValue().toString());
						itmChar.setReach(reach.getValue().toString());
						itmChar.setEffect(effect.getValue().toString());
						itmChar.setDuration(duration.getValue().toString());
						itmChar.setDescription(description.getValue().toString());
						itmChar.setMutantCharacter(mutantCharacter);

						em.persist(itmChar);
						em.getTransaction().commit();

						ownTable.addLine(itmChar);
						ownTable.select(itmChar);
						updateCosts();
					} catch (Exception e) {
						notifyError("Error", "adding new ability to character", e, null);
					}
				}
			}
		}

		/*
		 * REMOVER
		 */
		private class Remover implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				Object obj = ownTable.getValue();
				if (obj instanceof MutantAbilityCharacter) {
					ownTable.removeItem(obj);
					EntityManager em = ThePersister.getEntityManager();
					try {
						em.getTransaction().begin();
						MutantAbilityCharacter item = em.find(MutantAbilityCharacter.class, itmChar.getId());
						em.remove(item);
						em.getTransaction().commit();

						updateCosts();
					} catch (Exception e) {
						notifyError("Error", "removing ability from character", e, null);
					}
				}
			}
		}

		/*
		 * EDITER
		 */
		private class Editer implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				EntityManager em = ThePersister.getEntityManager();
				try {
					em.getTransaction().begin();
					MutantAbilityCharacter item = em.find(MutantAbilityCharacter.class, itmChar.getId());

					item.setName(name.getValue().toString());
					item.setCost(Integer.parseInt(cost.getValue().toString()));
					item.setActivation(activation.getValue().toString());
					item.setReach(reach.getValue().toString());
					item.setEffect(effect.getValue().toString());
					item.setDuration(duration.getValue().toString());
					item.setDescription(description.getValue().toString());
					em.merge(item);

					em.getTransaction().commit();
					ownTable.removeItem(itmChar);
					itmChar = item;
					ownTable.addLine(itmChar);
					ownTable.select(itmChar);

					updateCosts();
				} catch (Exception e) {
					notifyError("ItemPanel", "Failed updating information on character ability", e, null);
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

	@Override
	public void valueChange(ValueChangeEvent event) {
		Object eventObject = event.getProperty().getValue();
		if (eventObject instanceof MutantAbility) {
			MutantAbility item = (MutantAbility) eventObject;
			infoLayout.removeAllComponents();
			infoLayout.addComponent(new ItemPanel(item));
		} else if (eventObject instanceof MutantAbilityCharacter) {
			MutantAbilityCharacter item = (MutantAbilityCharacter) eventObject;
			infoLayout.removeAllComponents();
			infoLayout.addComponent(new ItemPanel(item));
		} else {
			infoLayout.removeAllComponents();
			infoLayout.addComponent(new ItemPanel());
		}
	}
}
