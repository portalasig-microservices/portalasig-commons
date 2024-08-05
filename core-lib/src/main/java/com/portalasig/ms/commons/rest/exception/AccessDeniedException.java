package com.portalasig.ms.commons.rest.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * AccessDeniedException to include error codes/title.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccessDeniedException extends RuntimeException {

    private int errorCode;

    /**
     * AccessDeniedException constructor.
     *
     * @param message error description
     */
    public AccessDeniedException(String message) {
        super(message);
        this.errorCode = HttpStatus.FORBIDDEN.value();
    }
}
