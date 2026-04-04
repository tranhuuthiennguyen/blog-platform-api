package com.thiennth.blogplatformapi.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.thiennth.blogplatformapi.security.JwtUtils;
import com.thiennth.blogplatformapi.service.TokenBlacklistService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenBlacklistService {

    private final JwtUtils jwtUtils;
    private final RedisTemplate<String, String> redisTemplate;
    
    private static final String BLACKLIST_KEY_PREFIX = "blacklist:";

    public void blacklist(String rawToken) {
        String tokenHash = hash(rawToken);

        long remainingTtl = jwtUtils.extractExpirationFromAccessToken(rawToken) - System.currentTimeMillis();

        log.info(String.valueOf(remainingTtl));

        if (remainingTtl > 0) {
            redisTemplate.opsForValue().set(
                BLACKLIST_KEY_PREFIX + tokenHash, 
                "revoked",
                remainingTtl,
                TimeUnit.MILLISECONDS
            );
        }
    }

    public boolean isBlacklisted(String rawToken) {
        return Boolean.TRUE.equals(
            redisTemplate.hasKey(BLACKLIST_KEY_PREFIX + hash(rawToken))
        );
    }

    private String hash(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }
}
