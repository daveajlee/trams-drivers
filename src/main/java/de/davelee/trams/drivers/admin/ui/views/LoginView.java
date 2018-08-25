package de.davelee.trams.drivers.admin.ui.views;

import com.google.common.eventbus.EventBus;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import de.davelee.trams.drivers.admin.ui.DriverApplicationUI;
import de.davelee.trams.drivers.admin.ui.components.GoToWelcomeViewLink;
import de.davelee.trams.drivers.admin.ui.events.NavigationEvent;
import de.davelee.trams.drivers.security.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@SpringView(name = LoginView.NAME)
/**
 * This view represents the login view when a user wants to login to the system.
 * @author Roland Krüger
 * Source: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/ui/views/LoginView.java
 */
public class LoginView extends AbstractView implements Button.ClickListener {

    public final static String NAME = "login";

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    private String forwardTo;
    private TextField nameTF;
    private PasswordField passwordTF;

    /**
     * Create a new login view with the login form allowing the user to enter their credentials.
     */
    public LoginView() {
        addComponent(new Label(
                "Please enter your credentials:"));
        nameTF = new TextField();
        nameTF.setRequired(true);
        nameTF.focus();

        passwordTF = new PasswordField();
        passwordTF.setRequired(true);

        addComponent(nameTF);
        addComponent(passwordTF);

        Button loginButton = new Button("Login");
        loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        loginButton.addClickListener(this);
        loginButton.setIcon(FontAwesome.SIGN_IN);
        addComponent(loginButton);

        addComponent(new GoToWelcomeViewLink());
    }

    @Override
    /**
     * When entering the login view, save the source view so that we can forward to it after login.
     * @param event a <code>ViewChangeEvent</code> object containing the source view.
     */
    public void enter(final ViewChangeEvent event) {
        forwardTo = event.getParameters();
    }

    @Override
    /**
     * Submit the login form and process the login event by logging the user into the system if their credentials
     * are correct.
     * @param event a <code>ClickEvent</code> containing the event of clicking the login button.
     */
    public void buttonClick(final ClickEvent event) {
        if (nameTF.isValid() && passwordTF.isValid()) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(nameTF.getValue(), passwordTF.getValue());
            if (userAuthenticationService.loginUser(authentication)) {
                EventBus eventbus = DriverApplicationUI.getCurrent().getEventbus();
                eventbus.post(new NavigationEvent(this, forwardTo));
            } else {
                passwordTF.setValue("");
            }
        } else {
            if (nameTF.isEmpty()) {
                nameTF.setRequiredError("Please enter your username");
            }
            if (passwordTF.isEmpty()) {
                passwordTF.setRequiredError("Please enter your password");
            }
        }
    }

    /**
     * Make the login path for a requested view out of login and requestedViewName.
     * @param requestedViewName a <code>String</code> containing the name of the requested view.
     * @return a <code>String</code> containing the full login path for this view.
     */
    public static String loginPathForRequestedView(final String requestedViewName) {
        return NAME + "/" + requestedViewName;
    }
}
