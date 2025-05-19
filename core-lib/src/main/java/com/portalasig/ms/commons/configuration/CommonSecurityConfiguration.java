package com.portalasig.ms.commons.configuration;

import com.portalasig.ms.commons.rest.security.CurrentAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

/**
 * Common security configuration for the application.
 * <p>
 * This configuration:
 * <ul>
 *   <li>Enables method-level security annotations.</li>
 *   <li>Customizes JWT authority extraction using the "authorities" claim without a prefix.</li>
 *   <li>Provides a bean for accessing the current authentication context.</li>
 * </ul>
 */
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class CommonSecurityConfiguration {

    /**
     * Configures a JwtAuthenticationConverter to extract authorities from the "authorities" claim without a prefix.
     *
     * @return a customized JwtAuthenticationConverter bean
     */
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthoritiesClaimName("authorities");
        authoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter converterResponse = new JwtAuthenticationConverter();
        converterResponse.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return converterResponse;
    }

    /**
     * Provides a bean for accessing the current authentication context.
     *
     * @return a CurrentAuthentication bean
     */
    @Bean
    public CurrentAuthentication currentAuthentication() {
        return new CurrentAuthentication();
    }
}