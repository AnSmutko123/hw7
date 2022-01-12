package com.stortor.hw7.controllers;


import com.stortor.hw7.converters.OrderConverter;
import com.stortor.hw7.converters.OrderDetailsConverter;
import com.stortor.hw7.dto.OrderDetailsDto;
import com.stortor.hw7.dto.OrderDto;
import com.stortor.hw7.entity.Order;
import com.stortor.hw7.entity.User;
import com.stortor.hw7.exceptions.ResourceNotFoundException;
import com.stortor.hw7.servieces.OrderService;
import com.stortor.hw7.servieces.UserService;
import com.stortor.hw7.validators.OrderValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    private final OrderDetailsConverter orderDetailsConverter;
    private final OrderConverter orderConverter;
    private final OrderValidator orderValidator;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(Principal principal, @RequestBody OrderDetailsDto orderDetailsDto) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException(String.format("Пользователь с именем = %s не найден", principal.getName())));
        log.info(orderDetailsDto.toString());
        orderValidator.validate(orderDetailsDto);
        Order order = orderDetailsConverter.dtoToEntity(orderDetailsDto);
        orderService.createOrder(user, order);
    }

    @GetMapping()
    public List<OrderDto> getCurrentUserOrders(Principal principal) {
        return orderService.findOrdersByUsername(principal.getName()).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }


}
