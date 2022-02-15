package com.stortor.spring.web.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CartWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartWebApplication.class, args);
	}

}
