package com.portalasig.ms.commons.configuration.exception;

import com.portalasig.ms.commons.rest.dto.ApiError;
import com.portalasig.ms.commons.rest.exception.AccessDeniedException;
import com.portalasig.ms.commons.rest.exception.BadRequestException;
import com.portalasig.ms.commons.rest.exception.ConflictException;
import com.portalasig.ms.commons.rest.exception.InvalidTokenException;
import com.portalasig.ms.commons.rest.exception.PreconditionFailedException;
import com.portalasig.ms.commons.rest.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundHttpClientException(ResourceNotFoundException ex) {
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

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleForbiddenResourceHttpClientException(ConflictException ex) {
        return ApiError
                .builder()
                .title("Access denied")
                .status(ex.getErrorCode())
                .message(ex.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleInvalidTokenHttpClientException(ConflictException ex) {
        return ApiError
                .builder()
                .title("Invalid token")
                .status(ex.getErrorCode())
                .message(ex.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ApiError handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ApiError
                .builder()
                .title("Precondition Failed")
                .status(ex.getStatusCode().value())
                .message(ex.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        return ApiError
                .builder()
                .title("Database Integrity Violation")
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
    }

}
