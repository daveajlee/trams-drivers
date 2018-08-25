package de.davelee.trams.drivers.admin.ui;

import de.davelee.trams.drivers.admin.ui.events.NavigationEvent;

/**
 * This class provides static access to some methods in the current DriverApplicationUI.
 * @author Roland Krüger
 * Source: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/service/impl/VaadinUIServiceImpl.java
 */
public class VaadinUIService {

    /**
     * Post the navigation event to the event bus based on the source and target.
     * @param source The <code>Object</code> on which the Event initially occurred.
     * @param target a <code>String</code> containing the target link to navigate to.
     */
    public void postNavigationEvent(final Object source, final String target) {
        DriverApplicationUI.getCurrent().getEventbus().post(new NavigationEvent(source, target));
    }

    /**
     * Check if the user is currently logged in or is anonymous.
     * @return a <code>boolean</code> which is true iff the user is anonymous i.e. not logged in.
     */
    public boolean isUserAnonymous() {
        return DriverApplicationUI.getCurrent().isUserAnonymous();
    }
}
