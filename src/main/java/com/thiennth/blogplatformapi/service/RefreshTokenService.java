package com.thiennth.blogplatformapi.service;

public interface RefreshTokenService {
    String store(String email);
    String validate(String rawToken);
    void revoke(String rawToken);
    void revokeAll(String email);
}
