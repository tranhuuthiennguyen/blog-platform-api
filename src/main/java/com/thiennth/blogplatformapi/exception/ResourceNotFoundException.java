package com.thiennth.blogplatformapi.exception;

public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String resource, Object identifier) {
        super(resource + " not found with: " + identifier);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
