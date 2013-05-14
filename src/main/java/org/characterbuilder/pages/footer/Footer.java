package org.characterbuilder.pages.footer;

import com.vaadin.ui.VerticalLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.characterbuilder.pages.TopPage;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
public class Footer extends VerticalLayout {

	private FootAdd addLabel = new FootAdd();
	private FootLicence licenceLabel = new FootLicence();
	private Tic tic;
	private Long time = 30000L;

	public Footer() {


		addComponent(licenceLabel);
		addComponent(addLabel);
		tic = new Tic();
	}

	public void startTic() {
		startTic(null);
	}

	public void startTic(Long mills) {
		if (mills != null) {
			time = mills;
			tic.start();
		}
	}

	private class Tic extends Thread {

		public void run() {
			try {
				sleep(time);
			} catch (InterruptedException ex) {
				Logger.getLogger(TopPage.class.getName()).log(Level.SEVERE, null, ex);
			}
			removeComponent(addLabel);
			addLabel = new FootAdd();
			addComponent(addLabel);
		}
	}
}
