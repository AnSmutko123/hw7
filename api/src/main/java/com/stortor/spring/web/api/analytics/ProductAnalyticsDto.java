package com.stortor.spring.web.api.analytics;

import java.util.Date;

public class ProductAnalyticsDto {
    private Long productId;
    private String productTitle;
    private Date boughtDate;
    private Date addedToCartDate;

    public ProductAnalyticsDto() {
    }

    public ProductAnalyticsDto(Long productId, String productTitle, Date boughtData, Date addedToCartData) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.boughtDate = boughtData;
        this.addedToCartDate = addedToCartData;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Date getBoughtDate() {
        return boughtDate;
    }

    public void setBoughtDate(Date boughtDate) {
        this.boughtDate = boughtDate;
    }

    public Date getAddedToCartDate() {
        return addedToCartDate;
    }

    public void setAddedToCartDate(Date addedToCartDate) {
        this.addedToCartDate = addedToCartDate;
    }
}
