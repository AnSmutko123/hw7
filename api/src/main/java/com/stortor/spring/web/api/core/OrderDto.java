package com.stortor.spring.web.api.core;


import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Модель заказа")
public class OrderDto {

    @Schema(description = "ID продукта", required = true, example = "1")
    private Long id;

    @Schema(description = "Имя пользователя", required = true, example = "Alice")
    private String username;

    @Schema(description = "Список товаров", required = true)
    private List<OrderItemDto> items;

    @Schema(description = "Итоговая цена заказа", required = true, example = "800.00")
    private BigDecimal totalPrice;

    @Schema(description = "Адрес доставки", required = true, example = "Moscow")
    private String address;

    @Schema(description = "Город доставки", required = true, example = "Moscow")
    private String city;

    @Schema(description = "Номер телефона", required = true, example = "7878565464")
    private String phone;

    @Schema(description = "Статус заказа", required = true, example = "PAID")
    private String state;

    public OrderDto() {
    }

    public OrderDto(Long id, String username, List<OrderItemDto> items, BigDecimal totalPrice, String address, String city, String phone, String state) {
        this.id = id;
        this.username = username;
        this.items = items;
        this.totalPrice = totalPrice;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

