package com.stortor.spring.web.cart.controllers;

import com.stortor.spring.web.api.carts.CartDto;
import com.stortor.spring.web.api.dto.StringResponse;
import com.stortor.spring.web.api.errors.CartServiceAppError;
import com.stortor.spring.web.api.exceptions.ResourceNotFoundException;
import com.stortor.spring.web.api.exceptions.ServerNotWorkingException;
import com.stortor.spring.web.cart.converterts.CartConverter;
import com.stortor.spring.web.cart.model.Cart;
import com.stortor.spring.web.cart.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/cart")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/{uuid}")
    @Operation(
            summary = "Запрос на получение корзины пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CartDto.class))
                    )
            }
    )
    public CartDto getCart(
            @RequestHeader (required = false) @Parameter(description = "Имя пользователя", required = false)  String username,
            @PathVariable @Parameter(description = "Uuid корзины", required = true) String uuid) {
        Cart cart = cartService.getCurrentCart(getCurrentCartUuid(username, uuid));
        return cartConverter.modelToDto(cart);
    }

    @GetMapping("/generate")
    @Operation(
            summary = "Запрос на создание корзины пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    )
            }
    )
    public StringResponse getCart() {
        return new StringResponse(cartService.generateCartUuid());
    }

    @GetMapping("/{uuid}/add/{productId}")
    @Operation(
            summary = "Запрос на добавление продукта в корзину пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Продукт не был найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class))
                    ),
                    @ApiResponse(
                            description = "Сервис Продуктов не работает", responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ServerNotWorkingException.class))
                    )
            }
    )
    public void add(@RequestHeader(required = false) @Parameter(description = "Имя пользователя", required = false) String username,
                    @PathVariable @Parameter(description = "ID продукта", required = true) String uuid, @PathVariable Long productId) {
        cartService.addToCart(getCurrentCartUuid(username, uuid), productId);
    }

    @GetMapping("/{uuid}/decrement/{productId}")
    @Operation(
            summary = "Запрос на уменьшение количества продукта в корзине пользователя на 1",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    public void decrement(@RequestHeader(required = false) @Parameter(description = "Имя пользователя", required = false) String username,
                          @PathVariable @Parameter(description = "Uuid корзины", required = true) String uuid,
                          @PathVariable @Parameter(description = "ID продукта", required = true) Long productId) {
        cartService.decrementItem(getCurrentCartUuid(username, uuid), productId);
    }

    @GetMapping("/{uuid}/remove/{productId}")
    @Operation(
            summary = "Запрос на удаление продукта в корзине",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    public void remove(@RequestHeader(required = false) @Parameter(description = "Имя пользователя", required = false) String username,
                       @PathVariable @Parameter(description = "Uuid корзины", required = true) String uuid,
                       @PathVariable @Parameter(description = "ID продукта", required = true) Long productId) {
        cartService.removeItemFromCart(getCurrentCartUuid(username, uuid), productId);
    }

    @GetMapping("/{uuid}/clear")
    @Operation(
            summary = "Запрос на очистку корзины",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    public void clear(@RequestHeader(required = false) @Parameter(description = "Имя пользователя", required = false) String username,
                      @PathVariable @Parameter(description = "Uuid корзины", required = true) String uuid)
    {
        cartService.clearCart(getCurrentCartUuid(username, uuid));
    }

    @GetMapping("/{uuid}/merge")
    @Operation(
            summary = "Запрос на объединение корзины гостя и корзины пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    public void merge(@RequestHeader(required = false) @Parameter(description = "Имя пользователя", required = false) String username,
                      @PathVariable @Parameter(description = "Uuid корзины", required = true) String uuid) {
        cartService.merge(
                getCurrentCartUuid(username, null),
                getCurrentCartUuid(null, uuid)
        );
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
