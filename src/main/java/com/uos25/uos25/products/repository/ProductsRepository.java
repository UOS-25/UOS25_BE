package com.uos25.uos25.products.repository;

import com.uos25.uos25.products.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Products, Long> {
    Optional<Products> findByProductCode(String productCode);
    Optional<Products> findByProductName(String productName);

    Boolean existsByProductCode(String productCode);
    Boolean existsByProductName(String productName);
}
