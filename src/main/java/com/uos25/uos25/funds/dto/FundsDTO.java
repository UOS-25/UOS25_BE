package com.uos25.uos25.funds.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class FundsDTO {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class MaintenanceDecideRequest {
        private int expense;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class WithdrawalRequest {
        private int money;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class FundsCreateRequest {
        private int headPayment;
        private int maintenanceExpense;
        private int personalExpense;
        private int sales;
        private Long storeId;
    }
}
