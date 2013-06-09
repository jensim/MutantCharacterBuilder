package org.characterbuilder.pages;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import org.characterbuilder.CharbuildApp;
import org.characterbuilder.pages.abst.WorkspaceLayout;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.RollspelUser;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@SuppressWarnings("serial")
public abstract class MainPage extends HorizontalLayout {

	protected VerticalLayout workspace = new VerticalLayout();

	protected void goLogin() {
		getUI().close();
	}

	protected void goTo(WorkspaceLayout layout) {
		workspace.removeAllComponents();
		workspace.addComponent(layout);
	}

	protected void notifyError(String type, String info, Throwable exception, RollspelUser user) {
		if (user == null) {
			try {
				user = ThePersister.getRollspelUser(VaadinService.getCurrentRequest().getUserPrincipal().getName());
			} catch (Exception ex) {
			}
		}
		ThePersister.logIt(9, type + ": " + info, exception, user);
		Notification.show(type, info, Notification.Type.WARNING_MESSAGE);
	}

	protected void notifyWarning(String type, String info, Throwable exception, RollspelUser user) {
		if (user == null) {
			try {
				user = ThePersister.getRollspelUser(VaadinService.getCurrentRequest().getUserPrincipal().getName());
			} catch (Exception ex) {
			}
		}
		ThePersister.logIt(2, type + ": " + info, exception, user);
		Notification.show(type, info, Notification.Type.WARNING_MESSAGE);
	}

	protected void notifyInfo(String type, String info, Throwable exception, RollspelUser user) {
		if (user == null) {
			try {
				user = ThePersister.getRollspelUser(VaadinService.getCurrentRequest().getUserPrincipal().getName());
			} catch (Exception ex) {
			}
		}
		ThePersister.logIt(1, type + ": " + info, exception, user);
		Notification.show(type, info, Notification.Type.TRAY_NOTIFICATION);
	}
}
