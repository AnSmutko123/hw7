package com.stortor.spring.web.cart.converterts;

import com.stortor.spring.web.api.carts.CartDto;
import com.stortor.spring.web.api.carts.CartItemDto;
import com.stortor.spring.web.cart.model.Cart;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartConverter {
    public CartDto modelToDto(Cart cart) {
        List<CartItemDto> items = cart.getItems().stream().
                map(i -> new CartItemDto(i.getProductId(), i.getProductTitle(), i.getQuantity(), i.getPricePerProduct(), i.getPrice()))
                .collect(Collectors.toList());
        CartDto cartDto = new CartDto(items, cart.getTotalPrice());
        return cartDto;
    }
}
