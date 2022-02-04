package com.stortor.spring.web.analytics.repositories;

import com.stortor.spring.web.analytics.entities.ProductsAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsAnalyticsRepository extends JpaRepository<ProductsAnalytics, Long>, JpaSpecificationExecutor<ProductsAnalytics> {

    @Query(
            value = "select products_analytics.product_id from products_analytics " +
                    "where products_analytics.added_to_cart_date = current_date " +
                    "group by products_analytics.product_id " +
                    "order by count(products_analytics.product_id) desc limit 5",
            nativeQuery = true
    )
    List<Long> getTheMostPutToCartProductsPerDay();

    @Query(
            value = "select products_analytics.product_id from products_analytics " +
                    "where month (products_analytics.bought_date) = month (now()) and year (products_analytics.bought_date) = year (now())" +
                    "group by products_analytics.product_id " +
                    "order by count(products_analytics.product_id) desc limit 5",
            nativeQuery = true
    )
    List<Long> getTheMostBoughtProductsPerMonth();


}
