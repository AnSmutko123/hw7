package com.stortor.spring.web.cart.integrations;

import com.stortor.spring.web.api.carts.CartDto;
import com.stortor.spring.web.api.core.ProductDto;
import com.stortor.spring.web.api.exceptions.ResourceNotFoundException;
import com.stortor.spring.web.api.exceptions.ServerNotWorkingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductsServiceIntegration {
    private final WebClient coreServiceWebClient;

    public Optional<ProductDto> findById(Long id) {
        ProductDto productDto = coreServiceWebClient.get()
                .uri("/api/v1/products/" + id)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new ServerNotWorkingException("Product-MS not working")))
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new ResourceNotFoundException("Product not found, id = " + id)))
                .bodyToMono(ProductDto.class)
                .block();
        return Optional.ofNullable(productDto);
    }

}







