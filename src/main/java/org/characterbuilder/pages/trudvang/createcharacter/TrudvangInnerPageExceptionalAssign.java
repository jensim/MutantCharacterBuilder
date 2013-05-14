package org.characterbuilder.pages.trudvang.createcharacter;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.TrudvangElaborationFree;
import org.characterbuilder.persist.entity.TrudvangExceptional;
import org.characterbuilder.persist.entity.TrudvangExceptionalLevel;
import org.characterbuilder.persist.entity.TrudvangMod;

/**
 * TrudvangInnerPageExceptionalAssign
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public final class TrudvangInnerPageExceptionalAssign extends VerticalLayout {

	private JPAContainer<TrudvangExceptional> container = null;
	protected ExcepPanel pos1 = null,
			  pos2 = null,
			  neg = null;

	public TrudvangInnerPageExceptionalAssign() {

		container = JPAContainerFactory.make(TrudvangExceptional.class,
				  ThePersister.ENTITY_MANAGER_NAME);
		pos1 = new ExcepPanel(true);
		pos2 = new ExcepPanel(true);
		neg = new ExcepPanel(false);

		addComponent(new Label("<h2>Exceptionella karaktärsdrag</h2>", ContentMode.HTML));
		addComponent(pos1);
		addComponent(pos2);
		addComponent(neg);
	}

	protected class ExcepPanel extends Panel {

		private final VerticalLayout topVertLayout = new VerticalLayout();
		private NativeSelect excepSelect = null,
				  levelSelect = null;
		private JPAContainer<TrudvangExceptionalLevel> levelContainer = null;
		private final Label excepLabel = new Label("", ContentMode.HTML),
				  levelLabel = new Label("", ContentMode.HTML);
		private boolean positive = true;

		public ExcepPanel(boolean positive) {
			this.positive = positive;
			setContent(topVertLayout);

			levelContainer = JPAContainerFactory.make(TrudvangExceptionalLevel.class,
					  ThePersister.ENTITY_MANAGER_NAME);

			setupSelects();

			topVertLayout.addComponent(new Label(positive
					  ? "<h3>Positivt karaktärsdrag</h3>"
					  : "<h3>Negativt karaktärsdrag</h3>",
					  ContentMode.HTML));

			topVertLayout.addComponent(excepSelect);
			topVertLayout.addComponent(excepLabel);
			topVertLayout.addComponent(levelSelect);
			topVertLayout.addComponent(levelLabel);

			levelSelect.setVisible(false);
			levelLabel.setVisible(false);
		}

		public final TrudvangExceptionalLevel getTrudvangExcepLevel() {
			try {
				return levelContainer.getItem(levelSelect.getValue()).getEntity();
			} catch (Exception ex) {
				return null;
			}
		}

		private void setupSelects() {
			excepSelect = new NativeSelect(null, container);
			excepSelect.select(container.getItemIds().iterator().next());

			levelSelect = new NativeSelect(null, levelContainer);
			levelSelect.select(levelContainer.getItemIds().iterator().next());
			for (NativeSelect select : new NativeSelect[]{excepSelect, levelSelect}) {
				select.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ITEM);
				//select.setItemCaptionPropertyId("name");
				select.setNullSelectionAllowed(false);
				select.setImmediate(true);
			}
			excepSelect.addValueChangeListener(new Property.ValueChangeListener() {
				@Override
				public void valueChange(ValueChangeEvent event) {
					Object obj = excepSelect.getValue();
					if (obj != null) {
						TrudvangExceptional exep = container.getItem(obj).getEntity();
						levelContainer.removeAllContainerFilters();
						levelContainer.addContainerFilter(new Compare.Equal("exceptionalId", exep));
						if (positive) {
							levelContainer.addContainerFilter(new Compare.Greater("value", 0));
						} else {
							levelContainer.addContainerFilter(new Compare.Less("value", 0));
						}

						StringBuilder sb = new StringBuilder();
						sb.append("Bok: ").append(exep.getBookId()).append("<br/>");
						sb.append("Beskrivning: ").append(exep.getDescription()).append("<br/>");
						excepLabel.setValue(sb.toString());

						levelSelect.select(levelContainer.getItemIds().iterator().next());
						levelSelect.setVisible(true);
						levelLabel.setVisible(true);
					}
				}
			});
			levelSelect.addValueChangeListener(new Property.ValueChangeListener() {
				@Override
				public void valueChange(ValueChangeEvent event) {
					Object obj = levelSelect.getValue();
					if (obj != null) {
						TrudvangExceptionalLevel exLevel = levelContainer.getItem(obj).getEntity();
						StringBuilder sb = new StringBuilder();
						sb.append("Beskrivning: ").append(exLevel.getDescription()).append("<br/>");
						sb.append("Värde: ").append(exLevel.getValue()).append("<br/>");
						try {
							for (TrudvangMod mod : exLevel.getTrudvangModList()) {
								sb.append("Mod: ").append(mod.getTypeId().getName());
								if (mod.getSkill() != null) {
									sb.append(" - ").append(mod.getSkill().getName());
								}
								sb.append(", Värde: ").append(mod.getValue()).append("<br/>");
							}
							for (TrudvangElaborationFree free : exLevel.getFreeElaborations()) {
								sb.append("Fördjupning på köpet: ");
								sb.append(free.getElaboration().getName());
								sb.append("<br/>");
							}
						} catch (Exception exception) {
						}
						levelLabel.setValue(sb.toString());
					} else {
						levelLabel.setVisible(false);
						levelSelect.setVisible(false);
					}
				}
			});
		}
	}
}
