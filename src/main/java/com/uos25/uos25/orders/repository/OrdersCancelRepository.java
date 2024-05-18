package com.uos25.uos25.orders.repository;

import com.uos25.uos25.orders.entity.Orders;
import com.uos25.uos25.orders.entity.OrdersCancel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersCancelRepository extends JpaRepository<OrdersCancel, Long> {
}
