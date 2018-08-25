package de.davelee.trams.drivers.admin.ui.security;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import de.davelee.trams.drivers.security.VaadinAccessDecisionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.annotation.SecuredAnnotationSecurityMetadataSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.util.SimpleMethodInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.util.Collection;

/**
 * This class decides for a particular view whether to grant the user access or not.
 * @author Roland Krüger
 * Source: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/ui/security/ViewAccessDecisionManager.java
 */
@Component
@UIScope
public class ViewAccessDecisionManager implements Serializable{

    @Autowired
    private SpringViewProvider viewProvider;

    @Autowired
    private VaadinAccessDecisionManager accessDecisionManager;

    /**
     * Check if the user should be granted access to the requested view.
     * @param navigator a <code>Navigator</code> object from Vaadin containing the requested view.
     */
    public void checkAccessRestrictionForRequestedView(final Navigator navigator) {
        final View targetView = viewProvider.getView(navigator.getState());

        if (targetView != null) {
            final Collection<ConfigAttribute> attributes = new SecuredAnnotationSecurityMetadataSource()
                    .getAttributes(new SimpleMethodInvocation(targetView, ReflectionUtils.findMethod(View.class, "enter", ViewChangeListener.ViewChangeEvent.class)));
            try {
                accessDecisionManager.decide(SecurityContextHolder.getContext().getAuthentication(), targetView, attributes);
            } catch (AccessDeniedException adExc) {
                // must be ignored as this exception is already handled in the AccessDecisionManager
            }
        }
    }
}
