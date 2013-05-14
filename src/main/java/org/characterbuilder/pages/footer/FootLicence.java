package org.characterbuilder.pages.footer;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
public class FootLicence extends Panel {

	private static final String html =
			  "<a rel=\"license\" href=\"http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_US\">"
			  + "<img alt=\"Creative Commons License\" style=\"border-width:0\" src=\"http://i.creativecommons.org/l/by-nc-sa/3.0/88x31.png\" />"
			  + "</a><br />"
			  + "<span xmlns:dct=\"http://purl.org/dc/terms/\" property=\"dct:title\">Karaktärsverktyget</span> "
			  + "by <span xmlns:cc=\"http://creativecommons.org/ns#\" property=\"cc:attributionName\">Jens Brimberg</span> "
			  + "is licensed under a <br/><a rel=\"license\" href=\"http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_US\">"
			  + "Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License</a>.";

	public FootLicence() {
		super();
		setContent(new Label(html, Label.CONTENT_XHTML));
		setSizeUndefined();
	}
}
