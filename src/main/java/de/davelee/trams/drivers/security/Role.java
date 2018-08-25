package de.davelee.trams.drivers.security;

import org.springframework.security.core.GrantedAuthority;
import java.util.Objects;

/**
 * This class represents a role which a user can have (e.g. admin).
 * @author Roland Krüger
 * Source: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/model/Role.java
 */
public class Role implements GrantedAuthority {
    private String authority;

    /**
     * Default constructor - initialise the role without a name.
     */
    public Role() {}

    /**
     * Create a new role which consists of an authority with the name of the role.
     * @param authority a <code>String</code> containing the name of the role to create.
     */
    public Role(final String authority) {
        setAuthority(authority);
    }

    /**
     * Set the authority/name for this role.
     * @param authority a <code>String</code> containing the name of the role.
     */
    public void setAuthority(String authority) {
        Objects.requireNonNull(authority);
        this.authority = authority;
    }

    @Override
    /**
     * Return the authority/name for this role.
     * @return a <code>String</code> containing the name of the role.
     */
    public String getAuthority() {
        return authority;
    }

    @Override
    /**
     * Return a string representation of this role containing the name/authority of this role.
     * @return a <code>String</code> containing the name of the role.
     */
    public String toString() {
        return authority;
    }
}
