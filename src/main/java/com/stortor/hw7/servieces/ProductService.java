package com.stortor.hw7.servieces;

import com.stortor.hw7.converters.ProductConverter;
import com.stortor.hw7.dto.ProductDto;
import com.stortor.hw7.entity.Product;
import com.stortor.hw7.exceptions.ResourceNotFoundException;
import com.stortor.hw7.repositories.ProductRepository;
import com.stortor.hw7.repositories.specification.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter converter;

    public Page<Product> findAll(Integer minCost, Integer maxCost, String titlePart, Integer page) {
        Specification<Product> spec = Specification.where(null);
        if (minCost != null) {
            spec = spec.and(ProductSpecifications.costGreaterOrEqualsThen(minCost));
        }
        if (maxCost != null) {
            spec = spec.and(ProductSpecifications.costLessOrEqualsThen(maxCost));
        }
        if (titlePart != null) {
            spec = spec.and(ProductSpecifications.titleLike(titlePart));
        }
        return productRepository.findAll(spec, PageRequest.of(page - 1, 5));
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public Optional<Product>  findProductById(Long id) {
        return productRepository.findById(id);
    }

    public ProductDto findProductDtoById(Long id) {
        return converter.entityToDto(findProductById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id)));
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product changeCost(Long productId, Integer delta) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Unable to change product's cost. Product not found, id: " + productId));
        product.setCost(product.getCost() + delta);
        return product;
    }

    @Transactional
    public Product update(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException(String.format("Unable to update product. Product id = %d not found", productDto.getId())));
        product.setCost(productDto.getCost());
        product.setTitle(productDto.getTitle());
        return product;
    }

}
