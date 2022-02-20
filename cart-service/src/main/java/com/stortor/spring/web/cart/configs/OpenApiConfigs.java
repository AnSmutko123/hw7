package com.stortor.spring.web.cart.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfigs {
    @Bean
    public OpenAPI api() {
        return new OpenAPI().info(new Info().title("Spring-Web - Сервис корзин").version("1"));
    }
}
