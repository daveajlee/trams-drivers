package de.davelee.trams.drivers.admin.ui.views;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;

@SpringView(name = AccessDeniedView.NAME)
/**
 * This view represents the access denied view when a user does not have permission to view the requested screen.
 * @author Roland Krüger
 * Source: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/ui/views/AccessDeniedView.java
 */
public class AccessDeniedView extends AbstractView  {

    public static final String NAME = "accessDenied";

    /**
     * Create a new AccessDeniedView with a text message for the user and a link back to the home page.
     */
    public AccessDeniedView() {
        addComponent(new Label("<h1>Access Denied!</h1>", ContentMode.HTML));
        addComponent(new Label("You don't have required permission to access this resource."));
        Link homeLink = new Link("Home", new ExternalResource("#"));
        homeLink.setIcon(FontAwesome.HOME);
        addComponent(homeLink);
    }

    @Override
    /**
     * The enter method is not currently used.
     * @param event a <code>ViewChangeEvent</code> object which will be ignored.
     */
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //Do nothing.
    }

}
