package org.characterbuilder.pages.footer;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class FootAdd extends Label {

	private static final String addHTML =
			  "<script type=\"text/javascript\"><!--"
			  + "google_ad_client = \"ca-pub-8943848441535418\";"
			  + "/* Role playing games */"
			  + "google_ad_slot = \"2734920821\";"
			  + "google_ad_width = 468;"
			  + "google_ad_height = 60;"
			  + "//-->"
			  + "</script>"
			  + "<script type=\"text/javascript\""
			  + "src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\">"
			  + "</script>";

	public FootAdd() {
		super(addHTML, ContentMode.HTML);
	}
}
