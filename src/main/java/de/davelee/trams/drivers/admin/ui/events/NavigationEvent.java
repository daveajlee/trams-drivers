package de.davelee.trams.drivers.admin.ui.events;

import java.util.EventObject;

/**
 * This class represents a navigation event where the user wants to move to a new link.
 * @author Roland Krüger
 * Source: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/ui/events/NavigationEvent.java
 */
public class NavigationEvent extends EventObject {

	private String target;

	/**
	 * Create a new navigation element with a source and a target.
	 * @param source the <code>Object</code> on which the Event initially occurred.
	 * @param target a <code>String</code> containing the link that the user now wishes to view.
	 */
	public NavigationEvent(final Object source, final String target) {
		super(source);
		this.target = target == null ? "" : target;
	}

	/**
	 * Return the link that the user now wishes to view as a String.
	 * @return a <code>String</code> containing the link that the user now wishes to view.
	 */
	public String getTarget() {
		return target;
	}
}
