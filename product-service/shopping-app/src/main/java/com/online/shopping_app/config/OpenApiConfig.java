package com.online.shopping_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI shoppingAppOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Shopping App API - Demo Project")
                .description("REST API for managing products")
                .version("1.0"));
    }
}