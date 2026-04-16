package com.parkyc.devog.login.controller;

import com.parkyc.devog.login.domain.dto.LoginDTO;
import com.parkyc.devog.login.service.LoginService;
import com.parkyc.devog.security.DevogUserDetails;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/lgn")
public class LoginController {

    private final LoginService loginService;

    @Value("${token.expire.refresh}")
    private long refreshExpired;

    @PostMapping("/web/login")
    public LoginDTO.Response webLogin(HttpServletResponse response, LoginDTO.Request loginDTO) {
        LoginDTO.Response result = loginService.login(loginDTO);

        ResponseCookie cookie = ResponseCookie.from("refresh-token", result.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(refreshExpired)
                .build();
        response.addHeader(HttpHeaders.COOKIE, cookie.toString());

        return LoginDTO.Response.builder()
                .loginId(result.getLoginId())
                .accessToken(result.getAccessToken())
                .build();
    }

    @PostMapping("/app/login")
    public LoginDTO.Response appLogin(LoginDTO.Request loginDTO) {
        LoginDTO.Response result = loginService.login(loginDTO);

        // app의 경우 DTO로 내려주고 front에서 관리한다.

        return result;
    }

    @GetMapping("/oauth/callback")
    public Map<String, Object> githubCallback(@AuthenticationPrincipal OAuth2User oAuth2User,
                                              @RegisteredOAuth2AuthorizedClient("github") OAuth2AuthorizedClient authorizedClient) {

        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        /* Github */
        HashMap<String, Object> result = new HashMap<>();
        result.put("user-info", oAuth2User);
        result.put("token", accessToken.getTokenValue());
        result.put("expired", accessToken.getExpiresAt());

        return result;
    }

    @GetMapping("/check/user")
    public DevogUserDetails checkUserDetail(@AuthenticationPrincipal DevogUserDetails userDetails){
        return userDetails;
    }
}
