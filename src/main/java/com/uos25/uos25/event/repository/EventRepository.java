package com.uos25.uos25.event.repository;

import com.uos25.uos25.event.entity.Event;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByStoreId(Long storeID);

    Optional<Event> findByStoreId(Long storeID);

    Optional<Event> findByIdAndStoreId(Long eventId, Long storeID);

    Optional<Event> findByProductsProductCode(String code);
}
