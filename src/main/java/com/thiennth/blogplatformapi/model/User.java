package com.thiennth.blogplatformapi.model;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.ParamDef;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;

@Entity
@Table(
    name = "users",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_users_email",
        columnNames = {"email"}
    )
)
@Getter
@FilterDef(
    name = "deactivateFilter",
    parameters = @ParamDef(name = "isActive", type = Boolean.class)
)
@Filter(name = "deactivateFilter", condition = "is_active = :isActive")
public class User extends BaseEntity implements UserDetails {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "role")
    @JdbcType(value = PostgreSQLEnumJdbcType.class)
    private Role role;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Column(name = "is_active")
    private Boolean isActive = true;

    // -------------------------------------------------------------------------
    // JPA no-arg constructor (package-private; not for application use)
    // -------------------------------------------------------------------------
    protected User() {}

    // -------------------------------------------------------------------------
    // Static factory — the ONLY way to create an instance
    // -------------------------------------------------------------------------
    public static User of(
        String email,
        String password,
        String firstName,
        String lastName,
        Role role,
        String bio,
        String avatarUrl,
        Boolean isActive
    ) {
        Objects.requireNonNull(email, "email must not be null");
        Objects.requireNonNull(password, "password must not be null");
        Objects.requireNonNull(role, "role must not be null");
        Objects.requireNonNull(isActive, "isActive must not be null");

        var entry = new User();
        entry.email = email;
        entry.password = password;
        entry.firstName = firstName;
        entry.lastName = lastName;
        entry.role = role;
        entry.bio = bio;
        entry.avatarUrl = avatarUrl;
        entry.isActive = isActive;
        return entry;
    }

    // -------------------------------------------------------------------------
    // Domain methods
    // -------------------------------------------------------------------------
    public void updateProfile(
        String firstName,
        String lastName,
        String bio,
        String avatarUrl
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.avatarUrl = avatarUrl;
    }

    public void changePassword(String password) {
        Objects.requireNonNull(password, "password must not ben null");
        this.password = password;
    }

    public void deactivate() {
        if (!isEnabled()) {
            throw new IllegalStateException("Entity already deleted: " + this);
        }
        this.isActive = false;
    }

    public void changeRole(Role role) {
        Objects.requireNonNull(role, "role must not be null");
        if (this.role.equals(role)) {
            throw new IllegalStateException("role already been set");
        }
        this.role = role;
    }

    // -------------------------------------------------------------------------
    // Implementation of UserDetails
    // -------------------------------------------------------------------------

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(isActive);
    }

    // -------------------------------------------------------------------------
    // Business key
    // -------------------------------------------------------------------------

    @Override
    protected Object domainKey() {
        return email;
    }

    public enum Role {
        USER,
        ADMIN;

        public static Role fromValue(String value) {
            for (Role role : values()) {
                if (role.name().equalsIgnoreCase(value)) {
                    return role;
                }
            }
            throw new IllegalArgumentException("Unknown role: " + value);
        }
    }

}
