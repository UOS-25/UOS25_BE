package com.uos25.uos25.funds.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

public class FundsDTO {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class FundsInfoResponse {
        private int totalFunds;
        private int headPayment; //본사 납입급
        private int maintenanceExpense; //유지비
        private int personalExpense; // 인건비
        private int sales; //매출
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Jacksonized
    public static class MaintenanceDecideRequest {
        private int expense;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Jacksonized
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
