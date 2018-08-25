package de.davelee.trams.drivers.security;

import com.vaadin.ui.UI;
import de.davelee.trams.drivers.admin.ui.DriverApplicationUI;
import de.davelee.trams.drivers.admin.ui.VaadinUIService;
import de.davelee.trams.drivers.admin.ui.views.AccessDeniedView;
import de.davelee.trams.drivers.admin.ui.views.LoginView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
/**
 * This class extends the normal AccessDecisionManager in Spring Security for Vaadin.
 * @author Roland Krüger
 * Source: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/spring/security/VaadinAccessDecisionManager.java
 */
public class VaadinAccessDecisionManager implements AccessDecisionManager {

    private Logger LOG = LoggerFactory.getLogger(VaadinAccessDecisionManager.class);

    private AccessDecisionManager delegate;
    public static final VaadinUIService UI_SERVICE = DriverApplicationUI.getUiService();

    /**
     * Method to set the access decision manager delegate.
     * @param accessDecisionManager a <code>AccessDecisionManager</code> to use as the access decision manager delegate.
     */
    public void setAccessDecisionManager(final AccessDecisionManager accessDecisionManager) {
        this.delegate = accessDecisionManager;
    }

    @Override
    /**
     * Decide if the user should gain access to the desired object depending on configuration attributes and the authentication request.
     * @param authentication a <code>Authentication</code> object representing the user.
     * @param object a <code>Object</code> representing the object that the user wishes to access.
     * @param configAttributes a <code>Collection</code> of <code>ConfigAttribute</code> objects with further rules to decide access.
     */
    public void decide(final Authentication authentication, final Object object, final Collection<ConfigAttribute> configAttributes) throws AccessDeniedException {
        try {
            if (configAttributes == null) {
                // no action if an object with no access restrictions is visited
                return;
            }
            // delegate access decision itself to super class
            delegate.decide(authentication, object, configAttributes);
        } catch (AccessDeniedException adExc) {
            // we handle security exceptions in the Vaadin way, i. e. we publish appropriate events on the event bus instead
            // of redirecting to some error page (remember that we've a single-page application)
            if (UI_SERVICE.isUserAnonymous()) {
                UI_SERVICE.postNavigationEvent(this, LoginView.loginPathForRequestedView(UI.getCurrent().getNavigator().getState()));
                throw adExc;
            } else {
                // if she is logged in but doesn't have adequate access rights, send her to the access denied view
                UI_SERVICE.postNavigationEvent(this, AccessDeniedView.NAME);
                throw adExc;
            }
        } catch (Exception exc) {
            LOG.info("Exception after authentication decision: {}", exc.getClass().getName());
            exc.printStackTrace();
        }
    }

    @Override
    /**
     * Indicates whether this AccessDecisionManager is able to process authorization requests presented with the
     * passed ConfigAttribute.
     * @param attribute a <code>ConfigAttribute</code> that has been configured.
     * @return a <code>boolean</code> which is true iff this AccessDecisionManager can support the configuration attribute.
     */
    public boolean supports(final ConfigAttribute attribute) {
        return delegate.supports(attribute);
    }

    @Override
    /**
     * Indicates whether this AccessDecisionManager is able to provide access control decisions for this object type.
     * @param class a <code>Class</code> that is being queried.
     * @return a <code>boolean</code> which is true iff this AccessDecisionManager can process this class.
     */
    public boolean supports(final Class<?> clazz) {
        return delegate.supports(clazz);
    }
}
