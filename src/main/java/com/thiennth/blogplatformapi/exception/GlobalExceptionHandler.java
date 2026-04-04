package com.thiennth.blogplatformapi.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.thiennth.blogplatformapi.dto.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ForBiddenActionException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenAction(ForBiddenActionException ex) {
        return respond(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler({
        BadRequestException.class,
        AuthorizationDeniedException.class,
        UnauthorizedException.class,
        BadCredentialsException.class,
        UsernameNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleUnauthorized(RuntimeException ex) {
        log.warn(ex.getMessage());
        return respond(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        log.warn(ex.getMessage());
        return respond(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex) {
        log.warn(ex.getMessage());
        return respond(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        log.warn(ex.getMessage());
        List<String> subErrors = ex.getBindingResult().getFieldErrors().stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .toList();
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.badRequest("Validation errors", HttpStatus.BAD_REQUEST.getReasonPhrase(), subErrors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.warn(ex.getMessage());
        String message = "Invalid request body";
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException ife) {
            String field = ife.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
            message = "Invalid value '%s' for field '%s', expected type: '%s'"
                .formatted(String.valueOf(ife.getValue()), field, ife.getTargetType().getSimpleName());
        }
        return respond(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex) {
        log.error("Data integrity violation — likely a missing DTO validation: ", ex);
        return respond(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccess(DataAccessException ex) {
        log.error("Database error: ", ex);
        return respond(HttpStatus.INTERNAL_SERVER_ERROR, "A database error occurred");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnknown(Exception ex) {
        log.error("Unknown error: ", ex);
        return respond(HttpStatus.INTERNAL_SERVER_ERROR, "An unknown error occurred");
    }

    // ── private helpers ───────────────────────────────────────────────────────

    private ResponseEntity<ErrorResponse> respond(HttpStatus status, String message) {
        return ResponseEntity
            .status(status)
            .body(ErrorResponse.of(status, message, status.getReasonPhrase()));
    }
}