package com.thiennth.blogplatformapi.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.thiennth.blogplatformapi.config.JwtProperties;
import com.thiennth.blogplatformapi.model.User;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtils {
    
    private final JwtProperties jwtProperties;

    public String generateAccessToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();

        return Jwts.builder()
            .subject(userPrincipal.getEmail())
            .issuedAt(new Date())
            .expiration(new Date((new Date()).getTime() + jwtProperties.access().expiration()))
            .signWith(key(jwtProperties.access().secret()))
            .compact();
    }

    public String generateRefreshToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();

        return Jwts.builder()
            .subject(userPrincipal.getEmail())
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
