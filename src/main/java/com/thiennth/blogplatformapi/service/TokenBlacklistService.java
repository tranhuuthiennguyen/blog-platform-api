package com.thiennth.blogplatformapi.service;

public interface TokenBlacklistService {
    void blacklist(String rawAccessToken);
    boolean isBlacklisted(String rawAccessToken);
}
