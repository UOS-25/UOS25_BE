package com.uos25.uos25.sales.repository;


import com.uos25.uos25.orders.entity.Orders;
import com.uos25.uos25.sales.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Long> {
    @Query("SELECT o FROM Orders o WHERE o.store.id = :storeId")
    List<Sales> findByStoreId(Long storeId);

    List<Sales> findBySalesDateAndStoreId(LocalDate salesDate, Long storeId);
}
