package com.uos25.uos25.products.repository;

import com.uos25.uos25.products.entity.Products;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, String> {
    
    Optional<Products> findByProductCode(String productCode);

    Optional<Products> findByProductName(String productName);



    Boolean existsByProductCode(String productCode);

    Boolean existsByProductName(String productName);
}
