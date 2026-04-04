package com.thiennth.blogplatformapi.dto.response;

import java.time.Instant;

import com.thiennth.blogplatformapi.model.User;

public record UserResponse(
    Long id,
    String email,
    String firstName,
    String lastName,
    String bio,
    String avatarUrl,
    Boolean isActive,
    Instant createdAt,
    Instant updatedAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(), 
            user.getEmail(), 
            user.getFirstName(), 
            user.getLastName(), 
            user.getBio(), 
            user.getAvatarUrl(), 
            user.getIsActive(), 
            user.getCreatedAt(), 
            user.getUpdatedAt());
    }
}