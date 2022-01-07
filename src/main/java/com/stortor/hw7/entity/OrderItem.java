package com.stortor.hw7.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "product_id")
    @OneToOne
    private Product product;

    @Column(name = "user_id")
    @ManyToOne
    private User user;

    @Column(name = "order_id")
    @ManyToOne
    private Order order;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "pricePerProduct")
    private Integer pricePerProduct;

    @Column(name = "price")
    private Integer price;


}
