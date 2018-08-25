package de.davelee.trams.drivers.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
/**
 * Class to configure security through the VaadinAccessDecisionManager.
 * @author Roland Krüger
 * Source: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/spring/security/MethodSecurityConfig.java
 */
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Autowired
    private VaadinAccessDecisionManager vaadinAccessDecisionManager;

    @Override
    /**
     * Configure the Access Decision Manager with vaadin and return the configured Access Decision Manager.
     * @return a <code>AccessDecisionManager</code> object configured correctly.
     */
    protected AccessDecisionManager accessDecisionManager() {
        vaadinAccessDecisionManager.setAccessDecisionManager(super.accessDecisionManager());
        return vaadinAccessDecisionManager;
    }
}
