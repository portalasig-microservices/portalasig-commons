package com.portalasig.ms.commons.configuration.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portalasig.ms.commons.rest.dto.ApiError;
import com.portalasig.ms.commons.rest.exception.AccessDeniedException;
import com.portalasig.ms.commons.rest.exception.BadRequestException;
import com.portalasig.ms.commons.rest.exception.ConflictException;
import com.portalasig.ms.commons.rest.exception.InvalidTokenException;
import com.portalasig.ms.commons.rest.exception.PreconditionFailedException;
import com.portalasig.ms.commons.rest.exception.ResourceNotFoundException;
import com.portalasig.ms.commons.rest.exception.SystemErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;

/**
 * Global exception handler for API responses.
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    /**
     * ObjectMapper used for deserializing error responses.
     */
    private final ObjectMapper objectMapper;

    /**
     * Handles ResourceNotFoundException.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundHttpClientException(ResourceNotFoundException ex) {
        return ApiError.builder()
                .title("Not Found")
                .status(ex.getErrorCode())
                .message(ex.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
    }

    /**
     * Handles PreconditionFailedException.
     */
    @ExceptionHandler(PreconditionFailedException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ApiError handlePreconditionFailedHttpClientException(PreconditionFailedException ex) {
        return ApiError.builder()
                .title("Precondition Failed")
                .status(ex.getErrorCode())
                .message(ex.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
    }

    /**
     * Handles BadRequestException.
     */
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlePreconditionFailedHttpClientException(BadRequestException ex) {
        return ApiError.builder()
                .title("Bad Request")
                .status(ex.getErrorCode())
                .message(ex.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
    }

    /**
     * Handles ConflictException.
     */
    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictHttpClientException(ConflictException ex) {
        return ApiError.builder()
                .title("Conflict Exception")
                .status(ex.getErrorCode())
                .message(ex.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
    }

    /**
     * Handles AccessDeniedException.
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleForbiddenResourceHttpClientException(ConflictException ex) {
        return ApiError.builder()
                .title("Access denied")
                .status(ex.getErrorCode())
                .message(ex.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
    }

    /**
     * Handles InvalidTokenException.
     */
    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleInvalidTokenHttpClientException(ConflictException ex) {
        return ApiError.builder()
                .title("Invalid token")
                .status(ex.getErrorCode())
                .message(ex.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
    }

    /**
     * Handles validation exceptions.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ApiError handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ApiError.builder()
                .title("Precondition Failed")
                .status(ex.getStatusCode().value())
                .message(ex.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
    }

    /**
     * Handles database constraint violations.
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        return ApiError.builder()
                .title("Database Integrity Violation")
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
    }

    /**
     * Handles SystemErrorException.
     */
    @ExceptionHandler(SystemErrorException.class)
    public ResponseEntity<ApiError> handleSystemErrorException(SystemErrorException ex) {
        ApiError apiError = ApiError.builder()
                .title(HttpStatus.valueOf(ex.getErrorCode()).getReasonPhrase())
                .status(ex.getErrorCode())
                .message(ex.getMessage())
                .dateTime(LocalDateTime.now())
                .build();

        return ResponseEntity.status(ex.getErrorCode()).body(apiError);
    }

    /**
     * Handles WebClientResponseException and attempts to deserialize body.
     */
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ApiError> handleWebClientResponseException(WebClientResponseException ex) {
        ApiError apiError;
        try {
            apiError = objectMapper.readValue(ex.getResponseBodyAsString(), ApiError.class);
        } catch (Exception e) {
            apiError = ApiError.builder()
                    .title(HttpStatus.valueOf(ex.getStatusCode().value()).getReasonPhrase())
                    .status(ex.getStatusCode().value())
                    .message(ex.getResponseBodyAsString())
                    .dateTime(LocalDateTime.now())
                    .build();
        }
        return ResponseEntity.status(ex.getStatusCode()).body(apiError);
    }
}