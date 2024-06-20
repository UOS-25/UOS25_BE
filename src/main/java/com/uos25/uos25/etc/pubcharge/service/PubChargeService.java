package com.uos25.uos25.etc.pubcharge.service;

import com.uos25.uos25.etc.pubcharge.dto.PubChargeDTO.PubChargePayRequest;
import com.uos25.uos25.etc.pubcharge.entity.PubCharge;
import com.uos25.uos25.etc.pubcharge.repository.PubChargeRepository;
import com.uos25.uos25.store.entity.Store;
import com.uos25.uos25.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PubChargeService {
    private final PubChargeRepository pubChargeRepository;
    private final StoreService storeService;

    public void payPubCharge(PubChargePayRequest request, Long storeId) {
        Store store = storeService.getStoreById(storeId);

        PubCharge pubCharge = PubCharge.builder()
                .money(request.getMoney())
                .accountNumber(request.getAccountNumber())
                .pubChargeType(request.getPubChargeType())
                .bank(request.getBank())
                .store(store)
                .build();

        pubChargeRepository.save(pubCharge);
    }

}
