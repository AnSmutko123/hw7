package com.stortor.hw7.data;

import com.stortor.hw7.dto.ProductDto;
import com.stortor.hw7.entity.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Cart {

    private List<ProductDto> productsDtoList;

    public Cart(List<ProductDto> products) {
        this.productsDtoList = products;
    }

    public ProductDto addToCart(ProductDto productDto) {
        productsDtoList.add(productDto);
        return productDto;
    }

    public void deleteFromCart(Long id) {
        productsDtoList.removeIf(p -> p.getId() == id));
    }

    public List<ProductDto> showCart() {
        return productsDtoList;
    }
}
