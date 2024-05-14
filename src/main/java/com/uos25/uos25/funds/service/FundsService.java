package com.uos25.uos25.funds.service;

import com.uos25.uos25.common.error.funds.FundsNotFoundException;
import com.uos25.uos25.funds.entity.Funds;
import com.uos25.uos25.funds.repository.FundsRepository;
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

    @Transactional //유지비 결정
    public void sales(int money, Long storeId) {
        Funds funds = fundsRepository.findByStoreId(storeId).orElseThrow(
                FundsNotFoundException::new
        );

        funds.updateSales(money);

        fundsRepository.save(funds);
    }

}
