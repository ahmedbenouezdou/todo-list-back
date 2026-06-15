package com.kata.todo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("To-Do List API")
                        .version("1.0")
                        .description("API REST pour la gestion des tâches de la To-Do List")
                        .contact(new Contact()
                                .name("Kata Dev")
                                .email("contact@kata.com")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Serveur local")));
    }
}
