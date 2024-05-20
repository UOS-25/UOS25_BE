package com.uos25.uos25.etc.parcel.service;

import com.uos25.uos25.common.error.etc.ParcelNotFoundException;
import com.uos25.uos25.etc.parcel.dto.ParcelDTO.ParcelInfoResponse;
import com.uos25.uos25.etc.parcel.dto.ParcelDTO.ParcelInfoResponses;
import com.uos25.uos25.etc.parcel.dto.ParcelDTO.ParcelRegistryRequest;
import com.uos25.uos25.etc.parcel.entity.Parcel;
import com.uos25.uos25.etc.parcel.entity.ParcelState;
import com.uos25.uos25.etc.parcel.repository.ParcelRepository;
import com.uos25.uos25.funds.service.FundsService;
import com.uos25.uos25.store.entity.Store;
import com.uos25.uos25.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ParcelService {
    private final ParcelRepository parcelRepository;
    private final FundsService fundsService;
    private final StoreService storeService;

    public void registryParcel(ParcelRegistryRequest request, Long storeId) {
        Store store = storeService.getStoreById(storeId);

        Parcel parcel = Parcel.builder()
                .fromAddress(request.getFromAddress())
                .toAddress(request.getToAddress())
                .fromPhoneNumber(request.getFromPhoneNumber())
                .toPhoneNumber(request.getToPhoneNumber())
                .weight(request.getWeight())
                .goods(request.getGoods())
                .store(store)
                .build();
        parcelRepository.save(parcel);

        fundsService.plusTotalFunds(2000 + request.getWeight() * 100, storeId); //택배비 =  2000 + 무게 * 100
    }

    public ParcelInfoResponses getAllParcelInfo(Long storeId) {
        Store store = storeService.getStoreById(storeId);

        return ParcelInfoResponses.toDTO(parcelRepository.findAllByStoreId(storeId));
    }

    public ParcelInfoResponse getParcelInfo(Long parcelId) {
        Parcel parcel = parcelRepository.findById(parcelId).orElseThrow(ParcelNotFoundException::new);

        return ParcelInfoResponse.toDTO(parcel);
    }

    public void updateState(Long parcelId, ParcelState state) {
        Parcel parcel = parcelRepository.findById(parcelId).orElseThrow(ParcelNotFoundException::new);

        parcel.updateState(state);

        parcelRepository.save(parcel);
    }
}
