package com.stortor.spring.web.analytics.services;

import com.stortor.spring.web.analytics.converters.ProductAnalyticsConverter;
import com.stortor.spring.web.analytics.entities.ProductsAnalytics;
import com.stortor.spring.web.analytics.repositories.ProductsAnalyticsRepository;
import com.stortor.spring.web.api.analytics.ProductAnalyticsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductsAnalyticsService {

    private final ProductsAnalyticsRepository productsAnalyticsRepository;
    private final ProductAnalyticsConverter converter;

    public void addToCartProducts(List<ProductAnalyticsDto> analyticsProductDto) {
        List<ProductsAnalytics> productsAnalytics = analyticsProductDto.stream().map(p -> converter.dtoToEntity(p)).collect(Collectors.toList());
        addAnalyticsProductsToBd(productsAnalytics);
    }

    @Transactional
    public void addAnalyticsProductsToBd(List<ProductsAnalytics> productsAnalytics) {
        productsAnalytics.stream().forEach(p -> productsAnalyticsRepository.save(p));
    }

    public List<Long> getTheMostPutToCartProductsPerDay() {
        return productsAnalyticsRepository.getTheMostPutToCartProductsPerDay();
    }

    public List<Long> getTheMostBoughtProductsPerMonth() {
        return productsAnalyticsRepository.getTheMostBoughtProductsPerMonth();
    }

}
