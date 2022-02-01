package com.stortor.spring.web.core.servieces;

import com.stortor.spring.web.api.exceptions.ResourceNotFoundException;
import com.stortor.spring.web.core.converters.ProductConverter;
import com.stortor.spring.web.api.dto.ProductDto;
import com.stortor.spring.web.core.entity.Product;
import com.stortor.spring.web.core.repositories.ProductRepository;
import com.stortor.spring.web.core.repositories.specification.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductConverter converter;
    private final ProductRepository productRepository;

    public Page<Product> findAll(Integer minPrice, Integer maxPrice, String titlePart, String titlePartCategory, Integer page) {
        Specification<Product> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThen(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpecifications.priceLessOrEqualsThen(maxPrice));
        }
        if (titlePart != null) {
            spec = spec.and(ProductSpecifications.titleLike(titlePart));
        }
        if (titlePartCategory != null) {
            spec = spec.and(ProductSpecifications.titleLikeCategory(titlePartCategory));
        }
        return productRepository.findAll(spec, PageRequest.of(page - 1, 8));
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public ProductDto findProductDtoById(Long id) {
        return converter.entityToDto(findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id)));
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(Long productId, Integer delta) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Unable to change product's cost. Product not found, id: " + productId));
        product.setPrice(product.getPrice() + delta);
        return product;
    }

    @Transactional
    public Product update(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException(String.format("Unable to update product. Product id = %d not found", productDto.getId())));
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        return product;
    }

}
