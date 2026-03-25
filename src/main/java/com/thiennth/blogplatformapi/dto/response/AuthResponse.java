package com.thiennth.blogplatformapi.dto.response;

public record AuthResponse(
    String accessToken,
    String refreshToken
) {
    public static AuthResponse from(String atk, String rtk) {
        return new AuthResponse(atk, rtk);
    }
}
