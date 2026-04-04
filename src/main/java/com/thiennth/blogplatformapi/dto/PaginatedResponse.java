package com.thiennth.blogplatformapi.dto;

import lombok.Getter;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.function.Function;

@Getter
public class PaginatedResponse<T> extends ApiResponse<List<T>> {

    private final int     page;
    private final int     limit;
    private final long    total;
    private final int     totalPages;
    private final boolean hasNext;
    private final boolean hasPrevious;

    private PaginatedResponse(String message, List<T> data,
                               int page, int limit, long total) {
        super(true, HttpStatus.OK, message, data);
        this.page        = page;
        this.limit       = limit;
        this.total       = total;
        this.totalPages  = limit > 0 ? (int) Math.ceil((double) total / limit) : 0;
        this.hasNext     = page < totalPages;
        this.hasPrevious = page > 1;
    }

    public static <S, T> PaginatedResponse<T> of(String message, Page<S> page, Function<S, T> mapper) {
        List<T> content = page.getContent().stream().map(mapper).toList();
        return new PaginatedResponse<>(message, content, page.getNumber(), page.getSize(), page.getTotalElements());
    }

    public static <T> PaginatedResponse<T> of(List<T> data,
                                               int page, int limit, long total) {
        return new PaginatedResponse<>("Success", data, page, limit, total);
    }

    public static <T> PaginatedResponse<T> of(String message, List<T> data,
                                               int page, int limit, long total) {
        return new PaginatedResponse<>(message, data, page, limit, total);
    }
}