package com.uos25.uos25.etc.parcel.api;

import com.uos25.uos25.auth.login.entity.PrincipalDetails;
import com.uos25.uos25.etc.parcel.dto.ParcelDTO.ParcelInfoResponse;
import com.uos25.uos25.etc.parcel.dto.ParcelDTO.ParcelInfoResponses;
import com.uos25.uos25.etc.parcel.dto.ParcelDTO.ParcelRegistryRequest;
import com.uos25.uos25.etc.parcel.dto.ParcelDTO.ParcelStateRequest;
import com.uos25.uos25.etc.parcel.service.ParcelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "택배", description = "택배 API")
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/parcel")
public class ParcelController {
    private final ParcelService parcelService;

    @Operation(summary = "택배 등록 및 결제", description = "무게 수량 만큼 결제 되고 , 택배 등록")
    @PostMapping
    public ResponseEntity<?> registryParcel(@AuthenticationPrincipal PrincipalDetails principalDetails
            , @RequestBody ParcelRegistryRequest request) {
        parcelService.registryParcel(request, principalDetails.getId());

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "모든 택배 조회", description = "특정 지점 모든 택배 조회")
    @GetMapping()
    public ResponseEntity<ParcelInfoResponses> getAllParcelInfo(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ParcelInfoResponses responses = parcelService.getAllParcelInfo(principalDetails.getId());

        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "특정 택배 조회", description = "특정 택배 조회")
    @GetMapping("/{parcelId}")
    public ResponseEntity<ParcelInfoResponse> getParcelInfo(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                            @PathVariable Long parcelId) {
        ParcelInfoResponse response = parcelService.getParcelInfo(parcelId);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "택배 상태 변경", description = "택배의 배송 상태를 변경한다")
    @PatchMapping("/{parcelId}")
    public ResponseEntity<?> updateParcelState(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                               @PathVariable Long parcelId, @RequestBody ParcelStateRequest request) {
        parcelService.updateState(parcelId, request.getParcelState());

        return ResponseEntity.ok().build();
    }
}
