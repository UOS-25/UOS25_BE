package com.uos25.uos25.auth.login.service;

import com.uos25.uos25.auth.jwt.service.JwtService;
import com.uos25.uos25.auth.login.dto.LoginDTO.TokenResponse;
import com.uos25.uos25.store.repository.StoreRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final StoreRepository storeRepository;

    @Value("${jwt.access.expiration}")
    private String accessTokenExpiration;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String email = extractUsername(authentication); // 인증 정보에서 Username(email) 추출
        String accessToken = jwtService.createAccessToken(email); // JwtService의 createAccessToken을 사용하여 AccessToken 발급
        String refreshToken = jwtService.createRefreshToken(); // JwtService의 createRefreshToken을 사용하여 RefreshToken 발급

        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

        jwtService.sendAccessAndRefreshToken(response, accessToken,
                refreshToken); // 응답 헤더에 AccessToken, RefreshToken 실어서 응답

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(TokenResponse.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build().convertToJson());

        jwtService.updateRefreshToken(email, refreshToken);

        log.info("[AUTH] : 로그인에 성공하였습니다. 이메일 : {}", email);
        log.info("[AUTH] : 로그인에 성공하였습니다. AccessToken : {}", accessToken);
        log.info("[AUTH] : 발급된 AccessToken 만료 기간 : {}", accessTokenExpiration);
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}