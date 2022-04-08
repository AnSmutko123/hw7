package com.stortor.spring.web.core.events;

import com.stortor.spring.web.api.core.OrderDetailsDto;
import com.stortor.spring.web.core.converters.ProductConverter;
import com.stortor.spring.web.core.integrations.CartServiceIntegration;
import com.stortor.spring.web.core.repositories.OrderRepository;
import com.stortor.spring.web.core.servieces.ProductsService;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

public class CreateOrderEvents extends ApplicationEvent {

    private String username;
    private OrderDetailsDto orderDetailsDto;

    public CreateOrderEvents(String username, OrderDetailsDto orderDetailsDto) {
        super(username);
        this.username = username;
        this.orderDetailsDto = orderDetailsDto;
    }

    public String getUsername() {
        return username;
    }

    public OrderDetailsDto getOrderDetailsDto() {
        return orderDetailsDto;
    }

}
