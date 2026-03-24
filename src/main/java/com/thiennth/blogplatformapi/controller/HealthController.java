package com.thiennth.blogplatformapi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.thiennth.blogplatformapi.dto.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HealthController {
    
    @GetMapping("/health-check")
    public ResponseEntity<ApiResponse<String>> getServerHealthStatus() {
        return ResponseEntity.ok(ApiResponse.ok("Server is alive", null));
    }
    
}
