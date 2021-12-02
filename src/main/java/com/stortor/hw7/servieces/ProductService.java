package com.stortor.hw7.servieces;

import com.stortor.hw7.entity.Product;
import com.stortor.hw7.exceptions.ResourceNotFoundException;
import com.stortor.hw7.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public void addNewProduct(Product product) {
        productRepository.save(product);
    }

    @Transactional
    public void changeCost(Long productId, Integer delta) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Unable to change product's cost. Product not found, id: " + productId));
        product.setCost(product.getCost() + delta);
    }

    // Хочется узнать как обрабатывать пустой лист, в случае если он вернется в таком методе
    // Вот то что я придумала, но интересно узнать как правильно
    public List <Product> filterByMin(Integer min) {
        List<Product> products = productRepository.findProductByCostAfter(min);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Products more than %d not found", min));
        } else {
            return products;
        }
    }

    public List<Product> filterByMax(Integer max) {
        List<Product> products = productRepository.findProductByCostBefore(max);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Products less than %d not found", max));
        } else {
            return products;
        }
    }

    public List<Product> filterCostBetween(Integer min, Integer max) {
        return productRepository.findAllByCostBetween(min, max);
    }
}
