package org.characterbuilder.pages.mutant;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import javax.persistence.EntityManager;
import org.characterbuilder.CharbuildApp;
import org.characterbuilder.defaults.MyTextArea;
import org.characterbuilder.defaults.ThousandsField;
import org.characterbuilder.pages.abst.mutant.MutantCharacterWorkspaceLayout;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.MutantArmor;
import org.characterbuilder.persist.entity.MutantArmorBodypart;
import org.characterbuilder.persist.entity.MutantArmorCharacter;
import org.characterbuilder.persist.entity.MutantCharacter;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@SuppressWarnings("serial")
public class MutantArmors extends MutantCharacterWorkspaceLayout {

	private VerticalLayout infoLayout = new VerticalLayout();
	private VerticalLayout charInfoLayout = new VerticalLayout();
	private ItemPanel panel = new ItemPanel();
	private ItemTabel ownTable = new ItemTabel();
	private ItemTabel otherTable = new ItemTabel();

	public MutantArmors(MutantCharacter mc) {
		super(mc);

		setInfo("Rustningsdelar, " + mutantCharacter.getName());
		infoPanel.setContent(charInfoLayout);
		updateInfo();

		ownTable.addListener(this);
		otherTable.addListener(this);

		addComponent(ownTable);
		infoLayout.addComponent(panel);
		addComponent(infoLayout);
		addComponent(otherTable);

		//POPULATE ITEM
		EntityManager em = ThePersister.getEntityManager();
		try {
			//OWN WEAPONS
			List<MutantArmorCharacter> ownItemList = em.createQuery(
					  "SELECT armor "
					  + "FROM MutantArmorCharacter armor "
					  + "WHERE armor.mutantCharacter.id = :charID",
					  MutantArmorCharacter.class)
					  .setParameter("charID", mutantCharacter.getId())
					  .getResultList();
			//Set<MutantArmorCharacter> ownItemSet = mutantCharacter.getMutantArmorCharacters();
			for (MutantArmorCharacter mwc : ownItemList) {
				ownTable.addLine(mwc);
			}

			//OTHER WEAPONS
			List<MutantArmor> itemList = em.createQuery(
					  "SELECT r "
					  + "FROM MutantArmor r "
					  + "ORDER BY r.name",
					  MutantArmor.class).getResultList();

			for (MutantArmor item : itemList) {
				otherTable.addLine(item);
			}

		} catch (Exception e) {
			notifyError("Items", "Exception occured populating page.", e, null);
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

	private class ItemTabel extends Table {

		private String[] headerNames = new String[]{
			"Rustning",
			"ABS",
			"BEG",
			"Vikt",
			"Kroppsdel",
			"Pris"
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

		public void addLine(MutantArmor item) {
			String[] weaponData = new String[]{
				item.getName(),
				item.getAbs().toString(),
				item.getBeg().toString(),
				(item.getWeight() / 1000.0) + " kg",
				item.getMutantArmorBodypart().getName(),
				(item.getPrice() / 1000.0) + " krediter"};

			addItem(weaponData, item);
		}

		public void addLine(MutantArmorCharacter item) {
			String[] weaponData = new String[]{
				item.getName(),
				item.getAbs().toString(),
				item.getBeg().toString(),
				(item.getWeight() / 1000.0) + " kg",
				item.getMutantArmorBodypart().getName(),
				(item.getPrice() / 1000.0) + " krediter"};

			addItem(weaponData, item);
		}
	}

	private class ItemPanel extends VerticalLayout {

		private TextField name = new TextField("Föremål");
		private TextField abs = new TextField("ABS");
		private TextField beg = new TextField("BEG");
		private ThousandsField weight = new ThousandsField("Vikt");
		private Select bodyPart = new Select("Kroppsdel");
		private ThousandsField cost = new ThousandsField("Värde");
		private MyTextArea description = new MyTextArea("Beskrivning");
		private Button addButton = new Button("Add");
		private Button remButton = new Button("Remove");
		private Button edtButton = new Button("Commit changes");
		private MutantArmorCharacter itmchar = null;
		private MutantArmor itm = null;

		public ItemPanel() {
			super();
			itmchar = new MutantArmorCharacter();

			buildUI();

			addComponent(addButton);
			addButton.addListener(new Adder());
		}

		public ItemPanel(MutantArmor item) {
			super();
			itm = item;

			addComponent(new Label("<b><u>Rustning:</u></b> " + item.getName().replace("\n", "<br/>"), Label.CONTENT_XHTML));
			addComponent(new Label(("<b><u>Absorbtion:</u></b> " + item.getAbs()).replace("\n", "<br/>"), Label.CONTENT_XHTML));
			addComponent(new Label(("<b><u>Begränsning:</u></b> " + item.getBeg()).replace("\n", "<br/>"), Label.CONTENT_XHTML));
			addComponent(new Label("<b><u>Kroppsdel:</u></b> " + item.getMutantArmorBodypart().getName().replace("\n", "<br/>"), Label.CONTENT_XHTML));
			String itemWeight = item.getWeight() / 1000 + "," + item.getWeight() % 1000 + " kg";
			addComponent(new Label(("<b><u>Vikt:</u></b> " + itemWeight).replace("\n", "<br/>"), Label.CONTENT_XHTML));
			String itemPrice = item.getPrice() / 1000 + "," + item.getPrice() % 1000 + " krediter";
			addComponent(new Label(("<b><u>Värde:</u></b> " + itemPrice).replace("\n", "<br/>"), Label.CONTENT_XHTML));
			addComponent(new Label("<b><u>Beskrivning:</u></b> " + item.getDescription().replace("\n", "<br/>"), Label.CONTENT_XHTML));

			addComponent(addButton);
			addButton.addListener(new Adder());
		}

		public ItemPanel(MutantArmorCharacter item) {
			super();
			itmchar = item;

			buildUI();

			name.setValue(item.getName());
			abs.setValue(item.getAbs().toString());
			beg.setValue(item.getBeg().toString());
			//bodypart, handled in buildUI
			String itemWeight = item.getWeight() / 1000 + "," + item.getWeight() % 1000;
			weight.setValue(itemWeight);
			String itemPrice = item.getPrice() / 1000 + "," + item.getPrice() % 1000;
			cost.setValue(itemPrice);
			description.setValue(item.getDescription());

			addComponent(edtButton);
			edtButton.addListener(new Editer());
			addComponent(remButton);
			remButton.addListener(new Remover());
		}

		private void buildUI() {
			bodyPart.setNullSelectionAllowed(false);
			bodyPart.setMultiSelect(false);

			addComponent(name);
			addComponent(abs);
			addComponent(beg);
			addComponent(bodyPart);
			addComponent(weight);
			addComponent(cost);
			addComponent(description);

			EntityManager em = ThePersister.getEntityManager();
			try {
				List<MutantArmorBodypart> partList = em.createQuery(
						  "SELECT r "
						  + "FROM MutantArmorBodypart r ",
						  MutantArmorBodypart.class)
						  .getResultList();
				for (MutantArmorBodypart part : partList) {
					bodyPart.addItem(part);
					if (itmchar != null) {
						if (itmchar.getMutantArmorBodypart() != null) {
							if (itmchar.getMutantArmorBodypart().getId() == part.getId()) {
								bodyPart.select(part);
							}
						}
					} else if (itm != null) {
						if (itm.getMutantArmorBodypart() != null) {
							if (itm.getMutantArmorBodypart().getId() == part.getId()) {
								bodyPart.select(part);
							}
						}
					}
				}
			} catch (Exception e) {
				notifyError("Mutant armor", "failed loading page", e, null);
			}


		}

		/*
		 * ADDER
		 */
		private class Adder implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				if (itmchar == null) {
					Object obj = otherTable.getValue();
					if (obj instanceof MutantArmor) {
						MutantArmor item = (MutantArmor) obj;
						EntityManager em = ThePersister.getEntityManager();
						try {
							em.getTransaction().begin();
							MutantArmorCharacter newItem = new MutantArmorCharacter(mutantCharacter, item);
							em.persist(newItem);
							em.getTransaction().commit();

							ownTable.addLine(newItem);
							ownTable.select(newItem);

							ThePersister.logIt(1, "MutantArmor added to character " + mutantCharacter.getId(), null, CharbuildApp.getRollspelUser());
						} catch (Exception e) {
							notifyError("Mutant Armor", "failed adding new weapon to character", e, null);
						}
					} else {
						notifyError("Mutant armor", "error reloading page..", null, null);
					}
				} else {
					EntityManager em = ThePersister.getEntityManager();
					try {
						em.getTransaction().begin();
						//TODO
						itmchar.setName(name.getValue().toString());
						itmchar.setAbs(Integer.parseInt(abs.getValue().toString()));
						itmchar.setBeg(Integer.parseInt(beg.getValue().toString()));
						MutantArmorBodypart part = (MutantArmorBodypart) bodyPart.getValue();
						itmchar.setMutantArmorBodypart(part);
						itmchar.setWeight(weight.getThousands());
						itmchar.setPrice(cost.getThousands());
						itmchar.setDescription(description.getValue().toString());
						itmchar.setMutantCharacter(mutantCharacter);

						em.persist(itmchar);
						em.getTransaction().commit();

						ownTable.addLine(itmchar);
						ownTable.select(itmchar);

						ThePersister.logIt(1, "Mutant armor added to character " + mutantCharacter.getId(), null, CharbuildApp.getRollspelUser());
					} catch (Exception e) {
						notifyError("Mutant armor", "error adding new weapon to character", e, null);
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
				if (obj instanceof MutantArmorCharacter) {
					ownTable.removeItem(obj);
					EntityManager em = ThePersister.getEntityManager();
					try {
						em.getTransaction().begin();
						MutantArmorCharacter item = em.find(MutantArmorCharacter.class, itmchar.getId());
						em.remove(item);
						em.getTransaction().commit();

						ThePersister.logIt(1, "MutantArmor removed from character " + mutantCharacter.getId(), null, CharbuildApp.getRollspelUser());
					} catch (Exception e) {
						notifyError("Mutant armor", "error removing weapon from character", e, null);
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
					MutantArmorCharacter item = em.find(MutantArmorCharacter.class, itmchar.getId());

					item.setName(name.getValue().toString());
					item.setAbs(Integer.parseInt(abs.getValue().toString()));
					item.setBeg(Integer.parseInt(beg.getValue().toString()));
					MutantArmorBodypart part = em.find(
							  MutantArmorBodypart.class,
							  ((MutantArmorBodypart) bodyPart.getValue()).getId());
					item.setMutantArmorBodypart(part);
					item.setWeight(weight.getThousands());
					item.setPrice(cost.getThousands());
					item.setDescription(description.getValue().toString());

					em.getTransaction().commit();
					ownTable.removeItem(itmchar);
					itmchar = item;
					ownTable.addLine(item);
					ownTable.select(itmchar);

					ThePersister.logIt(1, "MutantArmor edited on character " + mutantCharacter.getId(), null, CharbuildApp.getRollspelUser());
				} catch (Exception e) {
					notifyError("Mutant armor", "Failed updating information on character weapon", e, null);
				}
			}
		}
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		Object eventObject = event.getProperty().getValue();
		if (eventObject instanceof MutantArmor) {
			MutantArmor item = (MutantArmor) eventObject;
			infoLayout.removeAllComponents();
			infoLayout.addComponent(new ItemPanel(item));
		} else if (eventObject instanceof MutantArmorCharacter) {
			MutantArmorCharacter item = (MutantArmorCharacter) eventObject;
			infoLayout.removeAllComponents();
			infoLayout.addComponent(new ItemPanel(item));
		} else {
			infoLayout.removeAllComponents();
			infoLayout.addComponent(new ItemPanel());
		}
	}
}
