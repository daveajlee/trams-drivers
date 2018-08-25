package de.davelee.trams.drivers.admin.ui.events;

import com.google.common.base.Preconditions;
import de.davelee.trams.drivers.security.User;

import java.util.EventObject;

/**
 * This class represents a login event where the user successfully logins in to the application.
 * @author Roland Krüger
 * Source: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/ui/events/UserLoggedInEvent.java
 */
public class UserLoggedInEvent extends EventObject {

	private User user;

	/**
	 * Create a new login event with the source and the user who wishes to login.
	 * @param source The <code>Object</code> on which the Event initially occurred.
	 * @param user a <code>User</code> object representing the user who wishes to login.
	 */
	public UserLoggedInEvent(final Object source, final User user) {
		super(source);
		Preconditions.checkNotNull(user);
		this.user = user;
	}

	/**
	 * Return the user who wishes to login.
	 * @return a <code>User</code> object representing the user who wishes to login.
	 */
	public User getUser() {
		return user;
	}
}
