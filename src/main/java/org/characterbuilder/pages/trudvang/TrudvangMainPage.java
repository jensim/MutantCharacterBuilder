package org.characterbuilder.pages.trudvang;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import javax.persistence.EntityManager;
import org.characterbuilder.CharbuildApp;
import org.characterbuilder.defaults.MenuButton;
import org.characterbuilder.pages.MainPage;
import org.characterbuilder.pages.trudvang.admin.arktype.TrudvangManageSuperArktype;
import org.characterbuilder.pages.trudvang.admin.exceptional.TrudvangManageSuperExceptional;
import org.characterbuilder.pages.trudvang.admin.people.TrudvangManageSuperPeople;
import org.characterbuilder.pages.trudvang.admin.skill.TrudvangManageSuperSkill;
import org.characterbuilder.pages.trudvang.admin.skill.group.TrudvangManageSuperSkillInGroup;
import org.characterbuilder.pages.trudvang.admin.upbringing.TrudvangManageSuperUpbringing;
import org.characterbuilder.pages.trudvang.createcharacter.TrudvangPageCreateCharacter;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.RollspelUser;
import org.characterbuilder.persist.entity.TrudvangCharacter;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
public class TrudvangMainPage extends MainPage {

	JPAContainer<TrudvangCharacter> characterContainer = null;
	private Tree characterMenuTree = null;
	private VerticalLayout menuLayout = new VerticalLayout(),
			  adminLayout = new VerticalLayout(),
			  userLayout = new VerticalLayout(),
			  generatePdfDownloadLinkArea = new VerticalLayout();

	public TrudvangMainPage() {
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

		adminLayout.addComponent(new MenuButton("Manage Arktypes",
				  new Button.ClickListener() {
					  @Override
					  public void buttonClick(ClickEvent event) {
						  goTo(new TrudvangManageSuperArktype());
					  }
				  }));
		adminLayout.addComponent(new MenuButton("Manage People",
				  new Button.ClickListener() {
					  @Override
					  public void buttonClick(ClickEvent event) {
						  goTo(new TrudvangManageSuperPeople());
					  }
				  }));
		adminLayout.addComponent(new MenuButton("Manage Upbringing",
				  new Button.ClickListener() {
					  @Override
					  public void buttonClick(ClickEvent event) {
						  goTo(new TrudvangManageSuperUpbringing());
					  }
				  }));
		adminLayout.addComponent(new MenuButton("Manage Exceptional attributes",
				  new Button.ClickListener() {
					  @Override
					  public void buttonClick(ClickEvent event) {
						  goTo(new TrudvangManageSuperExceptional());
					  }
				  }));
		adminLayout.addComponent(new MenuButton("Manage Skills",
				  new Button.ClickListener() {
					  @Override
					  public void buttonClick(ClickEvent event) {
						  goTo(new TrudvangManageSuperSkill());
					  }
				  }));
		adminLayout.addComponent(new MenuButton("Manage Skill groups",
				  new Button.ClickListener() {
					  @Override
					  public void buttonClick(ClickEvent event) {
						  goTo(new TrudvangManageSuperSkillInGroup());
					  }
				  }));
	}

	private void setUpRoleUser() {
		menuLayout.addComponent(userLayout);
		userLayout.setMargin(true);
		userLayout.setSpacing(true);
		
		goTo(new TrudvangPageCreateCharacter());

		//CREATE CHARACTER
		userLayout.addComponent(new MenuButton("Ny karaktär",
				  new Button.ClickListener() {
					  @Override
					  public void buttonClick(ClickEvent event) {
						  goTo(new TrudvangPageCreateCharacter());
					  }
				  }));

		//DELETE CHARACTER
		userLayout.addComponent(new MenuButton("Ta bort karaktär",
				  new Button.ClickListener() {
					  @Override
					  public void buttonClick(ClickEvent event) {
						  Integer charIdnr = getSelectedCharacterIdnr();
						  if (charIdnr != null) {
							  EntityManager em = ThePersister.getEntityManager();

							  em.getTransaction().begin();
							  TrudvangCharacter character = em.find(
										 TrudvangCharacter.class, charIdnr);
							  em.remove(character);
							  em.getTransaction().commit();
							  characterContainer.refresh();
						  } else {
							  notifyWarning("Character delete", "Not possible when no character is selected", null, null);
						  }
						  goTo(new TrudvangPageCreateCharacter());
					  }
				  }));

		//CHARACTER LIST/TREE
		characterContainer = JPAContainerFactory.make(TrudvangCharacter.class, ThePersister.ENTITY_MANAGER_NAME);
		RollspelUser user = ThePersister.getRollspelUser(VaadinService.getCurrentRequest().getUserPrincipal().getName());
		characterContainer.addContainerFilter(new Compare.Equal("user", user));
		characterMenuTree = new Tree("Karaktärer", characterContainer);
		characterMenuTree.setMultiSelect(false);
		characterMenuTree.setImmediate(true);
		characterMenuTree.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
		characterMenuTree.setItemCaptionPropertyId("name");
		userLayout.addComponent(characterMenuTree);
		characterMenuTree.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				TrudvangCharacter character = getSelectedCharacter();
				if (character != null) {
					goTo(new TrudvangPageCharacter(character));
				} else {
					goTo(new TrudvangPageCreateCharacter());
				}
			}
		});

		//EXCEPTIONAL ATTRIBUTES
		userLayout.addComponent(new MenuButton("Exceptionella Karaktärsdrag",
				  new Button.ClickListener() {
					  @Override
					  public void buttonClick(ClickEvent event) {
						  notifyInfo("Not yet implemented", "Coming soon.", null, null);
					  }
				  }));
		//SKILLS
		userLayout.addComponent(new MenuButton("Färdigheter",
				  new Button.ClickListener() {
					  @Override
					  public void buttonClick(ClickEvent event) {
						  notifyInfo("Not yet implemented", "Coming soon.", null, null);
					  }
				  }));
		//ITEM
		userLayout.addComponent(new MenuButton("Föremål",
				  new Button.ClickListener() {
					  @Override
					  public void buttonClick(ClickEvent event) {
						  notifyInfo("Not yet implemented", "Coming soon.", null, null);
					  }
				  }));
		//WEAPON
		userLayout.addComponent(new MenuButton("Vapen",
				  new Button.ClickListener() {
					  @Override
					  public void buttonClick(ClickEvent event) {
						  notifyInfo("Not yet implemented", "Coming soon.", null, null);
					  }
				  }));
		//ARMOR
		userLayout.addComponent(new MenuButton("Rustning",
				  new Button.ClickListener() {
					  @Override
					  public void buttonClick(ClickEvent event) {
						  notifyInfo("Not yet implemented", "Coming soon.", null, null);
					  }
				  }));
		//PDF
		userLayout.addComponent(new MenuButton("Generera PDF",
				  new Button.ClickListener() {
					  @Override
					  public void buttonClick(ClickEvent event) {
						  generatePdfDownloadLinkArea.removeAllComponents();
						  TrudvangCharacter character = getSelectedCharacter();
						  /*TODO: 
						   * Generate the pdf
						   * Make a StreamResource out of it
						   * Put the resource in a Link
						   * Add Link to the downloadLinkArea
						   */
					  }
				  }));

		userLayout.addComponent(generatePdfDownloadLinkArea);
	}

	private Integer getSelectedCharacterIdnr() {
		try {
			Item selected = characterMenuTree.getItem(characterMenuTree.getValue());
			return Integer.parseInt(selected.getItemProperty("id").toString());
		} catch (Exception ex) {
		}
		return null;
	}

	private TrudvangCharacter getSelectedCharacter() {
		try {
			return characterContainer.getItem(characterMenuTree.getValue()).getEntity();
		} catch (Exception ex) {
		}
		return null;
	}
}
