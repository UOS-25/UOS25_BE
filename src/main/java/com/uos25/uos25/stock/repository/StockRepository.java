package com.uos25.uos25.stock.repository;

import com.uos25.uos25.stock.entity.Stocks;
import com.uos25.uos25.stock.entity.StoreProductId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stocks, StoreProductId> {
    List<Stocks> findByStoreId(Long storeId);
    Optional<Stocks> findByStoreIdAndProductsProductCode(Long storeId, String productCode);
}
