package com.stortor.hw7.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDetailsDto {

    private String address;
    private String phone;

    public OrderDetailsDto(String address, String phone) {
        this.address = address;
        this.phone = phone;
    }
}
