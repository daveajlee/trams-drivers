package de.davelee.trams.drivers.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@Order(200)
/**
 * This class manages the configuration of Spring Security for Vaadin.
 * @author Roland Krüger
 * Source: https://github.com/rolandkrueger/vaadin-by-example/blob/master/en/architecture/SpringBootSecurity/src/main/java/de/oio/spring/security/SecurityConfiguration.java
 */
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static Logger LOG = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Autowired
    private UserDetailsService userDetailService;

    @Autowired
    /**
     * Configure the user detail service to use for authentication purposes.
     * @oaram auth a <code>AuthenticationManagerBuilder</code> to configure.
     */
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        auth.userDetailsService(userDetailService).and().authenticationProvider(authenticationProvider);
    }

    /**
     * Configure Spring Security for this application.
     * @param http a <code>HttpSecurity</code> object which can be configured.
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http
                // permit access to any resource, access restrictions are handled at the level of Vaadin views
                .authorizeRequests()
                .antMatchers("/**").permitAll()
        .and()
                        // disable CSRF (Cross-Site Request Forgery) since Vaadin implements its own mechanism for this
                .csrf().disable()
                // let Vaadin be responsible for creating and managing its own sessions
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
        .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
        // @formatter:on
    }

    @Bean
    /**
     * Create a new AuthenticationEntryPoint whih can handle errors in Authentication.
     * @return a <code>AuthenticationEntryPoint</code> object.
     */
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                final Throwable cause = exception.getCause();
                LOG.error("An error occurred: {}", exception.getClass().getName());
                exception.printStackTrace();
                if (cause != null) {
                    LOG.error("        Caused by: {}", cause.getClass().getName());
                }
                response.sendRedirect("/error");
            }
        };
    }
}
