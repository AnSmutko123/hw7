package com.stortor.spring.web.core.integrations;

import com.stortor.spring.web.core.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

public class DbIntegration {

    @Autowired
    private WebClient webClient;

    public Product postProduct(Product product, Integer port) {
        return webClient.post()
                .uri("http://localhost:" + port +"/cache")
                .bodyValue(product)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }

}
