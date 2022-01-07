package com.stortor.hw7.servieces;

import com.stortor.hw7.dto.Cart;
import com.stortor.hw7.entity.Product;
import com.stortor.hw7.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductService productService;
    private Cart cart;

    @PostConstruct
    public void init() {
        cart = new Cart();
    }

    public Cart getCurrentCart() {
        return cart;
    }

    public void addProductByIdToCart(Long productId) {
        if (!getCurrentCart().addProduct(productId)) {
            Product product = productService.findProductById(productId).orElseThrow( ()-> new ResourceNotFoundException("Невозможно добавить продукт в корзину. Продукт не найден, id: " + productId));
            getCurrentCart().addProduct(product);
        }
    }

    public void clear() {
        getCurrentCart().clear();
    }

}
