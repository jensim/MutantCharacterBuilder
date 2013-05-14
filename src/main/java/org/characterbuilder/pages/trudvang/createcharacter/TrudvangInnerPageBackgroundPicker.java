package org.characterbuilder.pages.trudvang.createcharacter;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;
import org.characterbuilder.defaults.MyHorizontalLayout;
import org.characterbuilder.defaults.MyVerticalLayout;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.TrudvangArktype;
import org.characterbuilder.persist.entity.TrudvangPeople;
import org.characterbuilder.persist.entity.TrudvangSkillStart;
import org.characterbuilder.persist.entity.TrudvangUpbringing;

/**
 * TrudvangInnerPageBackgroundPicker
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public final class TrudvangInnerPageBackgroundPicker extends MyVerticalLayout {

	private JPAContainer<TrudvangArktype> arkContainer = null;
	private JPAContainer<TrudvangPeople> peoContainer = null;
	private JPAContainer<TrudvangUpbringing> upbContainer = null;
	private ListSelect arkSelect = null,
			  sexSelect = null,
			  upbSelect = null;
	private final Table peoTable;
	private MyVerticalLayout sexLayout = new MyVerticalLayout(),
			  arkLayout = new MyVerticalLayout(),
			  peoLayout = new MyVerticalLayout(),
			  upbLayout = new MyVerticalLayout();

	public TrudvangInnerPageBackgroundPicker() {
		//SEX
		sexSelect = new ListSelect("K�n");
		sexSelect.addItem("Man");
		sexSelect.addItem("Kvinna");
		sexSelect.setRows(2);
		sexSelect.setMultiSelect(false);
		sexSelect.setNullSelectionAllowed(true);
		sexSelect.setImmediate(true);
		sexSelect.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				String sex = null;
				String notSex = null;
				try {
					sex = event.getProperty().toString();
					sexLayout.removeAllComponents();
					if(sex.equalsIgnoreCase("Man")){
						notSex = "Female";
						sexLayout.addComponent(new Label(
								  "<p>Man</p><p>"
								  + "M�n �r rent fysiskt oftast st�rre �n kvinnor,<br/>"
								  + "detta �r inte alltid fallet f�r alla folk och<br/>"
								  + "raser, men det �r ovanligt att kvinnorna �r st�rre<br/>"
								  + "�n m�nnen.</p><p>"
								  + "Sammh�llsbilden och maktf�rdelningen m�n och kvinnor<br/>"
								  + "emellan �r olika f�r olika folkslag, men genomg�ende<br/>"
								  + "�r det m�nnen som styr samh�llet.</p>", ContentMode.HTML));
					}else if(sex.equalsIgnoreCase("Kvinna")){
						notSex = "Male";
						sexLayout.addComponent(new Label(
								  "<p>Kvinna</p><p>"
								  + "Kvinnors �den �r inte lika h�rt spunna som m�nnens<br/>"
								  + "och detta leder till att alla kvinnor har +1 p� sitt<br/>"
								  + "t�rningsslag f�r att rulla fram Raud.</p><p>"
								  + "Sammh�llsbilden och maktf�rdelningen m�n och kvinnor<br/>"
								  + "emellan �r olika f�r olika folkslag, men genomg�ende<br/>"
								  + "�r det m�nnen som styr samh�llet.</p>", ContentMode.HTML));
					}
				} catch (Exception ex) {
				}
				for (Object id : peoTable.getVisibleColumns()) {
					peoTable.setColumnCollapsed(id, false);
					
					if(id instanceof String 
							  && notSex != null 
							  && !notSex.equals("") 
							  && ((String)id).contains(notSex)){
						peoTable.setColumnCollapsed(id, true);
					}
				}
				peoTable.setSizeUndefined();
			}
		});


		//ARKTYPE
		arkContainer = JPAContainerFactory.make(TrudvangArktype.class,
				  ThePersister.ENTITY_MANAGER_NAME);
		arkSelect = new ListSelect("Arketyp", arkContainer);
		arkSelect.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		arkSelect.setItemCaptionPropertyId("name");
		arkSelect.addListener(new ArkListener());
		fixSelect(arkSelect, arkContainer);
		//PEOPLE
		peoContainer = JPAContainerFactory.make(TrudvangPeople.class,
				  ThePersister.ENTITY_MANAGER_NAME);
		peoTable = new Table("Folkslag", peoContainer);
		peoTable.setColumnCollapsingAllowed(true);
		peoTable.setSelectable(true);
		peoTable.setMultiSelect(false);
		peoTable.setNullSelectionAllowed(false);
		peoTable.addListener(new PeoListener());
		peoTable.setImmediate(true);
		peoTable.select(peoContainer.getItemIds().iterator().next());
		peoTable.setVisibleColumns(new String[]{"name", "movement",
					  "tkpFemale", "tkpMale",
					  "ageMaxFemale", "ageMaxMale",
					  "weightMinMale", "weightMaxMale",
					  "weightMinFemale", "weightMaxFemale",
					  "lenghtMinMale", "lenghtMaxMale",
					  "lenghtMinFemale", "lenghtMaxFemale"});
		peoTable.setColumnHeader("name", "Folkslag");
		peoTable.setColumnHeader("movement", "F�rflyttning");
		peoTable.setColumnHeader("tkpFemale", "TKP-grund Kvinnor");
		peoTable.setColumnHeader("tkpMale", "TKP-grun M�n");
		peoTable.setColumnHeader("ageMaxFemale", "Max�lder Kvinnor");
		peoTable.setColumnHeader("ageMaxMale", "Max�lder M�n");
		peoTable.setColumnHeader("weightMinMale", "Minvikt M�n");
		peoTable.setColumnHeader("weightMaxMale", "Maxvikt M�n");
		peoTable.setColumnHeader("weightMinFemale", "Minvikt Kvinnor"); 
		peoTable.setColumnHeader("weightMaxFemale", "Maxvikt Kvinnor");
		peoTable.setColumnHeader("lenghtMinMale", "Minl�ngd M�n");
		peoTable.setColumnHeader("lenghtMaxMale", "Maxl�ngd M�n");
		peoTable.setColumnHeader("lenghtMinFemale", "Minl�ngd Kvinnor"); 
		peoTable.setColumnHeader("lenghtMaxFemale", "Maxl�ngd Kvinnor");
		for (Object id : peoTable.getVisibleColumns()) {
			peoTable.setColumnWidth(id, 90);
		}
		
		//fixSelect(peoSelect, peoContainer);
		//UPBRINGING
		upbContainer = JPAContainerFactory.make(TrudvangUpbringing.class,
				  ThePersister.ENTITY_MANAGER_NAME);
		upbSelect = new ListSelect("Uppv�xtmilj�", upbContainer);
		upbSelect.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		upbSelect.setItemCaptionPropertyId("name");
		upbSelect.addListener(new UpbListener());
		fixSelect(upbSelect, upbContainer);

		addComponent(new Label("<h2>Bakgrund</h2>", ContentMode.HTML));
		addComponent(new MyHorizontalLayout(sexSelect, sexLayout));
		addComponent(new MyHorizontalLayout(arkSelect, arkLayout));
		//addComponent(new MyHorizontalLayout(peoSelect, peoLayout));
		addComponent(new MyVerticalLayout(peoTable, peoLayout));
		addComponent(new MyHorizontalLayout(upbSelect, upbLayout));
	}

	private void fixSelect(ListSelect sel, JPAContainer<?> container) {
		sel.setMultiSelect(false);
		sel.setNullSelectionAllowed(false);
		sel.setImmediate(true);
		try {
			sel.select(container.getItemIds().iterator().next());
		} catch (Exception ex) {
		}
	}

	private class ArkListener implements Property.ValueChangeListener {

		@Override
		public void valueChange(ValueChangeEvent event) {
			arkLayout.removeAllComponents();
			TrudvangArktype ark = getSelectedArktype();
			String arkName = ark.getName();
			arkLayout.addComponent(new Label(
					  "<b><u>Namn</u></b>: " + arkName, ContentMode.HTML));
			arkLayout.addComponent(new Label(
					  "<b><u>Beskrivning</u></b>: " + ark.getDescription(), ContentMode.HTML));
			StringBuilder sb = new StringBuilder("<p><b><u>F�rdigheter</u></b><br/>"
					  + "(Rekommenderade f�rdigheter att l�gga erf i f�r en<br/>"
					  + "oerfaren, nyskapad karakt�r. Om du vill l�gga po�ng<br/>"
					  + "i andra f�rdigheter, �verv�g en annan arketyp eller<br/>"
					  + "ta upp det med din spelledare. Kom ih�g att du f�r<br/>"
					  + "f�rdighetspo�ng fr�n folkslag och bakgrundsmilj� som<br/>"
					  + "du f�r l�gga d�r dom indikerar.)</p><p><ul>");
			for (TrudvangSkillStart skill : ark.getTrudvangSkillStartList()) {
				if (skill.getSkillId() != null) {
					sb.append("<li>").append(skill.getSkillId().getName()).append("</li>");
				} else if (skill.getGroup() != null) {
					sb.append("<li>").append(skill.getGroup().getName()).append("</li>");
				}
			}
			sb.append("</ul></p>");
			arkLayout.addComponent(new Label(sb.toString(), ContentMode.HTML));
		}
	}

	private class PeoListener implements Property.ValueChangeListener {

		@Override
		public void valueChange(ValueChangeEvent event) {
			peoLayout.removeAllComponents();
			TrudvangPeople peo = getSelectedPeople();
			peoLayout.addComponent(new Label(
					  "<b><u>Namn</u></b>: " + peo.getName(), ContentMode.HTML));

			peoLayout.addComponent(new Label(
					  "<b><u>Beskrivning</u></b>: " + peo.getDescription(),
					  ContentMode.HTML));
			StringBuilder sb = new StringBuilder("<p><b><u>F�rdigheter</u></b><br/>"
					  + "(+1T6 f�rdighetsv�rde)</p><p><ul>");
			for (TrudvangSkillStart skill : peo.getTrudvangSkillStartList()) {
				if (skill.getSkillId() != null) {
					sb.append("<li>").append(skill.getSkillId().getName()).append("</li>");
				} else if (skill.getGroup() != null) {
					sb.append("<li>").append(skill.getGroup().getName()).append("</li>");
				}
			}
			sb.append("</ul></p>");
			peoLayout.addComponent(new Label(sb.toString(), ContentMode.HTML));
		}
	}

	private class UpbListener implements Property.ValueChangeListener {

		@Override
		public void valueChange(ValueChangeEvent event) {
			upbLayout.removeAllComponents();
			TrudvangUpbringing upb = getSelectedUpbringing();
			upbLayout.addComponent(new Label(
					  "<b><u>Namn</u></b>: " + upb.getName(), ContentMode.HTML));
			upbLayout.addComponent(new Label(
					  "<b><u>Beskrivning</u></b>: " + upb.getDescription(), ContentMode.HTML));
			StringBuilder sb = new StringBuilder("<p><b><u>F�rdigheter</u></b><br/>"
					  + "(+1T6 f�rdighetsv�rde)</p><p><ul>");
			for (TrudvangSkillStart skill : upb.getTrudvangSkillStartList()) {
				if (skill.getSkillId() != null) {
					sb.append("<li>").append(skill.getSkillId().getName()).append("</li>");
				} else if (skill.getGroup() != null) {
					sb.append("<li>").append(skill.getGroup().getName()).append("</li>");
				}
			}
			sb.append("</ul></p>");
			upbLayout.addComponent(new Label(sb.toString(), ContentMode.HTML));
		}
	}

	protected TrudvangArktype getSelectedArktype() {
		try {
			return arkContainer.getItem(arkSelect.getValue()).getEntity();
		} catch (Exception ex) {
			return null;
		}
	}

	protected TrudvangPeople getSelectedPeople() {
		try {
			return peoContainer.getItem(peoTable.getValue()).getEntity();
		} catch (Exception ex) {
			return null;
		}
	}

	protected TrudvangUpbringing getSelectedUpbringing() {
		try {
			return upbContainer.getItem(upbSelect.getValue()).getEntity();
		} catch (Exception ex) {
			return null;
		}
	}
	
	protected String getSex(){
		Object obj = sexSelect.getValue();
		if(obj != null && obj instanceof String){
			return (String)obj;
		}else{
			return "";
		}
	}
}
