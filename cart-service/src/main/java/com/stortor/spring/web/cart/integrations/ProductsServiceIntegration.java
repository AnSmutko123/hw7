package com.stortor.spring.web.cart.integrations;

import com.stortor.spring.web.api.carts.CartDto;
import com.stortor.spring.web.api.core.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductsServiceIntegration {
    private final WebClient productsServiceWebClient;

    @Value("${integrations.core-service.url}")
    private String productServiceUrl;

    public Optional<ProductDto> findById(Long id) {
        ProductDto productDto = productsServiceWebClient.get()
                .uri("/api/v1/products/" + id)
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block();
        return Optional.ofNullable(productDto);
    }


}







