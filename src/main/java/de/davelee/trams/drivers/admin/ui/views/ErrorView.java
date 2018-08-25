package de.davelee.trams.drivers.admin.ui.views;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import de.davelee.trams.drivers.admin.ui.components.GoToWelcomeViewLink;

@SpringView(name = ErrorView.NAME)
/**
 * This view represents the error view when a user enters a view name which does not exist.
 * @author Roland Krüger
 * Source: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/ui/views/ErrorView.java
 */
public class ErrorView extends AbstractView {

	public final static String NAME = "error";

	private ObjectProperty<String> errorMessage;

	/**
	 * Create a new error view containing an empty error message which can be updated with the entered view name.
	 */
	public ErrorView() {
		errorMessage = new ObjectProperty<String>("");
		Label errorMessageLabel = new Label(errorMessage, ContentMode.HTML);
		addComponent(errorMessageLabel);
		addComponent(new GoToWelcomeViewLink());
	}

	@Override
	/**
	 * Display the error message based on the view name that the user entered.
	 * @param event a <code>ViewChangeEvent</code> object including the name of the view that the user entered.
	 */
	public void enter(ViewChangeEvent event) {
		errorMessage
				.setValue("<h1>Oops, page not found!</h1><hr/>"
						+ "Unfortunately, the page with name <em>"
						+ event.getViewName()
						+ "</em> is unknown to me :-( <br/>Please try something different.");
	}
}
