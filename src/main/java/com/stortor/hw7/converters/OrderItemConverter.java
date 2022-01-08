package com.stortor.hw7.converters;

import com.stortor.hw7.dto.OrderDto;
import com.stortor.hw7.dto.OrderItemDto;
import com.stortor.hw7.entity.Order;
import com.stortor.hw7.entity.OrderItem;
import com.stortor.hw7.entity.User;
import com.stortor.hw7.exceptions.ResourceNotFoundException;
import com.stortor.hw7.servieces.ProductService;
import com.stortor.hw7.servieces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderItemConverter {

    private final ProductService productService;

    public OrderItem dtoToEntity(OrderItemDto orderItemDto, Order order) {
        return new OrderItem(
                productService.findProductById(orderItemDto.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Товар не найден, id: " + orderItemDto.getProductId())),
                order.getUser(),
                order,
                orderItemDto.getQuantity(),
                orderItemDto.getPricePerProduct(),
                orderItemDto.getPrice());
    }


    public OrderItemDto entityToDto(OrderItem orderItem) {
        return new OrderItemDto(
                orderItem.getProduct().getId(),
                orderItem.getProduct().getTitle(),
                orderItem.getQuantity(),
                orderItem.getPricePerProduct(),
                orderItem.getPrice()
        );
    }

}
