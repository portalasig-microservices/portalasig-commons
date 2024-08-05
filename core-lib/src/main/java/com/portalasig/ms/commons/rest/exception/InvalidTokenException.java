package com.portalasig.ms.commons.rest.exception;

import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;

/**
 * InvalidTokenException to include error codes/title.
 */
@EqualsAndHashCode(callSuper = true)
public class InvalidTokenException extends AccessDeniedException {

    private final int errorCode;

    /**
     * InvalidTokenException constructor.
     *
     * @param message error description
     */
    public InvalidTokenException(String message) {
        super(message);
        this.errorCode = HttpStatus.FORBIDDEN.value();
    }
}
