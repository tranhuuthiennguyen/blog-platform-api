package com.thiennth.blogplatformapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.thiennth.blogplatformapi.config.JwtProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
@EnableJpaRepositories(basePackages = "com.thiennth.blogplatformapi.repository")
public class BlogPlatformApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogPlatformApiApplication.class, args);
    }
}
