package com.stortor.hw7.dto;

import com.stortor.hw7.entity.OrderItem;
import com.stortor.hw7.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private int totalPrice;
    private String address;
    private String phone;
    private List<OrderItemDto> orderItemsDto;

    public OrderDto(int totalPrice, String address, String phone) {
        this.totalPrice = totalPrice;
        this.address = address;
        this.phone = phone;
    }
}
