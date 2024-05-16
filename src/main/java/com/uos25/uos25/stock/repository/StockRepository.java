package com.uos25.uos25.stock.repository;

import com.uos25.uos25.stock.entity.Stocks;
import com.uos25.uos25.stock.entity.StoreProductId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stocks, StoreProductId> {
    List<Stocks> findByStoreId(Long storeId);
}
