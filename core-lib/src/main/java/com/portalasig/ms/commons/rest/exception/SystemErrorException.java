package com.portalasig.ms.commons.rest.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * System Error Exception.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SystemErrorException extends RuntimeException {

    private int errorCode;

    /**
     * System Error exception constructor.
     *
     * @param errorCode error_code
     * @param message   error description
     * @param cause     actual exception
     */
    public SystemErrorException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * System Error exception constructor.
     *
     * @param errorCode error_code
     * @param message   error description
     */
    public SystemErrorException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * System Error exception constructor.
     *
     * @param message error description
     */
    public SystemErrorException(String message) {
        super(message);
        this.errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
