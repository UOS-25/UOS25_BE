package com.uos25.uos25.funds.repository;

import com.uos25.uos25.funds.entity.Funds;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundsRepository extends JpaRepository<Funds, Long> {
    Optional<Funds> findByStoreId(Long id);
}
