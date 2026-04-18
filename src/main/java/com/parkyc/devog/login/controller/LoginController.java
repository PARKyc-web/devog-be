package com.parkyc.devog.login.controller;

import com.parkyc.devog.common.CookieProvider;
import com.parkyc.devog.common.HeaderProvider;
import com.parkyc.devog.common.code.ResponseCode;
import com.parkyc.devog.common.dto.CommonDTO;
import com.parkyc.devog.login.domain.dto.LoginDTO;
import com.parkyc.devog.login.service.LoginService;
import com.parkyc.devog.security.DevogUserDetails;
import io.jsonwebtoken.Header;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/lgn")
public class LoginController {

    private final LoginService loginService;

    private final HeaderProvider headerProvider;
    private final CookieProvider cookieProvider;

    @PostMapping("/login")
    public CommonDTO.Response<LoginDTO.Response> webLogin(HttpServletRequest request,
                                                          HttpServletResponse response,
                                                          @RequestBody LoginDTO.Request loginDTO) {
        LoginDTO.Response result = loginService.login(loginDTO);
        if(headerProvider.isAppPlatform(request)){
            return new CommonDTO.Response<>(ResponseCode.API_SUCCESS, result);
        }

        ResponseCookie cookie = cookieProvider.getRefreshCookie(result);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        result.setRefreshToken(null);
        return new CommonDTO.Response<>(ResponseCode.API_SUCCESS, result);
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
