package com.uos25.uos25.store.repository;

import com.uos25.uos25.store.entity.Store;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByCode(String code);

    Optional<Store> findByRefreshToken(String refreshToken);
}
