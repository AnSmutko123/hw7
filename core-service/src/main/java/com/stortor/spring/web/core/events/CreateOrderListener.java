package com.stortor.spring.web.core.events;

import com.stortor.spring.web.api.carts.CartDto;
import com.stortor.spring.web.api.core.ProductDto;
import com.stortor.spring.web.api.exceptions.ResourceNotFoundException;
import com.stortor.spring.web.core.converters.ProductConverter;
import com.stortor.spring.web.core.entity.Order;
import com.stortor.spring.web.core.entity.OrderItem;
import com.stortor.spring.web.core.entity.Product;
import com.stortor.spring.web.core.enums.OrderStateEnum;
import com.stortor.spring.web.core.integrations.CartServiceIntegration;
import com.stortor.spring.web.core.repositories.OrderRepository;
import com.stortor.spring.web.core.servieces.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CreateOrderListener implements ApplicationListener<CreateOrderEvents> {

    private final CartServiceIntegration cartServiceIntegration;
    private final ProductsService productsService;
    private final ProductConverter productConverter;
    private final OrderRepository orderRepository;

    @Override
    public void onApplicationEvent(CreateOrderEvents event) {
        CartDto currentCartDto = cartServiceIntegration.getUserCart(event.getUsername());
        Order order = Order.builder()
                .address(event.getOrderDetailsDto().getAddress())
                .phone(event.getOrderDetailsDto().getPhone())
                .city(event.getOrderDetailsDto().getCity())
                .state(OrderStateEnum.CREATED)
                .totalPrice(currentCartDto.getTotalPrice())
                .username(event.getUsername())
                .build();

        List<ProductDto> productDtoList = new ArrayList<>();
        List<OrderItem> items = currentCartDto.getItems().stream()
                .map(orderItemDto -> {
                    Product product = productsService.findById(orderItemDto.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
                    OrderItem item = OrderItem.builder()
                            .setOrder(order)
                            .setQuantity(orderItemDto.getQuantity())
                            .setPricePerProduct(orderItemDto.getPricePerProduct())
                            .setPrice(orderItemDto.getPrice())
                            .setProduct(product)
                            .build();
                    productDtoList.add(productConverter.entityToDto(product));
                    return item;
                }).collect(Collectors.toList());

        order.setItems(items);
//        analyticsProductsIntegration.sendToAnalytics(productDtoList);
        orderRepository.save(order);
        cartServiceIntegration.clearUserCart(event.getUsername());
    }
}
