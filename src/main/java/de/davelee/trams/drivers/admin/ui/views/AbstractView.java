package de.davelee.trams.drivers.admin.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import de.davelee.trams.drivers.admin.ui.DriverApplicationUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;

/**
 * This is an abstract class which provides helper methods to building a view in Vaadin.
 * @author Roland Krüger
 * Source: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/ui/views/AbstractView.java
 */
public abstract class AbstractView extends Panel implements View {
	private Logger LOG = LoggerFactory.getLogger(AbstractView.class);

	private VerticalLayout layout;

	/**
	 * Create a new AbstractView which uses a vertical layout.
	 */
	public AbstractView() {
		setSizeFull();
		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		setContent(layout);
	}

	/**
	 * Add the specified component to the layout &amp; view.
	 * @param component a <code>Component</code> object representing the component to add to the view.
	 */
	public void addComponent(final Component component) {
		layout.addComponent(component);
	}

	/**
	 * Register this view with the event bus.
	 */
	protected void registerWithEventbus() {
		DriverApplicationUI.getCurrent().getEventbus().register(this);
	}

	@PreDestroy
	/**
	 * Destroy this view by unregistering the component with the event bus.
	 */
	public void destroy() {
		LOG.debug("About to destroy {}", getClass().getName());
		DriverApplicationUI.getCurrent().getEventbus().unregister(this);
	}
}
