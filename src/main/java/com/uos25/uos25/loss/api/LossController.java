package com.uos25.uos25.loss.api;

import com.uos25.uos25.auth.login.entity.PrincipalDetails;
import com.uos25.uos25.loss.dto.LossDTO;
import com.uos25.uos25.loss.service.LossService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Loss", description = "Loss API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/loss")
public class LossController {

    private final LossService lossService;
    @PostMapping("/save")
    public ResponseEntity<?> saveLoss(@RequestBody LossDTO lossDTO, @AuthenticationPrincipal PrincipalDetails principalDetails){
        lossService.saveLoss(lossDTO, principalDetails.getId());
        return ResponseEntity.ok().build();
    }
}
