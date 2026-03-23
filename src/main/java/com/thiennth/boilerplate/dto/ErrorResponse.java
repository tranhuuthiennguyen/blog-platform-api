package com.thiennth.boilerplate.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ErrorResponse extends HttpResponse {

    private final String error;
    private final List<String> subErrors;

    public ErrorResponse(Builder builder) {
        super(builder);
        this.error = builder.error;
        this.subErrors = builder.subErrors;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends HttpResponse.Builder<ErrorResponse> {

        private String error;
        private List<String> subErrors;

        @Override
        public Builder success(Boolean success) {
            super.success(success);
            return this;
        }

        @Override
        public Builder statusCode(HttpStatus status) {
            super.statusCode(status);
            return this;
        }

        @Override
        public Builder message(String message) {
            super.message(message);
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder subErrors(List<String> subErrors) {
            this.subErrors = subErrors;
            return this;
        }
        
        @Override
        public ErrorResponse build() {
            return new ErrorResponse(this);
        }
    }
}
