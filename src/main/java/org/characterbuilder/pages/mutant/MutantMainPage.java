package org.characterbuilder.pages.mutant;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import org.characterbuilder.CharbuildApp;
import org.characterbuilder.defaults.MenuButton;
import org.characterbuilder.pages.MainPage;
import org.characterbuilder.pages.footer.SideBannerAdd;
import org.characterbuilder.pages.mutant.admin.MutantManageAbility;
import org.characterbuilder.pages.mutant.admin.MutantManageArmor;
import org.characterbuilder.pages.mutant.admin.MutantManageItem;
import org.characterbuilder.pages.mutant.admin.MutantManageSkill;
import org.characterbuilder.pages.mutant.admin.MutantManageWeapon;
import org.characterbuilder.pdf.MutantPDF;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.MutantBaseStat;
import org.characterbuilder.persist.entity.MutantBaseStatCharacter;
import org.characterbuilder.persist.entity.MutantCharacter;
import org.characterbuilder.persist.entity.MutantClass;
import org.characterbuilder.persist.entity.MutantOccupation;
import org.characterbuilder.persist.entity.MutantSkill;
import org.characterbuilder.persist.entity.MutantSkillCharacter;
import org.characterbuilder.persist.entity.MutantSkillOccupation;
import org.characterbuilder.persist.entity.RollspelUser;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@SuppressWarnings({"serial", "unused"})
public class MutantMainPage extends MainPage implements ValueChangeListener {

	private static final long serialVersionUID = 945039679881712425L;
	private VerticalLayout menuLayout = new VerticalLayout();
	private VerticalLayout adminLayout = new VerticalLayout();
	private VerticalLayout userLayout = new VerticalLayout();
	private Tree characterMenuTree = new Tree("Karaktärer");
	private MenuButton createCharacter = new MenuButton("Skapa karaktär");
	private MenuButton delCharacter = new MenuButton("Dräp karaktär");
	private MenuButton charBaseStat = new MenuButton("Grundegenskaper");
	private MenuButton charAbility = new MenuButton("Förmågor");
	private MenuButton charSkill = new MenuButton("Färdigheter");
	private MenuButton charItem = new MenuButton("Utrustning");
	private MenuButton charWeapon = new MenuButton("Vapen");
	private MenuButton charArmor = new MenuButton("Rustning");
	private MenuButton charGeneratePDF = new MenuButton("Generate PDF");
	private VerticalLayout generatePdfDownloadLinkArea = new VerticalLayout();

	public MutantMainPage() {
		super();
		addComponent(menuLayout);
		addComponent(workspace);

		setMargin(true);
		setSpacing(true);

		Integer userRole = 0;
		try {
			userRole = ThePersister.getRollspelUser(VaadinService.getCurrentRequest().getUserPrincipal().getName())
					.getRollspelUserRole().getId();
		} catch (NullPointerException e) {
			notifyError("Session error.", "Unable to map Session to a user.", e, null);
			goLogin();
		}
		//userRole = CharbuildApp.getRollspelUser().getRollspelUserRole().getId();

		switch (userRole) {
			case 8:
			case 7:
			case 6:
			case 5:
			case 4:
				setUpRoleAdmin();

			case 3:
			case 2:
			case 1:
				setUpRoleUser();
				break;
			default:
				goLogin();
				break;
		}
	}

	private void setUpRoleAdmin() {
		menuLayout.addComponent(adminLayout);
		adminLayout.setMargin(true);
		adminLayout.setSpacing(true);

		MenuButton abilityButton = new MenuButton("Manage Abilities");
		adminLayout.addComponent(abilityButton);
		abilityButton.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				generatePdfDownloadLinkArea.removeAllComponents();
				goTo(new MutantManageAbility());
			}
		});

		MenuButton skillButton = new MenuButton("Manage Skills");
		adminLayout.addComponent(skillButton);
		skillButton.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				generatePdfDownloadLinkArea.removeAllComponents();
				goTo(new MutantManageSkill());
			}
		});

		MenuButton gearButton = new MenuButton("Manage Items");
		adminLayout.addComponent(gearButton);
		gearButton.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				generatePdfDownloadLinkArea.removeAllComponents();
				goTo(new MutantManageItem());
			}
		});

		MenuButton weaponButton = new MenuButton("Manage Weapons");
		adminLayout.addComponent(weaponButton);
		weaponButton.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				generatePdfDownloadLinkArea.removeAllComponents();
				goTo(new MutantManageWeapon());
			}
		});

		MenuButton armorButton = new MenuButton("Manage Armor parts");
		adminLayout.addComponent(armorButton);
		armorButton.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				generatePdfDownloadLinkArea.removeAllComponents();
				goTo(new MutantManageArmor());
			}
		});
	}

	private void setUpRoleUser() {
		menuLayout.addComponent(userLayout);
		userLayout.setMargin(true);
		userLayout.setSpacing(true);

		workspace.addComponent(new NewCharacterPopup());

		//NEW CHARACTER BUTTON
		userLayout.addComponent(createCharacter);
		createCharacter.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				generatePdfDownloadLinkArea.removeAllComponents();
				characterMenuTree.select(null);
				workspace.removeAllComponents();
				workspace.addComponent(new NewCharacterPopup());
			}
		});

		//DELETE CHARACTER BUTTON
		userLayout.addComponent(delCharacter);
		delCharacter.addListener(new DeleteButtonListener());

		//CHARACTER LIST
		characterMenuTree.setMultiSelect(false);
		userLayout.addComponent(characterMenuTree);
		characterMenuTree.addListener(this);
		characterMenuTree.setImmediate(true);

		//POPULATE WITH CHARACTERS
		RollspelUser user = ThePersister.getRollspelUser(VaadinService.getCurrentRequest().getUserPrincipal().getName());
		try {
			Set<MutantCharacter> mcList = user.getMutantCharacters();
			for (MutantCharacter mc : mcList) {
				characterMenuTree.addItem(mc);
				characterMenuTree.setChildrenAllowed(mc, false);
			}
		} catch (Exception e) {
			notifyError("Mutant Update characters", "failed.", e, null);
		}

		//ABILITIES BUTTON
		userLayout.addComponent(charAbility);
		charAbility.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Object obj = characterMenuTree.getValue();
				if (obj == null) {
					notifyInfo("Selection fail.", "No character selected.", null, null);
				} else if (obj instanceof MutantCharacter) {
					notifyInfo("Ability", "Loading...", null, null);
					generatePdfDownloadLinkArea.removeAllComponents();
					goTo(new MutantAbilities((MutantCharacter) obj));
				} else {
					notifyError("Session error.", "Unable to map Session to a user.", null, null);
					goLogin();
				}
			}
		});

		//BASE_STATS BUTTON
		userLayout.addComponent(charBaseStat);
		charBaseStat.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Object obj = characterMenuTree.getValue();
				if (obj == null) {
					notifyInfo("Selection fail.", "No character selected.", null, null);
				} else if (obj instanceof MutantCharacter) {
					notifyInfo("Base stats", "Loading...", null, null);
					generatePdfDownloadLinkArea.removeAllComponents();
					goTo(new MutantBaseStats((MutantCharacter) obj));
				} else {
					notifyError("Session error.", "Unable to map Session to a user.", null, null);
					goLogin();
				}
			}
		});



		//SKILLS BUTTON
		userLayout.addComponent(charSkill);
		charSkill.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Object obj = characterMenuTree.getValue();
				if (obj == null) {
					notifyError("Selection fail.", "No character selected.", null, null);
				} else if (obj instanceof MutantCharacter) {
					notifyInfo("Skill", "Loading...", null, null);
					generatePdfDownloadLinkArea.removeAllComponents();
					goTo(new MutantSkills((MutantCharacter) obj));
				} else {
					notifyError("Session error.", "Unable to map Session to a user.", null, null);
					goLogin();
				}
			}
		});

		//EDIT WEAPON BUTTON
		userLayout.addComponent(charWeapon);
		charWeapon.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Object obj = characterMenuTree.getValue();
				if (obj == null) {
					notifyError("Selection fail.", "No character selected.", null, null);
				} else if (obj instanceof MutantCharacter) {
					generatePdfDownloadLinkArea.removeAllComponents();
					goTo(new MutantWeapons((MutantCharacter) obj));
				} else {
					notifyError("Session error.", "Unable to map Session to a user.", null, null);
					goLogin();
				}
			}
		});

		//EDIT ARMOR BUTTON
		userLayout.addComponent(charArmor);
		charArmor.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Object obj = characterMenuTree.getValue();
				if (obj == null) {
					notifyError("Selection fail.", "No character selected.", null, null);
				} else if (obj instanceof MutantCharacter) {
					generatePdfDownloadLinkArea.removeAllComponents();
					goTo(new MutantArmors((MutantCharacter) obj));
				} else {
					notifyError("Session error.", "Unable to map Session to a user.", null, null);
					goLogin();
				}
			}
		});

		//EDIT ITEM BUTTON
		userLayout.addComponent(charItem);
		charItem.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Object obj = characterMenuTree.getValue();
				if (obj == null) {
					notifyError("Session error.", "No character selected.", null, null);
				} else if (obj instanceof MutantCharacter) {
					generatePdfDownloadLinkArea.removeAllComponents();
					goTo(new MutantItems((MutantCharacter) obj));
				} else {
					try {
						notifyError("Session error.", "Unable to map Session to a user.", null, null);
					} catch (Exception ex) {
						notifyError("Session error.", "Unable to map Session to a user.", ex, null);
					}
					goLogin();
				}
			}
		});

		//DOWNLOAD PDF BUTTON
		userLayout.addComponent(charGeneratePDF);
		userLayout.addComponent(generatePdfDownloadLinkArea);
		charGeneratePDF.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				generatePdfDownloadLinkArea.removeAllComponents();
				Object obj = characterMenuTree.getValue();
				if (obj == null) {
					notifyError("Selection fail.", "No character selected.", null, null);
				} else if (obj instanceof MutantCharacter) {
					try {
						MutantPDF pdf = new MutantPDF();
						Resource res = pdf.getPDF((MutantCharacter) obj);
						generatePdfDownloadLinkArea.addComponent(new Link("Download pdf", res));
					} catch (Exception e) {
						notifyError("PDFing error.", e.getMessage(), e, null);
					}

				} else {
					notifyError("Session error.", "Unable to map Session to a user.", null, null);
					goLogin();
				}
			}
		});
		
		userLayout.addComponent(new SideBannerAdd());
	}

	private class NewCharacterPopup extends VerticalLayout { //implements PopupView.Content {

		private TextField nameField = new TextField("Namn: ");
		private OptionGroup classSelect = new OptionGroup("Klass");
		private OptionGroup occupationSelect = new OptionGroup("Tidigare yrke");
		private HorizontalLayout classLayout = new HorizontalLayout();
		private HorizontalLayout occupationLayout = new HorizontalLayout();
		private Label occupationLabel = new Label("", Label.CONTENT_PREFORMATTED);
		private Button createCharacterButton = new Button("Skapa karaktär");

		public NewCharacterPopup() {
			buildUI();
		}

		private void buildUI() {
			setMargin(true);
			setSpacing(true);

			addComponent(new Label("<h3>New character</h3>", Label.CONTENT_XHTML));
			addComponent(nameField);

			HorizontalLayout lay1 = new HorizontalLayout();
			addComponent(lay1);
			lay1.setMargin(true);
			lay1.setSpacing(true);
			lay1.addComponent(classSelect);
			lay1.addComponent(classLayout);
			classSelect.setImmediate(true);
			classSelect.addListener(new ValueChangeListener() {
				@Override
				public void valueChange(ValueChangeEvent event) {
					Object obj = classSelect.getValue();
					if (obj instanceof MutantClass) {
						MutantClass klass = (MutantClass) obj;
						classLayout.removeAllComponents();
						classLayout.addComponent(new Label(
								  "<h3>" + klass.getName() + "</h3>"
								  + klass.getDescription().replace("\n", "<br/>"),
								  Label.CONTENT_XHTML));
					}
				}
			});

			HorizontalLayout lay2 = new HorizontalLayout();
			addComponent(lay2);
			lay2.setMargin(true);
			lay2.setSpacing(true);
			lay2.addComponent(occupationSelect);
			lay2.addComponent(occupationLayout);
			occupationSelect.setImmediate(true);
			occupationSelect.addListener(new ValueChangeListener() {
				@Override
				public void valueChange(ValueChangeEvent event) {
					Object obj = occupationSelect.getValue();
					if (obj instanceof MutantOccupation) {
						MutantOccupation occupado = (MutantOccupation) obj;
						occupationLayout.removeAllComponents();
						occupationLayout.addComponent(new Label(
								  "<h3>" + occupado.getName() + "</h3>"
								  + occupado.getDescription().replace("\n", "<br/>"),
								  Label.CONTENT_XHTML));
					}
				}
			});

			nameField.focus();
			classSelect.setMultiSelect(false);
			classSelect.setNullSelectionAllowed(false);
			occupationSelect.setMultiSelect(false);
			occupationSelect.setNullSelectionAllowed(false);

			addComponent(createCharacterButton);
			createCharacterButton.addListener(new NewCharacterButtonListener());


			try {
				EntityManager em = ThePersister.getEntityManager();

				List<MutantClass> classList = em.createQuery(
						  "SELECT r FROM MutantClass r ORDER BY r.name",
						  MutantClass.class).getResultList();
				List<MutantOccupation> occupationList = em.createQuery(
						  "SELECT r FROM MutantOccupation r ORDER BY r.name",
						  MutantOccupation.class).getResultList();

				for (MutantClass mc : classList) {
					classSelect.addItem(mc);
					if (!(classSelect.getValue() instanceof MutantClass)) {
						classSelect.select(mc);
					}
				}

				for (MutantOccupation mo : occupationList) {
					occupationSelect.addItem(mo);
					if (!(occupationSelect.getValue() instanceof MutantOccupation)) {
						occupationSelect.select(mo);
					}
				}
			} catch (Exception e) {
				notifyError("Mutant new character page.", "Exception retrtieving classes or occupations.", e, null);
			}

		}

		/*
		 * Listener to the character register
		 */
		private class NewCharacterButtonListener implements Button.ClickListener {

			private static final long serialVersionUID = -8108831501931151445L;

			@Override
			public void buttonClick(ClickEvent event) {
				//Application app = getApplication();
				//String sessionID = ((WebApplicationContext) app.getContext()).getHttpSession().getId();
				String sessionID = getUI().getSession().getSession().getId();
				RollspelUser user = ThePersister.getRollspelUser(
						VaadinService.getCurrentRequest().getUserPrincipal().getName());


				if (user == null) {

					getUI().close();
				} else if (nameField.getValue().toString().equalsIgnoreCase("")) {
					notifyError("Error.", "No user info.", null, user);
				} else {

					EntityManager em = null;
					try {
						em = ThePersister.getEntityManager();

						em.getTransaction().begin();

						MutantOccupation occupation = (MutantOccupation) occupationSelect.getValue();
						MutantClass mutantClass = (MutantClass) classSelect.getValue();

						MutantCharacter newCharacter = new MutantCharacter();
						newCharacter.setRollspelUser(user);
						newCharacter.setMutantClass(mutantClass);

						newCharacter.setName(nameField.getValue().toString());
						newCharacter.setPriorOccupation(occupation.toString());

						em.persist(newCharacter);

						//Populate BASE STATS
						List<MutantBaseStat> statList = em.createQuery(
								  "SELECT r FROM MutantBaseStat r",
								  MutantBaseStat.class).getResultList();
						for (MutantBaseStat stat : statList) {
							MutantBaseStatCharacter baseStatCharacter = new MutantBaseStatCharacter();

							baseStatCharacter.setMutantBaseStat(stat);
							baseStatCharacter.setMutantCharacter(newCharacter);
							newCharacter.getMutantBaseStatCharacters().add(baseStatCharacter);

							em.persist(baseStatCharacter);
						}

						//Populate TRAINED SKILLS from profession
						for (MutantSkillOccupation mso : occupation.getMutantSkillOccupations()) {
							MutantSkill mutantSkill = mso.getMutantSkill();

							MutantSkillCharacter mutantSkillCharacter = new MutantSkillCharacter(newCharacter, mutantSkill);
							mutantSkillCharacter.setCreationTrained(true);

							loop:
							for (MutantBaseStatCharacter characterBaseStat : newCharacter.getMutantBaseStatCharacters()) {
								if (mutantSkill.getMutantBaseStat().getId() == characterBaseStat.getMutantBaseStat().getId()) {
									mutantSkillCharacter.setMutantBaseStatCharacter(characterBaseStat);
									break loop;
								}
							}
							em.persist(mutantSkillCharacter);
							newCharacter.getMutantSkillCharacters().add(mutantSkillCharacter);
						}


						//Populate NATURAL SKILLS
						List<MutantSkill> skillList = em.createQuery(
								  "SELECT r FROM MutantSkill r WHERE r.naturalSkill = :isNatural",
								  MutantSkill.class).setParameter("isNatural", true).getResultList();

						for (MutantSkill mutantSkill : skillList) {
							if (mutantSkill.getMutantAbilities().isEmpty()) {

								MutantCharacter mutantCharacter = em.find(MutantCharacter.class, newCharacter.getId());
								MutantSkillCharacter mutantSkillCharacter = new MutantSkillCharacter(mutantCharacter, mutantSkill);
								mutantSkillCharacter.setCreationTrained(true);

								loop:
								for (MutantBaseStatCharacter characterBaseStat : mutantCharacter.getMutantBaseStatCharacters()) {
									if (mutantSkill.getMutantBaseStat().getId() == characterBaseStat.getMutantBaseStat().getId()) {
										mutantSkillCharacter.setMutantBaseStatCharacter(characterBaseStat);
										break loop;
									}
								}

								em.persist(mutantSkillCharacter);
								newCharacter.getMutantSkillCharacters().add(mutantSkillCharacter);
							}
						}
						em.merge(newCharacter);

						em.getTransaction().commit();

						//updateCharacters();
						characterMenuTree.addItem(newCharacter);
						characterMenuTree.setChildrenAllowed(newCharacter, false);
						characterMenuTree.select(newCharacter);

						notifyInfo("Character", "Created successfully", null, user);
						//workspace.removeAllComponents();
						//workspace.addComponent(new MutantCharacterPage(newCharacter));


					} catch (Exception e) {
						notifyError("Exception.", "failed creating character.", e, null);
					}
				}

			}
		}
	};


	/*
	 * Listener to the delete button
	 */
	private class DeleteButtonListener implements Button.ClickListener {

		private static final long serialVersionUID = -787282961701329480L;

		@Override
		public void buttonClick(ClickEvent event) {
			RollspelUser user = ThePersister.getRollspelUser(
					VaadinService.getCurrentRequest().getUserPrincipal().getName());

			if (user != null) {
				Object obj = characterMenuTree.getValue();
				if (obj instanceof MutantCharacter) {
					MutantCharacter mc = (MutantCharacter) obj;

					EntityManager em = null;
					try {
						em = ThePersister.getEntityManager();
						em.getTransaction().begin();
						MutantCharacter managedMC = em.find(MutantCharacter.class, mc.getId());
						em.remove(managedMC);
						em.getTransaction().commit();

						characterMenuTree.removeItem(obj);
						//workspace.removeAllComponents();
						//updateCharacters();

					} catch (Exception e) {
						notifyError("Mutant Main page", "Remove character fail.", e, null);
						characterMenuTree.removeItem(obj);
					}
				} else {
					notifyError("MutantMainPage", "Object not a character.", null, null);
				}
			} else {
				notifyError("MutantMainPage", "User not found.", null, null);
			}
		}
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		Object obj = event.getProperty().getValue();
		generatePdfDownloadLinkArea.removeAllComponents();
		if (obj instanceof MutantCharacter) {
			MutantCharacter mc = (MutantCharacter) obj;
			workspace.removeAllComponents();
			workspace.addComponent(new MutantCharacterPage(mc));
		} else {
			workspace.removeAllComponents();
			workspace.addComponent(new NewCharacterPopup());
		}

	}
}
