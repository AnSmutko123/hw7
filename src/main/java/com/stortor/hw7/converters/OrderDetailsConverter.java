package com.stortor.hw7.converters;

import com.stortor.hw7.dto.OrderDetailsDto;
import com.stortor.hw7.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderDetailsConverter {

    public Order dtoToEntity(OrderDetailsDto orderDetailsDto) {
        return new Order(
                orderDetailsDto.getAddress(),
                orderDetailsDto.getPhone()
        );
    }

    public OrderDetailsDto entityToDto(Order order) {
        return new OrderDetailsDto(
                order.getAddress(),
                order.getPhone()
        );
    }

}
