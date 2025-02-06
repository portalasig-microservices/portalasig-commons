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
     * @return a set of authorities (roles) as strings, or an empty list if the user has no authorities.
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
                    "Error user authorities is not healthy, error while attempting to map authorities string",
                    e
            );
        }
    }

    public Jwt getJwtToken() {
        return (Jwt) getAuthentication().getPrincipal();
    }

    public Long getIdentity() {
        Authentication authentication = this.getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            Object identity = jwt.getClaims().get("identity");
            if (identity instanceof Number) {
                return ((Number) identity).longValue();
            }
        }
        return null;
    }


    public String getClientId() {
        return getAuthentication().getName();
    }

    public boolean hasAuthority(Authority authority) {
        return getAuthorities()
                .stream()
                .anyMatch(auth -> auth.equals(authority));
    }

    public boolean hasAnyAuthority(Authority... authorities) {
        Set<Authority> authoritySet = new HashSet<>(List.of(authorities));
        return getAuthorities()
                .stream()
                .anyMatch(authoritySet::contains);
    }
}