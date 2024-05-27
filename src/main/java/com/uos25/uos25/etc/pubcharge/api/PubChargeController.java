package com.uos25.uos25.etc.pubcharge.api;

import com.uos25.uos25.auth.login.entity.PrincipalDetails;
import com.uos25.uos25.etc.pubcharge.dto.PubChargeDTO.PubChargePayRequest;
import com.uos25.uos25.etc.pubcharge.service.PubChargeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "공공 요금 수납", description = "공공 요금 수납")
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/pubCharge")
public class PubChargeController {
    private final PubChargeService pubChargeService;

    @Operation(summary = "공공 요금 수납", description = "공공 요금을 수납 합니다")
    @PostMapping
    public ResponseEntity<?> payPubCharge(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                          @RequestBody PubChargePayRequest request) {
        pubChargeService.payPubCharge(request, principalDetails.getId());

        return ResponseEntity.ok().build();
    }
}
