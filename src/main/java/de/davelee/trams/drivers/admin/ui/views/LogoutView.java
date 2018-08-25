package de.davelee.trams.drivers.admin.ui.views;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import de.davelee.trams.drivers.admin.ui.DriverApplicationUI;
import de.davelee.trams.drivers.admin.ui.events.LogoutEvent;

@SpringView(name = LogoutView.NAME)
/**
 * This view represents the logout view when a user wants to logout of the system.
 * @author Roland Krüger
 * Source: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/ui/views/LogoutView.java
 */
public class LogoutView extends Navigator.EmptyView {

    public static final String NAME = "logout";

    @Override
    /**
     * When entering the logout view, simply record the logout event.
     * @param event a <code>ViewChangeEvent</code> object representing the logout event.
     */
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        DriverApplicationUI.getCurrent().getEventbus().post(new LogoutEvent(this));
    }
}
