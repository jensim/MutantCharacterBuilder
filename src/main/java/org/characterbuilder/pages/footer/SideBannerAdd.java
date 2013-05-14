package org.characterbuilder.pages.footer;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

/**
 * SideBannerAdd
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public class SideBannerAdd extends Label{

	private static final String scriptos =
			  "<script type=\"text/javascript\"><!--"
			  + "google_ad_client = \"ca-pub-8943848441535418\";"
			  + "/* SkyScraperWide */"
			  + "google_ad_slot = \"8478573227\";"
			  + "google_ad_width = 160;"
			  + "google_ad_height = 600;"
			  + "//-->"
			  + "</script>"
			  + "<script type=\"text/javascript\""
			  + "src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\">"
			  + "</script>";

	public SideBannerAdd() {
		super(scriptos, ContentMode.HTML);
	}
}
