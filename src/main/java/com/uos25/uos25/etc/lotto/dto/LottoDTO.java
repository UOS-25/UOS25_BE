package com.uos25.uos25.etc.lotto.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

public class LottoDTO {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class LottoAssignRequest {
        private int price;
        private String winningNumber;
    }

    @Jacksonized
    @Getter
    @Builder
    @AllArgsConstructor
    public static class LottoBuyRequest {
        private int amount;
    }

    @Jacksonized
    @Getter
    @Builder
    @AllArgsConstructor
    public static class LottoBuyResponse {
        private List<LottoResultResponse> results;
    }

    @Jacksonized
    @Getter
    @Builder
    @AllArgsConstructor
    public static class LottoResultResponse {
        private String numbers;
        private LottoCheckResponse result;
    }


    @Jacksonized
    @Builder
    @Getter
    @AllArgsConstructor
    public static class LottoCheckRequest {
        private String numbers;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class LottoCheckResponse {

        private int count;
        private String rank;
    }

}
