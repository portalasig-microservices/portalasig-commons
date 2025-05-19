package com.portalasig.ms.commons.rest.security;

import com.portalasig.ms.commons.constants.Authority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class to extract additional information from the current authentication context.
 * <p>
 * This class provides methods to:
 * <ul>
 *   <li>Retrieve the current {@link Authentication} object.</li>
 *   <li>Get authorities (roles) of the authenticated user as {@link Authority} enums.</li>
 *   <li>Access the current JWT token.</li>
 *   <li>Extract the user's identity from the JWT claims.</li>
 *   <li>Get the client ID (username) of the authenticated user.</li>
 *   <li>Check if the user has a specific authority or any of a set of authorities.</li>
 * </ul>
 */
@RequiredArgsConstructor
@Slf4j
public class CurrentAuthentication {

    /**
     * Retrieves the current {@link Authentication} object from the security context.
     *
     * @return the current {@link Authentication} object, or null if there is no authentication information.
     */
    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Gets the authorities (roles) for the current authenticated user.
     *
     * @return a set of authorities (roles) as {@link Authority} enums, or an empty set if the user has no authorities.
     * @throws RuntimeException if authority mapping fails
     */
    public Set<Authority> getAuthorities() {
        try {
            return getAuthentication()
                    .getAuthorities()
                    .stream()
                    .map(auth -> Authority.valueOf(auth.getAuthority()))
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error: user authorities are not healthy, error while attempting to map authorities string",
                    e
            );
        }
    }

    /**
     * Retrieves the current JWT token from the authentication principal.
     *
     * @return the {@link Jwt} token of the authenticated user
     */
    public Jwt getJwtToken() {
        return (Jwt) getAuthentication().getPrincipal();
    }

    /**
     * Extracts the user's identity from the JWT claims.
     *
     * @return the identity as a {@link Long}, or null if not present or not a valid number
     */
    public Long getIdentity() {
        String identity = (String) this.getJwtToken().getClaims().getOrDefault("username", null);
        try {
            return identity != null ? Long.parseLong(identity) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Gets the client ID (username) of the authenticated user.
     *
     * @return the client ID as a string
     */
    public String getClientId() {
        return getAuthentication().getName();
    }

    /**
     * Checks if the current user has the specified authority.
     *
     * @param authority the authority to check
     * @return true if the user has the authority, false otherwise
     */
    public boolean hasAuthority(Authority authority) {
        return getAuthorities()
                .stream()
                .anyMatch(auth -> auth.equals(authority));
    }

    /**
     * Checks if the current user has any of the specified authorities.
     *
     * @param authorities the authorities to check
     * @return true if the user has any of the authorities, false otherwise
     */
    public boolean hasAnyAuthority(Authority... authorities) {
        Set<Authority> authoritySet = new HashSet<>(List.of(authorities));
        return getAuthorities()
                .stream()
                .anyMatch(authoritySet::contains);
    }
}