package com.stortor.spring.web.core.integrations;

import com.stortor.spring.web.api.analytics.ProductAnalyticsDto;
import com.stortor.spring.web.api.core.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AnalyticsProductsIntegration {
    private final WebClient analyticsProductsServiceWebClient;

    @Value("${integrations.analytics-service.url}")
    private String analyticsProductsServiceUrl;

    public void sendToAnalytics(List<ProductDto> productDtoList) {
        List<ProductAnalyticsDto> productAnalyticsDto = productDtoList.stream()
                .map(p -> new ProductAnalyticsDto(p.getId(), p.getTitle(), new Date(), null)).collect(Collectors.toList());
        analyticsProductsServiceWebClient.post()
                .uri(analyticsProductsServiceUrl + "/api/v1/products_analytics")
                .syncBody(productAnalyticsDto)
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }

}







