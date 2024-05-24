package com.uos25.uos25.etc.atm.service;

import com.uos25.uos25.etc.atm.dto.AtmDTO.DepositRequest;
import com.uos25.uos25.etc.atm.dto.AtmDTO.WithdrawRequest;
import com.uos25.uos25.etc.atm.entity.Atm;
import com.uos25.uos25.etc.atm.entity.WorkType;
import com.uos25.uos25.etc.atm.repository.AtmRepository;
import com.uos25.uos25.store.entity.Store;
import com.uos25.uos25.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AtmService {
    private final AtmRepository atmRepository;
    private final StoreService storeService;

    //입금
    public void deposit(DepositRequest request, Long storeId) {
        Store store = storeService.getStoreById(storeId);

        Atm atm = Atm.builder()
                .accountHolder(request.getAccountHolder())
                .money(request.getMoney())
                .accountNumber(request.getAccountNumber())
                .store(store)
                .workType(WorkType.DEPOSIT)
                .build();

        atmRepository.save(atm);
    }

    //출금
    public void withdraw(WithdrawRequest request, Long storeId) {
        Store store = storeService.getStoreById(storeId);

        Atm atm = Atm.builder()
                .money(request.getMoney())
                .store(store)
                .workType(WorkType.WITHDRAW)
                .build();

        atmRepository.save(atm);
    }

    //예금
    public void account(int money, Long storeId) {
        Store store = storeService.getStoreById(storeId);

        Atm atm = Atm.builder()
                .money(money)
                .store(store)
                .workType(WorkType.ACCOUNT)
                .build();

        atmRepository.save(atm);
    }
}
