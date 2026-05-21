package com.parkyc.devog.integration.controller;

import com.parkyc.devog.member.domain.code.OAuthType;
import com.parkyc.devog.member.repository.MemberOAuthRepository;
import com.parkyc.devog.member.repository.MemberRepository;
import com.parkyc.devog.security.DevogPrincipal;
import com.parkyc.devog.security.DevogUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.servlet.Session;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.security.Principal;

@RestController
@RequestMapping("/integration")
@RequiredArgsConstructor
public class IntegrationController {

    private final MemberRepository memberRepository;
    private final MemberOAuthRepository oAuthRepository;

    @GetMapping("/connect/{provider}")
    public String connect(HttpServletRequest request, @PathVariable String provider){

        OAuthType type = OAuthType.from(provider);

        HttpSession session = request.getSession();
        session.setAttribute("PURPOSE", "CONNECT");
        session.setAttribute("PROVIDER", type);

        return switch (type){
            case GITHUB -> "http://localhost:8080/oauth2/authorization/github";
            case GOOGLE -> "http://localhost:8080/oauth2/authorization/google";
            case NOTION -> "http://localhost:8080/oauth2/authorization/notion";
        };
    }

    @GetMapping("/callback/github")
    public String callback(OAuth2AuthenticationToken token,
                           @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client){

        return client.getPrincipalName();
    }

    @GetMapping("/test")
    public String loginTest(@AuthenticationPrincipal DevogPrincipal userDetails){

        return userDetails.loginId();
    }

}
