package com.thiennth.blogplatformapi.dto;

import org.slf4j.MDC;

import com.thiennth.blogplatformapi.filter.CorrelationIdFilter;
import lombok.Getter;

@Getter
public abstract class HttpResponse {

    protected final boolean success;
    protected final int     statusCode;
    protected final String  message;
    protected final String  correlationId;

    protected HttpResponse(boolean success, int statusCode, String message) {
        this.success       = success;
        this.statusCode    = statusCode;
        this.message       = message;
        this.correlationId = MDC.get(CorrelationIdFilter.CORRELATION_ID_MDC_KEY);
    }
}