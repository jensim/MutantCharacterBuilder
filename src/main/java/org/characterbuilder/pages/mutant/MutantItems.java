package org.characterbuilder.pages.mutant;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import javax.persistence.EntityManager;
import org.characterbuilder.defaults.ThousandsField;
import org.characterbuilder.pages.abst.mutant.MutantCharacterWorkspaceLayout;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.MutantCharacter;
import org.characterbuilder.persist.entity.MutantItem;
import org.characterbuilder.persist.entity.MutantItemCharacter;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@SuppressWarnings("serial")
public class MutantItems extends MutantCharacterWorkspaceLayout {
	//private static final long serialVersionUID = -4734431848998229426L;

	private VerticalLayout infoLayout = new VerticalLayout();
	private VerticalLayout charInfoLayout = new VerticalLayout();
	private ItemPanel panel = new ItemPanel();
	private ItemTabel ownTable = new ItemTabel();
	private ItemTabel otherTable = new ItemTabel();

	public MutantItems(MutantCharacter mc) {
		super(mc);

		setInfo("Föremål, " + mutantCharacter.getName());
		infoPanel.setContent(charInfoLayout);
		updateInfo();

		EntityManager em = ThePersister.getEntityManager();

		ownTable.addListener(this);
		otherTable.addListener(this);

		addComponent(ownTable);
		infoLayout.addComponent(panel);
		addComponent(infoLayout);
		addComponent(otherTable);

		//OWN WEAPONS
		List<MutantItemCharacter> ownItemList = em.createQuery(
				  "SELECT item "
				  + "FROM MutantItemCharacter item "
				  + "WHERE item.mutantCharacter.id = :charID",
				  MutantItemCharacter.class)
				  .setParameter("charID", mutantCharacter.getId())
				  .getResultList();
		//Set<MutantItemCharacter> ownItemSet = mutantCharacter.getMutantItemCharacters();
		for (MutantItemCharacter mwc : ownItemList) {
			ownTable.addLine(mwc);
		}

		//POPULATE ITEM

		try {
			//OTHER WEAPONS
			List<MutantItem> itemList = em.createQuery(
					  "SELECT r "
					  + "FROM MutantItem r "
					  + "ORDER BY r.name",
					  MutantItem.class).getResultList();

			for (MutantItem item : itemList) {
				otherTable.addLine(item);
			}



		} catch (Exception e) {
			notifyError("Mutant Items", "Exception occured populating page.", e, null);
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
			"Föremål",
			"Vikt",
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

		public void addLine(MutantItem item) {
			String[] weaponData = new String[]{
				item.getName(),
				(item.getWeight() / 1000.0) + " kg",
				(item.getPrice() / 1000.0) + " krediter"};

			addItem(weaponData, item);
		}

		public void addLine(MutantItemCharacter item) {
			String[] weaponData = new String[]{
				item.getName(),
				(item.getWeight() / 1000.0) + " kg",
				(item.getPrice() / 1000.0) + " krediter"};

			addItem(weaponData, item);
		}
	}

	private class ItemPanel extends VerticalLayout {

		private TextField name = new TextField("Föremål");
		private ThousandsField weight = new ThousandsField("Vikt");
		private ThousandsField cost = new ThousandsField("Värde");
		private TextField ammount = new TextField("Antal", "1");
		private TextArea description = new TextArea("Beskrivning");
		private Button addButton = new Button("Add");
		private Button remButton = new Button("Remove");
		private Button edtButton = new Button("Commit changes");
		private MutantItemCharacter itm = null;

		public ItemPanel() {
			super();
			itm = new MutantItemCharacter();

			buildUI();
			addComponent(ammount);

			addComponent(addButton);
			addButton.addListener(new Adder());
		}

		public ItemPanel(MutantItem templateItem) {
			super();

			addComponent(new Label("<b><u>Föremål:</u></b> " + templateItem.getName().replace("\n", "<br/>"), Label.CONTENT_XHTML));
			String itemWeight = templateItem.getWeight() / 1000 + "," + templateItem.getWeight() % 1000 + " kg";
			addComponent(new Label("<b><u>Vikt:</u></b> " + itemWeight, Label.CONTENT_XHTML));
			String itemCost = templateItem.getPrice() / 1000 + "," + templateItem.getPrice() % 1000 + " krediter";
			addComponent(new Label("<b><u>Värde:</u></b> " + itemCost, Label.CONTENT_XHTML));
			addComponent(new Label("<b><u>Beskrivning:</u></b> " + templateItem.getDescription().replace("\n", "<br/>"), Label.CONTENT_XHTML));

			addComponent(addButton);
			addButton.addListener(new Adder());
		}

		public ItemPanel(MutantItemCharacter item) {
			super();
			itm = item;

			buildUI();
			addComponent(ammount);

			name.setValue(item.getName());
			String itemWeight = item.getWeight() / 1000 + "," + item.getWeight() % 1000;
			weight.setValue(itemWeight);
			String itemCost = item.getPrice() / 1000 + "," + item.getPrice() % 1000;
			cost.setValue(itemCost);
			ammount.setValue(item.getAmmount().toString());

			addComponent(edtButton);
			edtButton.addListener(new Editer());
			addComponent(remButton);
			remButton.addListener(new Remover());
		}

		private void buildUI() {
			addComponent(name);
			addComponent(weight);
			addComponent(cost);
		}

		/*
		 * ADDER
		 */
		private class Adder implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				if (itm == null) {
					Object obj = otherTable.getValue();
					if (obj instanceof MutantItem) {
						MutantItem item = (MutantItem) obj;
						EntityManager em = ThePersister.getEntityManager();
						try {
							em.getTransaction().begin();
							MutantItemCharacter newItem = new MutantItemCharacter(mutantCharacter, item);
							em.persist(newItem);
							em.getTransaction().commit();

							ownTable.addLine(newItem);
							ownTable.select(newItem);

						} catch (Exception e) {
							notifyError("Mutant items", "error adding new weapon to character", e, null);
						}
					} else {
						notifyError("Mutant items", "error try reloading page..", null, null);
					}
				} else {
					EntityManager em = ThePersister.getEntityManager();
					try {
						em.getTransaction().begin();

						itm.setName(name.getValue().toString());
						itm.setWeight(weight.getThousands());
						itm.setPrice(cost.getThousands());
						itm.setAmmount(Integer.parseInt(ammount.getValue().toString()));
						itm.setMutantCharacter(mutantCharacter);

						em.persist(itm);
						em.getTransaction().commit();

						ownTable.addLine(itm);
						ownTable.select(itm);

					} catch (Exception e) {
						notifyError("Mutant items", "error adding new weapon to character", e, null);
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
				if (obj instanceof MutantItemCharacter) {
					ownTable.removeItem(obj);
					EntityManager em = ThePersister.getEntityManager();
					try {
						em.getTransaction().begin();
						MutantItemCharacter item = em.find(MutantItemCharacter.class, itm.getId());
						em.remove(item);
						em.getTransaction().commit();
					} catch (Exception e) {
						notifyError("Mutant items", "error removing weapon from character", e, null);
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
					MutantItemCharacter item = em.find(MutantItemCharacter.class, itm.getId());

					item.setName(name.getValue().toString());
					item.setWeight(weight.getThousands());
					item.setPrice(cost.getThousands());
					item.setAmmount(Integer.parseInt(ammount.getValue().toString()));

					em.getTransaction().commit();
					ownTable.removeItem(itm);
					itm = item;
					ownTable.addLine(itm);
					ownTable.select(itm);
				} catch (Exception e) {
					notifyError("Mutant items", "Error updating information on character weapon", e, null);
				}
			}
		}
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		Object eventObject = event.getProperty().getValue();
		if (eventObject instanceof MutantItem) {
			MutantItem item = (MutantItem) eventObject;
			infoLayout.removeAllComponents();
			infoLayout.addComponent(new ItemPanel(item));
		} else if (eventObject instanceof MutantItemCharacter) {
			MutantItemCharacter item = (MutantItemCharacter) eventObject;
			infoLayout.removeAllComponents();
			infoLayout.addComponent(new ItemPanel(item));
		} else {
			infoLayout.removeAllComponents();
			infoLayout.addComponent(new ItemPanel());
		}
	}
}
