package com.stortor.hw7.servieces;

import com.stortor.hw7.dto.Cart;
import com.stortor.hw7.dto.OrderItemDto;
import com.stortor.hw7.entity.Order;
import com.stortor.hw7.entity.OrderItem;
import com.stortor.hw7.entity.Product;
import com.stortor.hw7.entity.User;
import com.stortor.hw7.exceptions.ResourceNotFoundException;
import com.stortor.hw7.repositories.OrderRepository;
import com.stortor.hw7.validators.OrderValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductService productService;

    public void createOrder(User user, Order order) {
        Cart currentCart = cartService.getCurrentCart();
        order.setUser(user);
        order.setTotalPrice(currentCart.getTotalPrice());
        List<OrderItem> items = currentCart.getItems().stream()
                .map(orderItemDto -> {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setQuantity(orderItemDto.getQuantity());
                    item.setPricePerProduct(orderItemDto.getPricePerProduct());
                    item.setPrice(orderItemDto.getPrice());
                    item.setProduct(productService.findProductById(orderItemDto.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
                    return item;
                }).collect(Collectors.toList());
        order.setItems(items);
        orderRepository.save(order);
        currentCart.clear();
    }

    public List<Order> findOrdersByUsername(String username) {
        return orderRepository.findAllByUsername(username);
    }
}
