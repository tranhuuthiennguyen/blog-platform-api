package com.thiennth.blogplatformapi.exception;

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(String email) {
        super("User", "email = " + email);
    }

    public UserNotFoundException(Long id) {
        super("User", "id = " + id);
    }
    
}
