/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.characterbuilder.navigation;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;

/**
 *
 * @author jens
 */
public class ChargenMenu extends HorizontalLayout {

	private final MenuBar leftMenu;
	private final MenuBar rightMenu;

	public ChargenMenu() {
		this.leftMenu = new MenuBar();
		this.rightMenu = new MenuBar();

		MenuBar.MenuItem trudvangMenu = leftMenu.addItem("Trudvang", null);

		MenuBar.MenuItem mutantMenu = leftMenu.addItem("Mutant: UA", null);

		rightMenu.addItem("Logout", new LogoutCommand());

		leftMenu.setWidth("100%");
		setExpandRatio(leftMenu, 1);
		setExpandRatio(rightMenu, 0);
		setSizeFull();

		addComponent(leftMenu);
		addComponent(rightMenu);
	}

	private class LogoutCommand implements MenuBar.Command {
		@Override
		public void menuSelected(MenuBar.MenuItem selectedItem) {
			getUI().getPage().setLocation("../login/logout.jsp");
		}
	}

}
