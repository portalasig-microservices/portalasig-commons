package com.portalasig.ms.commons.rest.exception;

import jakarta.validation.ValidationException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


/**
 * Precondition failed exception.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PreconditionFailedException extends ValidationException {

    private int errorCode;

    /**
     * Precondition Failed exception constructor.
     *
     * @param message error description
     */
    public PreconditionFailedException(String message) {
        super(message);
        this.errorCode = HttpStatus.PRECONDITION_FAILED.value();
    }
}
