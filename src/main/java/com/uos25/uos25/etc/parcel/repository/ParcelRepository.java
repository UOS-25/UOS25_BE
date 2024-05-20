package com.uos25.uos25.etc.parcel.repository;

import com.uos25.uos25.etc.parcel.entity.Parcel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {

    List<Parcel> findAllByStoreId(Long storeId);
}
