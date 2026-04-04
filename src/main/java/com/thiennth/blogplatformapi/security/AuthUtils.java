package com.thiennth.blogplatformapi.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.thiennth.blogplatformapi.model.User;

@Component
public class AuthUtils {
    
    public User currentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Long currentUserId() {
        return currentUser().getId();
    }
}
