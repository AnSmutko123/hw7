package com.stortor.spring.web.cart.integrations;

import com.stortor.spring.web.api.analytics.ProductAnalyticsDto;
import com.stortor.spring.web.api.core.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import redis.clients.jedis.Response;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AnalyticsProductsIntegration {
    private final WebClient analyticsProductsServiceWebClient;

    @Value("${integrations.analytics-service.url}")
    private String analyticsProductsServiceUrl;

    public void sendToAnalytics(ProductDto productDto) {
        ProductAnalyticsDto productAnalyticsDto = new ProductAnalyticsDto(productDto.getId(), productDto.getTitle(), null, new Date());
        analyticsProductsServiceWebClient.post()
                .uri(analyticsProductsServiceUrl + "/api/v1/products_analytics/add")
                .syncBody(productAnalyticsDto)
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block();
    }

}







