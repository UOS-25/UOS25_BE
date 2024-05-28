package com.uos25.uos25.etc.atm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class AtmDTO {

    @Getter
    @AllArgsConstructor
    public static class DepositRequest {
        private int money;
        private String accountHolder;
        private String accountNumber;
        private String password;
    }


    @Getter
    @AllArgsConstructor
    public static class WithdrawRequest {
        private int money;
        private String password;
    }
}
