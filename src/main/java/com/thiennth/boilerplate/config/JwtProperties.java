package com.thiennth.boilerplate.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
    Access access,
    Refresh refres
) {
    public record Access(String secret, long expirationMs) {}
    public record Refresh(String secret, long expirationMs) {}
}
