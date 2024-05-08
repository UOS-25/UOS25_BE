package com.uos25.uos25.auth.service;

import com.uos25.uos25.Store.entity.Role;
import com.uos25.uos25.Store.entity.Store;
import com.uos25.uos25.Store.repository.StoreRepository;
import com.uos25.uos25.auth.jwt.service.JwtService;
import com.uos25.uos25.auth.login.dto.LoginDTO.TokenResponse;
import com.uos25.uos25.auth.login.dto.SignUpDTO.SignUpRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignUpService {
    private final PasswordEncoder passwordEncoder;
    private final StoreRepository storeRepository;
    private final JwtService jwtService;

    @Transactional
    public TokenResponse signUp(SignUpRequest request) {

        String accessToken = jwtService.createAccessToken(request.getCode());
        String refreshToken = jwtService.createRefreshToken();

        Store store = Store.builder()
                .role(Role.USER)
                .code(request.getCode())
                .location(request.getLocation())
                .refreshToken(refreshToken)
                .build();

        log.info("[AUTH] : 회원가입 성공");

        store.passwordEncode(passwordEncoder);

        storeRepository.save(store);

        return TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();

    }
}

