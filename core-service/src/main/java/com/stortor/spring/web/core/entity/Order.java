package com.stortor.spring.web.core.entity;

import com.stortor.spring.web.core.enums.OrderStateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @Column(name = "username")
    private String username;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "phone")
    private String phone;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private OrderStateEnum state;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<OrderItem> items;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Order(String address, String phone) {
        this.address = address;
        this.phone = phone;
    }

    public Order(String username, BigDecimal totalPrice, String address, String city, String phone, OrderStateEnum state, List<OrderItem> items) {
        this.username = username;
        this.totalPrice = totalPrice;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.state = state;
        this.items = items;
    }

    public static OrderEntityBuilder builder() {
        return new OrderEntityBuilder();
    }

    public static class OrderEntityBuilder {
        private String username;
        private BigDecimal totalPrice;
        private String address;
        private String city;
        private String phone;
        private OrderStateEnum state;
        private List<OrderItem> items;

        public OrderEntityBuilder setUsername(final String username) {
            this.username = username;
            return this;
        }

        public OrderEntityBuilder setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public OrderEntityBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public OrderEntityBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public OrderEntityBuilder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public OrderEntityBuilder setState(OrderStateEnum state) {
            this.state = state;
            return this;
        }

        public OrderEntityBuilder setItems(List<OrderItem> items) {
            this.items = items;
            return this;
        }

        public Order build() {
            return new Order(username, totalPrice, address, city, phone, state, items);
        }
    }


}
