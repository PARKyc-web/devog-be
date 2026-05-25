package com.parkyc.devog.integration.controller;

import com.parkyc.devog.integration.seervice.IntegrationService;
import com.parkyc.devog.member.domain.code.OAuthType;
import com.parkyc.devog.member.repository.MemberOAuthRepository;
import com.parkyc.devog.member.repository.MemberRepository;
import com.parkyc.devog.member.service.MemberService;
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
    public String connect(HttpServletRequest request,
                          @PathVariable String provider,
                          @AuthenticationPrincipal DevogPrincipal user
    ){

        OAuthType type = OAuthType.from(provider);

        HttpSession session = request.getSession();
        session.setAttribute(CONNECT_PURPOSE, "CONNECT");
        session.setAttribute("PROVIDER", type);
        session.setAttribute(CONNECT_ID, user);

        System.out.println("At Connect/github");
        System.out.println("JSESSIONID :: " + session.getId());

        // 나중에 OAuthType 안에 URL 넣어두기
        return switch (type){
            case GITHUB -> "http://localhost:8080/oauth2/authorization/github";
            case GOOGLE -> "http://localhost:8080/oauth2/authorization/google";
            case NOTION -> "http://localhost:8080/oauth2/authorization/notion";
        };
    }

    @GetMapping("/callback/github")
    public void callback(
            HttpServletRequest request,
            HttpServletResponse response,
            OAuth2AuthenticationToken token,
            @RegisteredOAuth2AuthorizedClient("github") OAuth2AuthorizedClient client){

        HttpSession session = request.getSession(false);

        if(session == null){
            System.out.println(FAIL_URL + "?reason=session_expired");
            return;
        }

        System.out.println("At Callback");
        System.out.println("JSESSIONID :: " + session.getId());
        try{
            // OAuth 정보 및 User 정보 조회
            DevogPrincipal user = (DevogPrincipal) session.getAttribute(CONNECT_ID);

            System.out.println("Session은 살아있음?");
            System.out.println(session.getAttribute(CONNECT_PURPOSE));
            System.out.println(user);

            // Token 저장
            integrationService.connectExternalAccount(token, client, user);

            response.sendRedirect(SUCCESS_URL);
        } catch (Exception e){
            log.error("error >> ", e);

        } finally {
            // 외부 서비스 연결 이후 세션 삭제처리
            session.invalidate();
        }
    }

}
