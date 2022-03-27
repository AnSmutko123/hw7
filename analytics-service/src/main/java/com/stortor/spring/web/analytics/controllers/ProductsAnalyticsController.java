package com.stortor.spring.web.analytics.controllers;

import com.stortor.spring.web.analytics.entities.ProductsAnalytics;
import com.stortor.spring.web.analytics.integrations.ProductsServiceIntegration;
import com.stortor.spring.web.analytics.services.ProductsAnalyticsService;
import com.stortor.spring.web.api.analytics.ProductAnalyticsDto;
import com.stortor.spring.web.api.core.ProductDto;
import com.stortor.spring.web.api.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/products_analytics")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductsAnalyticsController {

    private final ProductsAnalyticsService productsAnalyticsService;
    private final ProductsServiceIntegration productsServiceIntegration;

    @GetMapping("/bought")
    public List<ProductDto> getTheMostBoughtProductsPerMonth() {
        List<Long> productsIdList = productsAnalyticsService.getTheMostBoughtProductsPerMonth();
        List<ProductDto> productDtos = productsIdList.stream().map(i -> productsServiceIntegration.findById(i).orElseThrow(() -> new ResourceNotFoundException("Product not found, id = " + i))).collect(Collectors.toList());;
        return productDtos;
    }

    @GetMapping("/added")
    public List<ProductDto> getTheMostPutToCartProductsPerDay() {
        log.info("Добавили в корзину");
        List<Long> productsIdList = productsAnalyticsService.getTheMostPutToCartProductsPerDay();
        List<ProductDto> productDtos = productsIdList.stream().map(i -> productsServiceIntegration.findById(i).orElseThrow(() -> new ResourceNotFoundException("Product not found, id = " + i))).collect(Collectors.toList());;
        return productDtos;
    }

    @PostMapping()
    public void addProductAnalytics(@RequestBody List<ProductAnalyticsDto> analyticsProductDto) {
        productsAnalyticsService.addToCartProducts(analyticsProductDto);
    }

}
