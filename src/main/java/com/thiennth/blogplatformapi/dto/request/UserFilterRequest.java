package com.thiennth.blogplatformapi.dto.request;

import java.time.LocalDate;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public record UserFilterRequest(

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate createdFrom,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate createdTo,

    @Min(0)
    Integer page,

    @Max(100)
    Integer size,

    @Pattern(regexp = "createdAt|isActive")
    String sortBy,

    @Pattern(regexp = "ASC|DESC")
    String sortDir
) {
    public UserFilterRequest {
        page = (page == null) ? 0 : page;
        size = (size == null) ? 20 : size;
        sortBy = (sortBy == null) ? "createdAt" : sortBy;
        sortDir = (sortDir == null) ? "DESC" : sortDir;
    }

    public Pageable toPageable() {
        Sort sort = Sort.by(
            "DESC".equalsIgnoreCase(sortDir)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC,
            sortBy   
        );
        return PageRequest.of(page, size, sort);
    }

    public boolean hasCreatedRange() {
        return createdFrom != null || createdTo != null;
    }
}
