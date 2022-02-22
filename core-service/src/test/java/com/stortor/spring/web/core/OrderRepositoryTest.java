package com.stortor.spring.web.core;

import com.stortor.spring.web.api.carts.CartDto;
import com.stortor.spring.web.api.carts.CartItemDto;
import com.stortor.spring.web.api.core.OrderDetailsDto;
import com.stortor.spring.web.api.core.OrderItemDto;
import com.stortor.spring.web.core.entity.Category;
import com.stortor.spring.web.core.entity.Order;
import com.stortor.spring.web.core.entity.OrderItem;
import com.stortor.spring.web.core.entity.Product;
import com.stortor.spring.web.core.repositories.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;



import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@DataJpaTest
@ActiveProfiles("test")
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void createOrderTest() {
        Product milk = new Product();
        milk.setId(1L);
        milk.setTitle("Milk");
        milk.setPrice(BigDecimal.valueOf(100));
        milk.setCategory(new Category());

        CartItemDto cartItemMilk = new CartItemDto(1L, "Milk", 3, BigDecimal.valueOf(60), BigDecimal.valueOf(180));
        CartDto cartDto = new CartDto(Arrays.asList(cartItemMilk), cartItemMilk.getPrice());
        String username = "Alice";
        OrderDetailsDto orderDetailsDto = new OrderDetailsDto("Moscow", "dsfsdf", "111111");
        Order order = new Order();
        List<OrderItem> items = new ArrayList<>(Arrays.asList(
                new OrderItem(milk, order, cartItemMilk.getQuantity(), cartItemMilk.getPricePerProduct(), cartItemMilk.getPrice())));
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(orderDetailsDto.getPhone());
        order.setUsername(username);
        order.setTotalPrice(cartDto.getTotalPrice());
        order.setItems(items);

        entityManager.persist(order);
        entityManager.flush();

        List<Order> orders = orderRepository.findAllByUsername("Alice");
        Order firstOrder = orderRepository.findAllByUsername("Alice").stream().findFirst().get();
        Assertions.assertEquals(2, orders.size());
        Assertions.assertEquals(2, firstOrder.getItems().size());
        Assertions.assertEquals(3, orders.stream().mapToInt(o -> o.getItems().size()).sum());
    }


}
