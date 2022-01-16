package com.stortor.hw7.repositories.specification;

import com.stortor.hw7.entity.Category;
import com.stortor.hw7.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> priceGreaterOrEqualsThen(Integer price) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price));
    }

    public static Specification<Product> priceLessOrEqualsThen(Integer price) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price));
    }

    public static Specification<Product> titleLike(String titlePart) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart)));
    }

    public static Specification<Product> titleLikeCategory(String titlePartCategory) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("category").get("title"), String.format("%%%s%%", titlePartCategory)));
    }

}
