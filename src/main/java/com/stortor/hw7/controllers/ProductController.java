package com.stortor.hw7.controllers;

import com.stortor.hw7.converters.ProductConverter;
import com.stortor.hw7.dto.ProductDto;
import com.stortor.hw7.entity.Product;
import com.stortor.hw7.exceptions.ResourceNotFoundException;
import com.stortor.hw7.servieces.ProductService;
import com.stortor.hw7.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;

    @GetMapping()
    public Page<ProductDto> getAllProducts(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "max_cost", required = false) Integer maxCost,
            @RequestParam(name = "min_cost", required = false) Integer minCost,
            @RequestParam(name = "title_part", required = false) String titlePart
    ) {
        if (page < 1) {
            page = 1;
        }
        return productService.findAll(minCost, maxCost, titlePart, page)
                .map(p -> productConverter.entityToDto(p));
    }

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        Product product = productService.findProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
        return productConverter.entityToDto(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

    @PostMapping()
    public ProductDto addNewProduct(@RequestBody ProductDto productDto) {
        productDto.setId(null);
        productValidator.validate(productDto);
        Product product = productConverter.dtoToEntity(productDto);
        return productConverter.entityToDto(productService.save(product));
    }

    @PutMapping()
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productService.update(productDto);
        return productConverter.entityToDto(product);
    }

    @PatchMapping("/change_cost")
    public ProductDto changeCost(@RequestParam Long productId, @RequestParam Integer delta) {
        Product product = productService.changeCost(productId, delta);
        productValidator.validateCost(product);
        return productConverter.entityToDto(product);
    }

}
