package com.portalasig.ms.commons.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring configuration for setting up CORS (Cross-Origin Resource Sharing).
 * <p>
 * This configuration:
 * <ul>
 *   <li>Allows requests from Front-End.</li>
 *   <li>Permits GET, POST, PUT, DELETE, and PATCH HTTP methods.</li>
 *   <li>Allows all headers.</li>
 *   <li>Supports credentials in requests.</li>
 * </ul>
 */
@Configuration
public class CorsConfiguration {

    /**
     * Configures global CORS mappings for the application.
     *
     * @return a WebMvcConfigurer bean with CORS settings
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8080")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}