package com.stortor.hw7.servieces;

import com.stortor.hw7.converters.OrderItemConverter;
import com.stortor.hw7.dto.OrderItemDto;
import com.stortor.hw7.entity.Order;
import com.stortor.hw7.entity.OrderItem;
import com.stortor.hw7.entity.Product;
import com.stortor.hw7.entity.User;
import com.stortor.hw7.exceptions.ResourceNotFoundException;
import com.stortor.hw7.repositories.OrderItemRepository;
import com.stortor.hw7.validators.OrderValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final CartService cartService;
    private final OrderItemConverter orderItemConverter;
    private final OrderValidator orderItemValidator;

    public void createOrderWithOrderItems(User user, Order order) {
        List<OrderItem> orderItems = cartService.getCurrentCart().getItems().stream().map(p -> orderItemConverter.dtoToEntity(p, user, order)).collect(Collectors.toList());
        orderItemValidator.validateOrderItems(orderItems);
        orderItemRepository.saveAll(orderItems);
    }

    public List<OrderItem> showAllOrders() {
        return orderItemRepository.findAll();
    }
}
