package com.thiennth.blogplatformapi.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.thiennth.blogplatformapi.exception.UnauthorizedException;
import com.thiennth.blogplatformapi.security.JwtUtils;
import com.thiennth.blogplatformapi.service.RefreshTokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {
    
    private final JwtUtils jwtUtils;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String TOKEN_KEY_PREFIX = "refresh:token:";
    private static final String USER_KEY_PREFIX = "refresh:user:";

    public String store(String email) {
        String rawToken = jwtUtils.generateRefreshToken(email);
        String tokenHash = hash(rawToken);
        long ttlSeconds = jwtUtils.getJwtProperties().refresh().expiration();

        redisTemplate.opsForValue().set(
            TOKEN_KEY_PREFIX + tokenHash, 
            email,
            ttlSeconds,
            TimeUnit.MILLISECONDS
        );

        redisTemplate.opsForSet().add(USER_KEY_PREFIX + email, tokenHash);
        redisTemplate.expire(USER_KEY_PREFIX + email, ttlSeconds, TimeUnit.MILLISECONDS);

        return rawToken;
    }

    public String validate(String rawToken) {
        String value = redisTemplate.opsForValue().get(TOKEN_KEY_PREFIX + hash(rawToken));
        if (value == null) {
            throw new UnauthorizedException("Invalid or expired refresh token");
        }
        return value;
    }

    public void revoke(String rawToken) {
        String tokenHash = hash(rawToken);
        String email = redisTemplate.opsForValue().get(TOKEN_KEY_PREFIX + tokenHash);

        redisTemplate.delete(TOKEN_KEY_PREFIX + tokenHash);
        log.info("Refresh token [{}] has been revoked", rawToken);

        if (email != null) {
            redisTemplate.opsForSet().remove(USER_KEY_PREFIX + email, tokenHash);
        }
    }

    public void revokeAll(String email) {
        Set<String> tokenHashes = redisTemplate.opsForSet().members(USER_KEY_PREFIX + email);
        if (tokenHashes != null) {
            tokenHashes.forEach(hash ->
                redisTemplate.delete(TOKEN_KEY_PREFIX + hash)
            );
        }
        redisTemplate.delete(USER_KEY_PREFIX + email);
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
