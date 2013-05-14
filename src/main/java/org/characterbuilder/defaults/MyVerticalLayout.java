package org.characterbuilder.defaults;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
public class MyVerticalLayout extends VerticalLayout {

	public MyVerticalLayout() {
		super();
		setSpacing(true);
		setMargin(true);
	}

	public MyVerticalLayout(Component... c) {
		super();
		setSpacing(true);
		setMargin(true);
		for (Component comp : c) {
			addComponent(comp);
		}
	}
}
