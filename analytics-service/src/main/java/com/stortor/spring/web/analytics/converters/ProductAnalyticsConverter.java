package com.stortor.spring.web.analytics.converters;

import com.stortor.spring.web.analytics.entities.ProductsAnalytics;
import com.stortor.spring.web.api.analytics.ProductAnalyticsDto;
import org.springframework.stereotype.Component;


@Component
public class ProductAnalyticsConverter {
    public ProductAnalyticsDto entityToDto(ProductsAnalytics productsAnalytics) {
        ProductAnalyticsDto p = new ProductAnalyticsDto();
        p.setProductId(productsAnalytics.getProductId());
        p.setProductTitle(productsAnalytics.getProductTitle());
        p.setBoughtDate(productsAnalytics.getBoughtDate());
        p.setAddedToCartDate(productsAnalytics.getAddedToCartDate());
        return p;
    }

    public ProductsAnalytics dtoToEntity(ProductAnalyticsDto productAnalyticsDto) {
        ProductsAnalytics p = new ProductsAnalytics();
        p.setProductId(productAnalyticsDto.getProductId());
        p.setProductTitle(productAnalyticsDto.getProductTitle());
        p.setBoughtDate(productAnalyticsDto.getBoughtDate());
        p.setAddedToCartDate(productAnalyticsDto.getAddedToCartDate());
        return p;

    }
}
