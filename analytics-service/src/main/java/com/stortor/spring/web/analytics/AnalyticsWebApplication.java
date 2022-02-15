package com.stortor.spring.web.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class AnalyticsWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalyticsWebApplication.class, args);
	}

}
