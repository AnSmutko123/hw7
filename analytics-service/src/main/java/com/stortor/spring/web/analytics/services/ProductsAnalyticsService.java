package com.stortor.spring.web.analytics.services;

import com.stortor.spring.web.analytics.converters.ProductAnalyticsConverter;
import com.stortor.spring.web.analytics.entities.ProductsAnalytics;
import com.stortor.spring.web.analytics.repositories.ProductsAnalyticsRepository;
import com.stortor.spring.web.api.analytics.ProductAnalyticsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductsAnalyticsService {

    private final ProductsAnalyticsRepository productsAnalyticsRepository;
    private final ProductAnalyticsConverter converter;
    private final List<ProductsAnalytics> analyticsList = new ArrayList<>();

    public void addToCartProducts(ProductAnalyticsDto analyticsProductDto) {
        ProductsAnalytics productsAnalytics = converter.dtoToEntity(analyticsProductDto);
        System.out.println(productsAnalytics);
        analyticsList.add(productsAnalytics);
        if (analyticsList.size() > 10) {
            addAnalyticsProductsToBd(analyticsList);
            analyticsList.clear();
        }
    }

    @Transactional
    public void boughtProducts(List<ProductAnalyticsDto> analyticsProductDto) {
        analyticsProductDto.stream().map(i -> converter.dtoToEntity(i)).forEach(i -> productsAnalyticsRepository.save(i));
    }

    // для того чтобы не дергать бд по каждому запросу на добавление продукта
    // кешируем по 10 обьектов
    @Transactional
    public void addAnalyticsProductsToBd(List<ProductsAnalytics> analyticsList) {
        analyticsList.stream().forEach(i -> productsAnalyticsRepository.save(i));
    }

    public List<Long> getTheMostPutToCartProductsPerDay() {
        return productsAnalyticsRepository.getTheMostPutToCartProductsPerDay();
    }

    public List<Long> getTheMostBoughtProductsPerMonth() {
        return productsAnalyticsRepository.getTheMostBoughtProductsPerMonth();
    }

}
