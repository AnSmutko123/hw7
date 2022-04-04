package com.stortor.spring.web.core.proxy;

import com.stortor.spring.web.api.exceptions.ResourceNotFoundException;
import com.stortor.spring.web.core.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CachedProductRepository extends ProductRepositoryAdapter {

    List<Optional<Product>> productsList = new ArrayList();

    @Override
    public Optional<Product> findById(Long id) {
        Optional<Product> product = productsList.stream().filter(p -> p.get().getId() == id).findFirst().orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
        if (product.isPresent()) {
            return product;
        }
        Optional<Product> productFromDB = super.findById(id);
        productsList.add(productFromDB);
        return productFromDB;
    }
}
