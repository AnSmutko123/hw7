package com.stortor.spring.web.core.converters;

import com.stortor.spring.web.core.dto.OrderDetailsDto;
import com.stortor.spring.web.core.dto.OrderDto;
import com.stortor.spring.web.core.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final OrderItemConverter orderItemConverter;

    public Order dtoToEntity(OrderDetailsDto orderDto) {
        throw new UnsupportedOperationException();
    }

    public OrderDto entityToDto(Order order) {
        OrderDto out = new OrderDto();
        out.setId(order.getId());
        out.setAddress(order.getAddress());
        out.setPhone(order.getPhone());
        out.setTotalPrice(order.getTotalPrice());
        out.setUsername(order.getUsername());
        out.setItems(order.getItems().stream().map(
                orderItemConverter::entityToDto).collect(Collectors.toList())
        );
        return out;
    }

}