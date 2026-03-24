package com.thiennth.blogplatformapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
    Access access,
    Refresh refresh
) {
    public record Access(String secret, Long expiration) {}
    public record Refresh(String secret, Long expiration) {}
}
