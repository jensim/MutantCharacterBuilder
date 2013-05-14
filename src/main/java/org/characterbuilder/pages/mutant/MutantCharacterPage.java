package org.characterbuilder.pages.mutant;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import javax.persistence.EntityManager;
import org.characterbuilder.defaults.MenuButton;
import org.characterbuilder.pages.abst.mutant.MutantCharacterWorkspaceLayout;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.MutantBaseStatCharacter;
import org.characterbuilder.persist.entity.MutantCharacter;
import org.characterbuilder.persist.entity.MutantDamageBonus;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@SuppressWarnings("serial")
public class MutantCharacterPage extends MutantCharacterWorkspaceLayout {

	private VerticalLayout costLayout = new VerticalLayout();
	private TextField name = new TextField("Namn");
	private TextField occupation = new TextField("Tidigare Yrke");
	private TextField hometown = new TextField("Hemort");
	private TextField age = new TextField("Ålder");
	private TextField sex = new TextField("Kön");
	private TextField length = new TextField("Längd");
	private TextField weight = new TextField("Vikt");
	private TextField looks = new TextField("Utseende");
	private TextField run1 = new TextField("FF - Strid");
	private TextField run2 = new TextField("FF - Springa");
	private TextField run3 = new TextField("FF - Sprint");
	private TextField kp = new TextField("Kroppspoäng (KP)");
	private TextField tt = new TextField("Traumatröskel (TT)");
	private TextField status = new TextField("Status");
	private TextField rumor = new TextField("Rykte");
	private TextField damage = new TextField("Skadebonus (SB)");
	private TextField ib = new TextField("Initiativbonus (IB)");
	private TextField bf = new TextField("Bärförmåga (BF)");
	private TextField rea = new TextField("Reaktionsvärde (REA)");
	private Label ageMod = new Label();
	private MenuButton defaultWeightButt = new MenuButton("Beräkna vikt");
	private MenuButton defaultRun1Butt = new MenuButton("Beräkna FF - Strid");
	private MenuButton defaultRun2Butt = new MenuButton("Beräkna FF - Springa");
	private MenuButton defaultRun3Butt = new MenuButton("Beräkna FF - Sprint");
	private MenuButton defaultKPButt = new MenuButton("Beräkna KP");
	private MenuButton defaultTTButt = new MenuButton("Beräkna TT");
	private MenuButton defaultDamageButt = new MenuButton("Beräkna Skagebonus");
	private MenuButton defaultIBButt = new MenuButton("Beräkna IB");
	private MenuButton defaultBFButt = new MenuButton("Beräkna BF");
	private MenuButton defaultREAButt = new MenuButton("Beräkna REA");
	private MenuButton defaultALLButt = new MenuButton("Beräkna ALLA");
	private Button saveButton = new Button("Spara..");

	public MutantCharacterPage(MutantCharacter mc) {
		super(mc);

		setInfo(mutantCharacter.getName());
		infoPanel.setContent(costLayout);
		updateCosts();

		addComponent(name);
		addComponent(occupation);
		addComponent(hometown);
		addComponent(new HorizontalLayout(age, ageMod));
		addComponent(sex);
		addComponent(length);
		addComponent(new HorizontalLayout(weight, defaultWeightButt));
		addComponent(looks);
		looks.setColumns(35);

		addComponent(new HorizontalLayout(run1, defaultRun1Butt));
		addComponent(new HorizontalLayout(run2, defaultRun2Butt));
		addComponent(new HorizontalLayout(run3, defaultRun3Butt));

		addComponent(rumor);
		addComponent(status);
		addComponent(new HorizontalLayout(kp, defaultKPButt));
		addComponent(new HorizontalLayout(tt, defaultTTButt));
		addComponent(new HorizontalLayout(damage, defaultDamageButt));
		addComponent(new HorizontalLayout(ib, defaultIBButt));
		addComponent(new HorizontalLayout(bf, defaultBFButt));
		addComponent(new HorizontalLayout(rea, defaultREAButt));

		addComponent(defaultALLButt);
		addComponent(saveButton);

		name.setValue(mutantCharacter.getName());
		occupation.setValue(mutantCharacter.getPriorOccupation());
		hometown.setValue(mutantCharacter.getHome());
		age.setValue(mutantCharacter.getAge().toString());
		//FIXME
		ageMod.setValue(getAgeMod(mutantCharacter.getAge()));
		sex.setValue(mutantCharacter.getSex());
		length.setValue(mutantCharacter.getLength().toString());
		weight.setValue(mutantCharacter.getWeight().toString());
		run1.setValue(mutantCharacter.getFfStrid().toString());
		run2.setValue(mutantCharacter.getFfSpringa().toString());
		run3.setValue(mutantCharacter.getFfSprint().toString());
		looks.setValue(mutantCharacter.getLooks());
		rumor.setValue(mutantCharacter.getRumorPoints().toString());
		status.setValue(mutantCharacter.getStatusPoints().toString());
		kp.setValue(mutantCharacter.getHealthPoints().toString());
		tt.setValue(mutantCharacter.getTraumaPoints().toString());
		damage.setValue(mutantCharacter.getDamageBonus());
		ib.setValue(mutantCharacter.getInitiativbonus().toString());
		bf.setValue(mutantCharacter.getCarryCap().toString());
		rea.setValue(mutantCharacter.getReactionValue().toString());

		addListeners();
	}

	private void addListeners() {
		age.setImmediate(true);
		age.addListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				ageMod.setValue(getAgeMod(mutantCharacter.getAge()));
			}
		});

		defaultWeightButt.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					MutantBaseStatCharacter stoChar = ThePersister.getCharacterBaseStat(mutantCharacter, 3);

					weight.setValue(defaultWeight(stoChar.getValueTotal()).toString());
				} catch (Exception e) {
					notifyError("Mutant character page", "Error default weight", e, mutantCharacter.getRollspelUser());
				}

			}
		});

		defaultRun1Butt.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					MutantBaseStatCharacter smiChar = ThePersister.getCharacterBaseStat(mutantCharacter, 4);
					MutantBaseStatCharacter stoChar = ThePersister.getCharacterBaseStat(mutantCharacter, 3);

					run1.setValue(defaultRun1(smiChar.getValueTotal(), stoChar.getValueTotal()).toString());
				} catch (Exception e) {
					notifyError("Mutant character page", "Error default weight", e, mutantCharacter.getRollspelUser());
				}
			}
		});

		defaultRun2Butt.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					MutantBaseStatCharacter smiChar = ThePersister.getCharacterBaseStat(mutantCharacter, 4);
					MutantBaseStatCharacter stoChar = ThePersister.getCharacterBaseStat(mutantCharacter, 3);

					run2.setValue(defaultRun2(smiChar.getValueTotal(), stoChar.getValueTotal()).toString());
				} catch (Exception e) {
					notifyError("Mutant character page", "Error default weight", e, mutantCharacter.getRollspelUser());
				}
			}
		});

		defaultRun3Butt.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					MutantBaseStatCharacter smiChar = ThePersister.getCharacterBaseStat(mutantCharacter, 4);
					MutantBaseStatCharacter stoChar = ThePersister.getCharacterBaseStat(mutantCharacter, 3);
					run3.setValue(defaultRun3(smiChar.getValueTotal(), stoChar.getValueTotal()).toString());
				} catch (Exception e) {
					notifyError("Mutant character page", "Error default weight", e, mutantCharacter.getRollspelUser());
				}
			}
		});

		defaultKPButt.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					MutantBaseStatCharacter fysChar = ThePersister.getCharacterBaseStat(mutantCharacter, 2);
					MutantBaseStatCharacter stoChar = ThePersister.getCharacterBaseStat(mutantCharacter, 3);
					kp.setValue(defaultKP(fysChar.getValueTotal(), stoChar.getValueTotal()).toString());
				} catch (Exception e) {
					notifyError("Mutant character page", "Error default weight", e, mutantCharacter.getRollspelUser());
				}
			}
		});

		defaultTTButt.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					MutantBaseStatCharacter fysChar = ThePersister.getCharacterBaseStat(mutantCharacter, 2);
					MutantBaseStatCharacter stoChar = ThePersister.getCharacterBaseStat(mutantCharacter, 3);
					tt.setValue(defaultTT(fysChar.getValueTotal(), stoChar.getValueTotal()).toString());
				} catch (Exception e) {
					notifyError("Mutant character page", "Error default weight", e, mutantCharacter.getRollspelUser());
				}
			}
		});

		defaultDamageButt.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					MutantBaseStatCharacter styChar = ThePersister.getCharacterBaseStat(mutantCharacter, 1);
					MutantBaseStatCharacter stoChar = ThePersister.getCharacterBaseStat(mutantCharacter, 3);
					damage.setValue(defaultDamage(styChar.getValueTotal(), stoChar.getValueTotal()));
				} catch (Exception e) {
					notifyError("Mutant character page", "Error default weight", e, mutantCharacter.getRollspelUser());
				}
			}
		});

		defaultIBButt.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					MutantBaseStatCharacter smiChar = ThePersister.getCharacterBaseStat(mutantCharacter, 4);
					ib.setValue(defaultIB(smiChar.getValueTotal()).toString());
				} catch (Exception e) {
					notifyError("Mutant character page", "Error default weight", e, mutantCharacter.getRollspelUser());
				}
			}
		});

		defaultBFButt.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					MutantBaseStatCharacter styChar = ThePersister.getCharacterBaseStat(mutantCharacter, 1);
					bf.setValue(defaultBF(styChar.getValueTotal()).toString());
				} catch (Exception e) {
					notifyError("Mutant character page", "Error default weight", e, mutantCharacter.getRollspelUser());
				}
			}
		});

		defaultREAButt.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					MutantBaseStatCharacter perChar = ThePersister.getCharacterBaseStat(mutantCharacter, 7);
					rea.setValue(defaultREA(perChar.getValueTotal()).toString());
				} catch (Exception e) {
					notifyError("Mutant character page", "Error default weight", e, mutantCharacter.getRollspelUser());
				}
			}
		});

		defaultALLButt.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					MutantBaseStatCharacter styChar = ThePersister.getCharacterBaseStat(mutantCharacter, 1);
					MutantBaseStatCharacter fysChar = ThePersister.getCharacterBaseStat(mutantCharacter, 2);
					MutantBaseStatCharacter stoChar = ThePersister.getCharacterBaseStat(mutantCharacter, 3);
					MutantBaseStatCharacter smiChar = ThePersister.getCharacterBaseStat(mutantCharacter, 4);
					MutantBaseStatCharacter perChar = ThePersister.getCharacterBaseStat(mutantCharacter, 7);

					weight.setValue(defaultWeight(stoChar.getValueTotal()).toString());
					run1.setValue(defaultRun1(smiChar.getValueTotal(), stoChar.getValueTotal()).toString());
					run2.setValue(defaultRun2(smiChar.getValueTotal(), stoChar.getValueTotal()).toString());
					run3.setValue(defaultRun3(smiChar.getValueTotal(), stoChar.getValueTotal()).toString());
					kp.setValue(defaultKP(fysChar.getValueTotal(), stoChar.getValueTotal()).toString());
					tt.setValue(defaultTT(fysChar.getValueTotal(), stoChar.getValueTotal()).toString());
					damage.setValue(defaultDamage(styChar.getValueTotal(), stoChar.getValueTotal()));
					ib.setValue(defaultIB(smiChar.getValueTotal()).toString());
					bf.setValue(defaultBF(styChar.getValueTotal()).toString());
					rea.setValue(defaultREA(perChar.getValueTotal()).toString());
				} catch (Exception e) {
					notifyError("Mutant character page", "Error default weight", e, mutantCharacter.getRollspelUser());
				}
			}
		});

		saveButton.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				EntityManager em = ThePersister.getEntityManager();
				try {
					em.getTransaction().begin();
					MutantCharacter character = em.find(MutantCharacter.class, mutantCharacter.getId());
					character.setName(name.getValue().toString());
					character.setPriorOccupation(occupation.getValue().toString());
					character.setHome(hometown.getValue().toString());
					character.setAge(Integer.parseInt(age.getValue().toString()));
					character.setSex(sex.getValue().toString());
					character.setLength(Integer.parseInt(length.getValue().toString()));
					character.setWeight(Integer.parseInt(weight.getValue().toString()));
					character.setLooks(looks.getValue().toString());
					character.setFfStrid(Integer.parseInt(run1.getValue().toString()));
					character.setFfSpringa(Integer.parseInt(run2.getValue().toString()));
					character.setFfSprint(Integer.parseInt(run3.getValue().toString()));
					character.setRumorPoints(Integer.parseInt(rumor.getValue().toString()));
					character.setStatusPoints(Integer.parseInt(status.getValue().toString()));
					character.setHealthPoints(Integer.parseInt(kp.getValue().toString()));
					character.setTraumaPoints(Integer.parseInt(tt.getValue().toString()));
					character.setDamageBonus(damage.getValue().toString());
					character.setInitiativbonus(Integer.parseInt(ib.getValue().toString()));
					character.setCarryCap(Integer.parseInt(bf.getValue().toString()));
					character.setReactionValue(Integer.parseInt(rea.getValue().toString()));

					em.getTransaction().commit();
					notifyInfo("Mutant character", "Saved basic info", null, null);
				} catch (NumberFormatException nfe) {
					notifyError("Mutant character", "Number format exception.", nfe, null);
				} catch (Exception e) {
					notifyError("Mutant character", "General exception..", e, null);
				}
			}
		});
	}

	private String getAgeMod(int age) {
		if (age < 26) {
			return "inga avdrag";
		} else if (age < 36) {
			return "-1 SMI";
		} else if (age < 51) {
			return "-1 STY, -1 FYS, -1 SMI";
		} else if (age < 66) {
			return "-2 STY, -2 FYS, -2 SMI";
		} else {
			return "-3 STY, -3 FYS, -3 SMI";
		}
	}

	private Integer defaultWeight(int storlek) {
		return (5 * storlek) + 15;
	}

	private Integer defaultRun1(int smidighet, int storlek) {
		return (smidighet + storlek) / 5;
	}

	private Integer defaultRun2(int smidighet, int storlek) {
		return (smidighet + storlek) / 2;
	}

	private Integer defaultRun3(int smidighet, int storlek) {
		return smidighet + storlek;
	}

	private Integer defaultKP(int fysik, int storlek) {
		return fysik + storlek;
	}

	private Integer defaultTT(int fysik, int storlek) {
		return defaultKP(fysik, storlek) / 2;
	}

	private String defaultDamage(int styrka, int storlek) {
		try {
			EntityManager em = ThePersister.getEntityManager();
			List<MutantDamageBonus> bonusList = em.createQuery(
					  "SELECT r "
					  + "FROM MutantDamageBonus r "
					  + "WHERE r.stySto <= :sum "
					  + "ORDER BY r.id DESC ",
					  MutantDamageBonus.class)
					  .setParameter("sum", styrka + storlek)
					  .getResultList();
			MutantDamageBonus bonus = bonusList.get(0);
			return bonus.toString();
		} catch (Exception e) {
			return "0";
		}

	}

	private Integer defaultIB(int smidighet) {
		return smidighet;
	}

	private Integer defaultBF(int styrka) {
		return styrka;
	}

	private Integer defaultREA(int personlighet) {
		return personlighet * 5;
	}

	private void updateCosts() {
		costLayout.removeAllComponents();
		Integer erfSpent = mutantCharacter.getErfSpent();
		Integer spSpent = mutantCharacter.getSpSpent();
		Integer spMax = mutantCharacter.getCreationPoints();
		StringBuilder sb = new StringBuilder("SP spenderade: ");
		sb.append(spSpent.toString());
		sb.append(" / ");
		sb.append(spMax.toString());
		sb.append("\nERF spenderade: ");
		sb.append(erfSpent.toString());
		Label costLabel = new Label(sb.toString(), Label.CONTENT_PREFORMATTED);
		costLayout.addComponent(costLabel);
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
	}
}
