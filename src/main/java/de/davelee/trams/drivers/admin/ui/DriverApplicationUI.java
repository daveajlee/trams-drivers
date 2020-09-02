package de.davelee.trams.drivers.admin.ui;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import de.davelee.trams.drivers.admin.ui.converters.MyConverterFactory;
import de.davelee.trams.drivers.admin.ui.events.LogoutEvent;
import de.davelee.trams.drivers.admin.ui.events.NavigationEvent;
import de.davelee.trams.drivers.admin.ui.security.SecurityErrorHandler;
import de.davelee.trams.drivers.admin.ui.security.ViewAccessDecisionManager;
import de.davelee.trams.drivers.admin.ui.views.ErrorView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;

@SpringUI(path="/admin")
@Theme("valo")
@PreserveOnRefresh
/**
 * This class manages the UI of the Driver Admin Application.
 * It is largely based on Roland Krüger's Main UI but with some adaptions from Dave Lee
 * @author Roland Krüger/Dave Lee
 * Source based on: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/ui/MainUI.java
 */
public class DriverApplicationUI extends UI {

    private static final VaadinUIService uiService = new VaadinUIService();

    @Autowired
    private SpringViewProvider viewProvider;

    @Autowired
    private ViewAccessDecisionManager viewAccessDecisionManager;

    private EventBus eventbus;

    public static DriverApplicationUI getCurrent() {
        return (DriverApplicationUI) UI.getCurrent();
    }

    @Autowired
    /**
     * Create a new DriverApplicationUI.
     */
    public DriverApplicationUI() {
        VaadinSession.getCurrent().setConverterFactory(new MyConverterFactory());
    }

    @Override
    /**
     * Initialise the User Interface by calling the welcome view.
     * @param request a <code>VaadinRequest</code> containing the internal Vaadin request.
     */
    protected void init(final VaadinRequest request) {

        buildNavigator();
        VaadinSession.getCurrent().setErrorHandler(new SecurityErrorHandler(eventbus, getNavigator()));

        viewAccessDecisionManager.checkAccessRestrictionForRequestedView(getNavigator());

        Page.getCurrent().setTitle("TraMS Driver Admin Site");

    }

    /**
     * Build the Vaadin Navigator.
     */
    private void buildNavigator() {
        Navigator navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);
        navigator.setErrorView(ErrorView.class);
        setNavigator(navigator);
    }

    /**
     * Return the event bus used in the application.
     * @return a <code>EventBus</code> object containing the event bus for this application.
     */
    public EventBus getEventbus() {
        return eventbus;
    }

    @PostConstruct
    /**
     * Initialise the event bus when setting up the application.
     */
    private void initEventbus() {
        eventbus = new EventBus("main");
        eventbus.register(this);
    }

    /**
     * Check if the user is currently anonymous i.e. not logged in.
     * @return a <code>boolean</code> which is true iff the user is currently anonymous.
     */
    public boolean isUserAnonymous() {
        return SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
    }

    @Subscribe
    /**
     * Process the user logout event by closing the VaadinSession and redirecting to the welcome page.
     * @param event a <code>LogoutEvent</code> object representing the logout event.
     */
    public void userLoggedOut(final LogoutEvent event) throws ServletException {
        ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest().logout();
        VaadinSession.getCurrent().close();
        SecurityContextHolder.clearContext(); //This is important otherwise spring security will keep session open!
        Page.getCurrent().setLocation("/admin/");
    }

    @Subscribe
    /**
     * Handle a redirect to an alternative view through a Navigation Event.
     * @oaram event a <code>NavigationEvent</code> object containing the view to redirect to.
     */
    public void handleNavigation(final NavigationEvent event) {
        getNavigator().navigateTo(event.getTarget());
    }

    /**
     * Access the VaadinUIService through a static method.
     * @return a <code>VaadinUIService</code> containing the UI Service.
     */
    public static VaadinUIService getUiService() {
        return uiService;
    }

}

