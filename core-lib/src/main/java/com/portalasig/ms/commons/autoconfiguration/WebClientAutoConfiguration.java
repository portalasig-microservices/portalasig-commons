package com.portalasig.ms.commons.autoconfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration for WebClient beans.
 */
@Configuration
@RequiredArgsConstructor
public class WebClientAutoConfiguration {

    private final ObjectMapper objectMapper;

    /**
     * WebClient with client_credentials flow.
     */
    @Bean("clientCredentialsWebClient")
    @Primary
    public WebClient clientCredentialsWebClient(
            AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager oauth2AuthorizedClientManager) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth2Client =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(oauth2AuthorizedClientManager);
        oauth2Client.setDefaultClientRegistrationId("portalasig_engine");
        return WebClient.builder()
                .filter(oauth2Client)
                .codecs(configurer -> configurer.defaultCodecs()
                        .jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper)))
                .codecs(configurer -> configurer.defaultCodecs()
                        .jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper)))
                .build();
    }

    /**
     * WebClient with bearer token from security context.
     */
    @Bean("jwtTokenWebClient")
    public WebClient jwtTokenWebClient(ExchangeFilterFunction bearerAuthFromSecurityContext) {
        return WebClient.builder()
                .filter(bearerAuthFromSecurityContext)
                .codecs(configurer -> configurer.defaultCodecs()
                        .jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper)))
                .codecs(configurer -> configurer.defaultCodecs()
                        .jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper)))
                .build();
    }

    /**
     * Extracts JWT from SecurityContext and adds it as Bearer token.
     */
    @Bean
    public ExchangeFilterFunction bearerAuthFromSecurityContext() {
        return (request, next) -> {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth instanceof JwtAuthenticationToken jwtAuth) {
                return next.exchange(
                        ClientRequest.from(request)
                                .headers(headers -> headers.setBearerAuth(jwtAuth.getToken().getTokenValue()))
                                .build());
            }
            return next.exchange(request);
        };
    }

    /**
     * Registers OAuth2 client for client_credentials flow.
     */
    @Bean
    public ReactiveClientRegistrationRepository clientRegistrations(
            @Value("${spring.security.oauth2.client.provider.portalasig_engine.token-uri}")
            String tokenUri,
            @Value("${spring.security.oauth2.client.registration.portalasig_engine.client-id}")
            String clientId,
            @Value("${spring.security.oauth2.client.registration.portalasig_engine.client-secret}")
            String clientSecret,
            @Value("${spring.security.oauth2.client.registration.portalasig_engine.authorization-grant-type}")
            String authGrantType) {

        ClientRegistration registration = ClientRegistration
                .withRegistrationId("portalasig_engine")
                .tokenUri(tokenUri)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .authorizationGrantType(new AuthorizationGrantType(authGrantType))
                .build();

        return new InMemoryReactiveClientRegistrationRepository(registration);
    }

    /**
     * Sets up OAuth2 client manager.
     */
    @Bean
    public AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager(
            ReactiveClientRegistrationRepository clientRegistrationRepository) {

        InMemoryReactiveOAuth2AuthorizedClientService clientService =
                new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrationRepository);

        ReactiveOAuth2AuthorizedClientProvider authorizedClientProvider =
                ReactiveOAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build();

        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, clientService);

        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
        return authorizedClientManager;
    }
}
