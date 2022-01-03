package com.stortor.hw7.data;

import com.stortor.hw7.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class Cart {

    // ключ - значение
    private Map<ProductDto, Integer> productsDtoMap;

//    public Cart(Map<ProductDto, Integer> productsDtoList) {
//        this.productsDtoMap = productsDtoList;
//    }
//
//    public ProductDto addToCart(ProductDto productDto) {
//        if (productsDtoMap.containsKey(productDto)) {
//            Integer count = productsDtoMap.get(productDto);
//            count++;
//            productsDtoMap.put(productDto, count);
//        }
//        if (!productsDtoMap.containsKey(productDto)) {
//            productsDtoMap.put(productDto, 1);
//        }
//        return productDto;
//    }

    public void deleteFromCart(ProductDto productDto) {
        if (productsDtoMap.get(productDto) == 1) {
            productsDtoMap.remove(productDto);
            return;
        }
        int count = productsDtoMap.get(productDto);
        count--;
        productsDtoMap.put(productDto, count);
    }

    public Map<ProductDto, Integer> showCart() {
        return productsDtoMap;
    }
}
