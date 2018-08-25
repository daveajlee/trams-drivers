package de.davelee.trams.drivers.admin.ui.views;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import de.davelee.trams.drivers.admin.ui.DriverApplicationUI;

@SpringView(name = "")
/**
 * This view represents the redirect to welcome view (originally it was called main view).
 * @author Roland Krüger
 * Source: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/ui/views/RedirectToMainView.java
 */
public class RedirectToWelcomeView extends Navigator.EmptyView {

    @Override
    /**
     * When entering the redirect to welcome view, redirect the user to the welcome view.
     * @param viewChangeEvent a <code>ViewChangeEvent</code> object requesting the redirect.
     */
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        DriverApplicationUI.getCurrent().getNavigator().navigateTo(WelcomeView.NAME);
    }
}
