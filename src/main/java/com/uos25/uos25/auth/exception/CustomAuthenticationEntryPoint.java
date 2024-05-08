package com.uos25.uos25.auth.exception;


import com.uos25.uos25.common.error.ErrorCode;
import com.uos25.uos25.common.error.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.error("[AUTH] : 가입되지 않은 사용자 접근 {}", authException.getMessage(), authException);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(
                ErrorResponse.of(ErrorCode.USER_ACCESS_DENIED, request.getRequestURI()).convertToJson()
        );

    }
}
