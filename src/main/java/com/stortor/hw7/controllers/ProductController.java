package com.stortor.hw7.controllers;

import com.stortor.hw7.entity.Product;
import com.stortor.hw7.exceptions.ResourceNotFoundException;
import com.stortor.hw7.servieces.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/products/delete/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

    @GetMapping("/products/{id}")
    public Product findProductById(@PathVariable Long id) {
        return productService.findProductById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
    }

    @PostMapping("/products")
    public void addNewProduct(@RequestBody Product product) {
        productService.addNewProduct(product);
    }

    @GetMapping("/products/change_cost")
    public void changeCost(@RequestParam Long productId, @RequestParam Integer delta) {
        productService.changeCost(productId, delta);
    }

    @GetMapping("/products/change_page")
    public List<Product> changePage(@RequestParam(defaultValue = "1") Long min, @RequestParam(defaultValue = "10") Long max) {
        return productService.changePage(min, max);
    }


    @GetMapping("/products/between_cost")
    public List<Product> filterCostBetween(@RequestParam(required = false, defaultValue = "0") Integer min, @RequestParam(required = false, defaultValue = "100000") Integer max) {
        return productService.filterCostBetween(min, max);
    }

}
