package com.stortor.spring.web.core.integrations;

import com.stortor.spring.web.api.analytics.ProductAnalyticsDto;
import com.stortor.spring.web.api.core.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class AnalyticsProductsIntegration {
    private final WebClient analyticsProductsServiceWebClient;

    @Value("${integrations.analytics-service.url}")
    private String analyticsProductsServiceUrl;

    public void sendToAnalytics(ProductDto productDto) {
        ProductAnalyticsDto productAnalyticsDto = new ProductAnalyticsDto(productDto.getId(), productDto.getTitle(), new Date(), null);
        analyticsProductsServiceWebClient.post()
                .uri(analyticsProductsServiceUrl + "/api/v1/products_analytics/bought")
                .syncBody(productAnalyticsDto)
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block();
    }

}







