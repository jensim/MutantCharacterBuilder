package org.characterbuilder.defaults;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupView;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
public class PopupContent implements PopupView.Content {

	private String caption, content;

	public PopupContent(String caption, String content) {
		this.caption = caption;
		this.content = content;
	}

	@Override
	public String getMinimizedValueAsHTML() {
		return caption;
	}

	@Override
	public Component getPopupComponent() {
		return new Label(content);
	}
}
