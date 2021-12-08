package com.stortor.hw7.controllers;

import com.stortor.hw7.dto.ProductDto;
import com.stortor.hw7.entity.Product;
import com.stortor.hw7.exceptions.ResourceNotFoundException;
import com.stortor.hw7.servieces.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/products")
@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

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
                .map(p -> new ProductDto(p));
    }

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        return productService
                .findProductById(id).map(p -> new ProductDto(p))
                .orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

    @PostMapping()
    public ProductDto addNewProduct(@RequestBody ProductDto productDto) {
        productDto.setId(null);
        return new ProductDto(productService.addNewProduct(new Product(productDto)));
    }

    @PutMapping()
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        return new ProductDto(productService.save(new Product(productDto)));
    }

    @PatchMapping("/change_cost")
    public void changeCost(@RequestParam Long productId, @RequestParam Integer delta) {
        productService.changeCost(productId, delta);
    }

}
