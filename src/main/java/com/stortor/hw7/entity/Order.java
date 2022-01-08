package com.stortor.hw7.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

//    @JoinColumn()
//    @OneToMany
//    private List<OrderItem> orderItems;

    public Order(User user, int totalPrice, String address, String phone) {
        this.user = user;
        this.totalPrice = totalPrice;
        this.address = address;
        this.phone = phone;
    }
}
