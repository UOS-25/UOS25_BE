package com.uos25.uos25.orders.repository;

import com.uos25.uos25.orders.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long>  {
    List<Orders> findByOrderNumber(String orderNumber);
    List<Orders> findByOrderDate(LocalDate orderDate);
    @Query("SELECT o FROM Orders o WHERE o.product.productCode = :productCode")
    List<Orders> findByProductCode(@Param("productCode") String productCode);

    @Query("SELECT o FROM Orders o WHERE o.store.id = :storeId")
    List<Orders> findByStoreId(Long storeId);

    List<Orders> findByConfirm(boolean confirm);

    Orders findByOrderNumberAndProductProductCodeAndStoreIdAndOrderDate(String orderNumber, String productCode, Long storeId, LocalDate orederDate);

}
