package com.stortor.spring.web.core.controllers;


import com.stortor.spring.web.core.converters.OrderConverter;
import com.stortor.spring.web.api.core.OrderDetailsDto;
import com.stortor.spring.web.api.core.OrderDto;
import com.stortor.spring.web.core.servieces.OrderService;
import com.stortor.spring.web.core.validators.OrderValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final OrderConverter orderConverter;
    private final OrderValidator orderValidator;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username, @RequestBody OrderDetailsDto orderDetailsDto) {
        orderValidator.validate(orderDetailsDto);
        orderService.createOrder(username, orderDetailsDto);
    }

    @GetMapping()
    public List<OrderDto> getCurrentUserOrders(@RequestHeader String username) {
        return orderService.findOrdersByUsername(username).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }



}
