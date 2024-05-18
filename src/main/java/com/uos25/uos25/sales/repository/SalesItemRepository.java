package com.uos25.uos25.sales.repository;

import com.uos25.uos25.sales.entity.SalesItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesItemRepository extends JpaRepository<SalesItem, Long> {
}
