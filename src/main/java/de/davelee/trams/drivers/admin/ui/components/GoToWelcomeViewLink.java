package de.davelee.trams.drivers.admin.ui.components;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Link;
import de.davelee.trams.drivers.admin.ui.views.WelcomeView;

/**
 * This class creates the go back to welcome view link.
 * @author Roland Krüger.
 * Source: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/ui/components/GoToMainViewLink.java
 */
public class GoToWelcomeViewLink extends CustomComponent {

	/**
	 * Create a new go back to welcome view link.
	 * The link consists of text and an icon with the home logo.
	 */
	public GoToWelcomeViewLink() {
		Link goBackLink = new Link("Go back to welcome", new ExternalResource("#!" + WelcomeView.NAME));
		goBackLink.setIcon(FontAwesome.HOME);
		setCompositionRoot(goBackLink);
	}

}
