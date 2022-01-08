package com.stortor.hw7.converters;

import com.stortor.hw7.dto.OrderDto;
import com.stortor.hw7.dto.OrderItemDto;
import com.stortor.hw7.dto.ProductDto;
import com.stortor.hw7.entity.Order;
import com.stortor.hw7.entity.OrderItem;
import com.stortor.hw7.entity.Product;
import com.stortor.hw7.entity.User;
import com.stortor.hw7.exceptions.ResourceNotFoundException;
import com.stortor.hw7.servieces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConverter {

    private final UserService userService;

    public Order dtoToEntity(Long id, OrderDto orderDto) {
        User user = userService.findUserById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Пользователь с id = %d при оформлении заказа не найден")));
        Order order = new Order(user, orderDto.getTotalPrice(), orderDto.getAddress(), orderDto.getPhone());
        log.info(order.toString());
        return new Order(user, orderDto.getTotalPrice(), orderDto.getAddress(), orderDto.getPhone());
    }

//    public Order dtoToEntityWithOrderItems(Long id, OrderDto orderDto) {
//        User user = userService.findUserById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Пользователь с id = %d при оформлении заказа не найден")));
//        return new Order(user, orderDto.getTotalPrice(), orderDto.getAddress(), orderDto.getPhone(), orderDto.getOrderItemsDto());
//    }
//
//    public Order dtoToEntityWithoutOrderItems(Long id, OrderDto orderDto) {
//        User user = userService.findUserById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Пользователь с id = %d при оформлении заказа не найден")));
//        return new Order(user, orderDto.getTotalPrice(), orderDto.getAddress(), orderDto.getPhone());
//    }

//    public OrderDto entityToDto(OrderItem orderItem) {
//        return new OrderItemDto();
//    }

}
