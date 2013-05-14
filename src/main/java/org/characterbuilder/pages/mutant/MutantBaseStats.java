package org.characterbuilder.pages.mutant;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import org.characterbuilder.defaults.MyTextField;
import org.characterbuilder.pages.abst.mutant.MutantCharacterWorkspaceLayout;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.MutantBaseStatCharacter;
import org.characterbuilder.persist.entity.MutantCharacter;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@SuppressWarnings("serial")
public class MutantBaseStats extends MutantCharacterWorkspaceLayout {
	//private static final	long	serialVersionUID	= -4484866743053241956L;

	private VerticalLayout costLayout = new VerticalLayout();
	private ArrayList<StatField> listFields = new ArrayList<>();
	private Button commitStats = new Button("Commit stats");
	private Label gePointLabel = new Label();
	private Label erfPointLabel = new Label();
	private Integer classPoints = new Integer(0);

	public MutantBaseStats(MutantCharacter mc) {
		super(mc);

		gePointLabel.setCaption("GE po�ng spenderade: ");
		gePointLabel.setDescription("Baserat p� statiska po�ngr�kningen f�r klasser i Mutant: Underg�ngens arvtagare.");
		erfPointLabel.setCaption("ERF po�ng spenderade: ");
		erfPointLabel.setDescription("Baserat p� hur m�nga stegs tr�ning du specificerade ovan.");

		setInfo("Grundegenskaper, " + mutantCharacter.getName());
		infoPanel.setContent(costLayout);

		try {
			Integer points = mutantCharacter.getMutantClass().getBaseStatPoints();
			classPoints = points;
		} catch (IllegalStateException ise) {
			notifyError("Mutant base stats", "Unable to retrieve all information", ise, null);
		} catch (NullPointerException npe) {
			notifyError("Mutant base stats", "Unable to retrieve all information", npe, null);
		} catch (Exception e) {
			notifyError("Mutant base stats", "Unable to retrieve all information", e, null);
		}

		for (MutantBaseStatCharacter mbc : mutantCharacter.getMutantBaseStatCharacters()) {
			StatField sf = new StatField(mbc, this);
			sf.addListener(new FieldFocusListener());
			sf.setImmediate(true);
			listFields.add(sf);
			addComponent(sf);
		}

		addComponent(gePointLabel);
		addComponent(erfPointLabel);
		addComponent(commitStats);
		commitStats.addListener(new CommitButtonListener());

		updateCosts();
	}

	protected class StatField extends HorizontalLayout implements FieldEvents.FocusNotifier {

		private static final long serialVersionUID = -379160727583569775L;
		MutantBaseStatCharacter mutantBaseStatCharacter = null;
		MyTextField valueField = new MyTextField(2);
		MyTextField bonusField = new MyTextField(2);
		MyTextField trainedField = new MyTextField(2);
		Label totalLabel = new Label();

		public StatField(MutantBaseStatCharacter mutantBaseStatCharacter,
				  Property.ValueChangeListener listener) {
			super();

			setMargin(true);
			setSpacing(true);

			this.mutantBaseStatCharacter = mutantBaseStatCharacter;

			setCaption(
					  mutantBaseStatCharacter.getMutantBaseStat().getShortName()
					  + ", "
					  + mutantBaseStatCharacter.getMutantBaseStat().getName());

			setDescription(mutantBaseStatCharacter.getMutantBaseStat().getDescripion());
			valueField.setDescription(mutantBaseStatCharacter.getMutantBaseStat().getDescripion());

			valueField.setCaption("base");
			addComponent(valueField);
			valueField.addListener(listener);
			valueField.setImmediate(true);

			bonusField.setCaption("bonus");
			addComponent(bonusField);
			bonusField.addListener(listener);
			bonusField.setImmediate(true);
			bonusField.setDescription("Bonusar kan komma fr�n f�rm�gor<br />"
					  + "(<b>MUT, PSI, OPT, TAL</b>)<br />"
					  + "Po�ng h�r r�knas inte mot GE-kvot");

			trainedField.setCaption("trained");
			addComponent(trainedField);
			trainedField.addListener(listener);
			trainedField.setImmediate(true);
			trainedField.setDescription("<h3>H�ja grundegenskaper</h3>"
					  + "Det g�r att spendera erfarenhet p� att �ka v�rdet i grundegen"
					  + "skaper, men det kostar mycket erf. F�rdelen �r s�klart att se"
					  + "kund�ra egenskaper direkt p�verkas, liksom alla fv som base"
					  + "ras p� den h�jda grundegenskapen. Kostnaden f�r att h�ja en "
					  + "GE �r lika m�nga ERF som 2 x n�sta ge-v�rde. Att �ka smi fr�n "
					  + "14 till 15 kostar med andra ord 30 erf. Robotar m�ste ocks� "
					  + "investera en reservdel per 10 erf som g�r �t. "
					  + "F�rdigheter som baseras p� h�jda ge p�verkas positivt."
					  + "Dela f�rdighetens fv med det gamla v�rdet p� ge. Resultatet "
					  + "�r hur m�nga %-enheter som varje ge-po�ng motsvarar. Ber�k"
					  + "ningen m�ste g�ras separat f�r varje f�rdighet som p�verkas. "
					  + "<b>GE-v�rden kan maximalt h�jas med 5 po�ng fr�n ursprungsv�r"
					  + "det.</b> Varje varelse i mutantv�rlden har f�ruts�ttningar som ald"
					  + "rig helt kan s�ttas ur spel, varf�r en mycket klen person aldrig "
					  + "kan bli Hindenburgs starkaste armbrytare.");

			totalLabel.setCaption("Total GE");
			addComponent(totalLabel);

			valueField.setValue(mutantBaseStatCharacter.getValue().toString());
			bonusField.setValue(mutantBaseStatCharacter.getValueBonus().toString());
			trainedField.setValue(mutantBaseStatCharacter.getValueTrained().toString());
			totalLabel.setValue(mutantBaseStatCharacter.getValueTotal().toString());
		}

		String getValue() {
			return valueField.getValue().toString();
		}

		String getBonus() {
			return bonusField.getValue().toString();
		}

		String getTrained() {
			return trainedField.getValue().toString();
		}

		void updateTotal() {
			try {
				Integer base = Integer.parseInt(valueField.getValue().toString());
				Integer bonus = Integer.parseInt(bonusField.getValue().toString());
				Integer trained = Integer.parseInt(trainedField.getValue().toString());

				totalLabel.setValue((base + bonus + trained) + "");
			} catch (NumberFormatException nfe) {
				notifyError("Mutant base stats", "Number format.", nfe, null);
			} catch (Exception e) {
				notifyError("Mutant base stats", "General exception..", e, null);
			}
		}

		@Override
		public void addFocusListener(FocusListener listener) {
			valueField.addListener(listener);
			bonusField.addListener(listener);
			trainedField.addListener(listener);
		}

		@Override
		public void addListener(FocusListener listener) {
			valueField.addListener(listener);
			bonusField.addListener(listener);
			trainedField.addListener(listener);
		}

		@Override
		public void removeFocusListener(FocusListener listener) {
			valueField.removeListener(listener);
			bonusField.removeListener(listener);
			trainedField.removeListener(listener);
		}

		@Override
		public void removeListener(FocusListener listener) {
			valueField.removeListener(listener);
			bonusField.removeListener(listener);
			trainedField.removeListener(listener);
		}
	}

	private class CommitButtonListener implements Button.ClickListener {

		private static final long serialVersionUID = -5891099466926134913L;

		@Override
		public void buttonClick(ClickEvent event) {
			EntityManager em = ThePersister.getEntityManager();
			try {

				for (StatField field : listFields) {
					MutantBaseStatCharacter baseStat = field.mutantBaseStatCharacter;

					try {
						em.getTransaction().begin();
						Integer value = Integer.parseInt(field.getValue());
						baseStat.setValue(value);

						Integer bonus = Integer.parseInt(field.getBonus());
						baseStat.setValueBonus(bonus);

						Integer trained = Integer.parseInt(field.getTrained());
						baseStat.setValueTrained(trained);

						em.merge(baseStat);
						em.getTransaction().commit();

						updateCosts();
					} catch (NumberFormatException nfe) {
						notifyError("Mutant base stats", "Number format exception.", nfe, null);
					} catch (Exception e) {
						notifyError("Mutant base stats", "General exception..", e, null);
					}
				}
			} catch (Exception e) {
				notifyError("Mutant base stats", "Could not commit stats.", e, null);
			}
		}
	}

	private Integer sumGEStats() {
		Integer sum = 0;
		for (StatField field : listFields) {
			try {
				Integer i = Integer.parseInt(field.getValue().toString());
				sum += i;
			} catch (NumberFormatException nfe) {
				notifyError("Mutant base stats", "Number format exception.", nfe, null);
			} catch (Exception e) {
				notifyError("Mutant base stats", "General exception..", e, null);
			}
		}
		return sum;
	}

	private Integer sumErfStats() {
		Integer sum = 0;
		for (StatField field : listFields) {
			try {
				Integer base = Integer.parseInt(field.getValue().toString());
				Integer trained = Integer.parseInt(field.getTrained().toString());

				for (int i = 1; i <= trained; ++i) {
					sum += (base + trained) * 2;
					/* made to calc skills.. a misstake, but good calculus
					 if (trained != 0) {
					 if (base + trained < 85) { // x1
					 sum += trained;
					 } else if (base > 100) { //x3
					 sum += trained * 3;
					 } else if (sum >= 85 && trained <= 100) { //x2
					 sum += (base + trained - 85) * 2;
					 }else if(sum < 85 && trained > 100){ //x1,x2,x3
					 sum += (85 - base);
					 sum += (100-85)*2;
					 sum += ((base + trained) -100)*3;
					 }else if(base < 85 && trained < 100){ //x1,x2
					 sum += (85 - base);
					 sum += (trained-85)*2;
					 }else if(base > 85 && trained > 100){ //x2,x3
					 sum += (100-base)*2;
					 sum += (trained-100)*3;
					 }*/
				}

				field.updateTotal();
			} catch (NumberFormatException nfe) {
				notifyError("Mutant base stats", "Number format exception.", nfe, null);
			} catch (Exception e) {
				notifyError("Mutant base stats", "General exception..", e, null);
			}
		}

		return sum;
	}

	private void updateCosts() {
		costLayout.removeAllComponents();
		Integer erfSpent = sumErfStats();
		Integer geSpent = sumGEStats();
		Integer geMax = mutantCharacter.getMutantClass().getBaseStatPoints();
		StringBuilder sb = new StringBuilder("ERF spenderade: ");
		sb.append(erfSpent.toString());
		sb.append("\nGE spenderade: ");
		sb.append(geSpent.toString());
		sb.append(" / ");
		sb.append(geMax.toString());

		Label costLabel = new Label(sb.toString(), Label.CONTENT_PREFORMATTED);
		costLayout.addComponent(costLabel);
	}

	private class FieldFocusListener implements FocusListener {

		@Override
		public void focus(FocusEvent event) {
			updateCosts();
		}
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		gePointLabel.setValue(sumGEStats() + "/" + classPoints);
		erfPointLabel.setValue(sumErfStats().toString());
	}
}
