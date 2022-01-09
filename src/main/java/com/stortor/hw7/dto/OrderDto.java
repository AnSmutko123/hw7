package com.stortor.hw7.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDto {

    private String address;
    private String phone;

    public OrderDto( String address, String phone) {
        this.address = address;
        this.phone = phone;
    }
}
