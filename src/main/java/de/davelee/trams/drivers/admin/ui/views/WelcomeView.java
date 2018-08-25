package de.davelee.trams.drivers.admin.ui.views;

import com.google.common.eventbus.Subscribe;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import de.davelee.trams.drivers.admin.ui.DriverApplicationUI;
import de.davelee.trams.drivers.admin.ui.components.LogoutLink;
import de.davelee.trams.drivers.admin.ui.events.LogoutEvent;
import de.davelee.trams.drivers.admin.ui.events.UserLoggedInEvent;
import de.davelee.trams.drivers.security.User;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringView(name = WelcomeView.NAME)
/**
 * The Welcome view is the first view that the user sees in the admin application. Formerly it was called MainView.
 * @author Roland Krüger
 * Source: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/ui/views/MainView.java
 */
public class WelcomeView extends AbstractView {

    public final static String NAME = "welcome";

    private ObjectProperty<String> welcomeLabelText;
    private LogoutLink logoutLink;

    /**
     * Create a new WelcomeView with the components that should be displayed to the user.
     */
    public WelcomeView() {
        welcomeLabelText = new ObjectProperty<>("");

        updateWelcomeMessage();
        Label welcomeLabel = new Label(welcomeLabelText, ContentMode.HTML);

        addComponent(welcomeLabel);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);

        logoutLink = new LogoutLink();
        logoutLink.updateVisibility();
        horizontalLayout.addComponent(logoutLink);

        Link adminLink = new Link("Admin page", new ExternalResource("#!" + DriverEditorView.NAME));
        adminLink.setIcon(FontAwesome.LOCK);
        horizontalLayout.addComponent(adminLink);

        addComponent(horizontalLayout);

        registerWithEventbus();
    }

    /**
     * Update the welcome message depending on whether the user is logged in or not.
     */
    private void updateWelcomeMessage() {
        String username = null;
        if (!DriverApplicationUI.getCurrent().isUserAnonymous()) {
            final User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            username = principal.getFullName();
        }

        welcomeLabelText
                .setValue(username == null ? "<h1>Welcome to TraMS Driver Admin Site</h1><hr/>You're currently not logged in. Please click on admin page to log in.<hr/>"
                        : "<h1>Welcome " + username + "!</h1><hr/>");
    }

    @Subscribe
    /**
     * User has logged in so update login message and make logout link visible.
     * @param event a <code>UserLoggedInEvent</code> representing the event that the user logged in.
     */
    public void userLoggedIn(UserLoggedInEvent event) {
        updateWelcomeMessage();
        logoutLink.updateVisibility();
    }

    @Subscribe
    /**
     * User has logged out so update login message and hide logout link.
     * @param event a <code>UserLoggedInEvent</code> representing the event that the user logged out.
     */
    public void userLoggedOut(LogoutEvent event) {
        updateWelcomeMessage();
        logoutLink.updateVisibility();
    }

    @Override
    /**
     * The enter method is not currently used.
     * @param event a <code>ViewChangeEvent</code> object which will be ignored.
     */
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
