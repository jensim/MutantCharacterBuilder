package org.characterbuilder.defaults;

import com.vaadin.data.Property;
import com.vaadin.ui.TextField;

/**
 *
* @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
public class ThousandsField extends TextField {

	public ThousandsField(String caption, String value) {
		super(caption, value);
	}

	public ThousandsField(String caption, Property dataSource) {
		super(caption, dataSource);
	}

	public ThousandsField(String caption) {
		super(caption);
	}

	public ThousandsField(Property dataSource) {
		super(dataSource);
	}

	public ThousandsField() {
		super();
	}

	public Integer getThousands() throws Exception {
		String inputVal = getValue().toString().trim();
		if (inputVal.contains(",")) {
			String[] valParts = inputVal.split(",");
			Integer thou = Integer.parseInt(valParts[0]+"000");
			Integer frac = padRight(valParts[1], 3);
			return thou + frac;
		} else if (inputVal.contains(".")) {
			String[] valParts = inputVal.split(".");
			Integer thou = Integer.parseInt(valParts[0]+"000");
			Integer frac = padRight(valParts[1], 3);
			return thou + frac;
		} else {
			return Integer.parseInt(inputVal+"000");
		}
	}
	
	private Integer padRight(String value, Integer padSize) throws Exception{
		if(value.length() > padSize){
			throw new IllegalArgumentException("String longer than padding.");
		}
		return Integer.parseInt(String.format("%-"+padSize+"s", value)
				.replace(" ", "0"));
	}
}
