package org.characterbuilder.pages.abst;

import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.characterbuilder.CharbuildApp;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.RollspelUser;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@SuppressWarnings("serial")
public abstract class WorkspaceLayout extends VerticalLayout {

	protected Panel infoPanel = new Panel();

	protected WorkspaceLayout() {
		super();
		setMargin(true);
		setSpacing(true);
	}

	protected void verifyUser() {
		if (CharbuildApp.getRollspelUser() == null) {
			CharbuildApp.logout();
		}
	}

	protected void setInfo(String info) {
		infoPanel.setContent(new Label(info, Label.CONTENT_XHTML));
	}

	protected void setInfoCaption(String caption) {
		infoPanel.setCaption(caption);
	}

	protected void notifyError(String type, String info, Throwable exception, RollspelUser user) {
		if (user == null) {
			try {
				user = CharbuildApp.getRollspelUser();
			} catch (Exception ex) {
			}
		}
		ThePersister.logIt(9, type + ": " + info, exception, user);
		Notification.show(type, info, Notification.Type.WARNING_MESSAGE);
	}

	protected void notifyWarning(String type, String info, Throwable exception, RollspelUser user) {
		if (user == null) {
			try {
				user = CharbuildApp.getRollspelUser();
			} catch (Exception ex) {
			}
		}
		ThePersister.logIt(2, type + ": " + info, exception, user);
		Notification.show(type, info, Notification.Type.WARNING_MESSAGE);
	}

	protected void notifyInfo(String type, String info, Throwable exception, RollspelUser user) {
		if (user == null) {
			try {
				user = CharbuildApp.getRollspelUser();
			} catch (Exception ex) {
			}
		}
		ThePersister.logIt(1, type + ": " + info, exception, user);
		Notification.show(type, info, Notification.Type.TRAY_NOTIFICATION);
	}
}
