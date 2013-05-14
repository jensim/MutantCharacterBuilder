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
import org.characterbuilder.defaults.ThousandsField;
import org.characterbuilder.pages.abst.WorkspaceLayout;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.MutantArmor;
import org.characterbuilder.persist.entity.MutantArmorBodypart;

/**
 * 
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@SuppressWarnings("serial")
public class MutantManageArmor extends WorkspaceLayout implements ValueChangeListener {

    private Panel thePanel = new Panel();
    private Table armorTable = new Table();
    private String[] headerNames = new String[]{
        "Rustning",
        "ABS",
        "BEG",
        "Pris",
        "Vikt"
    };

    public MutantManageArmor() {

        removeAllComponents();

        armorTable.setMultiSelect(false);
        armorTable.setNullSelectionAllowed(true);
        armorTable.setColumnReorderingAllowed(false);
        armorTable.setColumnCollapsingAllowed(false);

        armorTable.addListener(this);
        armorTable.setImmediate(true);
        armorTable.setSelectable(true);
        for (String name : headerNames) {
            armorTable.addContainerProperty(name, String.class, null);
        }

        addComponent(armorTable);
        //POPULATE WEAPONS
        EntityManager em = ThePersister.getEntityManager();
        try {
            List<MutantArmor> itemList = em.createQuery(
                    "SELECT r FROM MutantArmor r",
                    MutantArmor.class).getResultList();

            for (MutantArmor ma : itemList) {
                addItemToTabel(ma);
            }
        } catch (Exception e) {
            notifyError("Mutant Armor", "Unable to load..", e,null);
        }

        addComponent(thePanel);
        thePanel.setContent(new ArmorView());
    }

    private void addItemToTabel(MutantArmor armor) {
        String[] itemData = new String[]{
            armor.getName(),
            armor.getAbs().toString(),
            armor.getBeg().toString(),
            (armor.getPrice() / 1000.0) + " krediter",
            (armor.getWeight() / 1000.0) + " kg"
        };

        armorTable.addItem(itemData, armor);

    }

    private class ArmorView extends VerticalLayout {

        MutantArmor armor = null;
        TextField nameField = new TextField("Namn", "");
        TextField absField = new TextField("ABS", "0");
        TextField begField = new TextField("BEG", "0");
        ThousandsField weightField = new ThousandsField("Vikt, kg", "1,0");
        ThousandsField priceField = new ThousandsField("Värde, kr", "1,0");
        MyTextArea descriptionArea = new MyTextArea("Beskrivning", "");
        ComboBox bodypartSelect = new ComboBox("Kroppsdel");
        Button addButton = new Button("Lägg till föremål");
        Button saveButton = new Button("Spara föremål");
        Button removeButton = new Button("Ta bort föremål");

        /**
         * When no item in the itemTabel is marked
         */
        ArmorView() {
            buildUI();

            addComponent(addButton);
            addButton.addListener(new Adder());
        }

        /**
         * When marking an item in the itemTabel
         *
         * @param ittem
         */
        ArmorView(MutantArmor ittem) {
            armor = ittem;
            buildUI();

            nameField.setValue(armor.getName());
            absField.setValue(armor.getAbs().toString());
            begField.setValue(armor.getBeg().toString());
			String weight = armor.getWeight()/1000+","+armor.getWeight()%1000;
            weightField.setValue(weight);
			String cost = armor.getPrice()/1000+","+armor.getPrice()%1000;
            priceField.setValue(cost);
            descriptionArea.setValue(armor.getDescription());

            addComponent(removeButton);
            removeButton.addListener(new Remover());
            addComponent(saveButton);
            saveButton.addListener(new Saver());
        }

        private void buildUI() {
            addComponent(nameField);
            addComponent(absField);
            addComponent(begField);
            addComponent(weightField);
            addComponent(priceField);
            addComponent(descriptionArea);

            addComponent(bodypartSelect);

            bodypartSelect.setMultiSelect(false);
            bodypartSelect.setNullSelectionAllowed(false);

            EntityManager em = ThePersister.getEntityManager();
            try {
                List<MutantArmorBodypart> bodypartList = em.createQuery(
                        "SELECT r FROM MutantArmorBodypart r",
                        MutantArmorBodypart.class).getResultList();
                for (MutantArmorBodypart bodypartSingle : bodypartList) {
                    bodypartSelect.addItem(bodypartSingle);

                    if (armor != null) {
                        MutantArmorBodypart armorBodypart = armor.getMutantArmorBodypart();
                        if (bodypartSingle.getId() == armorBodypart.getId()) {
                            bodypartSelect.select(bodypartSingle);
                        }
                    }
                }
            } catch (Exception e) {
                notifyError("Mutant Armor", "Unable to load..", e,null);
            }
        }

        private class Adder implements Button.ClickListener {

            @Override
            public void buttonClick(ClickEvent event) {
                EntityManager em = ThePersister.getEntityManager();
                try {
                    em.getTransaction().begin();
                    MutantArmor newArmor = new MutantArmor();

                    newArmor.setName(nameField.getValue().toString());
                    newArmor.setAbs(Integer.parseInt(absField.getValue().toString()));
                    newArmor.setBeg(Integer.parseInt(begField.getValue().toString()));
                    newArmor.setWeight(weightField.getThousands());
                    newArmor.setPrice(priceField.getThousands());
                    newArmor.setDescription(descriptionArea.getValue().toString());

                    Object obj = bodypartSelect.getValue();
                    if (obj instanceof MutantArmorBodypart) {
                        MutantArmorBodypart selectedPart = (MutantArmorBodypart) obj;
                        newArmor.setMutantArmorBodypart(selectedPart);

                        em.persist(newArmor);

                        em.getTransaction().commit();
                        addItemToTabel(newArmor);

                        notifyInfo("new item", "saved",null,null);
                    } else {
                        notifyError("Failed", "body part selection",null,null);
                    }

                } catch (Exception e) {
                    notifyError("Failed", "saving armor..", e,null);
                }

            }
        }

        private class Saver implements Button.ClickListener {

            @Override
            public void buttonClick(ClickEvent event) {
                EntityManager em = ThePersister.getEntityManager();
                try {
                    em.getTransaction().begin();

                    armor.setName(nameField.getValue().toString());
                    armor.setAbs(Integer.parseInt(absField.getValue().toString()));
                    armor.setBeg(Integer.parseInt(begField.getValue().toString()));
                    armor.setWeight(weightField.getThousands());
                    armor.setPrice(priceField.getThousands());
                    armor.setDescription(descriptionArea.getValue().toString());

                    Object obj = bodypartSelect.getValue();
                    if (obj instanceof MutantArmorBodypart) {
                        MutantArmorBodypart selectedPart = (MutantArmorBodypart) obj;
                        armor.setMutantArmorBodypart(selectedPart);

                        em.merge(armor);

                        em.getTransaction().commit();

                        notifyInfo("save item", "sucess",null,null);
                    } else {
                        notifyError("Failed", "body part selection",null,null);
                    }

                } catch (Exception e) {
                    notifyError("Failed", "saving armor", e,null);
                }

            }
        }

        private class Remover implements Button.ClickListener {

            @Override
            public void buttonClick(ClickEvent event) {
                EntityManager em = ThePersister.getEntityManager();
                try {
                    em.getTransaction().begin();
                    Object obj = armorTable.getValue();
                    MutantArmor armour = (MutantArmor) obj;
                    armour = em.find(MutantArmor.class, armour.getId());
                    em.remove(armour);
                    em.getTransaction().commit();

                    armorTable.removeItem(obj);
                } catch (Exception e) {
                    notifyError("Failed", "What did you do?!...", e,null);
                }

            }
        }
    }

    @Override
    public void valueChange(ValueChangeEvent event) {
        Object eventObject = event.getProperty().getValue();
		armorTable.setWidth("100%");
        if (eventObject instanceof MutantArmor) {
            MutantArmor item = (MutantArmor) eventObject;
            thePanel.setContent(new ArmorView(item));
        } else {
            thePanel.setContent(new ArmorView());
        }
    }
}
