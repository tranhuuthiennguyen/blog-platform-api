package com.thiennth.blogplatformapi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.thiennth.blogplatformapi.dto.ApiResponse;
import com.thiennth.blogplatformapi.dto.request.LoginUserRequest;
import com.thiennth.blogplatformapi.dto.request.RefreshTokenRequest;
import com.thiennth.blogplatformapi.dto.request.RegisterUserRequest;
import com.thiennth.blogplatformapi.dto.response.AuthResponse;
import com.thiennth.blogplatformapi.dto.response.UserResponse;
import com.thiennth.blogplatformapi.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterUserRequest request) {
        UserResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.created("New account registered successfully", response));
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginUserRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.ok("User login successfully", response));
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        AuthResponse response = authService.refresh(request);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.ok("Success", response));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logout(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody RefreshTokenRequest request) {

        String accessToken = authHeader.substring(7);
        authService.logout(accessToken, request);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.ok("User logout successfully", null));
    }
    
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> me() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.ok("Success", authService.me()));
    }
    
}
