package com.uos25.uos25.funds.service;

import com.uos25.uos25.common.error.funds.FundsNotFoundException;
import com.uos25.uos25.funds.dto.FundsDTO;
import com.uos25.uos25.funds.entity.Funds;
import com.uos25.uos25.funds.repository.FundsRepository;
import com.uos25.uos25.store.entity.Store;
import com.uos25.uos25.store.repository.StoreRepository;
import com.uos25.uos25.store.service.StoreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FundsService {
    private final FundsRepository fundsRepository;
    private final StoreService storeService;
    private final StoreRepository storeRepository;

    @Transactional //출금
    public void withdrawal(int money, Long storeId) {
        Funds funds = fundsRepository.findByStoreId(storeId).orElseThrow(
                FundsNotFoundException::new
        );

        funds.withdrawal(money);

        fundsRepository.save(funds);
    }

    @Transactional //유지비 결정
    public void decideMaintenance(int expense, Long storeId) {
        Funds funds = fundsRepository.findByStoreId(storeId).orElseThrow(
                FundsNotFoundException::new
        );

        funds.decideMaintenanceExpense(expense);

        fundsRepository.save(funds);
    }

    @Transactional //판매
    public void sales(int money, Long storeId) {
        Funds funds = fundsRepository.findByStoreId(storeId).orElseThrow(
                FundsNotFoundException::new
        );

        funds.updateSales(money);

        fundsRepository.save(funds);
    }

    @Transactional // 총 자금 추가
    public void plusTotalFunds(int money, Long storeId) {
        Funds funds = fundsRepository.findByStoreId(storeId).orElseThrow(
                FundsNotFoundException::new
        );

        funds.plusTotalFunds(money);

        fundsRepository.save(funds);
    }

    @Transactional
    public Funds createFunds(FundsDTO.FundsCreateRequest request, Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid store ID"));

        Funds funds = Funds.builder()
                .headPayment(request.getHeadPayment())
                .maintenanceExpense(request.getMaintenanceExpense())
                .personalExpense(request.getPersonalExpense())
                .sales(request.getSales())
                .store(store)
                .build();

        return fundsRepository.save(funds);
    }

}
