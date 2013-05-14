package org.characterbuilder.defaults;

import com.vaadin.data.Property;
import com.vaadin.ui.TextArea;

@SuppressWarnings("serial")
public class MyTextArea extends TextArea {

	public MyTextArea() {
		super();
		setTheSize();
	}

	public MyTextArea(String caption) {
		super(caption);
		setTheSize();
	}

	public MyTextArea(String caption, String value) {
		super(caption, value);
		setTheSize();
	}

	public MyTextArea(Property dataSource) {
		super(dataSource);
		setTheSize();
	}

	public MyTextArea(String caption, Property dataSource) {
		super(caption, dataSource);
		setTheSize();
	}

	private void setTheSize() {
		setRows(10);
		setColumns(35);
	}
}
