package com.stortor.spring.web.cart;

import com.stortor.spring.web.api.core.ProductDto;
import com.stortor.spring.web.cart.services.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@SpringBootTest
public class CartTest {
    @Autowired
    private CartService cartService;

    @MockBean
    private RestTemplate restTemplate;

    private static ProductDto productDtoMilk;
    private static ProductDto productDtoBread;
    private static ProductDto productDtoApples;

    @BeforeAll
    public static void initProducts() {
        productDtoMilk = new ProductDto();
        productDtoMilk.setId(2L);
        productDtoMilk.setTitle("Milk");
        productDtoMilk.setPrice(BigDecimal.valueOf(100));
        productDtoMilk.setCategory("X");

        productDtoBread = new ProductDto();
        productDtoBread.setId(4L);
        productDtoBread.setTitle("Bread");
        productDtoBread.setPrice(BigDecimal.valueOf(150));
        productDtoBread.setCategory("Y");

        productDtoApples = new ProductDto();
        productDtoApples.setId(5L);
        productDtoApples.setTitle("Apples");
        productDtoApples.setPrice(BigDecimal.valueOf(200));
        productDtoApples.setCategory("Y");
    }

    @BeforeEach
    public void initCart() {
        cartService.clearCart("test_cart");
    }

    @Test
    public void addToCartTest() {
        Mockito.doReturn(productDtoMilk).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoMilk.getId(), ProductDto.class);
        cartService.addToCart("test_cart", productDtoMilk.getId());
        cartService.addToCart("test_cart", productDtoMilk.getId());
        cartService.addToCart("test_cart", productDtoMilk.getId());
        cartService.addToCart("test_cart", productDtoMilk.getId());
        Assertions.assertEquals(1, cartService.getCurrentCart("test_cart").getItems().size());
        Mockito.doReturn(productDtoBread).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoBread.getId(), ProductDto.class);
        cartService.addToCart("test_cart", productDtoBread.getId());
        Assertions.assertEquals(2, cartService.getCurrentCart("test_cart").getItems().size());

        int totalPrice = cartService.getCurrentCart("test_cart").getItems().stream().mapToInt(p -> p.getPrice().intValue()).sum();
        Assertions.assertEquals(totalPrice, cartService.getCurrentCart("test_cart").getTotalPrice());
    }

    @Test
    public void decrementItemFromCartTest() {
        Mockito.doReturn(productDtoMilk).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoMilk.getId(), ProductDto.class);
        cartService.addToCart("test_cart", productDtoMilk.getId());
        cartService.addToCart("test_cart", productDtoMilk.getId());
        Assertions.assertEquals(2, cartService.getCurrentCart("test_cart").getItems().stream().map(p -> p.getQuantity()).findFirst().get());
        cartService.decrementItem("test_cart", productDtoMilk.getId());
        Assertions.assertEquals(1, cartService.getCurrentCart("test_cart").getItems().stream().map(p -> p.getQuantity()).findFirst().get());
        cartService.decrementItem("test_cart", productDtoMilk.getId());
        Assertions.assertTrue(cartService.getCurrentCart("test_cart").getItems().isEmpty());

        int totalPrice = cartService.getCurrentCart("test_cart").getItems().stream().mapToInt(p -> p.getPrice().intValue()).sum();
        Assertions.assertEquals(totalPrice, cartService.getCurrentCart("test_cart").getTotalPrice());
    }

    @Test
    public void clearCartTest() {
        Mockito.doReturn(productDtoMilk).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoMilk.getId(), ProductDto.class);
        cartService.addToCart("test_cart", productDtoMilk.getId());
        cartService.addToCart("test_cart", productDtoMilk.getId());
        Mockito.doReturn(productDtoBread).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoBread.getId(), ProductDto.class);
        cartService.addToCart("test_cart", productDtoBread.getId());
        cartService.clearCart("test_cart");
        Assertions.assertTrue(cartService.getCurrentCart("test_cart").getItems().isEmpty());
        Assertions.assertEquals(0, cartService.getCurrentCart("test_cart").getTotalPrice());
    }

    @Test
    public void mergeCartTest() {
        cartService.getCurrentCart("user_cart");

        Mockito.doReturn(productDtoMilk).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoMilk.getId(), ProductDto.class);
        cartService.addToCart("test_cart", productDtoMilk.getId());
        cartService.addToCart("test_cart", productDtoMilk.getId());
        Mockito.doReturn(productDtoBread).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoBread.getId(), ProductDto.class);
        cartService.addToCart("test_cart", productDtoBread.getId());

        Mockito.doReturn(productDtoApples).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoApples.getId(), ProductDto.class);
        cartService.addToCart("user_cart", productDtoApples.getId());

        cartService.merge("user_cart", "test_cart");
        Assertions.assertEquals(3, cartService.getCurrentCart("user_cart").getItems().size());
        int totalPrice = cartService.getCurrentCart("user_cart").getItems().stream().mapToInt(p -> p.getPrice().intValue()).sum();
        Assertions.assertEquals(totalPrice, cartService.getCurrentCart("user_cart").getTotalPrice());
    }

    @Test
    public void removeCartTest() {
        Mockito.doReturn(productDtoMilk).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoMilk.getId(), ProductDto.class);
        cartService.addToCart("test_cart", productDtoMilk.getId());
        cartService.addToCart("test_cart", productDtoMilk.getId());
        cartService.removeItemFromCart("test_cart", productDtoMilk.getId());
        Assertions.assertTrue(cartService.getCurrentCart("test_cart").getItems().isEmpty());
        Assertions.assertEquals(0, cartService.getCurrentCart("test_cart").getTotalPrice());
    }


}
