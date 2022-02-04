package com.stortor.spring.web.analytics.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "products_analytics")
public class ProductsAnalytics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_title")
    private String productTitle;

    @Column(name = "bought_date")
    private Date boughtDate;

    @Column(name = "added_to_cart_date")
    private Date addedToCartDate;

    public ProductsAnalytics(Long productId, String productTitle, Date boughtData, Date addedToCartData) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.boughtDate = boughtData;
        this.addedToCartDate = addedToCartData;
    }


}
