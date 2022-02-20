package com.stortor.spring.web.cart.exception;

public class CartIsBrokenException extends RuntimeException{
    public CartIsBrokenException(String message) {
        super(message);
    }
}
