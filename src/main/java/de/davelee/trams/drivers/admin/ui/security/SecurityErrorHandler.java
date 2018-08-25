package de.davelee.trams.drivers.admin.ui.security;

import com.google.common.eventbus.EventBus;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.ErrorEvent;
import com.vaadin.server.ErrorHandler;
import de.davelee.trams.drivers.admin.ui.DriverApplicationUI;
import de.davelee.trams.drivers.admin.ui.events.NavigationEvent;
import de.davelee.trams.drivers.admin.ui.views.AccessDeniedView;
import de.davelee.trams.drivers.admin.ui.views.LoginView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;

/**
 * This class handles exceptions which occur when using Spring Security with Vaadin.
 * @author Roland Krüger
 * Source: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/ui/security/SecurityErrorHandler.java
 */
public class SecurityErrorHandler implements ErrorHandler {

    private static Logger LOG = LoggerFactory.getLogger(SecurityErrorHandler.class);
    private EventBus eventbus;
    private Navigator navigator;

    /**
     * Create a new SecurityErrorHandler with an event bus and a navigator from Vaadin.
     * @param eventbus a <code>EventBus</code> containing events running in the system.
     * @param navigator a <code>Navigator</code> object from Vaadin.
     */
    public SecurityErrorHandler(final EventBus eventbus, final Navigator navigator) {
        this.eventbus = eventbus;
        this.navigator = navigator;
    }

    @Override
    /**
     * Process an error event e.g. person could not login.
     * @param event The <code>ErrorEvent</code> object which should be processed.
     */
    public void error(final ErrorEvent event) {
        LOG.error("Error handler caught exception {}", event.getThrowable().getClass().getName());
        if (event.getThrowable() instanceof AccessDeniedException || event.getThrowable().getCause() instanceof AccessDeniedException) {
            if (DriverApplicationUI.getCurrent().isUserAnonymous() && !navigator.getState().startsWith(LoginView.NAME)) {
                eventbus.post(new NavigationEvent(this, LoginView.loginPathForRequestedView(navigator.getState())));
            } else if (!DriverApplicationUI.getCurrent().isUserAnonymous()) {
                eventbus.post(new NavigationEvent(this, AccessDeniedView.NAME));
            }
        } else {
            // handle other exceptions a bit more graciously than this
            event.getThrowable().printStackTrace();
        }
    }
}
