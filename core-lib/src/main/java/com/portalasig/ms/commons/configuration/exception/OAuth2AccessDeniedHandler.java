package com.portalasig.ms.commons.configuration.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portalasig.ms.commons.rest.dto.ApiError;
import com.portalasig.ms.commons.rest.security.CurrentAuthentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.portalasig.ms.commons.constants.RestConstants.APPLICATION_JSON;
import static com.portalasig.ms.commons.constants.RestConstants.UNKNOWN_USER;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;
    private final CurrentAuthentication currentAuthentication;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
            throws IOException {
        String userName = currentAuthentication.getClientId();
        String user = userName != null ? userName : UNKNOWN_USER;
        log.warn("User: {} attempted to access the protected URL: {}", user, request.getRequestURI());
        ApiError apiError = ApiError
                .builder()
                .title("Forbidden")
                .status(HttpStatus.FORBIDDEN.value())
                .message(ex.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
        response.setStatus(response.SC_FORBIDDEN);
        response.setContentType(APPLICATION_JSON);
        response.getWriter().write(objectMapper.writeValueAsString(apiError));
    }
}
