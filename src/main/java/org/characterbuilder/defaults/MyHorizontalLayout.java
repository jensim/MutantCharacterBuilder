package org.characterbuilder.defaults;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

@SuppressWarnings("serial")
public class MyHorizontalLayout extends HorizontalLayout {

	public MyHorizontalLayout() {
		super();
		setSpacing(true);
		setMargin(true);
	}

	public MyHorizontalLayout(Component... c) {
		super();
		setSpacing(true);
		setMargin(true);
		for (Component comp : c) {
			addComponent(comp);
		}
	}
}
