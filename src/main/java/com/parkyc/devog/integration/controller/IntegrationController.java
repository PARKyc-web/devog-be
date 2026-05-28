package com.parkyc.devog.integration.controller;

import com.parkyc.devog.common.response.ApiResponse;
import com.parkyc.devog.integration.seervice.IntegrationService;
import com.parkyc.devog.member.domain.code.OAuthProvider;
import com.parkyc.devog.security.DevogPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/integration")
@RequiredArgsConstructor
public class IntegrationController {

    private final IntegrationService integrationService;

    private final String CONNECT_PURPOSE = "PURPOSE";
    private final String CONNECT_ID = "LOGIN_ID";
    private final String SUCCESS_URL = "http://localhost:5173/integration/success";
    private final String FAIL_URL = "http://localhost:5173/integration/fail";

    @GetMapping("/connect/{provider}")
    public ApiResponse<String> connect(HttpServletRequest request,
                          @PathVariable String provider,
                          @AuthenticationPrincipal DevogPrincipal user
    ){

        OAuthProvider type = OAuthProvider.from(provider);

        HttpSession session = request.getSession();
        session.setAttribute(CONNECT_PURPOSE, "CONNECT");
        session.setAttribute("PROVIDER", type);
        session.setAttribute(CONNECT_ID, user);

        // Think : OAuthProvider안에 넣는게 맞는지?
        return switch (type){
            case GITHUB -> ApiResponse.ok("http://localhost:8080/oauth2/authorization/github");
            case GOOGLE -> ApiResponse.ok("http://localhost:8080/oauth2/authorization/google");
            case NOTION -> ApiResponse.ok("http://localhost:8080/oauth2/authorization/notion");
        };
    }

    // Think : callback 함수를 여러개 두는지 아니면 하나에서 분기처리할지 고민
    @GetMapping("/callback/github")
    public void callback(
            HttpServletRequest request,
            HttpServletResponse response,
            OAuth2AuthenticationToken token,
            @RegisteredOAuth2AuthorizedClient("github") OAuth2AuthorizedClient client) throws IOException {

        HttpSession session = request.getSession(false);
        if(session == null){
            log.info(FAIL_URL + "?reason=session_expired");
            return;
        }

        try{
            // OAuth 정보 및 User 정보 조회
            DevogPrincipal user = (DevogPrincipal) session.getAttribute(CONNECT_ID);

            // Token 저장
            integrationService.connectGithub(token, client, user);

            response.sendRedirect(SUCCESS_URL);
        } catch (Exception e){
            log.error("Error >> ", e);
            response.sendRedirect(FAIL_URL);
        } finally {
            session.invalidate();
        }
    }

}
