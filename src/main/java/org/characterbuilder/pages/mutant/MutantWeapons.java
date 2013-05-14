package org.characterbuilder.pages.mutant;

import java.util.List;

import javax.persistence.EntityManager;

import org.characterbuilder.pages.abst.mutant.MutantCharacterWorkspaceLayout;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.MutantCharacter;
import org.characterbuilder.persist.entity.MutantWeapon;
import org.characterbuilder.persist.entity.MutantWeaponCharacter;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.characterbuilder.defaults.ThousandsField;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@SuppressWarnings({"unused", "serial"})
public class MutantWeapons extends MutantCharacterWorkspaceLayout {

	private static final long serialVersionUID = -4491910468074637328L;
	private VerticalLayout infoLayout = new VerticalLayout();
	private VerticalLayout charInfoLayout = new VerticalLayout();
	private WeaponPanel weaponPanel = new WeaponPanel();
	private WeaponTable ownTable = new WeaponTable();
	private WeaponTable otherTable = new WeaponTable();

	public MutantWeapons(MutantCharacter mc) {
		super(mc);

		setInfo("Vapen, " + mutantCharacter.getName());
		infoPanel.setContent(charInfoLayout);
		updateInfo();

		ownTable.addListener(this);
		otherTable.addListener(this);

		addComponent(ownTable);
		infoLayout.addComponent(weaponPanel);
		addComponent(infoLayout);
		addComponent(otherTable);

		//POPULATE WEAPONS
		EntityManager em = ThePersister.getEntityManager();
		try {
			//OWN WEAPONS
			List<MutantWeaponCharacter> ownItemList = em.createQuery(
					  "SELECT wep "
					  + "FROM MutantWeaponCharacter wep "
					  + "WHERE wep.mutantCharacter.id = :charID",
					  MutantWeaponCharacter.class)
					  .setParameter("charID", mutantCharacter.getId())
					  .getResultList();
			//Set<MutantWeaponCharacter> ownWeaponSet = mutantCharacter.getMutantWeaponCharacters();
			for (MutantWeaponCharacter mwc : ownItemList) {
				ownTable.addWeapon(mwc);
			}

			//OTHER WEAPONS
			List<MutantWeapon> weaponList = em.createQuery(
					  "SELECT r "
					  + "FROM MutantWeapon r "
					  + "ORDER BY r.name",
					  MutantWeapon.class).getResultList();

			for (MutantWeapon mw : weaponList) {
				otherTable.addWeapon(mw);
			}



		} catch (Exception e) {
			notifyError("Mutant Weapons", "Exception occured populating page.", e, null);
		}
	}

	private void updateInfo() {
		charInfoLayout.removeAllComponents();
		Integer cost = mutantCharacter.getItemCost();
		Integer weight = mutantCharacter.getItemWeight();
		if (cost != null) {
			charInfoLayout.addComponent(new Label(
					  "<b><u>Total item cost:</u></b> "
					  + cost / 1000 + "," + cost % 1000 + " krediter", Label.CONTENT_XHTML));
		}
		if (weight != null) {
			charInfoLayout.addComponent(new Label(
					  "<b><u>Total item weight:</u></b> "
					  + weight / 1000 + "," + weight % 1000 + " kg", Label.CONTENT_XHTML));
		}
	}

	private class WeaponTable extends Table implements ValueChangeListener {

		private String[] headerNames = new String[]{
			"Vapen",
			"Fattn",
			"Init",
			"Skada",
			"STY-krav",
			"Pen",
			"Räckvidd",
			"Tål",
			"Pål",
			"Vikt",
			"Värde"
		};

		public WeaponTable() {
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

		public void addWeapon(MutantWeapon weapon) {
			String[] weaponData = new String[]{
				weapon.getName(),
				weapon.getGrip(),
				weapon.getInitiative() + "",
				weapon.getDamage(),
				weapon.getStyKrav() + "",
				weapon.getPenetration() + "",
				weapon.getReach() + " meter",
				weapon.getDurability() + "",
				weapon.getDependability() + "/100",
				(weapon.getWeight() / 1000.0) + " kg",
				(weapon.getPrice() / 1000.0) + " krediter"};

			addItem(weaponData, weapon);
		}

		public void addWeapon(MutantWeaponCharacter weapon) {
			String[] weaponData = new String[]{
				weapon.getName(),
				weapon.getGrip(),
				weapon.getInitiative() + "",
				weapon.getDamage(),
				weapon.getStyKrav() + "",
				weapon.getPenetration() + "",
				weapon.getReach() + " meter",
				weapon.getDurability() + "",
				weapon.getDependability() + "/100",
				(weapon.getWeight() / 1000.0) + " kg",
				(weapon.getPrice() / 1000.0) + " krediter"};

			addItem(weaponData, weapon);
		}
	}

	/**
	 * Unified class for viewing or editing weapons marked in the WeaponTable
	 *
	 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
	 *
	 */
	private class WeaponPanel extends VerticalLayout {

		private TextField name = new TextField("Vapen");
		private TextField fattn = new TextField("Fattn");
		private TextField init = new TextField("Init");
		private TextField dmg = new TextField("Skada");
		private TextField styKrav = new TextField("STY-krav");
		private TextField penetration = new TextField("Pen");
		private TextField reach = new TextField("Räckv");
		private TextField durability = new TextField("Tål");
		private TextField dependability = new TextField("Pål");
		private ThousandsField weight = new ThousandsField("Vikt");
		private ThousandsField cost = new ThousandsField("Värde");
		private Label description = new Label("", Label.CONTENT_PREFORMATTED);
		private Button addButton = new Button("Add");
		private Button remButton = new Button("Remove");
		private Button edtButton = new Button("Commit changes");
		private MutantWeaponCharacter mwc = null;

		public WeaponPanel() {
			super();
			mwc = new MutantWeaponCharacter();

			buildUI();

			addComponent(addButton);
			addButton.addListener(new Adder());
		}

		public WeaponPanel(MutantWeapon weapon) {
			super();

			addComponent(new Label("<b><u>Vapen:</u></b> " + weapon.getName().replace("\n", "<br/>"), Label.CONTENT_XHTML));
			addComponent(new Label("<b><u>Fattn:</u></b> " + weapon.getGrip().replace("\n", "<br/>"), Label.CONTENT_XHTML));
			addComponent(new Label(("<b><u>Initiativ:</u></b> " + weapon.getInitiative()).replace("\n", "<br/>"), Label.CONTENT_XHTML));
			addComponent(new Label("<b><u>Skada:</u></b> " + weapon.getDamage().replace("\n", "<br/>"), Label.CONTENT_XHTML));
			addComponent(new Label(("<b><u>STY-krav:</u></b> " + weapon.getStyKrav()).replace("\n", "<br/>"), Label.CONTENT_XHTML));
			addComponent(new Label(("<b><u>Penetration:</u></b> " + weapon.getPenetration()).replace("\n", "<br/>"), Label.CONTENT_XHTML));
			addComponent(new Label(("<b><u>Räckvidd:</u></b> " + weapon.getReach()).replace("\n", "<br/>"), Label.CONTENT_XHTML));
			addComponent(new Label(("<b><u>Tålighet:</u></b> " + weapon.getDurability()).replace("\n", "<br/>"), Label.CONTENT_XHTML));
			addComponent(new Label(("<b><u>Pålitlighet:</u></b> " + weapon.getDependability()).replace("\n", "<br/>"), Label.CONTENT_XHTML));
			String itemWeight = weapon.getWeight() / 1000 + "," + weapon.getWeight() % 1000 + " kg";
			String itemCost = weapon.getPrice() / 1000 + "," + weapon.getPrice() % 1000 + " krediter";
			addComponent(new Label("<b><u>Vikt:</u></b> " + itemWeight, Label.CONTENT_XHTML));
			addComponent(new Label("<b><u>Värde:</u></b> " + itemCost, Label.CONTENT_XHTML));
			addComponent(new Label("<b><u>Beskrivning:</u></b> " + weapon.getDescription().replace("\n", "<br/>"), Label.CONTENT_XHTML));


			addComponent(addButton);
			addButton.addListener(new Adder());
		}

		public WeaponPanel(MutantWeaponCharacter weapon) {
			super();
			mwc = weapon;

			buildUI();

			name.setValue(weapon.getName());
			fattn.setValue(weapon.getGrip());
			init.setValue(weapon.getInitiative().toString());
			dmg.setValue((weapon.getDamage()));
			styKrav.setValue(weapon.getStyKrav().toString());
			penetration.setValue(weapon.getPenetration().toString());
			reach.setValue(weapon.getReach().toString());
			durability.setValue(weapon.getDurability().toString());
			dependability.setValue(weapon.getDependability().toString());
			String itemWeight = weapon.getWeight() / 1000 + "," + weapon.getWeight() % 1000;
			String itemCost = weapon.getPrice() / 1000 + "," + weapon.getPrice() % 1000;
			weight.setValue(itemWeight);
			cost.setValue(itemCost);

			addComponent(edtButton);
			edtButton.addListener(new Editer());
			addComponent(remButton);
			remButton.addListener(new Remover());
		}

		private void buildUI() {
			addComponent(name);
			addComponent(fattn);
			addComponent(init);
			addComponent(dmg);
			addComponent(styKrav);
			addComponent(penetration);
			addComponent(reach);
			addComponent(durability);
			addComponent(dependability);
			addComponent(weight);
			addComponent(cost);
		}

		/*
		 * ADDER
		 */
		private class Adder implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				if (mwc == null) {
					Object obj = otherTable.getValue();
					if (obj instanceof MutantWeapon) {
						MutantWeapon weapon = (MutantWeapon) obj;
						EntityManager em = ThePersister.getEntityManager();
						try {
							em.getTransaction().begin();
							MutantWeaponCharacter newWeapon = new MutantWeaponCharacter(mutantCharacter, weapon);
							em.persist(newWeapon);
							em.getTransaction().commit();


							ownTable.addWeapon(newWeapon);
							ownTable.select(newWeapon);

						} catch (Exception e) {
							notifyError("Mutant weapon", "error adding new weapon to character", e, null);
						}
					} else {
						notifyError("Mutant weapon", "Error try reloading page..", null, null);
					}
				} else {
					EntityManager em = ThePersister.getEntityManager();
					try {
						em.getTransaction().begin();

						mwc.setName(name.getValue().toString());
						mwc.setGrip(fattn.getValue().toString());
						mwc.setInitiative(Integer.parseInt(init.getValue().toString()));
						mwc.setDamage(dmg.getValue().toString());
						mwc.setStyKrav(Integer.parseInt(styKrav.getValue().toString()));
						mwc.setPenetration(Integer.parseInt(penetration.getValue().toString()));
						mwc.setReach(Integer.parseInt(reach.getValue().toString()));
						mwc.setDurability(Integer.parseInt(durability.getValue().toString()));
						mwc.setDependability(Integer.parseInt(dependability.getValue().toString()));
						mwc.setWeight(weight.getThousands());
						mwc.setPrice(cost.getThousands());

						em.persist(mwc);
						em.getTransaction().commit();

						ownTable.addWeapon(mwc);
						ownTable.select(mwc);

					} catch (Exception e) {
						notifyError("Mutant weapon", "error adding new weapon to character", e, null);
					}
				}
				updateInfo();
			}
		}

		/*
		 * REMOVER
		 */
		private class Remover implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				Object obj = ownTable.getValue();
				if (obj instanceof MutantWeaponCharacter) {
					ownTable.removeItem(obj);
					EntityManager em = ThePersister.getEntityManager();
					try {
						em.getTransaction().begin();
						MutantWeaponCharacter weapon = em.find(MutantWeaponCharacter.class, mwc.getId());
						em.remove(weapon);
						em.getTransaction().commit();
					} catch (Exception e) {
						notifyError("mutant weapon", "error removing weapon from character", e, null);
					}
				}
				updateInfo();
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
					MutantWeaponCharacter weapon = em.find(MutantWeaponCharacter.class, mwc.getId());

					weapon.setName(name.getValue().toString());
					weapon.setDamage(dmg.getValue().toString());
					weapon.setGrip(fattn.getValue().toString());
					Integer initTmp = Integer.parseInt(init.getValue().toString());
					weapon.setInitiative(initTmp);
					weapon.setStyKrav(Integer.parseInt(styKrav.getValue().toString()));
					weapon.setPenetration(Integer.parseInt(penetration.getValue().toString()));
					weapon.setReach(Integer.parseInt(reach.getValue().toString()));
					weapon.setDurability(Integer.parseInt(durability.getValue().toString()));
					weapon.setDependability(Integer.parseInt(dependability.getValue().toString()));
					weapon.setWeight(weight.getThousands());
					weapon.setPrice(cost.getThousands());
					weapon.setDamage(dmg.getValue().toString());

					em.getTransaction().commit();
					ownTable.removeItem(mwc);
					mwc = weapon;
					ownTable.addWeapon(mwc);
					ownTable.select(mwc);
				} catch (Exception e) {
					notifyError("Mutant Weapon", "Failed updating information on character weapon", e, null);
				}
				updateInfo();
			}
		}
	}


	/*
	 * VALUE CHANGED
	 */
	@Override
	public void valueChange(ValueChangeEvent event) {
		Object eventObject = event.getProperty().getValue();
		if (eventObject instanceof MutantWeapon) {
			MutantWeapon weapon = (MutantWeapon) eventObject;
			infoLayout.removeAllComponents();
			infoLayout.addComponent(new WeaponPanel(weapon));
		} else if (eventObject instanceof MutantWeaponCharacter) {
			MutantWeaponCharacter weapon = (MutantWeaponCharacter) eventObject;
			infoLayout.removeAllComponents();
			infoLayout.addComponent(new WeaponPanel(weapon));
		} else {
			infoLayout.removeAllComponents();
			infoLayout.addComponent(new WeaponPanel());
		}
	}
}
