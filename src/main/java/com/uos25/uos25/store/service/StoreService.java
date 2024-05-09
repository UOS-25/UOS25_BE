package com.uos25.uos25.store.service;

import com.uos25.uos25.common.error.store.StoreNotFoundException;
import com.uos25.uos25.store.entity.Store;
import com.uos25.uos25.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {
    private final StoreRepository storeRepository;

    public Store getStoreById(Long id) {
        return storeRepository.findById(id).orElseThrow(
                () -> new StoreNotFoundException()
        );
    }
}
