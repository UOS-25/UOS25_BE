package com.uos25.uos25.auth.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class SignUpDTO {

    @AllArgsConstructor
    @Getter
    @Builder
    public static class SignUpRequest {
        private String code;
        private String location;
    }
}
