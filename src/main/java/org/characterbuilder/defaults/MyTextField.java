package org.characterbuilder.defaults;

import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class MyTextField extends TextField {

	public MyTextField(String caption, String value, int columns) {
		super(caption, value);
		setColumns(columns);
	}

	public MyTextField(int columns) {
		super();
		setColumns(columns);
	}
}
