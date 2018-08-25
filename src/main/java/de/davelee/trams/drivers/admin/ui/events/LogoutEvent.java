package de.davelee.trams.drivers.admin.ui.events;

import java.util.EventObject;

/**
 * This class represents the logout event which happens when a user logs out of the system.
 * @author Roland Krüger.
 * Source: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/ui/events/LogoutEvent.java
 */
public class LogoutEvent extends EventObject {

	/**
	 * Create a new logout event.
	 * @param source the <code>Object</code> on which the event initially occurred.
	 */
	public LogoutEvent(final Object source) {
		super(source);
	}
}
