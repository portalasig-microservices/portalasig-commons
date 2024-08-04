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
public class BadRequestException extends RuntimeException {

    private int errorCode;

    /**
     * Bad Request exception constructor.
     *
     * @param message    error description
     */
    public BadRequestException(String message) {
        super(message);
        this.errorCode = HttpStatus.BAD_REQUEST.value();
    }
}
