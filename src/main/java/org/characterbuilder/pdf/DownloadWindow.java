/*package org.characterbuilder.pdf;

import com.vaadin.terminal.Resource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class DownloadWindow extends Window {

    private static final long serialVersionUID = 4251939087428239055L;

    public DownloadWindow(Resource r) {

        ((VerticalLayout) getContent()).setSizeFull();
        setResizable(true);
        setWidth("800");
        setHeight("600");
        center();
        Embedded e = new Embedded();
        e.setSizeFull();
        e.setType(Embedded.TYPE_BROWSER);

        e.setSource(r);
        setContent(e);
    }
}
*/