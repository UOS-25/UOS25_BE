package com.uos25.uos25.funds.service;

import com.uos25.uos25.common.error.funds.FundsNotFoundException;
import com.uos25.uos25.funds.dto.FundsDTO.MaintenanceDecideRequest;
import com.uos25.uos25.funds.dto.FundsDTO.WithdrawalRequest;
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

    @Transactional
    public void withdrawal(WithdrawalRequest request, Long storeId) {
        int money = request.getMoney();

        Funds funds = fundsRepository.findByStoreId(storeId).orElseThrow(
                FundsNotFoundException::new
        );

        funds.withdrawal(money);

        fundsRepository.save(funds);
    }

    @Transactional //유지비 결정
    public void decideMaintenance(MaintenanceDecideRequest request, Long storeId) {
        int expense = request.getExpense();

        Funds funds = fundsRepository.findByStoreId(storeId).orElseThrow(
                FundsNotFoundException::new
        );

        funds.decideMaintenanceExpense(expense);

        fundsRepository.save(funds);
    }

}
