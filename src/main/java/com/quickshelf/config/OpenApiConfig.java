package com.quickshelf.config;

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
    public OpenAPI quickShelfOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("QuickShelf API")
                        .description("RESTful API for managing product inventory")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("QuickShelf Team")
                                .email("support@quickshelf.com")
                                .url("https://quickshelf.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080/api")
                                .description("Local Development Server")
                ));
    }
}