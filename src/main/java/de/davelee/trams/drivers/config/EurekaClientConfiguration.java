package de.davelee.trams.drivers.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!local")
@Configuration
@EnableDiscoveryClient
/**
 * Configure Eureka so that it runs on every profile apart from development
 * @author Dave Lee
 */
public class EurekaClientConfiguration {
}
