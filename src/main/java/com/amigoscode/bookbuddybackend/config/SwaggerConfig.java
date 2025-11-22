package com.amigoscode.bookbuddybackend.config;  // Note: This package differs from your project's 'com.bookbuddy'—update if needed

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Book Buddy API")
                        .version("1.0")
                        .description("API for Book Buddy application"));
    }
}