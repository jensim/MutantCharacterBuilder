package org.characterbuilder.pages.admin;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.characterbuilder.CharbuildApp;
import org.characterbuilder.defaults.MenuButton;
import org.characterbuilder.pages.MainPage;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.RollspelUser;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@SuppressWarnings("serial")
public class AdminMainPage extends MainPage {

	private static final long serialVersionUID = -2133903264324157181L;
	private VerticalLayout menuLayout = new VerticalLayout();
	private VerticalLayout userLayout = new VerticalLayout();
	private VerticalLayout adminLayout = new VerticalLayout();
	private Label adminHeader = null;
	private MenuButton logoutButton = new MenuButton("Log out.");

	public AdminMainPage() {
		super();


		Integer userRole = 0;
		try {
			RollspelUser user = ThePersister.getRollspelUser(VaadinService.getCurrentRequest().getUserPrincipal().getName());
			userRole = user.getRollspelUserRole().getId();
			adminHeader = new Label("<b><u>user: </u></b>"
					  + user.getOauthUserID() + "@" + user.getOauthProvider().getName(),
					  Label.CONTENT_XHTML);
		} catch (NullPointerException ex1) {
			try {
				notifyWarning("Session error.", "Unable to map Session to a user.", ex1, null);
			} catch (Exception ex2) {
				notifyError("Session error.", "Unable to map Session to a user.", ex2, null);
			}
			goLogin();
		}
		//userRole = CharbuildApp.getRollspelUser().getRollspelUserRole().getId();

		switch (userRole) {
			case 8:
				MenuButton userButton = new MenuButton("Manage Users");
				adminLayout.addComponent(userButton);
				userButton.addListener(new Button.ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						goTo(new AdminUsers());
					}
				});
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

		menuLayout.addComponent(adminHeader);
		menuLayout.addComponent(adminLayout);
		menuLayout.addComponent(userLayout);
		menuLayout.addComponent(logoutButton);

		addComponent(menuLayout);
		addComponent(workspace);
	}

	private void setUpRoleAdmin() {
		adminLayout.setSpacing(true);
		adminLayout.setMargin(true);
		adminLayout.addComponent(adminHeader);
		//adminLayout.addComponent(new Label("DETTA ĄR ADMIN"));

		MenuButton newsButton = new MenuButton("Manage News");
		adminLayout.addComponent(newsButton);
		newsButton.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				goTo(new AdminNews());
			}
		});
		MenuButton logButton = new MenuButton("Watch logs");
		adminLayout.addComponent(logButton);
		logButton.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				goTo(new AdminLogView());
			}
		});
	}

	private void setUpRoleUser() {
		userLayout.setSpacing(true);
		userLayout.setMargin(true);
		logoutButton.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 3446070187695611705L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					goLogin();
				} catch (IllegalStateException ise) {
					notifyError("Logout error",
							  "Failed logging out, for one reason or another. 1",
							  ise, null);
					goLogin();
				} catch (Exception e) {
					notifyError("Logout error",
							  "Failed logging out, for one reason or another. 2",
							  e, null);
					goLogin();
				}
			}
		});
	}
}
