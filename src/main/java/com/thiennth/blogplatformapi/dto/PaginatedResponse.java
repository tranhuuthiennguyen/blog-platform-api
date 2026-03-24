package com.thiennth.blogplatformapi.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class PaginatedResponse<T> extends ApiResponse<List<T>> {

    private final int page;
    private final int limit;
    private final long total;
    private final int totalPages;
    private final boolean hasNext;
    private final boolean hasPrevious;

    public PaginatedResponse(Builder<T> builder) {
        super(builder);
        this.page = builder.page;
        this.limit = builder.limit;
        this.total = builder.total;
        this.totalPages = builder.limit > 0 
            ? (int) Math.ceil((double) builder.total / builder.limit) : 0;
        this.hasNext = page < totalPages;
        this.hasPrevious = page > 1;
    }
    
    public static class Builder<T> extends ApiResponse.Builder<List<T>> {
        private int page;
        private int limit;
        private long total;

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

        @Override
        public Builder<T> data(List<T> data) {
            super.data(data);
            return this;
        }

        public Builder<T> page(int p) {
            this.page = p;
            return this;
        }

        public Builder<T> limit(int l) {
            this.limit = l;
            return this;
        }

        public Builder<T> total(long t) {
            this.total = t;
            return this;
        }

        @Override
        public PaginatedResponse<T> build() {
            return new PaginatedResponse<T>(this);
        }
    }

    public static <T> PaginatedResponse<T> of(List<T> data, int page, int limit, long total) {
        PaginatedResponse.Builder<T> builder = new PaginatedResponse.Builder<>();
        return builder
            .success(true)
            .statusCode(HttpStatus.OK)
            .message("Success")
            .data(data)
            .page(page)
            .limit(limit)
            .total(total)
            .build();
    }

    public static <T> PaginatedResponse<T> of(String message, List<T> data, int page, int limit, long total) {
        PaginatedResponse.Builder<T> builder = new PaginatedResponse.Builder<>();
        return builder
            .success(true)
            .statusCode(HttpStatus.OK)
            .message(message)
            .data(data)
            .page(page)
            .limit(limit)
            .total(total)
            .build();
    }
}
