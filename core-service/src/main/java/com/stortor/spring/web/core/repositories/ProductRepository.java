package com.stortor.spring.web.core.repositories;

import com.stortor.spring.web.core.entity.Product;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Cacheable(cacheNames="id")
    Optional<Product> findById(Long id);


}
