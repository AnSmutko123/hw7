package com.stortor.hw7.servieces;

import com.stortor.hw7.converters.ProductConverter;
import com.stortor.hw7.dto.ProductDto;
import com.stortor.hw7.entity.Product;
import com.stortor.hw7.exceptions.ResourceNotFoundException;
import com.stortor.hw7.repositories.ProductRepository;
import com.stortor.hw7.repositories.specification.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public static final Function<Product, com.stortor.hw7.soap.Product> functionEntityToSoap = se -> {
        com.stortor.hw7.soap.Product p = new com.stortor.hw7.soap.Product();
        p.setId(se.getId());
        p.setTitle(se.getTitle());
        p.setPrice(se.getPrice());
        p.setCategoryTitle(se.getCategory().getTitle());
        return p;
    };

    public List<com.stortor.hw7.soap.Product> findAll() {
        return productRepository.findAll().stream().map(functionEntityToSoap).collect(Collectors.toList());
    }

    public com.stortor.hw7.soap.Product findByTitle(String title) {
        return productRepository.findByTitle(title).map(functionEntityToSoap).get();
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
