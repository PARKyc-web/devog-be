package com.parkyc.devog.login.service;

import com.parkyc.devog.common.exception.DevogApiException;
import com.parkyc.devog.login.exception.LoginErrorCode;
import com.parkyc.devog.login.exception.LoginException;
import com.parkyc.devog.login.service.command.LoginCommand;
import com.parkyc.devog.login.service.result.LoginResult;
import com.parkyc.devog.member.domain.code.OAuthType;
import com.parkyc.devog.member.domain.entity.MemberOAuth;
import com.parkyc.devog.member.repository.MemberOAuthRepository;
import com.parkyc.devog.member.repository.MemberRepository;
import com.parkyc.devog.security.DevogUserDetails;
import com.parkyc.devog.token.dto.LoginClaim;
import com.parkyc.devog.token.dto.LoginToken;
import com.parkyc.devog.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final MemberOAuthRepository oAuthRepository;
    private final AuthenticationManager authManager;
    private final TokenService tokenService;

    public LoginResult login(LoginCommand command){
        // Spring Security를 사용해서 로그인 검증
        // 실패 시, new LoginException(LoginErrorCode.INVALID_LOGIN_ID)
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        command.loginId(),
                        command.password()
                )
        );
        DevogUserDetails detail = (DevogUserDetails) auth.getPrincipal();
        List<String> roles = detail.getAuthorities().stream()
                .map(r -> r.getAuthority())
                .toList();

        LoginToken token = tokenService.issueLoginToken(
                new LoginClaim(detail.getLoginId(), roles)
        );

        return new LoginResult(detail.getLoginId(), token);
    }

    public LoginResult oAuthLogin(OAuth2AuthenticationToken oauth){

        String provider = oauth.getAuthorizedClientRegistrationId();
        OAuth2User user = oauth.getPrincipal();

        Map<String, Object> attributes = user.getAttributes();
        if(attributes == null){
            // 에러코드 대충 넣어둠.
            throw new LoginException(LoginErrorCode.INVALID_LOGIN_ID);
        }
        Optional<MemberOAuth> oauthInfo = oAuthRepository.findByOAuthTypeAndOAuthLoginId(
                OAuthType.GITHUB, (String) attributes.get("id")
        );

        if(oauthInfo.isEmpty()){
            // 아이디 생성
        }

        // token 생성

        return new LoginResult("", null);
    }

}
