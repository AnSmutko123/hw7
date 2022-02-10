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

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductsAnalyticsService {

    private final ProductsAnalyticsRepository productsAnalyticsRepository;
    private final ProductAnalyticsConverter converter;

    // не знаю правильно ли тут использую лист, но мне показалось, что одного финального листа, который создается один раз для наших задач будет достаточно
    private final List<ProductsAnalytics> analyticsList = new ArrayList<>();

    public void addToCartProducts(ProductAnalyticsDto analyticsProductDto) {
        ProductsAnalytics productsAnalytics = converter.dtoToEntity(analyticsProductDto);
        analyticsList.add(productsAnalytics);
    }

    // для того чтобы не дергать бд по каждому запросу на добавление продукта
    // по таймеру отправляем в бд
    @Transactional
    @Scheduled(fixedRate = 30000)
    public void addAnalyticsProductsToBd() {
        analyticsList.stream().forEach(i -> productsAnalyticsRepository.save(i));
        analyticsList.clear();
        log.info(String.valueOf(new Date()));
    }

    public List<Long> getTheMostPutToCartProductsPerDay() {
        return productsAnalyticsRepository.getTheMostPutToCartProductsPerDay();
    }

    public List<Long> getTheMostBoughtProductsPerMonth() {
        return productsAnalyticsRepository.getTheMostBoughtProductsPerMonth();
    }

}
