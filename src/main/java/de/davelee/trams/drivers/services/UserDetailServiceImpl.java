package de.davelee.trams.drivers.services;

import de.davelee.trams.drivers.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
/**
 * This class loads users for the Vaadin Admin Application.
 * @author Roland Krüger/Dave Lee
 * Source based on: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/service/UserDetailServiceImpl.java
 */
public class UserDetailServiceImpl implements UserDetailsService {

    private Logger LOG = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Override
    /**
     * Load a user for a particular username. Currently only a single user is supported.
     * @param username a <code>String</code> containing the username to retrieve the user details for.
     * @return a <code>UserDetails</code> object containing the retrieved user.
     * @throws a <code>UsernameNotFoundException</code> if the user is not found.
     */
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Objects.requireNonNull(username);

        User user = null;
        if ( username.contentEquals("admin")) {
            user = new User();
            user.setUsername(username);
            user.setUnencryptedPassword("admin");
            user.setFullName("Max Mustermann");
        }

        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " is unknown");
        }

        LOG.debug("Loaded user {}", user);

        return user;
    }
}
