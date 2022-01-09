package com.stortor.hw7.controllers;


import com.stortor.hw7.converters.OrderConverter;
import com.stortor.hw7.converters.OrderItemConverter;
import com.stortor.hw7.dto.OrderDto;
import com.stortor.hw7.dto.OrderItemDto;
import com.stortor.hw7.entity.Order;
import com.stortor.hw7.entity.OrderItem;
import com.stortor.hw7.entity.User;
import com.stortor.hw7.exceptions.ResourceNotFoundException;
import com.stortor.hw7.servieces.CartService;
import com.stortor.hw7.servieces.OrderItemService;
import com.stortor.hw7.servieces.OrderService;
import com.stortor.hw7.servieces.UserService;
import com.stortor.hw7.validators.OrderValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;
    private final OrderItemService orderItemService;

    private final OrderConverter orderConverter;
    private final OrderItemConverter orderItemConverter;


    @PostMapping()
    public void createOrder(Principal principal, @RequestBody OrderDto orderDto) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException(String.format("Пользователь с именем = %s не найден", principal.getName())));
        Order order = orderConverter.dtoToEntity(user, cartService.getCurrentCart(), orderDto);
        orderItemService.createOrderWithOrderItems(user, orderService.createOrder(order));
    }

    @GetMapping()
    public List<OrderDto> showAllOrders() {
        log.info(orderService.showAllOrders().toString());
        return orderService.showAllOrders().stream().map(p -> orderConverter.entityToDto(p)).collect(Collectors.toList());
    }

    @GetMapping("/items")
    public List<OrderItemDto> showAllOrderItems() {
        return orderItemService.showAllOrders().stream().map(p -> orderItemConverter.entityToDto(p)).collect(Collectors.toList());
    }


}
