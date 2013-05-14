package org.characterbuilder.pages.mutant.admin;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import javax.persistence.EntityManager;
import org.characterbuilder.defaults.MyTextArea;
import org.characterbuilder.pages.abst.WorkspaceLayout;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.MutantBaseStat;
import org.characterbuilder.persist.entity.MutantSkill;

/**
 * 
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@SuppressWarnings("serial")
public class MutantManageSkill extends WorkspaceLayout implements ValueChangeListener {

    private Panel thePanel = new Panel();
    private Table skillTable = new Table();
    private String[] headerNames = new String[]{
        "Färdighet",
        "Grundegenskap",
        "Naturlig"
    };

    public MutantManageSkill() {

        removeAllComponents();

        skillTable.setMultiSelect(false);
        skillTable.setNullSelectionAllowed(true);
        skillTable.setColumnReorderingAllowed(false);
        skillTable.setColumnCollapsingAllowed(false);

        skillTable.addListener(this);
        skillTable.setImmediate(true);
        skillTable.setSelectable(true);
        for (String name : headerNames) {
            skillTable.addContainerProperty(name, String.class, null);
        }

        addComponent(skillTable);
        //POPULATE ABILITIES
        EntityManager em = ThePersister.getEntityManager();
        try {
            List<MutantSkill> abilityList = em.createQuery(
                    "SELECT r FROM MutantSkill r",
                    MutantSkill.class).getResultList();

            for (MutantSkill ma : abilityList) {
                addSkillToTabel(ma);
            }
        } catch (Exception e) {
            notifyError("Failed", "Dont do it!", e,null);
        }

        addComponent(thePanel);
        thePanel.setContent(new AbilityLayout());
    }

    private void addSkillToTabel(MutantSkill thing) {
        String[] abilityData = new String[]{
            thing.getName(),
            thing.getMutantBaseStat().getName(),
            thing.getNaturalSkill().toString()
        };

        skillTable.addItem(abilityData, thing);
    }

    private class AbilityLayout extends VerticalLayout {

        MutantSkill skill = null;
        TextField nameField = new TextField("Namn", "");
        MyTextArea descriptionField = new MyTextArea("Beskrivning", "");
        Select baseStat = new Select("GrundEgenskap");
        CheckBox natural = new CheckBox("Färdighet");
        Button addButton = new Button("Lägg till färdighet..");
        Button comitButton = new Button("Spara färdighet..");
        Button removeButton = new Button("Ta bort färdighet..");

        public AbilityLayout() {
            buildUI();
            addComponent(addButton);
            addButton.addListener(new AddListener());
        }

        public AbilityLayout(MutantSkill skill) {
            this.skill = skill;
            buildUI();

            nameField.setValue(skill.getName());
            natural.setValue(skill.getNaturalSkill());
            descriptionField.setValue(skill.getDescription());

            addComponent(removeButton);
            removeButton.addListener(new Remover());
            addComponent(comitButton);
            comitButton.addListener(new ComitListener());
        }

        private void buildUI() {
            removeAllComponents();

            addComponent(nameField);
            addComponent(baseStat);
            addComponent(natural);
            addComponent(descriptionField);

            baseStat.setMultiSelect(false);
            baseStat.setNullSelectionAllowed(false);

            EntityManager em = ThePersister.getEntityManager();
            try {
                List<MutantBaseStat> statList = em.createQuery(
                        "SELECT r "
                        + "FROM MutantBaseStat r",
                        MutantBaseStat.class)
                        .getResultList();
                for (MutantBaseStat stat : statList) {
                    baseStat.addItem(stat);
                    if (skill != null) {
                        if (skill.getMutantBaseStat() != null) {
                            if (skill.getMutantBaseStat().getId() == stat.getId()) {
                                baseStat.select(stat);
                            }
                        }
                    } 

                }
            } catch (Exception e) {
                notifyError("Failed", "Dont do it!", e, null);
            }
        }

        private class ComitListener implements Button.ClickListener {

			  @Override
            public void buttonClick(ClickEvent event) {

                EntityManager em = ThePersister.getEntityManager();
                try {
                    em.getTransaction().begin();

                    skill.setName(nameField.getValue().toString());
                    skill.setDescription(descriptionField.getValue().toString());
                    skill.setNaturalSkill(natural.booleanValue());

                    Object classobj = baseStat.getValue();
                    if (classobj instanceof MutantBaseStat) {
                        MutantBaseStat mClass = (MutantBaseStat) classobj;
                        skill.setMutantBaseStat(mClass);

                        em.merge(skill);
                        em.getTransaction().commit();
                        notifyInfo("Skill", skill.getName()+" edited", null, null);
                    } else {
                        notifyError("Skill", "Failed Base stat choise for skill "
								+skill.getName(), null, null);
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

                    MutantSkill thing = new MutantSkill();
                    thing.setName(nameField.getValue().toString());
                    thing.setDescription(descriptionField.getValue().toString());

                    Object classobj = baseStat.getValue();
                    if (classobj instanceof MutantBaseStat) {
                        MutantBaseStat mClass = (MutantBaseStat) classobj;
                        thing.setMutantBaseStat(mClass);

                        em.persist(thing);
                        em.getTransaction().commit();

                        addSkillToTabel(thing);

                        notifyInfo("Skill", "added", null, null);
                    } else {
                        notifyError("Failed", "Base stat choise", null, null);
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
                    Object obj = skillTable.getValue();
                    MutantSkill thing = (MutantSkill) obj;
                    thing = em.find(MutantSkill.class, thing.getId());
                    em.remove(thing);
                    em.getTransaction().commit();

                    skillTable.removeItem(obj);
					notifyInfo("Edit weapon", "Success", null, null);
                } catch (Exception e) {
                    notifyError("Failed", "What did you do?!...", e, null);
                }

            }
        }
    }

    @Override
    public void valueChange(ValueChangeEvent event) {
        Object eventObject = event.getProperty().getValue();
		skillTable.setWidth("100%");
        if (eventObject instanceof MutantSkill) {
            MutantSkill thing = (MutantSkill) eventObject;

            //TODO: Stuff
            thePanel.setContent(new AbilityLayout(thing));
        } else {
            thePanel.setContent(new AbilityLayout());
        }
    }
}
