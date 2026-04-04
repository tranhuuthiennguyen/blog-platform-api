package com.thiennth.blogplatformapi.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ErrorResponse extends HttpResponse {

    private final String       error;
    private final List<String> subErrors;

    private ErrorResponse(HttpStatus status, String message,
                          String error, List<String> subErrors) {
        super(false, status.value(), message);
        this.error     = error;
        this.subErrors = subErrors;
    }

    public static ErrorResponse of(HttpStatus status, String message, String error) {
        return new ErrorResponse(status, message, error, List.of());
    }

    public static ErrorResponse of(HttpStatus status, String message,
                                   String error, List<String> subErrors) {
        return new ErrorResponse(status, message, error, subErrors);
    }

    public static ErrorResponse badRequest(String message, String error) {
        return of(HttpStatus.BAD_REQUEST, message, error);
    }

    public static ErrorResponse badRequest(String message, String error,
                                           List<String> subErrors) {
        return of(HttpStatus.BAD_REQUEST, message, error, subErrors);
    }

    public static ErrorResponse notFound(String message, String error) {
        return of(HttpStatus.NOT_FOUND, message, error);
    }

    public static ErrorResponse internalError(String message, String error) {
        return of(HttpStatus.INTERNAL_SERVER_ERROR, message, error);
    }
}