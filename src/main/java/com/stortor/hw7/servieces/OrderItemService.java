package com.stortor.hw7.servieces;

import com.stortor.hw7.dto.OrderItemDto;
import com.stortor.hw7.entity.Order;
import com.stortor.hw7.entity.OrderItem;
import com.stortor.hw7.entity.User;
import com.stortor.hw7.exceptions.ResourceNotFoundException;
import com.stortor.hw7.repositories.OrderItemRepository;
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
    private final ProductService productService;

    public void createOrderWithOrderItems(Long id, Order order, List<OrderItemDto> orderItemDtos) {
        List<OrderItem> orderItems = orderItemDtos
                .stream()
                .map(p -> new OrderItem(
                        productService.findProductById(p.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Товар не найден, id: " + id)),
                        order.getUser(), order, p.getQuantity(), p.getPricePerProduct(), p.getPrice())).collect(Collectors.toList());
        orderItems.stream().forEach(o -> orderItemRepository.save(o));
    }
}
