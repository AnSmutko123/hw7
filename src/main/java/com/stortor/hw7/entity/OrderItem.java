package com.stortor.hw7.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "order_items")
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "order_id")
    @ManyToOne
    private Order order;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "pricePerProduct")
    private Integer pricePerProduct;

    @Column(name = "price")
    private Integer price;

    public OrderItem(Product product, User user, Order order, Integer quantity, Integer pricePerProduct, Integer price) {
        this.product = product;
        this.user = user;
        this.order = order;
        this.quantity = quantity;
        this.pricePerProduct = pricePerProduct;
        this.price = price;
    }
}
