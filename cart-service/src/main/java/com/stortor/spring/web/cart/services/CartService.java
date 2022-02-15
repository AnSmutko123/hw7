package com.stortor.spring.web.cart.services;

import com.stortor.spring.web.api.core.ProductDto;
import com.stortor.spring.web.api.exceptions.ResourceNotFoundException;
import com.stortor.spring.web.cart.integrations.AnalyticsProductsIntegration;
import com.stortor.spring.web.cart.integrations.ProductsServiceIntegration;
import com.stortor.spring.web.cart.model.Cart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final ProductsServiceIntegration productsServiceIntegration;
    private final AnalyticsProductsIntegration analyticsProductsIntegration;
    private final RedisTemplate<String, Object> redisTemplate;

    private final List<ProductDto> productDtoAnalyticsList = new ArrayList<>();

    @Value("${utils.cart.prefix}")
    private String cartPrefix;

    public String getCartUuidFromSuffix(String suffix) {
        return cartPrefix + suffix;
    }

    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }

    public Cart getCurrentCart(String cartKey) {
        if (!redisTemplate.hasKey(cartKey)) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(cartKey);
    }

    public void addToCart(String cartKey, Long productId) {
        ProductDto productDto = productsServiceIntegration.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found, id = " + productId));
        productDtoAnalyticsList.add(productDto);
        execute(cartKey, c -> {
            c.add(productDto);
        });
    }

    // 30 sec
    @Scheduled(fixedRate = 30000)
    public void addAnalyticsProductsToBd() {
        log.info(new Date().toString());
        analyticsProductsIntegration.sendToAnalytics(productDtoAnalyticsList);
        productDtoAnalyticsList.clear();
    }

    public void clearCart(String cartKey) {
        execute(cartKey, Cart::clear);
    }

    public void removeItemFromCart(String cartKey, Long productId) {
        execute(cartKey, c -> c.remove(productId));
    }

    public void decrementItem(String cartKey, Long productId) {
        execute(cartKey, c -> c.decrement(productId));
    }

    public void merge(String userCartKey, String guestCartKey) {
        Cart guestCart = getCurrentCart(guestCartKey);
        Cart userCart = getCurrentCart(userCartKey);
        userCart.merge(guestCart);
        updateCart(guestCartKey, guestCart);
        updateCart(userCartKey, userCart);
    }

    private void execute(String cartKey, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartKey);
        action.accept(cart);
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    public void updateCart(String cartKey, Cart cart) {
        redisTemplate.opsForValue().set(cartKey, cart);
    }
}
