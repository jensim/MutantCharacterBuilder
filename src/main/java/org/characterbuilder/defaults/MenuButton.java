package org.characterbuilder.defaults;

import com.vaadin.ui.Button;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
public class MenuButton extends Button {

	public MenuButton() {
		super();
		setupStyle();
	}

	public MenuButton(String caption) {
		super(caption);
		setupStyle();
	}

	public MenuButton(String caption, ClickListener listener) {
		super(caption, listener);
		setupStyle();
	}

	private void setupStyle() {
		setStyleName(BaseTheme.BUTTON_LINK);
	}
}
