package com.parkyc.devog.common;

import com.parkyc.devog.member.domain.code.OAuthType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) authentication;
        String provider = auth.getAuthorizedClientRegistrationId();

        HttpSession session = request.getSession();

        if("github".equals(provider)){
            response.sendRedirect("/integration/callback/github");
            return;
        }

        if("notion".equals(provider)){
            return;
        }
    }
}
