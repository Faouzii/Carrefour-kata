package com.carrefour.kata.discountmvp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Discount MVP API")
                        .description("A Spring Boot application implementing a discount system with support for both in-memory and JPA database implementations.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Carrefour Kata Team")
                                .email("team@carrefour.com")
                                .url("https://github.com/carrefour/discount-mvp"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Development Server"),
                        new Server()
                                .url("https://api.discount-mvp.com")
                                .description("Production Server")
                ));
    }
} 