package org.characterbuilder.pages.trudvang.admin.skill;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import javax.persistence.EntityManager;
import org.characterbuilder.defaults.MyHorizontalLayout;
import org.characterbuilder.defaults.MyTextArea;
import org.characterbuilder.defaults.MyVerticalLayout;
import org.characterbuilder.pages.abst.trudvang.admin.TrudvangManage;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.TrudvangElaboration;
import org.characterbuilder.persist.entity.TrudvangPower;
import org.characterbuilder.persist.entity.TrudvangReligion;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
public class TrudvangManagePower extends TrudvangManage {

	private JPAContainer<TrudvangPower> container = null;
	private MyHorizontalLayout horLayout = new MyHorizontalLayout();
	private MyVerticalLayout editLayout = new MyVerticalLayout();
	private TrudvangElaboration elaboration;

	public TrudvangManagePower(TrudvangElaboration elaboration) {
		super();
		this.elaboration = elaboration;
		container = JPAContainerFactory.make(TrudvangPower.class, ThePersister.getEntityManager());
		container.addContainerFilter(new Compare.Equal("elaborationId", elaboration));
		table = new Table(null, container);
		fixTableSettings(container.getContainerPropertyIds(), new String[]{"name"});

		table.addListener(new TableListener());

		addComponent(horLayout);
		horLayout.addComponent(table);
		horLayout.addComponent(editLayout);
		editLayout.addComponent(new PowerLayout());
	}

	public TrudvangPower getSelectedPower() {
		try {
			return container.getItem(table.getValue()).getEntity();
		} catch (Exception ex) {
			return null;
		}
	}

	private class TableListener implements Property.ValueChangeListener {

		@Override
		public void valueChange(ValueChangeEvent event) {
			editLayout.removeAllComponents();
			TrudvangPower selectedPower = getSelectedPower();
			if (selectedPower == null) {
				editLayout.addComponent(new PowerLayout());
			} else {
				editLayout.addComponent(new PowerLayout(selectedPower));
			}
		}
	}

	private class PowerLayout extends Panel {

		private JPAContainer<TrudvangReligion> container = null;
		private TextField name;
		private MyTextArea description;
		private NativeSelect religion;
		private Button addButt = new Button("Add", new Adder());
		private Button editButt = new Button("Edit", new Editer());
		private Button removeButt = new Button("Remove", new Remover());
		private HorizontalLayout buttonLayout = new HorizontalLayout();
		private VerticalLayout panelLayout = new VerticalLayout();

		public PowerLayout() {
			super();
			buildUI();
			buttonLayout.addComponent(addButt);
		}

		public PowerLayout(TrudvangPower power) {
			super();
			buildUI();
			TrudvangReligion reli = power.getReligionId();
			religion.select(reli);
			buttonLayout.addComponent(editButt);
			buttonLayout.addComponent(removeButt);
		}

		private void buildUI() {
			container = JPAContainerFactory.make(TrudvangReligion.class, ThePersister.getEntityManager());
			name = new TextField("Name");
			description = new MyTextArea("Description");
			religion = new NativeSelect("Religion", container);
			panelLayout.addComponent(name);
			panelLayout.addComponent(description);
			panelLayout.addComponent(religion);
			panelLayout.addComponent(buttonLayout);
			setContent(panelLayout);
		}

		private class Adder implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				EntityManager em = ThePersister.getEntityManager();
				try {
					em.getTransaction().begin();
					TrudvangPower newPower = new TrudvangPower();
					TrudvangReligion reli = ((TrudvangReligion) (religion.getValue()));
					reli = em.find(TrudvangReligion.class, reli.getId());
					newPower.setReligionId(reli);
					newPower.setName(name.getValue().toString());
					newPower.setDescription(description.getValue().toString());
					em.persist(newPower);
					em.getTransaction().commit();

					container.refresh();
					table.select(newPower);
				} catch (Exception ex) {
					if (em.getTransaction().isActive()) {
						em.getTransaction().rollback();
					}
				}
				container.refresh();
			}
		}

		private class Editer implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				EntityManager em = ThePersister.getEntityManager();
				try {
					em.getTransaction().begin();
					TrudvangPower power = getSelectedPower();
					power = em.find(TrudvangPower.class, power.getId());
					TrudvangReligion reli = ((TrudvangReligion) (religion.getValue()));
					reli = em.find(TrudvangReligion.class, reli.getId());
					power.setReligionId(reli);
					power.setName(name.getValue().toString());
					power.setDescription(description.getValue().toString());
					em.getTransaction().commit();

					container.refresh();
				} catch (Exception ex) {
					if (em.getTransaction().isActive()) {
						em.getTransaction().rollback();
					}
				}
				container.refresh();
			}
		}

		private class Remover implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				EntityManager em = ThePersister.getEntityManager();
				try {
					em.getTransaction().begin();
					TrudvangPower power = getSelectedPower();
					power = em.find(TrudvangPower.class, power.getId());
					em.remove(power);
					em.getTransaction().commit();

					container.refresh();
					table.select(null);
				} catch (Exception ex) {
					if (em.getTransaction().isActive()) {
						em.getTransaction().rollback();
					}
				}
				container.refresh();
			}
		}
	}
}
