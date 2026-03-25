package com.thiennth.blogplatformapi.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.thiennth.blogplatformapi.config.JwtProperties;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtils {
    
    private final JwtProperties jwtProperties;

    public String generateAccessToken(String email) {
        return Jwts.builder()
            .subject(email)
            .issuedAt(new Date())
            .expiration(new Date((new Date()).getTime() + jwtProperties.access().expiration()))
            .signWith(key(jwtProperties.access().secret()))
            .compact();
    }

    public String generateRefreshToken(String email) {
        return Jwts.builder()
            .subject(email)
            .issuedAt(new Date())
            .expiration(new Date((new Date()).getTime() + jwtProperties.refresh().expiration()))
            .signWith(key(jwtProperties.refresh().secret()))
            .compact();
    }

    private Key key(String secretKey) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String extractEmailFromAccessToken(String token) {
        return Jwts.parser().verifyWith((SecretKey) key(jwtProperties.access().secret())).build()
            .parseSignedClaims(token).getPayload().getSubject();
    }

    public long extractExpirationFromAccessToken(String token) {
        return Jwts.parser().verifyWith((SecretKey) key(jwtProperties.access().secret())).build()
            .parseSignedClaims(token).getPayload().getExpiration().getTime();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) key(jwtProperties.access().secret())).build().parse(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
