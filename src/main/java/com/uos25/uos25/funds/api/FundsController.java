package com.uos25.uos25.funds.api;

import com.uos25.uos25.auth.login.entity.PrincipalDetails;
import com.uos25.uos25.funds.dto.FundsDTO;
import com.uos25.uos25.funds.dto.FundsDTO.FundsInfoResponse;
import com.uos25.uos25.funds.dto.FundsDTO.MaintenanceDecideRequest;
import com.uos25.uos25.funds.service.FundsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Funds", description = "Funds API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/funds")
public class FundsController {

    private final FundsService fundsService;

    @Operation(summary = "자금 조회", description = "자금을 조회합니다")
    @GetMapping
    public ResponseEntity<?> getFundsInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        FundsInfoResponse response = fundsService.getFundsInfo(principalDetails.getId());

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "유지금 결정")
    @PostMapping("/maintenance")
    public ResponseEntity<?> maintenanceDecide(MaintenanceDecideRequest request,
                                               @AuthenticationPrincipal PrincipalDetails principalDetails) {
        fundsService.decideMaintenance(request.getExpense(), principalDetails.getId());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createFunds(@RequestBody FundsDTO.FundsCreateRequest request,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails) {
        fundsService.createFunds(request, principalDetails.getId());
        return ResponseEntity.ok().build();
    }
}
