package com.uos25.uos25.etc.lotto.api;

import com.uos25.uos25.auth.login.entity.PrincipalDetails;
import com.uos25.uos25.etc.lotto.dto.LottoDTO.LottoAssignRequest;
import com.uos25.uos25.etc.lotto.dto.LottoDTO.LottoBuyRequest;
import com.uos25.uos25.etc.lotto.dto.LottoDTO.LottoBuyResponse;
import com.uos25.uos25.etc.lotto.dto.LottoDTO.LottoCheckResponse;
import com.uos25.uos25.etc.lotto.service.LottoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "로또", description = "로또 API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/lotto")
public class LottoController {
    private final LottoService lottoService;

    @Operation(summary = "로또 당첨 번호 등록", description = "지점 로또 당첨 번호 등록")
    @PostMapping("/")
    public ResponseEntity<?> assignLottoInfo(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                             @RequestBody LottoAssignRequest request) {
        lottoService.assignLottoInfo(principalDetails.getId(), request.getWinningNumber(), request.getPrice());

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "로또 구매", description = "로또 구매 및 이에 대한 결과 반환")
    @PostMapping("/buy")
    public ResponseEntity<?> buyLotto(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                      @RequestBody LottoBuyRequest request) {
        LottoBuyResponse response = lottoService.buyLotto(principalDetails.getId(), request.getAmount());

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "로또 번호 확인", description = "번호를 통해 로또 당첨 여부 조회")
    @GetMapping("/check/{numbers}")
    public ResponseEntity<LottoCheckResponse> checkLotto(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                         @PathVariable String numbers) {
        LottoCheckResponse response = lottoService.checkLotto(principalDetails.getId(), numbers);

        return ResponseEntity.ok().body(response);
    }
}
