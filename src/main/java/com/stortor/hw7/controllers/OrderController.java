package com.stortor.hw7.controllers;


import com.stortor.hw7.converters.OrderConverter;
import com.stortor.hw7.converters.ProductConverter;
import com.stortor.hw7.dto.OrderDto;
import com.stortor.hw7.entity.Order;
import com.stortor.hw7.entity.OrderItem;
import com.stortor.hw7.entity.Product;
import com.stortor.hw7.exceptions.ResourceNotFoundException;
import com.stortor.hw7.servieces.OrderItemService;
import com.stortor.hw7.servieces.OrderService;
import com.stortor.hw7.servieces.ProductService;
import com.stortor.hw7.servieces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final OrderItemService orderItemService;

    @PostMapping("/{id}")
    public void createOrder(@PathVariable Long id, @RequestBody OrderDto orderDto){
        Order order = orderConverter.dtoToEntity(id, orderDto);
        log.warn(order.toString());
        Order orderSavedInDb = orderService.createOrder(order);
        orderItemService.createOrderWithOrderItems(id, orderSavedInDb, orderDto.getOrderItemsDto());

    }


}
