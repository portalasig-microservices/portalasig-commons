package com.portalasig.ms.commons.rest.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.NoSuchElementException;

/**
 * Not Found exception to include error codes/title.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NotFoundException extends NoSuchElementException {

    private int errorCode;

    /**
     * Not Found Exception exception constructor.
     *
     * @param message error description
     */
    public NotFoundException(String message) {
        super(message);
        this.errorCode = HttpStatus.NOT_FOUND.value();
    }
}
