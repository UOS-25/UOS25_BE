package com.uos25.uos25.etc.lotto.repository;

import com.uos25.uos25.etc.lotto.entity.Lotto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LottoRepository extends JpaRepository<Lotto, Long> {

    Optional<Lotto> findByStoreId(Long storeId);
}
