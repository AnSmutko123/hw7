package com.stortor.hw7.servieces;

import com.stortor.hw7.dto.OrderItemDto;
import com.stortor.hw7.entity.Order;
import com.stortor.hw7.entity.Product;
import com.stortor.hw7.entity.User;
import com.stortor.hw7.exceptions.ResourceNotFoundException;
import com.stortor.hw7.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final UserService userService;

    @Transactional
    public Order createOrder(Order order) {
        order.setId(null);
        Order orderWithoutOrderItems = new Order(order.getUser(), order.getTotalPrice(), order.getAddress(), order.getPhone());
        log.warn(orderWithoutOrderItems.toString());
        orderRepository.save(orderWithoutOrderItems);
        return orderWithoutOrderItems;
    }

}
