package com.uos25.uos25.auth.service;

import com.uos25.uos25.auth.jwt.service.JwtService;
import com.uos25.uos25.auth.login.dto.LoginDTO.TokenResponse;
import com.uos25.uos25.auth.login.dto.SignUpDTO.SignUpRequest;
import com.uos25.uos25.funds.entity.Funds;
import com.uos25.uos25.funds.repository.FundsRepository;
import com.uos25.uos25.store.entity.Role;
import com.uos25.uos25.store.entity.Store;
import com.uos25.uos25.store.repository.StoreRepository;
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
    private final FundsRepository fundsRepository;

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

        Funds funds = Funds.builder()
                .store(store)
                .build();

        fundsRepository.save(funds);

        return TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();

    }
}

