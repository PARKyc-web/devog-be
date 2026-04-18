package com.parkyc.devog.common;

import com.parkyc.devog.login.domain.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CookieProvider {

    @Value("${token.expire.refresh}")
    private long refreshExpired;

    public ResponseCookie getRefreshCookie(LoginDTO.Response loginInfo){
        return ResponseCookie.from("refresh-token", loginInfo.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(Duration.ofMillis(refreshExpired).minusSeconds(10))
                .build();
        // Token 만료시간 보다 10초 정도 짧게 설정 > Token이 만료되었는데 쿠키에 남아있는 것을 방지
    }
}
