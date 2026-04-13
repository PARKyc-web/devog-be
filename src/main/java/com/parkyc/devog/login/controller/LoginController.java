package com.parkyc.devog.login.controller;

import com.parkyc.devog.login.domain.dto.LoginDTO;
import com.parkyc.devog.login.service.LoginService;
import com.parkyc.devog.security.DevogUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/lgn")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public DevogUserDetails login(LoginDTO.Request loginDTO) {
        // loginService.login(loginDTO);

        return new DevogUserDetails();
    }


    @GetMapping("/oauth/callback")
    public OAuth2User githubCallback(@AuthenticationPrincipal OAuth2User oAuth2User) {

        return oAuth2User;
    }

    @GetMapping("/check/user")
    public DevogUserDetails checkUserDetail(@AuthenticationPrincipal DevogUserDetails userDetails){
        return userDetails;
    }
}
