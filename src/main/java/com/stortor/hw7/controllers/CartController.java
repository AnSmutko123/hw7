package com.stortor.hw7.controllers;

import com.stortor.hw7.dto.ProductDto;
import com.stortor.hw7.servieces.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/api/v1/carts")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public Map<ProductDto, Integer> showCart() {
        return cartService.showCart();
    }

    @GetMapping("/{id}")
    public ProductDto addToCart(@PathVariable Long id) {
        return cartService.addToCart(id);
    }

    @DeleteMapping("/{id}")
    public void removeFromCart(@PathVariable Long id) {
        cartService.removeFromCart(id);
    }
}
