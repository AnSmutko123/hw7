package com.stortor.hw7.servieces;

import com.stortor.hw7.data.Cart;
import com.stortor.hw7.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final Cart cart;
    private final ProductService productService;

    public List<ProductDto> showCart() {
        return cart.showCart();
    }

    public ProductDto addToCart(Long id) {
        ProductDto productDto = productService.findProductDtoById(id);
        return cart.addToCart(productDto);
    }

    public void removeFromCart(Long id) {
        cart.deleteFromCart(id);
    }
}
