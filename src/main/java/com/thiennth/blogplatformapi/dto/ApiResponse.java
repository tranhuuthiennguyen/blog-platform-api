package com.thiennth.blogplatformapi.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<D> extends HttpResponse {

    private final D data;

    protected ApiResponse(boolean success, HttpStatus status, String message, D data) {
        super(success, status.value(), message);
        this.data = data;
    }

    public static <D> ApiResponse<D> of(HttpStatus status, String message, D data) {
        return new ApiResponse<>(true, status, message, data);
    }

    public static <D> ApiResponse<D> ok(String message, D data) {
        return of(HttpStatus.OK, message, data);
    }

    public static <D> ApiResponse<D> created(String message, D data) {
        return of(HttpStatus.CREATED, message, data);
    }
}