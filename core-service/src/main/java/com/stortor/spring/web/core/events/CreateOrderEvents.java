package com.stortor.spring.web.core.events;

import com.stortor.spring.web.api.core.OrderDetailsDto;
import com.stortor.spring.web.core.converters.ProductConverter;
import com.stortor.spring.web.core.integrations.CartServiceIntegration;
import com.stortor.spring.web.core.repositories.OrderRepository;
import com.stortor.spring.web.core.servieces.ProductsService;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

public class CreateOrderEvents extends ApplicationEvent {

    private final OrderRepository orderRepository;
    private final ProductsService productService;
    private final CartServiceIntegration cartServiceIntegration;
    private final ProductConverter productConverter;
    private final String username;
    private final OrderDetailsDto orderDetailsDto;

    public CreateOrderEvents(String username, OrderDetailsDto orderDetailsDto, OrderRepository orderRepository, ProductsService productService, CartServiceIntegration cartServiceIntegration, ProductConverter productConverter) {
        super(orderRepository);
        this.username = username;
        this.orderDetailsDto = orderDetailsDto;
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.cartServiceIntegration = cartServiceIntegration;
        this.productConverter = productConverter;
    }

    public String getUsername() {
        return username;
    }

    public OrderDetailsDto getOrderDetailsDto() {
        return orderDetailsDto;
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

    public ProductsService getProductService() {
        return productService;
    }

    public CartServiceIntegration getCartServiceIntegration() {
        return cartServiceIntegration;
    }

    public ProductConverter getProductConverter() {
        return productConverter;
    }
}
