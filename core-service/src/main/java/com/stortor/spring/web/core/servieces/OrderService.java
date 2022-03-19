package com.stortor.spring.web.core.servieces;

import com.stortor.spring.web.api.carts.CartDto;
import com.stortor.spring.web.api.core.ProductDto;
import com.stortor.spring.web.api.exceptions.ResourceNotFoundException;
import com.stortor.spring.web.api.core.OrderDetailsDto;
import com.stortor.spring.web.core.converters.ProductConverter;
import com.stortor.spring.web.core.entity.Order;
import com.stortor.spring.web.core.entity.OrderItem;
import com.stortor.spring.web.core.entity.Product;
import com.stortor.spring.web.core.enums.OrderStateEnum;
import com.stortor.spring.web.core.integrations.AnalyticsProductsIntegration;
import com.stortor.spring.web.core.integrations.CartServiceIntegration;
import com.stortor.spring.web.core.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductsService productService;
    private final CartServiceIntegration cartServiceIntegration;
    private final AnalyticsProductsIntegration analyticsProductsIntegration;
    private final ProductConverter productConverter;

    public void createOrder(String username, OrderDetailsDto orderDetailsDto) {
        CartDto currentCartDto = cartServiceIntegration.getUserCart(username);
        Order order = new Order();
        order.setAddress(orderDetailsDto.getAddress());
        order.setCity(orderDetailsDto.getCity());
        order.setPhone(orderDetailsDto.getPhone());
        order.setUsername(username);
        order.setTotalPrice(currentCartDto.getTotalPrice());
        List<ProductDto> productDtoList = new ArrayList<>();
        List<OrderItem> items = currentCartDto.getItems().stream()
                .map(orderItemDto -> {
                    Product product = productService.findById(orderItemDto.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setQuantity(orderItemDto.getQuantity());
                    item.setPricePerProduct(orderItemDto.getPricePerProduct());
                    item.setPrice(orderItemDto.getPrice());
                    item.setProduct(product);
                    productDtoList.add(productConverter.entityToDto(product));
                    return item;
                }).collect(Collectors.toList());
        analyticsProductsIntegration.sendToAnalytics(productDtoList);
        order.setItems(items);
        order.setState(OrderStateEnum.CREATED);
        orderRepository.save(order);
        cartServiceIntegration.clearUserCart(username);
    }

    @Transactional
    public void updateOrderState(Order order, OrderStateEnum stateEnum) {
        order.setState(stateEnum);
        orderRepository.save(order);
    }

    public List<Order> findOrdersByUsername(String username) {
        return orderRepository.findAllByUsername(username);
    }

    public Optional<Order> findById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public Optional<Order> findByIdCreated(Long orderId) {
        return orderRepository.findByIdAndState_Created(orderId);
    }
}

