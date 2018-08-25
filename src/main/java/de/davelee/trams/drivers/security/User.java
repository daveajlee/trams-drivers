package de.davelee.trams.drivers.security;

import com.google.common.base.MoreObjects;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents a user consisting of username, password and full name.
 * @author Roland Krüger/Dave Lee
 * Adapted from original source: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/model/User.java
 */
public class User implements UserDetails {
    private String username;
    private String password;
    private String fullName;

    /**
     * Default constructor which creates a new user without setting any attributes.
     */
    public User() {
    }

    @Override
    /**
     * Is an account still active? Since accounts currently cannot be disabled, this method always returns true.
     * @return a <code>boolean</code> which is true iff the account is still active.
     */
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    /**
     * Is an account not locked? Since accounts currently cannot be locked, this method always returns true.
     * @return a <code>boolean</code> which is true iff the account is not locked.
     */
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    /**
     * Are the credentials for the account still valid? Since credentials currently do not expire, this method always
     * returns true.
     * @return a <code>boolean</code> which is true iff the credentials have not expired.
     */
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    /**
     * Is the account enabled? Since accounts are currently enabled by default, this method always returns true.
     * @return a <code>boolean</code> which is true iff the account is enabled.
     */
    public boolean isEnabled() {
        return true;
    }

    /**
     * Get the username for this user as a String.
     * @return a <code>String</code> containing the username of this user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username for this user as a String.
     * @param username a <code>String</code> containing the username of this user.
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    /**
     * Get the authorities (name of roles) that this user has (currently all users are admins).
     * @return a <code>Collection</code> object containing the roles that the user has.
     */
    public Collection<Role> getAuthorities() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_ADMIN"));
        return roles;
    }

    /**
     * Get the encrypted password for this user.
     * @return a <code>String</code> containing the encrypted password of this user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the encrypted password for this user.
     * @param password a <code>String</code> containing the encrypted password of this user.
     */
    private void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Set a new password for this user which will be automatically encrypted.
     * @param password a <code>String</code> containing the new password for this user.
     */
    public void setUnencryptedPassword(final String password) {
        setPassword(new BCryptPasswordEncoder().encode(password));
    }

    /**
     * Get the full name for this user.
     * @return a <code>String</code> containing the full name for this user.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Set the full name for this user.
     * @param fullName a <code>String</code> containing the full name for this user.
     */
    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    @Override
    /**
     * Return a String representation of this User containing username, full name and roles.
     * @return a <code>String</code> containing username, full name and roles.
     */
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .add("fullname", fullName)
                .add("roles", getAuthorities())
                .toString();
    }

    /**
     * Check if user has one of several required roles.
     * @param requiredRoles a <code>String</code> array of required roles.
     * @return a <code>boolean</code> which is true iff the user has one of several required roles.
     */
    public boolean hasAuthority(final String[] requiredRoles) {
        if (getAuthorities().isEmpty() && requiredRoles.length > 0) {
            return false;
        }

        for (String requiredRole : requiredRoles) {
            for (Role role : getAuthorities()) {
                if (role.getAuthority().equals(requiredRole)) {
                    return true;
                }
            }
        }
        return false;
    }
}
