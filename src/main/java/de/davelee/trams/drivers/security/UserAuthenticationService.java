package de.davelee.trams.drivers.security;

import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
/**
 * This class manages the ability for users to login using Spring Security.
 * @author Roland Krüger
 * Source: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/spring/security/UserAuthenticationService.java
 */
public class UserAuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Attempt to authenticate the user based on the supplied authentication request.
     * @param authenticationRequest a <code>Authentication</code> object representing the authentication request.
     * @return a <code>boolean</code> which is true iff the authentication could be processed successfully.
     */
    public boolean loginUser(final Authentication authenticationRequest) {
        try {
            final Authentication authentication = authenticationManager.authenticate(authenticationRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        } catch (AuthenticationException aExc) {
            Notification.show("Authentication error", "Could not authenticate", Notification.Type.ERROR_MESSAGE);
            return false;
        }
    }
}
