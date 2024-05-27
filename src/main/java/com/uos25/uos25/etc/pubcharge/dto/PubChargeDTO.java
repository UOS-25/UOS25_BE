package com.uos25.uos25.etc.pubcharge.dto;

import com.uos25.uos25.etc.pubcharge.entity.PubChargeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PubChargeDTO {

    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    @Builder
    public static class PubChargePayRequest {
        private String accountNumber;
        private int money;
        private PubChargeType pubChargeType;
    }
}
