package com.thiennth.blogplatformapi.dto;

import java.util.function.Function;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import com.thiennth.blogplatformapi.filter.CorrelationIdFilter;

import lombok.Getter;

@Getter
public abstract class HttpResponse {
    
    protected       Boolean success;
    protected final int statusCode;
    protected final String message;
    protected final String correlationId;

    public HttpResponse(Builder<?> builder) {
        this.success = builder.success;
        this.statusCode = builder.statusCode;
        this.message = builder.message;
        this.correlationId = MDC.get(CorrelationIdFilter.CORRELATION_ID_MDC_KEY);
    }

    public static class Builder<T extends HttpResponse> {

        private Boolean success;
        private int statusCode;
        private String message;
        private Function<Builder<T>, T> constructor;

        public Builder<T> constructor(Function<Builder<T>, T> constructor) {
            this.constructor = constructor;
            return this;
        }

        public Builder<T> success(Boolean success) {
            this.success = success;
            return this;
        }

        public Builder<T> statusCode(HttpStatus status) {
            this.statusCode = status.value();
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public T build() {
            return constructor.apply(this);
        }
    }

}
