package com.stortor.spring.web.cart.controllers;

import com.stortor.spring.web.api.dto.CartDto;
import com.stortor.spring.web.api.dto.StringResponse;
import com.stortor.spring.web.cart.dto.Cart;
import com.stortor.spring.web.cart.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/cart")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{uuid}")
    public CartDto getCart(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        Cart cart = cartService.getCurrentCart(getCurrentCartUuid(username, uuid));
        return new CartDto(cart.getItems(), cart.getTotalPrice());
    }

    @GetMapping("/generate")
    public StringResponse getCart() {
        return new StringResponse(cartService.generateCartUuid());
    }

    @GetMapping("/{uuid}/add/{productId}")
    public ResponseEntity<?> add(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.addToCart(getCurrentCartUuid(username, uuid), productId);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }

    @GetMapping("/{uuid}/decrement/{productId}")
    public ResponseEntity<?> decrement(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.decrementItem(getCurrentCartUuid(username, uuid), productId);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }

    @GetMapping("/{uuid}/remove/{productId}")
    public ResponseEntity<?> remove(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.removeItemFromCart(getCurrentCartUuid(username, uuid), productId);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }

    @GetMapping("/{uuid}/clear")
    public ResponseEntity<?> clear(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        cartService.clearCart(getCurrentCartUuid(username, uuid));
        return (ResponseEntity<?>) ResponseEntity.ok();
    }

    @GetMapping("/{uuid}/merge")
    public ResponseEntity<?> merge(@RequestHeader String username, @PathVariable String uuid) {
        cartService.merge(
                getCurrentCartUuid(username, null),
                getCurrentCartUuid(null, uuid)
        );
        return (ResponseEntity<?>) ResponseEntity.ok();
    }

    @GetMapping
    public String getCartUuidFromSuffix(String suffix) {
        return cartService.getCartUuidFromSuffix(suffix);
    }

    private String getCurrentCartUuid(String username, String uuid) {
        if (username != null) {
            return cartService.getCartUuidFromSuffix(username);
        }
        return cartService.getCartUuidFromSuffix(uuid);
    }

}
