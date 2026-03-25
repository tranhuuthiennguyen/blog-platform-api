package com.thiennth.blogplatformapi.config;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.PropertyNamingStrategies;

@Configuration
public class JacksonConfig {
    
    @Bean
    JsonMapperBuilderCustomizer customizer() {
        return builder -> builder
            .disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .disable(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES)
            .enable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES);
    }
}
