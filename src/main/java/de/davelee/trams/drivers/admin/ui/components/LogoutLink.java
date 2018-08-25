package de.davelee.trams.drivers.admin.ui.components;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Link;
import de.davelee.trams.drivers.admin.ui.DriverApplicationUI;
import de.davelee.trams.drivers.admin.ui.views.LogoutView;

/**
 * This class represents the logout link.
 * @author Roland Krüger
 * Source: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/ui/components/LogoutLink.java
 */
public class LogoutLink extends CustomComponent {

    /**
     * Create a new logout link.
     * A link consists of a text as well as an icon representing sign out.
     */
    public LogoutLink() {
        Link logoutLink = new Link("Logout", new ExternalResource("#!" + LogoutView.NAME));
        logoutLink.setIcon(FontAwesome.SIGN_OUT);
        setCompositionRoot(logoutLink);
    }

    /**
     * Only shown the logout link when a user is logged in.
     */
    public void updateVisibility() {
        setVisible(!DriverApplicationUI.getCurrent().isUserAnonymous());
    }
}