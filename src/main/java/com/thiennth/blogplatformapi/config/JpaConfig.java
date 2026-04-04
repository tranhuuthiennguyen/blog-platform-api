package com.thiennth.blogplatformapi.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.thiennth.blogplatformapi.model.User;

/**
 * Central JPA configuration.
 *
 * <ul>
 *   <li>{@code @EnableJpaAuditing} activates {@code @CreatedDate / @LastModifiedDate} etc.</li>
 *   <li>{@code AuditorAware} resolves the current principal for {@code @CreatedBy / @LastModifiedBy}.</li>
 *   <li>{@code @EnableTransactionManagement} is included here for a single-config-class setup.</li>
 * </ul>
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableTransactionManagement
public class JpaConfig {
    
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal)
            .filter(User.class::isInstance)
            .map(User.class::cast)
            .map(User::getEmail)
            .or(() -> Optional.of("system"));
    }
}
