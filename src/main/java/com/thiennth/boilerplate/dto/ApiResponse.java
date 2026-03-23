package com.thiennth.boilerplate.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApiResponse<T> extends HttpResponse {

    private final T data;

    public ApiResponse(Builder<T> builder) {
        super(builder);
        this.data = builder.data;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> extends HttpResponse.Builder<ApiResponse<T>> {
        
        private T data;

        @Override
        public Builder<T> success(Boolean success) {
            super.success(success);
            return this;
        }

        @Override
        public Builder<T> statusCode(HttpStatus status) {
            super.statusCode(status);
            return this;
        }

        @Override
        public Builder<T> message(String message) {
            super.message(message);
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        @Override
        public ApiResponse<T> build() {
            return new ApiResponse<T>(this);
        }
    }

    public static <T> ApiResponse<T> of(HttpStatus status, String message, T data) {
        return ApiResponse.<T>builder()
            .success(true)
            .statusCode(status)
            .message(message)
            .data(data)
            .build();
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return of(HttpStatus.OK, message, data);
    }

    public static <T> ApiResponse<T> created(String message, T data) {
        return of(HttpStatus.CREATED, message, data);
    }

}
