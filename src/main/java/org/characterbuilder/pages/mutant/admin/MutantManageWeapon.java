package org.characterbuilder.pages.mutant.admin;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import javax.persistence.EntityManager;
import org.characterbuilder.defaults.MyTextArea;
import org.characterbuilder.defaults.ThousandsField;
import org.characterbuilder.pages.abst.WorkspaceLayout;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.MutantWeapon;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@SuppressWarnings("serial")
public class MutantManageWeapon extends WorkspaceLayout implements ValueChangeListener {

	private Panel thePanel = new Panel();
	private Table weaponTable = new Table();
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

	public MutantManageWeapon() {

		removeAllComponents();

		weaponTable.setMultiSelect(false);
		weaponTable.setNullSelectionAllowed(true);
		weaponTable.setColumnReorderingAllowed(false);
		weaponTable.setColumnCollapsingAllowed(false);

		weaponTable.addListener(this);
		weaponTable.setImmediate(true);
		weaponTable.setSelectable(true);
		for (String name : headerNames) {
			weaponTable.addContainerProperty(name, String.class, null);
		}

		addComponent(weaponTable);
		//POPULATE WEAPONS
		EntityManager em = ThePersister.getEntityManager();
		try {
			List<MutantWeapon> weaponList = em.createQuery(
					  "SELECT r FROM MutantWeapon r",
					  MutantWeapon.class).getResultList();

			for (MutantWeapon mw : weaponList) {
				addWeaponToTabel(mw);
			}
		} catch (Exception e) {
			notifyError("Failed", "Dont do that.", e, null);
		}

		addComponent(thePanel);
		thePanel.setContent(new WeaponView());
	}

	private void addWeaponToTabel(MutantWeapon weapon) {
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

		weaponTable.addItem(weaponData, weapon);

	}

	private class WeaponView extends VerticalLayout {

		MutantWeapon weapon = null;
		TextField nameField = new TextField("Namn", "");
		TextField gripField = new TextField("Fattning", "1H");
		TextField initField = new TextField("Initiativ", "0");
		TextField damageField = new TextField("Skada", "1T6");
		TextField styField = new TextField("STY-krav", "0");
		TextField penField = new TextField("PEN", "0");
		TextField reachField = new TextField("Räckvidd", "5");
		TextField duraField = new TextField("TÅL", "10");
		TextField dependField = new TextField("PÅL", "100");
		ThousandsField weightField = new ThousandsField("Vikt, kg", "1,0");
		ThousandsField priceField = new ThousandsField("Värde, kr", "1,0");
		MyTextArea descriptionArea = new MyTextArea("Beskrivning", "");
		Button addButton = new Button("Lägg till vapen");
		Button saveButton = new Button("Spara vapen");
		Button removeButton = new Button("Ta bort vapen");

		public WeaponView() {
			buildUI();

			addComponent(addButton);
			addButton.addListener(new Adder());
		}

		WeaponView(MutantWeapon wep) {
			weapon = wep;
			buildUI();

			nameField.setValue(weapon.getName());
			gripField.setValue(weapon.getGrip());
			initField.setValue(weapon.getInitiative().toString());
			damageField.setValue(weapon.getDamage());
			styField.setValue(weapon.getStyKrav().toString());
			penField.setValue(weapon.getPenetration().toString());
			reachField.setValue(weapon.getReach().toString());
			duraField.setValue(weapon.getDurability().toString());
			dependField.setValue(weapon.getDependability().toString());
			String weight = wep.getWeight() / 1000 + "," + wep.getWeight() % 1000;
			String cost = wep.getPrice() / 1000 + "," + wep.getPrice() % 1000;
			weightField.setValue(weight);
			priceField.setValue(cost);
			descriptionArea.setValue(weapon.getDescription());

			addComponent(removeButton);
			removeButton.addListener(new Remover());
			addComponent(saveButton);
			saveButton.addListener(new Saver());
		}

		private void buildUI() {
			addComponent(nameField);
			addComponent(gripField);
			addComponent(initField);
			addComponent(damageField);
			addComponent(styField);
			addComponent(penField);
			addComponent(reachField);
			addComponent(duraField);
			addComponent(dependField);
			addComponent(weightField);
			addComponent(priceField);
			addComponent(descriptionArea);

		}

		private class Adder implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				EntityManager em = ThePersister.getEntityManager();
				try {
					em.getTransaction().begin();
					MutantWeapon newWep = new MutantWeapon();

					newWep.setName(nameField.getValue().toString());
					newWep.setGrip(gripField.getValue().toString());
					newWep.setInitiative(Integer.parseInt(initField.getValue().toString()));
					newWep.setDamage(damageField.getValue().toString());
					newWep.setStyKrav(Integer.parseInt(styField.getValue().toString()));
					newWep.setPenetration(Integer.parseInt(penField.getValue().toString()));
					newWep.setReach(Integer.parseInt(reachField.getValue().toString()));
					newWep.setDurability(Integer.parseInt(duraField.getValue().toString()));
					newWep.setDependability(Integer.parseInt(dependField.getValue().toString()));
					newWep.setWeight(weightField.getThousands());
					newWep.setPrice(priceField.getThousands());
					newWep.setDescription(descriptionArea.getValue().toString());

					em.persist(newWep);

					em.getTransaction().commit();
					addWeaponToTabel(newWep);

					notifyInfo("new weapon", "saved", null, null);
				} catch (Exception e) {
					notifyError("Failed", "Dont do that.", e, null);
				}

			}
		}

		private class Saver implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				EntityManager em = ThePersister.getEntityManager();
				try {
					em.getTransaction().begin();

					weapon.setName(nameField.getValue().toString());
					weapon.setGrip(gripField.getValue().toString());
					weapon.setInitiative(Integer.parseInt(initField.getValue().toString()));
					weapon.setDamage(damageField.getValue().toString());
					weapon.setStyKrav(Integer.parseInt(styField.getValue().toString()));
					weapon.setPenetration(Integer.parseInt(penField.getValue().toString()));
					weapon.setReach(Integer.parseInt(reachField.getValue().toString()));
					weapon.setDurability(Integer.parseInt(duraField.getValue().toString()));
					weapon.setDependability(Integer.parseInt(dependField.getValue().toString()));
					weapon.setWeight(weightField.getThousands());
					weapon.setPrice(priceField.getThousands());
					weapon.setDescription(descriptionArea.getValue().toString());

					em.merge(weapon);

					em.getTransaction().commit();
					notifyInfo("save weapon", "sucess", null, null);
				} catch (Exception e) {
					notifyError("Failed", "Dont do that.", e, null);
				}

			}
		}

		private class Remover implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				EntityManager em = ThePersister.getEntityManager();
				try {
					em.getTransaction().begin();
					Object obj = weaponTable.getValue();
					MutantWeapon thing = (MutantWeapon) obj;
					thing = em.find(MutantWeapon.class, thing.getId());
					em.remove(thing);
					em.getTransaction().commit();

					weaponTable.removeItem(obj);
					notifyInfo("Manage weapon", "Weapon removed: " + thing.getName(), null, null);
				} catch (Exception e) {
					notifyError("Failed", "What did you do?!...", e, null);
				}

			}
		}
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		Object eventObject = event.getProperty().getValue();
		weaponTable.setWidth("100%");
		if (eventObject instanceof MutantWeapon) {
			MutantWeapon weapon = (MutantWeapon) eventObject;
			thePanel.setContent(new WeaponView(weapon));
		} else {
			thePanel.setContent(new WeaponView());
		}
	}
}
