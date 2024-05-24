package com.uos25.uos25.etc.atm.api;

import com.uos25.uos25.auth.login.entity.PrincipalDetails;
import com.uos25.uos25.etc.atm.dto.AtmDTO.DepositRequest;
import com.uos25.uos25.etc.atm.dto.AtmDTO.WithdrawRequest;
import com.uos25.uos25.etc.atm.service.AtmService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ATM", description = "ATM을 관리합니다")
@RestController
@RequestMapping("/api/v1/atm")
@Slf4j
@RequiredArgsConstructor
public class AtmController {
    private final AtmService atmService;

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody DepositRequest request,
                                     @AuthenticationPrincipal PrincipalDetails principalDetails) {
        atmService.deposit(request, principalDetails.getId());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdrawo(@RequestBody WithdrawRequest request,
                                       @AuthenticationPrincipal PrincipalDetails principalDetails) {
        atmService.withdraw(request, principalDetails.getId());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/account/{money}")
    public ResponseEntity<?> account(@PathVariable int money,
                                     @AuthenticationPrincipal PrincipalDetails principalDetails) {
        atmService.account(money, principalDetails.getId());

        return ResponseEntity.ok().build();
    }
}
