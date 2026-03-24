package com.thiennth.blogplatformapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.thiennth.blogplatformapi.config.JwtProperties;

@EnableConfigurationProperties(JwtProperties.class)
@SpringBootApplication
public class BlogPlatformApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogPlatformApiApplication.class, args);
    }
}
