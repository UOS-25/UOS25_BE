package com.uos25.uos25.auth.api;


import static com.uos25.uos25.auth.login.dto.SignUpDTO.SignUpRequest;

import com.uos25.uos25.auth.login.dto.LoginDTO.LoginRequest;
import com.uos25.uos25.auth.login.dto.LoginDTO.TokenResponse;
import com.uos25.uos25.auth.service.SignUpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "Auth API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final SignUpService signUpService;

    @Operation(summary = "회원 가입")
    @PostMapping("/signUp")
    public ResponseEntity<TokenResponse> signUpBasic(@Valid @RequestBody SignUpRequest request) {
        log.info("[AUTH] : 기본 회원가입");
        TokenResponse response = signUpService.signUp(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok().build();
    }

}
