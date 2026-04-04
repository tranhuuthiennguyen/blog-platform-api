package com.thiennth.blogplatformapi.dto.request;

import com.thiennth.blogplatformapi.validation.Password;

public record ChangePasswordRequest(
    @Password
    String oldPassword,
    @Password
    String newPassword
) {}
