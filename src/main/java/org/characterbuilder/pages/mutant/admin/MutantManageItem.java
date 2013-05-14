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
import org.characterbuilder.persist.entity.MutantItem;


/**
 * 
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@SuppressWarnings("serial")
public class MutantManageItem extends WorkspaceLayout implements ValueChangeListener {

    private Panel thePanel = new Panel();
    private Table itemTable = new Table();
    private String[] headerNames = new String[]{
        "Föremål",
        "Pris",
        "Vikt"
    };

    public MutantManageItem() {

        removeAllComponents();

        itemTable.setMultiSelect(false);
        itemTable.setNullSelectionAllowed(true);
        itemTable.setColumnReorderingAllowed(false);
        itemTable.setColumnCollapsingAllowed(false);

        itemTable.addListener(this);
        itemTable.setImmediate(true);
        itemTable.setSelectable(true);
        for (String name : headerNames) {
            itemTable.addContainerProperty(name, String.class, null);
        }

        addComponent(itemTable);
        //POPULATE WEAPONS
        EntityManager em = ThePersister.getEntityManager();
        try {
            List<MutantItem> itemList = em.createQuery(
                    "SELECT r FROM MutantItem r",
                    MutantItem.class).getResultList();

            for (MutantItem mi : itemList) {
                addItemToTabel(mi);
            }
        } catch (Exception e) {
            notifyError("Failed", "loading item", e,null);
        }

        addComponent(thePanel);
        thePanel.setContent(new ItemView());
    }

    private void addItemToTabel(MutantItem item) {
        String[] itemData = new String[]{
            item.getName(),
            (item.getPrice() / 1000.0) + " krediter",
            (item.getWeight() / 1000.0) + " kg"
        };

        itemTable.addItem(itemData, item);
    }

    private class ItemView extends VerticalLayout {

        MutantItem item = null;
        TextField nameField = new TextField("Namn", "");
        ThousandsField weightField = new ThousandsField("Vikt, kg", "1,0");
        ThousandsField priceField = new ThousandsField("Värde, kr", "1,0");
        MyTextArea descriptionArea = new MyTextArea("Beskrivning", "");
        Button addButton = new Button("Lägg till föremål");
        Button saveButton = new Button("Spara föremål");
        Button removeButton = new Button("Ta bort föremål");

        ItemView() {
            buildUI();

            addComponent(addButton);
            addButton.addListener(new Adder());
        }

        ItemView(MutantItem ittem) {
            item = ittem;
            buildUI();

            nameField.setValue(item.getName());
			String weight = ittem.getWeight()/1000+","+ittem.getWeight()%1000;
			String cost = ittem.getPrice()/1000+","+ittem.getPrice()%1000;
            weightField.setValue(weight);
            priceField.setValue(cost);
            descriptionArea.setValue(item.getDescription());

            addComponent(removeButton);
            removeButton.addListener(new Remover());
            addComponent(saveButton);
            saveButton.addListener(new Saver());
        }

        private void buildUI() {
            addComponent(nameField);
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
                    MutantItem newItem = new MutantItem();

                    newItem.setName(nameField.getValue().toString());
                    newItem.setWeight(weightField.getThousands());
                    newItem.setPrice(priceField.getThousands());
                    newItem.setDescription(descriptionArea.getValue().toString());

                    em.persist(newItem);

                    em.getTransaction().commit();
                    addItemToTabel(newItem);

                    notifyInfo("new item", "saved",null,null);
                } catch (Exception e) {
                    notifyError("Failed", "new item", e,null);
                }

            }
        }

        private class Saver implements Button.ClickListener {

            @Override
            public void buttonClick(ClickEvent event) {
                EntityManager em = ThePersister.getEntityManager();
                try {
                    em.getTransaction().begin();

                    item.setName(nameField.getValue().toString());
                    item.setWeight(weightField.getThousands());
                    item.setPrice(priceField.getThousands());
                    item.setDescription(descriptionArea.getValue().toString());

                    em.merge(item);

                    em.getTransaction().commit();
                    notifyInfo("save item", "sucess",null,null);
                } catch (Exception e) {
                    notifyError("Failed", "save item", e,null);
                }

            }
        }

        private class Remover implements Button.ClickListener {

            @Override
            public void buttonClick(ClickEvent event) {
                EntityManager em = ThePersister.getEntityManager();
                try {
                    em.getTransaction().begin();
                    Object obj = itemTable.getValue();
                    MutantItem thing = (MutantItem) obj;
                    thing = em.find(MutantItem.class, thing.getId());
                    em.remove(thing);
                    em.getTransaction().commit();

                    itemTable.removeItem(obj);
                } catch (Exception e) {
                    notifyError("Failed", "What did you do?!...", e,null);
                }

            }
        }
    }

    @Override
    public void valueChange(ValueChangeEvent event) {
        Object eventObject = event.getProperty().getValue();
		itemTable.setWidth("100%");
        if (eventObject instanceof MutantItem) {
            MutantItem item = (MutantItem) eventObject;
            thePanel.setContent(new ItemView(item));
        } else {
            thePanel.setContent(new ItemView());
        }

    }
}
