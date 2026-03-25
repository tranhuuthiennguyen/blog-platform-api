package com.thiennth.blogplatformapi.service.impl;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.thiennth.blogplatformapi.dto.request.LoginUserRequest;
import com.thiennth.blogplatformapi.dto.request.RefreshTokenRequest;
import com.thiennth.blogplatformapi.dto.request.RegisterUserRequest;
import com.thiennth.blogplatformapi.dto.response.AuthResponse;
import com.thiennth.blogplatformapi.dto.response.UserResponse;
import com.thiennth.blogplatformapi.exception.ConflictException;
import com.thiennth.blogplatformapi.exception.UnauthorizedException;
import com.thiennth.blogplatformapi.model.User;
import com.thiennth.blogplatformapi.repository.UserRepository;
import com.thiennth.blogplatformapi.security.JwtUtils;
import com.thiennth.blogplatformapi.service.AuthService;
import com.thiennth.blogplatformapi.service.RefreshTokenService;
import com.thiennth.blogplatformapi.service.TokenBlacklistService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final TokenBlacklistService tokenBlacklistService;

    @Override
    @Transactional
    public UserResponse register(RegisterUserRequest request) {
        Optional<User> user = userRepository.findByEmail(request.email());
        if (user.isPresent()) {
            throw new ConflictException("Account with this email already exists");
        }
        String encryptedPassword = passwordEncoder.encode(request.password());
        User newUser = User.builder()
            .email(request.email())
            .password(encryptedPassword)
            .role(User.Role.USER)
            .build();
        return UserResponse.from(userRepository.save(newUser));
    }

    @Override
    @Transactional
    public AuthResponse login(LoginUserRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.email(), 
                request.password()
            )
        );
        User userPrincipal = (User) authentication.getPrincipal();
        refreshTokenService.revokeAll(userPrincipal.getEmail());
        return issueTokens(userPrincipal);
    }

    @Override
    public AuthResponse refresh(RefreshTokenRequest request) {
        String email = refreshTokenService.validate(request.refreshToken());
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UnauthorizedException("User no longer exists"));
        
        refreshTokenService.revoke(request.refreshToken());
        return issueTokens(user);
    }

    @Override
    public void logout(String accessToken, RefreshTokenRequest request) {
        tokenBlacklistService.blacklist(accessToken);
        refreshTokenService.revoke(request.refreshToken());
    }

    @Override
    public UserResponse me() {
        return UserResponse.from((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    private AuthResponse issueTokens(User user) {
        String accessToken = jwtUtils.generateAccessToken(user.getEmail());
        String refreshToken = refreshTokenService.store(user.getEmail());

        return AuthResponse.from(accessToken, refreshToken);
    }

}
