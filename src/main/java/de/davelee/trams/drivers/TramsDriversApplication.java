package de.davelee.trams.drivers;

import com.google.common.base.Predicate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * This class represents the Spring Boot application for TraMS Driver Management.
 * @author Dave Lee
 */
@SpringBootApplication
@EnableSwagger2
@ComponentScan
@EnableJpaRepositories("de.davelee.trams.drivers.repository")
public class TramsDriversApplication {

    /**
     * Main method to start the application.
     * @param args a <code>String</code> array of arguments which are not presently used.
     */
    public static void main ( String[] args ) {
        SpringApplication.run(TramsDriversApplication.class, args);
    }

    @Bean
    /**
     * Configure Swagger to display appropriate information.
     * @return a <code>Docket</code> object containing the configured information.
     */
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("operations")
                .apiInfo(apiInfo())
                .select()
                .paths(paths())
                .build();
    }

    /**
     * Only map urls starting with driver.
     * @return a <code>Predicate</code> object containing the configuration of limited urls.
     */
    private Predicate<String> paths() {
        return or (
                regex("/driver.*")
        );
    }

    /**
     * Return an API Info object with the configured information for the Swagger UI.
     * @return a <code>ApiInfo</code> object with the configured information for the Swagger UI.
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("TraMS Driver Rest API")
                .description("Rest API for TraMS Driver")
                .termsOfServiceUrl("http://www.davelee.de")
                .contact("Dave Lee")
                .license("TraMS Website")
                .licenseUrl("http://www.davelee.de")
                .version("0.1")
                .build();
    }

}
