package com.stortor.hw7.converters;

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

    private final UserService userService;

    public Order dtoToEntity(Long id, OrderDto orderDto) {
        User user = userService.findUserById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Пользователь с id = %d при оформлении заказа не найден")));
        Order order = new Order(
                user,
                orderDto.getTotalPrice(),
                orderDto.getAddress(),
                orderDto.getPhone()
        );
        return order;
    }

    public OrderDto entityToDto(Order order) {
        return new OrderDto(
                order.getTotalPrice(),
                order.getAddress(),
                order.getPhone()
        );
    }

}
