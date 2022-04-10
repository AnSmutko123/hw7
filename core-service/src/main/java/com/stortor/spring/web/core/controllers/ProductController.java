package com.stortor.spring.web.core.controllers;

import com.stortor.spring.web.api.errors.CartServiceAppError;
import com.stortor.spring.web.api.exceptions.ResourceNotFoundException;
import com.stortor.spring.web.core.converters.ProductConverter;
import com.stortor.spring.web.api.core.ProductDto;
import com.stortor.spring.web.core.entity.Product;
import com.stortor.spring.web.core.servieces.ProductsService;
import com.stortor.spring.web.core.validators.ProductValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/v1/products")
@RestController
@RequiredArgsConstructor
@Tag(name = "Продукты", description = "Методы работы с продуктами")
public class ProductController {

    private final ProductsService productService;
    private final ProductValidator productValidator;

    @Operation(
            summary = "Запрос на получение страницы продуктов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Page.class))
                    )
            }
    )
    @GetMapping()
    public Page<ProductDto> getAllProducts(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "max_price", required = false) Integer maxPrice,
            @RequestParam(name = "min_price", required = false) Integer minPrice,
            @RequestParam(name = "title_part", required = false) String titlePart,
            @RequestParam(name = "title_part_category", required = false) String titlePartCategory
    ) {
        if (page < 1) {
            page = 1;
        }
        return productService.findAll(minPrice, maxPrice, titlePart, titlePartCategory, page)
                .map(p -> ProductConverter.INSTANCE.entityToDto(p));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Запрос на получение продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            description = "Ошибка пользователя", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = CartServiceAppError.class))
                    )
            }
    )
    public ProductDto findProductById(
            @PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long id
    ) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
        return ProductConverter.INSTANCE.entityToDto(product);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Запрос на удаление продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                        )
            }
    )
    public void deleteProductById(
            @PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long id
    ) {
        productService.deleteProductById(id);
    }

    @PostMapping()
    @Operation(
            summary = "Запрос на добавление нового продукта",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ProductDto addNewProduct(@RequestBody ProductDto productDto) {
        productDto.setId(null);
        productValidator.validate(productDto);
        Product product = ProductConverter.INSTANCE.dtoToEntity(productDto);
        return ProductConverter.INSTANCE.entityToDto(productService.save(product));
    }

    @PutMapping()
    @Operation(
            summary = "Запрос на обновление продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productService.update(productDto);
        return ProductConverter.INSTANCE.entityToDto(product);
    }

    @PatchMapping("/change_price")
    @Operation(
            summary = "Запрос на изменение цены продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ProductDto changePrice(
            @RequestParam @Parameter(description = "Идентификатор продукта", required = true) Long productId,
            @RequestParam @Parameter(description = "Размер изменения цены продукта", required = true) Integer delta
    ) {
        Product product = productService.changePrice(productId, delta);
        productValidator.validatePrice(product);
        return ProductConverter.INSTANCE.entityToDto(product);
    }

}
