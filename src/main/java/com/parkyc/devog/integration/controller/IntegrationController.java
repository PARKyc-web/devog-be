package com.parkyc.devog.integration.controller;

import com.parkyc.devog.security.DevogPrincipal;
import com.parkyc.devog.security.DevogUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;

@RestController
@RequestMapping("/integration")
public class IntegrationController {

    @GetMapping("/connect/callback")
    public void integration(OAuth2AuthenticationToken authentication,
                            @RegisteredOAuth2AuthorizedClient("github") OAuth2AuthorizedClient client,
                            @AuthenticationPrincipal DevogUserDetails userDetails){

        System.out.println(authentication);

        String provider = authentication.getAuthorizedClientRegistrationId();
        OAuth2User user = authentication.getPrincipal();

    }

    @GetMapping("/test")
    public String loginTest(@AuthenticationPrincipal DevogPrincipal userDetails){

        return userDetails.loginId();
    }

    // 연결 끊기
    @PostMapping("/close-connect")
    public void disconnect(){

    }

}
