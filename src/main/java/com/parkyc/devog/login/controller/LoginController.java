package com.parkyc.devog.login.controller;

import com.parkyc.devog.common.response.ApiResponse;
import com.parkyc.devog.login.controller.request.LoginRequest;
import com.parkyc.devog.login.controller.response.LoginResponse;
import com.parkyc.devog.login.controller.response.LoginResponseWriter;
import com.parkyc.devog.login.service.LoginService;
import com.parkyc.devog.login.service.result.LoginResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final LoginResponseWriter writer;

    // 자체 회원가입 계정으로 로그인한다.
    @PostMapping("")
    public ApiResponse<LoginResponse>  login(@Valid @RequestBody LoginRequest login,
                                             HttpServletRequest request,
                                             HttpServletResponse response){
        LoginResult result = loginService.login(login.toCommand());

        return ApiResponse.ok(
                writer.write(request, response, result)
        );
    }

    @GetMapping("/oauth/callback")
    public String callback(OAuth2AuthenticationToken authentication,
                           @RegisteredOAuth2AuthorizedClient("github") OAuth2AuthorizedClient client){

        // 여기서 provider 보고 확인한다.
        // Response는 LoginResponse와 동일하게 나가야 함.
        String provider = authentication.getAuthorizedClientRegistrationId();
        OAuth2User user = authentication.getPrincipal();

        Map<String, Object> map = user.getAttributes();

        System.out.println(provider);
        System.out.println(user);
        System.out.println("###########");
        System.out.println(client.getAccessToken().getTokenValue());
        System.out.println(client.getAccessToken().getExpiresAt());
        System.out.println("###########");


        // return 할때 최초 로그인인지, 그리고 token을 반환

        return map.toString();
    }
}
