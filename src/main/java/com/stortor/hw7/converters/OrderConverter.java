package com.stortor.hw7.converters;

import com.stortor.hw7.dto.Cart;
import com.stortor.hw7.dto.OrderDto;
import com.stortor.hw7.entity.Order;
import com.stortor.hw7.entity.User;
import com.stortor.hw7.exceptions.ResourceNotFoundException;
import com.stortor.hw7.servieces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConverter {

    public Order dtoToEntity(User user, Cart cart, OrderDto orderDto) {
        Order order = new Order(
                user,
                cart.getTotalPrice(),
                orderDto.getAddress(),
                orderDto.getPhone()
        );
        return order;
    }

    public OrderDto entityToDto(Order order) {
        return new OrderDto(
                order.getAddress(),
                order.getPhone()
        );
    }

}
