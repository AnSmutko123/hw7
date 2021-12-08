package com.stortor.hw7.repositories;

import com.stortor.hw7.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public List <Product> findAllByCostBetween(Integer min, Integer max);
    public List <Product> findAllByIdBetween(Long min, Long max);

}
