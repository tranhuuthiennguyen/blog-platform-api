package com.thiennth.blogplatformapi.service;

import com.thiennth.blogplatformapi.dto.request.LoginUserRequest;
import com.thiennth.blogplatformapi.dto.request.RefreshTokenRequest;
import com.thiennth.blogplatformapi.dto.request.RegisterUserRequest;
import com.thiennth.blogplatformapi.dto.response.AuthResponse;
import com.thiennth.blogplatformapi.dto.response.UserResponse;

public interface AuthService {
    UserResponse register(RegisterUserRequest request);
    AuthResponse login(LoginUserRequest request);
    AuthResponse refresh(RefreshTokenRequest request);
    void logout(String accessToken, RefreshTokenRequest request);
    UserResponse me();
}
