package com.thiennth.blogplatformapi.dto.request;

import com.thiennth.blogplatformapi.validation.Email;
import com.thiennth.blogplatformapi.validation.Password;

public record RegisterUserRequest(
    
    @Email
    String email,

    @Password
    String password
) {}
