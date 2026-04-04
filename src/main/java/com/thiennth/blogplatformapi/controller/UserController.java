package com.thiennth.blogplatformapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.thiennth.blogplatformapi.dto.ApiResponse;
import com.thiennth.blogplatformapi.dto.PaginatedResponse;
import com.thiennth.blogplatformapi.dto.request.ChangePasswordRequest;
import com.thiennth.blogplatformapi.dto.request.UpdateUserProfileRequest;
import com.thiennth.blogplatformapi.dto.request.UserFilterRequest;
import com.thiennth.blogplatformapi.dto.response.PublishedPostsListByAuthorResponse;
import com.thiennth.blogplatformapi.dto.response.UserResponse;
import com.thiennth.blogplatformapi.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    @GetMapping("/api/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> get(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.ok("Retrieved user profile successfully", userService.get(id)));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/api/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(@PathVariable Long id, @Valid @RequestBody UpdateUserProfileRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.ok("Update user profile successfully", userService.updateProfile(id, request)));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/api/users/{id}/password")
    public ResponseEntity<ApiResponse<String>> changePassword(@PathVariable Long id, @Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(id, request);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.ok("Password change successfully", null));
    }

    @GetMapping("/api/users/{id}/posts")
    public ResponseEntity<ApiResponse<PublishedPostsListByAuthorResponse>> getPublishedPosts(@PathVariable Long id) {
        PublishedPostsListByAuthorResponse response = userService.getListPublishedPost(id);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.ok("Success", response));
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<ApiResponse<Void>> deactivate(@PathVariable Long id) {
        userService.deactivate(id);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.ok("User has been deactivated", null));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/admin/users")
    public ResponseEntity<PaginatedResponse<UserResponse>> geAllUsers(@ModelAttribute @Valid UserFilterRequest filter) {
        PaginatedResponse<UserResponse> response = userService.getAll(filter);
        return ResponseEntity.status(HttpStatus.OK)
            .body(response);
    }
}
