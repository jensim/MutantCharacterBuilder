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
import org.characterbuilder.persist.entity.TrudvangVitner;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
public class TrudvangManageVitner extends TrudvangManage {

	private JPAContainer<TrudvangVitner> container = null;
	private MyHorizontalLayout horLayout = new MyHorizontalLayout();
	private MyVerticalLayout layout = new MyVerticalLayout();
	private TrudvangElaboration elaboration;

	public TrudvangManageVitner(TrudvangElaboration elaboration) {
		super();
		this.elaboration = elaboration;
		container = JPAContainerFactory.make(TrudvangVitner.class, ThePersister.getEntityManager());
		container.addContainerFilter(new Compare.Equal("elaborationId", elaboration));
		table = new Table(null, container);
		fixTableSettings(container.getContainerPropertyIds(), new String[]{"name"});

		table.addListener(new TableListener());
		addComponent(horLayout);
		horLayout.addComponent(table);
		horLayout.addComponent(layout);
		layout.addComponent(new VitnerLayout());
	}

	public TrudvangVitner getSelectedVitner() {
		try {
			return container.getItem(table.getValue()).getEntity();
		} catch (Exception ex) {
			return null;
		}
	}

	private class TableListener implements Property.ValueChangeListener {

		@Override
		public void valueChange(ValueChangeEvent event) {
			layout.removeAllComponents();
			TrudvangVitner selectedVitner = getSelectedVitner();
			if (selectedVitner == null) {
				layout.addComponent(new VitnerLayout());
			} else {
				layout.addComponent(new VitnerLayout(selectedVitner));
			}
		}
	}

	private class VitnerLayout extends Panel {

		private TextField name;
		private MyTextArea description;
		private Button addButt = new Button("Add", new Adder());
		private Button editButt = new Button("Edit", new Editer());
		private Button removeButt = new Button("Remove", new Remover());
		private HorizontalLayout buttonLayout = new HorizontalLayout();
		private VerticalLayout panelLayout = new VerticalLayout();

		public VitnerLayout() {
			super();
			buildUI();
			buttonLayout.addComponent(addButt);
		}

		public VitnerLayout(TrudvangVitner power) {
			super();
			buildUI();
			buttonLayout.addComponent(editButt);
			buttonLayout.addComponent(removeButt);
		}

		private void buildUI() {

			name = new TextField("Name");
			description = new MyTextArea("Description");
			panelLayout.addComponent(name);
			panelLayout.addComponent(description);
			panelLayout.addComponent(buttonLayout);
			setContent(panelLayout);
		}

		private class Adder implements Button.ClickListener {

			@Override
			public void buttonClick(ClickEvent event) {
				EntityManager em = ThePersister.getEntityManager();
				try {
					em.getTransaction().begin();
					TrudvangVitner newVitner = new TrudvangVitner();
					newVitner.setName(name.getValue().toString());
					newVitner.setDescription(description.getValue().toString());
					em.persist(newVitner);
					em.getTransaction().commit();
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
					TrudvangVitner power = getSelectedVitner();
					power = em.find(TrudvangVitner.class, power.getId());
					power.setName(name.getValue().toString());
					power.setDescription(description.getValue().toString());

					em.getTransaction().commit();
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
					TrudvangVitner power = getSelectedVitner();
					power = em.find(TrudvangVitner.class, power.getId());
					em.remove(power);
					em.getTransaction().commit();
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
