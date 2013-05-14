package org.characterbuilder.pages;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import org.characterbuilder.pages.admin.AdminMainPage;
import org.characterbuilder.pages.mutant.MutantMainPage;
import org.characterbuilder.pages.trudvang.TrudvangMainPage;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public class TopPage extends VerticalLayout {

	private static final long serialVersionUID = -4279070331895173821L;
	private TabSheet tabbe = new TabSheet();
	private MutantMainPage mmp =  new MutantMainPage();
	private TrudvangMainPage tmp = new TrudvangMainPage();
	private AdminMainPage amp = new AdminMainPage();

	public TopPage() {

		setSizeFull();

		tabbe.addTab(mmp, "Mutant: UA");
		tabbe.addTab(tmp, "D&D: Trudvang");

		tabbe.addTab(amp, "Account");

		

		tabbe.setSizeFull();
		tabbe.setVisible(true);

		addComponent(tabbe);
	}
}
