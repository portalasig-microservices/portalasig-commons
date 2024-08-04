package com.portalasig.ms.commons.rest.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Bad Request Exception.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ConflictException extends RuntimeException {

    private int errorCode;

    /**
     * Conflict Exception exception constructor.
     *
     * @param message    error description
     */
    public ConflictException(String message) {
        super(message);
        this.errorCode = HttpStatus.CONFLICT.value();
    }
}
