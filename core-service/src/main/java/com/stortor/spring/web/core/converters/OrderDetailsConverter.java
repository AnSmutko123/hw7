package com.stortor.spring.web.core.converters;

import com.stortor.spring.web.core.dto.OrderDetailsDto;
import com.stortor.spring.web.core.entity.Order;
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
