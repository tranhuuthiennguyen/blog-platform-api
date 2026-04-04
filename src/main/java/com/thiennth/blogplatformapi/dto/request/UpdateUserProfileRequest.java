package com.thiennth.blogplatformapi.dto.request;

import jakarta.validation.constraints.Size;

public record UpdateUserProfileRequest(
    @Size(max = 100)
    String firstName,
    @Size(max = 100)
    String lastName,
    String bio,
    @Size(max = 500)
    String avatarUrl
) {}
