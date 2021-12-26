package com.stortor.hw7.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 1. Добавить регистрацию новых пользователей
 */

@Configuration
@PropertySource("secrets.properties")
public class AppConfig {
}
