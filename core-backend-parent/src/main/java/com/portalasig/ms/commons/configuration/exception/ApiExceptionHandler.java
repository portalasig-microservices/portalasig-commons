package com.portalasig.ms.commons.configuration.exception;

import com.portalasig.ms.commons.rest.dto.ApiError;
import com.portalasig.ms.commons.rest.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundHttpClientException(NotFoundException ex) {
        return ApiError
                .builder()
                .title("Not Found")
                .status(ex.getErrorCode())
                .message(ex.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(PreconditionFailedException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ApiError handlePreconditionFailedHttpClientException(PreconditionFailedException ex) {
        return ApiError
                .builder()
                .title("Precondition Failed")
                .status(ex.getErrorCode())
                .message(ex.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlePreconditionFailedHttpClientException(BadRequestException ex) {
        return ApiError
                .builder()
                .title("Bad Request")
                .status(ex.getErrorCode())
                .message(ex.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictHttpClientException(ConflictException ex) {
        return ApiError
                .builder()
                .title("Conflict Exception")
                .status(ex.getErrorCode())
                .message(ex.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(SystemErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleInternalServerErrorHttpClientException(ConflictException ex) {
        return ApiError
                .builder()
                .title("Internal Server Error")
                .status(ex.getErrorCode())
                .message(ex.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
    }
}
